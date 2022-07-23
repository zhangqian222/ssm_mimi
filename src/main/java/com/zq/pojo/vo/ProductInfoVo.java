package com.zq.pojo.vo;

/**
 * 商品查询条件封装的类
 */
public class ProductInfoVo {
    //商品名称
    private String pname;
    //商品类型
    private String typeid;
    //最低价格
    private Integer minPrice;
    //最高价格
    private Integer maxPrice;
    //页码
    private Integer pageNum = 1;

    public ProductInfoVo() {
    }


    public ProductInfoVo(String pname, String typeid, Integer minPrice, Integer maxPrice) {
        this.pname = pname;
        this.typeid = typeid;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public ProductInfoVo(String pname, String typeid, Integer minPrice, Integer maxPrice, Integer pageNum) {
        this.pname = pname;
        this.typeid = typeid;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.pageNum = pageNum;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", typeid='" + typeid + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", pageNum=" + pageNum +
                '}';
    }
}
