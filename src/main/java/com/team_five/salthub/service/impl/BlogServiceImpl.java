package com.team_five.salthub.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.AttachmentDao;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Attachment;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.constant.BlogStateEnum;
import com.team_five.salthub.model.constant.ModuleEnum;
import com.team_five.salthub.service.BlogService;
import com.team_five.salthub.userBasedCollaborativeFiltering.UserCF;
import com.team_five.salthub.util.AttachmentUtil;
import com.team_five.salthub.wordFliter.WordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
    private static final int TITLE_MAX_LENGTH = 256;
    private static final int CONTENT_MAX_LENGTH = 65535;
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private AttachmentDao attachmentDao;

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

        if (StrUtil.isEmpty(blog.getTitle())) {//标题是否为空
            throw new BaseException(ExceptionInfo.TITLE_EMPTY_ERROR);
        }
        if (blog.getTitle().length() > TITLE_MAX_LENGTH) {//标题是否过长
            throw new BaseException(ExceptionInfo.TITLE_ERROR);
        }
        if (StrUtil.isEmpty(blog.getContent())) {//内容是否为空
            throw new BaseException(ExceptionInfo.CONTENT_EMPTY_ERROR);
        }
        if (blog.getContent().length() > CONTENT_MAX_LENGTH) {
            throw new BaseException(ExceptionInfo.CONTENT_ERROR);
        }

    }

    @Override
    public Long insert(Blog blog) {
        String content = WordFilter.doFilter(blog.getContent());
        blog.setContent(content);
        blogDao.insert(blog);

        return blog.getId();
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
    public Blog searchBlogByBlogId(Long blogId) {
        Blog blog = blogDao.selectById(blogId);
        return blog;
        /*QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", blogId);
        wrapper.eq("state", BlogStateEnum.NORMAL.getId());
        List<Blog> blogList = blogDao.selectList(wrapper);
        return blogList;*/
    }

    @Override
    public void accountValidityCheck(String account) {
        if (StrUtil.isEmpty(account)) {
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
    public void updateBlogByBlogId(Blog blog, Long blogId) {
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<Blog>();
        if (blog.getModuleId() != null) {
            updateWrapper.set("module_id", blog.getModuleId());
        }
        if (StrUtil.isEmpty(blog.getTitle())) {
            updateWrapper.set("title", blog.getTitle());
        }
        if (StrUtil.isEmpty(blog.getContent())) {
            updateWrapper.set("content", blog.getContent());
        }
        updateWrapper.eq("id", blogId);
        blogDao.update(null, updateWrapper);
    }

    @Override
    public void whetherLikeBlogOrNot(Boolean flag, Long blogId) {
        Blog blog = blogDao.selectById(blogId);//主键必须是id  否则应该用@TableId("主键名")
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<Blog>();
        if (flag) {
            updateWrapper.set("like_number", blog.getLikeNumber() + 1);
        } else {
            updateWrapper.set("like_number", blog.getLikeNumber() - 1);
        }
        updateWrapper.eq("id", blogId);
        blogDao.update(null, updateWrapper);
    }

    @Override
    public void banBlogByBlogId(Long blogId) {
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", blogId).set("state", BlogStateEnum.BAN.getId());
        blogDao.update(null, updateWrapper);
    }

    @Override
    public void cancelBanBlogByBlogId(Long blogId) {
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", blogId).set("state", BlogStateEnum.NORMAL.getId());
        blogDao.update(null, updateWrapper);
    }

    @Override
    public List<Blog> readAll(String name) {
        List<Blog> blogList = blogDao.selectList(new QueryWrapper<Blog>().eq("state",
                BlogStateEnum.NORMAL.getId()));
        List<Blog> defaultBlog = blogList.subList(0, Math.min(blogList.size(), 500));
        if ("".equals(name)) {
            return defaultBlog;
        }
        List<Blog> result = UserCF.getResult(name);
        return (result != null) ? result.subList(0, Math.min(result.size(), 500)) : defaultBlog;
    }

    @Override
    public void validityCheckFile(File file) throws IOException {
        if ((!file.exists()) || (file.length() < 1)) {
            if (file.exists()) {
                file.delete();
            }
            throw new BaseException(ExceptionInfo.ATTACHMENT_EMPTY);
        }
        String ext = (file.getName().substring(file.getName().lastIndexOf(".") + 1)).toLowerCase();
        if (!AttachmentUtil.attachmentSuffix(ext)) {
            if (file.exists()) {
                file.delete();
            }
            throw new BaseException(ExceptionInfo.ATTACHMENT_EXT_ILLEGAL);
        }
    }

    @Override
    public void insertAttachment(Attachment attachment) {
        attachmentDao.insert(attachment);
    }


    @Override
    public Page<Blog> selectBlogByTitle(String title, Long current) {


        Page<Blog> page = new Page<Blog>(current, 10);
        Page<Blog> blogList = blogDao.selectBlogByTitle(page, title);
        if (blogList.getTotal() <= 0) {
            throw new BaseException(ExceptionInfo.BLOG_NOT_MATCH_ERROR);
        }
        return blogList;
    }

    @Override
    public int searchBlogCount() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("state", BlogStateEnum.NORMAL.getId());
        int count = blogDao.selectCount(wrapper);
        return count;
    }

    @Override
    public int searchIntradayBlogCount() {
        Date date = new java.sql.Date(new java.util.Date().getTime());
        int count = blogDao.searchIntradayBlogCount(date);
        return count;
    }

    @Override
    public Blog selectTreeHoleByRand() {
        Blog blog = blogDao.selectTreeHoleByRand();
        blog.setCollectionNumber(null);
        blog.setState(null);
        blog.setTitle(null);
        blog.setReleaseTime(null);
        blog.setLikeNumber(null);
        blog.setId(null);
        blog.setModuleId(null);
        return blog;
    }
}
