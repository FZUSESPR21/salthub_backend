package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Attachment;
import com.team_five.salthub.model.Blog;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public interface BlogService extends IService<Blog> {
    //发布博客合法性验证
    public void validityCheck(Blog blog);

    //将博客数据插入数据库中
    public Long insert(Blog blog);

    //板块id合法性验证
    public void moduleIdValidityCheck(Long moduleId);

    //通过板块id查询博客
    public Page<Blog> searchBlogByModuleId(Long moduleId, Long current);

    //标签id合法性验证
    public void tagIdValidityCheck(Long tagId);

    public Page<Blog> searchBlogByTagId(Long tagId, Long current);

    //通过博客id查询博客
    public Blog searchBlogByBlogId(Long blogId);

    //用户名合法性验证
    void accountValidityCheck(String account);

    //通过用户名查询博客
    public Page<Blog> searchBlogByAccount(String account, Long current);

    //通过博客id删除博客
    public void deleteBlogByBlogId(Long blogId);

    public void cancelBanBlogByBlogId(Long blogId);

    public void banBlogByBlogId(Long blogId);


    //通过博客id更新博客
    public void updateBlogByBlogId(Blog blog, Long blogId);

    //点赞（取消点赞）博客
    public void whetherLikeBlogOrNot(Boolean flag, Long blogId);

    // 读取所有博客
    List<Blog> readAll(String name);
    Page<Blog> queryAllBlog(Integer current);

    void validityCheckFile(File file) throws IOException;

    void insertAttachment(Attachment attachment);

    public Page<Blog> selectBlogByTitle(String title, Long current);

    //获取所有博客数量
    public int searchBlogCount();

    //获取当天新增博客数量
    public int searchIntradayBlogCount();

    //随机取一条树洞
    public Blog selectTreeHoleByRand();


}
