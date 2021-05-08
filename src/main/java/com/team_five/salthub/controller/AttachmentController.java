package com.team_five.salthub.controller;

import com.team_five.salthub.model.Attachment;
import com.team_five.salthub.model.ResponseMessage;
import com.team_five.salthub.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @DeleteMapping
    public ResponseMessage deleteAttachment(@RequestParam("id") int id) {
        attachmentService.attachmentIdValidityCheck(Long.valueOf(id));

        attachmentService.deleteAttachment(Long.valueOf(id));
        return ResponseMessage.success();
    }
    @GetMapping
    public ResponseMessage searchAttachmentById(@RequestParam("id") int id){
        attachmentService.attachmentIdValidityCheck(Long.valueOf(id));
        Attachment attachment= attachmentService.searchAttachmentById(Long.valueOf(id));
        attachment.setName("/attachment/"+attachment.getName());
        return ResponseMessage.success(attachment);
    }
}
