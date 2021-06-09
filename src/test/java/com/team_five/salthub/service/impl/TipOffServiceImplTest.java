package com.team_five.salthub.service.impl;

import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.TipOff;
import com.team_five.salthub.service.TipOffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TipOffServiceImplTest {
    @Autowired
    TipOffService tipOffService;
    @ParameterizedTest
    @MethodSource("args3")
    void TipOffTest(TipOff tipOff, String correct) {
        try {
            this.tipOffService.tipOffBlog(tipOff);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream args3() {

        return Stream.of(

                Arguments.of(new TipOff((long)0,(long)1,(long)0),""),
                Arguments.of(new TipOff((long)0,(long)7,(long)0),""),
                Arguments.of(new TipOff((long)0,(long)10,(long)0),"")
        );
    }

    @ParameterizedTest
    @MethodSource("args4")
    void deleteTipOffTest(TipOff tipOff, String correct) {
        try {
            this.tipOffService.deleteTipOff(tipOff);
        } catch (BaseException baseException) {
            Assertions.assertEquals(correct, baseException.getMessage());
        }
    }

    static Stream args4() {

        return Stream.of(

                Arguments.of(new TipOff((long)0,(long)1,(long)0),""),
                Arguments.of(new TipOff((long)0,(long)10,(long)0),"该举报记录不存")
        );
    }
}




