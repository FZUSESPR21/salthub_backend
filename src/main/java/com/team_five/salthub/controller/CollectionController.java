package com.team_five.salthub.controller;


import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.CollectionService;
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
    private Collection collection=new Collection();
    @PostMapping
    public ResponseMessage addCollection(@RequestParam("id") long id) {
        collection.setBlogId(id);
        collection.setAccountName("221801310");
        return ResponseMessage.success(collectionService.addCollection(collection));
    }
}

