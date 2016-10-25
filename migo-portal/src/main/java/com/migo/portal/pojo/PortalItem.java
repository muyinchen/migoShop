package com.migo.portal.pojo;

import com.migo.pojo.TbItem;

/**
 * Author  知秋
 * Created by kauw on 2016/10/25.
 */
public class PortalItem extends TbItem{
    //利用了多态 前台页面属性 "${item.images[0]}"
    public String[] getImages(){
        String imags=this.getImage();
        if (imags != null&&!imags.equals("")) {
            String[] ims=imags.split(",");
            return ims;
        }
    return null;
    }
}
