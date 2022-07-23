package com.zq.service;

import com.github.pagehelper.PageInfo;
import com.zq.pojo.ProductInfo;
import com.zq.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    /**
     * 显示全部商品不分页
     */
    List<ProductInfo> getAll();

    /**
     * 分页显示商品
     * 传入参数：当前页pageNum，每页的条数pages
     */
    PageInfo getAllWithPageInfo(int pageNum, int pageSize);

    /**
     * 多条件商品查询（带分页参数）
     */
    PageInfo<ProductInfo> selectWithConditions(ProductInfoVo productInfoVo,int pageSize);

    /**
     * 添加商品
     *
     * @param info
     */
    int save(ProductInfo info);

    ProductInfo updateById(int id);

    /**
     * 更新商品
     *
     * @return
     */

    int update(ProductInfo productInfo);

    /**
     * 删除单个商品
     *
     * @param pid
     * @return sql语句执行成功的条数
     */
    int delete(String pid);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteBatch(String[] ids);

    /**
     * 多条件查询商品
     */
    List<ProductInfo> selectConditions(ProductInfoVo productInfoVo);
}
