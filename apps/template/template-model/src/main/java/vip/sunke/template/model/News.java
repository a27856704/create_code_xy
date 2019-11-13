package vip.sunke.template.model;

import java.util.Date;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import vip.sunke.pubInter.BaseTimeDoMain;

public class News extends BaseTimeDoMain {
    /**
     * 标题
     */
    @Length(max=50,message="标题长度最大值50")
    private String title;

    /**
     * 类型
     * 1:行业,2:专业,3:科技,4:社会
     */
    private Integer type;

    /**
     * 状态
     * 1:待审,2:通过,-2:拒绝,3:发布
     */
    private Integer status;

    /**
     * 作者
     * 1:张三,2:李四,3:王五,4:赵六
     */
    private Integer author;

    /**
     * 是否显示
     * 1:显示,0:隐藏
     */
    private Integer show;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 简介
     */
    @Length(max=200,message="简介长度最大值200")
    private String intro;

    /**
     * 来源
     * 1:新浪,2:腾讯,3:知乎
     */
    private Integer source;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 详情
     */
    private String info;

     /**
     *主键
     */
    public static final String ID = "news.n_id";

     /**
     *创建时间
     */
    public static final String CREATE_TIME = "news.n_create_time";

     /**
     *更新时间
     */
    public static final String UPDATE_TIME = "news.n_update_time";

     /**
     *标题
     */
    public static final String TITLE = "news.n_title";

     /**
     *类型
     */
    public static final String TYPE = "news.n_type";

     /**
     *状态
     */
    public static final String STATUS = "news.n_status";

     /**
     *作者
     */
    public static final String AUTHOR = "news.n_author";

     /**
     *是否显示
     */
    public static final String SHOW = "news.n_show";

     /**
     *发送时间
     */
    public static final String SEND_TIME = "news.n_send_time";

     /**
     *简介
     */
    public static final String INTRO = "news.n_intro";

     /**
     *来源
     */
    public static final String SOURCE = "news.n_source";

     /**
     *添加时间
     */
    public static final String ADD_TIME = "news.n_add_time";

     /**
     *详情
     */
    public static final String INFO = "news.n_info";

    /**
     * 获取标题
     *
     * @return n_title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public News setTitle(String title) {
        this.title = title == null ? null : title.trim();
        return this;
    }

    /**
     * 获取类型
     *
     * @return n_type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     * 1:行业,2:专业,3:科技,4:社会
     *
     * @param type 类型
     */
    public News setType(Integer type) {
        this.type = type;
        return this;
    }

    /**
     * 获取状态
     *
     * @return n_status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     * 1:待审,2:通过,-2:拒绝,3:发布
     *
     * @param status 状态
     */
    public News setStatus(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * 获取作者
     *
     * @return n_author - 作者
     */
    public Integer getAuthor() {
        return author;
    }

    /**
     * 设置作者
     * 1:张三,2:李四,3:王五,4:赵六
     *
     * @param author 作者
     */
    public News setAuthor(Integer author) {
        this.author = author;
        return this;
    }

    /**
     * 获取是否显示
     *
     * @return n_show - 是否显示
     */
    public Integer getShow() {
        return show;
    }

    /**
     * 设置是否显示
     * 1:显示,0:隐藏
     *
     * @param show 是否显示
     */
    public News setShow(Integer show) {
        this.show = show;
        return this;
    }

    /**
     * 获取发送时间
     *
     * @return n_send_time - 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public News setSendTime(Date sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    /**
     * 获取简介
     *
     * @return n_intro - 简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置简介
     *
     * @param intro 简介
     */
    public News setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
        return this;
    }

    /**
     * 获取来源
     *
     * @return n_source - 来源
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置来源
     * 1:新浪,2:腾讯,3:知乎
     *
     * @param source 来源
     */
    public News setSource(Integer source) {
        this.source = source;
        return this;
    }

    /**
     * 获取添加时间
     *
     * @return n_add_time - 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置添加时间
     *
     * @param addTime 添加时间
     */
    public News setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    /**
     * 获取详情
     *
     * @return n_info - 详情
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置详情
     *
     * @param info 详情
     */
    public News setInfo(String info) {
        this.info = info == null ? null : info.trim();
        return this;
    }
}