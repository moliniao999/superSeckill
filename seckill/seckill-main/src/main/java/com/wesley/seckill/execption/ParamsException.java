package com.wesley.seckill.execption;


import com.wesley.seckill.enumeration.CodeEnum;

/**
 * 参数校验异常
 */
public class ParamsException extends RuntimeException {
    private String code;

    public ParamsException(String msg) {
        super(msg);
        this.code = CodeEnum.REQUEST_PARAM_ERROR.getCode()+"";
    }

    public ParamsException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
