package com.migo.search.service;

import com.migo.search.pojo.SearchResult;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
public interface SearchService {
    SearchResult search(String queryString,int page,int rows)throws Exception;
}
