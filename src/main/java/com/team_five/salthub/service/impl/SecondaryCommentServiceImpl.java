package com.team_five.salthub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.SecondaryCommentDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.SecondaryComment;
import com.team_five.salthub.service.SecondaryCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class SecondaryCommentServiceImpl extends ServiceImpl<SecondaryCommentDao, SecondaryComment> implements SecondaryCommentService {


	@Autowired
	SecondaryCommentDao secondaryCommentDao;

	@Override
	public void publishSecondaryComment(SecondaryComment secondaryComment){
		if (secondaryComment.getContent() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_CONTENT);
		} else if (secondaryComment.getCommentId() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_COMMENT_ID);
		} else if (secondaryComment.getAccountName()==null){
			throw new BaseException(ExceptionInfo.EMPTY_ACCOUNT_NAME);
		}

		secondaryComment.setAuthor(StpUtil.getLoginIdAsString());    //设置评论者	登陆后可使用
//		secondaryComment.setAuthor("221801310");
		secondaryComment.setReleaseTime(new Date());                //设置发布时间

		secondaryCommentDao.insert(secondaryComment);

	}
}
