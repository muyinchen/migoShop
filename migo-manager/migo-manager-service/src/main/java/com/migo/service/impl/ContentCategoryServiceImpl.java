package com.migo.service.impl;

import com.migo.mapper.TbContentCategoryMapper;
import com.migo.pojo.EUTreeNode;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContentCategory;
import com.migo.pojo.TbContentCategoryExample;
import com.migo.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/10.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list=tbContentCategoryMapper.selectByExample(example);
        //转换成EUTreeNode列表
        List<EUTreeNode> treeNodeList=new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一个EUTreeNode节点
            EUTreeNode node=new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加到列表
            treeNodeList.add(node);
        }
        return treeNodeList;
    }

    @Override
    public MigoResult insertCategory(Long parentId, String name) {
        //创建一个POJO对象
        TbContentCategory contentCategory=new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //1(正常),2(删除)
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        //'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
        contentCategory.setSortOrder(1);
        Date date=new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        //插入数据
        tbContentCategoryMapper.insert(contentCategory);
        //取返回主键
        Long id=contentCategory.getId();
        //判断父节点的isparent属性
        //查询父节点
        TbContentCategory parentNode=tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()){
            parentNode.setIsParent(true);
            //更新父节点
            tbContentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        return MigoResult.ok(id);
    }
}
