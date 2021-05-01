package com.team_five.salthub.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
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
    public ResponseMessage releaseBlog(@RequestBody Blog blog, @RequestParam("attachments") MultipartFile[] attachments) {
        String name = " ";//发布者的用户名
        blogService.validityCheck(blog);//检查博客合法性
        //处理一下文件
        blogService.insert(blog);//将博客存储到数据库中
        return ResponseMessage.success();
    }

    @PostMapping("/module")
    public ResponseMessage searchBlogByModuleId(@RequestParam("current") int current, @RequestParam("moduleId") int moduleId) {
        blogService.moduleIdValidityCheck(Long.valueOf(moduleId));

        Page<Blog> blogList = blogService.searchBlogByModuleId(Long.valueOf(moduleId), Long.valueOf(current));
        return ResponseMessage.success(blogList);

    }

    @PostMapping("/tag")
    public ResponseMessage searchBlogByTagId(@RequestParam("current") int current, @RequestParam("tagId") int tagId) {
        blogService.moduleIdValidityCheck(Long.valueOf(tagId));

        blogService.searchBlogByTagId(Long.valueOf(tagId), Long.valueOf(current));
        return ResponseMessage.success();
    }

    @PostMapping("/account")
    public ResponseMessage searchBlogByAccount(@RequestParam("current") int current, @RequestParam("account") String account) {
        blogService.accountValidityCheck(account);

        Page<Blog> blogList = blogService.searchBlogByAccount(account, Long.valueOf(current));
        return ResponseMessage.success(blogList);
    }

    @PostMapping
    public ResponseMessage searchBlogByBolgId(@RequestParam("current") int current, @RequestParam("blogId") int blogId) {
        Page<Blog> blogList = blogService.searchBlogByModuleId(Long.valueOf(blogId), Long.valueOf(current));
        return ResponseMessage.success(blogList);

    }

    @PostMapping
    public ResponseMessage deleteBlogByBlogId(@RequestParam("blogId") int blogId) {
        blogService.deleteBlogByBlogId(Long.valueOf(blogId));
        return ResponseMessage.success();
    }
}

