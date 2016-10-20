package com.migo.search.test;


import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
public class SolrTest {
    protected class QESXMLResponseParser extends XMLResponseParser {
        public QESXMLResponseParser() {
            super();
        }

        @Override
        public String getContentType() {
            return "text/xml; charset=UTF-8";
        }
    }
    @Test
    public void TSolrJ() throws IOException, SolrServerException {
        //创建连接,见apache-solr-ref-guide-6.1 p207
        HttpSolrClient client=new HttpSolrClient.Builder("http://192.168.42.132:8080/solr/solrcore").build();

       // client.setParser(new QESXMLResponseParser());
        //创建一个文档对象
        SolrInputDocument document=new SolrInputDocument();


        //添加域
        document.addField("id","dog01");
        document.addField("item_title","商品测试");
        document.addField("item_sell_point","这是卖点");
        //添加到索引库
        client.add(document);
        //提交
        client.commit();

    }

}
