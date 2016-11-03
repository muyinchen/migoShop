package com.migo.order.service;

import com.migo.order.pojo.OrderInfo;
import com.migo.pojo.MigoResult;

/**
 * Author  知秋
 * Created by kauw on 2016/11/3.
 */
public interface OrderService {
    MigoResult createOrder(OrderInfo orderInfo);
}
