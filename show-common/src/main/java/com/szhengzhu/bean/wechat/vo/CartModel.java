package com.szhengzhu.bean.wechat.vo;

import com.szhengzhu.bean.goods.ShoppingCart;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class CartModel implements Serializable {

    private static final long serialVersionUID = 8973436180570569000L;

    @NotBlank
    private String userId;

    @NotEmpty
    private List<ShoppingCart> cartList;
}
