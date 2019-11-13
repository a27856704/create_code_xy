package vip.sunke.pubInter;

import org.springframework.beans.factory.annotation.Value;
import vip.sunke.common.StringUtil;

import java.util.List;

/**
 * @author sunke
 * @Date 2019-06-27 15:10
 * @description
 */

public abstract class BackManageController<T extends BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> extends BackController<T, TS, KeyType> {


    private static boolean debug;


    public static String getSplitMenuUrl(String menuUrl) {

        if (StringUtil.isEmpty(menuUrl))
            return "";

        return menuUrl.split(";")[0];


    }


    public static String getMenu(String menuUrl) {

        try {
            String url = getSplitMenuUrl(menuUrl);

            String[] urlArr = url.split("/");

            return urlArr[1];
        } catch (Exception e) {
            return "";
        }


    }

    public static String getMenuUrl(String menuUrl) {
        try {
            String url = getSplitMenuUrl(menuUrl);

            String[] urlArr = url.split("/");

            return urlArr[2] + "/" + urlArr[3];
        } catch (Exception e) {
            return "";
        }


    }


    public static boolean contains(List<String> values, String rightsUrl) {

        if (debug)
            return true;

        if (values == null || values.size() == 0 || StringUtil.isEmpty(rightsUrl))
            return false;

        for (String value : values) {
            if (rightsUrl.toLowerCase().contains(value.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    @Value("${debug}")
    public void setDebug(boolean debug) {
        BackManageController.debug = debug;
    }

    /*public Admin getAdmin(HttpSession session) throws BusinessException {

        Admin admin = (Admin) session.getAttribute(Const.ADMIN_SESSION);

        if (admin == null)
            throw new BusinessException(BusinessExceptionEnum.OBJ_NULL_ERROR);

        return admin;


    }*/


}
