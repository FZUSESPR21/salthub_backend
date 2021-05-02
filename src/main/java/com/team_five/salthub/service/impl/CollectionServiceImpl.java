package com.team_five.salthub.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.AccountDao;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.dao.CollectionDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.Blog;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.CollectionService;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private AccountDao accountDao;

    @Override

    public int addCollection(Collection collection) {

       if (collection.getBlogId()==null) {
            throw new BaseException(ExceptionInfo.BLOG_ID_EMPTY_ERROR);
        }
       else if(StrUtil.isEmpty(collection.getAccountName())) {
            throw new BaseException(ExceptionInfo.COLLECTION_ACCOUNT_EMPTY_ERROR);
        }
       else if(judgeCollection(collection)) {
           throw new BaseException(ExceptionInfo.COLLECTION_ALREADY_EXIST_ERROR);
       }
        blogDao.addCollectionCount(collection.getBlogId());
        return collectionDao.insert(collection);
    }

    @Override
    public int deleteCollection(Collection collection) {

        if (collection.getBlogId()==null) {
            throw new BaseException(ExceptionInfo.BLOG_ID_EMPTY_ERROR);
        }
        else if(StrUtil.isEmpty(collection.getAccountName())) {
            throw new BaseException(ExceptionInfo.COLLECTION_ACCOUNT_EMPTY_ERROR);
        }
        else if(!judgeCollection(collection)) {
            throw new BaseException(ExceptionInfo.COLLECTION_NOT_ERROR);
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("account_name",collection.getAccountName());
        map.put("blog_id",collection.getBlogId());
        blogDao.deleteCollectionCount(collection.getBlogId());
        int sum = collectionDao.deleteByMap(map);
        return sum;

    }
    @Override
    public Page<Blog> queryCollection(Collection collection,long current) {
        if(StrUtil.isEmpty(collection.getAccountName())) {
            throw new BaseException(ExceptionInfo.COLLECTION_ACCOUNT_EMPTY_ERROR);
        }
        else if(!judgeAccount(collection)) {
            throw  new BaseException(ExceptionInfo.COLLECTION_Account_NOT_ERROR);
        }
        System.out.println(collection.getAccountName());
        Page<Blog> page = new Page<>(current,10);
        List<Blog> list =blogDao.collectionBLog(collection.getAccountName());
        page.setRecords(list);
        page.setTotal(list.size());
        list.forEach(item->System.out.println(item.toString()));
        return page;

//        Page<Blog>  collectionBLog = new blogDao.collectionBLog(collection.getAccountName());
//            collectionBLog.forEach(item->item.setState(null));
//            return collectionBLog;
    }




    public boolean judgeCollection(Collection collection)
    {

        HashMap<String,Object> map = new HashMap<>();
        map.put("account_name",collection.getAccountName());
        map.put("blog_id",collection.getBlogId());
        List<Collection>  list = collectionDao.selectByMap(map);
        if (list.size()>0) {
            return true;
        }
        return false;
    }
    public boolean judgeAccount(Collection collection)
    {

        HashMap<String,Object> map = new HashMap<>();
        map.put("name",collection.getAccountName());
        List<Account>  list = accountDao.selectByMap(map);
        if (list.size()>0) {
            return true;
        }
        return false;
    }


}
