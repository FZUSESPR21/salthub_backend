package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Tag;
import com.team_five.salthub.model.TipOff;
import com.team_five.salthub.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TagServiceImplTest {
    @Autowired
    TagService tagService;
    @Test
    @ParameterizedTest
    @MethodSource("args3")
    void TipOffTest(Long blogId, int[] tag,String correct) {
        try {
            this.tagService.setTag(blogId,tag);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream args3() {

        return Stream.of(

                Arguments.of((long)900, new int[]{1, 2, 3},"该博客不存在"),
                Arguments.of((long)9, new int[]{1, 2, 90},"输入的标签有误"),
                Arguments.of((long)9, new int[]{1},"")
        );
    }
    }
