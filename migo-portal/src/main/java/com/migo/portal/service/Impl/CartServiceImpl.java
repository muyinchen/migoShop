package com.migo.portal.service.Impl;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbItem;
import com.migo.portal.pojo.CartItem;
import com.migo.portal.service.CartService;
import com.migo.portal.service.ItemService;
import com.migo.utils.CookieUtils;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/11/1.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemService itemService;
    @Value("${TT_CART_COOKIE}")
    private String TT_CART_COOKIE;
    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;
    @Override
    public MigoResult addCart(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 1、接收商品id
        // 2、从cookie中购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //3、从商品列表中查询是否存在此商品
        boolean haveFlag=false;
        for (CartItem cartItem : cartItemList) {
            //如果商品存在则数量相加
            if (cartItem.getId()==itemId) {
                cartItem.setNum(cartItem.getNum()+num);
                haveFlag=true;
                break;
            }
        }
        //如果不存在，调用rest服务，根据商品id获得商品数据
        if (!haveFlag) {
            TbItem item = itemService.getItemById(itemId);
            //转换成CartItem
            CartItem cartItem=new CartItem();
            cartItem.setNum(num);
            cartItem.setId(itemId);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if (!StringUtils.isEmpty(item.getImage())){
                String image = item.getImage();
                String[] strings = image.split(",");
                cartItem.setImage(strings[0]);
            }
            //将商品数据添加到购物车商品列表
            cartItemList.add(cartItem);
        }
        //把购物车商品列表写入cookie
        CookieUtils.setCookie(request,response,TT_CART_COOKIE,JsonUtils.objectToJson(cartItemList),COOKIE_EXPIRE,true);
        //返回MigoResult

        return MigoResult.ok();
    }

    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request) {
        try {
            //从cookie中去商品列表
            String cookieValue = CookieUtils.getCookieValue(request, TT_CART_COOKIE, true);
            //把cookieValue转换成Java对象
            List<CartItem> list = JsonUtils.jsonToList(cookieValue, CartItem.class);
            return list==null?new ArrayList<CartItem>():list;
        } catch (Exception e) {
            return new ArrayList<CartItem>();
        }
    }

    @Override
    public MigoResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //根据商品id查询商品
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getId()==itemId){
                //更新商品数量
                cartItem.setNum(num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,TT_CART_COOKIE,JsonUtils.objectToJson(cartItemList),COOKIE_EXPIRE,true);

        return MigoResult.ok();
    }
}
