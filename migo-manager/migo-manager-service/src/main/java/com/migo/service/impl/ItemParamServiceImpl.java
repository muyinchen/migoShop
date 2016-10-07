package com.migo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.migo.mapper.TbItemParamMapper;
import com.migo.mapper.TbItemparamexdMapper;
import com.migo.pojo.EasyUIDataGridResult;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbItemParam;
import com.migo.pojo.TbItemParamExample;
import com.migo.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 模板管理
 * Author  知秋
 * Created by kauw on 2016/10/6.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    TbItemparamexdMapper itemParamexdMapper;

    @Autowired
    TbItemParamMapper tbItemParamMapper;
    @Override
    public EasyUIDataGridResult getItemParamList(int page,int rows) {
        //分页处理
        PageHelper.startPage(page,rows);
        //执行查询

        List list=itemParamexdMapper.selectList();
        //取分页信息
        PageInfo pageInfo=new PageInfo<>(list);
        //返回处理结果
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;


   }

    @Override
    public MigoResult getItemParamByCid(Long cid) {
        //根据cid查询规格参数模板
        TbItemParamExample example=new TbItemParamExample();
        TbItemParamExample.Criteria criteria=example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list=tbItemParamMapper.selectByExampleWithBLOBs(example);
        if (list != null&&list.size()>0) {
            TbItemParam itemParam=list.get(0);
            return MigoResult.ok(itemParam);
        }
        return MigoResult.ok();
    }

    @Override
    public MigoResult insertItemParam(Long cid, String paramData) {
        TbItemParam itemParam=new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        Date date=new Date();
        itemParam.setCreated(date);
        itemParam.setUpdated(date);
        tbItemParamMapper.insert(itemParam);
        return MigoResult.ok();
    }
}
