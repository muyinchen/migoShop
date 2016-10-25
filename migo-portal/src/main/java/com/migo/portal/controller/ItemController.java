package com.migo.portal.controller;

import com.migo.pojo.TbItem;
import com.migo.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/10/25.
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String getItemInfo(@PathVariable Long itemId, Model model){
        TbItem item = itemService.getItemById(itemId);

        model.addAttribute("item",item);
        return "item";
    }
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDescinfo(@PathVariable Long itemId){
        String itemDesc = itemService.getItemDescById(itemId);
        return itemDesc;
    }

    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemParam(@PathVariable Long itemId){
        String itemParamHtml = itemService.getItemParamById(itemId);
        return itemParamHtml;
    }
}
