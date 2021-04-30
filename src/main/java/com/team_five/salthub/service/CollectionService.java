package com.team_five.salthub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team_five.salthub.model.Collection;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @date 2021/04/26
 */
public interface CollectionService extends IService<Collection> {
    public int addCollection(Collection collection);
    public int deleteCollection(Collection collection);

}
