package com.team_five.salthub.controller;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team_five.salthub.config.AddressMapping;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.mail.PasswordSecurityEmail;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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
    private static final String LOGIN_PREFIX = "LOGIN_";
    private static final String WAIT_PREFIX = "WAIT_";
    private static final int LOGIN_MAX_COUNT = 3;
    private static final int CODE_LENGTH = 6;
    private static final long WAIT_TIME = 3 * 60 * 1000;


    @Autowired
    private AccountService accountService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 登录接口
     *
     * @param account  用户
     * @param flag     true为记住我
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
        try {
            if (redisUtil.hasKey(WAIT_PREFIX + account.getName())) {
                throw new BaseException(ExceptionInfo.WAIT);
            }
            Account account1 = accountService.login(account);
            StpUtil.logoutByLoginId(account.getName(), DeviceUtil.getDevice(device));
            SaLoginModel saLoginModel = new SaLoginModel();
            saLoginModel.setDevice(DeviceUtil.getDevice(device));
            saLoginModel.setIsLastingCookie(flag != 0);
            StpUtil.setLoginId(account.getName(), saLoginModel);
            response.setHeader("jwt", StpUtil.getTokenValue());
            log.info("用户：" + account.getName() + " 登录成功");
            redisUtil.delete(LOGIN_PREFIX + account.getName());
            redisUtil.delete(WAIT_PREFIX + account.getName());
            account1.setAvatar("/avatar/" + account1.getAvatar());
            return ResponseMessage.success(account1);
        } catch (BaseException e) {
            if (ExceptionInfo.PASSWORD_ERROR.getMessage().equals(e.getMessage())) {
                String key = LOGIN_PREFIX + account.getName();
                int num = 1;
                if (redisUtil.hasKey(key)) {
                    num = (int) redisUtil.get(key) + 1;
                    if (num > LOGIN_MAX_COUNT) {
                        redisUtil.set(WAIT_PREFIX + account.getName(), num, WAIT_TIME);
                        (new PasswordSecurityEmail(accountService.getById(account.getName()).getEmail())).send();
                        throw e;
                    }
                }
                redisUtil.set(key, num, DateUtil.between(new Date(),
                        DateUtil.parseDate(DateUtil.formatDate(DateUtil.tomorrow())), DateUnit.MS));
            }
            throw e;
        }
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
        if (!Validator.isEmail(email)) {
            throw new BaseException(ExceptionInfo.EMAIL_ILLEGAL);
        }
        String code = VerificationCodeUtil.getCode(CODE_LENGTH).toLowerCase();
        new VerificationCodeEmail(email, code).send();
        redisUtil.set(email, code, VerificationCodeEmail.CODE_EXPIRE);
        return ResponseMessage.success();
    }

    /**
     * 注册
     *
     * @param account
     * @param code
     * @return
     */
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

    /**
     * 判断用户名是否存在
     *
     * @param name
     * @return
     */
    @GetMapping("/name")
    @ApiOperation(value = "判断用户名是否存在")
    public ResponseMessage nameExist(@RequestParam("name") String name) {
        return ResponseMessage.success(accountService.getById(name) != null);
    }

    /**
     * 判断邮箱是否存在
     *
     * @param email
     * @return
     */
    @GetMapping("/email")
    @ApiOperation(value = "判断邮箱是否存在")
    public ResponseMessage emailExist(@RequestParam("email") String email) {
        return ResponseMessage.success(accountService.getOne(new QueryWrapper<Account>().
                eq("email", email)) != null);
    }

    /**
     * 修改用户密码
     *
     * @param oldPassword,newPassword
     * @return
     */
    @ApiOperation(value = "修改用户密码")
    @PutMapping("/password")
    public ResponseMessage updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        String name = StpUtil.getLoginIdAsString();
        Account account = accountService.updatePassword(name, oldPassword, newPassword);
        return ResponseMessage.success(account);
    }

    /**
     * 修改用户信息
     *
     * @param account
     * @return
     */
    @ApiOperation(value = "修改用户信息")
    @PutMapping
    public ResponseMessage upadateInformation(@RequestBody Account account) {
        if (!StrUtil.isEmpty(account.getNickname())) {
            accountService.nicknameValidityCheck(account.getNickname());
        }
        if (!StrUtil.isEmpty(account.getSlogan())) {
            accountService.sloganValidityCheck(account.getSlogan());
        }
        String name = StpUtil.getLoginIdAsString();
        accountService.updateInformation(name, account.getNickname(), account.getSlogan());
        return ResponseMessage.success();
    }

    @PutMapping("/avatar")
    @ApiOperation(value = "头像上传")
    public ResponseMessage uploadAvatar(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        File file = new File((AddressMapping.FILE_SAVE_ROOT + UUID.randomUUID() + avatar.getOriginalFilename())
            .replace("-", ""));
        try {
            avatar.transferTo(file);
        } catch (IOException e) {
            if (file.exists()) {
                file.delete();
            }
            e.printStackTrace();
            throw new BaseException(ExceptionInfo.UPLOAD_FAIL);
        }
        accountService.updateAvatar(StpUtil.getLoginIdAsString(), file);
        return ResponseMessage.success("/avatar/" + file.getName());
    }
}

