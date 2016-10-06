package com.migo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.migo.mapper.TbItemDescMapper;
import com.migo.mapper.TbItemMapper;
import com.migo.pojo.*;
import com.migo.service.ItemService;
import com.migo.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;

    public TbItem getItemById(long itemId){
        //添加查询条件
        TbItemExample example=new TbItemExample();
        TbItemExample.Criteria criteria=example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list=itemMapper.selectByExample(example);
        if (list != null&&list.size()>0) {
            TbItem item=list.get(0);
            return item;
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //分页处理
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> list=itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        //返回处理结果
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public MigoResult CreateItem(TbItem item, String desc) {
        //生成商品id
        long itemId= IDUtils.genItemId();
        //补全TbItem属性
        item.setId(itemId);
        //商品状态，1 正常 2下架 3 删除
        item.setStatus((byte) 1);
        //补全创建和更新时间
        Date date=new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品表
        itemMapper.insert(item);
        //商品描述
        TbItemDesc itemDesc=new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //插入商品描述数据
        itemDescMapper.insert(itemDesc);

        return MigoResult.ok();
    }
}
