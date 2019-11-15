package vip.sunke.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.sunke.common.NumberUtil;
import vip.sunke.common.StringUtil;
import vip.sunke.mybatis.InputTypeEnum;
import vip.sunke.mybatis.SearchTypeEnum;
import vip.sunke.web.common.enums.WhetherEnum;

/**
 * @author sunke
 * @Date 2019-06-24 14:31
 * @description
 */

@Data
@NoArgsConstructor
public class TableField {

    private String name;
    private String type;
    private String comment;//原始数据
    private String positionDesc = "";//分割描述
    private String info = "";//说明

    private String valueString = "";


    public TableField(String name, String type, String comment) {
        this.name = name;
        this.comment = comment;
    }


    //名称|搜索|添加或修改显示|列表显示|必选|输入框类型|1@是,0@否
    public void setComment(String comment) {
        this.comment = comment;

        if (!StringUtil.isEmpty(comment)) {

            String[] commentArr = comment.split("\\|");
            int len = commentArr.length;

            if (len > 1) {
                positionDesc = positionDesc + "搜索类型：" + SearchTypeEnum.getDescByType(NumberUtil.parseInt(commentArr[1])) + "|";
            }
            if (len > 2) {
                positionDesc = positionDesc + "添加显示：" + WhetherEnum.getDescByValue(NumberUtil.parseInt(commentArr[2])) + "|";
            }

            if (len > 3) {
                positionDesc = positionDesc + "列表显示：" + WhetherEnum.getDescByValue(NumberUtil.parseInt(commentArr[3])) + "|";
            }
            if (len > 4) {
                positionDesc = positionDesc + "添加必选：" + WhetherEnum.getDescByValue(NumberUtil.parseInt(commentArr[4])) + "|";
            }
            if (len > 5) {
                positionDesc = positionDesc + "输入类型：" + InputTypeEnum.getDescByType(NumberUtil.parseInt(commentArr[5])) + "|";
            }

            info = commentArr[0];
            String commentV = commentArr[len - 1];
            commentArr = commentV.split(",");
            len = commentArr.length;

            if (len > 1) {

                for (int i = 0; i < len; i++) {

                    if(commentArr[i].split("@").length>2) {

                        valueString = valueString + commentArr[i].substring(0, commentArr[i].lastIndexOf("@")).replace("@", ":") + " ";
                    }else{
                        valueString = valueString + commentArr[i].replace("@", ":") + " ";

                    }
                }

            }



        }
    }
}
