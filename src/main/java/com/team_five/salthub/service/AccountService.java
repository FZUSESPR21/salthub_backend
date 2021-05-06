package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    void banAccount(Account account);

    void cancelBanAccount(Account account);

    /***
     * @Description: 获取用户列表（分页）
     * @Param:
     * @return:
     * @Author: top
     * @Date: 2021/5/5
     */
    Page<Account> queryAll(Integer current);

    /**
     * 修改用户密码
     *
     * @param oldPassword,newPassword
     * @return
     */

    Account updatePassword(String accountName, String oldPassword, String newPassword);

    void nicknameValidityCheck(String nickname);

    void sloganValidityCheck(String slogan);

    void updateInformation(String name, String nickname, String slogan);
}
