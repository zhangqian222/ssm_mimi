package com.zq.service;

import com.zq.pojo.Admin;

public interface AdminService {
    /**
     * 登陆判断功能
     */
    Admin login(String name, String pwd);
}
