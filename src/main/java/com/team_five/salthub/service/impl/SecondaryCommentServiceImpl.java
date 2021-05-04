package com.team_five.salthub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.FirstCommentDao;
import com.team_five.salthub.dao.SecondaryCommentDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.SecondaryComment;
import com.team_five.salthub.service.SecondaryCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class SecondaryCommentServiceImpl extends ServiceImpl<SecondaryCommentDao, SecondaryComment> implements SecondaryCommentService {


	@Autowired
	SecondaryCommentDao secondaryCommentDao;
	@Autowired
	FirstCommentDao firstCommentDao;

	private QueryWrapper wrapper = new QueryWrapper();

	/***
	 * @Description: 发布二级评论
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	@Override
	public void publishSecondaryComment(SecondaryComment secondaryComment) {
		if (secondaryComment.getContent() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_CONTENT);
		} else if (secondaryComment.getCommentId() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_COMMENT_ID);
		} else if (secondaryComment.getAccountName() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_ACCOUNT_NAME);
		} else if(!isFirstCommentExist(secondaryComment.getCommentId())){
			throw new BaseException(ExceptionInfo.FIRST_COMMENT_NO_EXIST);
		}

		secondaryComment.setAuthor(StpUtil.getLoginIdAsString());    //设置评论者	登陆后可使用
		secondaryComment.setReleaseTime(new Date());                //设置发布时间

		secondaryCommentDao.insert(secondaryComment);
	}


	/*** 
	 * @Description: 根据一级评论id查询其下属的所有二级评论
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	@Override
	public List<SecondaryComment> querySecondaryComment(Long id) {
		if(!isFirstCommentExist(id)){
			throw new BaseException(ExceptionInfo.BLOG_NO_EXIST);
		}

		wrapper.clear();
		wrapper.eq("comment_id", id);
		List<SecondaryComment> secondaryComments = secondaryCommentDao.selectList(wrapper);

		return secondaryComments;
	}


	/***
	 * @Description: 判断一级评论是否存在
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	public boolean isFirstCommentExist(Long id){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("id",id);
		if (firstCommentDao.selectList(queryWrapper).size() == 0) {
			return false;
		}
		return true;
	}

	/*** 
	* @Description: 判断二级评论是否存在
	* @Param:  
	* @return:  
	* @Author: top
	* @Date: 2021/5/2 
	*/
	public boolean isSecondaryCommentExist(Long id){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("id",id);
		if (secondaryCommentDao.selectList(queryWrapper).size() == 0) {
			return false;
		}
		return true;
	}


}
