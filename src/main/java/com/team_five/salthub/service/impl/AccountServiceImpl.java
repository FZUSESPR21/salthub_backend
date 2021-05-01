package com.team_five.salthub.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {

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
     * md5加密（加盐）
     *
     * @param password
     * @return
     */
    private static String md5BySalt(String password) {
        password = new StringBuilder(password).reverse().toString();
        StringBuilder newPassword = new StringBuilder(password.length() + 8);
        boolean flag = false;
        for (int i = 0;i < password.length();i++) {
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
