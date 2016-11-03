package com.migo.portal.controller;

import com.migo.pojo.TbUser;
import com.migo.portal.pojo.CartItem;
import com.migo.portal.pojo.OrderInfo;
import com.migo.portal.service.CartService;
import com.migo.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/11/3.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(Model model, HttpServletRequest request){
        //取购物车商品列表
        List<CartItem> list = cartService.getCartItemList(request);
        model.addAttribute("cartList",list);
        return "order-cart";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,
                              Model model, HttpServletRequest request){
        //取用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //补全orderinfo属性
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());

        //调用服务
        String orderid = orderService.createOrder(orderInfo);

        //订单号给页面
        model.addAttribute("orderId",orderid);
        model.addAttribute("payment",orderInfo.getPayment());
        //使用joda这个工具
        DateTime dateTime=new DateTime();
        dateTime  = dateTime.plusDays(3);
        model.addAttribute("date",dateTime.toString("yyyy-MM-dd"));

        return "success";


    }

}
