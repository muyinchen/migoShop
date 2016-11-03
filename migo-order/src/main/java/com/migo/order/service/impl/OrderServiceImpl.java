package com.migo.order.service.impl;

import com.migo.mapper.TbOrderItemMapper;
import com.migo.mapper.TbOrderMapper;
import com.migo.mapper.TbOrderShippingMapper;
import com.migo.order.jediscomp.JedisClient;
import com.migo.order.pojo.OrderInfo;
import com.migo.order.service.OrderService;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbOrderItem;
import com.migo.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.sun.tools.doclint.Entity.or;

/**
 * Author  知秋
 * Created by kauw on 2016/11/3.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ORDER_JC_KEY}")
    private String REDIS_ORDER_JC_KEY;
    @Value("${ORDER_ID_INIT}")
    private String ORDER_ID_INIT;
    @Value("${REDIS_ORDER_DETAIL_JC_KEY}")
    private String REDIS_ORDER_DETAIL_JC_KEY;
    @Override
    public MigoResult createOrder(OrderInfo orderInfo) {
        // 取订单号
        String id = jedisClient.get(REDIS_ORDER_JC_KEY);
        if (StringUtils.isEmpty(id)) {
            //如果订单号初始key不存在，则设置其初始值
            jedisClient.set(REDIS_ORDER_JC_KEY,ORDER_ID_INIT);
        }
        Long orderId = jedisClient.incr(REDIS_ORDER_JC_KEY);
        //补全字段
        orderInfo.setOrderId(orderId.toString());
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        Date date=new Date();
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        //插入订单表
        orderMapper.insert(orderInfo);

        /*插入订单明细*/
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        //补全字段


        for (TbOrderItem orderItem : orderItems) {
            // 生成订单明细id，使用redis的incr命令生成
            /**
             * `id` varchar(20) COLLATE utf8_bin NOT NULL,
             `item_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '商品id',
             `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单id',
             */
            Long detailId = jedisClient.incr(REDIS_ORDER_DETAIL_JC_KEY);
            orderItem.setId(detailId.toString());
            //订单号
            orderItem.setOrderId(orderId.toString());
          //插入数据，其他数据传入的时候都有，无须再设置
            orderItemMapper.insert(orderItem);

        }

        /**
         * 插入物流表
         */
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        //补全字段
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        //插入数据
        orderShippingMapper.insert(orderShipping);
        //返回订单号，用MigoResult包装

        return MigoResult.ok(orderId);
    }
}
