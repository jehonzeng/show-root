package com.szhengzhu.code;

/**
 * @author Jehon Zeng
 */
public interface GroupStatus {
    
    /** 待成团 */
    String UNGROUP = "GT01";

    /** 已成团 */
    String SUCCEED = "GT02";
    
    /** 拼团失败 */
    String FAILED = "GT03";
}
