package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.TableParam;
import com.szhengzhu.bean.ordering.vo.TableBaseVo;
import com.szhengzhu.bean.ordering.vo.TableVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.xwechat.param.TableOpenParam;
import com.szhengzhu.bean.xwechat.vo.TableModel;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weixin.popular.api.QrcodeAPI;
import weixin.popular.bean.qrcode.QrcodeTicket;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "餐桌：TableController")
@RestController
@RequestMapping("/v1/table")
public class TableController {

    @Resource
    private WechatConfig config;

    @Resource
    private FtpServer ftpServer;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取餐桌分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<TableVo>> page(HttpServletRequest req, @RequestBody PageParam<TableParam> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        TableParam param = ObjectUtil.isNull(pageParam.getData()) ? new TableParam() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageTable(pageParam);
    }

    @ApiOperation(value = "获取餐桌详细信息")
    @GetMapping(value = "/{tableId}")
    public Result<Table> getInfo(@PathVariable("tableId") @NotBlank String tableId) {
        return showOrderingClient.getTableInfo(tableId);
    }

    @ApiOperation(value = "添加餐桌")
    @PostMapping(value = "")
    public Result<String> add(HttpServletRequest req, @RequestBody @Validated Table table) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        table.setStoreId(storeId);
        return showOrderingClient.addTable(table);
    }

    @ApiOperation(value = "生成餐桌二维码")
    @GetMapping(value = "/qrcode/create")
    public Result<String> createQrcode(@RequestParam("tableId") @NotBlank String tableId) {
        Result<Table> tableResult = showOrderingClient.getTableInfo(tableId);
        ShowAssert.checkResult(tableResult);
        String codePath = "";
        Table table = tableResult.getData();
        ImageInfo imageInfo = new ImageInfo();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        imageInfo.setMarkId(snowflake.nextIdStr());
        try {
            QrcodeTicket ticket = QrcodeAPI.qrcodeCreateFinal(config.getToken(),
                    Contacts.TABLE_CODE_PREFIX + "_" + table.getStoreId() + "_" + table.getCode());
            if (ticket.isSuccess()) {
                table.setQrUrl(ticket.getUrl());
                BufferedImage image = QrcodeAPI.showqrcode(ticket.getTicket());
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(image, "png", output);
                byte[] buff = output.toByteArray();
                InputStream in = new ByteArrayInputStream(buff);
                String savePath = "/table/" + DateUtil.today() + "/";
                String newFileName = imageInfo.getMarkId() + ".png";
                boolean result = ftpServer.upload(savePath, newFileName, in);
//                boolean result = FtpUtils.uploadFile(ftpServer, savePath, newFileName, in);
                if (!result) {
                    return new Result<>(codePath);
                }
                imageInfo.setImagePath(savePath + newFileName);
                imageInfo.setFileType("png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        showBaseClient.addImgInfo(imageInfo);
        table.setQrCode(imageInfo.getMarkId());
        ShowAssert.checkResult(showOrderingClient.modifyTable(table));
        return new Result<>(imageInfo.getImagePath());
    }

    @ApiOperation(value = "修改餐桌")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Table table) {
        return showOrderingClient.modifyTable(table);
    }

    @ApiOperation(value = "删除餐桌")
    @DeleteMapping(value = "/{tableId}")
    public Result delete(@PathVariable("tableId") @NotBlank String tableId) {
        return showOrderingClient.deleteTable(tableId);
    }

    @ApiOperation(value = "获取餐桌列表", notes = "点餐时获取餐桌列表")
    @PostMapping(value = "/res/list")
    public Result<List<TableBaseVo>> listResByStore(HttpServletRequest req,
                                                    @RequestBody(required = false) TableReservation tableReservation) {
        tableReservation.setStoreId((String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE));
        return showOrderingClient.listResTableByStore(tableReservation);
    }

    @ApiOperation(value = "获取餐桌信息 ", notes = "点餐时获取餐桌信息")
    @GetMapping(value = "/res/info")
    public Result<TableModel> getLjsTableInfo(@RequestParam("tableId") @NotBlank String tableId) {
        return showOrderingClient.getLjsTableInfo(tableId);
    }

    @ApiOperation(value = "换桌 ", notes = "点餐时换桌")
    @GetMapping(value = "/res/change")
    public Result<List<String>> changeTable(HttpServletRequest req, @RequestParam("oldTableId") @NotBlank String oldTableId,
                                            @RequestParam("newTableId") @NotBlank String newTableId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.changeTable(oldTableId, newTableId, employeeId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "开桌修改用餐人数 ", notes = "点餐时开桌")
    @PostMapping(value = "/res/open")
    public Result open(@RequestBody @Validated TableOpenParam param) {
        return showOrderingClient.openTable(param);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "清理餐桌 ", notes = "点餐时清理餐桌")
    @GetMapping(value = "/res/clear")
    public Result clear(@RequestParam("tableId") @NotBlank String tableId) {
        return showOrderingClient.clearTable(tableId);
    }

    @ApiOperation(value = "获取餐桌区域分页列表")
    @PostMapping(value = "/area/page")
    public Result<PageGrid<TableArea>> pageArea(HttpServletRequest req, @RequestBody PageParam<TableArea> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        TableArea param = ObjectUtil.isNull(pageParam.getData()) ? new TableArea() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageTableArea(pageParam);
    }

    @ApiOperation(value = "添加餐桌区域")
    @PostMapping(value = "/area")
    public Result<String> addArea(HttpServletRequest req, @RequestBody @Validated TableArea tableArea) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        tableArea.setStoreId(storeId);
        return showOrderingClient.addTableArea(tableArea);
    }

    @ApiOperation(value = "修改餐桌区域")
    @PatchMapping(value = "/area")
    public Result<TableArea> modifyArea(@RequestBody @Validated TableArea tableArea) {
        return showOrderingClient.modifyTableArea(tableArea);
    }

    @ApiOperation(value = "删除餐桌区域")
    @DeleteMapping(value = "/area/{areaId}")
    public Result deleteArea(@PathVariable("areaId") @NotBlank String areaId) {
        return showOrderingClient.deleteTableArea(areaId);
    }

    @ApiOperation(value = "获取餐桌区域键值对列表")
    @GetMapping(value = "/area/combobox")
    public Result<List<Combobox>> listAreaCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.listTableAreaCombobox(storeId);
    }

    @ApiOperation(value = "获取餐桌类型分页列表")
    @PostMapping(value = "/cls/page")
    public Result<PageGrid<TableCls>> pageCls(HttpServletRequest req, @RequestBody PageParam<TableCls> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        TableCls param = ObjectUtil.isNull(pageParam.getData()) ? new TableCls() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageTableCls(pageParam);
    }

    @ApiOperation(value = "添加餐桌类型")
    @PostMapping(value = "/cls")
    public Result<String> addCls(HttpServletRequest req, @RequestBody @Validated TableCls tableCls) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        tableCls.setStoreId(storeId);
        return showOrderingClient.addTableCls(tableCls);
    }

    @ApiOperation(value = "修改餐桌类型")
    @PatchMapping(value = "/cls")
    public Result modifyCls(@RequestBody @Validated TableCls tableCls) {
        return showOrderingClient.modifyTableCls(tableCls);
    }

    @ApiOperation(value = "删除餐桌类型")
    @DeleteMapping(value = "/cls/{clsId}")
    public Result deleteCls(@PathVariable("clsId") @NotBlank String clsId) {
        return showOrderingClient.deleteTableCls(clsId);
    }

    @ApiOperation(value = "获取餐桌类型键值对列表")
    @GetMapping(value = "/cls/combobox")
    public Result<List<Combobox>> listClsCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.listTableClsCombobox(storeId);
    }

    @ApiOperation(value = "获取餐桌状态分页列表")
    @PostMapping(value = "/status/page")
    public Result<PageGrid<TableStatus>> pageStatus(HttpServletRequest req,
                                                    @RequestBody PageParam<TableStatus> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        TableStatus param = ObjectUtil.isNull(pageParam.getData()) ? new TableStatus() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageTableStatus(pageParam);
    }

    @ApiOperation(value = "添加状态")
    @PostMapping(value = "/status")
    public Result<TableStatus> addStatus(HttpServletRequest req, @RequestBody @Validated TableStatus tableStatus) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        tableStatus.setStoreId(storeId);
        return showOrderingClient.addTableStatus(tableStatus);
    }

    @ApiOperation(value = "修改状态")
    @PatchMapping(value = "/status")
    public Result modifyStatus(@RequestBody @Validated TableStatus tableStatus) {
        return showOrderingClient.modifyTableStatus(tableStatus);
    }

    @ApiOperation(value = "删除餐桌状态")
    @DeleteMapping(value = "/status/{statusId}")
    public Result deleteStatus(@PathVariable("statusId") @NotBlank String statusId) {
        return showOrderingClient.deleteTableStatus(statusId);
    }

    @ApiOperation(value = "获取餐桌状态键值对列表")
    @GetMapping(value = "/status/combobox")
    public Result<List<Combobox>> listStatusCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.listTableStatusCombobox(storeId);
    }
}
