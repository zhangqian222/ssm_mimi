package com.zq.listener;

import com.zq.pojo.ProductType;
import com.zq.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 开发监听器，把typelists对象放入application作用域
 */
@WebListener
public class ProductTypeListerner implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手动从spring容器中取出ProductTypeServiceImpl
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService bean = context.getBean(ProductTypeService.class);
        List<ProductType> all = bean.getAll();
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("typeLists", all);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
