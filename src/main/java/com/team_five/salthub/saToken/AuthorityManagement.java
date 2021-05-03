package com.team_five.salthub.saToken;

import cn.dev33.satoken.stp.StpInterface;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.model.constant.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 *
 * @date 2021/05/03
 */
@Component
public class AuthorityManagement implements StpInterface {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> roles = new ArrayList<>();
        roles.add(RoleEnum.getRole(accountDao.selectById((String) o).getRoleId()).getName());
        return roles;
    }
}
