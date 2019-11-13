package vip.sunke.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author sunke
 * @Date 2017/6/13 13:01
 * @description 全局变量
 */

@Component
public  class Const {


    public final static String ENCODE = "utf-8";


    public static final String[] IMG_TYPE = new String[]{"jpg", "png", "gif", "jpeg"};


    public final static String REDIRECT = "redirect:";

    public final static String PAGINATE_DATA_KEY = "list";//分页时返回数据的Key
    public final static String PAGINATE_PAGE_KEY = "page";//分页时返回分页的Key

    public final static String DETAIL_KEY = "domain";
    public final static String IDCARD_DIR = "idcard";
    public final static String IDCARD_WATEMARK = "watemark_";
    public final static String IDCARD_FRONT = "front_";
    public final static String IDCARD_BACK = "back_";
    public final static String IDCARD_BODY = "body_";
    public final static String CONTRACT_DIR = "contract";//合同目录
    public final static int TRY_MAX_COUNT = 10;//网络请求重试次数
    public static String UPLOAD_PATH = "/upload";

    @Value("${file.upload.root}")
    public void setUpload(String upload) {
        Const.UPLOAD_PATH = upload;
    }
}
