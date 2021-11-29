package com.szhengzhu.core;

/**
 * 枚举型自定义状态码，方便调试
 * 4** : 客户端异常
 * 5** : 服务端异常
 *
 * @author Administrator
 * @date 2019年2月20日
 */
public enum StatusCode {

    _4000("4000", "请输入正确的手机号!"),
    _4001("4001", "操作频繁，请稍后再试！"),
    _4002("4002", "验证码错误！"),
    _4003("4003", "验证码超时！"),
    _4004("4004", "提交数据有误，请重试！"),
    _4005("4005", "用户未登录！"),
    _4006("4006", "时间过期，无法设置与修改节假日！"),
    _4007("4007", "重复添加！"),
    _4008("4008", "上传到图片服务器出现异常，请联系管理员！"),
    _4009("4009", "图片上传失败！"),
    _4010("4010", "配送日期不可以为节假日！"),
    _4011("4011", "该用户不存在！"),
    _4012("4012", "该用户未授权或登录失效！"),
    _4013("4013", "登录失效！"),
    _4014("4014", "订单不存在！"),
    _4015("4015", "该订单不可申请退款！"),
    _4016("4016", "该订单不可取消！"),
    _4017("4017", "该账号不是后台管理员账号！"),
    _4018("4018", "请先维护商品类型和商品类型规格！"),
    _4019("4019", "请先维护该商品规格价！"),
    _4020("4020", "配送信息未更新！"),
    _4021("4021", "获取打印信息失败"),
    _4022("4022", "该订单不可以取消申请退款！"),
    _4023("4023", "该订单不可以确认收货！"),
    _4024("4024", "该订单非支付订单，不可备货！"),
    _4025("4025", "您来晚了，奖品没有了！"),
    _4026("4026", "时间未到或已经超时，无法操作！"),
    _4027("4027", "您已经领取完毕！"),
    _4028("4028", "您沒有达到领取条件！"),
    _4029("4029", "礼物领取方式错误！"),
    _4030("4030", "您非新用户，无资格领取！"),
    _4031("4031", "您不能给自己助力！"),
    _4032("4032", "您已经没有助力次数了！"),
    _4033("4033", "您还未关注，请先关注！"),
    _4034("4034", "该手机号未充值！"),
    _4035("4035", "余额不足！"),
    _4036("4036", "口令错误，请重新输入！"),
    _4037("4037", "用户登录超时！"),
    _4038("4038", "授权失败，请重试！"),
    _4039("4039", "该用户未关联门店，请联系管理员！"),
    _4040("4040", "请添加订单或用餐人数！"),
    _4041("4041", "请支付剩余订单金额！"),
    _4042("4042", "订单金额已支付，请到前台确认！"),
    _4043("4043", "支付金额已超出订单剩余支付金额，请核对！"),
    _4044("4044", "餐台已被使用，不能进行该操作！"),
    _4045("4045", "非支付订单，无法退款"),
    _4046("4045", "订单退款失败"),
    _4047("4047", "条形码已过期，请刷新重试！"),
    _4048("4048", "该券不能使用！"),
    _4049("4049", "未注册会员！"),
    _4050("4050", "该账号已在其他设备登录！"),
    _4051("4051", "订单不可重复支付，请确认！"),
    _4052("4052", "部分商品已参与其他活动，请确认！"),
    _4053("4053", "该桌台正在结账，请进入订单确认！"),
    _4054("4054", "该订单已结账，请确认！"),
    _4055("4055", "该券已被使用或不可用！"),
    _4056("4056", "该手机号已注册会员！"),
    _4057("4057", "积分不足！"),
    _4058("4058", "不可修改其他用户兑换的积分商品！"),
    _4059("4059", "用户投票次数不够！"),
    _4060("4060", "条形码失效，重新扫码！"),
    _4061("4061", "该竞赛活动已停止投票！"),
    _4062("4062", "投票次数已用完！"),
    _5000("5000", "服务器异常！"),
    _5001("5001", "验证码发送失败！"),
    _5002("5002", "该用户不存在！"),
    _5003("5003", "菜品已售罄！"),
    _5004("5004", "无效code,请重新获取！"),
    _5005("5005", "文件格式不允许！"),
    _5006("5006", "该订单已经支付！"),
    _5007("5007", "该团单已结束，请选择其他团单进行拼团！"),
    _5008("5008", "同一规格名不可以设置多个默认值！"),
    _5009("5009", "券发放失败！"),
    _5010("5010", "服务器加载中…"),
    _5011("5011", "商品已设置预上架，无法手动操作！"),
    _5012("5012", "无法进行状态更改！"),
    _5013("5013", "下单失败！"),
    _5014("5014", "支付超时，订单已取消！"),
    _5015("5015", "非内部人员，无权限访问！"),
    _5016("5016", "该地址不可配送！"),
    _5017("5017", "该商品已售完！"),
    _5018("5018", "该活动参与资格已使用完！"),
    _5019("5019", "该记录不可撤销！"),
    _5020("5020", "非本店员工，无权操作！"),
    _5021("5021", "该手机号已被注册！"),
    _5022("5022", "生日每年只可修改一次！"),
    _5023("5023", "为撤销记录，无法再次操作！"),
    _5024("5024", "该手机号已注册会员！"),
    _5025("5025", "餐台未开桌，请联系餐厅服务员！"),
    _5026("5026", "该券只为部分员工可使用！"),
    _5027("5027", "该订单未绑定会员！"),
    _5028("5028", "获取手机号错误！"),
    _5029("5029", "该订单已绑定会员！"),
    _5030("5030", "微信统一下单失败！"),
    _5031("5031", "厨房正在准备中，请稍后买单！"),
    _5032("5032", "该记录为用户自助充值，不可删除！"),
    _5033("5033", "该商品已沽清！"),
    _5034("5034", "今天已经领取过了,请明天再来领取！"),
    _5035("5035", "无权限"),
    _5036("5036", "已评论过"),
    _5037("5037", "已抽过奖"),
    _5038("5038", "该订单未结账！"),
    _5039("5039", "本次竞赛活动已结束！"),
    _5040("5040", "投票失败！"),
    _5041("5041", "您兑换券的数量不足"),
    _5042("5042", "您今天已经签到过了"),
    _5043("5043", "活动暂停使用"),
    _5044("5044", "已有一条可用的记录，请对当前记录进行操作"),
    _5045("5045", "天数不可以重复"),
    _5046("5046","已经有使用推送模板信息！"),
    _5047("5047","正在使用该类型信息！"),
    _5048("5048","已经使用了信息作为推送！"),
    _5049("5049","该活动已有详细信息请点击进行修改！");

    public String code;
    public String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
