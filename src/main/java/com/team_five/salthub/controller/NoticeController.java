package com.team_five.salthub.controller;


import com.team_five.salthub.model.Notice;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * <p>
 * 前端控制器
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
	 * @Param: body：公告主体
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
	@ApiOperation(value = "公告发布接口")
	@PostMapping
	public ResponseMessage publishNotice(@RequestBody Notice notice) {
		noticeService.publishNotice(notice);        //service层实现业务逻辑

		return ResponseMessage.success();
	}

	/*** 
	 * @Description: 通过用户名查询公告
	 * @Param: 用户名 name
	 * @return: Notice
	 * @Author: top
	 * @Date: 2021/4/28
	 */
	@ApiOperation(value = "公告查询接口")
	@GetMapping
	public ResponseMessage queryNoticeByName(@RequestParam String accountName, @RequestParam Long current) {
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
	@ApiOperation(value = "公告删除接口")
	@DeleteMapping
	public ResponseMessage deleteNotice(@RequestParam String id) {
		noticeService.deleteNotice(id);

		return ResponseMessage.success();
	}

	/*** 
	 * @Description: 修改公告
	 * @Param:
	 * @return:
	 * @Author: top
	 * @Date: 2021/4/28
	 */
	@ApiOperation(value = "公告修改")
	@PutMapping
	public ResponseMessage modifyNotice(@RequestBody Notice notice) {
		noticeService.modifyNotice(notice);

		return ResponseMessage.success();
	}

//endregion


}

