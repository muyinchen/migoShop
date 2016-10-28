package com.migo.sso.service.impl;

import com.migo.mapper.TbUserMapper;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbUser;
import com.migo.pojo.TbUserExample;
import com.migo.sso.jediscomp.JedisClient;
import com.migo.sso.service.LoginService;
import com.migo.utils.CookieUtils;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Author  知秋
 * Created by kauw on 2016/10/27.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_Key;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public MigoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //校验用户名密码是否正确
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUserList = userMapper.selectByExample(example);
        //取用户信息
        if (tbUserList==null||tbUserList.isEmpty()) {
            return MigoResult.build(400,"用户名或密码错误");
        }
        TbUser user=tbUserList.get(0);
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return MigoResult.build(400, "用户名或密码错误");
        }

        //登录成功后
        //生成token
        String token= UUID.randomUUID().toString();

        //把用户信息写入redis
        //key:REDIS_SESSION:{TOKEN}
        //value:user转json
        user.setPassword(null);

        jedisClient.set(REDIS_SESSION_Key+":"+token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        jedisClient.expire(REDIS_SESSION_Key+":"+token,SESSION_EXPIRE);
        //写入cookie
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);

        return MigoResult.ok(token);


    }

    @Override
    public MigoResult getUserByToken(String token) {
        //根据token取用户信息
        String json = jedisClient.get(REDIS_SESSION_Key + ":" + token);
        //判断是否有查到结果
        if (StringUtils.isEmpty(json)){
            return MigoResult.build(400,"用户session已过期");
        }
        //把json转换成Java对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        //更新session过期时间
        jedisClient.expire(REDIS_SESSION_Key + ":" + token,SESSION_EXPIRE);
        return MigoResult.ok(user);
    }

    @Override
    public MigoResult loginoutByToken(String token) {
        jedisClient.del(REDIS_SESSION_Key + ":" + token);
        return MigoResult.ok();
    }
}
