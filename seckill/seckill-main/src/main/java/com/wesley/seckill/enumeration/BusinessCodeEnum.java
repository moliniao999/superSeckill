package com.wesley.seckill.enumeration;

/**
 * @program: profit-card-mall-order-parent
 * @description: 业务异常code枚举
 * @author: weili
 * @create: 2018-05-22 14:54
 **/
public enum BusinessCodeEnum {


    SUCCESS(0, "成功"),
    SERVER_ERROR(-1, "服务器错误"),
    ACCESS_REFULE(-2, "访问被拒绝"),
    SERVER_BUSY(-3, "服务器忙"),
    REQUEST_PARAM_ERROR(-4, "请求参数错误"),
    REQUEST_JSON_FORMAT_ERR(-5, "请求json格式错误"),
    STOCK_EMPTY(-31, "已售完"),
    STOCK_NOTNULL(-32, "商品不存在"),
    UNIQUE_USER_SECKILL(-33, "一个账户相同商品只能秒杀一次"),
    STOCK_MAXBUY(-34,"超出商品最大购买数量"),
    STOCK_LESS(-35, "库存不足");
    private int code;
    private String msg;



    BusinessCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
