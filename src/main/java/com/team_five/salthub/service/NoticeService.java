package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Notice;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface NoticeService extends IService<Notice> {

	//定义常量
	Long PAGESIZE = 10L;
	Integer MAX_CONTENT_LENGTH = 65536;
	Integer MAX_TITLE_LENGTH = 256;

	void publishNotice(Notice notice);
	Page<Notice> queryNoticeByName(String accountName, Integer current);
	void deleteNotice(String id);
	void modifyNotice(Notice notice);
	Page<Notice> getAllNotice(Integer current);
}
