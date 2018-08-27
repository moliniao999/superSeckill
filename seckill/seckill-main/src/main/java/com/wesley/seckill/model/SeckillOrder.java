package com.wesley.seckill.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "seckill_order")
@ApiModel
public class SeckillOrder {
    /**
     * 订单id
     */
    @Id
    @Column(name = "orderId")
    @ApiModelProperty(hidden = true)
    private Long orderid;

    /**
     * 秒杀商品ID
     */
    @Column(name = "seckill_id")
    @ApiModelProperty(value = "库存id", example = "1000", required = true)
    private Integer seckillId;


    /**
     * 用户手机号
     */
    @Column(name = "user_phone")
    @ApiModelProperty(value = "手机号", example = "13982135607", required = true)
    private Long userPhone;

    /**
     * 订单状态:-1:无效 0:成功 1:已付款 2:已发货
     */
    @ApiModelProperty(hidden = true)
    private Byte state;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 下单时间
     */
    @Column(name = "apply_time")
    @ApiModelProperty(hidden = true)
    private Date applyTime;

    @Column(name = "user_name")
    @ApiModelProperty(value = "用户名", required = false)
    private String userName;

    /**
     * 购买数量
     */
    @Column(name = "goods_num")
    @ApiModelProperty(value = "购买数量", example = "1", required = true)
    private Integer goodsNum;

    /**
     * 订单金额(分)
     */
    @Column(name = "orderAmount")
    @ApiModelProperty(value = "订单金额", example = "99", required = true)
    private Long orderAmount;


    /**
     * 获取订单id
     *
     * @return orderId - 订单id
     */
    public Long getOrderid() {
        return orderid;
    }

    /**
     * 设置订单id
     *
     * @param orderid 订单id
     */
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    /**
     * 获取秒杀商品ID
     *
     * @return seckill_id - 秒杀商品ID
     */
    public Integer getSeckillId() {
        return seckillId;
    }

    /**
     * 设置秒杀商品ID
     *
     * @param seckillId 秒杀商品ID
     */
    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    /**
     * 获取用户手机号
     *
     * @return user_phone - 用户手机号
     */
    public Long getUserPhone() {
        return userPhone;
    }

    /**
     * 设置用户手机号
     *
     * @param userPhone 用户手机号
     */
    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * 获取订单状态:-1:无效 0:成功 1:已付款 2:已发货
     *
     * @return state - 订单状态:-1:无效 0:成功 1:已付款 2:已发货
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置订单状态:-1:无效 0:成功 1:已付款 2:已发货
     *
     * @param state 订单状态:-1:无效 0:成功 1:已付款 2:已发货
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取下单时间
     *
     * @return apply_time - 下单时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 设置下单时间
     *
     * @param applyTime 下单时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取购买数量
     *
     * @return goods_num - 购买数量
     */
    public Integer getGoodsNum() {
        return goodsNum;
    }

    /**
     * 设置购买数量
     *
     * @param goodsNum 购买数量
     */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
     * 获取订单金额(分)
     *
     * @return orderAmount - 订单金额(分)
     */
    public Long getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置订单金额(分)
     *
     * @param orderAmount 订单金额(分)
     */
    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }


    @Override
    public String toString() {
        return "SeckillOrder{" +
                "orderid=" + orderid +
                ", seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", applyTime=" + applyTime +
                ", userName='" + userName + '\'' +
                ", goodsNum=" + goodsNum +
                ", orderAmount=" + orderAmount +
                '}';
    }
}