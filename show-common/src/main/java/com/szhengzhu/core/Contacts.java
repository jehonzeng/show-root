package com.szhengzhu.core;

import java.io.File;

/**
 * 公共常量
 * @author Administrator
 * @date 2019年2月28日
 */
public class Contacts {
    
    public static final String TIMEZONE = "GMT+08:00";
    
    public static final String TIME_MILLSECOND = "yyyy-MM-dd HH:mm:ss SSS";
    
    public static final String TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    
    public static final String TIME_MIN = "yyyy-MM-dd HH:mm";
    
    public static final String TIME_DATE = "yyyy-MM-dd";
    
    public static final String IMAGE_SERVER="http://192.168.31.133:2004";
    
    public static final Integer DEF_IMG_SIZE = 500;
    
    public static final String BASE_IMG_PATH = File.separator + "usr" + File.separator + "pictures";
    
    public static final String CONSUMES = "application/json";
}
