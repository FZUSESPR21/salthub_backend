package com.team_five.salthub.service;

import com.team_five.salthub.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xydf
 * @since 2021-05-05
 */
public interface TagService extends IService<Tag> {
    public List<Tag> selectTag();
    public List<Tag> selectBlogTag(long id);
    public void setTag(long blogId,int[] tag);

}
