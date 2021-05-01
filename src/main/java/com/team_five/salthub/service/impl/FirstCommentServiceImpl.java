package com.team_five.salthub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.FirstCommentDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.FirstCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class FirstCommentServiceImpl extends ServiceImpl<FirstCommentDao, FirstComment> implements FirstCommentService {


	@Autowired
	FirstCommentDao firstCommentDao;


	@Override
	public void publishFirstComment(FirstComment firstComment) {
		//发布一级评论
		if (firstComment.getContent() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_CONTENT);
		} else if (firstComment.getBlogId() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_BLOG_ID);
		}

		firstComment.setAuthor(StpUtil.getLoginIdAsString());    //设置评论者	登陆后可使用
//		firstComment.setAuthor("221801310");
		firstComment.setReleaseTime(new Date());                //设置发布时间

		firstCommentDao.insert(firstComment);
	}


}
