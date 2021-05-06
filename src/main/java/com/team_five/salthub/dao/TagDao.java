package com.team_five.salthub.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xydf
 * @since 2021-05-05
 */
public interface TagDao extends BaseMapper<Tag> {
    @Select("SELECT t.* FROM blog_tag b,tag t WHERE b.tag_id=t.id AND blog_id =#{id}")
    List<Tag> selectBLogTag(long id);
}
