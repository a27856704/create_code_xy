package ${pubPackage}.pubInter;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IShowMenu {
    String getMenuModel();//返回当前模板

    String getCurrentMenu();//返回当前菜单

    String getAddTitle();

    String getModTitle();
}
