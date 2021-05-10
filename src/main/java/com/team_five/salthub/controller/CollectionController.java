package com.team_five.salthub.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.CollectionService;
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
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    private Collection collection = new Collection();
    @ApiOperation(value = "用户收藏文章")
    @PostMapping
    public ResponseMessage addCollection(@RequestParam("id") long id) {
        collection.setBlogId(id);
        collection.setAccountName(StpUtil.getLoginIdAsString());
        return ResponseMessage.success(collectionService.addCollection(collection));
    }
    @ApiOperation(value = "用户删除收藏")
    @DeleteMapping
    public ResponseMessage deleteCollection( @RequestParam("id") long id) {
        collection.setAccountName(StpUtil.getLoginIdAsString());
        collection.setBlogId(id);
        return ResponseMessage.success(collectionService.deleteCollection(collection));
    }
    @ApiOperation(value = "用户查询收藏")
    @GetMapping
    public ResponseMessage queryCollection(@RequestParam("current") long current) {
        collection.setAccountName(StpUtil.getLoginIdAsString());
        System.out.println(collection.getAccountName());
        return ResponseMessage.success(collectionService.queryCollection(collection,current));
    }
}

