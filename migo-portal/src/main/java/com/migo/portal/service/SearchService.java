package com.migo.portal.service;

import com.migo.portal.pojo.SearchResult;

/**
 * Author  知秋
 * Created by kauw on 2016/10/21.
 */
public interface SearchService {
    SearchResult search(String keyword,int page,int rows);
}
