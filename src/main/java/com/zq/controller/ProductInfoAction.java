package com.zq.controller;


import com.github.pagehelper.PageInfo;
import com.zq.pojo.ProductInfo;
import com.zq.pojo.vo.ProductInfoVo;
import com.zq.service.ProductInfoService;
import com.zq.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    /**
     * 每页显示的记录数
     */
    public static final int PAGE_SIZE = 4;

    @Autowired
    ProductInfoService productInfoService;

    String uuidFileName = "";


    /**
     * 显示第一页的记录
     */

    @RequestMapping("/getAll")
    public String getAllWithPageInfo(HttpServletRequest request) {
        PageInfo pageInfo = null;
        //获取session
        HttpSession session = request.getSession();
        //从session中取出pageinfovo
        ProductInfoVo productInfoVo = (ProductInfoVo) session.getAttribute("productInfoVoAfterUpdate");
        //如果是从更新商品后的请求中获取到的查看商品列表请求 roductInfoVo != 1
        if (productInfoVo != null) {
            pageInfo = productInfoService.selectWithConditions(productInfoVo, PAGE_SIZE);
        }
        //如果是首次访问商品列表   productInfoVo = 1
        else {
            pageInfo = productInfoService.getAllWithPageInfo(1, PAGE_SIZE);
        }
        session.removeAttribute("productInfoVoAfterUpdate");
        request.setAttribute("info", pageInfo);
        return "product";
    }

    /**
     * AJAX分页处理
     */
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(HttpSession session, ProductInfoVo productInfoVo) {
        PageInfo<ProductInfo> pageInfo = productInfoService.selectWithConditions(productInfoVo, PAGE_SIZE);
        session.setAttribute("info", pageInfo);
    }

    /**
     * 异步ajax处理文件上传
     */
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) throws IOException {
        uuidFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //图片存储路径
        String realPath = request.getServletContext().getRealPath("/image_big");
        //转存
        pimage.transferTo(new File(realPath + File.separator + uuidFileName));
        JSONObject jsonObject = new JSONObject();
        JSONObject imgurl = jsonObject.put("imgurl", uuidFileName);
        return imgurl.toString();
    }

    /**
     * 增加商品
     */
    @RequestMapping("/save")
    public String save(ProductInfo info, Model model) {
        info.setpImage(uuidFileName);
        info.setpDate(new Date());
        int i = productInfoService.save(info);
        if (i > 0) {
            model.addAttribute("msg", "商品添加成功了哈哈哈哈哈哈哈哈哈hahhhhhhhh");
        } else {
            model.addAttribute("msg", "增加失败了，caoccccccccccccccccc");
        }
        uuidFileName = "null";
        return "forward:/prod/getAll.action";
    }

    /**
     * 更新商品
     */
    @RequestMapping("/one")
    public String update(int pid, HttpSession session, ProductInfoVo productInfoVo) {
        System.out.println("11");
        ProductInfo productInfo = productInfoService.updateById(pid);
        session.setAttribute("prod", productInfo);
        session.setAttribute("productInfoVoAfterUpdate", productInfoVo);
        return "update";
    }

    /**
     * 更新提交页面
     */
    @RequestMapping("/update")
    public String update(ProductInfo productInfo, Model model) {
        int i = 0;
        if (uuidFileName == "") {
            i = productInfoService.update(productInfo);
        } else {
            productInfo.setpImage(uuidFileName);
            i = productInfoService.update(productInfo);
        }
        if (i > 0) {
            model.addAttribute("msg", "更新成功");
        } else if (i == -1) {
            model.addAttribute("msg", "更新失败，tmd");
        }
        uuidFileName = "";
        return "forward:/prod/getAll.action";
    }

    /**
     * 单个商品的删除功能
     */
    @RequestMapping("/delete")
    public String delete(String pid, ProductInfoVo productInfoVo, HttpServletRequest request) {
        int i = -1;
        //根据异步ajax请求中的pid参数从数据库中删除相关数据
        i = productInfoService.delete(pid);
        //将msg提示弹窗信息放入model中返回浏览器
        if (i > 0) {
            request.setAttribute("msg", "好了，删了");
            request.setAttribute("productInfoVoAfterDelete", productInfoVo);
        } else {
            request.setAttribute("msg", "出问题了，没删掉");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    /**
     * 删除后刷新商品分页界面
     */

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        PageInfo info = null;
        //从request中取出productInfo
        ProductInfoVo productInfoVo = (ProductInfoVo) request.getAttribute("productInfoVoAfterDelete");
        //获取pageInfo
        System.out.println("productInfoVo:" + productInfoVo);
        if (productInfoVo != null) {
            info = productInfoService.selectWithConditions(productInfoVo, PAGE_SIZE);
        } else {
            info = productInfoService.getAllWithPageInfo(1, PAGE_SIZE);
        }
        //放入session作用域
        HttpSession session = request.getSession();
        session.setAttribute("info", info);
        //清除条件
        request.removeAttribute("productInfoVoAfterDelete");
        Object msg = request.getAttribute("msg");
        return msg;
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deletebatch")
    public String deletebatch(String pids, HttpServletRequest request) {
        int i = 0;
        String[] ids = pids.split(",");
        i = productInfoService.deleteBatch(ids);
        if (i > 0) {
            request.setAttribute("msg", "批量删除成功");
        } else {
            request.setAttribute("msg", "批量删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    /**
     * 多条件查询功能
     */
    @RequestMapping("/selectConditions")
    @ResponseBody()
    public void selectConditions(ProductInfoVo productInfoVo, HttpServletRequest request) {
        //多条件查询商品
        List<ProductInfo> productInfos = productInfoService.selectConditions(productInfoVo);
        //获取session
        HttpSession session = request.getSession();
        session.setAttribute("productInfos", productInfos);
    }
}
