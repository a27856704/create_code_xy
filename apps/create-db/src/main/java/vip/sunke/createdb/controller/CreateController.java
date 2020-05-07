package vip.sunke.createdb.controller;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import vip.sunke.CreateDbApplication;
import vip.sunke.common.FileUtil;
import vip.sunke.common.SkList;
import vip.sunke.common.StringUtil;
import vip.sunke.common.ZipUtils;
import vip.sunke.createdb.config.SkDatasource;
import vip.sunke.createdb.dto.FieldDto;
import vip.sunke.createdb.dto.FieldValueDto;
import vip.sunke.createdb.dto.GeneratorDto;
import vip.sunke.domain.TableComment;
import vip.sunke.domain.TableField;
import vip.sunke.domain.TableKeyColumn;
import vip.sunke.mybatis.*;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.web.common.SkMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sunke
 * @Date 2019-05-05 10:35
 * @description
 */

@Controller
@RequestMapping("/create/")
public class CreateController {


    private static Connection connection;
    @Resource(name = "skDatasource")
    private SkDatasource skDatasource;
    @Value("${xmlDir}")
    private String xmlDir;

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.driver}")
    private String driver;

    @Value("${controllerTemplate}")
    private String controllerTemplate;
    @Value("${searchTemplate}")
    private String searchTemplate;
    @Value("${modelRootClass}")
    private String modelRootClass;


    public static void emptyConnection() {
        connection = null;
    }

    public Connection getCreateConnection() throws Exception {


        if (StringUtil.isEmpty(CreateDbApplication.getDB()))
            throw new BusinessException("请先选择数据库");


        synchronized (CreateController.class) {
            if (connection == null)
                return DriverManager.getConnection(getUrl(), skDatasource.getUsername(), skDatasource.getPassword());
        }
        return connection;
    }

    public void closeConn(ResultSet resultSet, Statement statement, Connection conn) {

        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getUrl() {
        try {

            String[] dbUrl = skDatasource.getUrl().split("\\?");
            String url = dbUrl[0].substring(0, dbUrl[0].lastIndexOf("/") + 1) + CreateDbApplication.getDB() + "?" + dbUrl[1];

            return url;

        } catch (Exception e) {


        }

        return "jdbc:mysql://127.0.0.1:3306/" + CreateDbApplication.getDB() + "?useSSL=true&useUnicode=true";


    }

    public String getBaseView() {
        return "create/";
    }

    public String getBaseRoute() {
        return "/create/";
    }

    @GetMapping("db")
    public String db(Model model) throws Exception {

        model.addAttribute("action", getBaseRoute() + "/postCreateDB");


        return getBaseView() + "db";
    }

    @PostMapping("/postCreateDB")
    @ResponseBody
    public SkMap postCreateDB(String dbName) throws Exception {


        Class.forName(skDatasource.getDriverClassName());

        //一开始必须填一个已经存在的数据库
        String url = skDatasource.getUrl();
        Connection conn = DriverManager.getConnection(url, skDatasource.getUsername(), skDatasource.getPassword());
        Statement stat = conn.createStatement();

        CreateDbApplication.setDB(dbName);

        //创建数据库hello
        stat.executeUpdate(" CREATE DATABASE `" + dbName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;");


        closeConn(null, stat, conn);


        return SkMap.ok();


    }

    /**
     * 创建表页面
     *
     * @param model
     * @return
     * @throws BusinessException
     */
    @GetMapping("table")
    public String table(Model model) throws BusinessException {


        model.addAttribute("db", CreateDbApplication.getDB());
        model.addAttribute("action", getBaseRoute() + "/postTable");

        model.addAttribute("databaseList", getDataBases());

        return getBaseView() + "table";


    }

    /**
     * 数据表页面
     *
     * @param model
     * @return
     * @throws BusinessException
     */
    @GetMapping("tableList")
    public String tableList(Model model, @RequestParam(required = false, defaultValue = "") String db) throws BusinessException {

        if (StringUtil.isEmpty(db)) {
            db = CreateDbApplication.getDB();
        } else {
            CreateDbApplication.setDB(db);
        }
        model.addAttribute("db", db);
        model.addAttribute("databaseList", getDataBases());
        model.addAttribute("tableList", null);


        if (!StringUtil.isEmpty(db)) {

            model.addAttribute("tableList", getTableCommentList(db));

        }

        return getBaseView() + "tableList";


    }

    /**
     * 数据表字段列表页面
     *
     * @param model
     * @return
     * @throws BusinessException
     */
    @GetMapping("fieldList")
    public String fieldList(Model model,

                            @RequestParam(required = false, defaultValue = "") String table) throws BusinessException {

        if (StringUtil.isEmpty(table) || StringUtil.isEmpty(CreateDbApplication.getDB())) {

            return "redirect:" + getBaseRoute() + "tableList";

        }

        model.addAttribute("fieldList", getTableFieldList(CreateDbApplication.getDB(), table));
        model.addAttribute("table", table);


        return getBaseView() + "fieldList";
    }


    /**
     * 删除字段
     *
     * @param table
     * @param field
     * @return
     */

    @PostMapping("postDelField")
    @ResponseBody
    public SkMap postDelField(String table, String field) throws Exception {


        String sql = "ALTER TABLE " + table + " DROP COLUMN  " + field;


        executeSql(sql.toString());


        return SkMap.ok();


    }


    /**
     * 生成类文件
     *
     * @param model
     * @return
     * @throws BusinessException
     */
    @GetMapping("file")
    public String file(Model model) throws BusinessException {

        String db = CreateDbApplication.getDB();
        List<TableComment> tableList = null;
        if (StringUtil.isNotEmpty(db)) {
            tableList = getTableCommentList(db);
            model.addAttribute("tableList", tableList);
        }

        model.addAttribute("db", db);
        model.addAttribute("url", url);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("driver", driver);

        model.addAttribute("controllerTemplate", controllerTemplate);
        model.addAttribute("searchTemplate", searchTemplate);


        model.addAttribute("action", getBaseRoute() + "postFile");


        model.addAttribute("databaseList", getDataBases());
        model.addAttribute("createDir", xmlDir + "/create");
        //model.addAttribute("modelRootClass", "vip.sunke.pubInter.BaseTimeDoMain");

        model.addAttribute("modelRootClass", modelRootClass);
        model.addAttribute("searchTemplateList", new SkList<String>().addObjToList("Search.ftl").addObjToList("SourceSearch.ftl"));
        model.addAttribute("modelTemplateList", new SkList<String>().addObjToList("BaseTimeDoMain").addObjToList("BaseSourceDoMain"));
        model.addAttribute("controllerTemplateList", new SkList<String>().addObjToList("BackController.ftl").addObjToList("RestfulController.ftl"));


        return getBaseView() + "file";


    }

    /**
     * 生成类文件
     *
     * @param generatorDto
     * @throws Exception
     */
    @PostMapping("postFile")

    public void postFile(GeneratorDto generatorDto, HttpServletResponse response) throws Exception {

        String db = generatorDto.getDb();

        Main.clearMenuList();
        CreateDbApplication.setDB(db);
        List<TableKeyColumn> list = getTableKeyColumnList(db);
        List<TableKeyColumn> tableList = null;
        List<String> tableNameList = generatorDto.getTableList();
        if (StringUtil.isNotObjEmpty(tableNameList)) {

            tableList = list.parallelStream().filter(tableKeyColumn ->

                    tableNameList.stream().anyMatch(name -> tableKeyColumn.getTableName().equalsIgnoreCase(name))

            ).collect(Collectors.toList());


        } else {
            tableList = list;
        }


        generatorDto.setConnectionUrl("jdbc:mysql://" + generatorDto.getConnectionUrl() + "/" + generatorDto.getDb() + (StringUtil.isEmpty(generatorDto.getParam()) ? "" : "?" + generatorDto.getParam()));


        FileUtil.mkdirs(generatorDto.getTargetProject());


        String tempPage = "/";

        FreeMarkerUtil freeMarkerUtil = FreeMarkerUtil.getInstance("2.3.28", tempPage);


        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put("tableList", tableList);
        dataMap.put("driveJar", StringUtil.isNullToDefault(generatorDto.getDriveJar(), "/sk/lib/mysql-connector-java-5.1.20-bin.jar"));
        dataMap.put("projectName", StringUtil.isNullToDefault(generatorDto.getProjectName(), "后台管理系统"));
        dataMap.put("copyright", StringUtil.isNullToDefault(generatorDto.getCopyright(), "vip.sunke"));
        dataMap.put("packageProject", StringUtil.isNullToDefault(generatorDto.getPackageProject(), "vip.sunke"));
        dataMap.put("packageAppProject", StringUtil.isNullToDefault(generatorDto.getPackageAppProject(), "vip.sunke"));
        dataMap.put("targetProject", StringUtil.isNullToDefault(generatorDto.getTargetProject(), xmlDir + "/create"));
        dataMap.put("dbType", StringUtil.isNullToDefault(generatorDto.getDbType(), "mysql"));
        dataMap.put("author", StringUtil.isNullToDefault(generatorDto.getAuthor(), "sunke"));
        dataMap.put("templateDir", StringUtil.isNullToDefault(generatorDto.getTemplateDir(), "/work/vip-sunke/apps/mybatis-generator/src/main/resources/template/feng/templates"));
        dataMap.put("templateRoot", StringUtil.isNullToDefault(generatorDto.getTemplateRoot(), "/work/vip-sunke/apps/mybatis-generator/src/main/resources/template"));
        dataMap.put("driverClass", StringUtil.isNullToDefault(generatorDto.getDriverClass(), "com.mysql.jdbc.Driver"));
        dataMap.put("connectionUrl", StringUtil.isNullToDefault(generatorDto.getConnectionUrl(), "127.0.0.1:3306"));
        dataMap.put("userId", StringUtil.isNullToDefault(generatorDto.getUserId(), "root"));
        dataMap.put("password", StringUtil.isNullToDefault(generatorDto.getPassword(), "123456"));
        dataMap.put("modelTargetPackage", StringUtil.isNullToDefault(generatorDto.getPackageAppProject(), "vip.sunke") + ".model");
        dataMap.put("modelTargetProject", StringUtil.isNullToDefault(generatorDto.getModelTargetProject(), xmlDir + "/create"));
        dataMap.put("activeProfile", CreateDbApplication.getActiveProfile());


        if (StringUtil.isBlank(generatorDto.getModelRootClass())) {
            dataMap.put("modelClass", "BaseTimeDoMain");
            dataMap.put("modelRootClass", StringUtil.isNullToDefault(generatorDto.getPackageProject(), "vip.sunke") + "pubInter.BaseTimeDoMain");
        } else {
            dataMap.put("modelClass", generatorDto.getModelRootClass());
            dataMap.put("modelRootClass", generatorDto.getPackageProject() + ".pubInter." + generatorDto.getModelRootClass());
        }
        dataMap.put("mapperTargetProject", StringUtil.isNullToDefault(generatorDto.getMapperTargetProject(), xmlDir + "/create"));
        dataMap.put("mapperInterfaceTargetPackage", StringUtil.isNullToDefault(generatorDto.getPackageAppProject(), "vip.sunke") + ".dao.mapper");
        dataMap.put("mapperInterfaceTargetProject", StringUtil.isNullToDefault(generatorDto.getMapperInterfaceTargetProject(), xmlDir + "/create"));

        dataMap.put("searchTemplate", StringUtil.isNullToDefault(generatorDto.getSearchTemplate(), "Search.ftl"));
        dataMap.put("controllerTemplate", StringUtil.isNullToDefault(generatorDto.getControllerTemplate(), "BackController.ftl"));
        dataMap.put("route", StringUtil.isNullToDefault(generatorDto.getRoute(), "/back/"));

        String page = "generatorConfig.xml";

        String ftlName = "generatorConfig.ftl";

        String xmlPath = xmlDir + ("/generatorConfig.xml");


        FileUtil.deleteFile(xmlPath);


        freeMarkerUtil.printFile(ftlName, dataMap, page, xmlDir, tempPage);


        File configFile = new File(xmlPath);


        Main.create(configFile);


        response.setContentType("application/zip");

        response.setHeader("Content-disposition", "attachment; filename=project.zip");
        response.setCharacterEncoding("utf-8");


        ZipUtils.toZip(generatorDto.getTargetProject(), response.getOutputStream(), true);



        /*List<String> warnings = new ArrayList<String>();
        SkConfigurationParser cp = new SkConfigurationParser(warnings);
        Configuration config = null;
        //true:覆盖生成
        SkDefaultShellCallback callback = new SkDefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = null;
        try {
            config = cp.parseConfiguration(configFile);
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.err.println("代码成功生成!");
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }


    /**
     * 生成文档页面
     *
     * @param model
     * @return
     * @throws BusinessException
     */
    @GetMapping("doc")
    public String doc(Model model) throws BusinessException {


        model.addAttribute("db", CreateDbApplication.getDB());

        model.addAttribute("action", getBaseRoute() + "postDoc");


        model.addAttribute("databaseList", getDataBases());

        return getBaseView() + "doc";


    }

    @PostMapping("postDoc")

    public void postDoc(String db, HttpServletResponse response) throws Exception {

        String fileName = URLDecoder.decode(db + "数据库设计.doc", "UTF-8");
        fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        response.setContentType("application/msword");


        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("utf-8");


        CreateDbApplication.setDB(db);

        List<TableComment> tableCommentList = getTableCommentList(db);
        Map<String, Object> dataMap = new HashMap<>();


        if (tableCommentList != null) {


            for (TableComment tableComment : tableCommentList) {

                tableComment.setFieldList(getTableFieldList(db, tableComment.getName()));

            }


            dataMap.put("list", tableCommentList);


        }
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        path = "/";


        FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil(path);


        //  freeMarkerUtil.printFile("database.ftl", dataMap, "数据库.doc", "/sk/upload", "/");

        OutputStream out = response.getOutputStream();

        freeMarkerUtil.print("database.ftl", dataMap, path, out);


    }


    /**
     * 生成文档
     *
     * @param db
     * @throws Exception
     */
    @PostMapping("postDoc1")
    public void postDoc1(String db, HttpServletResponse response) throws Exception {


        String fileName = URLDecoder.decode("数据库设计.doc", "UTF-8");
        fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        response.setContentType("application/msword");


        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("utf-8");

        XWPFDocument document = new XWPFDocument();

        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("数据库表设计");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(17);
        titleParagraphRun.setBold(true);


        CreateDbApplication.setDB(db);

        List<TableComment> tableCommentList = getTableCommentList(db);

        if (tableCommentList != null) {
            createWordLineBreak(document);

            int index = 0;

            for (TableComment tableComment : tableCommentList) {

                createLine(document, (index + 1) + "、" + tableComment.getComment() + "(" + tableComment.getName() + ")");


                List<TableField> fieldList = getTableFieldList(db, tableComment.getName());

                if (fieldList == null && fieldList.size() == 0)
                    return;

                XWPFTable table = document.createTable(fieldList.size() + 1, 3);

                // table.setWidth("100%");


                table.setTableAlignment(TableRowAlign.LEFT);

/*

                CTTbl ttbl=table.getCTTbl();

                CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
                CTTblLayoutType t = tblPr.isSetTblLayout()?tblPr.getTblLayout():tblPr.addNewTblLayout();
                t.setType(STTblLayoutType.FIXED);//使布局固定，不随内容改变宽度
*/


                // 获取到刚刚插入的行
                XWPFTableRow row = table.getRow(0);
                // 设置单元格内容

                int cellIndex = 0;
                XWPFTableCell cell = row.getCell(cellIndex);
                setCell(cell, 30, "字段名", true);


                cellIndex = cellIndex + 1;
                cell = row.getCell(cellIndex);

                setCell(cell, 25, "类型", true);

                cellIndex = cellIndex + 1;
                cell = row.getCell(cellIndex);
                setCell(cell, 45, "说明", true);


                int rowIndex = 1;

                for (TableField field : fieldList) {

                    row = table.getRow(rowIndex++);


                    cellIndex = 0;
                    cell = row.getCell(cellIndex);
                    setCell(cell, 30, field.getName(), false);


                    cellIndex = cellIndex + 1;
                    cell = row.getCell(cellIndex);

                    setCell(cell, 25, field.getType(), false);


                    cellIndex = cellIndex + 1;
                    cell = row.getCell(cellIndex);

                    String comment = field.getInfo();


                    setCell(cell, 45, comment, false);


                }

                document.setTable(index, table);
                createWordLineBreak(document);

                index = index + 1;


            }


        }


        OutputStream out = response.getOutputStream();

        document.write(out);
        out.close();

    }

    private void createLine(XWPFDocument document, String content) {
        createLine(document, content, 14, true);

    }

    private void createLine(XWPFDocument document, String content, int fontSize, boolean bold) {
        XWPFParagraph line = document.createParagraph();


        //设置段落居中
        line.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun lineRun = line.createRun();
        lineRun.setText(content);
        lineRun.setColor("000000");
        lineRun.setFontSize(fontSize);
        lineRun.setBold(bold);


    }


    /**
     * word换行
     *
     * @param document
     */
    private void createWordLineBreak(XWPFDocument document) {
        //换行
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun paragraphRun1 = paragraph1.createRun();
        paragraphRun1.setText("\r");
    }

    /**
     * 显示当前所有的数据库
     *
     * @return
     */

    public List<String> getDataBases() {


        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            //一开始必须填一个已经存在的数据库
            String url = skDatasource.getUrl();
            conn = DriverManager.getConnection(url, skDatasource.getUsername(), skDatasource.getPassword());

            stat = conn.createStatement();
            resultSet = stat.executeQuery("show DATABASES;");

            List<String> dataBaseList = new ArrayList<String>();

            String databaseName = "";
            while (resultSet.next()) {
                databaseName = resultSet.getString(1);
                if ("information_schema".equalsIgnoreCase(databaseName)
                        || "mysql".equalsIgnoreCase(databaseName)
                        || "sys".equalsIgnoreCase(databaseName)

                        || "performance_schema".equalsIgnoreCase(databaseName))
                    continue;

                dataBaseList.add(databaseName);

            }


            return dataBaseList;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(resultSet, stat, conn);


        }


        return null;


    }


    @PostMapping("getTables")
    @ResponseBody
    public SkMap getTables(String db) {

        CreateDbApplication.setDB(db);
        return SkMap.ok("tableList", getTables());


    }


    /**
     * 返回数据库的所有表内字段和注释
     *
     * @param table
     * @return
     */
    public List<TableField> getTableFieldList(String db, String table) {

        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {

            conn = getCreateConnection();
            stat = conn.createStatement();
            String sql = "select column_name,column_type,column_comment from information_schema.columns where table_schema ='" + db + "' and table_name='" + table + "';";
            resultSet = stat.executeQuery(sql);

            List<TableField> tableList = new ArrayList<TableField>();
            TableField tableField = null;
            while (resultSet.next()) {
                tableField = new TableField();
                tableField.setName(resultSet.getString("column_name"));
                tableField.setType(resultSet.getString("column_type"));
                tableField.setComment(resultSet.getString("column_comment"));


                tableList.add(tableField);

            }

            return tableList;


        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            closeConn(resultSet, stat, conn);

        }

        return null;


    }


    /**
     * 返回数据库的所有表名和注释
     *
     * @param db
     * @return
     */
    public List<TableKeyColumn> getTableKeyColumnList(String db) {

        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {

            conn = getCreateConnection();
            stat = conn.createStatement();

            String sql = "select  table_name,column_name from  INFORMATION_SCHEMA.KEY_COLUMN_USAGE  t where t.table_schema = '" + db + "'and constraint_name='PRIMARY';";
            resultSet = stat.executeQuery(sql);

            List<TableKeyColumn> tableList = new ArrayList<TableKeyColumn>();
            TableKeyColumn tableKeyColumn = null;

            while (resultSet.next()) {
                tableKeyColumn = new TableKeyColumn();
                tableKeyColumn.setKeyColumn(resultSet.getString("column_name"));
                tableKeyColumn.setTableName(resultSet.getString("table_name"));
                tableKeyColumn.setPrefix(tableKeyColumn.getKeyColumn().substring(0, tableKeyColumn.getKeyColumn().indexOf("_") + 1));
                tableKeyColumn.setObjectName(BeanName.getFirstUpperCase(BeanName.camelCaseName(tableKeyColumn.getTableName())));
                tableList.add(tableKeyColumn);

            }

            return tableList;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(resultSet, stat, conn);
        }

        return null;


    }

    /**
     * 返回数据库的所有表名和注释
     *
     * @param db
     * @return
     */
    public List<TableComment> getTableCommentList(String db) {

        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {

            conn = getCreateConnection();
            stat = conn.createStatement();

            String sql = "select table_name,table_comment from information_schema.tables where table_schema = '" + db + "';";
            resultSet = stat.executeQuery(sql);

            List<TableComment> tableList = new ArrayList<TableComment>();
            while (resultSet.next()) {
                tableList.add(new TableComment(resultSet.getString("table_name"), resultSet.getString("table_comment"), null));

            }

            return tableList;


        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            closeConn(resultSet, stat, conn);
        }

        return null;


    }


    public List<String> getTables() {
        Statement stat = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {

            conn = getCreateConnection();
            stat = conn.createStatement();
            resultSet = stat.executeQuery("show tables;");

            List<String> tableList = new ArrayList<String>();
            while (resultSet.next()) {
                tableList.add(resultSet.getString(1));

            }


            return tableList;


        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            closeConn(resultSet, stat, conn);

        }


        return null;


    }


    @PostMapping("postTable")
    @ResponseBody
    public SkMap postTable(String db, String tableName, String fieldPrefix, String tableDesc, boolean sourceFlag) throws Exception {


        CreateDbApplication.setFieldPrefix("");
        CreateDbApplication.setTABLE("");

        if (db != null && !db.equalsIgnoreCase(CreateDbApplication.getDB())) {

            CreateDbApplication.setDB(db);
        }

        if (StringUtil.isEmpty(tableName)) {
            return SkMap.ok();
        }


        if (StringUtil.isEmpty(CreateDbApplication.getDB()))
            throw new BusinessException("请先创建数据库");


        CreateDbApplication.setFieldPrefix(fieldPrefix);
        createTable(tableName, CreateDbApplication.getFieldPrefix(), tableDesc, sourceFlag);
        CreateDbApplication.setTABLE(tableName);


        return SkMap.ok();

    }


    /**
     * 添加字段
     *
     * @param model
     * @return
     * @throws BusinessException
     */
    @GetMapping("field")
    public String field(Model model) throws BusinessException {


        model.addAttribute("db", CreateDbApplication.getDB());
        model.addAttribute("table", CreateDbApplication.getTABLE());

        model.addAttribute("databaseList", getDataBases());

        model.addAttribute("tableList", getTables());


        model.addAttribute("fieldPrefix", CreateDbApplication.getFieldPrefix());


        model.addAttribute("action", getBaseRoute() + "/postField");
        model.addAttribute("searchFlags", SearchTypeEnum.values());
        model.addAttribute("inputTypes", InputTypeEnum.values());
        model.addAttribute("fieldTypes", FieldTypeEnum.values());


        return getBaseView() + "field";
    }


    @PostMapping("postField")
    @ResponseBody
    public SkMap postField(@RequestBody FieldDto fieldDto) throws Exception {


        if (!fieldDto.getDb().equalsIgnoreCase(CreateDbApplication.getDB()))
            CreateDbApplication.setDB(fieldDto.getDb());


        if (fieldDto.getInputType() == InputTypeEnum.CHECKBOX.getType()) {


            if ("int".equalsIgnoreCase(fieldDto.getType()) && fieldDto.getSearchFlag() == SearchTypeEnum.BIT.getType()) {

            } else {


                if (

                        "float".equalsIgnoreCase(fieldDto.getType())

                                || "double".equalsIgnoreCase(fieldDto.getType())
                                || "decimal".equalsIgnoreCase(fieldDto.getType())


                ) {
                    if (fieldDto.getSearchFlag() != SearchTypeEnum.BIT.getType()) {

                        throw new BusinessException("复选框要选择数据类型为字符");

                    }


                }
            }


        }


        CreateDbApplication.setTABLE(fieldDto.getTableName());

        CreateDbApplication.setFieldPrefix(fieldDto.getFieldPrefix());


        if (StringUtil.isEmpty(CreateDbApplication.getTABLE()))
            throw new BusinessException("请先创建数据表");


        StringBuffer sql = new StringBuffer();


        sql.append("ALTER TABLE `" + CreateDbApplication.getDB() + "`.`" + CreateDbApplication.getTABLE() + "` ADD COLUMN");


        buildFields(fieldDto, sql);
        executeSql(sql.toString());

        return SkMap.ok();
    }


    private boolean executeSql(String sql) throws Exception {

        Statement stat = null;
        Connection conn = null;
        try {
            conn = getCreateConnection();
            stat = conn.createStatement();

            //创建表test
            stat.executeUpdate(sql.toString());

        } finally {
            closeConn(null, stat, conn);
        }


        return true;

    }


    private boolean createTable(String tableName, String fieldPrefix, String tableDesc, boolean sourceFlag) throws Exception {


        StringBuffer sql = new StringBuffer();
        String key = "`" + fieldPrefix + "id`";
        String keyStr = key + " varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键'";
        String createTimeStr = "`" + fieldPrefix + "create_time` datetime  COMMENT '创建时间|0|0|0|0|1'";
        String source = "`" + fieldPrefix + "source` int(11)  COMMENT '来源|0|0|0|0|1'";
        String updateTimeStr = "`" + fieldPrefix + "update_time` datetime  COMMENT '更新时间|0|0|0|0|1'";


        sql.append("CREATE TABLE `" + CreateDbApplication.getDB() + "`.`" + tableName + "` (");

        sql.append(keyStr + ",");
        if (sourceFlag)
            sql.append(source + ",");
        sql.append(createTimeStr + ",");
        sql.append(updateTimeStr + ",");


        sql.append("PRIMARY KEY (" + key + ")");
        sql.append(") CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='" + tableDesc + "';");

        return executeSql(sql.toString());


    }


    private String getRemark(FieldDto fieldDto) {

        if (fieldDto == null)
            return "";

        StringBuffer remark = new StringBuffer();

        remark.append(fieldDto.getDescName());
        remark.append("|");

        remark.append(fieldDto.getSearchFlag());
        remark.append("|");

        remark.append(fieldDto.isShowPage() ? "1" : "0");
        remark.append("|");


        if (fieldDto.isShowListPage() && fieldDto.isShowDetailPage()) {
            remark.append("2");

        } else if (fieldDto.isShowListPage()) {
            remark.append("1");

        } else {
            remark.append("0");
        }

        remark.append("|");
     /*   remark.append(fieldDto.isShowListPage() ? "1" : "0");
        remark.append("|");*/

        remark.append(fieldDto.isNeed() ? "1" : "0");
        remark.append("|");

        remark.append(fieldDto.getInputType());


        List<FieldValueDto> valueList = fieldDto.getFieldValueList();

        if (valueList != null && valueList.size() > 0) {
            remark.append("|");
            for (FieldValueDto valueObj : valueList) {

                remark.append(valueObj.getValue());
                remark.append("@");
                remark.append(valueObj.getDesc());
                remark.append("@");
                remark.append(valueObj.getEnName());
                remark.append(",");
            }

            return remark.substring(0, remark.length() - 1);

        }


        return remark.toString();
    }

    private void buildFields(FieldDto fieldDto, StringBuffer sql) {


        sql.append("`" + CreateDbApplication.getFieldPrefix() + fieldDto.getName() + "`");
        sql.append(" " + fieldDto.getType() + "");
        if (fieldDto.getLength() > 0) {
            if (FieldTypeEnum.getEnumByType(fieldDto.getType()).isDecimals() && fieldDto.getDecimals() > 0) {
                sql.append("(" + fieldDto.getLength() + "," + fieldDto.getDecimals() + ")");
            } else {
                sql.append("(" + fieldDto.getLength() + ")");
            }

        }
        if (!fieldDto.isNullable()) {

            sql.append(" NOT NULL");
        }
        if (!StringUtil.isEmpty(fieldDto.getDefaultValue())) {
            sql.append(" DEFAULT '" + fieldDto.getDefaultValue() + "'");
        }
        if ("varchar".equalsIgnoreCase(fieldDto.getType())) {
            sql.append(" CHARACTER SET utf8mb4 COLLATE utf8mb4_bin");
        }
        sql.append(" COMMENT '" + getRemark(fieldDto) + "'");

        sql.append(";");


    }

    private void setCell(XWPFTableCell cell, int w, String content, boolean head) {

        cell.setWidth(w * 1.8 + "%");

        if (head) {
            cell.setColor("f6f6f6");


        }
        cell.setText(content);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

    }
}
