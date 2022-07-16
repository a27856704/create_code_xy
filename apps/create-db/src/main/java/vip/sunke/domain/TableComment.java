package vip.sunke.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.List;



/**
 * @author sunke
 * @Date 2019-06-24 14:20
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableComment {
    private String name;
    private String comment;
    private Date createTime;
    private String indexNum;

    private List<TableField> fieldList;

/*
    public static void main(String[] args) {


        try {
            ImageRenderer render = new ImageRenderer();
            System.out.println("kaishi");
            String url = "http://127.0.0.1:8089/create/tableList?db=ztjckj_db";*//*网络链接的html*//*
            FileOutputStream out = new FileOutputStream(new File("/Users/sunke/Desktop/psd/"+ File.separator+"html.png"));*//*生成文件的路径*//*
            render.renderURL(url, out, ImageRenderer.Type.PNG);*//*将url网页写入生成文件中*//*
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
}
