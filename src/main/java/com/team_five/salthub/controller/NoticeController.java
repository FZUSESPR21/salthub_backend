package com.team_five.salthub.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	NoticeService noticeService;

//region 	公告增删改查

	/***
	* @Description: 发布通知
	* @Param:  body：公告主体 param：name 发布公告的人
	 * {
	 *     "title":,
	 *     "content":,
	 *     "accountName":,指定发布给谁
	 *
	 *
	 * }
	* @return:
	* @Author: top
	* @Date: 2021/4/28
	*/
	@PostMapping
	public ResponseMessage publishNotice(@RequestBody Notice notice, @RequestParam String name){
		noticeService.publishNotice(notice, name);		//service层实现业务逻辑

		return ResponseMessage.success();
	}

	/*** 
	* @Description: 通过用户名查询公告
	* @Param:  用户名 name
	* @return:  Notice
	* @Author: top
	* @Date: 2021/4/28 
	*/
	@GetMapping
	public ResponseMessage queryNoticeByName(@RequestParam String accountName, @RequestParam Long current){

		Page<Notice> notices = noticeService.queryNoticeByName(accountName, current);

		return ResponseMessage.success(notices);
	}

	/*** 
	* @Description: 删除公告 
	* @Param:  
	* @return:  
	* @Author: top
	* @Date: 2021/4/28 
	*/
	// TODO: 2021/4/28
	@DeleteMapping
	public ResponseMessage deleteNotice(){
		
		return ResponseMessage.success();
	}

	/*** 
	* @Description: 修改公告
	* @Param:  
	* @return:  
	* @Author: top
	* @Date: 2021/4/28 
	*/
	@PutMapping
	public ResponseMessage modifyNotice(){
		
		return ResponseMessage.success();
	}

//endregion

//region	评论增删改查



//endregion

//region	管理员操作



}

