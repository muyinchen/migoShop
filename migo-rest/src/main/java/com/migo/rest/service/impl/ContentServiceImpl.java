package com.migo.rest.service.impl;

import com.migo.mapper.TbContentMapper;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;
import com.migo.pojo.TbContentExample;
import com.migo.rest.jediscomp.JedisClient;
import com.migo.rest.service.ContentService;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.tagext.TryCatchFinally;
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
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;
    @Override
    public List<TbContent> getContentList(Long cid) {
        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
        //根据cid查询内容
        try {
            String json=jedisClient.hget(REDIS_CONTENT_KEY,cid+"");
            if (!StringUtils.isEmpty(json)){
                //json转换为list
                List<TbContent> list=JsonUtils.jsonToList(json,TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list=contentMapper.selectByExampleWithBLOBs(example);
        //返回结果前，向缓存中添加数据
        //为了规范key可以使用hash
        //定义一个保存内容的key，hash中每个项就是cid
        //value是list，需要把list转换成json数据。

        try {
            jedisClient.hset(REDIS_CONTENT_KEY,cid+"", JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();

        }
        return list;
    }

    /**
     * 做redis同步，其实就是删除下里面相应的条目就成
     * @param cid
     * @return
     */

    @Override
    public MigoResult synContent(Long cid) {
        jedisClient.hdel(REDIS_CONTENT_KEY,cid+"");
        return MigoResult.ok();
    }
}
