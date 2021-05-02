package com.team_five.salthub.mail;

import cn.hutool.extra.mail.MailAccount;

import java.nio.charset.StandardCharsets;

/**
 * 邮件基类
 *
 * @date 2021/05/01
 */
public abstract class BaseEmail {

    protected static MailAccount account = new MailAccount();

    static {
        account.setFrom("xydflxy@sina.com");
        account.setHost("smtp.sina.com");
        account.setPort(465);
        account.setUser("xydflxy@sina.com");
        account.setPass("2b0bbf79b6c62665");
        account.setAuth(true);
        account.setStarttlsEnable(true);
        account.setSslEnable(true);
        account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
        account.setSocketFactoryFallback(false);
        account.setSocketFactoryPort(465);
        account.setCharset(StandardCharsets.UTF_8);
    }

    protected String to;
    protected String title;

    protected abstract String getContent();

    public void send() {
        cn.hutool.extra.mail.MailUtil.send(account, to, title, getContent(), false);
    }
}
