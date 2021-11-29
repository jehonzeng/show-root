package com.szhengzhu.bean.xwechat.vo;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Data
public class CommodityModel implements Serializable {
    
    private static final long serialVersionUID = -129494023286904590L;

    private String commId;
    
    private String commCode;

    private String commName;

    private String introduction;
    
    private Integer quantity;

    private List<String> tagImgs;
    
    private List<String> imgs;
    
    private List<PriceModel> priceList;
    
    private List<SpecsModel> specsList;
    
    public void setTagImgs(String tagImgs) {
        if (StrUtil.isEmpty(tagImgs)) {
            this.tagImgs = new ArrayList<>();
        } else {
            this.tagImgs = Arrays.asList(tagImgs.split(","));
        }
    }
    
    public void setImgs(String imgs) {
        if (StrUtil.isEmpty(imgs)) {
            this.imgs = new ArrayList<>();
        } else {
            this.imgs = Arrays.asList(imgs.split(","));
        }
    }
}
