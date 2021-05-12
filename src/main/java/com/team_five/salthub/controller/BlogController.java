package com.team_five.salthub.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.config.AddressMapping;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Attachment;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.constant.BlogStateEnum;
import com.team_five.salthub.service.BlogService;
import com.team_five.salthub.userBasedCollaborativeFiltering.BlogPage;
import com.team_five.salthub.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private static final String BLOG_LIST_PREFIX = "BLOG_LIST_";
    private static final long EXPIRE_TIME = 60L * 60L * 1000L;
    public static final int PAGE_SIZE = 50;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "发布博客")
    @PostMapping
    public ResponseMessage releaseBlog(@RequestBody Blog blog,
                                       @RequestParam(value = "attachments",required = false) MultipartFile[] attachments) throws IOException {
        //, @RequestParam("attachments") MultipartFile[] attachments
        blogService.validityCheck(blog);//检查博客合法性
        String name = StpUtil.getLoginIdAsString();//发布者的用户名
        blog.setAuthor(name);
        blog.setLikeNumber(0L);
        blog.setCollectionNumber(0L);
        blog.setState(BlogStateEnum.NORMAL.getId().intValue());
        Date releaseTime = new Date();
        blog.setReleaseTime(releaseTime);

        Long id = blogService.insert(blog);//将博客存储到数据库中
        //处理一下文件
        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
                File file = new File((AddressMapping.FILE_ATTACHMENT_SAVE_ROOT + UUID.randomUUID() + attachment.getOriginalFilename()).replace("-", ""));
                try {
                    attachment.transferTo(file);
                } catch (IOException e) {
                    if (file.exists()) {
                        file.delete();
                    }
                    e.printStackTrace();
                    throw new BaseException(ExceptionInfo.UPLOAD_ATTACHMENT);
                }
                blogService.validityCheckFile(file);
                Attachment attachment1 = new Attachment();
                attachment1.setBlogId(id);
                attachment1.setName(file.getName());
                blogService.insertAttachment(attachment1);
            }
        }
        return ResponseMessage.success(id);
    }

    @ApiOperation(value = "根据板块id查询博客")
    @PostMapping("/module")
    public ResponseMessage searchBlogByModuleId(@RequestParam("current") int current, @RequestParam("moduleId") int moduleId) {
        blogService.moduleIdValidityCheck((long) moduleId);

        Page<Blog> blogList = blogService.searchBlogByModuleId((long) moduleId, (long) current);
        return ResponseMessage.success(blogList);

    }

    @ApiOperation(value = "根据标签id查询博客")
    @PostMapping("/tag")
    public ResponseMessage searchBlogByTagId(@RequestParam("current") int current, @RequestParam("tagId") int tagId) {
        blogService.tagIdValidityCheck((long) tagId);

        Page<Blog> blogPage = blogService.searchBlogByTagId((long) tagId, (long) current);
        return ResponseMessage.success(blogPage);
    }

    @ApiOperation(value = "根据用户名查询博客")
    @PostMapping("/account")
    public ResponseMessage searchBlogByAccount(@RequestParam("current") int current, @RequestParam("account") String account) {
        blogService.accountValidityCheck(account);

        Page<Blog> blogList = blogService.searchBlogByAccount(account, (long) current);
        return ResponseMessage.success(blogList);
    }

    @ApiOperation(value = "通过博客id查询博客")
    @GetMapping
    public ResponseMessage searchBlogByBolgId(@RequestParam("blogId") int blogId) {
        Blog blog = blogService.searchBlogByBlogId((long) blogId);
        return ResponseMessage.success(blog);

    }
    @ApiOperation(value = "通过title模糊查询博客")
    @GetMapping("title")
    public ResponseMessage selectBlogByTitle(@RequestParam("title") String title,@RequestParam("current") Long current) {

        Page<Blog> blogPage = blogService.selectBlogByTitle(title,current);
        return ResponseMessage.success(blogPage);

    }
    @ApiOperation(value = "根据博客id删除博客")
    @DeleteMapping
    public ResponseMessage deleteBlogByBlogId(@RequestParam("blogId") int blogId) {
        blogService.deleteBlogByBlogId((long) blogId);
        return ResponseMessage.success();
    }


    @ApiOperation(value = "根据博客id更新博客")
    @PutMapping
    public ResponseMessage updateBlogByBlogId(@RequestBody Blog blog, @RequestParam("blogId") int blogId) {
        blogService.updateBlogByBlogId(blog, (long) blogId);
        //如果全为空怎么判断
        return ResponseMessage.success();
    }

    @ApiOperation(value = "点赞（取消点赞）博客")
    @PutMapping("/like/{flag}")
    public ResponseMessage whetherLikeBlogOrNot(@PathVariable("flag") boolean flag, @RequestParam("blogId") int blogId) {
        blogService.whetherLikeBlogOrNot(flag, (long) blogId);
        return ResponseMessage.success();
    }

    /**
     * 查询所有博客（智能推荐）
     *
     * @param page
     * @return
     */
    @GetMapping("/all/{page}")
    @ApiOperation(value = "查询所有博客（智能推荐）")
    public ResponseMessage readAll(@PathVariable("page") long page) {
        String name = StpUtil.getLoginId("");
        String key = BLOG_LIST_PREFIX + name;
        if (redisUtil.hasKey(key)) {
            page = (page < 0) ? 0 : page;
            long start = page * PAGE_SIZE;
            List<Object> blogList = redisUtil.getList(key, start, start + PAGE_SIZE);
            return ResponseMessage.success(new BlogPage(redisUtil.getListSize(key), page, blogList));
        }
        List<Blog> blogList = blogService.readAll(name);
        redisUtil.setList(key, blogList, EXPIRE_TIME);
        List<Object> objects = redisUtil.getList(key, 0, PAGE_SIZE);
        return ResponseMessage.success(new BlogPage(blogList.size(), 0, objects));
    }
}

