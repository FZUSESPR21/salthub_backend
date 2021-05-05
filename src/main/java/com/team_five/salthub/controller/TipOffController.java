package com.team_five.salthub.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.model.TipOff;
import com.team_five.salthub.service.CollectionService;
import com.team_five.salthub.service.TipOffService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
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
@RequestMapping("/tipOff")

public class TipOffController {
    @Autowired
    private TipOffService tipOffService;
    private TipOff tipOff=new TipOff();
    @PostMapping
    @ApiOperation(value = "用户举报文章")
    public ResponseMessage tipOff(@RequestParam("id") long id) {
        tipOff.setBlogId(id);
        tipOffService.tipOffBlog(tipOff);
        return ResponseMessage.success();
    }
    @DeleteMapping
    @ApiOperation(value = "管理员取消举报文章")
    public ResponseMessage deleteTipOff(@RequestParam("id") long id) {
        tipOff.setBlogId(id);
        tipOffService.deleteTipOff(tipOff);
        return ResponseMessage.success();
    }

}

