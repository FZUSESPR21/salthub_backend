package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.TipOff;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface TipOffService extends IService<TipOff> {
    public boolean tipOffBlog(TipOff tipOff);
    public void deleteTipOff(TipOff tipOff);
}
