package com.zq.test01;

import com.zq.mapper.AdminMapper;
import com.zq.mapper.ProductInfoMapper;
import com.zq.pojo.Admin;
import com.zq.pojo.AdminExample;
import com.zq.pojo.ProductInfo;
import com.zq.pojo.vo.ProductInfoVo;
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

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void testConToMysql() {
        System.out.println(adminMapper);
        Admin admin = new Admin(null, "zq", "123");
        int i = adminMapper.insertSelective(admin);
        System.out.println(i == 1 ? "成功" : "失败");
    }

    @Test
    public void electConditionsMethod() {
        System.out.println(adminMapper);
        ProductInfoVo productInfoVo = new ProductInfoVo("小米", "", null, null);
        List<ProductInfo> productInfos = productInfoMapper.selectConditions(productInfoVo);
        for (ProductInfo productInfo : productInfos) {
            System.out.println(productInfo);
        }
    }
}
