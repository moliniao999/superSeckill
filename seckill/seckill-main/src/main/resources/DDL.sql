CREATE TABLE `seckill_order` (
  `orderId` bigint(20) NOT NULL COMMENT '订单id',
  `seckill_id` int(11) NOT NULL COMMENT '秒杀商品ID',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '订单状态:-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `apply_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `user_name` varchar(32) DEFAULT NULL,
  `goods_num` int(11) NOT NULL COMMENT '购买数量',
  `orderAmount` bigint(20) NOT NULL COMMENT '订单金额(分)',
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `unique_idx_so_phone_seckillId` (`user_phone`,`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表'

CREATE TABLE `seckill_stock` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `goods_id` int(11) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(120) NOT NULL COMMENT '商品名称',
  `sku` varchar(64) NOT NULL,
  `stock_init` int(11) NOT NULL DEFAULT '0' COMMENT '初始库存数量',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` tinyint(4) DEFAULT '2' COMMENT '上下架状态:1-上架 2-下架',
  `version` int(11) NOT NULL DEFAULT '1',
  `max_buy` int(11) NOT NULL DEFAULT '1' COMMENT '账户最大购买数量',
  PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'

