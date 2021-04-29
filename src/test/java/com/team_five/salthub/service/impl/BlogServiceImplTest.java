package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.constant.ModuleEnum;
import javafx.application.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest

class BlogServiceImplTest {

    @Autowired
    private BlogServiceImpl blogService;

    private Date date=Calendar.getInstance().getTime();

    private Blog blog =new Blog(123l, ModuleEnum.FZU.getId(),"221801310","yyg","yyg",date
            ,1l,1l,1);
    private Blog blog_1=new Blog(123l, null,"221801310","yyg","yyg",date
            ,1l,1l,1);

    @Test
    void insert() {
        Assertions.assertEquals(true,this.blogService.insert(blog));
    }

    @Test
    void validityCheck() {
        try {
            this.blogService.validityCheck(blog_1);
        } catch (BaseException baseException) {
            Assertions.assertEquals("模块id为空", baseException.getMessage());
        }
    }


    @ParameterizedTest
    @MethodSource("args")
    void fun(Blog blog, String correct) {
        try {
            this.blogService.validityCheck(blog);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream args() {
        Date date=Calendar.getInstance().getTime();
        return Stream.of(
                Arguments.of(new Blog(123l, ModuleEnum.FZU.getId(),"221801310",null,"yyg",date
                        ,1l,1l,1), "标题为空"),
                Arguments.of(new Blog(123l, ModuleEnum.FZU.getId(),"221801310","yyg",null,date
                        ,1l,1l,1), "内容为空"),
                Arguments.of(new Blog(123l, null,"221801310","yyg","yyg",date
                        ,1l,1l,1), "博客模块id为空"),
                Arguments.of(new Blog(123l, -1l,"221801310","yyg","yyg",date
                        ,1l,1l,1), "模块id不属于预设模块"),
                Arguments.of(new Blog(123l, 0l,"221801310","yyg","yyg",date
                        ,1l,1l,1), "模块id不属于预设模块")
        );
    }
}