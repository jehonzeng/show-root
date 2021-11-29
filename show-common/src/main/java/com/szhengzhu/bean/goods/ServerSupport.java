package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 服务支持信息
 * 
 * @author Administrator
 * @date 2019年3月27日
 */
@Data
public class ServerSupport implements Serializable {

    private static final long serialVersionUID = 9218683229590830153L;

    private String markId;

    @NotBlank
    private String theme;

    private String detailExplain;

    private Boolean serverStatus;

}