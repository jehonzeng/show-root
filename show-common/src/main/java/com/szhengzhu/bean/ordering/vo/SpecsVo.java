package com.szhengzhu.bean.ordering.vo;

import java.util.List;

import com.szhengzhu.bean.vo.Combobox;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpecsVo extends Combobox {

    private static final long serialVersionUID = 1177101879923582433L;
    
    private List<Combobox> itemList;
}
