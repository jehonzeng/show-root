package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.excel.VoucherCodeExcel;
import com.szhengzhu.bean.ordering.Voucher;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "代金券：VoucherController")
@RestController
@RequestMapping("/v1/voucher")
public class VoucherController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取代金券分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Voucher>> page(HttpServletRequest req, @RequestBody PageParam<Voucher> param) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Voucher voucher = ObjectUtil.isNull(param.getData()) ? new Voucher() : param.getData();
        voucher.setStoreId(storeId);
        param.setData(voucher);
        return showOrderingClient.pageVoucher(param);
    }

    @ApiOperation(value = "添加代金券")
    @PostMapping(value = "")
    public Result add(HttpServletRequest req, @RequestBody @Validated Voucher voucher) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        voucher.setStoreId(storeId);
        return showOrderingClient.addVoucher(voucher);
    }

    @ApiOperation(value = "修改代金券")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Voucher voucher) {
        return showOrderingClient.modifyVoucher(voucher);
    }

    @ApiOperation(value = "导出代金券码")
    @GetMapping(value = "/code/download")
    public Result downloadCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("voucherId") @NotBlank String voucherId) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        String name = voucherId + ".xls";
        String fileName;
        if (StringUtils.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<VoucherCodeExcel>> result = showOrderingClient.listVoucherCode(voucherId);
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "配送信息", VoucherCodeExcel.class);
        try {
            OutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }

    @ApiOperation(value = "扫码获取券信息")
    @GetMapping(value = "/code/{code}")
    public Result<Voucher> getCodeInfo(@PathVariable("code") @NotBlank String code) {
        return showOrderingClient.getVoucherCodeInfo(code);
    }

    @ApiOperation(value = "确认使用代金券")
    @GetMapping(value = "/code/use")
    public Result useCode(@RequestParam @NotBlank String code, @RequestParam @NotNull Integer amount) {
        return showOrderingClient.useVoucherCode(code, amount);
    }
}
