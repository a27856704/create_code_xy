package vip.sunke.createdb.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vip.sunke.CreateDbApplication;
import vip.sunke.common.*;
import vip.sunke.createdb.config.SkDatasource;
import vip.sunke.createdb.dto.FieldDto;
import vip.sunke.createdb.dto.FieldValueDto;
import vip.sunke.createdb.dto.GeneratorDto;
import vip.sunke.createdb.tools.WordListen;
import vip.sunke.createdb.tools.WordUtils;
import vip.sunke.domain.TableComment;
import vip.sunke.domain.TableField;
import vip.sunke.domain.TableKeyColumn;
import vip.sunke.mybatis.*;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.web.common.SkJsonResult;
import vip.sunke.web.common.SkMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
@Slf4j
@Controller
@RequestMapping("/create/")
public class CreateController {


    private static Connection connection;
    @Resource(name = "skDatasource")
    private SkDatasource skDatasource;
    @Value("${xmlDir}")
    private String xmlDir;
    @Value("${file.upload.tempRoot}")
    private String tempRoot;

    @Value("${datasource.url}")
    private String url;
    /* @Value("${datasource.urlParam}")
     private String urlParam;*/
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


        if (StringUtil.isEmpty(CreateDbApplication.getDB())) {
            throw new BusinessException("请先选择数据库");
        }


        synchronized (CreateController.class) {
            if (connection == null) {
                return DriverManager.getConnection(getUrl(), skDatasource.getUsername(), skDatasource.getPassword());
            }
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

    /**
     * 生成admin
     *
     * @throws Exception
     */
    private void createAdminTable() throws Exception {

        String sql = "DROP TABLE IF EXISTS `admin`;";

        executeSql(sql);

        sql = "CREATE TABLE `admin` (\n" +
                "  `a_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',\n" +
                "  `a_create_time` datetime DEFAULT NULL COMMENT '创建时间|0|0|0|0|1',\n" +
                "  `a_update_time` datetime DEFAULT NULL COMMENT '更新时间|0|0|0|0|1',\n" +
                "  `a_username` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名|2|1|1|0|1',\n" +
                "  `a_password` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码|0|1|0|0|1',\n" +
                "  `a_truename` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名|0|1|1|0|1',\n" +
                "  `a_phone` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号码|1|1|1|0|1',\n" +
                "  `a_forbidden` int(2) DEFAULT '0' COMMENT '是否禁用|4|1|1|0|1',\n" +
                "  `a_role_id` int(4) DEFAULT '1' COMMENT '角色|2|1|1|0|2|1@客服@Customer,2@账务@Finance,3@管理员@manage',\n" +
                "  `a_sort_num`  int(11)  COMMENT '排序|3|1|1|0|1'," +
                "  `a_show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|1|1|0|2|1@是@Y,0@否@N'," +
                "  `a_del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'," +
                "  PRIMARY KEY (`a_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统用户';\n";

        executeSql(sql);


        String current = YXDate.getFormatDate2String(new java.util.Date());


        sql = "INSERT INTO `admin`  VALUES ('" + IdGen.uuid() + "', '" + current + "', '" + current + "', 'admin', 'e10adc3949ba59abbe56e057f20f883e',  'admin', '13800000000', 0,3,1,1,0);";


        executeSql(sql);


    }


    //权限相关
    private void createRightsRelevance() throws Exception {

        createRightsTable();
        createPermissionGroupTable();
        createGroupRightsTable();
        createAdminGroupTable();


    }


    /**
     * 生成权限
     *
     * @throws Exception
     */
    private void createRightsTable() throws Exception {

        String sql = "DROP TABLE IF EXISTS `rights`;";

        executeSql(sql);

        sql = "CREATE TABLE `rights` (" +
                "  `r_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键|4|0|0|0|1'," +
                "  `r_create_time` datetime DEFAULT NULL COMMENT '创建时间|0|0|0|0|7'," +
                "  `r_update_time` datetime DEFAULT NULL COMMENT '更新时间|0|0|0|0|7'," +
                "  `r_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称|1|1|1|0|1'," +
                "  `r_value` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '路由|1|1|1|0|1'," +
                "  `r_remarks` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注|0|1|0|0|5'," +
                "  `r_up_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '上级ID|2|1|1|0|1'," +
                "  `r_order_num` int(10) DEFAULT NULL COMMENT '同级排序|3|1|1|0|1'," +
                "  `r_menu_flag` int(2) DEFAULT NULL COMMENT '类型|4|1|1|0|2|1@顶部@TOP,2@左侧@LEFT,3@按钮@BUTTON'," +
                "  `r_api_path` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '后端接口|1|1|1|0|1'," +
                "  `r_sort_num`  int(11)  COMMENT '排序|3|1|1|0|1'," +
                "  `r_show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|1|1|0|2|1@是@Y,0@否@N'," +
                "  `r_del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'," +
                "  PRIMARY KEY (`r_id`) USING BTREE" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='权限表';";
        executeSql(sql);

    }

    /**
     * 生成权限组
     *
     * @throws Exception
     */
    private void createPermissionGroupTable() throws Exception {

        String sql = "DROP TABLE IF EXISTS `permission_group`;";

        executeSql(sql);

        sql = "CREATE TABLE `permission_group` (" +
                "  `pg_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键|4|0|0|0|1'," +
                "  `pg_create_time` datetime DEFAULT NULL COMMENT '创建时间|0|0|0|0|7'," +
                "  `pg_update_time` datetime DEFAULT NULL COMMENT '更新时间|0|0|0|0|7'," +
                "  `pg_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限组名|1|1|1|0|1'," +
                "  `pg_remarks` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注|1|1|0|0|5'," +
                "  `pg_sort_num`  int(11)  COMMENT '排序|3|1|1|0|1'," +
                "  `pg_show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|1|1|0|2|1@是@Y,0@否@N'," +
                "  `pg_del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'," +
                "  PRIMARY KEY (`pg_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='权限组';";
        executeSql(sql);

    }


    /**
     * 生成权限组和权限关系表
     *
     * @throws Exception
     */
    private void createGroupRightsTable() throws Exception {

        String sql = "DROP TABLE IF EXISTS `group_rights`;";

        executeSql(sql);

        sql = "CREATE TABLE `group_rights` (" +
                "  `gr_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键|4|0|0|0|1'," +
                "  `gr_create_time` datetime DEFAULT NULL COMMENT '创建时间|0|0|0|0|7'," +
                "  `gr_update_time` datetime DEFAULT NULL COMMENT '更新时间|0|0|0|0|7'," +
                "  `gr_group_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组ID|2|1|1|0|1'," +
                "  `gr_rights_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限ID|2|1|1|0|1'," +
                "  `gr_sort_num`  int(11)  COMMENT '排序|3|1|1|0|1'," +
                "  `gr_show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|1|1|0|2|1@是@Y,0@否@N'," +
                "  `gr_del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'," +
                "  PRIMARY KEY (`gr_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='权限组权限关联';";
        executeSql(sql);

    }

    /**
     * 生成权限组和人员关系表
     *
     * @throws Exception
     */
    private void createAdminGroupTable() throws Exception {

        String sql = "DROP TABLE IF EXISTS `admin_permission_group`;";

        executeSql(sql);

        sql = "CREATE TABLE `admin_permission_group` (" +
                "  `apg_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键|4|0|0|0|1'," +
                "  `apg_create_time` datetime DEFAULT NULL COMMENT '创建时间|0|0|0|0|7'," +
                "  `apg_update_time` datetime DEFAULT NULL COMMENT '更新时间|0|0|0|0|7'," +
                "  `apg_staff_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '员工ID|2|1|1|0|1'," +
                "  `apg_group_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限组ID|2|1|1|0|1'," +
                "  `apg_sort_num`  int(11)  COMMENT '排序|3|1|1|0|1'," +
                "  `apg_show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|1|1|0|2|1@是@Y,0@否@N'," +
                "  `apg_del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'," +
                "  PRIMARY KEY (`apg_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='员工权限组';";
        executeSql(sql);

    }


    /**
     * 生成日志
     *
     * @throws Exception
     */
    private void createSysLogTable() throws Exception {

        String sql = "DROP TABLE IF EXISTS `sys_oper_log`;";

        executeSql(sql);

        sql = "CREATE TABLE `sys_oper_log` (" +
                "  `ol_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键'," +
                "  `ol_create_time` datetime DEFAULT NULL COMMENT '创建时间|0|0|0|0|1'," +
                "  `ol_update_time` datetime DEFAULT NULL COMMENT '更新时间|0|0|0|0|1'," +
                "  `ol_title` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模块标题|1|1|1|0|1'," +
                "  `ol_business_type` int(2) DEFAULT '0' COMMENT '业务类型|4|1|1|0|2|0@其它@OTHER,1@新增@INSERT,2@修改@UPDATE,3@删除@DELETE,4@授权@GRANT,5@导出@EXPORT,6@导入@IMPORT,7@强退@FORCE,8@生成代码@GENCODE,9@清空@CLEAN,10@详情@detail,11@列表@list,12@查询@query'," +
                "  `ol_method` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '方法名称|1|1|1|0|1'," +
                "  `ol_operator_type` int(2) DEFAULT NULL COMMENT '操作类别|4|1|1|0|2|0@其它@OTHER,1@后台用户@MANAGE,2@手机端用户@MOBILE'," +
                "  `ol_oper_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人员|1|1|1|0|1'," +
                "  `ol_dept_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '部门名称|1|1|1|0|1'," +
                "  `ol_oper_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求URL|1|1|1|0|1'," +
                "  `ol_oper_ip` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主机地址|1|1|1|0|1'," +
                "  `ol_oper_location` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作地点|1|1|1|0|1'," +
                "  `ol_oper_param` text COLLATE utf8mb4_bin COMMENT '请求参数|0|1|1|0|1'," +
                "  `ol_status` int(2) DEFAULT '0' COMMENT '操作状态（0正常 1异常）|4|1|1|0|2'," +
                "  `ol_error_msg` text COLLATE utf8mb4_bin COMMENT '错误消息|0|1|1|0|1'," +
                "  `ol_oper_time` datetime DEFAULT NULL COMMENT '操作时间|6|1|1|0|7'," +
                "  `ol_sort_num`  int(11)  COMMENT '排序|3|1|1|0|1'," +
                "  `ol_show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|1|1|0|2|1@是@Y,0@否@N'," +
                "  `ol_del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'," +
                "  PRIMARY KEY (`ol_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='操作日志记录';";

        executeSql(sql);

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

        //创建数据库
//        stat.executeUpdate(" CREATE DATABASE `" + dbName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;");
        stat.executeUpdate("CREATE DATABASE " + dbName
                + "\t WITH \n "
                + "\t OWNER = postgres\n"
                + "\t ENCODING = 'UTF8'\n"
                + "\t TABLESPACE = pg_default\n"
                + "\t CONNECTION LIMIT = -1;\n");

        closeConn(null, stat, conn);

        createSysLogTable();//日志
        createAdminTable();//管理员

        createRightsRelevance();//权限相关


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
     * 数据表页面
     *
     * @return
     * @throws BusinessException
     */
    @GetMapping("tableListToJson")
    @ResponseBody
    public SkJsonResult tableListToJson(@RequestParam(required = false, defaultValue = "") String db) throws BusinessException {

        if (StringUtil.isEmpty(db)) {
            db = CreateDbApplication.getDB();
        } else {
            CreateDbApplication.setDB(db);
        }
        SkJsonResult skJsonResult = SkJsonResult.ok();
        skJsonResult.setData(getTableCommentList(db));

        return skJsonResult;


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


        executeSql(sql);


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
        Main.clearTableIndex();
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


//        generatorDto.setConnectionUrl("jdbc:mysql://" + generatorDto.getConnectionUrl() + "/" + generatorDto.getDb() + (StringUtil.isEmpty(generatorDto.getParam()) ? "" : "?" + generatorDto.getParam()));
        generatorDto.setConnectionUrl(generatorDto.getConnectionUrl() + "/" + generatorDto.getDb() + (StringUtil.isEmpty(generatorDto.getParam()) ? "" : "?" + generatorDto.getParam()));
//        generatorDto.setConnectionUrl("jdbc:postgresql://192.168.110.9:5432/sophon_sharing_video?currentSchema=dictory,public,geo");

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
        dataMap.put("dbType", StringUtil.isNullToDefault(generatorDto.getDbType(), "postgresql"));
//        dataMap.put("dbType", StringUtil.isNullToDefault(generatorDto.getDbType(), "mysql"));
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
        dataMap.put("route", StringUtil.isNullToDefault(generatorDto.getRoute(), "/"));

        String page = "generatorConfig.xml";

        String ftlName = "generatorConfig.ftl";

        String xmlPath = xmlDir + ("/generatorConfig.xml");


        FileUtil.deleteFile(xmlPath);


        freeMarkerUtil.printFile(ftlName, dataMap, page, xmlDir, tempPage);


        File configFile = new File(xmlPath);

//        SkPlugin.dbType = SkPlugin.DB_TYPE_MYSQL;
        SkPlugin.dbType = SkPlugin.DB_TYPE_PGSQL;
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

    /**
     * 临时用
     *
     * @param db
     * @param response
     * @throws Exception
     */
    @GetMapping("addDelFlag")
    public void addDelFlag(@RequestParam String db, HttpServletResponse response) throws Exception {

        CreateDbApplication.setDB(db);

        List<TableComment> tableCommentList = getTableCommentList(db);


        for (TableComment table : tableCommentList) {


            try {


                FieldDto fieldDto = new FieldDto();
                fieldDto.setNullable(true);
                fieldDto.setDb(db);
                fieldDto.setLength(1);

                fieldDto.setTableName(table.getName());
                fieldDto.setName("del_flag");
                fieldDto.setType("int");
                fieldDto.setDefaultValue("0");
                fieldDto.setDescName("是否删除");
                fieldDto.setInputType(InputTypeEnum.RADIO.getType());
                fieldDto.setSearchFlag(SearchTypeEnum.EQUAL.getType());
                fieldDto.setShowDetailPage(false);
                fieldDto.setShowListPage(false);
                fieldDto.setNeed(false);
                fieldDto.setShowPage(false);
                List<FieldValueDto> valueLit = new ArrayList<>();
                FieldValueDto valueDto = new FieldValueDto();
                valueDto.setEnName("Y");
                valueDto.setValue("1");
                valueDto.setDesc("删除");
                valueLit.add(valueDto);
                valueDto = new FieldValueDto();
                valueDto.setEnName("N");
                valueDto.setValue("0");
                valueDto.setDesc("正常");
                valueLit.add(valueDto);

                fieldDto.setFieldValueList(valueLit);


                postField(fieldDto);
                //    executeSql(sql.toString());

            } catch (Exception e) {

            }


        }


    }


    @PostMapping("postDoc")
    public void postDoc(@RequestParam String db, HttpServletResponse response) throws Exception {

        String fileName = null;
        try {
            fileName = URLEncoder.encode(db + "数据库设计" + ".docx", "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);


        CreateDbApplication.setDB(db);

        List<TableComment> tableCommentList = getTableCommentList(db);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("db", db);

        ConfigureBuilder config = Configure.builder().useSpringEL();


        LoopRowTableRenderPolicy loopRowTableRenderPolicy = new LoopRowTableRenderPolicy(true);
        if (tableCommentList != null) {


            for (TableComment tableComment : tableCommentList) {


                List<TableField> fieldList = getTableFieldList(db, tableComment.getName());
                tableComment.setFieldList(fieldList);
                // dataMap.put("fieldList",fieldList);
                config = config.bind("fieldList", loopRowTableRenderPolicy);


            }


            dataMap.put("list", tableCommentList);


        }


        // String wordTemplateRes= ClassUtils.getDefaultClassLoader().getResource("").getPath()+"database.docx";


        // String wordTemplate= "/Users/work/create-code/apps/create-db/src/main/resources/database.docx";


        //  String wordTemplate=tempRoot+File.separator+"database.docx";

        InputStream is = CreateController.class.getResourceAsStream("/database.docx");


        // FileUtil.writeInputStreamToOutputStream(is,new FileOutputStream(new File(wordTemplate)));


        WordUtils.createWord(is, response.getOutputStream(), config.build(), dataMap, new WordListen() {
            @Override
            public void receive(XWPFTemplate xwpfTemplate) {

            }
        });


    }

    /*@PostMapping("postDoc")
    public void postDoc(String db, HttpServletResponse response) throws Exception {

        String fileName = URLDecoder.decode(db + "数据库设计.doc", "UTF-8");
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
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


    }*/


    /**
     * 生成文档
     *
     * @param db
     * @throws Exception
     */
    @PostMapping("postDoc1")
    public void postDoc1(String db, HttpServletResponse response) throws Exception {


        String fileName = URLDecoder.decode("数据库设计.doc", "UTF-8");
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
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

                if (fieldList == null && fieldList.size() == 0) {
                    return;
                }

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
//            resultSet = stat.executeQuery("show DATABASES;");
            resultSet = stat.executeQuery("select datname from pg_database;");

            List<String> dataBaseList = new ArrayList<String>();

            String databaseName = "";
            while (resultSet.next()) {
                databaseName = resultSet.getString(1);
                if ("information_schema".equalsIgnoreCase(databaseName)
                        || "mysql".equalsIgnoreCase(databaseName)
                        || "sys".equalsIgnoreCase(databaseName)

                        || "performance_schema".equalsIgnoreCase(databaseName)) {
                    continue;
                }

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
        return SkMap.ok("tableList", getTableCommentList(db));


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
//            String sql = "select column_name,column_type,column_comment from information_schema.columns where table_schema ='" + db + "' and table_name='" + table + "';";
            String sql = "SELECT  DISTINCT (A.attnum)  ,C.relname table_name," +
                    "CAST ( obj_description ( relfilenode, 'pg_class' ) AS VARCHAR ) table_comment," +
                    "A.attname column_name," +
                    "d.description column_comment," +
                    "concat_ws ( '', T.typname, SUBSTRING ( format_type ( A.atttypid, A.atttypmod ) FROM '\\(.*\\)' ) ) AS column_type " +
                    "FROM" +
                    " information_schema.COLUMNS s," +
                    "pg_class C," +
                    "pg_attribute A LEFT JOIN  pg_description d on  (d.objoid = A.attrelid AND d.objsubid = A.attnum )," +
                    "pg_type T" +
                    "  " +
                    "WHERE A.attnum > 0 " +
                    "AND A.attrelid = ( SELECT oid FROM pg_class WHERE relname = s.TABLE_NAME )" +
                    "AND A.attrelid = C.oid " +
                    "AND A.atttypid = T.oid " +
//                    "AND C.relname IN ( SELECT tablename FROM pg_tables WHERE (schemaname = 'geo' or schemaname='dictory') AND POSITION ( '_2' IN tablename ) = 0 ) " +
                    "AND C.relname='" + table + "'" +
                    "ORDER BY " +
                    "C.relname," +
                    "A.attnum;";


            System.out.println(sql + "\n");
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

//            String sql = "select  table_name,column_name from  INFORMATION_SCHEMA.KEY_COLUMN_USAGE  t where t.table_schema = '" + db + "'and constraint_name='PRIMARY';";
            String sql = "\t\tselect \n" +
                    "       kcu.table_name,\n" +
                    "       kcu.column_name as column_name\n" +
                    "from information_schema.table_constraints tco\n" +
                    "join information_schema.key_column_usage kcu \n" +
                    "     on kcu.constraint_name = tco.constraint_name\n" +
                    "     and kcu.constraint_schema = tco.constraint_schema\n" +
                    "     and kcu.constraint_name = tco.constraint_name\n" +
                    "where tco.constraint_type = 'PRIMARY KEY'\n" +
                    "and ( kcu.table_schema = 'geo' OR kcu.table_schema = 'dictory' )\n" +
                    "and kcu.ordinal_position=1 and kcu.table_catalog= '" + db + "';";
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

//            String sql = "select table_name,table_comment,create_time from information_schema.tables where table_schema = '" + db + "' order by table_name asc;";
            String sql = "SELECT DISTINCT ON\n" +
                    "\t( relname ) relname as table_name,\n" +
                    "\tpg_description.description AS table_comment \n" +
                    "FROM\n" +
                    "\tpg_description\n" +
                    "\tJOIN pg_class ON pg_description.objoid = pg_class.oid\n" +
                    "\tLEFT JOIN information_schema.COLUMNS B ON pg_class.relname = B.TABLE_NAME \n" +
                    "WHERE\n" +
                    "\t( B.table_schema = 'geo' OR B.table_schema = 'dictory' ) \n" +
                    "\tAND pg_description.objsubid =0 and B.table_catalog ='" + db + "';";

            log.debug("sql:{}", sql);
            resultSet = stat.executeQuery(sql);

            List<TableComment> tableList = new ArrayList<TableComment>();
            int index = 1;
            while (resultSet.next()) {
                tableList.add(new TableComment(resultSet.getString("table_name"), resultSet.getString("table_comment"), new Date(System.currentTimeMillis()), index + "", null));
                index = index + 1;
            }

            return tableList;


        } catch (Exception e) {
            e.printStackTrace();
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
//            resultSet = stat.executeQuery("show tables;");
//            resultSet = stat.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'dictory';");
            resultSet = stat.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'geo';");

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


        CreateDbApplication.setTABLE("");
        CreateDbApplication.setFieldPrefix("");

        if (db != null && !db.equalsIgnoreCase(CreateDbApplication.getDB())) {

            CreateDbApplication.setDB(db);
        }

        if (StringUtil.isEmpty(tableName)) {
            return SkMap.ok();
        }


        if (StringUtil.isEmpty(CreateDbApplication.getDB())) {
            throw new BusinessException("请先创建数据库");
        }

        CreateDbApplication.setTABLE(tableName);
        CreateDbApplication.setFieldPrefix(fieldPrefix);
        createTable(tableName, CreateDbApplication.getFieldPrefix(), tableDesc, sourceFlag);


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

        model.addAttribute("tableList", getTableCommentList(CreateDbApplication.getDB()));


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


        if (!fieldDto.getDb().equalsIgnoreCase(CreateDbApplication.getDB())) {
            CreateDbApplication.setDB(fieldDto.getDb());
        }

        if (fieldDto.getLength() == 0 && "varchar".equalsIgnoreCase(fieldDto.getType())) {
            fieldDto.setLength(50);
        }

        if ("int".equalsIgnoreCase(fieldDto.getType())

                && fieldDto.getInputType() == InputTypeEnum.RADIO.getType()
                && fieldDto.getInputType() == InputTypeEnum.CHECKBOX.getType()
                && (fieldDto.getFieldValueList() == null || fieldDto.getFieldValueList().size() == 0)


        ) {
            List<FieldValueDto> fvList = new ArrayList<>();

            FieldValueDto dto = new FieldValueDto();
            dto.setEnName("YES");
            dto.setDesc("是");
            dto.setValue("1");
            fvList.add(dto);

            dto = new FieldValueDto();
            dto.setEnName("NO");
            dto.setDesc("否");
            dto.setValue("0");
            fvList.add(dto);
            fieldDto.setFieldValueList(fvList);

        }


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


        if (StringUtil.isEmpty(CreateDbApplication.getTABLE())) {
            throw new BusinessException("请先创建数据表");
        }


        StringBuffer sql = new StringBuffer();


        sql.append("ALTER TABLE `" + CreateDbApplication.getDB() + "`.`" + CreateDbApplication.getTABLE() + "` ADD COLUMN ");


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
            stat.executeUpdate(sql);

        } finally {
            closeConn(null, stat, conn);
        }


        return true;

    }


    private boolean createTable(String tableName, String fieldPrefix, String tableDesc, boolean sourceFlag) throws Exception {
       /* StringBuffer sql = new StringBuffer();
        String key = "`" + fieldPrefix + "id`";
        String keyStr = key + " varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键|4|0|0|0|1'";
        String createTimeStr = "`" + fieldPrefix + "create_time` datetime  COMMENT '创建时间|0|0|1|0|7'";
        String source = "`" + fieldPrefix + "source` int(11)  COMMENT '来源|0|0|0|0|1'";
        String updateTimeStr = "`" + fieldPrefix + "update_time` datetime  COMMENT '更新时间|0|0|1|0|7'";
        String sortNumStr = "`" + fieldPrefix + "sort_num` int(11)  COMMENT '排序|3|0|1|0|1'";
        String showFlagStr = "`" + fieldPrefix + "show_flag` int(2) NULL DEFAULT 1 COMMENT '是否显示|4|0|1|0|2|1@是@Y,0@否@N'";
        String delFlagStr = "`" + fieldPrefix + "del_flag` int(2) NULL DEFAULT 0 COMMENT '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N'";


        sql.append("CREATE TABLE `" + CreateDbApplication.getDB() + "`.`" + tableName + "` (");

        sql.append(keyStr + ",");
        if (sourceFlag) {
            sql.append(source + ",");
        }
        sql.append(createTimeStr + ",");
        sql.append(updateTimeStr + ",");
        sql.append(sortNumStr + ",");
        sql.append(showFlagStr + ",");
        sql.append(delFlagStr + ",");


        sql.append("PRIMARY KEY (" + key + ")");
        sql.append(") CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='" + tableDesc + "';");*/

        StringBuffer sql = new StringBuffer();


        String key = "" + fieldPrefix + "id";
        String keyStr = key + " character varying(32) COLLATE pg_catalog.\"default\"";
        String createTimeStr = "" + fieldPrefix + "create_time timestamp(6) without time zone";
        String source = "" + fieldPrefix + "source integer";
        String updateTimeStr = "" + fieldPrefix + "update_time timestamp(6) without time zone";
        String sortNumStr = "" + fieldPrefix + "sort_num integer";
        String showFlagStr = "" + fieldPrefix + "show_flag integer";
        String delFlagStr = "" + fieldPrefix + "del_flag integer";

//        sql.append("CREATE TABLE IF NOT EXISTS " + CreateDbApplication.getDB() + "." + tableName + " (\n");
        sql.append("set search_path = 'geo',\"$user\",public, topology;\n");
        sql.append("CREATE TABLE IF NOT EXISTS  " + tableName + "\n");
        sql.append(" (\n");
        sql.append(keyStr + ",\n");
        if (sourceFlag) {
            sql.append(source + ",\n");
        }
        sql.append(createTimeStr + ",\n");
        sql.append(updateTimeStr + ",\n");
        sql.append(sortNumStr + ",\n");
        sql.append(showFlagStr + ",\n");
        sql.append(delFlagStr + ",\n");


        sql.append("CONSTRAINT " + tableName + "_pkey" + " PRIMARY KEY (" + key + ") \n");
        sql.append(") \n");

        sql.append(" WITH (\n");
        sql.append("         OIDS = FALSE\n");
        sql.append(" )\n");
        sql.append(" TABLESPACE pg_default;\n");

        sql.append(" ALTER TABLE IF EXISTS " + tableName + " OWNER to postgres;\n");
        sql.append(" COMMENT ON TABLE  " + tableName + " is '" + tableDesc + " ';\n");
        sql.append(" COMMENT ON COLUMN " + tableName + "." + key + "        IS '主键|4|0|0|0|1';\n");

        sql.append(" COMMENT ON COLUMN " + tableName + "." + fieldPrefix + "create_time" + "        IS '创建时间|0|0|1|0|7';\n");

        sql.append(" COMMENT ON COLUMN " + tableName + "." + fieldPrefix + "source" + "       IS '来源|0|0|0|0|1';\n");

        sql.append(" COMMENT ON COLUMN " + tableName + "." + fieldPrefix + "update_time" + "       IS '更新时间|0|0|1|0|7';\n");

        sql.append(" COMMENT ON COLUMN " + tableName + "." + fieldPrefix + "sort_num" + "       IS '排序|3|0|1|0|1';\n");

        sql.append(" COMMENT ON COLUMN " + tableName + "." + fieldPrefix + "show_flag" + "       IS '是否显示|4|0|1|0|2|1@是@Y,0@否@N';\n");

        sql.append(" COMMENT ON COLUMN " + tableName + "." + fieldPrefix + "del_flag" + "       IS '是否删除|2|0|0|0|2|1@删除@Y,0@正常@N';\n");

        System.out.println(sql.toString());
        return executeSql(sql.toString());


    }


    private String getRemark(FieldDto fieldDto) {

        if (fieldDto == null) {
            return "";
        }

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

        if ("varchar".equalsIgnoreCase(fieldDto.getType()) ||
                "text".equalsIgnoreCase(fieldDto.getType()) ||
                "longtext".equalsIgnoreCase(fieldDto.getType())


        ) {
            sql.append(" CHARACTER SET utf8mb4 COLLATE utf8mb4_bin");
        }

        if (!fieldDto.isNullable()) {

            sql.append(" NOT NULL");
        } else {
            sql.append(" NULL");

        }


        if (!StringUtil.isEmpty(fieldDto.getDefaultValue())) {

            if ("varchar".equalsIgnoreCase(fieldDto.getType()) ||
                    "text".equalsIgnoreCase(fieldDto.getType()) ||
                    "longtext".equalsIgnoreCase(fieldDto.getType())


            ) {

                sql.append(" DEFAULT '" + fieldDto.getDefaultValue() + "'");

            } else {
                sql.append(" DEFAULT " + fieldDto.getDefaultValue());
            }


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
