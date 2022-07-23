package com.zq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.mapper.ProductInfoMapper;
import com.zq.pojo.ProductInfo;
import com.zq.pojo.ProductInfoExample;
import com.zq.pojo.vo.ProductInfoVo;
import com.zq.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {


    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        List<ProductInfo> productInfos
                = productInfoMapper.selectByExample(null);
        return productInfos;
    }

    @Override
    public PageInfo getAllWithPageInfo(int pageNum, int pageSize) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        ProductInfoExample example = new ProductInfoExample();
        //设置主键降序排序
        example.setOrderByClause("p_id desc");
        //切记：在查询之前使用分页插件
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        //将从数据库中查到的集合封装到pageInfo中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfos);
        return pageInfo;
    }

    @Override
    public PageInfo<ProductInfo> selectWithConditions(ProductInfoVo productInfoVo, int pageSize) {
        PageHelper.startPage(productInfoVo.getPageNum(), pageSize);
        List<ProductInfo> productInfos = productInfoMapper.selectConditions(productInfoVo);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfos);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        int i = productInfoMapper.insert(info);
        return i;
    }

    @Override
    public ProductInfo updateById(int id) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(id);
        return productInfo;
    }

    @Override
    public int update(ProductInfo productInfo) {
        int i = productInfoMapper.updateByPrimaryKey(productInfo);
        return i;
    }

    @Override
    public int delete(String pid) {
        int i = Integer.parseInt(pid);
        int i1 = productInfoMapper.deleteByPrimaryKey(i);
        return i1;
    }

    @Override
    public int deleteBatch(String[] ids) {
        int i = productInfoMapper.deleteBath(ids);
        return i;
    }

    @Override
    public List<ProductInfo> selectConditions(ProductInfoVo productInfoVo) {
        List<ProductInfo> productInfos = productInfoMapper.selectConditions(productInfoVo);
        return productInfos;
    }
}
