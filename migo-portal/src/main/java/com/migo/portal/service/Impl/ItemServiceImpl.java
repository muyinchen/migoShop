package com.migo.portal.service.Impl;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbItem;
import com.migo.pojo.TbItemDesc;
import com.migo.pojo.TbItemParamItem;
import com.migo.portal.pojo.PortalItem;
import com.migo.portal.service.ItemService;
import com.migo.utils.HttpClientUtil;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author  知秋
 * Created by kauw on 2016/10/25.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;
    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;
    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;


    @Override
    public TbItem getItemById(Long itemId) {
        //根据商品id查询商品基本信息
        String json= HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_BASE_URL+itemId);
        //转换Java对象
        MigoResult migoResult=MigoResult.formatToPojo(json,PortalItem.class);
        //取商品对象
        TbItem item = (TbItem) migoResult.getData();
        return item;
    }

    @Override
    public String getItemDescById(Long itemId) {
        //根据商品id查询商品基本信息
        String json= HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_DESC_URL+itemId);
        //转换Java对象
        MigoResult migoResult=MigoResult.formatToPojo(json,TbItemDesc.class);
        //取商品描述
        TbItemDesc tbItemDesc= (TbItemDesc) migoResult.getData();
        String itemDesc = tbItemDesc.getItemDesc();
        return itemDesc;
    }

    @Override
    public String getItemParamById(Long itemId) {
        //根据商品id查询商品基本信息
        String json= HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_PARAM_URL+itemId);
        //转换Java对象
        MigoResult migoResult=MigoResult.formatToPojo(json, TbItemParamItem.class);
        //取规格参数
        TbItemParamItem tbItemParamItem= (TbItemParamItem) migoResult.getData();
        String paramJson = tbItemParamItem.getParamData();
        // 把规格参数的json数据转换成java对象
        // 转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);
        // 遍历list生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("		</tr>\n");
            // 取规格项
            List<Map> mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");


        return sb.toString();
    }
}
