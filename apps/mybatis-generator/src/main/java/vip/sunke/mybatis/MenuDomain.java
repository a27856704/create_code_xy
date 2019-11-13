package vip.sunke.mybatis;

import lombok.Data;
import vip.sunke.common.SkList;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019-05-14 09:44
 * @description
 */
@Data
public class MenuDomain implements Serializable {

    public MenuDomain(String menuName, String menuTitle) {
        this.menuName = menuName;
        this.menuTitle = menuTitle;
    }

    public MenuDomain() {
    }

    private String menuName;//模块名称
    private String menuTitle;//模块标题

    private SkList<SubMenuDomain> subList;


    public void addSubMenu(SubMenuDomain subMenuDomain) {
        if (subMenuDomain == null)
            return;
        if (subList == null)
            subList = new SkList<SubMenuDomain>();
        subList.addObjToList(subMenuDomain);
    }


}
