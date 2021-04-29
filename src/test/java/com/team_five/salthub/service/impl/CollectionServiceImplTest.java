package com.team_five.salthub.service.impl;

import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.model.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CollectionServiceImplTest {
    @Autowired
    private CollectionDao collectionDao;
    Collection collection=new Collection((long)0,"221801310",(long)1);
    @Test
    void addCollection() {
        Assertions.assertEquals(1,collectionDao.insert(collection));
    }
}