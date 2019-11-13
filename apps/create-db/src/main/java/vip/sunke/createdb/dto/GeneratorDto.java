package vip.sunke.createdb.dto;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019-06-26 09:16
 * @description
 */

public class GeneratorDto implements Serializable {

    private String driveJar;//驱动jar
    private String projectName;//项目名称
    private String copyright;//版权
    private String packageProject;//项目的根package
    private String packageAppProject;//项目App
    private String targetProject;//生成的目录
    private String dbType;//数据库类型
    private String author;//作者
    private String templateDir;//页面模块路径
    private String templateRoot;
    private String driverClass;//驱动类
    private String connectionUrl;//数据库连接
    private String param;//数据库连接串参数
    private String userId;//数据库用户名
    private String password;//数据库密码
    private String modelTargetPackage;//model包
    private String modelTargetProject;//生成的目录
    private String modelRootClass;//model的父类
    private String mapperTargetProject;//mapper文件生成的目录
    private String mapperInterfaceTargetPackage;//mapper接口包
    private String mapperInterfaceTargetProject;//mapper接口文件生成的目录
    private String db;//数据库名

    private String searchTemplate;//search类模板
    private String controllerTemplate;//controller类模板
    private String route;//route根

    public String getDriveJar() {
        return driveJar;
    }

    public void setDriveJar(String driveJar) {
        this.driveJar = driveJar;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPackageProject() {
        return packageProject;
    }

    public void setPackageProject(String packageProject) {
        this.packageProject = packageProject;
    }

    public String getPackageAppProject() {
        return packageAppProject;
    }

    public void setPackageAppProject(String packageAppProject) {
        this.packageAppProject = packageAppProject;
    }

    public String getTargetProject() {
        //targetProject="/upload/createdb/create";
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getTemplateRoot() {
        templateRoot="/template";
        return templateRoot;
    }

    public void setTemplateRoot(String templateRoot) {
        this.templateRoot = templateRoot;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModelTargetPackage() {
        return modelTargetPackage;
    }

    public void setModelTargetPackage(String modelTargetPackage) {
        this.modelTargetPackage = modelTargetPackage;
    }

    public String getModelTargetProject() {
        modelTargetProject=getTargetProject();
        return modelTargetProject;
    }

    public void setModelTargetProject(String modelTargetProject) {
        this.modelTargetProject = modelTargetProject;
    }

    public String getModelRootClass() {
        return modelRootClass;
    }

    public void setModelRootClass(String modelRootClass) {
        this.modelRootClass = modelRootClass;
    }

    public String getMapperTargetProject() {
        mapperTargetProject=getTargetProject();
        return mapperTargetProject;
    }

    public void setMapperTargetProject(String mapperTargetProject) {
        this.mapperTargetProject = mapperTargetProject;
    }

    public String getMapperInterfaceTargetPackage() {
        return mapperInterfaceTargetPackage;
    }

    public void setMapperInterfaceTargetPackage(String mapperInterfaceTargetPackage) {
        this.mapperInterfaceTargetPackage = mapperInterfaceTargetPackage;
    }

    public String getMapperInterfaceTargetProject() {
        mapperInterfaceTargetProject=getTargetProject();
        return mapperInterfaceTargetProject;
    }

    public void setMapperInterfaceTargetProject(String mapperInterfaceTargetProject) {
        this.mapperInterfaceTargetProject = mapperInterfaceTargetProject;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getSearchTemplate() {
        return searchTemplate;
    }

    public void setSearchTemplate(String searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    public String getControllerTemplate() {
        return controllerTemplate;
    }

    public void setControllerTemplate(String controllerTemplate) {
        this.controllerTemplate = controllerTemplate;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
