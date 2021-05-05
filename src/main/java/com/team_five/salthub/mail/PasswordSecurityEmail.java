package com.team_five.salthub.mail;

/**
 * 发送密码预警邮件
 *
 * @date 2021/05/04
 */
public class PasswordSecurityEmail extends BaseEmail {

    public PasswordSecurityEmail(String to) {
        this.to = to;
        this.title = "[考研论坛] 密码预警";
    }

    @Override
    protected String getContent() {
        return "您好，系统检测到您已连续输错密码超过5次，如果不是您本人操作，请立即登录账号修改密码。";
    }
}
