package com.team_five.salthub.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.model.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @date 2021/04/26
 */
public interface BlogDao extends BaseMapper<Blog> {
    @Select("SELECT b.* FROM blog b,blog_tag bt,tag t WHERE b.id=bt.blog_id and bt.tag_id=t.id and t.id= #{tagId}")
    Page<Blog> selectBlogByTagId(Page<Blog> page, Long tagId);
}
