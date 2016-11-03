package com.migo.portal.pojo;

import com.migo.pojo.TbOrder;
import com.migo.pojo.TbOrderItem;
import com.migo.pojo.TbOrderShipping;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/11/3.
 */
public class OrderInfo extends TbOrder{
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
