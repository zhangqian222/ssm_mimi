package com.zq.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.pojo.ProductInfo;
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
     * 显示所有商品不分页
     */
//    @RequestMapping("/getAll")
//    public String getAll(Model model) {
//        List<ProductInfo> productLists = productInfoService.getAll();
//        model.addAttribute("list", productLists);
//        return "product";
//    }

    /**
     * 显示第一页的记录
     */

    @RequestMapping("/getAll")
    public String getAllWithPageInfo(Model model) {
        PageInfo pageInfo = productInfoService.getAllWithPageInfo(1, PAGE_SIZE);
        model.addAttribute("info", pageInfo);
        return "product";

    }

    /**
     * AJAX分页处理
     */
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(HttpSession session, int page) {
        PageInfo pageInfo = productInfoService.getAllWithPageInfo(page, PAGE_SIZE);
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
    public String update(int pid, Model model) {
        ProductInfo productInfo = productInfoService.updateById(pid);
        model.addAttribute("prod", productInfo);
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
    public String delete(String pid, Model model) {
        int i = -1;
        //根据异步ajax请求中的pid参数从数据库中删除相关数据
        i = productInfoService.delete(pid);
        //将msg提示弹窗信息放入model中返回浏览器
        if (i > 0) {
            model.addAttribute("msg", "好了，删了");
        } else {
            model.addAttribute("msg", "出问题了，没删掉");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    /**
     * 删除后刷新商品分页界面
     */

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        //获取pageInfo
        PageInfo info = productInfoService.getAllWithPageInfo(1, PAGE_SIZE);
        //放入session作用域
        HttpSession session = request.getSession();
        session.setAttribute("info", info);
        Object msg = request.getAttribute("msg");
        System.out.println(msg);
        return msg;
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deletebatch")
    public String deletebatch(String pids, Model model) {
        int i = 0;
        String[] ids = pids.split(",");
        i = productInfoService.deleteBatch(ids);
        if (i > 0) {
            model.addAttribute("msg", "批量删除成功");
        } else {
            model.addAttribute("msg", "批量删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
}
