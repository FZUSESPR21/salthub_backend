package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.BlogModule;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xydf
 * @since 2021-05-01
 */
public interface BlogModuleService extends IService<BlogModule> {

    Object selectModule();
}
