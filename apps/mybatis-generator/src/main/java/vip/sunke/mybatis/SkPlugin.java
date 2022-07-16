package vip.sunke.mybatis;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;
import vip.sunke.common.FileUtil;
import vip.sunke.common.StringUtil;
import vip.sunke.common.YXDate;
import vip.sunke.mybatis.parser.ColumnRemark;
import vip.sunke.mybatis.parser.ColumnValue;
import vip.sunke.mybatis.parser.IParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunke
 * @Date 2017/11/28 14:48
 * @description
 */

public class SkPlugin extends PluginAdapter {

    public static String DB_TYPE_MYSQL = "mysql";
    public static String DB_TYPE_MSSERVER = "msserver";
    public static String DB_TYPE_ORACLE = "oracle";

    public static String dbType = DB_TYPE_MYSQL;

    // 注释生成器
    private CommentGeneratorConfiguration commentCfg;

    public static String getFormatDate2String(Date date, String formatStr) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

        return sdf.format(date);

    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath;
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

    /**
     * 返回数据库的所有表名和注释
     *
     * @return
     */
    public List<TableComment> getTableCommentList() {

        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {

            String db = getConnection().getCatalog();

            conn = getConnection();
            stat = conn.createStatement();

            String sql = "select table_name,table_comment,create_time from information_schema.tables where table_schema = '" + db + "' order by table_name asc;";
            resultSet = stat.executeQuery(sql);

            List<TableComment> tableList = new ArrayList<TableComment>();
            while (resultSet.next()) {
                tableList.add(new TableComment(resultSet.getString("table_name"), resultSet.getString("table_comment")));

            }

            return tableList;


        } catch (Exception e) {
            // e.printStackTrace();
        } finally {

        }

        return null;


    }

    public List<String> getTables() {
        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {

            conn = getConnection();
            stat = conn.createStatement();
            resultSet = stat.executeQuery("show tables;");

            List<String> tableList = new ArrayList<String>();
            while (resultSet.next()) {
                tableList.add(resultSet.getString(1));

            }

            return tableList;


        } catch (Exception e) {

        } finally {


        }


        return null;


    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {

        try {
            List<TableComment> tableList = getTableCommentList();

            // String tableObj="";
            String varTable = "";
            for (TableComment table : tableList) {

                //  tableObj=BeanName.getTableObjectByDbTable(table);
                varTable = table.getName().toUpperCase();
                FieldEnum field = new FieldEnum();
                field.setName(varTable + "_FULL_DETAIL");
                field.setDesc(table.getComment() + "详情");

                SkDefaultShellCallback.addFullKeyMap(field.getName(), field);

                field = new FieldEnum();
                field.setName(varTable + "_FULL_LIST");
                field.setDesc(table.getComment() + "列表");
                SkDefaultShellCallback.addFullKeyMap(field.getName(), field);


            }
        } catch (Exception e) {

        }

        return super.contextGenerateAdditionalJavaFiles();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);


        // 设置默认的注释生成器
        commentCfg = new CommentGeneratorConfiguration();
        commentCfg.setConfigurationType(SkGenCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(commentCfg);
        // 支持oracle获取注释#114
        context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
    }

    @Override
    public void setProperties(Properties properties) {

        super.setProperties(properties);

        BeanName.initPro(properties);

        clearOldDir(BeanName.getTargetProject());

        SkDefaultShellCallback.enumMap = new HashMap<>();
        SkDefaultShellCallback.fullKeyMap = new HashMap<>();

    }


    private boolean isBigType(String jdbcTypeName) {

        return "LONGVARCHAR".equalsIgnoreCase(jdbcTypeName)
                || "CLOB".equalsIgnoreCase(jdbcTypeName)
                || "BLOB".equalsIgnoreCase(jdbcTypeName);

    }

    private void clearOldDir(String dir) {

        delFolder(dir);

    }


    private Connection getConnection() throws SQLException {
        ConnectionFactory connectionFactory;
        if (this.getContext().getJdbcConnectionConfiguration() != null) {
            connectionFactory = new JDBCConnectionFactory(this.getContext().getJdbcConnectionConfiguration());
        } else {
            connectionFactory = ObjectFactory.createConnectionFactory(this.getContext());
        }

        return connectionFactory.getConnection();
    }


    private void setRemark(IntrospectedTable introspectedTable) {

        try {

            if (!StringUtil.isEmpty(introspectedTable.getRemarks())) {
                return;
            }

            String remarkSql = "";

            if (dbType == DB_TYPE_MSSERVER) {
                remarkSql = "SELECT DISTINCT CAST (d.name AS VARCHAR (500)) AS name,CAST (f.value AS VARCHAR (500)) AS COMMENT FROM syscolumns a LEFT JOIN systypes b ON a.xusertype=b.xusertype INNER JOIN sysobjects d ON a.id=d.id AND d.xtype='U' AND d.name<> 'dtproperties' LEFT JOIN syscomments e ON a.cdefault=e.id LEFT JOIN sys.extended_properties g ON a.id=G.major_id AND a.colid=g.minor_id LEFT JOIN sys.extended_properties f ON d.id=f.major_id AND f.minor_id= 0" +

                        " where  d.name like '" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "'";
            } else {
                remarkSql = "show table status like '" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "'";
            }
            Statement statement = getConnection().createStatement();

            ResultSet rs = statement.executeQuery(remarkSql);
            while (rs.next()) {

                // System.out.println(rs.getString(1));
                // System.out.println(rs.getString(2));
                introspectedTable.setRemarks(rs.getString("COMMENT"));
            }
            rs.close();
            statement.close();


        } catch (Exception e) {


        }


    }


    /**
     * 创建类
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        setRemark(introspectedTable);

        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();


        // 获取实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        String entityName = entityType.getShortName();
        // import实体类
        interfaze.addImportedType(new FullyQualifiedJavaType(BeanName.getFullModelExtClassName(entityName)));

        interfaze.addImportedType(new FullyQualifiedJavaType(BeanName.getFullBaseMapperClassName()));//导入IBaseMapper

        interfaze.addImportedType(new FullyQualifiedJavaType(BeanName.getFullSearchExtClassName(entityName)));

        interfaze.addSuperInterface(new FullyQualifiedJavaType(BeanName.getShortBaseMapperClassName() + "<" + BeanName.getShortModelExtClassName(entityName) + "," + BeanName.getShortSearchExtClassName(entityName) + "," + keyType + ">"));


        List<ColumnRemark> columnRemarkList = getColumnRemarkListByTable(introspectedTable);

        createMapperExt(introspectedTable, keyType);
        createModelDesc(introspectedTable, keyType, columnRemarkList);//生成ModelExt
        createModelExt(introspectedTable, keyType, columnRemarkList);//生成ModelDesc

        createDO(introspectedTable, keyType);//生成DO

        createDao(introspectedTable, keyType);//生成dao
        createService(introspectedTable, keyType);//生成service
        createSearch(introspectedTable, columnRemarkList, keyType);//生成search

        createController(entityName, introspectedTable.getRemarks(), keyType, columnRemarkList);//生成controller


        createDomainVO(introspectedTable, keyType, columnRemarkList);//创建DomainVO
        createDetailDomainVO(introspectedTable, keyType, columnRemarkList);//创建DetailDomainVO
        createDetailVO(introspectedTable, keyType);//创建DetailVO
        createListVO(introspectedTable, keyType);//创建ListVO

        createDTO(introspectedTable, keyType, columnRemarkList);//创建DTO
        createWebSite(introspectedTable, columnRemarkList, keyType);//创建页面


        createExceptionMessage(introspectedTable);//生成错误码


        return true;
    }

    /**
     * 生成每个表错误码
     *
     * @param introspectedTable
     */
    private void createExceptionMessage(IntrospectedTable introspectedTable) {

        try {
            String tableName = BeanName.getFirstUpperCase(getEntityName(introspectedTable));

            String tableDesc = introspectedTable.getRemarks();

            String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
            Map<String, Object> dataMap = new HashMap<>();

            setComment(dataMap, tableName, "ExceptionMessage", tableDesc);
            setBaseInfo(dataMap, tableName, keyType, tableDesc);
            dataMap.put("package", BeanName.getExceptionPackage());
            dataMap.put("tableName", tableName);
            dataMap.put("tableDesc", tableDesc);
            dataMap.put("tableIndex", Main.getTableIndex());


            BeanName.getFreeMarkerUtil().printFile("ExceptionMessage.ftl", dataMap, BeanName.getExceptionMessage(tableName) + ".java", BeanName.getExceptionMessagePath(), BeanName.getTempInPubJavaDir() + "/exception");
        } catch (Exception e) {

        }


    }


    /**
     * 生成mapperExt
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createMapperExt(IntrospectedTable introspectedTable, String keyType) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        Map<String, Object> dataMap = new HashMap<>();
        try {

            setComment(dataMap, entityName, "MapperExt", remark);
            setBaseInfo(dataMap, entityName, keyType, remark);
            dataMap.put("package", BeanName.getDaoMapperExtPackage());
            dataMap.put("mapperClass", BeanName.getFullMapperClassName(entityName));
            dataMap.put("shortMapperClassExt", BeanName.getShortMapperExtClassName(entityName));
            dataMap.put("shortMapperClass", BeanName.getShortMapperClassName(entityName));


            BeanName.getFreeMarkerUtil().printFile("MapperExt.ftl", dataMap, BeanName.getShortMapperExtClassName(entityName) + ".java", BeanName.getMapperExtPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }
        try {
            dataMap.put("namespace", BeanName.getFullMapperExtClassName(entityName));
            dataMap.put("table", introspectedTable.getFullyQualifiedTableNameAtRuntime());
            dataMap.put("searchWhere", BeanName.getSearchMapperWhereSql());
            dataMap.put("searchOrder", BeanName.getSearchMapperOrderBy());
            BeanName.getFreeMarkerUtil().printFile("MapperExtXml.ftl", dataMap, BeanName.getShortMapperExtClassName(entityName) + ".xml", BeanName.getMapperExtXmlPath(), BeanName.getTempInPubJavaDir() + "/template");


        } catch (Exception e) {

        }


    }


    /**
     * 生成Model Desc
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createModelDesc(IntrospectedTable introspectedTable, String keyType, List<ColumnRemark> columnRemarkList) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Desc", remark);
            dataMap.put("package", BeanName.getDescPackage());
            dataMap.put("modelDesc", BeanName.getShortModelDescClassName(entityName));
            dataMap.put("enumsPackage", BeanName.getPackageEnum());

            List<ColumnRemark> columnList = new ArrayList<>();
            if (columnRemarkList != null && columnRemarkList.size() > 0) {


                columnRemarkList.forEach(columnRemark -> {

                    if (columnRemark.getValueList() != null && columnRemark.getValueList().size() > 0) {
                        columnList.add(columnRemark);
                        return;
                    }


                });


            }

            dataMap.put("columnList", columnList);
            setBaseInfo(dataMap, entityName, keyType, remark);
            BeanName.getFreeMarkerUtil().printFile("Desc.ftl", dataMap, BeanName.getShortModelDescClassName(entityName) + ".java", BeanName.getDescPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }

    /**
     * 生成Model Ext
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createModelExt(IntrospectedTable introspectedTable, String keyType, List<ColumnRemark> columnRemarkList) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Ext", remark);
            dataMap.put("package", BeanName.getExtPackage());
            dataMap.put("modelExt", BeanName.getShortModelExtClassName(entityName));
            dataMap.put("enumsPackage", BeanName.getPackageEnum());

            List<ColumnRemark> columnList = new ArrayList<>();
            if (columnRemarkList != null && columnRemarkList.size() > 0) {


                columnRemarkList.forEach(columnRemark -> {

                    if (columnRemark.getValueList() != null && columnRemark.getValueList().size() > 0) {
                        columnList.add(columnRemark);
                        return;
                    }


                });


            }

            dataMap.put("columnList", columnList);
            setBaseInfo(dataMap, entityName, keyType, remark);
            BeanName.getFreeMarkerUtil().printFile("Ext.ftl", dataMap, BeanName.getShortModelExtClassName(entityName) + ".java", BeanName.getExtPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }


    /**
     * 生成Model DO
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createDO(IntrospectedTable introspectedTable, String keyType) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DO", remark);
            dataMap.put("package", BeanName.getDOPackage());
            dataMap.put("modelDO", BeanName.getShortDOClassName(entityName));
            setBaseInfo(dataMap, entityName, keyType, remark);
            dataMap.put("extModelClass", BeanName.getFullModelExtClassName(entityName));
            dataMap.put("shortExtModel", BeanName.getShortModelExtClassName(entityName));

            BeanName.getFreeMarkerUtil().printFile("DO.ftl", dataMap, BeanName.getShortDOClassName(entityName) + ".java", BeanName.getDOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }


    /**
     * 生成DetailVO
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createDetailVO(IntrospectedTable introspectedTable, String keyType) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DetailVO", remark);
            dataMap.put("package", BeanName.getVOPackage());


            setBaseInfo(dataMap, entityName, keyType, remark + "DetailVO");


            BeanName.getFreeMarkerUtil().printFile("DetailVO.ftl", dataMap, BeanName.getShortDetailVOClassName(entityName) + ".java", BeanName.getVOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }


    /**
     * 生成ListVO
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createListVO(IntrospectedTable introspectedTable, String keyType) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "ListVO", remark);
            dataMap.put("package", BeanName.getVOPackage());
            // dataMap.put("remark", remark+"ListVO");

            setBaseInfo(dataMap, entityName, keyType, remark + "ListVO");


            BeanName.getFreeMarkerUtil().printFile("ListVO.ftl", dataMap, BeanName.getShortListVOClassName(entityName) + ".java", BeanName.getVOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }


    /**
     * 生成Model DetailDomainVO
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createDetailDomainVO(IntrospectedTable introspectedTable, String keyType, List<ColumnRemark> columnRemarkList) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DetailDomainVO", remark);
            dataMap.put("package", BeanName.getVOPackage());
            dataMap.put("baseVOClass", BeanName.getFullBaseVOClass());
            dataMap.put("shortDetailDomainVO", BeanName.getShortDetailDomainVOClassName(entityName));
            dataMap.put("shortBaseVOClass", BeanName.getShortBaseVOClass());
            dataMap.put("columnList", columnRemarkList);
            setBaseInfo(dataMap, entityName, keyType, remark + "DetailDomainVO");
            BeanName.getFreeMarkerUtil().printFile("DetailDomainVO.ftl", dataMap, BeanName.getShortDetailDomainVOClassName(entityName) + ".java", BeanName.getVOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }


    /**
     * 生成Model VO
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createDomainVO(IntrospectedTable introspectedTable, String keyType, List<ColumnRemark> columnRemarkList) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DomainVO", remark);
            dataMap.put("package", BeanName.getVOPackage());
            dataMap.put("baseVOClass", BeanName.getFullBaseVOClass());
            dataMap.put("shortVO", BeanName.getShortVOClassName(entityName));
            dataMap.put("shortBaseVOClass", BeanName.getShortBaseVOClass());
            dataMap.put("columnList", columnRemarkList);
            setBaseInfo(dataMap, entityName, keyType, remark + "DomainVO");
            BeanName.getFreeMarkerUtil().printFile("DomainVO.ftl", dataMap, BeanName.getShortVOClassName(entityName) + ".java", BeanName.getVOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
        }

    }

    /**
     * 生成Model DTO
     *
     * @param introspectedTable
     * @param keyType
     */
    private void createDTO(IntrospectedTable introspectedTable, String keyType, List<ColumnRemark> columnRemarkList) {

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();
        entityName = BeanName.getShortModelClassName(entityName);
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DomainDTO", remark);
            dataMap.put("package", BeanName.getDTOPackage());
            dataMap.put("columnList", columnRemarkList);
            // dataMap.put("remark", remark+"ModelDTO");
            setBaseInfo(dataMap, entityName, keyType, remark + "ModelDomainDTO");
            BeanName.getFreeMarkerUtil().printFile("DomainDTO.ftl", dataMap, BeanName.getShortDomainDTOClassName(entityName) + ".java", BeanName.getDTOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {


        }
        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DTO", remark);
            dataMap.put("package", BeanName.getDTOPackage());
            dataMap.put("columnList", columnRemarkList);
            // dataMap.put("remark", remark+"ModelDTO");
            setBaseInfo(dataMap, entityName, keyType, remark + "ModelDTO");
            BeanName.getFreeMarkerUtil().printFile("DTO.ftl", dataMap, BeanName.getShortDTOClassName(entityName) + ".java", BeanName.getDTOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {


        }





        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DomainPageDTO", remark);
            dataMap.put("package", BeanName.getDTOPackage());
            dataMap.put("columnList", columnRemarkList);
            //  dataMap.put("extendsClass",BeanName.getShortDomainPageDTOClassName(entityName));
            //  dataMap.put("remark", remark+"PageDTO");
            setBaseInfo(dataMap, entityName, keyType, remark + "DomainPageDTO");
            BeanName.getFreeMarkerUtil().printFile("DomainPageDTO.ftl", dataMap, BeanName.getShortDomainPageDTOClassName(entityName) + ".java", BeanName.getDTOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {


        }

        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "PageDTO", remark);
            dataMap.put("package", BeanName.getDTOPackage());
            dataMap.put("extendsClass", BeanName.getShortDomainPageDTOClassName(entityName));
            dataMap.put("columnList", columnRemarkList);
            //  dataMap.put("remark", remark+"PageDTO");
            setBaseInfo(dataMap, entityName, keyType, remark + "PageDTO");
            BeanName.getFreeMarkerUtil().printFile("PageDTO.ftl", dataMap, BeanName.getShortPageDTOClassName(entityName) + ".java", BeanName.getDTOPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {


        }


    }


    /**
     * 解析remark
     *
     * @param introspectedTable
     * @return
     */
    private List<ColumnRemark> getSqlServerColumnRemarkListByTable(IntrospectedTable introspectedTable) {

        try {
            String table = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            String entityName = getEntityName(introspectedTable);
            String sql = "SELECT\n" +
                    "B.name,\n" +
                    "\tconvert(varchar(1000), C.\n" +
                    "VALUE)\n" +
                    "\tAS REMARKS\n" +
                    "FROM\n" +
                    "\tsys.tables A\n" +
                    "INNER JOIN sys.columns B ON B.object_id = A.object_id\n" +
                    "LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id\n" +
                    "AND C.minor_id = B.column_id\n" +
                    "WHERE\n" +
                    "\tA.name = '" + table + "'";

            Statement statement = getConnection().createStatement();

            ResultSet rs = statement.executeQuery(sql);

            List<ColumnRemark> remarkList = new ArrayList<ColumnRemark>();


            while (rs.next()) {

                ColumnRemark columnRemark = new ColumnRemark();
                String descName = rs.getString(2);
                String column = rs.getString(1);
                if (StringUtil.isEmpty(descName)) {
                    descName = column;
                }
                columnRemark.setDescName(descName);
                columnRemark.setDbName(column);
                columnRemark.setShow(true);
                if (!column.equalsIgnoreCase("id")) {
                    columnRemark.setShowListPage(true);
                }
                columnRemark.setShowDetailPage(false);


                columnRemark.parse(descName);
                remarkList.add(columnRemark);
            }
            rs.close();
            statement.close();

            List<IntrospectedColumn> columns = introspectedTable.getAllColumns();

            for (ColumnRemark columnRemark : remarkList) {


                for (IntrospectedColumn column : columns) {
                    if (columnRemark.getDbName().equalsIgnoreCase(column.getActualColumnName())) {

                        columnRemark.setDefaultValue(column.getDefaultValue());
                        columnRemark.setName(column.getJavaProperty());
                        String jdbcTypeName = column.getJdbcTypeName();
                        if ("INTEGER".equalsIgnoreCase(jdbcTypeName)) {
                            columnRemark.setSearchFlag(SearchTypeEnum.NUMBER.getType());

                        } else if ("VARCHAR".equalsIgnoreCase(jdbcTypeName)) {
                            columnRemark.setSearchFlag(SearchTypeEnum.LIKE.getType());

                        } else if ("DECIMAL".equalsIgnoreCase(jdbcTypeName)) {
                            columnRemark.setSearchFlag(SearchTypeEnum.NUMBER.getType());


                        } else if ("TIMESTAMP".equalsIgnoreCase(jdbcTypeName)) {
                            columnRemark.setSearchFlag(SearchTypeEnum.DATE.getType());

                        }


                        // columnRemark.setDbName(column.getActualColumnName());
                        columnRemark.setJdbcType(column.getJdbcTypeName());
                        columnRemark.setJavaType(column.getFullyQualifiedJavaType().getShortName());
                        columnRemark.setEntityName(entityName);
                        column.setRemarks(columnRemark.getDescName());
                        break;
                    }


                }


            }


            return remarkList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;


    }


    private List<ColumnRemark> getColumnRemarkListByTable(IntrospectedTable introspectedTable) {

        if (dbType == DB_TYPE_MSSERVER) {

            return getSqlServerColumnRemarkListByTable(introspectedTable);
        }


        String entityName = getEntityName(introspectedTable);

        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        if (columns == null || columns.size() == 0) {
            return null;
        }


        // ParserFactory parserFactory = ParserFactory.getInstance();

        String remark = null;
        ColumnRemark columnRemark = null;

        IParser parser = null;
        List<ColumnRemark> remarkList = new ArrayList<ColumnRemark>();


        for (IntrospectedColumn column : columns) {
            remark = column.getRemarks();
            if (remark == null || "".equalsIgnoreCase(remark)) {
                continue;
            }
            columnRemark = new ColumnRemark();
            columnRemark.setDefaultValue(column.getDefaultValue());
            columnRemark.setName(column.getJavaProperty());
            columnRemark.setDbName(column.getActualColumnName());
            columnRemark.setJdbcType(column.getJdbcTypeName());
            columnRemark.setJavaType(column.getFullyQualifiedJavaType().getShortName());
            columnRemark.setEntityName(entityName);
            columnRemark.parse(remark);

           /* parser = parserFactory.getParser(columnRemark.getInputType());
            if (parser != null) {
                columnRemark.setInputHtml(parser.parse(columnRemark));
            }*/
            remarkList.add(columnRemark);
            //说明要enums

            List<ColumnValue> columnValueList = columnRemark.getValueList();
            if (columnValueList != null && columnValueList.size() > 0) {

                String fieldName = BeanName.getFirstUpperCase(columnRemark.getEntityName()) + BeanName.getFirstUpperCase(columnRemark.getName()) + "@@" + columnRemark.getDescName();

                List<FieldEnum> fieldEnumList = new ArrayList<>();
                FieldEnum fieldEnum = null;
                for (ColumnValue columnValue : columnValueList) {
                    fieldEnum = new FieldEnum();
                    fieldEnum.setDesc(columnValue.getDesc());
                    fieldEnum.setType(columnValue.getValue());
                    fieldEnum.setName(columnValue.getEnName());
                    fieldEnum.setNumber("INTEGER".equalsIgnoreCase(columnRemark.getJdbcType()));
                    fieldEnumList.add(fieldEnum);
                }
                SkDefaultShellCallback.addFiledMap(fieldName, fieldEnumList);

            }


        }
        return remarkList;
    }

    /**
     * 创建页面
     *
     * @param introspectedTable
     * @param
     */
    private void createWebSite(IntrospectedTable introspectedTable, List<ColumnRemark> columnRemarkList, String keyType) {

        if (introspectedTable == null) {
            return;
        }
        String entityName = BeanName.getFirstLowerCase(getEntityName(introspectedTable));

        String tableRemarks = introspectedTable.getRemarks();


        if (columnRemarkList == null || columnRemarkList.size() == 0) {
            return;
        }


        List<ColumnRemark> showList = new ArrayList<ColumnRemark>();

        //    List<ColumnRemark> showListPageList = new ArrayList<ColumnRemark>();
        for (ColumnRemark columnRemark : columnRemarkList) {
            if (columnRemark.isShow()) {
                showList.add(columnRemark);
            }

          /*  if (columnRemark.isShowListPage()) {
                showListPageList.add(columnRemark);
            }*/
        }


        String pageDir = BeanName.getBackPageDir() + File.separator + entityName;
        createDir(pageDir);


        String jsDir = BeanName.getBackJsDir() + File.separator + entityName;

        createDir(jsDir);


        createEntityJs(jsDir, showList, entityName);


        //  pageDir = "/work/vip-sunke/apps/template/template-web/src/main/resources/templates/back/" + entityName;

        createAddPage(pageDir, showList, entityName);
        createDetailPage(pageDir, columnRemarkList, entityName);
        createListPage(pageDir, showList, entityName, tableRemarks);
        createModPage(pageDir, showList, entityName);
        addMenuData(entityName, tableRemarks);


    }

    private void createEntityJs(String jsDir, List<ColumnRemark> columnRemarkList, String entityName) {


        if (!BeanName.existEntityJs()) {
            return;
        }

        String js = entityName + ".js";

        Map<String, Object> dataMap = new HashMap<String, Object>();


        List<ColumnRemark> fileList = new ArrayList<>();


        if (columnRemarkList != null && columnRemarkList.size() > 0) {


            columnRemarkList.forEach(columnRemark -> {

                if (columnRemark.getInputType() == InputTypeEnum.FILE.getType()
                        || columnRemark.getInputType() == InputTypeEnum.FILE_MULTIPLE.getType()
                ) {

                    fileList.add(columnRemark);
                    return;

                }


            });

        }


        dataMap.put("fileList", fileList);
        dataMap.put("entityName", entityName);


        FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();


        try {
            freeMarkerUtil.printFile("entityJs.ftl", dataMap, js, jsDir, BeanName.getTempEntityJsDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void addMenuData(String entityName, String tableRemarks) {

        MenuDomain menuDomain = new MenuDomain(entityName, tableRemarks + "管理");
        SubMenuDomain subMenu = new SubMenuDomain("/back/" + entityName + "/list", tableRemarks + "列表", BeanName.getFirstLowerCase(entityName) + "/list");
        menuDomain.addSubMenu(subMenu);
        Main.addMenu(menuDomain);
    }


    private void setPageMapData(Map<String, Object> dataMap, String entityName) {

        dataMap.put("entityName", entityName);
        dataMap.put("menuName", entityName);
        dataMap.put("action", "action");
        dataMap.put("listPage", BeanName.getListPage(entityName));
        dataMap.put("contextPath", "pageContext.request.contextPath");
        dataMap.put("NumberUtil", BeanName.getPackageCommonProject() + ".NumberUtil");
        dataMap.put("manageController", BeanName.getFullBaseControllerClassName());


    }


    /**
     * 生成添加页面
     *
     * @param pageDir
     * @param columnRemarkList
     */


    /**
     * 生成列表页面
     *
     * @param pageDir
     * @param columnRemarkList
     */
    private void createListPage(String pageDir, List<ColumnRemark> columnRemarkList, String entityName, String tableRemarks) {
        try {
            String tempPage = BeanName.getTemplatePageDir();

            String page = "list" + BeanName.createPageSuffix();

            Map<String, Object> dataMap = new HashMap<String, Object>();


            dataMap.put("list", columnRemarkList);
            //  dataMap.put("menuName", entityName);
            dataMap.put("tableRemarks", tableRemarks);


            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

            TemplateHashModel staticModels = wrapper.getStaticModels();
            TemplateHashModel searchTypeUtils =
                    (TemplateHashModel) staticModels.get("vip.sunke.mybatis.SearchTypeEnum");


            //   dataMap.put("addPage", "/back/" + entityName + "/add");

            dataMap.put("searchTypeUtils", searchTypeUtils);


            setPageMapData(dataMap, entityName);
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile(BeanName.getListPageTemplateName(), dataMap, page, pageDir, tempPage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createAddPage(String pageDir, List<ColumnRemark> columnRemarkList, String entityName) {


        try {
            String tempPage = BeanName.getTemplatePageDir();

            String page = "add" + BeanName.createPageSuffix();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("list", columnRemarkList);
            setPageMapData(dataMap, entityName);
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile(BeanName.getAddPageTemplateName(), dataMap, page, pageDir, tempPage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成详情页面
     *
     * @param pageDir
     * @param columnRemarkList
     */
    private void createDetailPage(String pageDir, List<ColumnRemark> columnRemarkList, String entityName) {
        try {

            String tempPage = BeanName.getTemplatePageDir();
            String page = "detail" + BeanName.createPageSuffix();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("list", columnRemarkList);
            setPageMapData(dataMap, entityName);
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile(BeanName.getDetailPageTemplateName(), dataMap, page, pageDir, tempPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成修改页面
     *
     * @param pageDir
     * @param columnRemarkList
     */
    private void createModPage(String pageDir, List<ColumnRemark> columnRemarkList, String entityName) {
        try {

            String tempPage = BeanName.getTemplatePageDir();
            String page = "mod" + BeanName.createPageSuffix();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("list", columnRemarkList);
            setPageMapData(dataMap, entityName);
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile(BeanName.getModPageTemplateName(), dataMap, page, pageDir, tempPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createController(String entityName, String remark, String keyType, List<ColumnRemark> columnRemarkList) {

        if (BeanName.isApiController()) {
            createApiController(entityName, remark, keyType);
        } else {
            createBackController(entityName, remark, keyType, columnRemarkList);
        }


    }

    private void createApiController(String entityName, String remark, String keyType) {


        if (StringUtil.isEmpty(remark)) {
            remark = entityName;
        }

        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Controller", remark);
            dataMap.put("package", BeanName.getApiControllerPackage());
            setBaseInfo(dataMap, entityName, keyType, remark);

            dataMap.put("controllerName", BeanName.getFirstLowerCase(entityName) + "RestfulController");

            dataMap.put("route", BeanName.getRouteByEntityName(entityName));
            dataMap.put("baseView", BeanName.getBaseViewByEntityName(entityName));
            dataMap.put("controllerClass", BeanName.getShortControllerClassName(entityName));
            dataMap.put("apiTags", BeanName.getProjectName() + "-" + remark + "相关");
            dataMap.put("modelName", remark);
            dataMap.put("apiDesc", remark + "相关接口");

            BeanName.getFreeMarkerUtil().printFile("RestfulController.ftl", dataMap, BeanName.getShortControllerClassName(entityName) + ".java", BeanName.getApiControllerPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }
    }


    private void createBackController(String entityName, String remark, String keyType, List<ColumnRemark> columnRemarkList) {


        if (StringUtil.isEmpty(remark)) {
            remark = entityName;
        }

        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Controller", remark);
            dataMap.put("package", BeanName.getControllerPackage());
            setBaseInfo(dataMap, entityName, keyType, remark);


            dataMap.put("controllerName", BeanName.getFirstLowerCase(entityName) + "Controller");

            dataMap.put("route", BeanName.getRouteByEntityName(entityName));
            dataMap.put("baseView", BeanName.getBaseViewByEntityName(entityName));
            dataMap.put("controllerClass", BeanName.getShortControllerClassName(entityName));

            dataMap.put("menuModelName", BeanName.getFirstLowerCase(entityName));
            dataMap.put("menuPageName", BeanName.getFirstLowerCase(entityName) + "/list");
            dataMap.put("menuModel", remark + "管理");
            dataMap.put("pageName", remark);
            dataMap.put("apiTags", remark + "相关");
            dataMap.put("modelName", remark);
            dataMap.put("apiDesc", remark + "相关接口");
            dataMap.put("enumsPackage", BeanName.getPackageEnum());

            List<ColumnRemark> columnList = new ArrayList<>();
            if (columnRemarkList != null && columnRemarkList.size() > 0) {


                columnRemarkList.forEach(columnRemark -> {

                    if (columnRemark.getValueList() != null && columnRemark.getValueList().size() > 0) {
                        columnList.add(columnRemark);
                        return;
                    }


                });


            }

            dataMap.put("columnList", columnList);


            BeanName.getFreeMarkerUtil().printFile("Controller.ftl", dataMap, BeanName.getShortControllerClassName(entityName) + ".java", BeanName.getControllerPath(), BeanName.getTempInPubJavaDir() + "/template");


        } catch (Exception e) {

        }


        //创建index









       /* StringBuffer sf = new StringBuffer();
        sf.append("package " + BeanName.getControllerPackage() + ";\n\n");
        addBaseImport(sf);
        sf.append("import " + BeanName.getFullBaseSearchClassName() + ";\n");
        sf.append("import " + BeanName.getFullBaseServiceClassName() + ";\n\n");

        sf.append("import " + BeanName.getFullModelClassName(entityName) + ";\n");

        sf.append("import " + BeanName.getFullSearchClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullServiceClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullBaseControllerClassName() + ";\n");

        sf.append("import org.springframework.stereotype.Controller;\n");
        sf.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        sf.append("import javax.annotation.Resource;\n");


        setComment(sf, BeanName.getShortModelClassName(entityName), "BackController");

        String shortControllerName = BeanName.getShortControllerClassName(entityName);

        String route = "/back/" + BeanName.getFirstLowerCase(entityName) + "/";
        String view = "back/" + BeanName.getFirstLowerCase(entityName) + "/";


        sf.append("@Controller(value =\"" + BeanName.getFirstLowerCase(entityName) + "BackController\")\n");
        sf.append("@RequestMapping(\"" + route + "\")\n");


        sf.append("public class " + shortControllerName + " extends " + BeanName.getShortBaseControllerClassName() + "<" + entityName + "," + BeanName.getShortSearchClassName(entityName) + "," + keyType + ">" + " {\n\n");
        sf.append("\t@Resource(name = \"" + BeanName.getServiceObjectName(entityName) + "\")\n");
        sf.append("\tprivate " + BeanName.getShortServiceClassName(entityName) + " " + BeanName.getServiceObjectName(entityName) + ";\n\n");


        sf.append("\t@Override\n");
        sf.append("\tpublic IBaseService<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) +","+keyType+ "> getBaseService() throws SkException {\n");

        sf.append("\t\t\treturn " + BeanName.getServiceObjectName(entityName) + ";\n");

        sf.append("\t}\n\n");

        sf.append("\t@Override\n");
        sf.append("\tpublic String getBaseRoute() {\n");
        sf.append("\t\t\treturn \"" + route + "\";\n");
        sf.append("\t}\n\n");

        sf.append("\t@Override\n");
        sf.append("\tpublic String getBaseView() {\n");
        sf.append("\t\t\treturn \"" + view + "\";\n");
        sf.append("\t}\n\n");


        sf.append("\t@Override\n");
        sf.append("\tpublic String getCurrentMenu() {\n");
        sf.append("\t\t\treturn \"" + remark + "列表" + "\";\n");
        sf.append("\t}\n\n");


        sf.append("\t@Override\n");
        sf.append("\tpublic String getMenuModel() {\n");
        sf.append("\t\t\treturn \"" + remark + "管理" + "\";\n");
        sf.append("\t}\n\n");


        sf.append("}");

        createFile(BeanName.getControllerPath(), shortControllerName, sf.toString());*/


    }


    private void addBaseImport(StringBuffer sf) {


        sf.append("import " + BeanName.getPackageCommon() + ".Const;\n");
        sf.append("import " + BeanName.getPackageCommon() + ".StringUtil;\n");
        sf.append("import " + BeanName.getPackageWebCommon() + ".SkMap;\n");
        sf.append("import " + BeanName.getPubInterPackage() + ".exception.SkException;\n");
        sf.append("import org.springframework.stereotype.Controller;\n");
        sf.append("import org.springframework.ui.Model;\n");
        sf.append("import org.springframework.web.bind.annotation.*;\n");
        sf.append("import javax.annotation.Resource;\n");
        sf.append("import javax.servlet.http.HttpServletRequest;\n");
        sf.append("import javax.servlet.http.HttpSession;\n");


    }


    private String getKeyColumns(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        List<IntrospectedColumn> keyColumns = introspectedTable.getPrimaryKeyColumns();
        if (keyColumns == null || keyColumns.size() == 0) {
            return "";
        }


        return tableName + "." + keyColumns.get(0).getActualColumnName();

    }


    private void createSearchMethodOrField(String entityName, List<ColumnRemark> columnRemarkList, boolean field, StringBuffer sf) {


        if (columnRemarkList == null || columnRemarkList.size() == 0) {
            return;
        }

        int searchFlag = 0;

        for (ColumnRemark columnRemark : columnRemarkList) {
            searchFlag = columnRemark.getSearchFlag();

            if (searchFlag == 0) {
                continue;
            }


            if (searchFlag == SearchTypeEnum.LIKE.getType()
                    || searchFlag == SearchTypeEnum.EQUAL.getType()
            ) {

                sf.append(SearchFieldBuild.buildStringSearchName(field, entityName, columnRemark, columnRemark.getSearchFlag() == SearchTypeEnum.EQUAL.getType()));

            } else if (searchFlag == SearchTypeEnum.NUMBER.getType()

            ) {
                sf.append(SearchFieldBuild.buildNumberSearchName(field, entityName, columnRemark, true));
                sf.append(SearchFieldBuild.buildNumberSearchName(field, entityName, columnRemark, false));


            } else if (searchFlag == SearchTypeEnum.IN.getType()
                    || searchFlag == SearchTypeEnum.NOT_IN.getType()
            ) {

                sf.append(SearchFieldBuild.buildInSearchName(field, entityName, columnRemark, columnRemark.getSearchFlag() == SearchTypeEnum.IN.getType()));

            } else if (searchFlag == SearchTypeEnum.DATE.getType()
            ) {
                sf.append(SearchFieldBuild.buildDateSearchName(field, entityName, columnRemark, true));
                sf.append(SearchFieldBuild.buildDateSearchName(field, entityName, columnRemark, false));

            } else if (searchFlag == SearchTypeEnum.BIT.getType()) {
                sf.append(SearchFieldBuild.buildBitSearchName(field, entityName, columnRemark));

            }


        }

    }


    private void createSearch(IntrospectedTable introspectedTable, List<ColumnRemark> columnRemarkList, String keyType) {

        // 获取实体类
        // FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());


        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();


        entityName = BeanName.getShortModelClassName(entityName);


        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Search", remark);
            dataMap.put("package", BeanName.getSearchPackage());
            setBaseInfo(dataMap, entityName, keyType, remark);
            //  StringBuffer sf = new StringBuffer();
            //  createSearchMethodOrField(entityName, columnRemarkList, true, sf);
            //  dataMap.put("searchField", sf.toString());
            dataMap.put("columnList", columnRemarkList);
            //   sf = new StringBuffer();
            // createSearchMethodOrField(entityName, columnRemarkList, false, sf);
            // dataMap.put("searchFieldMethod", sf.toString());
            BeanName.getFreeMarkerUtil().printFile(BeanName.getSearchTemplate(), dataMap, BeanName.getShortSearchClassName(entityName) + ".java", BeanName.getSearchPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }

        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "SearchExt", remark);
            dataMap.put("package", BeanName.getSearchExtPackage());
            setBaseInfo(dataMap, entityName, keyType, remark);
            // StringBuffer sf = new StringBuffer();
            // createSearchMethodOrField(entityName, columnRemarkList, true, sf);
            //  dataMap.put("searchField", sf.toString());
            dataMap.put("columnList", columnRemarkList);
            //  sf = new StringBuffer();
            //  createSearchMethodOrField(entityName, columnRemarkList, false, sf);
            // dataMap.put("searchFieldMethod", sf.toString());
            String template = "SearchExt.ftl";
            if (BeanName.getSearchTemplate().equalsIgnoreCase("SourceSearch.ftl")) {
                template = "SourceSearchExt.ftl";
            }
            BeanName.getFreeMarkerUtil().printFile(template, dataMap, BeanName.getShortSearchExtClassName(entityName) + ".java", BeanName.getSearchExtPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }








       /* StringBuffer sf = new StringBuffer();
        sf.append("package " + BeanName.getSearchPackage() + ";\n\n");
        sf.append("import " + BeanName.getFullBaseSearchClassName() + ";\n");

        sf.append("import " + BeanName.getModelPackage() + "." + entityName + ";\n");


        setComment(sf, BeanName.getShortModelClassName(entityName), "Search");

        String shortSearchName = BeanName.getShortSearchClassName(entityName);

        sf.append("public class " + shortSearchName + " extends " + BeanName.getShortBaseSearchClassName() + " {\n\n");


        createSearchMethodOrField(entityName, columnRemarkList, true, sf);

        sf.append("\n");


        sf.append("     public static " + shortSearchName + " getInstance() {\n");
        sf.append("         return new " + shortSearchName + "();\n");
        sf.append("}\n");
        sf.append("\n");


        createSearchMethodOrField(entityName, columnRemarkList, false, sf);


        sf.append("     @Override\n");
        sf.append("     public String  setDefaultField() {\n");
        // sf.append("           return \"" + getKeyColumns(introspectedTable) + "\";\n");
        sf.append("           return " + BeanName.getFirstUpperCase(entityName) + ".CREATE_TIME" + ";\n");
        sf.append("     }\n");


        sf.append("     @Override\n");
        sf.append("     public String  toString() {\n");
        sf.append("           return \"" + shortSearchName + "{}\"+super.toString();\n");
        sf.append("     }\n");
        sf.append("}");


        createFile(BeanName.getSearchPath(), shortSearchName, sf.toString());*/


    }

    /**
     * 得到命名空间
     *
     * @param rootElement
     * @return
     */
    private String getNamespace(XmlElement rootElement) {


        List<Attribute> attributeList = rootElement.getAttributes();
        if (attributeList != null) {
            for (Attribute attribute : attributeList) {
                if ("namespace".equalsIgnoreCase(attribute.getName())) {
                    return attribute.getValue() + ".";
                }
            }

        }

        return "";
    }

    private void createDao(IntrospectedTable introspectedTable, String keyType) {

        // 获取实体类
        // FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();


        createDaoInterface(BeanName.getDaoPath(), entityName, keyType, remark);

        createDaoImpl(BeanName.getDaoImplPath(), entityName, keyType, remark);


    }


    /**
     * 模板要的数据
     *
     * @param dataMap
     * @param entityName
     * @param
     */


    private void setBaseInfo(Map<String, Object> dataMap, String entityName, String keyType, String remark) {

        if (remark == null) {
            remark = "";
        }
        dataMap.put("remark", remark);
        dataMap.put("entityName", entityName);
        dataMap.put("keyType", keyType);
        dataMap.put("pubInterPackage", BeanName.getPubInterPackage());
        dataMap.put("commonPackage", BeanName.getPackageCommon());
        dataMap.put("webCommonPackage", BeanName.getPackageWebCommon());
        dataMap.put("modelExtPackage", BeanName.getExtPackage());
        dataMap.put("servicePackage", BeanName.getServicePackage());
        dataMap.put("daoVar", BeanName.getDaoObjectName(entityName));//dao变量名
        dataMap.put("serviceVar", BeanName.getServiceObjectName(entityName));//server 变量名
        dataMap.put("mapperExtVar", BeanName.getMapperExtObjectName(entityName));//mapperExt 变量名
        dataMap.put("packageProject", BeanName.getPackageProject());//项目路径
        dataMap.put("baseController", BeanName.getBaseControllerPackage());//项目路径


        dataMap.put("modelClass", BeanName.getFullModelClassName(entityName));
        dataMap.put("model", BeanName.getShortModelClassName(entityName));

        dataMap.put("modelDescClass", BeanName.getFullModelDescClassName(entityName));
        dataMap.put("modelDesc", BeanName.getShortModelDescClassName(entityName));

        dataMap.put("modelExtClass", BeanName.getFullModelExtClassName(entityName));
        dataMap.put("modelExt", BeanName.getShortModelExtClassName(entityName));

        dataMap.put("modelDOClass", BeanName.getFullModelDOClassName(entityName));
        dataMap.put("modelDO", BeanName.getShortDOClassName(entityName));

        dataMap.put("modelSearchClass", BeanName.getFullSearchClassName(entityName));
        dataMap.put("modelSearch", BeanName.getShortSearchClassName(entityName));

        dataMap.put("modelSearchExtClass", BeanName.getFullSearchExtClassName(entityName));
        dataMap.put("modelSearchExt", BeanName.getShortSearchExtClassName(entityName));


        dataMap.put("modelDTOClass", BeanName.getFullModelDTOClassName(entityName));
        dataMap.put("modelDTO", BeanName.getShortDTOClassName(entityName));

        dataMap.put("modelDomainDTOClass", BeanName.getFullModelDomainDTOClassName(entityName));
        dataMap.put("modelDomainDTO", BeanName.getShortDomainDTOClassName(entityName));

        dataMap.put("modelPageDTOClass", BeanName.getFullModelPageDTOClassName(entityName));
        dataMap.put("modelPageDTO", BeanName.getShortPageDTOClassName(entityName));

        dataMap.put("modelDomainPageDTOClass", BeanName.getFullModelDomainPageDTOClassName(entityName));
        dataMap.put("modelDomainPageDTO", BeanName.getShortDomainPageDTOClassName(entityName));


        dataMap.put("modelVOClass", BeanName.getFullModelVOClassName(entityName));
        dataMap.put("modelVO", BeanName.getShortVOClassName(entityName));

        dataMap.put("detailModelVOClass", BeanName.getFullDetailModelVOClassName(entityName));
        dataMap.put("detailModelVO", BeanName.getShortDetailDomainVOClassName(entityName));

        dataMap.put("detailVOClass", BeanName.getFullDetailVOClassName(entityName));
        dataMap.put("detailVO", BeanName.getShortDetailVOClassName(entityName));

        dataMap.put("listVOClass", BeanName.getFullListVOClassName(entityName));
        dataMap.put("listVO", BeanName.getShortListVOClassName(entityName));

        dataMap.put("mapperClass", BeanName.getFullMapperClassName(entityName));
        dataMap.put("mapper", BeanName.getShortMapperClassName(entityName));
        dataMap.put("mapperExtClass", BeanName.getFullMapperExtClassName(entityName));
        dataMap.put("mapperExt", BeanName.getShortMapperExtClassName(entityName));


        dataMap.put("iDaoClass", BeanName.getFullDaoClassName(entityName));
        dataMap.put("iDao", BeanName.getShortDaoClassName(entityName));
        dataMap.put("daoImplClass", BeanName.getFullDaoImplClassName(entityName));
        dataMap.put("daoImpl", BeanName.getShortDaoImplClassName(entityName));

        dataMap.put("iServiceClass", BeanName.getFullServiceClassName(entityName));
        dataMap.put("iService", BeanName.getShortServiceClassName(entityName));
        dataMap.put("serviceImplClass", BeanName.getFullServiceImplClassName(entityName));
        dataMap.put("serviceImpl", BeanName.getShortServiceImplClassName(entityName));


        dataMap.put("iBaseMapperClass", BeanName.getFullBaseMapperClassName());
        dataMap.put("iBaseMapper", BeanName.getShortBaseMapperClassName());
        dataMap.put("iBaseDaoClass", BeanName.getFullBaseDaoClassName());
        dataMap.put("iBaseDao", BeanName.getShortBaseDaoClassName());

        dataMap.put("iBaseServiceClass", BeanName.getFullBaseServiceClassName());
        dataMap.put("iBaseService", BeanName.getShortBaseServiceClassName());


        dataMap.put("baseSearchClass", BeanName.getFullBaseSearchClassName());
        dataMap.put("baseSearch", BeanName.getShortBaseSearchClassName());


        dataMap.put("sourceBaseSearchClass", BeanName.getFullBaseSourceSearchClassName());
        dataMap.put("sourceBaseSearch", BeanName.getShortSourceBaseSearchClassName());


        dataMap.put("abstractBaseServiceClass", BeanName.getFullAbstractServiceClassName());
        dataMap.put("abstractBaseService", BeanName.getShortAbstractServiceClassName());

        dataMap.put("abstractBaseDaoClass", BeanName.getFullAbstractDaoClassName());
        dataMap.put("abstractBaseDao", BeanName.getShortAbstractDaoClassName());


        dataMap.put("restfulControllerClass", BeanName.getFullApiController());
        dataMap.put("restfulController", BeanName.getShortApiController());


        dataMap.put("abstractDTOClass", BeanName.getFullBaseDTOClass());
        dataMap.put("abstractDTO", BeanName.getShortBaseDTOClass());

        dataMap.put("abstractPageDTOClass", BeanName.getFullBasePageDTOClass());
        dataMap.put("abstractPageDTO", BeanName.getShortBasePageDTOClass());

        dataMap.put("baseVOClass", BeanName.getFullBaseVOClass());
        dataMap.put("baseVO", BeanName.getShortBaseVOClass());

        dataMap.put("baseDataVOClass", BeanName.getFullBaseDetailVOClass());
        dataMap.put("baseDataVO", BeanName.getShortBaseDetailVOClass());

        dataMap.put("baseListVOClass", BeanName.getFullBaseListVOClass());
        dataMap.put("baseListVO", BeanName.getShortBaseListVOClass());
        dataMap.put("route", BeanName.getRoute());


    }


    private void createDaoImpl(String daoImplPath, String entityName, String keyType, String remark) {

        entityName = BeanName.getShortModelClassName(entityName);


        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "DaoImpl", remark);
            dataMap.put("package", BeanName.getDaoImplPackage());
            setBaseInfo(dataMap, entityName, keyType, remark);

            BeanName.getFreeMarkerUtil().printFile("DaoImpl.ftl", dataMap, BeanName.getShortDaoImplClassName(entityName) + ".java", daoImplPath, BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }


/*        StringBuffer sf = new StringBuffer("");
        sf.append("package " + BeanName.getDaoImplPackage() + ";\n\n");
        sf.append("import " + BeanName.getFullModelClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullSearchClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullAbstractDaoClassName() + ";\n");
        sf.append("import " + BeanName.getFullMapperClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullDaoClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullBaseMapperClassName() + ";\n");
        sf.append("import org.springframework.stereotype.Repository;\n\n");

        sf.append("import javax.annotation.Resource;\n");

        setComment(sf, entityName, "Dao");

        sf.append("@Repository(value = \"" + BeanName.getDaoObjectName(entityName) + "\")\n");

        sf.append("public class " + BeanName.getShortDaoImplClassName(entityName) + " extends " + BeanName.getShortAbstractDaoClassName() + "<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) + "," + keyType + "> implements " + BeanName.getShortDaoClassName(entityName) + " {\n\n");

        sf.append("     @Resource(name = \"" + BeanName.getMapperObjectName(entityName) + "\")\n");

        sf.append("     private " + BeanName.getShortMapperClassName(entityName) + " " + BeanName.getMapperObjectName(entityName) + ";\n\n");

        sf.append("     @Override\n");
        sf.append("     public " + BeanName.getShortBaseMapperClassName() + "<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) + "," + keyType + "> getMapper() {\n");

        sf.append("           return " + BeanName.getMapperObjectName(entityName) + ";\n");

        sf.append("     }\n");


        sf.append("}");
        createFile(daoImplPath, BeanName.getShortDaoImplClassName(entityName), sf.toString());*/


    }

    /**
     * 生成注釋
     *
     * @param sf
     * @param entityName
     * @param type
     * @throws IOException
     */

    private void setComment(StringBuffer sf, String entityName, String type) {


        sf.append("\n/**\n");
        sf.append(" *    @author " + BeanName.getAuthor() + "\n");
        sf.append(" *    @Date " + getFormatDate2String(new Date(), "yyyy-MM-dd HH:mm:ss") + "\n");
        sf.append(" *    @description " + entityName + type + "\n");
        sf.append(" */\n");


    }


    private void setComment(Map<String, Object> map, String entityName, String type, String remark) {

        if (remark == null) {
            remark = "";
        }
        map.put("author", BeanName.getAuthor());
        map.put("date", getFormatDate2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("description", entityName + type + "      " + remark);


    }


    private void createDir(String filePath) {

        FileUtil.mkdirs(filePath);

       /* File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();// 目录不存在的情况下，创建目录。
        }*/

    }


    private void createFile(String filePath, String fileName, String fileStr) {


        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();// 目录不存在的情况下，创建目录。
        }


        File daoInterfaceFile = new File(filePath + "/" + fileName + ".java");

        if (daoInterfaceFile.exists()) {
            daoInterfaceFile.delete();
        }


        FileWriter writer = null;

        try {
            writer = new FileWriter(daoInterfaceFile);
            writer.append(fileStr);


        } catch (IOException e) {


        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception e) {

                }
                writer = null;


            }
        }


    }


    /**
     * 生成dao接口java
     *
     * @param daoInterfacePath 接口目录
     * @param entityName
     */
    private void createDaoInterface(String daoInterfacePath, String entityName, String keyType, String remark) {


        entityName = BeanName.getShortModelClassName(entityName);


        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Dao", remark);
            dataMap.put("package", BeanName.getDaoPackage());

            setBaseInfo(dataMap, entityName, keyType, remark);
            BeanName.getFreeMarkerUtil().printFile("IDao.ftl", dataMap, BeanName.getShortDaoClassName(entityName) + ".java", daoInterfacePath, BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }





     /*
        StringBuffer sf = new StringBuffer("");
        sf.append("package " + BeanName.getDaoPackage() + ";\n\n");
        sf.append("import " + BeanName.getFullModelClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullSearchClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullBaseDaoClassName() + ";\n\n");

        setComment(sf, entityName, "Dao");

        sf.append("public interface " + BeanName.getShortDaoClassName(entityName) + " extends " + BeanName.getShortBaseDaoClassName() + "<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) + "," + keyType + ">" + " {\n\n");

        sf.append("}");

        createFile(daoInterfacePath, BeanName.getShortDaoClassName(entityName), sf.toString());
*/

    }


    private void createService(IntrospectedTable introspectedTable, String keyType) {

        // 获取实体类
        // FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());


        String entityName = getEntityName(introspectedTable);
        String remark = introspectedTable.getRemarks();


        createServiceInterface(BeanName.getServicePath(), entityName, keyType, remark);


        createServiceImpl(BeanName.getServicImplPath(), entityName, keyType, remark);


    }


    /**
     * 創建server接口
     *
     * @param servicePath
     * @param entityName
     */
    private void createServiceInterface(String servicePath, String entityName, String keyType, String remark) {

        entityName = BeanName.getShortModelClassName(entityName);


        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Service", remark);
            dataMap.put("package", BeanName.getServicePackage());
            setBaseInfo(dataMap, entityName, keyType, remark);

            BeanName.getFreeMarkerUtil().printFile("IService.ftl", dataMap, BeanName.getShortServiceClassName(entityName) + ".java", servicePath, BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }


       /* StringBuffer sf = new StringBuffer("");

        sf.append("package " + BeanName.getServicePackage() + ";\n\n");
        sf.append("import " + BeanName.getFullModelClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullSearchClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullBaseServiceClassName() + ";\n\n");

        setComment(sf, entityName, "Service");

        sf.append("public interface " + BeanName.getShortServiceClassName(entityName) + " extends " + BeanName.getShortBaseServiceClassName() + "<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) + "," + keyType + ">" + " {\n\n");

        sf.append("}");

        createFile(servicePath, BeanName.getShortServiceClassName(entityName), sf.toString());*/


    }

    /**
     * 創建serviceImpl
     *
     * @param servicePath
     * @param entityName
     */
    private void createServiceImpl(String servicePath, String entityName, String keyType, String remark) {


        entityName = BeanName.getShortModelClassName(entityName);


        try {
            Map<String, Object> dataMap = new HashMap<>();
            setComment(dataMap, entityName, "Service", remark);
            dataMap.put("package", BeanName.getServiceImplPackage());
            setBaseInfo(dataMap, entityName, keyType, remark);

            BeanName.getFreeMarkerUtil().printFile("ServiceImpl.ftl", dataMap, BeanName.getShortServiceImplClassName(entityName) + ".java", servicePath, BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }


 /*       StringBuffer sf = new StringBuffer("");
        sf.append("package " + BeanName.getServiceImplPackage() + ";\n\n");
        sf.append("import " + BeanName.getFullModelClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullDaoClassName(entityName) + ";\n\n");
        sf.append("import " + BeanName.getFullSearchClassName(entityName) + ";\n");
        sf.append("import " + BeanName.getFullBaseDaoClassName() + ";\n");
        sf.append("import " + BeanName.getFullAbstractServiceClassName() + ";\n");
        sf.append("import " + BeanName.getFullServiceClassName(entityName) + ";\n");
        sf.append("import org.springframework.stereotype.Service;\n\n");

        sf.append("import javax.annotation.Resource;\n");

        setComment(sf, entityName, "Service");

        sf.append("@Service(value = \"" + BeanName.getServiceObjectName(entityName) + "\")\n");

        sf.append("public class " + BeanName.getShortServiceImplClassName(entityName) + " extends " + BeanName.getShortAbstractServiceClassName() + "<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) + "," + keyType + "> implements " + BeanName.getShortServiceClassName(entityName) + " {\n\n");

        sf.append("     @Resource(name = \"" + BeanName.getDaoObjectName(entityName) + "\")\n");

        sf.append("     private " + BeanName.getShortDaoClassName(entityName) + " " + BeanName.getDaoObjectName(entityName) + ";\n\n");

        sf.append("     @Override\n");
        sf.append("     public IBaseDao<" + entityName + ", " + BeanName.getShortSearchClassName(entityName) + "," + keyType + "> getDao() {\n");

        sf.append("           return " + BeanName.getDaoObjectName(entityName) + ";\n");

        sf.append("     }\n");

  *//*      sf.append("     @Override\n");
        sf.append("     public Class<" + BeanName.getShortSearchClassName(entityName) + "> getSearchClass() {\n");

        sf.append("           return " + BeanName.getShortSearchClassName(entityName) + ".class;\n");

        sf.append("     }\n");*//*


        sf.append("}");


        createFile(servicePath, BeanName.getShortServiceImplClassName(entityName), sf.toString());*/


    }


    /**
     * 生成model
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {


        setRemark(introspectedTable);

        topLevelClass.addFileCommentLine("/**\n" +
                "* @author " + BeanName.getAuthor() + "\n" +
                "* @Date " + YXDate.getFormatDate2String(new Date()) + "\n" +
                "* @description " + introspectedTable.getRemarks() + "\n" +
                "*/");

        if ("BaseIdDoMain".equalsIgnoreCase(topLevelClass.getSuperClass().getShortName())) {
            String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
            topLevelClass.setSuperClass("BaseIdDoMain<" + keyType + ">");
        }
        List<Field> fieldList = topLevelClass.getFields();
        if (StringUtil.isNotObjEmpty(fieldList)) {
            for (Field field : fieldList) {
                if ("sortNum".equalsIgnoreCase(field.getName())) {
                    field.setInitializationString("0");
                } else if ("showFlag".equalsIgnoreCase(field.getName())) {
                    field.setInitializationString("1");
                } else if ("delFlag".equalsIgnoreCase(field.getName())) {
                    field.setInitializationString("0");
                }
            }
        }


        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        SkField field = null;
        String remarks = "";
        for (IntrospectedColumn column : columnList) {

            remarks = (column.getRemarks() != null) ? column.getRemarks().split("\\|")[0] : "";
            field = new SkField();
            field.setRemark(remarks);
            field.setFinal(true);
            field.setStatic(true);
            field.setVisibility(JavaVisibility.PUBLIC);
            field.setType(new FullyQualifiedJavaType("java.lang.String"));
            field.setName(BeanName.underscoreName(column.getJavaProperty()).toUpperCase());
            field.setInitializationString("\"" + tableName + "." + column.getActualColumnName() + "\"");
            topLevelClass.addField(field);
        }

        field = new SkField();
        field.setRemark("填充detail key");
        field.setFinal(true);
        field.setStatic(true);
        field.setVisibility(JavaVisibility.PUBLIC);
        field.setType(new FullyQualifiedJavaType("java.lang.String"));
        field.setName((BeanName.underscoreName(tableName) + "_full_detail_key").toUpperCase());
        field.setInitializationString("\"" + (BeanName.underscoreName(tableName) + "_full_detail").toUpperCase() + "\"");
        topLevelClass.addField(field);
        field = new SkField();
        field.setRemark("填充list key");
        field.setFinal(true);
        field.setStatic(true);
        field.setVisibility(JavaVisibility.PUBLIC);
        field.setType(new FullyQualifiedJavaType("java.lang.String"));
        field.setName((BeanName.underscoreName(tableName) + "_full_list_key").toUpperCase());
        field.setInitializationString("\"" + (BeanName.underscoreName(tableName) + "_full_list").toUpperCase() + "\"");
        topLevelClass.addField(field);


        topLevelClass.addImportedType("org.hibernate.validator.constraints.Length");
        topLevelClass.addImportedType("javax.validation.constraints.*");


        boolean result = super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);

        return result;
    }


    private XmlElement createCacheElement() {

        return null;
      /*  XmlElement cache = new XmlElement("cache");
        Attribute attribute = new Attribute("type", BeanName.getPubInterPackage() + ".MybatisRedisCache");

        cache.addAttribute(attribute);

        return cache;*/

    }


    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {


        XmlElement rootElement = document.getRootElement();


        // 数据库表名
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        // 主键
        IntrospectedColumn pkColumn = introspectedTable.getPrimaryKeyColumns().get(0);


        String namespace = getNamespace(rootElement);

        XmlElement cacheElement = createCacheElement();
        if (cacheElement != null) {
            rootElement.addElement(cacheElement);
        }


        rootElement.addElement(createOneResultMap(introspectedTable));//resultMap
        // rootElement.addElement(createBaseResultMap(namespace, introspectedTable));//baseResultMap


        rootElement.addElement(createOneSmallColumnListSql(namespace, introspectedTable));//oneSmallColumnList
        //  rootElement.addElement(createBaseSmallColumnList(namespace));//createSmallColumnList


        rootElement.addElement(createOneColumnListSql(namespace, introspectedTable));//oneColumnList

        // rootElement.addElement(createBaseColumnList(namespace));//baseColumnList

        //  rootElement.addElement(createIncludeWhereSql());//includeWhereSql
        //  rootElement.addElement(createWhereSql());//whereSql


        // rootElement.addElement(createFromTable(tableName));//fromTable

        rootElement.addElement(createInsert(tableName, introspectedTable));//insert
        rootElement.addElement(createBatchInsert(tableName, introspectedTable));//batchInsert


        rootElement.addElement(createUpdate(tableName, pkColumn, introspectedTable));//update

        rootElement.addElement(createDelete(tableName, pkColumn));//delete
        rootElement.addElement(createBatchDelete(tableName, pkColumn));//批量删除


        rootElement.addElement(createDetail(tableName, pkColumn, introspectedTable));//getDetail
        //rootElement.addElement(createListSql());//getListSql

        rootElement.addElement(createAllList(introspectedTable));//getAllList
        rootElement.addElement(createList(introspectedTable));//getList

        rootElement.addElement(createListCount(introspectedTable));//getListCount

        rootElement.addElement(updateDataBySearch(tableName, pkColumn, introspectedTable)); //更新字段根据搜索
        rootElement.addElement(updateDataById(tableName, pkColumn, introspectedTable));//更新字段根据id

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }


    /**
     * 根据搜索更新字段
     *
     * @param introspectedTable
     * @return
     */
    private XmlElement updateDataBySearch(String tableName, IntrospectedColumn pkColumn, IntrospectedTable introspectedTable) {

        XmlElement updateElement = new XmlElement("update");

        Attribute flushCache = addFlushCache();
        if (flushCache != null) {
            updateElement.addAttribute(flushCache);
        }

        updateElement.addAttribute(new Attribute("id", "updateDataBySearch"));
        // updateElement.addAttribute(new Attribute("parameterType", getEntityName(introspectedTable)));
        StringBuilder updateStr = new StringBuilder("update " + tableName + "\n");
        // List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();


        updateStr.append("\t<set>\n");

        updateStr.append("\t\t<foreach collection=\"dataMap\" index=\"filed\" item=\"itemValue\">\n");
        updateStr.append("\t\t\t${filed} =#{itemValue},\n");
        updateStr.append("\t\t</foreach>\n");

        updateStr.append("\t</set>\n");

        String namespace = BeanName.getFullMapperExtClassName(getEntityName(introspectedTable));

        updateStr.append(" \t<include refid=\"" + namespace + ".whereSql\"></include>\n");

        updateElement.addElement(new TextElement(updateStr.toString()));
        return updateElement;

    }


    /**
     * 根据Id更新字段
     *
     * @param introspectedTable
     * @return
     */
    private XmlElement updateDataById(String tableName, IntrospectedColumn pkColumn, IntrospectedTable introspectedTable) {


        XmlElement updateElement = new XmlElement("update");
        Attribute flushCache = addFlushCache();
        if (flushCache != null) {
            updateElement.addAttribute(flushCache);
        }
        updateElement.addAttribute(new Attribute("id", "updateDataById"));
        // updateElement.addAttribute(new Attribute("parameterType", getEntityName(introspectedTable)));
        StringBuilder updateStr = new StringBuilder("update " + tableName + "\n");
        // List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();


        updateStr.append("\t<set>\n");

        updateStr.append("\t\t<foreach collection=\"dataMap\" index=\"filed\" item=\"itemValue\">\n");
        updateStr.append("\t\t\t${filed} =#{itemValue},\n");
        updateStr.append("\t\t</foreach>\n");


        updateStr.append("\t</set>\n");

        String namespace = BeanName.getFullMapperExtClassName(getEntityName(introspectedTable));

        updateStr.append("\t where " + pkColumn.getActualColumnName() + "=#{" + pkColumn.getJavaProperty() + "}");

        updateElement.addElement(new TextElement(updateStr.toString()));
        return updateElement;

    }


    private XmlElement createListCount(IntrospectedTable introspectedTable) {


        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());


        String entityName = getEntityName(introspectedTable);
        String namespace = BeanName.getFullMapperExtClassName(getEntityName(introspectedTable));

        XmlElement listCountElement = new XmlElement("select");
        listCountElement.addAttribute(new Attribute("id", "getListCount"));
        listCountElement.addAttribute(new Attribute("resultType", "java.lang.Integer"));
        // listCountElement.addAttribute(new Attribute("parameterType", BeanName.getShortSearchExtClassName(entityName)));

        IntrospectedColumn pkColumn = introspectedTable.getPrimaryKeyColumns().get(0);

        StringBuilder selectStr = new StringBuilder("select count(DISTINCT " + tableName + "." + pkColumn.getActualColumnName() + ")  \n");


        selectStr.append(" \t\t<include refid=\"" + namespace + ".fromTable\"></include>\n");
        selectStr.append(" \t\t<include refid=\"" + namespace + ".whereSql\"></include>\n");

       /* if (!dbType.equalsIgnoreCase(DB_TYPE_MSSERVER)) {

            selectStr.append("\t\t<include refid=\"" + BeanName.getSearchMapperOrderBy() + "\"></include>");
        }*/

        listCountElement.addElement(new TextElement(selectStr.toString()));


        return listCountElement;


    }


    private String getMsServerListSql(String namespace, IntrospectedTable introspectedTable) {


        StringBuilder selectStr = new StringBuilder("select top ${search.limit} \n");


        String keyColumn = getKeyColumns(introspectedTable);


        selectStr.append(" <include refid=\"" + namespace + ".baseSmallColumnList\"></include>\n");
        selectStr.append(" <include refid=\"" + namespace + ".fromTable\"></include>\n");
        selectStr.append(" \t<where>\n");
        selectStr.append(" \t\t<include refid=\"" + namespace + ".includeWhereSql\"></include>\n");
        selectStr.append(" \t\t<if test=\"search.pageNumber>1\">\n");
        selectStr.append(" \t\t\t and " + keyColumn + " not in (\n");
        selectStr.append(" \t\t\t\t select top ${search.start} " + keyColumn + "\n");
        selectStr.append(" \t\t\t\t  <include refid=\"" + namespace + ".fromTable\"></include> \n");

        selectStr.append(" \t\t\t\t <where> \n");
        selectStr.append(" \t\t\t\t\t  <include refid=\"" + namespace + ".includeWhereSql\"></include> \n");
        selectStr.append(" \t\t\t\t </where> \n");
        selectStr.append(" \t\t\t\t  <include refid=\"" + BeanName.getPubInterPackage() + ".SearchMapper.searchGroupByAndOrderBy\"></include> \n");

        selectStr.append(" \t\t\t )\n");

        selectStr.append(" \t\t</if>\n");

        selectStr.append(" \t</where>\n");

        selectStr.append(" \t\t\t\t  <include refid=\"" + BeanName.getPubInterPackage() + ".SearchMapper.searchGroupByAndOrderBy\"></include> \n");


        return selectStr.toString();


    }


    private XmlElement createList(IntrospectedTable introspectedTable) {


        //  FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());


        String entityName = getEntityName(introspectedTable);


        String namespace = BeanName.getFullMapperExtClassName(getEntityName(introspectedTable));


        XmlElement listElement = new XmlElement("select");


        listElement.addAttribute(new Attribute("id", "getList"));
        listElement.addAttribute(new Attribute("fetchSize", "10000"));

        listElement.addAttribute(new Attribute("resultMap", namespace + ".baseResultMap"));
        //    listElement.addAttribute(new Attribute("parameterType", BeanName.getShortSearchExtClassName(entityName)));
        StringBuffer selectStr = null;
        if (dbType.equalsIgnoreCase(DB_TYPE_MSSERVER)) {

            selectStr = new StringBuffer(getMsServerListSql(namespace, introspectedTable));

        } else if (dbType.equalsIgnoreCase(DB_TYPE_MYSQL)) {

            selectStr = new StringBuffer();
            selectStr.append("<include refid=\"" + namespace + ".getListSql\"></include>\n");
            selectStr.append("\t\tlimit #{search.start},#{search.limit}");

        } else if (dbType.equalsIgnoreCase(DB_TYPE_ORACLE)) {

            selectStr = new StringBuffer(" select * from ( \n");
            selectStr.append("\t\tselect a.*,rownum row_num from (\n");
            selectStr.append("\t\t\t<include refid=\"" + namespace + ".getListSql\"></include>\n");
            selectStr.append("\t\t) a\n");
            selectStr.append(") b\n");
            selectStr.append(" where b.row_num between #{start} and #{end}");

        }


        listElement.addElement(new TextElement(selectStr.toString()));


        return listElement;


    }


    private XmlElement createListSql() {

        XmlElement listSqlElement = new XmlElement("sql");
        listSqlElement.addAttribute(new Attribute("id", "getListSql"));


        listSqlElement.addElement(new TextElement(getSelectListSql()));


        return listSqlElement;

    }


    private String getSelectListSql() {


        StringBuilder selectStr = new StringBuilder("select \n");
        selectStr.append("\t\t<include refid=\"baseSmallColumnList\"/>\n");
        selectStr.append("\t\t<include refid=\"fromTable\"></include>\n");
        selectStr.append("\t\t<include refid=\"whereSql\"></include>\n");


        selectStr.append("\t\t<include refid=\"" + BeanName.getSearchMapperOrderBy() + "\"></include>");

        return selectStr.toString();

    }


    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {


        if ("id".equals(field.getName())) {
            return false;
        }
        if ("createTime".equals(field.getName())) {
            return false;
        }
        if ("updateTime".equals(field.getName())) {
            return false;
        }

        if ("BaseSourceDoMain".equals(topLevelClass.getSuperClass().getShortName())) {

            if ("source".equals(field.getName())) {
                return false;
            }
        }


        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if (("setId").equals(method.getName())) {
            return false;
        }
        if ("setCreateTime".equals(method.getName())) {
            return false;
        }
        if ("setUpdateTime".equals(method.getName())) {
            return false;
        }

        if ("BaseSourceDoMain".equals(topLevelClass.getSuperClass().getShortName())) {

            if ("setSource".equals(method.getName())) {
                return false;
            }
        }


        method.addBodyLine(method.getBodyLines().size(), "return this;");
        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);


    }


    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if ("getId".equals(method.getName())) {
            return false;
        }
        if ("getCreateTime".equals(method.getName())) {
            return false;
        }
        if ("getUpdateTime".equals(method.getName())) {
            return false;
        }

        if ("BaseSourceDoMain".equals(topLevelClass.getSuperClass().getShortName())) {

            if ("getSource".equals(method.getName())) {
                return false;
            }
        }


        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    private String getEntityName(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        return entityType.getShortName();

    }

    private String getModelExtName(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        return BeanName.getShortModelExtClassName(entityType.getShortName());


    }

    private String getModelDescName(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        return BeanName.getShortModelDescClassName(entityType.getShortName());


    }


    private XmlElement createAllList(IntrospectedTable introspectedTable) {


        String entityName = getEntityName(introspectedTable);

        String namespace = BeanName.getFullMapperExtClassName(getEntityName(introspectedTable));

        XmlElement allListElement = new XmlElement("select");

        allListElement.addAttribute(new Attribute("id", "getAllList"));
        allListElement.addAttribute(new Attribute("fetchSize", "10000"));
        allListElement.addAttribute(new Attribute("resultMap", namespace + ".baseResultMap"));
        //  allListElement.addAttribute(new Attribute("parameterType", BeanName.getShortSearchExtClassName(entityName)));


        allListElement.addElement(new TextElement("<include refid=\"" + namespace + ".getListSql\"></include>"));
        return allListElement;


    }


    private XmlElement createDetail(String tableName, IntrospectedColumn pkColumn, IntrospectedTable introspectedTable) {


        String namespace = BeanName.getFullMapperExtClassName(getEntityName(introspectedTable));
        XmlElement detailElement = new XmlElement("select");
        detailElement.addAttribute(new Attribute("id", "getDetail"));
        //   detailElement.addAttribute(new Attribute("parameterType", pkColumn.getFullyQualifiedJavaType().getFullyQualifiedNameWithoutTypeParameters()));
        detailElement.addAttribute(new Attribute("resultMap", namespace + ".baseResultMap"));

        StringBuilder selectStr = new StringBuilder("select \n");
        selectStr.append(" \t\t<include refid=\"" + namespace + ".baseColumnList\"></include>\n");
        selectStr.append(" \t\t<include refid=\"" + namespace + ".fromTable\"></include>\n");
        selectStr.append(" \t\twhere " + tableName + "." + pkColumn.getActualColumnName() + "=#{0}");
        detailElement.addElement(new TextElement(selectStr.toString()));

        return detailElement;

    }


    private XmlElement createDelete(String tableName, IntrospectedColumn pkColumn) {
        XmlElement deleteElement = new XmlElement("delete");

        deleteElement.addAttribute(new Attribute("id", "delete"));

        Attribute flushCache = addFlushCache();
        if (flushCache != null) {
            deleteElement.addAttribute(flushCache);
        }
        // deleteElement.addAttribute(new Attribute("parameterType", pkColumn.getFullyQualifiedJavaType().getFullyQualifiedNameWithoutTypeParameters()));

        deleteElement.addElement(new TextElement("delete from " + tableName + " where " + pkColumn.getActualColumnName() + "=#{0}"));

        return deleteElement;
    }

    /**
     * 批量删除
     *
     * @param tableName
     * @param pkColumn
     * @return
     */
    private XmlElement createBatchDelete(String tableName, IntrospectedColumn pkColumn) {
        XmlElement deleteElement = new XmlElement("delete");

        deleteElement.addAttribute(new Attribute("id", "batchDelete"));
        Attribute flushCache = addFlushCache();
        if (flushCache != null) {
            deleteElement.addAttribute(flushCache);
        }
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append(tableName + "\n");
        sql.append("    <where>\n");
        sql.append("        " + pkColumn.getActualColumnName() + " in \n");
        sql.append("        <foreach collection=\"list\"  item=\"deleteId\" separator=\",\" open=\"(\" close=\")\">\n");
        sql.append("            #{deleteId}\n");
        sql.append("        </foreach>\n");
        sql.append("    </where>");
        deleteElement.addElement(new TextElement(sql.toString()));

        return deleteElement;
    }


    private XmlElement createBatchInsert(String tableName, IntrospectedTable introspectedTable) {


        XmlElement insertElement = new XmlElement("insert");
        insertElement.addAttribute(new Attribute("id", "batchInsert"));
        insertElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        Attribute flushCache = addFlushCache();
        if (flushCache != null) {
            insertElement.addAttribute(flushCache);
        }

        IntrospectedColumn keyColumn = getPrimaryKeyColumns(introspectedTable);

        boolean isAutoKey = isAutoKey(introspectedTable);

        if (isAutoKey && keyColumn != null) {

            insertElement.addAttribute(new Attribute("keyColumn", keyColumn.getActualColumnName()));
            insertElement.addAttribute(new Attribute("keyProperty", keyColumn.getJavaProperty()));

            insertElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
        }


        StringBuilder insertStr = new StringBuilder("insert into " + tableName + "\n");

        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();

        XmlElement resultElement = null;

        insertStr.append("\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        for (IntrospectedColumn column : columnList) {

            if (isAutoKey && column.getActualColumnName().equalsIgnoreCase(keyColumn.getActualColumnName())) {
                continue;

            }

            // insertStr.append("\t\t<if test=\"" + column.getJavaProperty() + "!=null\">\n");
            insertStr.append("\t\t\t" + column.getActualColumnName() + ",\n");
            // insertStr.append("\t\t</if>\n");


        }
        insertStr.append("\t</trim>\n");

        insertStr.append("\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" open=\" VALUES \">\n");
        insertStr.append("\t\t<trim prefix=\"  (\" suffix=\")\" suffixOverrides=\",\">\n");
        for (IntrospectedColumn column : columnList) {

            if (isAutoKey && column.getActualColumnName().equalsIgnoreCase(keyColumn.getActualColumnName())) {
                continue;

            }


            // insertStr.append("\t\t<if test=\"" + column.getJavaProperty() + "!=null\">\n");


            if (isBigType(column.getJdbcTypeName())) {

                insertStr.append("\t\t\t\t#{item." + column.getJavaProperty() + ",jdbcType=" + column.getJdbcTypeName() + "},\n");

            } else {
                insertStr.append("\t\t\t\t#{item." + column.getJavaProperty() + "},\n");

            }

            // insertStr.append("\t\t\t#{" + column.getJavaProperty() + "},\n");
            //  insertStr.append("\t\t</if>\n");


        }
        insertStr.append("\t\t</trim>\n");
        insertStr.append("\t</foreach>");


        insertElement.addElement(new TextElement(insertStr.toString()));


        return insertElement;

    }


    private XmlElement createUpdate(String tableName, IntrospectedColumn pkColumn, IntrospectedTable introspectedTable) {
        // FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());


        XmlElement updateElement = new XmlElement("update");
        Attribute flushCache = addFlushCache();
        if (flushCache != null) {
            updateElement.addAttribute(flushCache);
        }

        updateElement.addAttribute(new Attribute("id", "update"));
        updateElement.addAttribute(new Attribute("parameterType", getEntityName(introspectedTable)));
        StringBuilder updateStr = new StringBuilder("update " + tableName + "\n");
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();


        updateStr.append("\t<set>\n");
        for (IntrospectedColumn column : columnList) {
            if (column.getActualColumnName().equalsIgnoreCase(pkColumn.getActualColumnName())) {
                continue;
            }


            updateStr.append("\t\t<if test=\"" + column.getJavaProperty() + "!=null\">\n");


            if (isBigType(column.getJdbcTypeName())) {
                updateStr.append("\t\t\t" + column.getActualColumnName() + "=#{" + column.getJavaProperty() + ",jdbcType=" + column.getJdbcTypeName() + "},\n");

            } else {
                updateStr.append("\t\t\t" + column.getActualColumnName() + "=#{" + column.getJavaProperty() + "},\n");

            }

            //  updateStr.append("\t\t\t" + column.getActualColumnName() + "=#{" + column.getJavaProperty() + "},\n");


            updateStr.append("\t\t</if>\n");


        }
        updateStr.append("\t</set>\n");

        updateStr.append("\t where " + pkColumn.getActualColumnName() + "=#{" + pkColumn.getJavaProperty() + "}");


        updateElement.addElement(new TextElement(updateStr.toString()));
        return updateElement;


    }


    /**
     * 得到主键字段
     *
     * @param introspectedTable
     * @return
     */
    private IntrospectedColumn getPrimaryKeyColumns(IntrospectedTable introspectedTable) {

        if (introspectedTable.hasPrimaryKeyColumns()) {
            return introspectedTable.getPrimaryKeyColumns().get(0);
        }
        return null;
    }


    // 如果是int 或long 就当他是自增长

    private boolean isAutoKey(IntrospectedTable introspectedTable) {

        IntrospectedColumn keyColumn = getPrimaryKeyColumns(introspectedTable);
        if (keyColumn == null) {
            return false;
        }

        String shortName = getEntityName(introspectedTable);

        return "integer".equalsIgnoreCase(shortName) || "long".equalsIgnoreCase(shortName);


    }


    private Attribute addFlushCache() {
        return null;
        // return new Attribute("flushCache","true");
    }

    private XmlElement createInsert(String tableName, IntrospectedTable introspectedTable) {


        // FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        XmlElement insertElement = new XmlElement("insert");
        insertElement.addAttribute(new Attribute("id", "insert"));
        insertElement.addAttribute(new Attribute("parameterType", getEntityName(introspectedTable)));
        Attribute flushCache = addFlushCache();
        if (flushCache != null) {

            insertElement.addAttribute(flushCache);
        }

        IntrospectedColumn keyColumn = getPrimaryKeyColumns(introspectedTable);

        boolean isAutoKey = isAutoKey(introspectedTable);

        if (isAutoKey && keyColumn != null) {

            insertElement.addAttribute(new Attribute("keyColumn", keyColumn.getActualColumnName()));
            insertElement.addAttribute(new Attribute("keyProperty", keyColumn.getJavaProperty()));

            insertElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
        }


        StringBuilder insertStr = new StringBuilder("insert into " + tableName + "\n");

        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();

        XmlElement resultElement = null;

        insertStr.append("\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        for (IntrospectedColumn column : columnList) {

            if (isAutoKey && column.getActualColumnName().equalsIgnoreCase(keyColumn.getActualColumnName())) {
                continue;

            }

            insertStr.append("\t\t<if test=\"" + column.getJavaProperty() + "!=null\">\n");
            insertStr.append("\t\t\t" + column.getActualColumnName() + ",\n");
            insertStr.append("\t\t</if>\n");


        }
        insertStr.append("\t</trim>\n");


        insertStr.append("\t<trim prefix=\" values (\" suffix=\")\" suffixOverrides=\",\">\n");
        for (IntrospectedColumn column : columnList) {

            if (isAutoKey && column.getActualColumnName().equalsIgnoreCase(keyColumn.getActualColumnName())) {
                continue;

            }


            insertStr.append("\t\t<if test=\"" + column.getJavaProperty() + "!=null\">\n");


            if (isBigType(column.getJdbcTypeName())) {

                insertStr.append("\t\t\t#{" + column.getJavaProperty() + ",jdbcType=" + column.getJdbcTypeName() + "},\n");

            } else {
                insertStr.append("\t\t\t#{" + column.getJavaProperty() + "},\n");

            }

            // insertStr.append("\t\t\t#{" + column.getJavaProperty() + "},\n");
            insertStr.append("\t\t</if>\n");

        }
        insertStr.append("\t</trim>");


        insertElement.addElement(new TextElement(insertStr.toString()));


        return insertElement;


    }


    private XmlElement createFromTable(String table) {
        XmlElement fromElement = new XmlElement("sql");
        fromElement.addAttribute(new Attribute("id", "fromTable"));
        fromElement.addElement(new TextElement("from " + table));
        return fromElement;


    }


    private XmlElement createIncludeWhereSql() {

        XmlElement whereElement = new XmlElement("sql");
        whereElement.addAttribute(new Attribute("id", "includeWhereSql"));


        StringBuffer whereSql = new StringBuffer();

        whereSql.append("\t<include refid=\"" + BeanName.getSearchMapperWhereSql() + "\"></include>");

        whereElement.addElement(new TextElement(whereSql.toString()));

        return whereElement;

    }


    private XmlElement createWhereSql() {

        XmlElement whereElement = new XmlElement("sql");
        whereElement.addAttribute(new Attribute("id", "whereSql"));


        StringBuffer whereSql = new StringBuffer("<where>\n");

        whereSql.append("\t\t\t<include refid=\"includeWhereSql\"></include>\n");

        whereSql.append("\t\t</where>");

        whereElement.addElement(new TextElement(whereSql.toString()));


        return whereElement;


    }


    private XmlElement createBaseSmallColumnList(String namespace) {

        XmlElement element = new XmlElement("sql");
        element.addAttribute(new Attribute("id", "baseSmallColumnList"));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", namespace + "oneSmallColumnList"));
        element.addElement(includeElement);

        return element;

    }

    private XmlElement createBaseColumnList(String namespace) {

        XmlElement element = new XmlElement("sql");
        element.addAttribute(new Attribute("id", "baseColumnList"));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", namespace + "oneColumnList"));
        element.addElement(includeElement);

        return element;

    }


    /**
     * 创建表内部列表sql语句
     *
     * @param namespace
     * @param introspectedTable
     * @return
     */
    private XmlElement createOneSmallColumnListSql(String namespace, IntrospectedTable introspectedTable) {


        XmlElement sqlElement = new XmlElement("sql");

        sqlElement.addAttribute(new Attribute("id", "oneSmallColumnList"));

        StringBuffer sf = new StringBuffer();


        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();


        String actualColumnName = null;
        for (IntrospectedColumn column : columnList) {


            actualColumnName = column.getActualColumnName();
            String jdbcTypeName = column.getJdbcTypeName();

            if (isBigType(jdbcTypeName)) {
                continue;
            }


            sf.append("," + tableName + "." + actualColumnName + " as " + getAsColumn(introspectedTable, actualColumnName));


        }

        TextElement textElement = new TextElement(sf.toString().substring(1));
        sqlElement.addElement(textElement);
        return sqlElement;


    }


    private String getAsColumn(IntrospectedTable introspectedTable, String actualColumnName) {


        return actualColumnName;

        //  FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        //   return entityType.getShortName().toUpperCase() + "_" + actualColumnName;


    }


    private XmlElement createOneColumnListSql(String namespace, IntrospectedTable introspectedTable) {

        XmlElement sqlElement = new XmlElement("sql");

        sqlElement.addAttribute(new Attribute("id", "oneColumnList"));

        StringBuffer sf = new StringBuffer();


        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();

        sf.append("<include refid=\"" + namespace + "oneSmallColumnList\"/>\n");
        sf.append("    ");

        String actualColumnName = null;
        String jdbcTypeName = null;
        for (IntrospectedColumn column : columnList) {
            jdbcTypeName = column.getJdbcTypeName();
            if (isBigType(jdbcTypeName)) {

                actualColumnName = column.getActualColumnName();
                sf.append("," + tableName + "." + actualColumnName + " as " + getAsColumn(introspectedTable, actualColumnName));

            }


        }

        TextElement textElement = new TextElement(sf.toString());
        sqlElement.addElement(textElement);
        return sqlElement;


    }


    private XmlElement createBaseResultMap(String namespace, IntrospectedTable introspectedTable) {
        // 获取实体类
        // FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        XmlElement resultMap = new XmlElement("resultMap");
        resultMap.addAttribute(new Attribute("id", "baseResultMap"));
        resultMap.addAttribute(new Attribute("type", getModelExtName(introspectedTable)));
        resultMap.addAttribute(new Attribute("extends", namespace + "oneResultMap"));

        return resultMap;
    }


    private XmlElement createOneResultMap(IntrospectedTable introspectedTable) {


        // 获取实体类
        //  FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        XmlElement resultMap = new XmlElement("resultMap");
        resultMap.addAttribute(new Attribute("id", "oneResultMap"));


        resultMap.addAttribute(new Attribute("type", getModelDescName(introspectedTable)));
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();

        XmlElement resultElement = null;

        for (IntrospectedColumn column : columnList) {

            if (column.isIdentity()) {
                resultElement = new XmlElement("id");

            } else {
                resultElement = new XmlElement("result");

            }
            resultElement.addAttribute(new Attribute("column", getAsColumn(introspectedTable, column.getActualColumnName())));
            resultElement.addAttribute(new Attribute("property", column.getJavaProperty()));

            //LONGVARCHAR

            if ("DATE".equalsIgnoreCase(column.getJdbcTypeName()) || "TIMESTAMP".equalsIgnoreCase(column.getJdbcTypeName())) {
                resultElement.addAttribute(new Attribute("javaType", "java.util.Date"));

            }


            if (isBigType(column.getJdbcTypeName())) {
                resultElement.addAttribute(new Attribute("jdbcType", column.getJdbcTypeName()));

            }

            resultMap.addElement(resultElement);


        }


        return resultMap;

    }


    private class TableComment {

        private String name;
        private String comment;

        public TableComment(String name, String comment) {
            this.name = name;
            this.comment = comment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
