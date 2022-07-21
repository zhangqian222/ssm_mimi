package com.zq.service.impl;


import com.zq.mapper.ProductTypeMapper;
import com.zq.pojo.ProductType;
import com.zq.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        List<ProductType> productTypes = productTypeMapper.selectByExample(null);
        return productTypes;
    }
}
