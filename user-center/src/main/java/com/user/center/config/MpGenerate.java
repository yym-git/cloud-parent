package com.user.center.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 18:53 2022/6/16
 */
public class MpGenerate {
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        String projectPath = System.getProperty("user.dir");

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath +"/user-center" +"/src/main/java/");
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(false); // 不需要ActiveRecord特性的请改为false
        globalConfig.setEnableCache(false); // XML 二级缓存
        globalConfig.setBaseResultMap(true); // XML ResultMap
        globalConfig.setBaseColumnList(true); // XML columList
        // .setKotlin(true) 是否生成 kotlin 代码
        globalConfig.setAuthor("yym");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(
                new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @SuppressWarnings("unused")
                    public IColumnType processTypeConvert(String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setUrl(
                "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&useSSL=false&useUnicode=true&autoReconnect=true");
        autoGenerator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        //需要修改的部分
        packageConfig.setParent("com.user.center");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");

        // 分模块生成
        // packageConfig.setModuleName("bms");

        packageConfig.setXml("mapper.xml");
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg =
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        // to do nothing
                    }
                };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(
                new FileOutConfig(templatePath) {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath
                                + "/src/main/resources/mapper/"
                                + tableInfo.getEntityName()
                                + "Mapper"
                                + StringPool.DOT_XML;
                    }
                });

        /*
         * cfg.setFileCreate(new IFileCreate() {
         *
         * @Override public boolean isCreate(ConfigBuilder configBuilder,
         * FileType fileType, String filePath) { // 判断自定义文件夹是否需要创建
         * checkDir("调用默认方法创建的目录"); return false; } });
         */
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);
        // autoGenerator.setTemplate(new TemplateConfig().setXml(null));
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 表名生成策略

        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//    strategy.setTablePrefix(new String[] {"user"}); // 此处可以修改为您的表前缀,修改后包含该表前缀的表名不会出现在entity名字中
        //需要生成的表
        strategy.setInclude(new String[]{
                "broker_message"
        });
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);

        strategy.setEntityLombokModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        autoGenerator.setStrategy(strategy);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        templateConfig.setController(null);
        autoGenerator.setTemplate(templateConfig);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig templateConfig = new TemplateConfig();
        // templateConfig.setController("...");
        // templateConfig.setEntity("...");
        // templateConfig.setMapper("...");
        // templateConfig.setXml("...");
        // templateConfig.setService("...");
        // templateConfig.semappertServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // autoGenerator.setTemplate(templateConfig);

        // 执行生成
        autoGenerator.execute();

        // 打印注入设置【可无】
        // System.err.println(autoGenerator.getCfg().getMap().get("abc"));
    }
}
