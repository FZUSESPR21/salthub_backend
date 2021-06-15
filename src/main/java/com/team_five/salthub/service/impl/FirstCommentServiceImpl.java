package com.team_five.salthub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.dao.FirstCommentDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.service.FirstCommentService;
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
public class FirstCommentServiceImpl extends ServiceImpl<FirstCommentDao, FirstComment> implements FirstCommentService {


	@Autowired
	FirstCommentDao firstCommentDao;
	@Autowired
	BlogDao blogDao;

	private QueryWrapper wrapper = new QueryWrapper();

	/*** 
	 * @Description: 发布一级评论
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	@Override
	public void publishFirstComment(FirstComment firstComment) {
		//发布一级评论
		if (StrUtil.isEmpty(firstComment.getContent())) {
			throw new BaseException(ExceptionInfo.EMPTY_CONTENT);
		} else if (firstComment.getBlogId() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_BLOG_ID);
		} else if (!isBlogExist(firstComment.getBlogId())) {        //判断博客是否存在
			throw new BaseException(ExceptionInfo.BLOG_NO_EXIST);
		}

		firstCommentDao.insert(firstComment);
	}


	/***
	 * @Description: 根据博客id查询一级评论 返回该博客所属的所有一级评论
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	@Override
	public List<FirstComment> queryFirstComment(Long blogId) {
		if (!isBlogExist(blogId)) {
			throw new BaseException(ExceptionInfo.BLOG_NO_EXIST);
		}

		List<FirstComment> firstComments = null;
		synchronized (wrapper){
			wrapper.clear();
			wrapper.eq("blog_id", blogId);
			firstComments = firstCommentDao.selectList(wrapper);
		}

		return firstComments;
	}

	/***
	 * @Description: 删除一级评论
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	@Override
	public void deleteComment(Long id) {
		if (!isFirstCommentExist(id)) {
			throw new BaseException(ExceptionInfo.FIRST_COMMENT_NO_EXIST);
		}

		wrapper.clear();
		wrapper.eq("id", id);
		firstCommentDao.delete(wrapper);
	}


	/***
	 * @Description: 判断博客是否存在
	 * @Param: blogId
	 * @return: true：存在 false：不存在
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	public boolean isBlogExist(Long blogId) {
		wrapper.clear();
		wrapper.eq("id", blogId);
		if (blogDao.selectList(wrapper).size() == 0) {
			return false;
		}
		return true;
	}

	public boolean isFirstCommentExist(Long id) {
		wrapper.clear();
		wrapper.eq("id", id);
		if (firstCommentDao.selectList(wrapper).size() == 0) {
			return false;
		}
		return true;
	}


}
