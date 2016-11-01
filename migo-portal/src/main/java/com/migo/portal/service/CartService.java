package com.migo.portal.service;

import com.migo.pojo.MigoResult;
import com.migo.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/11/1.
 */
public interface CartService {
    MigoResult addCart(long itemId, Integer num,
                       HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCartItemList(HttpServletRequest request);
}
