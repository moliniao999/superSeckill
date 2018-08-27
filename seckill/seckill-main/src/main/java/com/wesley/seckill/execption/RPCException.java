package com.wesley.seckill.execption;

/**
 * 参数校验异常
 */
public class RPCException extends RuntimeException {
    private String code;

    public RPCException(String msg) {
        super(msg);
    }

    public RPCException(String code, String msg) {
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
