package com.xiaoshijie.gateway;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    @Test
    public void len() {
        System.out.println();
        int cot = 103939121;
        System.out.println(Integer.toString(cot, 32));
        System.out.println(Integer.toString(cot, 16));
        System.out.println(Integer.toString(cot, 36));
    }

    @Test
    public void makeCode() {

        String moduleName = "http";
        String packageName = "com.xiaoshijie.gateway";
        String authorName = "duoduo";
        String tableName[] = {"api_gate"};
        String projectPath = System.getProperty("user.dir") + "/";
        System.out.println(projectPath);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = testDataSource();
        mpg.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(authorName);
        gc.setOpen(false);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);


        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.xiaoshijie.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
//        strategy.setSuperControllerClass("com.xiaoshijie.common.BaseController");
        strategy.setInclude(tableName);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setLogicDeleteFieldName("isDelete");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    public static DataSourceConfig testDataSource() {
        // 数据源配置
        String dataSourceDsn = "jdbc:mysql://127.0.0.1:3306/xjs_master?useUnicode=true&useSSL=false&characterEncoding=utf8";

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSourceDsn);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("");
        return dsc;
    }

}
