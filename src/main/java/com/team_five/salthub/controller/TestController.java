package com.team_five.salthub.controller;

import com.team_five.salthub.model.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @date 2021/04/26
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseMessage hello() {
        return ResponseMessage.success("hello");
    }

}
