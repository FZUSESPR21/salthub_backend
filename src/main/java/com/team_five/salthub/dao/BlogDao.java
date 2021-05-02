package com.team_five.salthub.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.model.Blog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @date 2021/04/26
 */
public interface BlogDao extends BaseMapper<Blog> {

    @Select("SELECT b.* FROM blog b,collection c WHERE c.blog_id=b.id  and account_name= #{account_name} and b.state= 2")
    List<Blog> collectionBLog(String account_name);

    @Select("SELECT b.* FROM blog b,blog_tag bt,tag t WHERE b.id=bt.blog_id and bt.tag_id=t.id and t.id= #{tagId} and b.state=2")
    Page<Blog> selectBlogByTagId(Page<Blog> page, Long tagId);
}
