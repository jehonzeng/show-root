package com.szhengzhu.bean;

import com.szhengzhu.bean.xwechat.param.CartParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartServerData implements Serializable {

    private static final long serialVersionUID = -7564393768229777560L;

    private String nickName;
    
    private String headerImg;
    
    private String opt;
    
    private CartParam commodity;
    
    private String tableId;
    
    private String indentId;
    
    private Object cartList;
    
    private String userId;
    
    public void setCartList(Object cartList) {
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        this.cartList = cartList;
    }
}
