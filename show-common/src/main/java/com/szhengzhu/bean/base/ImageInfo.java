package com.szhengzhu.bean.base;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图片信息
 * 
 * @author Administrator
 * @date 2019年3月5日
 */
@Builder
@NoArgsConstructor
@Data
public class ImageInfo implements Serializable {

    private static final long serialVersionUID = 8421486906021961502L;

    private String markId;

    private String imagePath;

    private String fileType;
    
    public ImageInfo(String markId, String imagePath, String fileType) {
        this.markId = markId;
        this.imagePath = imagePath;
        this.fileType = fileType.substring(1);//移除文件类型前面的.号
    }
}
