package com.team_five.salthub.generator;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * 数据库类型转换
 *
 * @date 2021/04/26
 */
public class MyTypeConvert implements ITypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        if (fieldType.toLowerCase().contains("datetime")) {
            return DbColumnType.DATE;
        }
        return new MySqlTypeConvert().processTypeConvert(globalConfig,fieldType);
    }
}
