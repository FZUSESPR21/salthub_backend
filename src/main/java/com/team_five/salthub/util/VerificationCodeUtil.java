package com.team_five.salthub.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;

import java.util.Random;

/**
 * 验证码生成
 *
 * @date 2021/05/01
 */
public class VerificationCodeUtil {
    private static final Random random = new Random();
    private static final String[] characters = new String[]{"a", "b", "c", "d", "e", "f", "h", "j", "k", "m", "n", "p",
        "r", "s", "t", "u", "v", "w", "x", "y", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N",
        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "3", "4", "5", "6", "7", "8"};

    /**
     * 生成普通验证码
     *
     * @param length
     * @return
     */
    public static String getCode(int length) {
        StringBuilder code = new StringBuilder(length + 8);
        while (code.length() < length) {
            code.append(characters[random.nextInt(characters.length)]);
        }
        return code.toString();
    }

    /**
     * 生成数字计算图形验证码
     *
     * @return
     */
    public static ShearCaptcha getImageCode() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        captcha.setGenerator(new MathGenerator());
        captcha.createCode();
        return captcha;
    }
}
