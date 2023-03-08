package vip.sunke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import vip.sunke.common.StringUtil;
import vip.sunke.createdb.controller.CreateController;

@SpringBootApplication
@EnableConfigurationProperties
public class CreateDbApplication {


    private static String DB = "";
    private static String TABLE = "";
    private static String FIELD_PREFIX = "";
    private static ApplicationContext applicationContext;

    public static String getFieldPrefix() {
        return FIELD_PREFIX;
    }




    public static void setFieldPrefix(String fieldPrefix) {

        String tab=getTABLE();
        //说明没设置表前缀，根据表名自动设置
        if(StringUtil.isNullOrEmpty(fieldPrefix) && StringUtil.isNotEmpty(tab)) {
            String[] tabArr = tab.split("_");
            fieldPrefix="";
            for(int i=0;i<tabArr.length;i++){
                fieldPrefix=fieldPrefix+tabArr[i].substring(0,1).toLowerCase();

            }
            fieldPrefix=fieldPrefix+"_";

        }


            FIELD_PREFIX = fieldPrefix;





        /*if (StringUtil.isEmpty(fieldPrefix) || fieldPrefix.endsWith("_")) {
            FIELD_PREFIX = fieldPrefix;
        } else {
            FIELD_PREFIX = fieldPrefix + "_";
        }*/


    }

    public static void main(String[] args) {

        applicationContext = SpringApplication.run(CreateDbApplication.class, args);
    }

    public static String getDB() {
        return CreateDbApplication.DB;
    }

    public static void setDB(String db) {

        CreateController.emptyConnection();

        CreateDbApplication.DB = db;
    }

    public static String getTABLE() {
        return TABLE;
    }

    public static void setTABLE(String TABLE) {
        CreateDbApplication.TABLE = TABLE;
    }

    /**
     * 获取spring.profiles.active
     */
    public static String getActiveProfile() {

        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

}
