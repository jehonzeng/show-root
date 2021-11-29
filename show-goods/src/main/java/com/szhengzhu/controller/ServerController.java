package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ServeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 商品服务支持信息管理
 * 
 * @author Administrator
 * @date 2019年4月1日
 */
@Validated
@RestController
@RequestMapping(value = "serves")
public class ServerController {

    @Resource
    private ServeService serveService;

    @PostMapping(value = "/add")
    public ServerSupport addServer(@RequestBody @Validated ServerSupport base) {
        return serveService.saveServer(base);
    }

    @PatchMapping(value = "/update")
    public ServerSupport modifyServer(@RequestBody @Validated ServerSupport base) {
        return serveService.modifyServer(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<ServerSupport> serverPage(@RequestBody PageParam<ServerSupport> base) {
        return serveService.getPage(base);
    }

    @GetMapping(value = "/list")
    public List<Combobox> listServer() {
        return serveService.listServer();
    }
    
    @GetMapping(value = "/{markId}")
    public ServerSupport getServeById(@PathVariable("markId") @NotBlank String markId){
        return serveService.getServeById(markId);
    }
    

}