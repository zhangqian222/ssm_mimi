package com.zq.controller;

import com.zq.pojo.Admin;
import com.zq.service.AdminService;
import com.zq.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request) {
        Admin admin = adminService.login(name, pwd);
        if (admin == null) {
            //登陆失败
            request.setAttribute("errmsg", "额贼，用户名或者密码不正确~~~~~~");
            return "login";
        }
        //登陆成功
        request.setAttribute("admin", admin);
        return "main";

    }
}
