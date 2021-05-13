package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.constant.ModuleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BlogServiceImplTest {

    @Autowired
    private BlogServiceImpl blogService;

    private Date date = Calendar.getInstance().getTime();

    private Blog blog = new Blog(123l, ModuleEnum.FZU.getId(), "221801310", "yyg", "yyg", date
            , 1l, 1l, 1);
    private Blog blog_1 = new Blog(123l, null, "221801310", "yyg", "yyg", date
            , 1l, 1l, 1);

    @Test
    void insert() {
        Assertions.assertEquals(true, this.blogService.insert(blog));
    }

    @Test
    void validityCheck() {
        try {
            this.blogService.validityCheck(blog_1);
        } catch (BaseException baseException) {
            Assertions.assertEquals("博客模块id为空", baseException.getMessage());
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
        Date date = Calendar.getInstance().getTime();
        return Stream.of(
                Arguments.of(new Blog(123l, ModuleEnum.FZU.getId(), "221801310", null, "yyg", date
                        , 1l, 1l, 1), "标题为空"),
                Arguments.of(new Blog(123l, ModuleEnum.FZU.getId(), "221801310", "yyg", null, date
                        , 1l, 1l, 1), "内容为空"),
                Arguments.of(new Blog(123l, null, "221801310", "yyg", "yyg", date
                        , 1l, 1l, 1), "博客模块id为空"),
                Arguments.of(new Blog(123l, -1l, "221801310", "yyg", "yyg", date
                        , 1l, 1l, 1), "模块id不属于预设模块"),
                Arguments.of(new Blog(123l, 0l, "221801310", "yyg", "yyg", date
                        , 1l, 1l, 1), "模块id不属于预设模块")
        );
    }

    @Test
    void banBlogByBlogIdTest() {

        blogService.banBlogByBlogId((long) 7);

    }

    @Test
    void selectblogbytitle() {

        Page<Blog> blogList = blogService.selectBlogByTitle("2323232323", (long) 1);


    }

    @Test
    void searchBlogByModuleId() {

    }

    @ParameterizedTest
    @MethodSource("test1")
    void searchBlogByModuleId(Long moduleId, Long current, String correct) {
        try {
            this.blogService.searchBlogByModuleId(moduleId, current);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test1() {
        return Stream.of(
                Arguments.of(ModuleEnum.FZU.getId(), 1L, ""),
                Arguments.of(ModuleEnum.OTHER_SCHOOL.getId(), 1L, ""),
                Arguments.of(ModuleEnum.MISCELLANY.getId(), 1L, ""),
                Arguments.of(ModuleEnum.GROUP_COURSE.getId(), 1L, "")
        );
    }

    @Test
    void tagIdValidityCheck() {
    }

    @ParameterizedTest
    @MethodSource("test2")
    void tagIdValidityCheck(Long tagId, String correct) {
        try {
            this.blogService.tagIdValidityCheck(tagId);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test2() {
        return Stream.of(
                Arguments.of(null, "标签id为空")
        );
    }

    @Test
    void searchBlogByTagId() {
    }

    @ParameterizedTest
    @MethodSource("test3")
    void searchBlogByTagId(Long tagId, Long current, String correct) {
        try {
            this.blogService.searchBlogByTagId(tagId, current);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test3() {
        return Stream.of(
                Arguments.of(1L, 1L, ""),
                Arguments.of(2L, 2L, "")
        );
    }

    @Test
    void searchBlogByBlogId() {
    }

    @ParameterizedTest
    @MethodSource("test4")
    void searchBlogByBlogId(Long blogId, String correct) {
        try {
            this.blogService.searchBlogByBlogId(blogId);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test4() {
        return Stream.of(
                Arguments.of(1L, ""),
                Arguments.of(2L, ""),
                Arguments.of(3L, ""),
                Arguments.of(6L, "")
        );
    }

    @Test
    void accountValidityCheck() {
    }

    @ParameterizedTest
    @MethodSource("test5")
    void accountValidityCheck(String account, String correct) {
        try {
            this.blogService.accountValidityCheck(account);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test5() {
        return Stream.of(
                Arguments.of(null, "用户名为空"),
                Arguments.of("", "用户名为空")
        );
    }

    @Test
    void searchBlogByAccount() {
    }

    @ParameterizedTest
    @MethodSource("test6")
    void searchBlogByAccount(String account, Long current, String correct) {
        try {
            this.blogService.searchBlogByAccount(account, current);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test6() {
        return Stream.of(
                Arguments.of("123456", 1L, ""),
                Arguments.of("1", 2L, "")
        );
    }

//    @Test
//    void deleteBlogByBlogId() {
//    }
//    @ParameterizedTest
//    @MethodSource("test7")
//    void deleteBlogByBlogId(Long blogId, String correct) {
//        try {
//            this.blogService.deleteBlogByBlogId(blogId);
//        } catch (BaseException baseException) {
//            Assertions.assertEquals(correct, baseException.getMessage());
//        }
//    }
//
//    static Stream test7() {
//        return Stream.of(

//        );
//    }


//    @Test
//    void updateBlogByBlogId() {
//    }
//    @ParameterizedTest
//    @MethodSource("test8")
//    void updateBlogByBlogId(Blog blog,Long blogId, String correct) {
//        try {
//            this.blogService.updateBlogByBlogId(blog,blogId);
//        } catch (BaseException baseException) {
//            Assertions.assertEquals(correct, baseException.getMessage());
//        }
//    }
//
//    static Stream test8() {
//        return Stream.of(

//        );
//    }

    @Test
    void whetherLikeBlogOrNot() {
    }

    @ParameterizedTest
    @MethodSource("test9")
    void whetherLikeBlogOrNot(Boolean flag, Long blogId, String correct) {
        try {
            this.blogService.whetherLikeBlogOrNot(flag, blogId);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream test9() {
        return Stream.of(
                Arguments.of(true, 1L, ""),
                Arguments.of(false, 1L, "")
        );
    }
}