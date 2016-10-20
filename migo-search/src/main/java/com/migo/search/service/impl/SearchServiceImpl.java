package com.migo.search.service.impl;

import com.migo.search.dao.SearchDao;
import com.migo.search.pojo.SearchResult;
import com.migo.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        //创建查询条件
        SolrQuery query=new SolrQuery();
        query.setQuery(queryString);
        //设置分页
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_title");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
        query.setHighlightSimplePost("</font>");
        //执行查询
        SearchResult searchResult=searchDao.search(query);
        // 计算总页数
        Long recordCount=searchResult.getRecordCount();
        int pageCount= (int) (recordCount/rows);
        if (recordCount%rows>0){
            pageCount++;
        }
        searchResult.setTotalPages(pageCount);
        searchResult.setCurPage(page);
        return searchResult;


    }
}
