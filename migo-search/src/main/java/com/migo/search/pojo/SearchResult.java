package com.migo.search.pojo;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
public class SearchResult {
    private List<SearchItem> itemList;
    //查询结果总记录数
    private Long recordCount;
    //查询结果总页数
    private int totalPages;
    //前期页
    private int curPage;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
