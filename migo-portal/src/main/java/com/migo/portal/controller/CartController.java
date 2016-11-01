package com.migo.portal.controller;

import com.migo.pojo.MigoResult;
import com.migo.portal.pojo.CartItem;
import com.migo.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/11/1.
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num
                          , HttpServletRequest request, HttpServletResponse response){
        cartService.addCart(itemId,num,request,response);
        return "cartSuccess";

    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, Model model){
        List<CartItem> cartItemList = cartService.getCartItemList(request);
        //把商品列表传递给jsp
        model.addAttribute("cartList",cartItemList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public MigoResult updateCartItemNum(@PathVariable Long itemId,@PathVariable Integer num,
                                        HttpServletRequest request,HttpServletResponse response){
        MigoResult migoResult = cartService.updateCartItem(itemId, num, request, response);
        return migoResult;
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                 HttpServletResponse response, HttpServletRequest request){
        cartService.deleteCartItem(itemId,request,response);
        return "redirect:/cart/cart.html";
    }
}
