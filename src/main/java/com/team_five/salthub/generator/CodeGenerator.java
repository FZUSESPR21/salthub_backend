package com.team_five.salthub.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 * mybatis-plus代码生成器
 *
 * @date 2021/04/26
 */
public class CodeGenerator {
    private static final String packageParent = "com.team_five.salthub";
    private static final String[] tables = new String[]{"role", "account", "blog_module", "blog", "collection", "tag",
        "blog_tag", "first_comment", "secondary_comment", "notice", "account_interest", "permission",
        "role_permission", "tip_off", "attachment"};
    private static final boolean isOverride = false;

    private static GlobalConfig getGlobalConfig() {
        GlobalConfig config = new GlobalConfig();
        config.setOutputDir(System.getProperty("user.dir") + "/src/main/java")
            .setOpen(false)
            .setAuthor("xydf")
            .setSwagger2(true)
            .setDateType(DateType.ONLY_DATE)
            .setEntityName("%s")
            .setMapperName("%sDao")
            .setXmlName("%sMapper")
            .setServiceName("%sService")
            .setServiceImplName("%sServiceImpl")
            .setControllerName("%sController")
            .setIdType(IdType.AUTO)
            .setFileOverride(isOverride);
        return config;
    }

    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig config = new DataSourceConfig();
        config.setDbType(DbType.MYSQL)
            .setTypeConvert(new MyTypeConvert())
            .setUrl("jdbc:mysql://47.100.89.20:3306/salt_hub?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8" +
                "&useSSL=true")
            .setDriverName("com.mysql.cj.jdbc.Driver")
            .setUsername("root")
            .setPassword("lw091211");
        return config;
    }

    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setSkipView(true)
            .setCapitalMode(true)
            .setInclude(tables)
            .setEntityLombokModel(true)
            .setRestControllerStyle(true)
            .setEntityTableFieldAnnotationEnable(true)
            .setControllerMappingHyphenStyle(true)
            .setEntitySerialVersionUID(true)
            .setNaming(NamingStrategy.underline_to_camel);
        return strategy;
    }

    private static PackageConfig getPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageParent)
            .setService("service")
            .setServiceImpl("service.impl")
            .setMapper("dao")
            .setXml("dao.mapper")
            .setController("controller")
            .setEntity("model");
        return packageConfig;
    }

    private static TemplateConfig getTemplateConfig() {
        return new TemplateConfig();
    }

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        generator.setDataSource(getDataSourceConfig())
            .setStrategy(getStrategyConfig())
            .setPackageInfo(getPackageConfig())
            .setTemplateEngine(new VelocityTemplateEngine())
            .setTemplate(getTemplateConfig())
            .setGlobalConfig(getGlobalConfig())
            .execute();
    }
}
