package com.team_five.salthub.service.impl;

import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.model.TipOff;
import com.team_five.salthub.service.TipOffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TipOffServiceImplTest {
    @Autowired
    private TipOffService tipOffService;
    @Test
    void tipOffBlogTest() {
        TipOff tipOff = new TipOff();
        tipOff.setBlogId((long)10);

        Assertions.assertEquals(true,tipOffService.tipOffBlog(tipOff));
    }
}