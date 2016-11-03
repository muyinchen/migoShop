package com.migo.portal.service.Impl;

import com.migo.pojo.MigoResult;
import com.migo.portal.pojo.OrderInfo;
import com.migo.portal.service.OrderService;
import com.migo.utils.HttpClientUtil;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author  知秋
 * Created by kauw on 2016/11/3.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;
    @Override
    public String createOrder(OrderInfo orderInfo) {
        //orderInfo转换为json数据
        String json = JsonUtils.objectToJson(orderInfo);
        //提交订单数据
        String doPostJson = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, json);
        //去订单id
        MigoResult format = MigoResult.format(doPostJson);
        String orderId = format.getData().toString();

        return orderId;
    }
}
