package com.wesley.seckill.execption;


import com.wesley.seckill.enumeration.BusinessCodeEnum;

/**
 * 订单服务业务异常类
 *
 * @author weili
 * @date 18/2/7 下午12:07
 */
public class ServiceException extends RuntimeException {
    private Integer code;
    private String msg;
    private String errorDesciption;

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public ServiceException(Integer code, String msg, String errorDesciption) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.errorDesciption = errorDesciption;
    }

    public ServiceException(BusinessCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorDesciption() {
        return errorDesciption;
    }

    public void setErrorDesciption(String errorDesciption) {
        this.errorDesciption = errorDesciption;
    }
}
