package com.migo.rest.controller;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbItem;
import com.migo.pojo.TbItemDesc;
import com.migo.pojo.TbItemParamItem;
import com.migo.rest.service.ItemService;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author  知秋
 * Created by kauw on 2016/10/25.
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 查询商品基本信息
     */
    @RequestMapping("/base/{itemId}")
    public MigoResult getItemById(@PathVariable Long itemId){
        try {
            TbItem item=itemService.getItemById(itemId);
            return MigoResult.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    /**
     * 查询商品描述信息
     */
    @RequestMapping("/desc/{itemId}")
    public MigoResult getItemDescById(@PathVariable Long itemId){
        try {
            TbItemDesc itemDesc=itemService.getItemDescById(itemId);
            return MigoResult.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 查询商品规格参数
     */
    @RequestMapping("/param/{itemId}")
    public MigoResult getItemParamById(@PathVariable Long itemId){
        try {
            TbItemParamItem tbItemParamItem=itemService.getItemparmById(itemId);
            return MigoResult.ok(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

}
