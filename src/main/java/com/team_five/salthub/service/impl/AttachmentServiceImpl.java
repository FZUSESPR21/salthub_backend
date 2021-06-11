package com.team_five.salthub.service.impl;

import com.team_five.salthub.dao.AttachmentDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Attachment;
import com.team_five.salthub.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    @Override
    public void attachmentIdValidityCheck(Long id) {
        if (id == null) {
            throw new BaseException(ExceptionInfo.ATTACHMENT_ID_EXIST);
        }
    }

    @Override
    public void deleteAttachment(Long id) {
        attachmentDao.deleteById(id);

    }

    @Override
    public Attachment searchAttachmentById(Long id) {
        Attachment attachment = attachmentDao.selectById(id);
        if (attachment == null) {
            throw new BaseException((ExceptionInfo.ATTACHMENT_EMPTY));
        }
        return attachment;
    }
}
