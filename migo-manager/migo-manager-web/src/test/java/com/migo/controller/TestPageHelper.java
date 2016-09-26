package com.migo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.migo.mapper.TbItemMapper;
import com.migo.pojo.TbItem;
import com.migo.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
public class TestPageHelper {
    @Test
    public void dummy() {
        ApplicationContext applicationContext=
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从Spring容器中获得Mapper的代理对象
        TbItemMapper mapper=applicationContext.getBean(TbItemMapper.class);
        //执行查询，并分页
        TbItemExample example=new TbItemExample();
        //分页处理
        PageHelper.startPage(2,10);
        List<TbItem> list=mapper.selectByExample(example);
        for (TbItem tbItem : list) {
            System.out.println(tbItem.getTitle());
        }
        //取分页信息
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        long total=pageInfo.getTotal();
        System.out.println("共有商品信息"+total);
    }
}
