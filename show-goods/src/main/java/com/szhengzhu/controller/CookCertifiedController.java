package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.CookCertifiedService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/cooks")
public class CookCertifiedController {


    @Resource
    private CookCertifiedService cookCertifiedService;

    @PatchMapping(value = "/modify")
    public CookCertified modify(@RequestBody @Validated CookCertified cookCertified) {
        return cookCertifiedService.modify(cookCertified);
    }

    @GetMapping(value = "/{markId}")
    public CookCertified getCookById(@PathVariable("markId") @NotBlank String markId) {
        return cookCertifiedService.getCookerById(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<CookCertified> page(@RequestBody PageParam<CookCertified> cookPage) {
        return cookCertifiedService.page(cookPage);
    }

    @PostMapping(value = "/add")
    public CookCertified add(@RequestBody @Validated CookCertified cookCertified) {
        return cookCertifiedService.add(cookCertified);
    }

    @GetMapping(value = "/sale/rank")
    public List<Cooker> listCookerRank(@RequestParam(value = "userId", required = false) String userId) {
        return cookCertifiedService.listCookerRank(userId);
    }

    @PostMapping(value = "/sale/rank/goods/page")
    public PageGrid<Cooker> pageCookerRank(@RequestBody PageParam<String> cookerPage) {
        return cookCertifiedService.pageCookerRank(cookerPage);
    }

    @GetMapping(value = "/detail/{cookerId}")
    public Cooker getCookerDetail(@PathVariable("cookerId") @NotBlank String cookerId,
                                  @RequestParam(value = "userId", required = false) String userId) {
        return cookCertifiedService.getCookerDetail(cookerId, userId);
    }

    @PatchMapping(value = "/follow/or")
    public void followOr(@RequestBody @Validated CookFollow cookFollow) {
        cookCertifiedService.followOr(cookFollow);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox() {
        return cookCertifiedService.listCombobox();
    }
}
