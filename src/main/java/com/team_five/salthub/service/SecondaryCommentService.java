package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.SecondaryComment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface SecondaryCommentService extends IService<SecondaryComment> {
	void publishSecondaryComment(SecondaryComment comment);
}
