package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.service.CollectionService;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @date 2021/04/26
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionDao, Collection> implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    @Override

    public int addCollection(Collection collection) {

       if (collection.getBlogId() == null)
        {
            throw new BaseException(ExceptionInfo.BLOD_ID_EMPTY_ERROR);
        }
       else if(collection.getAccountName() == null)
        {
            throw new BaseException(ExceptionInfo.COLLECTION_ACCOUNT_EMPTY_ERROR);
        }
       else if(judgeCollection(collection))
       {
           throw new BaseException(ExceptionInfo.COLLECTION_ALREADY_EXIST_ERROR);
       }
        return collectionDao.insert(collection);
    }

    @Override
    public int deleteCollection(Collection collection) {

        if (collection.getBlogId() == null)
        {
            throw new BaseException(ExceptionInfo.BLOD_ID_EMPTY_ERROR);
        }
        else if(collection.getAccountName() == null)
        {
            throw new BaseException(ExceptionInfo.COLLECTION_ACCOUNT_EMPTY_ERROR);
        }
        else if(!judgeCollection(collection))
        {
            throw new BaseException(ExceptionInfo.COLLECTION_NOT_ERRO);
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("account_name",collection.getAccountName());
        map.put("blog_id",collection.getBlogId());

        int sum = collectionDao.deleteByMap(map);
        return sum;

    }

    public boolean judgeCollection(Collection collection)
    {
        QueryWrapper<Collection> condition = new QueryWrapper<>();
        condition.equals(collection);
        Integer integer = collectionDao.selectCount(condition);
        if (integer>0)
        {
            return true;
        }
        return false;
    }

}
