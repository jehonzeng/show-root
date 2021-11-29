package com.szhengzhu.bean.kd;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class KdInfo implements Serializable {

    private static final long serialVersionUID = -2840770761442998568L;

    /** 物流轨迹节点内容 */
    private String context;

    /** 时间，原始格式 */
    private String time;

    /** 格式化后时间 */
    private String ftime;

    /** 本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现 */
    private String status;

    /** 本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现 */
    private String areaCode;

    /** 本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现 */
    private String areaName;
}
