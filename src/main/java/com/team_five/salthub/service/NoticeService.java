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

//	定义常量
	Long PAGESIZE = 20L;

	void publishNotice(Notice notice, String name);
	public Page<Notice> queryNoticeByName(String accountName, Long current);
}
