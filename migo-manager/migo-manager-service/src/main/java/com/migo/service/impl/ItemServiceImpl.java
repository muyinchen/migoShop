package com.migo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.migo.mapper.TbItemDescMapper;
import com.migo.mapper.TbItemMapper;
import com.migo.mapper.TbItemParamItemMapper;
import com.migo.pojo.*;
import com.migo.service.ItemService;
import com.migo.utils.IDUtils;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

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
    public MigoResult CreateItem(TbItem item, String desc,String itemParam) {
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
        //添加商品规格参数
        TbItemParamItem tbItemParamItem=new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParam);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        //插入数据
        tbItemParamItemMapper.insert(tbItemParamItem);


        return MigoResult.ok();
    }

    @Override
    public String getItemParamHtml(Long itemId) {
        //根据商品id查询规格参数
        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria=example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> list=tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list == null||list.isEmpty()) {
            return "";
        }
        //取规格参数
        TbItemParamItem itemParamItem=list.get(0);
        //取json数据
        String paramData=itemParamItem.getParamData();
        //转换Java对象
        List<Map> mapList= JsonUtils.jsonToList(paramData,Map.class);
        //遍历list生成html
        StringBuffer sb=new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"2\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
            sb.append("		</tr>\n");
            //取规格项
            List<Map>mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
                sb.append("			<td>"+map2.get("v")+"</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");
        return sb.toString();
    }
}
