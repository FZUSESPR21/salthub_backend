package com.team_five.salthub.controller;


import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.SecondaryComment;
import com.team_five.salthub.service.FirstCommentService;
import com.team_five.salthub.service.SecondaryCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private FirstCommentService firstCommentService;
	@Autowired
	private SecondaryCommentService secondaryCommentService;

	/***
	 * @Description: 添加评论
	 * @Param: firstComment(可能是一记评论或二级评论)
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/1
	 */
	@PostMapping("/{0}")
	public ResponseMessage publishFirstComment(@RequestBody FirstComment firstComment) {
		firstCommentService.publishFirstComment(firstComment);

		return ResponseMessage.success();
	}

	/*** 
	 * @Description: 添加二级评论
	 * @Param: secondaryComment
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/1
	 */
	@PostMapping("/{1}")
	public ResponseMessage publishSecondaryComment(@RequestBody SecondaryComment secondaryComment) {
		secondaryCommentService.publishSecondaryComment(secondaryComment);

		return ResponseMessage.success();
	}


}

