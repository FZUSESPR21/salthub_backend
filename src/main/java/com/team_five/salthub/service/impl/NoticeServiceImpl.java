package com.team_five.salthub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.dao.NoticeDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.NoticeService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private AccountDao accountDao;

	private QueryWrapper wrapper = new QueryWrapper();

	/*** 
	 * @Description: 发布博客
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/4/28
	 */
	@Override
	public void publishNotice(Notice notice) {
		//非空检测
		if (StrUtil.isEmpty(notice.getContent())) {
			throw new BaseException(ExceptionInfo.EMPTY_CONTENT);
		} else if (StrUtil.isEmpty(notice.getTitle())) {
			throw new BaseException(ExceptionInfo.EMPTY_TITLE);
		} else if (StrUtil.isEmpty(notice.getAccountName())) {
			throw new BaseException(ExceptionInfo.EMPTY_ACCOUNT_NAME);
		} else if (notice.getContent().length() > MAX_CONTENT_LENGTH) {		//合法性检测
			throw new BaseException(ExceptionInfo.ILLEGAL_LENGTH);
		} else if (notice.getTitle().length() > MAX_TITLE_LENGTH) {
			throw new BaseException(ExceptionInfo.ILLEGAL_TITLE_LENGTH);
		} else if (!isAccountExist(notice.getAccountName())){	//判断要通知的用户是否存在
			throw new BaseException(ExceptionInfo.ACCOUNT_NO_EXIST);
		}

		noticeDao.insert(notice);
	}

	/*** 
	 * @Description: 通过同户名查询公告
	 * @Param: accountName
	 * @return:
	 * @Author: top
	 * @Date: 2021/4/30
	 */
	@Override
	public Page<Notice> queryNoticeByName(String accountName, Integer current) {
		if (StrUtil.isEmpty(accountName)) {
			throw new BaseException(ExceptionInfo.EMPTY_ACCOUNT_NAME);
		} else if (!isAccountExist(accountName)){
			throw new BaseException(ExceptionInfo.ACCOUNT_NO_EXIST);
		}

		wrapper.clear();
		wrapper.eq("account_name", accountName);

		Page<Notice> page = new Page<>(current, PAGESIZE);        //当前页数， 页面大小(定义在接口中的常量)
		Page<Notice> notices = noticeDao.selectPage(page, wrapper);

		return notices;
	}
	/*** 
	 * @Description: 根据通知id删除通知
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/1
	 */
	@Override
	public void deleteNotice(String id) {

		wrapper.clear();
		wrapper.eq("id", id);    //判断该id是否存在
		//通知id不存在
		if (noticeDao.selectList(wrapper).size() == 0) {
			throw new BaseException(ExceptionInfo.NOTICE_NO_EXIST);
		}

		noticeDao.delete(wrapper);
	}

	/*** 
	 * @Description: 修改通知
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/1
	 */
	@Override
	public void modifyNotice(Notice notice) {
		wrapper.clear();
		wrapper.eq("id", notice.getId());    //判断该id是否存在
		//通知id不存在
		if (noticeDao.selectList(wrapper).size()==0) {
			throw new BaseException(ExceptionInfo.NOTICE_NO_EXIST);
		}

		noticeDao.update(notice, wrapper);
	}

	/*** 
	* @Description: 返回所有通告
	* @Param:  当前页
	* @return:  
	* @Author: top
	* @Date: 2021/5/7 
	*/
	@Override
	public Page<Notice> getAllNotice(Integer current){
		Page<Notice> page = new Page<>(current, PAGESIZE);        //当前页数， 页面大小(定义在接口中的常量)
		Page<Notice> notices = noticeDao.selectPage(page, null);

		return notices;
	}

	/***
	* @Description: 返回公告总条数
	* @Param:
	* @return:
	* @Author: top
	* @Date: 2021/6/12
	*/
	@Override
	public Integer getAmount(){
		Integer amout = noticeDao.selectList(null).size();

		return amout;
	}




	/*** 
	* @Description: 判断用户是否存在
	* @Param:  
	* @return:  
	* @Author: top
	* @Date: 2021/5/2 
	*/
	private boolean isAccountExist(String name){
		wrapper.clear();
		wrapper.eq("name", name);
		if (accountDao.selectOne(wrapper)==null){
			return false;
		}
		return true;
	}


}
