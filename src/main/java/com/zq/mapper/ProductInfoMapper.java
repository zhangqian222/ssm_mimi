package com.zq.mapper;

import com.zq.pojo.ProductInfo;
import com.zq.pojo.ProductInfoExample;
import com.zq.pojo.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Array;
import java.util.List;

public interface ProductInfoMapper {
    int countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Integer pId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);

    ProductInfo selectByPrimaryKey(Integer pId);

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    /**
     * 批量删除
     */
    int deleteBath(String[] pids);

    /**
     * 多条件商品查询
     */
    List<ProductInfo> selectConditions(ProductInfoVo productInfoVo);
}