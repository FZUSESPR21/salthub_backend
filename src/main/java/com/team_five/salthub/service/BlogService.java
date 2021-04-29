package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Blog;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public interface BlogService extends IService<Blog> {
        public void validityCheck(Blog blog);
        public boolean insert(Blog blog);
}
