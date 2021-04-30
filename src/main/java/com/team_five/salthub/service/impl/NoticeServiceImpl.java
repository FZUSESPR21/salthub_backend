package com.team_five.salthub.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.NoticeDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	/*** 
	 * @Description: 发布博客
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/4/28
	 */
	@Override
	public void publishNotice(Notice notice, String name) {
		//非空检测
		if (notice.getContent() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_CONTENT);
		} else if (notice.getTitle() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_TITLE);
		} else if (notice.getAccountName() == null) {
			throw new BaseException(ExceptionInfo.EMPTY_ACCOUNTNAME);
		} else if (name == null) {
			throw new BaseException(ExceptionInfo.EMPTY_AUTHOR);
		}
		//合法性检测
		else if (notice.getContent().length() > 65536) {
			throw new BaseException(ExceptionInfo.ILLEGAL_LENGTH);
		} else if (notice.getTitle().length() > 256) {
			throw new BaseException(ExceptionInfo.ILLEGAL_TITLELENGTH);
		}


		Date releaseTime = new Date();
		notice.setAuthor(name);
		notice.setReleaseTime(releaseTime);

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
	public List<Notice> queryNoticeByName(String accountName) {
		if (accountName == null || accountName.isEmpty()) {
			throw new BaseException(ExceptionInfo.EMPTY_ACCOUNTNAME);
		}

		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("account_name", accountName);
//		Page<Notice> page = new Page<Notice>(1,2);
//		Page<Notice> notices = noticeDao.selectPage(page, wrapper);

		List<Notice> notices = noticeDao.selectList(wrapper);

		return notices;
	}
}