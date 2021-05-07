package com.team_five.salthub.controller;

import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @DeleteMapping
    public ResponseMessage deleteAttachment(@RequestParam("id") int id) {
        attachmentService.attachmentIdValidityCheck(Long.valueOf(id));

        attachmentService.deleteAttachment(Long.valueOf(id));
        return ResponseMessage.success();
    }
}
