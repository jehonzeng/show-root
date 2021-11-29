package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.Store;
import com.szhengzhu.bean.ordering.Table;
import com.szhengzhu.bean.ordering.TableReservation;
import com.szhengzhu.bean.ordering.param.TableParam;
import com.szhengzhu.bean.ordering.vo.TableBaseVo;
import com.szhengzhu.bean.ordering.vo.TableVo;
import com.szhengzhu.bean.xwechat.param.TableOpenParam;
import com.szhengzhu.bean.xwechat.vo.TableModel;
import com.szhengzhu.code.IndentStatus;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.code.TableStatus;
import com.szhengzhu.mapper.CartMapper;
import com.szhengzhu.mapper.IndentMapper;
import com.szhengzhu.mapper.StoreMapper;
import com.szhengzhu.mapper.TableMapper;
import com.szhengzhu.print.PrintService;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.TableService;
import com.szhengzhu.util.ShowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Service("tableService")
public class TableServiceImpl implements TableService {

    @Resource
    private Sender sender;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private TableMapper tableMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private IndentMapper indentMapper;

    @Resource
    private PrintService printService;

    @Override
    public PageGrid<TableVo> page(PageParam<TableParam> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx()) ? "t.create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<TableVo> pageInfo = new PageInfo<>(tableMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Table getInfo(String tableId) {
        return tableMapper.selectByPrimaryKey(tableId);
    }

    @Override
    public String add(Table table) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        table.setMarkId(markId);
        table.setCreateTime(DateUtil.date());
        table.setTableStatus(TableStatus.FREEING.code);
        tableMapper.insertSelective(table);
        return markId;
    }

    @Override
    public void modify(Table table) {
        table.setModifyTime(DateUtil.date());
        tableMapper.updateByPrimaryKeySelective(table);
    }

    @Override
    public void delete(String tableId) {
        tableMapper.updateStatus(tableId, -1);
    }

    @Override
    public TableModel getLjsTableInfo(String tableId) {
        Table table = tableMapper.selectByPrimaryKey(tableId);
        Store store = storeMapper.selectByPrimaryKey(table.getStoreId());
        TableModel tableModel = TableModel.builder().tableId(tableId).tableCode(table.getCode()).seats(table.getSeats())
                .manNum(table.getManNum()).tableStatus(table.getTableStatus()).storeId(store.getMarkId()).storeName(store.getName()).build();
        Date date = DateUtil.date();
        int currTime = DateUtil.hour(date, true) * 60 + DateUtil.minute(date);
        int open = DateUtil.hour(store.getOpenBusiness(), true) * 60 + DateUtil.minute(store.getOpenBusiness());
        int close = DateUtil.hour(store.getCloseBusiness(), true) * 60 + DateUtil.minute(store.getCloseBusiness());
        boolean storeStatus = store.getStatus().equals(1) && currTime >= open && currTime <= close;
        tableModel.setStoreStatus(storeStatus);
        Indent indent = indentMapper.selectByTable(tableId, table.getTempNum());
        if (ObjectUtil.isNotNull(indent)) {
            tableModel.setIndentId(indent.getMarkId());
            tableModel.setUserId(indent.getMemberId());
        }
        return tableModel;
    }

    @Override
    public List<TableBaseVo> listResByStore(TableReservation tableReservation) {
        return tableMapper.selectResByStore(tableReservation);
    }

    @Transactional
    @Override
    public List<String> changeTable(String oldTableId, String newTableId, String employeeId) {
        Table oldTable = tableMapper.selectByPrimaryKey(oldTableId);
        Indent indent = indentMapper.selectByTable(oldTableId, oldTable.getTempNum());
        Table newTable = tableMapper.selectByPrimaryKey(newTableId);
        tableMapper.clearTable(oldTableId);
        newTable.setManNum(oldTable.getManNum());
        newTable.setOpenTime(oldTable.getOpenTime());
        newTable.setTempNum(oldTable.getTempNum());
        newTable.setTableStatus(oldTable.getTableStatus());
        tableMapper.deleteByPrimaryKey(newTableId);
        tableMapper.insertSelective(newTable);
        indent.setTableId(newTableId);
        indent.setModifier(employeeId);
        indent.setModifyTime(DateUtil.date());
        indentMapper.updateByPrimaryKeySelective(indent);
        // 换桌提示，制作部提示（某某桌换某某桌），重新打印客户预览单(待测试)
        String tableCode = StrUtil.format("{}换{}", oldTable.getCode(), newTable.getCode());
        List<String> logStamps = printService.orderOrChange(indent.getMarkId(), null, employeeId, tableCode, oldTable.getStoreId());
        // 给当前桌台用户推送信息
        sender.changeTablePush(indent.getMarkId(), newTable.getMarkId(), newTable.getName());
        return logStamps;
    }

    @Transactional
    @Override
    public void open(TableOpenParam param) {
        Table table = tableMapper.selectByPrimaryKey(param.getTableId());
        if (StrUtil.isEmpty(table.getTempNum())) {
            table.setTempNum(ShowUtils.createTempnum());
        }
        if (ObjectUtil.isNull(table.getOpenTime())) {
            table.setOpenTime(DateUtil.date());
        }
        table.setManNum(param.getManNum());
        if (TableStatus.FREEING.code.equals(table.getTableStatus())) {
            table.setTableStatus(TableStatus.ORDERING.code);
        }
        tableMapper.updateByPrimaryKeySelective(table);
        // 预点餐后扫码绑定到餐桌
        if (StrUtil.isEmpty(param.getUserId())) {
            cartMapper.updateByUser(param.getUserId(), param.getTableId());
        }
        indentMapper.updateManNum(param.getTableId(), table.getTempNum(), param.getManNum());
    }

    @Transactional
    @Override
    public void clear(String tableId) {
        Table table = tableMapper.selectByPrimaryKey(tableId);
        Indent indent = indentMapper.selectByTable(tableId, table.getTempNum());
        if (ObjectUtil.isNotNull(indent)) {
            indentMapper.updateIndentStatus(indent.getMarkId(), IndentStatus.FAILURE.code);
        }
        tableMapper.clearTable(tableId);
        cartMapper.clearCartByTable(tableId);
    }

    @Transactional
    @Override
    public void modifyManNum(String tableId, int manNum) {
        Table table = tableMapper.selectByPrimaryKey(tableId);
        table.setManNum(manNum);
        tableMapper.updateByPrimaryKeySelective(table);
        indentMapper.updateManNum(tableId, table.getTempNum(), manNum);
    }

    @Override
    public Table getInfoByStoreCode(String storeId, String tableCode) {
        return tableMapper.selectByStoreCode(storeId, tableCode);
    }

    @Override
    public String getTableId(String qrUrl) {
        return tableMapper.selectIdByUrl(qrUrl);
    }
}
