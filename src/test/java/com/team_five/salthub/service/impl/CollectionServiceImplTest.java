package com.team_five.salthub.service.impl;

import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.constant.ModuleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CollectionServiceImplTest {
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private CollectionServiceImpl collectionService;

    @ParameterizedTest
    @MethodSource("args")
    void addCollectionTest(Collection collection, String correct) {
        try {
            this.collectionService.addCollection(collection);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream args() {
        Date date= Calendar.getInstance().getTime();
        return Stream.of(
                Arguments.of(new Collection((long)0,"221801310",(long)1),"1"),
                Arguments.of(new Collection((long)0,null,(long)1),"用户名为空"),
                Arguments.of(new Collection((long)0,"221801310",null),"博客id为空"),
                Arguments.of(new Collection((long)0,"221801310",(long)1),"您已收藏该文章")

        );
    }

    @ParameterizedTest
    @MethodSource("args2")
    void deleteCollectionTest(Collection collection, String correct) {
        try {
            this.collectionService.deleteCollection(collection);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream args2() {
        Date date= Calendar.getInstance().getTime();
        return Stream.of(
                Arguments.of(new Collection((long)0,"221801310",(long)1),"1"),
                Arguments.of(new Collection((long)0,null,(long)1),"用户名为空"),
                Arguments.of(new Collection((long)0,"221801310",null),"博客id为空"),
                Arguments.of(new Collection((long)0,"221801310",(long)1),"您未收藏该文章")

        );
    }



}