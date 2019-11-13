package vip.sunke.common.idcard;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author:          sunke
 * Date:            2016/7/21
 * Time:            10:56
 * Description：
 */


public class IdcardInfoExtractor {


    private static IdcardInfoExtractor idcardInfoExtractor = new IdcardInfoExtractor();
    private Map<String, String> cityCodeMap = new HashMap<String, String>() {
        {
            this.put("11", "北京");
            this.put("12", "天津");
            this.put("13", "河北");
            this.put("14", "山西");
            this.put("15", "内蒙古");
            this.put("21", "辽宁");
            this.put("22", "吉林");
            this.put("23", "黑龙江");
            this.put("31", "上海");
            this.put("32", "江苏");
            this.put("33", "浙江");
            this.put("34", "安徽");
            this.put("35", "福建");
            this.put("36", "江西");
            this.put("37", "山东");
            this.put("41", "河南");
            this.put("42", "湖北");
            this.put("43", "湖南");
            this.put("44", "广东");
            this.put("45", "广西");
            this.put("46", "海南");
            this.put("50", "重庆");
            this.put("51", "四川");
            this.put("52", "贵州");
            this.put("53", "云南");
            this.put("54", "西藏");
            this.put("61", "陕西");
            this.put("62", "甘肃");
            this.put("63", "青海");
            this.put("64", "宁夏");
            this.put("65", "新疆");
            this.put("71", "台湾");
            this.put("81", "香港");
            this.put("82", "澳门");
            this.put("91", "国外");
        }
    };
    private IdcardValidator validator = null;

    private JSONArray jsonProvince = null;

    private JSONArray jsonCity = null;
    private JSONArray jsonRegion = null;


    private IdcardInfoExtractor() {


    }


    public static IdcardInfoExtractor getInstance() {

        return idcardInfoExtractor;

    }


    public static void main(String[] args) throws Exception {
        PersonInfo personInfo = IdcardInfoExtractor.getInstance().getPersonInfo("310223197003103217");


        System.out.println(personInfo);

    }


    private String getNameByCode(String code, int type) {


        JSONArray jsons = jsonProvince;

        if (type == 2) {
            jsons = jsonCity;
        } else if (type == 3) {
            jsons = jsonRegion;
        }

        if (jsons == null)
            return "";


        try {
            for (Object id : jsons) {


                if (code.equalsIgnoreCase(((JSONObject) id).getString("code"))) {

                    return ((JSONObject) id).getString("name");

                }


            }
        } catch (Exception e) {

        }

        return "";

    }


    /**
     * 通过构造方法初始化各个成员属性
     */
    public PersonInfo getPersonInfo(String idcard) {

        if (idcard == null || "".equals(idcard))
            return null;


        PersonInfo info = new PersonInfo();


        validator = new IdcardValidator();

        if (!validator.isValidatedAllIdcard(idcard))
            return null;


        if (idcard.length() == 15) {
            idcard = validator.convertIdcarBy15bit(idcard);
        }
        // 获取省份
        //      String provinceId = idcard.substring(0, 2) + "0000";


        String cityId = idcard.substring(0, 4) + "00";
        String regionId = idcard.substring(0, 6);


        info.setProvince(this.cityCodeMap.get(idcard.substring(0, 2)));

        info.setCity(getNameByCode(cityId, 2));

        info.setRegion(getNameByCode(regionId, 3));


        // 获取性别
        String id17 = idcard.substring(16, 17);
        if (Integer.parseInt(id17) % 2 != 0) {
            info.setSex(1);

        } else {
            info.setSex(2);
        }

        // 获取出生日期
        String birthday = idcard.substring(6, 14);
        Date birthdate = null;
        try {
            birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            info.setBirthday(birthdate);

            GregorianCalendar currentDay = new GregorianCalendar();
            currentDay.setTime(birthdate);

            info.setYear(currentDay.get(Calendar.YEAR));
            info.setMonth(currentDay.get(Calendar.MONTH) + 1);
            info.setDay(currentDay.get(Calendar.DAY_OF_MONTH));


            //获取年龄
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            String year = simpleDateFormat.format(new Date());


            info.setAge(Integer.parseInt(year) - info.getYear());

        } catch (ParseException e) {

        }


        return info;
    }


}
