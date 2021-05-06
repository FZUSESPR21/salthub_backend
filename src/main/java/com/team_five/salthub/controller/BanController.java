package com.team_five.salthub.controller;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.constant.BlogStateEnum;
import com.team_five.salthub.service.AccountService;
import com.team_five.salthub.service.BlogService;
import com.team_five.salthub.util.DeviceUtil;
import com.team_five.salthub.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 封禁控制器
 * </p>
 *
 * @date 2021/05/05
 */
@RestController
@RequestMapping("/ban")
public class BanController {
    private Account account = new Account();
    @Autowired
    AccountService accountService;
    @Autowired
    BlogService blogService;
    @PutMapping("account")
    @ApiOperation(value = "封禁用户接口")
    public ResponseMessage BanAccount(@RequestParam("name") String name){
       account.setName(name);
        accountService.banAccount(account);
       return ResponseMessage.success();
    }
    @DeleteMapping("account")
    @ApiOperation(value = "取消封禁用户接口")
    public ResponseMessage CancelBanAccount(@RequestParam("name") String name){
        account.setName(name);
        accountService.cancelBanAccount(account);
        return ResponseMessage.success();
    }
    @ApiOperation(value = "根据id封禁博客")
    @PutMapping("/blog")
    public ResponseMessage banBlogByBlogId(@RequestParam("blogId") long blogId) {
        blogService.banBlogByBlogId(blogId);
        return ResponseMessage.success();
    }

    @ApiOperation(value = "根据id取消封禁博客")
    @DeleteMapping("/blog")
    public ResponseMessage cancelBanBlogByBlogId(@RequestParam("blogId") long blogId) {
        blogService.cancelBanBlogByBlogId(blogId);
        return ResponseMessage.success();
    }
}
