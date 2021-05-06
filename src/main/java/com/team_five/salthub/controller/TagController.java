package com.team_five.salthub.controller;

import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.BlogTag;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.AccountService;
import com.team_five.salthub.service.BlogService;
import com.team_five.salthub.service.BlogTagService;
import com.team_five.salthub.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    private BlogTag blogTag = new BlogTag();
    @Autowired
    TagService tagService;

    @GetMapping("all")
    @ApiOperation(value = "查询所有标签")
    public ResponseMessage selectAllTag(){

        return ResponseMessage.success(tagService.selectTag());
    }
    @GetMapping()
    @ApiOperation(value = "查询博客标签")
    public ResponseMessage selectBlogTag(@RequestParam("id") long id){

        return ResponseMessage.success(tagService.selectBlogTag(id));
    }
}
