package com.szhengzhu.bean.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 内部人员口令信息
 * @author Administrator
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManagerCode implements Serializable {
	
	private static final long serialVersionUID = -9101539683345825718L;

	private String markId;

	@NotBlank
    private String userId;

    private String code;

    private BigDecimal discount;

    private Boolean serverStatus;
    
    private String nickName;
    
    private String realName;
}