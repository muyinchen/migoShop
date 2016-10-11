package com.migo.service.impl;

import com.migo.mapper.TbContentMapper;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;
import com.migo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
@Service
public class ContentServiceImpl implements ContentService{
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public MigoResult insertContent(TbContent tbContent) {
        Date date=new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        //插入数据
        contentMapper.insert(tbContent);
        return MigoResult.ok();
    }
}
