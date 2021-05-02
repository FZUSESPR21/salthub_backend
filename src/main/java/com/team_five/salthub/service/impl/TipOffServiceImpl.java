package com.team_five.salthub.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team_five.salthub.dao.TipOffDao;
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

    public long judgeTipOff(TipOff tipOff)
    {

        HashMap<String,Object> map = new HashMap<>();
        map.put("blog_id",tipOff.getBlogId());
        List<TipOff> list = tipOffDao.selectByMap(map);
        if (list.size()>0) {
            return list.get(0).getCount();
        }
        return 0;
    }


}
