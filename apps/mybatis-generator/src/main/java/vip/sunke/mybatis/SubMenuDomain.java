package vip.sunke.mybatis;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019-05-14 09:44
 * @description
 */
@Data
public class SubMenuDomain implements Serializable {


    private String url;
    private String title;
    private String subMenu;

    public SubMenuDomain(String url, String title, String subMenu) {
        this.url = url;
        this.title = title;
        this.subMenu = subMenu;
    }
}
