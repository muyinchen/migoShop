package com.migo.search.dao;

import com.migo.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws IOException, SolrServerException;
}
