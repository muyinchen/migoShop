package com.migo.service.impl;

import com.migo.mapper.TbItemCatMapper;
import com.migo.pojo.EUTreeNode;
import com.migo.pojo.TbItemCat;
import com.migo.pojo.TbItemCatExample;
import com.migo.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<EUTreeNode> getItemCatList(long parentId) {
        //根据parentId查询分类列表
        TbItemCatExample example=new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list=itemCatMapper.selectByExample(example);
        //转换成EUTreeNode列表
        List<EUTreeNode> resultList=new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            //创建一个节点对象
            EUTreeNode node=new EUTreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到列表中
            resultList.add(node);

        }
        return resultList;
    }
}
