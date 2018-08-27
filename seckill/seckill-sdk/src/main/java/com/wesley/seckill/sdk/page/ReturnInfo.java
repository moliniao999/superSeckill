package com.wesley.seckill.sdk.page;

import com.github.pagehelper.Page;
import com.wesley.seckill.sdk.enumeration.CodeEnum;

import java.util.List;

/**
 * Created by lrf on 17-7-27.
 */
public class ReturnInfo<T> {
    // code 码
    private int code;
    // 返回信息
    private String msg;
    // 返回数据
    private T data;

    private Paginnation paginNation;

    public static <T> ReturnInfo<T> create(int code, String msg, T data, Paginnation paginNation) {
        ReturnInfo<T> returnInfo = new ReturnInfo<T>();
        returnInfo.code=code;
        returnInfo.msg=msg;
        returnInfo.data=data;
        returnInfo.paginNation=paginNation;
        return returnInfo;
    }

    public static <T> ReturnInfo<T> create(CodeEnum codeEnum) {
        return create(codeEnum.getCode(), codeEnum.getMsg());
    }

    public static <T> ReturnInfo<T> create(int code, String msg) {
        return create(code,msg,null,new Paginnation());
    }

    /**
     * 组装返回前端的数据
     * @param data		源数据
     * @return
     */
    public static <T extends List> ReturnInfo<T> createReturnSucces(T data){
        ReturnInfo<T> returnInfo = new ReturnInfo<T>();
        returnInfo.setCode(CodeEnum.SUCCESS.getCode());
        returnInfo.setMsg(CodeEnum.SUCCESS.getMsg());
        returnInfo.setData(data);
        Paginnation paginnation = create(data);
        returnInfo.setPaginNation(paginnation);
        return returnInfo;
    }

    public static <T> ReturnInfo<T> createReturnSuccessOne(T data){
        ReturnInfo returnInfo = create(CodeEnum.SUCCESS);
        returnInfo.setData(data);
        return returnInfo;
    }

    public static Paginnation create(List<?> result){
        Paginnation paginnation = new Paginnation();
        if(result != null && !result.isEmpty()){
        	paginnation.setTotal(new Long(((Page<?>) result).getTotal()).intValue());
        	paginnation.setPageNo(((Page<?>) result).getPageNum());
        	paginnation.setPageSize(((Page<?>) result).getPageSize());
            paginnation.setPages(((Page<?>) result).getPages());
        }
        return paginnation;
    }

    public ReturnInfo() {

    }

    public ReturnInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Paginnation getPaginNation() {
        return paginNation;
    }

    public void setPaginNation(Paginnation paginNation) {
        this.paginNation = paginNation;
    }
}
