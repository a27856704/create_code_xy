package vip.sunke.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import vip.sunke.common.SkList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {


    private static SkList<MenuDomain> menuList = new SkList<MenuDomain>();

    private static int TABLE_INDEX=0;


    public static void clearMenuList() {
        Main.menuList.clear();
    }

    public static void clearTableIndex() {
        Main.TABLE_INDEX=0;
    }

    public static String getTableIndex() {

        int tableIndex= Main.TABLE_INDEX++;

        if(tableIndex<10)
            return tableIndex+"00";

        if(tableIndex<100)
            return tableIndex+"0";

        return tableIndex+"";

    }


    public static void addMenu(MenuDomain menuDomain) {
        menuList.addObjToList(menuDomain);

    }

    public static SkList<MenuDomain> getMenuList() {
        return menuList;
    }


    public static void create(File configFile) {

        List<String> warnings = new ArrayList<String>();
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
        }

    }

    public static void main(String[] args) {
        //读取文件
        ClassLoader classLoader = Main.class.getClassLoader();
        String configPath = classLoader.getResource("").getFile();
        File configFile = new File(configPath + ("/generatorConfig.xml"));
        SkPlugin.dbType=SkPlugin.DB_TYPE_MSSERVER;
        create(configFile);


    }
}
