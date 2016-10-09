package com.migo.rest.service.impl;

import com.migo.mapper.TbItemCatMapper;
import com.migo.pojo.TbItemCat;
import com.migo.pojo.TbItemCatExample;
import com.migo.rest.pojo.CatNode;
import com.migo.rest.pojo.ItemCatResult;
import com.migo.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息管理
 * Author  知秋
 * Created by kauw on 2016/10/9.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {


    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {
        List catList=getItemCatList(0L);
        ItemCatResult result=new ItemCatResult();
        result.setData(catList);
        return result;
    }
    /**
     * 查询分类列表
     * 使用了递归方法
     */
    private List<?> getItemCatList(Long parentId){
        //根据parentid查询列表
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list=itemCatMapper.selectByExample(example);
        List resultList=new ArrayList();
        int count=0;
        for (TbItemCat tbItemCat : list) {
            //如果是父节点
            if (tbItemCat.getIsParent()){
                CatNode node=new CatNode();
                node.setUrl("/products/"+tbItemCat.getId()+".html");
                //如果当前节点为一级节点
                if (tbItemCat.getParentId()==0){
                    node.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+
                    tbItemCat.getName()+"</a>");
                } else {
                    node.setName(tbItemCat.getName());
                }
                node.setItems(getItemCatList(tbItemCat.getId()));
                //把node添加到列表
                resultList.add(node);
                count++;
                //第一层只取14条记录
                if (parentId==0&&count>=14){
                    break;
                }
            }else {
                //如果是叶子节点
                String item="/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName();
                resultList.add(item);
            }

        }
        return resultList;
    }
}
