package com.team_five.salthub.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.NoticeDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	public void publishNotice(Notice notice, String name){
//		非空检测
		if (notice.getContent()==null){
			throw new BaseException(ExceptionInfo.EMPTYCONTENT);
		}
		else if (notice.getTitle()==null){
			throw new BaseException(ExceptionInfo.EMPTYTITLE);
		}
		else if(notice.getAccountName()==null){
			throw new BaseException(ExceptionInfo.RECIVEREMPTY);
		}
		else if (name==null){
			throw new BaseException(ExceptionInfo.AUTHOREMPTY);
		}
		//合法性检测
		else if (notice.getContent().length()>255){
			throw new BaseException(ExceptionInfo.ILLEGALLENGTH);
		}
		else if (notice.getTitle().length()>256){
			throw new BaseException(ExceptionInfo.ILLEGALTITLELENGTH);
		}


		Date releaseTime = new Date();
		notice.setAuthor(name);
		notice.setReleaseTime(releaseTime);

		noticeDao.insert(notice);
	}
	
	/*** 
	* @Description: 获取当前时间
	* @Param:  
	* @return:  
	* @Author: top
	* @Date: 2021/4/29 
	*/
	// FIXME: 2021/4/29
	public static Date  getNow() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//指定时间格式
		Date now = new Date();
		String releaseTime = format.format(now);

		return format.parse(releaseTime);
	}
	
}
