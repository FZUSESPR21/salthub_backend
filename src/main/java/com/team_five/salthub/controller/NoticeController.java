package com.team_five.salthub.controller;


import com.team_five.salthub.model.Notice;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	* @Param:  body：公告主体 param：name
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
	// TODO: 2021/4/28
	@PostMapping
	public ResponseMessage publishNotice(@RequestBody Notice notice, @RequestParam String name){

		try {
			noticeService.publishNotice(notice, name);		//service层实现业务逻辑
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return ResponseMessage.success();
	}

	/*** 
	* @Description: 通过用户名查询公告
	* @Param:  用户名 name
	* @return:  
	* @Author: top
	* @Date: 2021/4/28 
	*/
	// TODO: 2021/4/28
	@GetMapping
	public ResponseMessage queryNoticeByName(){


		return ResponseMessage.success();
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

