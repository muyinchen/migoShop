package com.migo.search.service.impl;

import com.migo.pojo.MigoResult;
import com.migo.search.mapper.ItemMapper;
import com.migo.search.pojo.SearchItem;
import com.migo.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public MigoResult importItems() throws Exception {
        //查询数据库获得商品列表
        List<SearchItem> itemList=itemMapper.getItemList();
        //遍历加载域
        for (SearchItem item : itemList) {
            //创建文档对象
            SolrInputDocument document=new SolrInputDocument();
            //添加域
            document.addField("id",item.getId());
            document.addField("item_title",item.getTitle());
            document.addField("item_sell_point",item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_desc());
            //写入索引库
            solrClient.add(document);
        }
        solrClient.commit();
        return MigoResult.ok();
    }
}
