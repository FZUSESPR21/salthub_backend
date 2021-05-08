package com.team_five.salthub.service;

import com.team_five.salthub.model.Attachment;

public interface AttachmentService {
    void attachmentIdValidityCheck(Long id);

    void deleteAttachment(Long id);

    Attachment searchAttachmentById(Long id);
}
