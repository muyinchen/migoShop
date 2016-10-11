package com.migo.portal.service.Impl;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;
import com.migo.portal.pojo.ADNode;
import com.migo.portal.service.ContentService;
import com.migo.utils.HttpClientUtil;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_BASE_URL}")
    private String REST_CONTENT_BASE_URL;
    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;
    @Override
    public String getAd1List() {
        //调用服务获取数据
       String json= HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_BASE_URL+REST_CONTENT_AD1_CID);
        //json转Java对象
        MigoResult migoResult=MigoResult.formatToList(json, TbContent.class);
        //取data属性里的内容
        List<TbContent> contentList= (List<TbContent>) migoResult.getData();
        //把内容列表转换为ADNode列表
        List<ADNode> resultList=new ArrayList<>();
        for (TbContent tbContent : contentList) {
            ADNode node=new ADNode();
            node.setHeight(240);
            node.setWidth(670);
            node.setSrc(tbContent.getPic());

            node.setHeightB(240);
            node.setWidthB(550);
            node.setSrcB(tbContent.getPic2());

            node.setAlt(tbContent.getSubTitle());
            node.setHref(tbContent.getUrl());

            resultList.add(node);

        }
        //将resultList转化为json数据
        String resultJson= JsonUtils.objectToJson(resultList);
        return resultJson;
    }
}
