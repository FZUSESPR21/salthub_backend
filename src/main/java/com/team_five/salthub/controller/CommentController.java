package com.team_five.salthub.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.SecondaryComment;
import com.team_five.salthub.service.FirstCommentService;
import com.team_five.salthub.service.SecondaryCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	@ApiOperation(value = "一级评论发布接口")
	@PostMapping("/0")
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
	@ApiOperation(value = "二级评论发布接口")
	@PostMapping("/1")
	public ResponseMessage publishSecondaryComment(@RequestBody SecondaryComment secondaryComment) {
		secondaryCommentService.publishSecondaryComment(secondaryComment);

		return ResponseMessage.success();
	}

	/*** 
	 * @Description: 查询评论
	 * @Param: id  flag:标记是一级评论0还是二级评论1
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/2
	 */
	@ApiOperation(value = "一级评论查询接口")
	@GetMapping("/2")
	public ResponseMessage queryComment(@RequestParam Long id, @RequestParam Integer flag) {
		if (flag == 0){		//查询一级评论
			List<FirstComment> firstComments = firstCommentService.queryFirstComment(id);
			return ResponseMessage.success(firstComments);
		}
		else if (flag == 1){		//查询二级评论
			List<SecondaryComment> secondaryComments = secondaryCommentService.querySecondaryComment(id);
			return ResponseMessage.success(secondaryComments);
		}

		return ResponseMessage.fail(new BaseException(ExceptionInfo.FLAG_ERROR));
	}

	/***
	* @Description: 删除评论
	* @Param:
	* @return:
	* @Author: top
	* @Date: 2021/5/2
	*/
	@ApiOperation(value = "删除评论")
	@DeleteMapping
	public ResponseMessage deleteComment(@RequestParam Long id, @RequestParam Integer flag){
		if (flag == 0){
			firstCommentService.deleteComment(id);
			return ResponseMessage.success();
		}
		else if (flag==1){
			secondaryCommentService.deleteComment(id);
			return ResponseMessage.success();
		}

		return ResponseMessage.fail(new BaseException(ExceptionInfo.FLAG_ERROR));
	}



}

