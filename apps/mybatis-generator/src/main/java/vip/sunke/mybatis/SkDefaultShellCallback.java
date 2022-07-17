package vip.sunke.mybatis;

import org.mybatis.generator.internal.DefaultShellCallback;
import vip.sunke.common.FileUtil;
import vip.sunke.common.StringUtil;
import vip.sunke.common.YXDate;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sunke
 * @Date 2019-05-14 09:32
 * @description
 */

public class SkDefaultShellCallback extends DefaultShellCallback {


    public static Map<String, List<FieldEnum>> enumMap = new HashMap<>();

    //附加数据填充的key
    public static Map<String,FieldEnum> fullKeyMap = new HashMap<>();


    public SkDefaultShellCallback(boolean overwrite) {
        super(overwrite);
    }

    public static void addFiledMap(String fieldName, List<FieldEnum> fieldEnumList) {
        enumMap.put(fieldName, fieldEnumList);
    }

    public static void addFullKeyMap(String fieldName, FieldEnum fieldEnum) {
        fullKeyMap.put(fieldName,fieldEnum);
    }


    @Override
    public void refreshProject(String project) {
        super.refreshProject(project);

        //说明是vue 模板
        if (BeanName.getTemplateDir().indexOf("/vue/") >= 0) {
            createVueIncPage();
        } else {
            createIncPage();
        }


        createJava();

        createSearchMapper();

        createEnumFile();

        createFullConfigKeyFile();

    }

    private void createFullConfigKeyFile(){

        FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();

        String dir = BeanName.getPackageCommonPath();
        FileUtil.mkdirs(dir);




        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("package", BeanName.getPackageCommon());
        dataMap.put("author", BeanName.getAuthor());
        dataMap.put("description", "附加数据填充");

        dataMap.put("createTime", YXDate.getFormatDate2String(new Date()));

        dataMap.put("fullList",fullKeyMap.values());
        String ftlName="FullConfigKeyConst.ftl";
        String filedName="FullConfigKeyConst";
        try {
            freeMarkerUtil.printFile(ftlName, dataMap, filedName + ".java", dir, BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {

        }


    }


    public void createEnumFile() {


        FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();

        String enumDir = BeanName.getEnumPath();
        FileUtil.mkdirs(enumDir);

        String[] keys = null;
        String filedName = null;
        String filedDesc = null;
        String ftlName = "Enum.ftl";
        for (Map.Entry<String, List<FieldEnum>> entry : enumMap.entrySet()) {
            try {

                String key = entry.getKey();
                keys = key.split("@@");
                filedName = "";
                filedDesc = "";
                if (keys.length > 0) {
                    filedName = key.split("@@")[0];
                }
                if (keys.length > 1) {
                    filedDesc = key.split("@@")[1];
                }
                filedName = filedName + "Enum";

                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("package", BeanName.getPackageEnum());
                dataMap.put("author", BeanName.getAuthor());
                dataMap.put("description", filedDesc);
                dataMap.put("enumName", filedName);
                dataMap.put("createTime", YXDate.getFormatDate2String(new Date()));

                dataMap.put("enumList", entry.getValue());
                dataMap.put("pubInterPackage", BeanName.getPubInterPackage());
                if (entry.getValue().stream().findFirst().get().isNumber()) {
                    ftlName = "EnumInt.ftl";
                } else {
                    ftlName = "Enum.ftl";
                }


                freeMarkerUtil.printFile(ftlName, dataMap, filedName + ".java", enumDir, BeanName.getTempInPubJavaDir() + "/template");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 创建页面
     */
    public void createIncPage() {
        String pageDir = BeanName.getWebsitePageDir() + File.separator + "inc";
        FileUtil.mkdirs(pageDir);


        //  pageDir = "/work/vip-sunke/apps/template/template-web/src/main/resources/templates/inc";


        createMenuPage(pageDir);
        createTopPage(pageDir);
        createFooterPage(pageDir);
        createHeadPage(pageDir);
        createLoginPage();
        createPswPage();
        createErrorPage();

    }

    public void createVueIncPage() {
        String pageDir = BeanName.getWebsitePageDir() + File.separator + "inc";
        FileUtil.mkdirs(pageDir);
        createVueMenuPage(pageDir);
    }


    public void createLoginPage() {

        try {
            String pageDir = BeanName.getWebsitePageDir() + File.separator + getFormatRoute() + "admin";

            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("projectName", BeanName.getProjectName());

            freeMarkerUtil.printFile("login.ftl", dataMap, "login.html", pageDir, BeanName.getTemplatePageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void createPswPage() {

        try {
            String pageDir = BeanName.getWebsitePageDir() + File.separator + getFormatRoute() + "admin";

            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("projectName", BeanName.getProjectName());

            freeMarkerUtil.printFile("psw.ftl", dataMap, "psw.html", pageDir, BeanName.getTemplatePageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createErrorPage() {

        try {
            String pageDir = BeanName.getWebsitePageDir();

            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("projectName", BeanName.getProjectName());

            freeMarkerUtil.printFile("error.ftl", dataMap, "error.html", pageDir, BeanName.getTemplatePageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void createTopPage(String pageDir) {

        try {
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("projectName", BeanName.getProjectName());
            freeMarkerUtil.printFile("top.ftl", dataMap, "top.html", pageDir, BeanName.getTempIncPageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void createHeadPage(String pageDir) {

        try {
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("projectName", BeanName.getProjectName());
            dataMap.put("manageController", BeanName.getFullBaseControllerClassName());
            freeMarkerUtil.printFile("header.ftl", dataMap, "header.html", pageDir, BeanName.getTempIncPageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void createFooterPage(String pageDir) {

        try {
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("copyright", BeanName.getCopyright());
            dataMap.put("projectName", BeanName.getProjectName());
            freeMarkerUtil.printFile("footer.ftl", dataMap, "footer.html", pageDir, BeanName.getTempIncPageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void createVueMenuPage(String pageDir) {


        try {
            String menuPage = "index.js";
            String menuTemplate = "menu.ftl";


            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("menuList", Main.getMenuList());
            dataMap.put("projectName", BeanName.getProjectName());

            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();

            freeMarkerUtil.printFile(menuTemplate, dataMap, menuPage, pageDir, BeanName.getTempIncPageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createMenuPage(String pageDir) {


        try {
            String menuPage = "menu.html";
            String menuTemplate = "menu.ftl";


            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("menuList", Main.getMenuList());
            dataMap.put("projectName", BeanName.getProjectName());

            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();

            freeMarkerUtil.printFile(menuTemplate, dataMap, menuPage, pageDir, BeanName.getTempIncPageDir());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 保证前面没/ 后面有/
     *
     * @return
     */
    private String getFormatRoute() {
        String route = BeanName.getRoute();

        if (StringUtil.isNullOrEmpty(route))
            return "";


        if (route.startsWith("/")) {
            route = route.substring(1);
        }
        if (!route.endsWith("/")) {
            route = route + "/";

        }

        return route;

    }

    public Map setPubMap() {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("pubPackage", BeanName.getPackageProject());
        dataMap.put("appPackage", BeanName.getPackageAppProject());
        dataMap.put("backControllerPackage", BeanName.getBackController());
        dataMap.put("modelClass", BeanName.getModelClass());
        dataMap.put("author", BeanName.getAuthor());
        dataMap.put("createTime", YXDate.getFormatDate2String(new Date()));
        dataMap.put("pubInterPackage", BeanName.getPubInterPackage());
        dataMap.put("commonPackage", BeanName.getPackageCommon());
        dataMap.put("webCommonPackage", BeanName.getPackageWebCommon());
        dataMap.put("modelExtPackage", BeanName.getExtPackage());
        dataMap.put("servicePackage", BeanName.getServicePackage());
        dataMap.put("route", getFormatRoute());
        dataMap.put("date", YXDate.getFormatDate2String(new Date()));

        return dataMap;

    }

    public void createJava() {

        FileUtil.mkdirs(BeanName.getPubInterPath());


        if (BeanName.isApiController()) {
            createApiManageController();
        } else {
            createBackManageController();
        }


        createPubInterJava();
        //  createBackController();
        //  createBaseDaoController();

        createBackIndexController();

        createAdminUserExceptionEnum();


    }


    public void createAdminUserExceptionEnum() {
        try {
            Map<String, Object> dataMap = setPubMap();
            dataMap.put("package", BeanName.getPackageWebCommon() + ".exceptionEnums");


            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile("AdminUserExceptionEnum.ftl", dataMap, "AdminUserExceptionEnum.java", BeanName.getExceptionEnumPath(), BeanName.getTempInPubJavaDir() + "/exception");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void createSearchMapper() {

        try {
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            Map<String, Object> dataMap = setPubMap();
            freeMarkerUtil.printFile("SearchMapper.ftl", dataMap, "SearchMapper.xml", BeanName.getMapperPath(), BeanName.getTempInPubMapperDir());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void createPubInterJava() {


        List<String> templateList = new ArrayList<>();

        templateList.add("common/PageBean");


        templateList.add("common/PubConst");
        templateList.add("exception/BusinessException");
        templateList.add("exception/BusinessExceptionEnum");
        templateList.add("exception/DaoException");
        templateList.add("exception/DaoExceptionEnum");
        templateList.add("exception/ExceptionEnum");
        templateList.add("exception/SkException");
        templateList.add("exception/GlobalExceptionHandler");
        templateList.add("AbstractBaseDao");
        templateList.add("AbstractBaseDoMain");
        templateList.add("AbstractBaseFindDao");
        templateList.add("AbstractBaseFindService");
        templateList.add("AbstractBaseService");
        templateList.add("MybatisRedisCache");

        if (BeanName.isApiController()) {
            templateList.add("RestfulController");
            templateList.add("ReadRestfulController");

        } else {
            templateList.add("BackController");
            templateList.add("ReadBackController");
        }


        templateList.add("BaseController");
        templateList.add("BaseIdDoMain");
        templateList.add("BaseSourceDoMain");
        templateList.add("BaseTimeDoMain");
        templateList.add("BaseSearch");
        templateList.add("FrontController");
        templateList.add("IBaseDao");
        templateList.add("IBaseFindDao");
        templateList.add("IBaseFindMapper");
        templateList.add("IBaseFindService");
        templateList.add("IBaseMapper");
        templateList.add("IBaseService");
        templateList.add("IShowMenu");
        templateList.add("SearchMapper");
        templateList.add("AbstractSourceBaseSearch");
        templateList.add("AbstractPageDTO");
        templateList.add("AbstractDTO");
        templateList.add("AbstractDomainVO");
        templateList.add("AbstractDataVO");
        templateList.add("AbstractPageVO");
        templateList.add("DecorateModel");
        templateList.add("DecoratePageList");
        templateList.add("EnumVO");
        templateList.add("FrontUploadFileController");
        templateList.add("UploadFileVO");
        templateList.add("AbstractTask");


        int size = templateList.size();
        FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
        Map<String, Object> dataMap = setPubMap();
        for (int i = 0; i < size; i++) {

            try {
                freeMarkerUtil.printFile(templateList.get(i) + ".ftl", dataMap, templateList.get(i) + ".java", BeanName.getPubInterPath(), BeanName.getTempInPubJavaDir());

            } catch (Exception e) {

            }
        }

    }

/*
    public void createBaseDaoController() {
        try {
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();

            Map<String, Object> dataMap = setPubMap();


            freeMarkerUtil.printFile("AbstractBaseDao.ftl", dataMap, "AbstractBaseDao.java", BeanName.getPubInterPath(), BeanName.getTempInPubJavaDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/


    public void createBackController() {
        try {
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();

            Map<String, Object> dataMap = setPubMap();

            freeMarkerUtil.printFile("BackController.ftl", dataMap, "BackController.java", BeanName.getPubInterPath(), BeanName.getTempInPubJavaDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 创建后台backManagerController
     */
    public void createBackManageController() {

        try {

            Map<String, Object> dataMap = setPubMap();
            dataMap.put("package", BeanName.getApiControllerPackage());
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile("BackManageController.ftl", dataMap, "BackManageController.java", BeanName.getControllerPath(), BeanName.getTempInPubJavaDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 创建后台backManagerController
     */
    public void createApiManageController() {

        try {

            Map<String, Object> dataMap = setPubMap();
            dataMap.put("package", BeanName.getApiControllerPackage());
            FreeMarkerUtil freeMarkerUtil = BeanName.getFreeMarkerUtil();
            freeMarkerUtil.printFile("ApiManageController.ftl", dataMap, "ApiManageController.java", BeanName.getApiControllerPath(), BeanName.getTempInPubJavaDir());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void createBackIndexController() {


        Map<String, Object> dataMap = setPubMap();
        dataMap.put("package", BeanName.getBackController());


        try {
            BeanName.getFreeMarkerUtil().printFile("IndexController.ftl", dataMap, "IndexController.java", BeanName.getControllerPath(), BeanName.getTempInPubJavaDir() + "/template");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
