package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.dao.BlogTagDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.BlogTag;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.model.Tag;
import com.team_five.salthub.dao.TagDao;
import com.team_five.salthub.service.BlogService;
import com.team_five.salthub.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xydf
 * @since 2021-05-05
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogTagDao blogTagDao;
    @Override
    public List<Tag> selectTag() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.clear();
        List<Tag> list = tagDao.selectList(wrapper);
        return list;

    }
    @Override
    public List<Tag> selectBlogTag(long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.clear();
        wrapper.eq("id", id);    //判断该id是否存在
        //通知id不存在
        if (blogDao.selectList(wrapper).size()==0) {
            throw new BaseException(ExceptionInfo.BLOG_NOT_EXIST_ERROR);
        }
        List<Tag> list = tagDao.selectBLogTag(id);
        if(list.size()==0){
            throw new BaseException(ExceptionInfo.BLOG_TAG_NOT_EXIST_ERROR);
        }
        return list;

    }

    @Override
    public void setTag(long blogId, int[] tag) {

        BlogTag blogTag = new BlogTag();
        blogTag.setBlogId(blogId);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.clear();
        wrapper.eq("id", blogId);    //判断该id是否存在
        //通知id不存在
        if (blogDao.selectList(wrapper).size()==0) {
            throw new BaseException(ExceptionInfo.BLOG_NOT_EXIST_ERROR);
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("blog_id",blogId);
        int sum = blogTagDao.deleteByMap(map);
        for (int t:tag
             ) {
            if (tagDao.selectById(t)==null){
                throw new BaseException(ExceptionInfo.TAG_NOT_EXIST_ERROR);
            }
        }
        for (long t:tag
             ) {
                blogTag.setTagId(t);
                blogTagDao.insert(blogTag);
        }

    }
}
