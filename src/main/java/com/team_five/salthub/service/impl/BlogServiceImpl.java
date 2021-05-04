package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.constant.BlogStateEnum;
import com.team_five.salthub.model.constant.ModuleEnum;
import com.team_five.salthub.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {
    private static final long PAGESIZE = 10;
    @Autowired
    private BlogDao blogDao;

    @Override
    public void validityCheck(Blog blog) {
        boolean flag = false;
        if (blog.getModuleId() == null) {//判断模块id是否为空
            throw new BaseException(ExceptionInfo.MODULE_ID_EMPTY_ERROR);
        } else {//判断模块id是否属于预设模块
            for (ModuleEnum moduleEnum : ModuleEnum.values()
            ) {
                if (blog.getModuleId().equals(moduleEnum.getId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new BaseException(ExceptionInfo.MODULE_ID_ERROR);

            }
        }

        if (blog.getTitle() == null) {
            throw new BaseException(ExceptionInfo.TITLE_EMPTY_ERROR);
        }
        if (blog.getTitle().length() > 256) {
            throw new BaseException(ExceptionInfo.TITLE_ERROR);
        }
        if (blog.getContent() == null) {
            throw new BaseException(ExceptionInfo.CONTENT_EMPTY_ERROR);
        }
        if (blog.getContent().length() > 65535) {
            throw new BaseException(ExceptionInfo.CONTENT_ERROR);
        }
    }

    @Override
    public boolean insert(Blog blog) {
        blogDao.insert(blog);
        return true;
    }

    @Override
    public void moduleIdValidityCheck(Long moduleId) {
        if (moduleId == null) {//判断模块id是否为空
            throw new BaseException(ExceptionInfo.MODULE_ID_EMPTY_ERROR);
        } else {//判断模块id是否属于预设模块
            for (ModuleEnum moduleEnum : ModuleEnum.values()
            ) {
                if (moduleId.equals(moduleEnum.getId())) {
                    break;
                }
            }
        }
    }

    @Override
    public Page<Blog> searchBlogByModuleId(Long moduleId, Long current) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("module_id", moduleId);
        wrapper.eq("state", BlogStateEnum.NORMAL.getId());
        Page<Blog> page = new Page<Blog>(current, PAGESIZE);
        Page<Blog> blogList = blogDao.selectPage(page, wrapper);
        return blogList;
    }

    @Override
    public void tagIdValidityCheck(Long tagId) {
        if (tagId == null) {
            throw new BaseException(ExceptionInfo.TAG_ID_EMPTY_ERROR);
        }
    }

    @Override
    public Page<Blog> searchBlogByTagId(Long tagId, Long current) {
        Page<Blog> page = new Page<Blog>(current, PAGESIZE);
        Page<Blog> blogList = blogDao.selectBlogByTagId(page, tagId);
        return blogList;
    }

    @Override
    public List<Blog> searchBlogByBlogId(Long blogId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("blog_id", blogId);
        wrapper.eq("state", BlogStateEnum.NORMAL.getId());
        List<Blog> blogList = blogDao.selectList(wrapper);
        return blogList;
    }

    @Override
    public void accountValidityCheck(String account) {
        if (account == null) {
            throw new BaseException(ExceptionInfo.ACCOUNT_EMPTY_ERROR);
        }
    }

    @Override
    public Page<Blog> searchBlogByAccount(String account, Long current) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("author", account);
        wrapper.eq("state", BlogStateEnum.NORMAL.getId());
        Page<Blog> page = new Page<Blog>(current, PAGESIZE);
        Page<Blog> blogList = blogDao.selectPage(page, wrapper);
        return blogList;
    }

    @Override
    public void deleteBlogByBlogId(Long blogId) {
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<Blog>();
        updateWrapper.eq("id", blogId).set("state", BlogStateEnum.DELETE.getId());
        blogDao.update(null, updateWrapper);
    }

    @Override
    public void updateBlogByBlogId(Blog blog, Long bolgId) {
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<Blog>();
        if (blog.getModuleId() != null) {
            updateWrapper.set("module_id", blog.getModuleId());
        }
        if (blog.getTitle() != null) {
            updateWrapper.set("title", blog.getTitle());
        }
        if (blog.getContent() != null) {
            updateWrapper.set("content", blog.getContent());
        }
        updateWrapper.eq("id", bolgId);
        blogDao.update(null, updateWrapper);
    }
}
