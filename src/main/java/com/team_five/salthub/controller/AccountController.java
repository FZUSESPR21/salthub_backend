package com.team_five.salthub.controller;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.mail.VerificationCodeEmail;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.constant.RoleEnum;
import com.team_five.salthub.service.AccountService;
import com.team_five.salthub.util.DeviceUtil;
import com.team_five.salthub.util.RedisUtil;
import com.team_five.salthub.util.VerificationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户controller
 * </p>
 *
 * @date 2021/04/26
 */
@Api("与用户相关的接口")
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    private static final int CODE_LENGTH = 6;

    @Autowired
    private AccountService accountService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 登录接口
     *
     * @param account 用户
     * @param flag true为记住我
     * @param response
     * @param device
     * @return
     */
    @PostMapping("/login/{flag}")
    @ApiOperation(value = "用户登录接口")
    public ResponseMessage login(@RequestBody Account account,
                                 @PathVariable("flag") int flag,
                                 HttpServletResponse response,
                                 Device device) {
        Account account1 = accountService.login(account);
        StpUtil.logoutByLoginId(account.getName(), DeviceUtil.getDevice(device));
        SaLoginModel saLoginModel = new SaLoginModel();
        saLoginModel.setDevice(DeviceUtil.getDevice(device));
        saLoginModel.setIsLastingCookie(flag != 0);
        StpUtil.setLoginId(account.getName(), saLoginModel);
        response.setHeader("jwt", StpUtil.getTokenValue());
        log.info("用户：" + account.getName() + " 登录成功");
        return ResponseMessage.success(account1);
    }

    /**
     * 用户登出接口
     *
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation(value = "用户登出接口")
    public ResponseMessage logout() {
        StpUtil.logout();
        return ResponseMessage.success();
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @GetMapping("/code")
    @ApiOperation(value = "发送邮箱验证码")
    public ResponseMessage getMailCode(@RequestParam("email") String email) {
        if (StrUtil.isEmpty(email)) {
            throw new BaseException(ExceptionInfo.MAIL_EMPTY);
        }
        // TODO : 邮箱合法性
        String code = VerificationCodeUtil.getCode(CODE_LENGTH).toLowerCase();
        new VerificationCodeEmail(email, code).send();
        redisUtil.set(email, code, VerificationCodeEmail.CODE_EXPIRE);
        return ResponseMessage.success();
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口")
    public ResponseMessage register(@RequestBody Account account, @RequestParam("code") String code) {
        String email = account.getEmail();
        if (StrUtil.isEmpty(email)) {
            throw new BaseException(ExceptionInfo.MAIL_EMPTY);
        }
        if (redisUtil.hasKey(email) && redisUtil.get(email).equals(code.toLowerCase())) {
            account.setRoleId(RoleEnum.NORMAL.getId());
            account.setAvatar("default.jpg");
            account.setNickname(account.getName());
            accountService.register(account);
            return ResponseMessage.success();
        }
        throw new BaseException(ExceptionInfo.VERIFICATION_CODE_INVALID);
    }


}

