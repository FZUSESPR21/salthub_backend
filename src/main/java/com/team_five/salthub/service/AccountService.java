package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Account;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface AccountService extends IService<Account> {

    /**
     * 登录
     *
     * @param account
     * @return
     */
    Account login(Account account);

    /**
     * 注册
     *
     * @param account
     */
    void register(Account account);

    /**
     * 修改用户密码
     *
     * @param oldPassword,newPassword
     * @return
     */
    Account updatePassword(String accountName, String oldPassword, String newPassword);
}
