package com.migo.order.controller;

import com.migo.order.pojo.OrderInfo;
import com.migo.order.service.OrderService;
import com.migo.pojo.MigoResult;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/11/3.
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 接收客户端发送的数据，接收json格式的数据使用@RequestBody OrderInfo 来接收数据
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    @ResponseBody
    public MigoResult createOrder(@RequestBody OrderInfo orderInfo){

        try {
            MigoResult migoResult = orderService.createOrder(orderInfo);
            return migoResult;
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
