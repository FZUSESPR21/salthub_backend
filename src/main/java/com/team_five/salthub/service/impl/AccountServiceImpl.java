package com.team_five.salthub.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.constant.RoleEnum;
import com.team_five.salthub.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {
    private static final int PASSWORD_MAX_LENGTH = 16;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int NAME_MAX_LENGTH = 32;
    private static final int NICKNAME_MAX_LENGTH = 32;
    private static final int SLOGAN_MAX_LENGTH = 256;
    @Autowired
    private AccountDao accountDao;


    /**
     * 登录
     *
     * @param account
     * @return
     */
    @Override
    public Account login(Account account) {
        if (StrUtil.isEmpty(account.getName())) {
            throw new BaseException(ExceptionInfo.NAME_EMPTY);
        }
        if (StrUtil.isEmpty(account.getPassword())) {
            throw new BaseException(ExceptionInfo.PASSWORD_EMPTY);
        }
        Account account1 = accountDao.selectById(account.getName());
        if (account1 == null) {
            throw new BaseException(ExceptionInfo.NAME_NOT_EXIST);
        }
        if (!account1.getPassword().equals(md5BySalt(account.getPassword()))) {
            throw new BaseException(ExceptionInfo.PASSWORD_ERROR);
        }
        account1.setPassword(null);
        account1.setRoleId(null);
        return account1;
    }

    /**
     * 注册
     *
     * @param account
     */
    @Override
    public void register(Account account) {
        if (StrUtil.isEmpty(account.getName())) {
            throw new BaseException(ExceptionInfo.NAME_EMPTY);
        }
        if (StrUtil.isEmpty(account.getPassword())) {
            throw new BaseException(ExceptionInfo.PASSWORD_EMPTY);
        }
        if (accountDao.selectById(account.getName()) != null) {
            throw new BaseException(ExceptionInfo.NAME_EXIST);
        }
        if (accountDao.selectOne(new QueryWrapper<Account>().eq("email", account.getEmail())) != null) {
            throw new BaseException(ExceptionInfo.EMAIL_EXIST);
        }
        if (!Validator.isGeneral(account.getPassword())) {
            throw new BaseException(ExceptionInfo.PASSWORD_ILLEGAL);
        }
        if ((account.getPassword().length() < PASSWORD_MIN_LENGTH) || (account.getPassword().length() > PASSWORD_MAX_LENGTH)) {
            throw new BaseException(ExceptionInfo.PASSWORD_ILLEGAL);
        }
        if (account.getName().length() > NAME_MAX_LENGTH) {
            throw new BaseException(ExceptionInfo.NAME_ILLEGAL);
        }
        account.setPassword(md5BySalt(account.getPassword()));
        accountDao.insert(account);
    }

    @Override
    public void banAccount(Account account) {

        if (accountDao.selectById(account.getName()) == null) {
            throw new BaseException(ExceptionInfo.BAN_ACCOUNT_ERROR);
        }
        if (accountDao.selectById(account.getName()).getRoleId() == RoleEnum.BAN.getId()) {
            throw new BaseException(ExceptionInfo.BAN_EXIST_ERROR);
        }
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", account.getName()).set("role_id", RoleEnum.BAN.getId());
        accountDao.update(null, updateWrapper);
    }

    @Override
    public void cancelBanAccount(Account account) {
        if (accountDao.selectById(account.getName()) == null) {
            throw new BaseException(ExceptionInfo.BAN_ACCOUNT_ERROR);
        }
        if (accountDao.selectById(account.getName()).getRoleId() == RoleEnum.NORMAL.getId()) {
            throw new BaseException(ExceptionInfo.BAN_NOT_EXIST_ERROR);
        }
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", account.getName()).set("role_id", RoleEnum.NORMAL.getId());
        accountDao.update(null, updateWrapper);
    }

    /**
     * 修改用户密码
     *
     * @param oldPassword
     * @return
     */
    @Override
    public Account updatePassword(String accountName, String oldPassword, String newPassword) {
        Account account = accountDao.selectById(accountName);
        if (!account.getPassword().equals(md5BySalt(oldPassword))) {
            throw new BaseException(ExceptionInfo.PASSWORD_ERROR);
        } else {
            UpdateWrapper<Account> updateWrapper = new UpdateWrapper<Account>();
            updateWrapper.set("password", md5BySalt(newPassword));
            updateWrapper.eq("name", accountName);
            accountDao.update(null, updateWrapper);
            return accountDao.selectById(accountName);
        }
    }

    @Override
    public void nicknameValidityCheck(String nickname) {
        if (nickname.length() > NICKNAME_MAX_LENGTH) {
            throw new BaseException(ExceptionInfo.NICKNAME_ERROR);
        }
    }

    @Override
    public void sloganValidityCheck(String slogan) {
        if (slogan.length() > SLOGAN_MAX_LENGTH) {
            throw new BaseException((ExceptionInfo.SLOGAN_ERROR));
        }
    }

    @Override
    public void updateInformation(String name, String nickname, String slogan) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<Account>();
        if (!StrUtil.isEmpty(nickname)) {
            updateWrapper.set("nickname", nickname);
        }
        if (!StrUtil.isEmpty(slogan)) {
            updateWrapper.set("slogan", slogan);
        }
        updateWrapper.eq("name", name);
        accountDao.update(null, updateWrapper);
    }

    /**
     * md5加密（加盐）
     *
     * @param password
     * @return
     */
    private static String md5BySalt(String password) {
        password = new StringBuilder(password).reverse().toString();
        StringBuilder newPassword = new StringBuilder(password.length() + 8);
        boolean flag = false;
        for (int i = 0; i < password.length(); i++) {
            if (flag) {
                flag = false;
                newPassword.append(password.charAt(i));
            } else {
                flag = true;
                newPassword.insert(0, password.charAt(i));
            }

        }
        return SaSecureUtil.md5(newPassword.toString());
    }


}
