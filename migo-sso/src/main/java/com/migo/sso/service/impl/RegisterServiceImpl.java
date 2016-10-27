package com.migo.sso.service.impl;

import com.migo.mapper.TbUserMapper;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbUser;
import com.migo.pojo.TbUserExample;
import com.migo.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 登录注册
 * Author  知秋
 * Created by kauw on 2016/10/27.
 */
@Service
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public MigoResult checkData(String param, int type) {
        //根据数据类型检查数据
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1、2、3分别代表username、phone、email
       /* 1==type?criteria.andUsernameEqualTo(param):2==type?criteria.andPhoneEqualTo(param)
                :3==type?criteria.andEmailEqualTo(param):criteria.;*/
        if (1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if ( 2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if ( 3 == type ) {
            criteria.andEmailEqualTo(param);
        }

        //执行查询
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list == null||list.isEmpty()) {
            return MigoResult.ok(true);
        }
        return MigoResult.ok(false);
    }

    @Override
    public MigoResult register(TbUser user) {
        //校验数据
        //校验用户名密码不能为空
        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())) {
            return MigoResult.build(400,"用户名或者密码不能为空");
        }
        //校验数据是否重复
        //校验用户名
        MigoResult migoResult = checkData(user.getUsername(), 1);
        if (!(boolean) migoResult.getData()) {
            return MigoResult.build(400,"用户名重复");
        }
        //校验手机号
        if (user.getPhone()!=null){
            migoResult=checkData(user.getPhone(),2);
            if (!(Boolean) migoResult.getData()) {
                return MigoResult.build(400,"手机号重复");

            }
        }
        //email
        if (user.getEmail()!=null){
            migoResult=checkData(user.getEmail(),3);

                if (!(Boolean) migoResult.getData()){
                    return MigoResult.build(400,"邮箱重复");
                }

        }
        //验证完之后符合要求插入数据
        Date date=new Date();
        user.setCreated(date);
        user.setUpdated(date);
        tbUserMapper.insert(user);
        return MigoResult.ok();

    }
}
