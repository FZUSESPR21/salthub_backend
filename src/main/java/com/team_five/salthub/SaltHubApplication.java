package com.team_five.salthub;

import com.team_five.salthub.userBasedCollaborativeFiltering.UserCF;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 启动类
 *
 * @date 2021/04/26
 */
@SpringBootApplication
@MapperScan("com.team_five.salthub.dao")
@ServletComponentScan(basePackages ="com.team_five.salthub.filter")
public class SaltHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaltHubApplication.class, args);
        UserCF.init();
    }

}
