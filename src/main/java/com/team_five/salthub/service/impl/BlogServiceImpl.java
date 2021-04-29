package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.constant.ModuleEnum;
import com.team_five.salthub.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Override
    public void validityCheck(Blog blog) {
        boolean flag = false;
        if(blog.getModuleId() == null){//判断模块id是否为空
            throw new BaseException(ExceptionInfo.MODULE_ID_EMPTY_ERROR);
        }
        else{//判断模块id是否属于预设模块
            for (ModuleEnum moduleEnum: ModuleEnum.values()
                 ) {
                if(blog.getModuleId().equals(moduleEnum.getId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                throw new BaseException(ExceptionInfo.MODULE_ID_ERROR);

            }
        }

        if(blog.getTitle() == null){
            throw new BaseException(ExceptionInfo.TITLE_EMPTY_ERROR);
        }
        if(blog.getTitle().length() > 256){
            throw new BaseException(ExceptionInfo.TITLE_ERROR);
        }
        if(blog.getContent() == null){
            throw new BaseException(ExceptionInfo.CONTENT_EMPTY_ERROR);
        }
        if(blog.getContent().length() > 65535){
            throw new BaseException(ExceptionInfo.CONTENT_ERROR);
        }
    }

    @Override
    public boolean insert(Blog blog) {
        blogDao.insert(blog);
        return true;
    }
}
