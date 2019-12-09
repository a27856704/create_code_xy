package vip.sunke.mybatis;

import vip.sunke.common.FileUtil;
import vip.sunke.common.StringUtil;

import java.io.File;
import java.util.Properties;

/**
 * @author sunke
 * @Date 2017/11/30 11:12
 * @description
 */

public class BeanName {

    public static String BACK_CONTROLLER_TEMPLATE = "BackController.ftl";
    public static String API_CONTROLLER_TEMPLATE = "RestfulController.ftl";
    private static String projectName;//项目名称
    private static String copyright;//版权
    private static String packageProject;//项目根
    private static String packageAppProject;//项目
    private static String packagePubInterProject;//
    private static String targetProject;//生成路径
    private static String dbType = "mysql";
    private static String author;//作者
    private static String templateRoot;//模板根目录
    private static String templateDir;//页面模块目录
    private static String searchTemplate;//搜索类模板
    private static String controllerTemplate;//controller类模板
    private static String modelClass;
    private static String route;
    private static String mapperTargetProject;

    public static String getActiveProfile() {
        return activeProfile;
    }

    private static String activeProfile;




    public static String getMapperTargetProject() {
        return mapperTargetProject;
    }

    public static void setMapperTargetProject(String mapperTargetProject) {
        BeanName.mapperTargetProject = mapperTargetProject;
    }

    public static String getRoute() {
        return route;
    }

    public static void setRoute(String route) {
        BeanName.route = route;
    }

    public static String getModelClass() {
        return modelClass;
    }

    public static void setModelClass(String modelClass) {
        BeanName.modelClass = modelClass;
    }

    public static String getControllerTemplate() {
        return controllerTemplate;
    }

    public static void setControllerTemplate(String controllerTemplate) {
        BeanName.controllerTemplate = controllerTemplate;
    }

    public static boolean isApiController() {
        return BeanName.API_CONTROLLER_TEMPLATE.equalsIgnoreCase(BeanName.getControllerTemplate());
    }

    public static String getSearchTemplate() {
        return searchTemplate;
    }


    public static void setSearchTemplate(String searchTemplate) {
        BeanName.searchTemplate = searchTemplate;
    }


    public static String getPackageAppProject() {
        return packageAppProject;
    }

    public static void setPackageAppProject(String packageAppProject) {
        BeanName.packageAppProject = packageAppProject;
    }

    /**
     * 枚举存在的包目录
     *
     * @return
     */
    public static String getPackageEnum() {

        return packageAppProject + ".enums";
    }

    public static void initPro(Properties properties) {

        if (properties == null)
            return;

        BeanName.projectName = properties.getProperty("projectName");
        BeanName.copyright = properties.getProperty("copyright");
        BeanName.packageProject = properties.getProperty("packageProject");
        BeanName.packageAppProject = properties.getProperty("packageAppProject");
        BeanName.targetProject = properties.getProperty("targetProject");
        BeanName.dbType = properties.getProperty("dbType");
        BeanName.author = properties.getProperty("author");
        BeanName.templateRoot = properties.getProperty("templateRoot");
        BeanName.templateDir = properties.getProperty("templateDir");
        BeanName.searchTemplate = properties.getProperty("searchTemplate");
        BeanName.controllerTemplate = properties.getProperty("controllerTemplate");
        BeanName.modelClass = properties.getProperty("modelClass");
        BeanName.mapperTargetProject = properties.getProperty("mapperTargetProject");
        BeanName.activeProfile=properties.getProperty("activeProfile");

        BeanName.route = properties.getProperty("route");
        if (StringUtil.isNullOrEmpty(BeanName.route)) {
            if (isApiController()) {
                BeanName.route = "api";
            } else {
                BeanName.route = "back";
            }
        } else {
            if ("/".equalsIgnoreCase(BeanName.route.substring(0, 1))) {
                BeanName.route = BeanName.route.substring(1);
            }
            if (BeanName.route.endsWith("/")) {
                BeanName.route = BeanName.route.substring(0, BeanName.route.length() - 1);
            }
        }


    }


    public static String getTempIncPageDir() {
        return getTemplateDir() + "/inc";
    }

    public static String getTempEntityJsDir() {
        return getTemplateDir() + "/js";
    }

    public static boolean existEntityJs() {
        return FileUtil.exists(getTempEntityJsDir() + "/entityJs.ftl");
    }

    public static String getTempInPubJavaDir() {
        return getTemplateRoot() + "/pub/java";
    }

    public static String getTempInPubMapperDir() {
        return getTemplateRoot() + "/pub/mapper";
    }

    public static String getDbType() {
        return dbType;
    }

    public static void setDbType(String dbType) {
        BeanName.dbType = dbType;
    }

    public static String getTemplateRoot() {
        templateRoot = "/template";
        return templateRoot;
    }

    public static void setTemplateRoot(String templateRoot) {
        BeanName.templateRoot = templateRoot;
    }





  /*  public BeanName() {

    }

    public BeanName(String packageProject, String targetProject, String packagePubInter, String templateDir) {

        this.packageProject = packageProject;
        this.targetProject = targetProject;
        this.packagePubInter = packagePubInter;
        this.templateDir = templateDir;

    }*/

    public static String getAuthor() {
        return author;
    }

    public static void setAuthor(String author) {
        BeanName.author = author;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static void setProjectName(String projectName) {
        BeanName.projectName = projectName;
    }

    public static String getCopyright() {
        return copyright;
    }

    public static void setCopyright(String copyright) {
        BeanName.copyright = copyright;
    }

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /* 转换为驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    public static String getTemplateDir() {
        return templateDir;
    }

    public static String getTemplatePageDir() {
        return templateDir + "/page";
    }


    public static FreeMarkerUtil getFreeMarkerUtil() {

        return FreeMarkerUtil.getInstance("2.3.28", getTemplateDir());
    }

    public static FreeMarkerUtil getFreeMarkerUtil(String templateDir) {

        return FreeMarkerUtil.getInstance("2.3.28", templateDir);
    }

    public static String getPackageProject() {
        return packageProject;
    }

    public static String getPackageCommonProject() {
        return packageProject + ".common";
    }


    public static String getTargetProject() {
        return targetProject;
    }

    public static void setTargetProject(String targetProject) {
        targetProject = targetProject;
    }

    public static String getBackPageDir() {
        return getWebsitePageDir() + File.separator + getRoute();
    }

    public static String getBackJsDir() {
        return getWebsitePageDir() + File.separator + "js";
    }


    public static String getWebsitePageDir() {
        return getTargetProject() + File.separator + "website";
    }

    /**
     * 首字母大寫
     *
     * @param name
     * @return
     */
    public static String getFirstUpperCase(String name) {

        if (name == null || "".equals(name))
            return "";
        if (name.length() == 1)
            return name.toUpperCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);

    }

    public static String getFirstLowerCase(String name) {

        if (name == null || "".equals(name))
            return "";
        if (name.length() == 1)
            return name.toLowerCase();
        return name.substring(0, 1).toLowerCase() + name.substring(1);

    }


    public static String setMethodName(String name) {

        if (name == null || "".equalsIgnoreCase(name))
            return null;

        return "set" + getFirstUpperCase(name);


    }

    public static String getMethodName(String name) {

        if (name == null || "".equalsIgnoreCase(name))
            return null;

        return "get" + getFirstUpperCase(name);


    }


    public static String getTargetDir(String str) {
        if (str == null || "".equals(str))
            return "";
        return str.replace(".", "/");
    }

    public static String getControllerPackage() {
        return getPackageAppProject() + ".controller." + StringUtil.replaceAll(BeanName.getRoute(), "/", ".");
    }

    public static String getApiControllerPackage() {
        return getPackageAppProject() + ".controller." + StringUtil.replaceAll(BeanName.getRoute(), "/", ".");
    }

    public static String getShortApiController() {
        return "RestfulController";
    }

    public static String getFullApiController() {

        return getPubInterPackage() + "." + getShortApiController();
    }


    public static String getControllerPath() {
        return getTargetProject() + "/" + getTargetDir(getControllerPackage());
    }

    public static String getApiControllerPath() {
        return getTargetProject() + "/" + getTargetDir(getApiControllerPackage());
    }

    public static String getPubInterPath() {

        return getTargetProject() + "/" + getTargetDir(getPackageProject() + ".pubInter");
    }

    public static String getEnumPath(){

        return getTargetProject() + "/" + getTargetDir(getPackageEnum());

    }


    public static String getMapperPath() {

        return getTargetProject() + "/" + getTargetDir("resources.mapper");
    }


    public static String getAddPageTemplateName() {

        return "add.ftl";
    }

    public static String getModPageTemplateName() {

        return "mod.ftl";
    }

    public static String getListPageTemplateName() {

        return "list.ftl";
    }

    public static String getPackageCommon() {
        return getPackageProject() + ".common";
    }

    public static String getPackageWebCommon() {
        return getPackageProject() + ".web.common";
    }

    public static String getPubInterPackage() {
        return getPackageProject() + ".pubInter";
    }

    public static String getFullAbstractDaoClassName() {
        return getPubInterPackage() + "." + getShortAbstractDaoClassName();
    }

    public static String getShortAbstractDaoClassName() {
        return "AbstractBaseDao";
    }

    public static String getFullAbstractServiceClassName() {
        return getPubInterPackage() + "." + getShortAbstractServiceClassName();
    }

    public static String getShortAbstractServiceClassName() {
        return "AbstractBaseService";
    }

    public static String getShortBaseSearchClassName() {
        return "BaseSearch";
    }

    public static String getShortSourceBaseSearchClassName() {
        return "AbstractSourceBaseSearch";
    }


    public static String getShortBaseControllerClassName() {

        return "BackManageController";

    }


    public static String getFullBaseControllerClassName() {

        return getBackController() + "." + getShortBaseControllerClassName();
    }

    public static String getFullBaseSearchClassName() {
        return getPubInterPackage() + "." + getShortBaseSearchClassName();
    }

    public static String getFullBaseSourceSearchClassName() {
        return getPubInterPackage() + "." + getShortSourceBaseSearchClassName();
    }


    public static String getShortBaseMapperClassName() {

        return "IBaseMapper";
    }

    public static String getShortMapperExtClassName(String modelName) {
        return getFirstUpperCase(modelName) + "MapperExt";
    }

    public static String getFullMapperExtClassName(String modelName) {
        return getDaoMapperExtPackage() + "." + getShortMapperExtClassName(modelName);
    }


    public static String getDaoMapperExtPackage() {
        return getDaoPackage() + ".mapperExt";
    }

    public static String getFullBaseMapperClassName() {
        return getPubInterPackage() + "." + getShortBaseMapperClassName();
    }

    public static String getShortBaseDaoClassName() {
        return "IBaseDao";
    }

    public static String getFullBaseDaoClassName() {
        return getPubInterPackage() + "." + getShortBaseDaoClassName();
    }

    public static String getShortBaseServiceClassName() {
        return "IBaseService";
    }

    public static String getFullBaseServiceClassName() {
        return getPubInterPackage() + "." + getShortBaseServiceClassName();
    }

    public static String getSearchMapperOrderBy() {
        return getPubInterPackage() + ".SearchMapper.searchGroupByAndOrderBy";

    }

    public static String getSearchMapperWhereSql() {

        return getPubInterPackage() + ".SearchMapper.searchWhere";
    }

    public static String getDaoPackage() {
        return getPackageAppProject() + ".dao";
    }

    public static String getDaoImplPackage() {
        return getDaoPackage() + ".impl";
    }

    public static String getMapperPackage() {
        return getDaoPackage() + ".mapper";
    }

    public static String getServicePackage() {
        return getPackageAppProject() + ".service";
    }

    public static String getServiceImplPackage() {
        return getServicePackage() + ".impl";
    }

    public static String getSearchPackage() {
        return getPackageAppProject() + ".search";
    }

    public static String getDOPackage() {
        return getPackageAppProject() + ".domain";
    }

    public static String getExtPackage() {
        return getPackageAppProject() + ".modelExt";
    }

    public static String getVOPackage() {
        return getPackageAppProject() + ".vo";
    }


    public static String getDTOPackage() {
        return getPackageAppProject() + ".dto";
    }


    public static String getFullBaseVOClass() {
        return getPubInterPackage() + "." + getShortBaseVOClass();
    }

    public static String getFullBaseDetailVOClass() {
        return getPubInterPackage() + ("test".equalsIgnoreCase(activeProfile)?".baseVO.":".")+getShortBaseDetailVOClass();
    }

    public static String getFullBaseListVOClass() {
        return getPubInterPackage() + ("test".equalsIgnoreCase(activeProfile)?".baseVO.":".") + getShortBaseListVOClass();
    }

    public static String getShortBaseListVOClass() {
        return "AbstractPageVO";
    }

    public static String getShortBaseDetailVOClass() {
        return "AbstractDataVO";
    }

    public static String getShortBaseVOClass() {
        return "AbstractDomainVO";
    }

    public static String getFullBaseDTOClass() {
        return getPubInterPackage() + "." + getShortBaseDTOClass();
    }

    public static String getShortBaseDTOClass() {
        return "AbstractDTO";
    }


    public static String getModelPackage() {
        return getPackageAppProject() + ".model";
    }

    public static String getBackController() {
        return getPackageAppProject() + ".controller." + StringUtil.replaceAll(BeanName.getRoute(), "/", ".");
    }

    public static String getFrontController() {
        return getPackageAppProject() + ".controller.front";
    }

    public static String getShortMapperClassName(String modelName) {
        return getFirstUpperCase(modelName) + "Mapper";
    }

    public static String getFullMapperClassName(String modelName) {
        return getMapperPackage() + "." + getShortMapperClassName(modelName);
    }

    public static String getMapperObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "Mapper";
    }

    public static String getMapperExtObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "MapperExt";
    }

    public static String getShortModelClassName(String modelName) {
        return getFirstUpperCase(modelName);
    }


    public static String getFullModelClassName(String modelName) {
        return getModelPackage() + "." + getShortModelClassName(modelName);
    }

    public static String getFullModelDOClassName(String modelName) {
        return getDOPackage() + "." + getShortDOClassName(modelName);
    }

    public static String getFullModelDTOClassName(String modelName) {
        return getDTOPackage() + "." + getShortDTOClassName(modelName);
    }

    public static String getFullModelVOClassName(String modelName) {
        return getVOPackage() + "." + getShortVOClassName(modelName);
    }

    public static String getFullDetailModelVOClassName(String modelName) {
        return getVOPackage() + "." + getShortDetailDomainVOClassName(modelName);
    }


    public static String getFullListVOClassName(String modelName) {
        return getVOPackage() + "." + getShortListVOClassName(modelName);
    }

    public static String getFullDetailVOClassName(String modelName) {
        return getVOPackage() + "." + getShortDetailVOClassName(modelName);
    }

    public static String getFullModelExtClassName(String modelName) {
        return getExtPackage() + "." + getShortModelExtClassName(modelName);
    }

    public static String getModelObjectName(String modelName) {
        return getFirstLowerCase(modelName);
    }

    public static String getShortSearchClassName(String modelName) {

        return getFirstUpperCase(modelName) + "Search";
    }

    public static String getShortDOClassName(String modelName) {

        return getFirstUpperCase(modelName) + "DO";
    }


    public static String getShortModelExtClassName(String modelName) {
        return getFirstUpperCase(modelName) + "Ext";
    }


    public static String getShortVOClassName(String modelName) {

        return getFirstUpperCase(modelName) + "DomainVO";
    }


    public static String getShortDetailDomainVOClassName(String modelName) {

        return getFirstUpperCase(modelName) + "DetailDomainVO";
    }


    public static String getShortListVOClassName(String modelName) {

        return getFirstUpperCase(modelName) + "ListVO";
    }

    public static String getShortDetailVOClassName(String modelName) {

        return getFirstUpperCase(modelName) + "DetailVO";
    }


    public static String getShortDTOClassName(String modelName) {

        return getFirstUpperCase(modelName) + "DTO";
    }

    public static String getShortControllerClassName(String modelName) {

        return getFirstUpperCase(modelName) + "Controller";
    }

    public static String getFullSearchClassName(String modelName) {

        return getSearchPackage() + "." + getShortSearchClassName(modelName);
    }

    public static String getSearchObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "Search";
    }

    public static String getShortDaoClassName(String modelName) {
        return "I" + getFirstUpperCase(modelName) + "Dao";
    }

    public static String getFullDaoClassName(String modelName) {

        return getDaoPackage() + "." + getShortDaoClassName(modelName);
    }

    public static String getDaoObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "Dao";
    }

    public static String getShortDaoImplClassName(String modelName) {
        return getFirstUpperCase(modelName) + "DaoImpl";
    }

    public static String getDaoImplObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "DaoImpl";
    }

    public static String getShortServiceClassName(String modelName) {

        return "I" + getFirstUpperCase(modelName) + "Service";

    }

    public static String getFullServiceClassName(String modelName) {
        return getServicePackage() + "." + getShortServiceClassName(modelName);
    }

    public static String getServiceObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "Service";
    }

    public static String getShortServiceImplClassName(String modelName) {
        return getFirstUpperCase(modelName) + "ServiceImpl";
    }

    public static String getServiceImplObjectName(String modelName) {
        return getFirstLowerCase(modelName) + "ServiceImpl";
    }

    public static String getDaoPath() {
        return getTargetProject() + "/" + getTargetDir(getDaoPackage());
    }

    public static String getDaoImplPath() {
        return getDaoPath() + "/impl";
    }

    public static String getServicePath() {
        return getTargetProject() + "/" + getTargetDir(getServicePackage());
    }

    public static String getServicImplPath() {
        return getServicePath() + "/impl";
    }

    public static String getSearchPath() {
        return getTargetProject() + "/" + getTargetDir(getSearchPackage());
    }

    public static String getDOPath() {
        return getTargetProject() + "/" + getTargetDir(getDOPackage());
    }

    public static String getExtPath() {
        return getTargetProject() + "/" + getTargetDir(getExtPackage());
    }

    public static String getMapperExtPath() {
        return getTargetProject() + "/" + getTargetDir(getDaoMapperExtPackage());
    }

    public static String getMapperExtXmlPath() {
        return mapperTargetProject + "/resources/mapper";
    }

    public static String getVOPath() {
        return getTargetProject() + "/" + getTargetDir(getVOPackage());
    }

    public static String getDTOPath() {
        return getTargetProject() + "/" + getTargetDir(getDTOPackage());
    }

    public static String getFullDaoImplClassName(String modelName) {
        return getDaoImplPackage() + "." + getShortDaoImplClassName(modelName);
    }

    public static String getFullServiceImplClassName(String modelName) {
        return getServiceImplPackage() + "." + getShortServiceImplClassName(modelName);
    }


}
