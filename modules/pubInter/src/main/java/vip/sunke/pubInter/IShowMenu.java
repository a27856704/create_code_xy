package vip.sunke.pubInter;

/**
 * @author sunke
 * @Date 2018/1/14 22:51
 * @description
 */

public interface IShowMenu {
    String getMenuModel();//返回当前模板

    String getCurrentMenu();//返回当前菜单

    String getAddTitle();

    String getModTitle();
}
