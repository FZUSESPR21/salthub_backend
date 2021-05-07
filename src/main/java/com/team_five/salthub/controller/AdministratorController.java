package com.team_five.salthub.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {

	@Autowired
	AccountService accountService;


	/***
	 * @Description: 获取用户列表
	 * @Param: current:当前页 初始为1
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/6
	 */
	@GetMapping("/all")
	@ApiOperation(value = "获取用户列表(分页)")
	public ResponseMessage getAccountList(@RequestParam Integer current) {
		return ResponseMessage.success(accountService.queryAll(current));
	}

	/***
	 * @Description: 查询单个用户
	 * @Param: 用户名name
	 * @return:
	 * @Author: top
	 * @Date: 2021/5/7
	 */
	@GetMapping
	@ApiOperation(value = "查询单个用户信息")
	public ResponseMessage queryOne(@RequestParam String name) {
		Account account = accountService.queryOne(name);

		return ResponseMessage.success(account);
	}




}
