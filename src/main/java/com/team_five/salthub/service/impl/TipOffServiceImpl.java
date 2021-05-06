package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.BlogDao;
import com.team_five.salthub.dao.TipOffDao;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.Collection;
import com.team_five.salthub.model.TipOff;
import com.team_five.salthub.service.TipOffService;
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
public class TipOffServiceImpl extends ServiceImpl<TipOffDao, TipOff> implements TipOffService {
    @Autowired
    private TipOffDao tipOffDao;
    @Autowired
    private BlogDao blogDao;
    @Override
    public boolean tipOffBlog(TipOff tipOff) {
        long count =judgeTipOff(tipOff);
        System.out.println(count);
        if (count>0){//已有举报记录，count+1
            tipOff.setCount(count+1);//只更新一个属性，，其他属性不变
            UpdateWrapper<TipOff> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("blog_id",tipOff.getBlogId()).set("count", count+1);
            if (tipOffDao.update(null, updateWrapper)>0){
                return true;
            }
        }
        else {//没有查到该id举报信息 新增一条，设置举报数为1
            tipOff.setCount((long)1);
            return this.save(tipOff);
        }

        return false;
    }

    @Override
    public void deleteTipOff(TipOff tipOff) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.clear();
        wrapper.eq("id", tipOff.getBlogId());    //判断该id是否存在
        //通知id不存在
        if (blogDao.selectList(wrapper).size()==0) {
            throw new BaseException(ExceptionInfo.BLOG_NOT_EXIST_ERROR);
        }
        long count =judgeTipOff(tipOff);
        if (count>0){//已有举报记录，删除该条举报
            HashMap<String,Object> map = new HashMap<>();
            map.put("blog_id",tipOff.getBlogId());
            tipOffDao.deleteByMap(map);
        }
        else {
            throw  new BaseException(ExceptionInfo.TIP_OFF_EXIST_ERROR);
        }


    }

    public long judgeTipOff(TipOff tipOff)
    {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.clear();
        wrapper.eq("id", tipOff.getBlogId());    //判断该id是否存在
        //通知id不存在
        if (blogDao.selectList(wrapper).size()==0) {
            throw new BaseException(ExceptionInfo.BLOG_NOT_EXIST_ERROR);
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("blog_id",tipOff.getBlogId());
        List<TipOff> list = tipOffDao.selectByMap(map);
        if (list.size()>0) {
            return list.get(0).getCount();
        }
        return 0;
    }


}
