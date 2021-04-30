package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Notice;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface NoticeService extends IService<Notice> {
	void publishNotice(Notice notice, String name);
	public List<Notice> queryNoticeByName(String accountName);
}
