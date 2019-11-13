package vip.sunke.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunke
 * @Date 2017/6/20 14:51
 * @description
 */

public final class PublishDateUtils {


    public static Map<String, Date> getMinMaxDate(int publishFlag) {

        Map<String, Date> resultMap = new HashMap<String, Date>();


        Date minDate = null;
        Date maxDate = new Date();
        ;


        if (publishFlag == 1) {//近三天
            minDate = YXDate.addDay(-3);

        } else if (publishFlag == 2) {//近一周
            minDate = YXDate.addDay(-7);
        } else if (publishFlag == 3) {//近半月
            minDate = YXDate.addDay(-15);
        } else if (publishFlag == 4) {//近一个月
            minDate = YXDate.addMonths(-1);
        }


        resultMap.put("minDate", YXDate.getTimeDayFirstSecond(minDate));
        resultMap.put("maxDate", YXDate.getTimeDayLastSecond(maxDate));

        return resultMap;


    }


}
