package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.model.Notice;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface FirstCommentService extends IService<FirstComment> {
	void publishFirstComment(FirstComment firstComment);
	List<FirstComment> queryFirstComment(Long blogId);

}
