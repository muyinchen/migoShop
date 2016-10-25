package com.migo.rest.service.impl;

import com.migo.mapper.TbItemDescMapper;
import com.migo.mapper.TbItemMapper;
import com.migo.mapper.TbItemParamItemMapper;
import com.migo.mapper.TbItemParamMapper;
import com.migo.pojo.*;
import com.migo.rest.jediscomp.JedisClient;
import com.migo.rest.service.ItemService;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.migo.utils.JsonUtils.jsonToPojo;

/**
 * Author  知秋
 * Created by kauw on 2016/10/24.
 */
@Service
public class ItemServiceimpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private  String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;

    @Override
    public TbItem getItemById(Long itemId) {
        try {
            //查询缓存，如果有缓存，直接返回
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_BASE_INFO_KEY);
            //判断数据的存在
            if (org.apache.commons.lang3.StringUtils.isNoneBlank(json)){
                //把json数据转换成Java对象
                TbItem item= jsonToPojo(json,TbItem.class);
                return item;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询商品基本信息
        TbItem item=itemMapper.selectByPrimaryKey(itemId);
        /**
         * 向redis中添加缓存
         * 原则：不能影响业务的正常逻辑，所以单独处理，使用trycatch
         */
        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_BASE_INFO_KEY,
                    JsonUtils.objectToJson(item));
            //设置key过期时间，因为要设置过期时间，只能在key上设置，所以不能用hash
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_BASE_INFO_KEY,ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        try {
            //查询缓存，如果有缓存，直接返回
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY);
            //判断数据的存在
            if (org.apache.commons.lang3.StringUtils.isNoneBlank(json)){
                //把json数据转换成Java对象
                TbItemDesc itemDesc= jsonToPojo(json,TbItemDesc.class);
                return itemDesc;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc=itemDescMapper.selectByPrimaryKey(itemId);

        /**
         * 向redis中添加缓存
         * 原则：不能影响业务的正常逻辑，所以单独处理，使用trycatch
         */
        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY,
                    JsonUtils.objectToJson(tbItemDesc));
            //设置key过期时间，因为要设置过期时间，只能在key上设置，所以不能用hash
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY,ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    @Override
    public TbItemParamItem getItemparmById(Long itemId) {

        try {
            //查询缓存，如果有缓存，直接返回
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY);
            //判断数据的存在
            if (org.apache.commons.lang3.StringUtils.isNoneBlank(json)){
                //把json数据转换成Java对象
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return tbItemParamItem;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItemList= itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (tbItemParamItemList!=null&&tbItemParamItemList.size()>0){
            TbItemParamItem itemParamItem=tbItemParamItemList.get(0);
            /**
             * 向redis中添加缓存
             * 原则：不能影响业务的正常逻辑，所以单独处理，使用trycatch
             */
            try {
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY,
                        JsonUtils.objectToJson(itemParamItem));
                //设置key过期时间，因为要设置过期时间，只能在key上设置，所以不能用hash
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY,ITEM_EXPIRE_SECOND);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }
}
