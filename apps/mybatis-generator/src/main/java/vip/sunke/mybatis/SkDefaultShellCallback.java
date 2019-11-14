package vip.sunke.mybatis;

import org.mybatis.generator.internal.DefaultShellCallback;
import vip.sunke.common.FileUtil;
import vip.sunke.common.YXDate;

import java.io.File;
import java.util.*;

/**
 * @author sunke
 * @Date 2019-05-14 09:32
 * @description
 */

public class SkDefaultShellCallback extends DefaultShellCallback {

    public SkDefaultShellCallback(boolean overwrite) {
        super(overwrite);
    }


    @Override
    public void refreshProject(String project) {
        super.refreshProject(project);


        createIncPage();
        createJava();

        createSearchMapper();


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

    public Map setPubMap() {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("pubPackage", BeanName.getPackageProject());
        dataMap.put("appPackage", BeanName.getPackageAppProject());
        dataMap.put("backControllerPackage", BeanName.getBackController());
        dataMap.put("modelClass", BeanName.getModelClass());
        dataMap.put("author", BeanName.getAuthor());
        dataMap.put("createTime", YXDate.getFormatDate2String(new Date()));
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


}
