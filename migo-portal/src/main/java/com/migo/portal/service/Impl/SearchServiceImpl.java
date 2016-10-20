package com.migo.portal.service.Impl;

import com.migo.pojo.MigoResult;
import com.migo.portal.pojo.SearchResult;
import com.migo.portal.service.SearchService;
import com.migo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  知秋
 * Created by kauw on 2016/10/21.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Override
    public SearchResult search(String keyword, int page, int rows) {
        //调用服务查询商品列表
        Map<String,String> param=new HashMap<>();
        param.put("keyword",keyword);
        param.put("page",page+"");
        param.put("rows",rows+"");
        //调用服务
        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
        //转换Java对象
        MigoResult migoResult = MigoResult.formatToPojo(json, SearchResult.class);
        //取返回结果
        SearchResult searchResult= (SearchResult) migoResult.getData();
        return searchResult;
    }
}
