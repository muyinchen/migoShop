package com.migo.rest.service.impl;

import com.migo.mapper.TbContentMapper;
import com.migo.pojo.TbContent;
import com.migo.pojo.TbContentExample;
import com.migo.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容查询服务
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
@Service
public class ContentServiceImpl implements ContentService{
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public List<TbContent> getContentList(Long cid) {
        //根据cid查询内容
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list=contentMapper.selectByExampleWithBLOBs(example);
        return list;
    }
}
