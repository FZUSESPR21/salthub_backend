package com.team_five.salthub.service.impl;

import com.team_five.salthub.model.Tag;
import com.team_five.salthub.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TagServiceImplTest {
    @Autowired
    TagService tagService;
    @Test
    void selectTag() {
//        List<Tag> list=tagService.selectTag();
//        System.out.println(list.size());
//        list.forEach(item->System.out.println(item.toString()));
        int[] a = new int[2];
        a[0]=1;
        a[1]=2;
        tagService.setTag((long)6,a);
    }
}