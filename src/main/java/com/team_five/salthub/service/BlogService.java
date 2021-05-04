package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Blog;
import org.springframework.stereotype.Service;

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
    public boolean insert(Blog blog);

    //板块id合法性验证
    public void moduleIdValidityCheck(Long moduleId);

    //通过板块id查询博客
    public Page<Blog> searchBlogByModuleId(Long moduleId, Long current);

    //标签id合法性验证
    public void tagIdValidityCheck(Long tagId);

    public Page<Blog> searchBlogByTagId(Long tagId, Long current);

    //通过博客id查询博客
    public Page<Blog> searchBlogByBlogId(Long blogId, Long current);

    //用户名合法性验证
    void accountValidityCheck(String account);

    //通过用户名查询博客
    public Page<Blog> searchBlogByAccount(String account, Long current);

    //通过博客id删除博客
    public void deleteBlogByBlogId(Long blogId);

    //通过博客id更新博客
    public void updateBlogByBlogId(Blog blog, Long bolgId);

    // 读取所有博客
    List<Blog> readAll(String name);

}
