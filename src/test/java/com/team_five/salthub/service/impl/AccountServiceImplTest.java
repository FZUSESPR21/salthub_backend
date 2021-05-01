package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 用户业务层单元测试
 *
 * @date 2021/04/30
 */
@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;

    @ParameterizedTest
    @MethodSource("loginArgs")
    void login(Account account, String expect) {
        try {
            Account account1 = accountService.login(account);
            Assertions.assertEquals(account.getName(), account1.getName());
            assertNull(account1.getPassword());
            assertNull(account1.getRoleId());
        } catch (BaseException e) {
            Assertions.assertEquals(expect, e.getMessage());
        }
    }

    static Stream loginArgs() {
        return Stream.of(
            Arguments.of(new Account("", null, "123456", 0L, "", "",
                ""), "用户名为空"),
            Arguments.of(new Account("123456", null, "", 0L, "", "",
                ""), "密码为空"),
            Arguments.of(new Account("````", null, "123456", 0L, "", "",
                ""), "用户名不存在"),
            Arguments.of(new Account("123456", null, "123", 0L, "", "",
                ""), "密码错误"),
            Arguments.of(new Account("123456", null, "123456", 0L, "", "",
                ""), "")
        );
    }
}