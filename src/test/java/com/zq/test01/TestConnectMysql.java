package com.zq.test01;

import com.zq.mapper.AdminMapper;
import com.zq.pojo.Admin;
import com.zq.pojo.AdminExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_Service.xml", "classpath:applicationContext_dao.xml"})
public class TestConnectMysql {
    @Autowired
    AdminMapper adminMapper;

    @Test
    public void testConToMysql() {
        System.out.println(adminMapper);
        Admin admin = new Admin(null, "zq", "123");
        int i = adminMapper.insertSelective(admin);
        System.out.println(i == 1 ? "成功" : "失败");
    }
}
