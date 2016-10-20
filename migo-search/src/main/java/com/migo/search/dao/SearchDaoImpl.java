package com.migo.search.dao;

import com.migo.search.pojo.SearchItem;
import com.migo.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
@Repository
public class SearchDaoImpl implements SearchDao{
    @Autowired
    private SolrClient solrClient;
    @Override
    public SearchResult search(SolrQuery query) throws IOException, SolrServerException {
        //执行查询
        QueryResponse response=solrClient.query(query);
        //取查询结果列表
        SolrDocumentList solrDocuments=response.getResults();
        List<SearchItem> itemList=new ArrayList<>();
        for (SolrDocument solrDocument : solrDocuments) {
            //创建一个SearchItem对象
            SearchItem item=new SearchItem();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //高亮显示
            Map<String,Map<String,List<String>>> highlighting=response.getHighlighting();
            List<String> list=highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle="";
            if (list!=null&&list.size()>0){
                //取高亮结果
                itemTitle=list.get(0);
            }else {
                itemTitle= (String) solrDocument.get("item_title");
            }
            item.setTitle(itemTitle);
            //添加到列表
            itemList.add(item);
        }
        SearchResult result=new SearchResult();
        result.setItemList(itemList);
        // 查询结果总数量
        result.setRecordCount(solrDocuments.getNumFound());
        return result;
    }
}
