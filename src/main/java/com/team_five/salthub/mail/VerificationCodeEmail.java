package com.team_five.salthub.mail;

/**
 * 验证码邮件类
 *
 * @date 2021/05/01
 */
public class VerificationCodeEmail extends BaseEmail {
    public static final long CODE_EXPIRE = 5 * 60 * 1000;
    private String code;

    public VerificationCodeEmail(String to, String code) {
        this.to = to;
        this.code = code;
        this.title = "[考研论坛] 邮箱激活";
    }

    @Override
    protected String getContent() {
        return "您好，您的邮箱激活码为：\n\n" + code + "\n\n有效时间为：" + (CODE_EXPIRE / (60 * 1000)) + "分钟";
    }
}
