package com.szhengzhu.bean.activity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SceneItem implements Serializable {

    private static final long serialVersionUID = 351697510325425507L;

    private String markId;

    private String orderId;

    private String goodsId;

    private String goodsName;

    private Integer quantity;

    private Boolean serverStatus;
}