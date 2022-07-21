package com.zq.service.impl;

import com.zq.mapper.AdminMapper;
import com.zq.pojo.Admin;
import com.zq.pojo.AdminExample;
import com.zq.service.AdminService;
import com.zq.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //创建AdminExample对象，用来封装条件
        AdminExample adminExample = new AdminExample();
        //添加用户名条件
        adminExample.createCriteria().andANameEqualTo(name);
        //查询结果
        List<Admin> list = adminMapper.selectByExample(adminExample);
        //校验查询到的结果是否为空
        if (list.size() > 0) {
            //取出用户对象
            Admin admin = list.get(0);
            String aPass = admin.getaPass();
            //输入密码进行加密
//            String md5aPass = MD5Util.getMD5(pwd);
            //进行密码比对
            if (aPass.equals(pwd)) {
                return admin;
            }
        }
        return null;
    }
}
