package vip.sunke.template.controller.back;

import org.springframework.beans.factory.annotation.Value;
import vip.sunke.common.StringUtil;
import vip.sunke.pubInter.BackController;
import vip.sunke.pubInter.BaseIdDoMain;
import vip.sunke.pubInter.BaseSearch;

import java.util.List;

/**
 * @author sunke
 * @Date 2019-11-13 10:38:18
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

    public static String containsValue(String values, String currValues) {
        if (StringUtil.isEmpty(values) || StringUtil.isEmpty(currValues))
            return currValues;

        String[] valueArr = values.split("\\,");

        if (valueArr == null || valueArr.length == 0)
            return currValues;


        String[] oneValueArr = null;
        for (String value : valueArr) {
            if (value == null || "".equalsIgnoreCase(value))
                continue;
            oneValueArr = value.split("@");

            if (currValues.equalsIgnoreCase(oneValueArr[0])) {
                if (oneValueArr.length > 1) {
                    return oneValueArr[1];
                } else {
                    return oneValueArr[0];
                }
            }

        }
        return currValues;
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
