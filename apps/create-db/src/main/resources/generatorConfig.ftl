<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <classPathEntry location="${driveJar!'/sk/lib/mysql-connector-java-5.1.20-bin.jar'}"/>
    <context id="default" defaultModelType="flat" targetRuntime="MyBatis3">

        <plugin type="vip.sunke.mybatis.SkPlugin">


            <property name="projectName" value="${projectName!'后台管理系统'}"></property>
            <property name="copyright" value="${copyright!'sunke.vip'}"></property>
            <property name="packageProject" value="${packageProject!'vip.sunke.template'}"></property>
            <property name="packageAppProject" value="${packageAppProject!'vip.sunke'}"></property>
            <property name="targetProject" value="${targetProject!'/work/levo-lee.com/create'}"></property>
            <property name="dbType" value="${dbType!'mysql'}"></property>
            <property name="author" value="${author!'sunke'}"></property>
            <property name="searchTemplate" value="${searchTemplate!'Search.ftl'}"></property>
            <property name="controllerTemplate" value="${controllerTemplate!'BackController.ftl'}"></property>
            <property name="modelClass" value="${modelClass!'BaseTimeDoMain'}"></property>
            <property name="route" value="${route!'back'}"></property>
            <property name="mapperTargetProject" value="${mapperTargetProject!'/upload/createdb/create'}"></property>


            <property name="templateRoot"
                      value="${templateRoot!'/work/vip-sunke/apps/mybatis-generator/src/main/resources/template'}"></property>

            <property name="templateDir"
                      value="${templateDir!'/work/vip-sunke/apps/mybatis-generator/src/main/resources/template/layui/templates'}"></property>

            <property name="activeProfile" value="${activeProfile!'local'}"/>

        </plugin>


        <commentGenerator>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>
        <jdbcConnection driverClass="${driverClass!'org.postgresql.Driver'}"
                        connectionURL="${connectionUrl}" userId="${userId!'postgres'}"
                        password="${password!'skzz@123'}"></jdbcConnection>

        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>


        <!--生成model-->
        <javaModelGenerator targetPackage="${modelTargetPackage!'vip.sunke.template.model'}"
                            targetProject="${modelTargetProject!'/upload/createdb/create'}">
            <property name="enableSubPackages" value="false"/>
            <property name="trimStrings" value="true"/>
            <property name="rootClass" value="${modelRootClass!'vip.sunke.pubInter.BaseTimeDoMain'}"></property>
        </javaModelGenerator>


        <!--生成mapper xml-->
        <sqlMapGenerator targetPackage="resources/mapper"
                         targetProject="${mapperTargetProject!'/upload/createdb/create'}">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>
        <!--1，ANNOTATEDMAPPER：会生成使用Mapper接口+Annotation的方式创建（SQL生成在annotation中），不会生成对应的XML；-->
        <!--2，MIXEDMAPPER：使用混合配置，会生成Mapper接口，并适当添加合适的Annotation，但是XML会生成在XML中；-->
        <!--3，XMLMAPPER：会生成Mapper接口，接口完全依赖XML；-->

        <!--生成mapper 接口-->
        <javaClientGenerator targetPackage="${mapperInterfaceTargetPackage!'vip.sunke.template.dao.mapper'}"
                             targetProject="${mapperInterfaceTargetProject!'/work/levo-lee.com/create'}"
                             type="XMLMAPPER">
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>


        <#list tableList as table>
            <table tableName="${table.tableName}" domainObjectName="${table.objectName}">
                <generatedKey column="${table.keyColumn}" sqlStatement="JDBC" identity="true"/>
                <columnRenamingRule searchString="^${table.prefix}" replaceString=""/>
            </table>
        </#list>

    </context>

</generatorConfiguration>