package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.BlogModuleDao;
import com.team_five.salthub.model.BlogModule;
import com.team_five.salthub.model.constant.ModuleEnum;
import com.team_five.salthub.service.BlogModuleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xydf
 * @since 2021-05-01
 */
@Service
public class BlogModuleServiceImpl extends ServiceImpl<BlogModuleDao, BlogModule> implements BlogModuleService {

    @Override
    public Object selectModule() {
        ArrayList<BlogModule> blogModuleArrayList = new ArrayList<BlogModule>();
        for (ModuleEnum moduleEnum : ModuleEnum.values()) {
            BlogModule blogModule = new BlogModule();
            blogModule.setId(moduleEnum.getId());
            blogModule.setName(moduleEnum.getName());
            blogModuleArrayList.add(blogModule);
        }
        return blogModuleArrayList;
    }
}
