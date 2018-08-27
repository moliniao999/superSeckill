package com.wesley.seckill.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "seckill_stock")
public class SeckillStock {
    /**
     * 商品库存ID
     */
    @Id
    @Column(name = "seckill_id")
    private Long seckillId;

    /**
     * 商品编号
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    private String sku;

    /**
     * 初始库存数量
     */
    @Column(name = "stock_init")
    private Integer stockInit;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 秒杀开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 上下架状态:1-上架 2-下架
     */
    private Byte state;

    /**
     * 商品最大购买数量
     */
    @Column(name = "max_buy")
    private Integer maxBuy;

    /**
     * 更新版本号
     */
    @Column(name = "version")
    private Integer version;

    /**
     * 获取商品库存ID
     *
     * @return seckill_id - 商品库存ID
     */
    public Long getSeckillId() {
        return seckillId;
    }

    /**
     * 设置商品库存ID
     *
     * @param seckillId 商品库存ID
     */
    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    /**
     * 获取商品编号
     *
     * @return goods_id - 商品编号
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品编号
     *
     * @param goodsId 商品编号
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品名称
     *
     * @return goods_name - 商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * 获取初始库存数量
     *
     * @return stock_init - 初始库存数量
     */
    public Integer getStockInit() {
        return stockInit;
    }

    /**
     * 设置初始库存数量
     *
     * @param stockInit 初始库存数量
     */
    public void setStockInit(Integer stockInit) {
        this.stockInit = stockInit;
    }

    /**
     * 获取库存数量
     *
     * @return stock - 库存数量
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存数量
     *
     * @param stock 库存数量
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取秒杀开始时间
     *
     * @return start_time - 秒杀开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置秒杀开始时间
     *
     * @param startTime 秒杀开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取秒杀结束时间
     *
     * @return end_time - 秒杀结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置秒杀结束时间
     *
     * @param endTime 秒杀结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 获取上下架状态:1-上架 2-下架
     *
     * @return state - 上下架状态:1-上架 2-下架
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置上下架状态:1-上架 2-下架
     *
     * @param state 上下架状态:1-上架 2-下架
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getMaxBuy() {
        return maxBuy;
    }

    public void setMaxBuy(Integer maxBuy) {
        this.maxBuy = maxBuy;
    }


    @Override
    public String toString() {
        return "SeckillStock{" +
                "seckillId=" + seckillId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", sku='" + sku + '\'' +
                ", stockInit=" + stockInit +
                ", stock=" + stock +
                ", state=" + state +
                ", maxBuy=" + maxBuy +
                ", version=" + version +
                '}';
    }
}