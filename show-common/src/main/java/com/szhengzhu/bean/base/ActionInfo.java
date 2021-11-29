package com.szhengzhu.bean.base;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Jehon Zeng
 */
@Data
public class ActionInfo implements Serializable {
    
    private static final long serialVersionUID = 3893171874049695533L;

    private String markId;

    private String name;

    @NotBlank
    private String actionCode;

    private String memo;
}