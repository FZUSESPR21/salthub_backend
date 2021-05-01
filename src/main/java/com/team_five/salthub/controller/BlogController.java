package com.team_five.salthub.controller;


import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping
    public ResponseMessage releaseBlog(@RequestBody Blog blog, @RequestParam("attachments") MultipartFile[] attachments){
        String name = " ";//发布者的用户名
        blogService.validityCheck(blog);//检查博客合法性
        //处理一下文件
        blogService.insert(blog);//将博客存储到数据库中
        return ResponseMessage.success();
    }

    @PostMapping("/module")
    public ResponseMessage searchBlogByModuleId(@RequestParam("moduleId") int moduleId) {
        blogService.moduleIdValidityCheck(Long.valueOf(moduleId));

        List<Blog> blogList = blogService.searchBlogByModuleId(Long.valueOf(moduleId));
        return ResponseMessage.success(blogList);
    }
}

