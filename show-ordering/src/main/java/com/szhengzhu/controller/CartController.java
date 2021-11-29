package com.szhengzhu.controller;

import com.szhengzhu.bean.xwechat.param.CartParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.CartService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated CartParam cartDetail) {
        return new Result<>(cartService.add(cartDetail));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated CartParam cartDetail) {
        cartService.modify(cartDetail);
    }

    @DeleteMapping(value = "/{tableId}")
    public void clearCart(@PathVariable("tableId") @NotBlank String tableId) {
        cartService.clearCart(tableId);
    }

    @GetMapping(value = "/{tableId}")
    public Map<String, Object> list(@PathVariable("tableId") @NotBlank String tableId) {
        return cartService.list(tableId);
    }
}
