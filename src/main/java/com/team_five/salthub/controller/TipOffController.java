package com.team_five.salthub.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.TipOff;
import com.team_five.salthub.service.CollectionService;
import com.team_five.salthub.service.TipOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/tipOff")
public class TipOffController {
    @Autowired
    private TipOffService tipOffService;
    private TipOff tipOff=new TipOff();
    @PostMapping
    public ResponseMessage tipOff(@RequestParam("id") long id) {
        tipOff.setBlogId(id);
        return ResponseMessage.success(tipOffService.tipOffBlog(tipOff));
    }

}

