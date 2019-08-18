package com.szhengzhu.common;

public class Commons {
    public static final String HEADER = "Authorization";// 存放Token的Header
    public static final String SESSION = "user";// session用户存储名称
    public static final int DEF_CONCURRENT = 2;// 并发消费者的个数
    public static final int DEF_PREFEICH = 50;// 一次性从broker里面取的待消费的消息的个数
    public static final String DEF_IMAGE = "default_";
}
