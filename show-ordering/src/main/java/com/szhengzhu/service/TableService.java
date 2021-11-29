package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Table;
import com.szhengzhu.bean.ordering.TableReservation;
import com.szhengzhu.bean.ordering.param.TableParam;
import com.szhengzhu.bean.ordering.vo.TableBaseVo;
import com.szhengzhu.bean.ordering.vo.TableVo;
import com.szhengzhu.bean.xwechat.param.TableOpenParam;
import com.szhengzhu.bean.xwechat.vo.TableModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface TableService {

    /**
     * 获取餐桌分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<TableVo> page(PageParam<TableParam> pageParam);

    /**
     * 获取餐桌详细信息
     *
     * @param tableId
     * @return
     */
    Table getInfo(String tableId);

    /**
     * 添加餐桌
     *
     * @param table
     * @return
     */
    String add(Table table);

    /**
     * 修改餐桌
     *
     * @param table
     * @return
     */
    void modify(Table table);

    /**
     * 删除餐桌
     *
     * @param tableId
     * @return
     */
    void delete(String tableId);

    /**
     * 小程序获取该餐桌及门店状态信息
     *
     * @param tableId
     * @return
     */
    TableModel getLjsTableInfo(String tableId);

    /**
     * 点餐获取餐桌信息列表(根据菜品名称)
     *
     * @param tableReservation
     * @return
     */
    List<TableBaseVo> listResByStore(TableReservation tableReservation);

    /**
     * 点餐时换桌
     *
     * @param oldTableId
     * @param newTableId
     * @return
     */
    List<String> changeTable(String oldTableId, String newTableId, String employeeId);

    /**
     * 点餐时开桌
     *
     * @param param
     * @return
     */
//    ?> open(String tableId, Integer manNum);
    void open(TableOpenParam param);

    /**
     * 清桌
     *
     * @param tableId
     * @return
     */
    void clear(String tableId);

    /**
     * 小程序修改用餐人数
     *
     * @param tableId
     * @param manNum
     * @return
     */
    void modifyManNum(String tableId, int manNum);

    /**
     * 根据餐厅与桌号获取餐桌信息
     *
     * @param storeId
     * @param tableCode
     * @return
     */
    Table getInfoByStoreCode(String storeId, String tableCode);

    /**
     * 扫二维码信息获取餐桌id
     *
     * @param qrUrl
     * @return
     */
    String getTableId(String qrUrl);
}
