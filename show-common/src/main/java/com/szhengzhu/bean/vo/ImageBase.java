package com.szhengzhu.bean.vo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片流信息
 * @author Administrator
 * @date 2019年3月11日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageBase implements Serializable{

    private static final long serialVersionUID = 504264016549868785L;
    
    private BufferedImage bufferedImage;
    
    private String type;

}
