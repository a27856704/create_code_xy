package vip.sunke.template.search;

import vip.sunke.pubInter.BaseSearch;
import vip.sunke.template.model.News;

/**
 * @author sunke
 * @Date 2019-11-13 10:38:18
 * @description NewsSearch      新闻
 */

public class NewsSearch extends BaseSearch {

    private String titleLike;
    private java.util.List<Object> typeIn = null;
    private java.util.List<Object> statusIn = null;
    private java.util.List<Object> authorIn = null;
    private java.util.List<Object> showIn = null;
    private java.util.Date sendTimeStart = null;
    private java.util.Date sendTimeEnd = null;
    private String introLike;
    private java.util.List<Object> sourceIn = null;
    private java.util.Date addTimeStart = null;
    private java.util.Date addTimeEnd = null;

    public static NewsSearch getInstance() {
        return new NewsSearch();
    }

    @Override
    public String setDefaultField() {
        return News.CREATE_TIME;
    }

    @Override
    public String toString() {
        return "NewsSearch{}" + super.toString();
    }

    public String getTitleLike() {
        return this.titleLike;
    }

    public NewsSearch setTitleLike(String titleLike) {
        this.titleLike = titleLike;
        setLikeField(News.TITLE, titleLike);
        return this;
    }

    public java.util.List<Object> getTypeIn() {
        return this.typeIn;
    }

    public NewsSearch setTypeIn(java.util.List<Object> typeIn) {
        this.typeIn = typeIn;
        setInField(News.TYPE, typeIn);
        return this;
    }

    public java.util.List<Object> getStatusIn() {
        return this.statusIn;
    }

    public NewsSearch setStatusIn(java.util.List<Object> statusIn) {
        this.statusIn = statusIn;
        setInField(News.STATUS, statusIn);
        return this;
    }

    public java.util.List<Object> getAuthorIn() {
        return this.authorIn;
    }

    public NewsSearch setAuthorIn(java.util.List<Object> authorIn) {
        this.authorIn = authorIn;
        setInField(News.AUTHOR, authorIn);
        return this;
    }

    public java.util.List<Object> getShowIn() {
        return this.showIn;
    }

    public NewsSearch setShowIn(java.util.List<Object> showIn) {
        this.showIn = showIn;
        setInField(News.SHOW, showIn);
        return this;
    }

    public java.util.Date getSendTimeStart() {
        return this.sendTimeStart;
    }

    public NewsSearch setSendTimeStart(java.util.Date sendTimeStart) {
        this.sendTimeStart = sendTimeStart;
        setDateStartField(News.SEND_TIME, sendTimeStart);
        return this;
    }

    public java.util.Date getSendTimeEnd() {
        return this.sendTimeEnd;
    }

    public NewsSearch setSendTimeEnd(java.util.Date sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd;
        setDateEndField(News.SEND_TIME, sendTimeEnd);
        return this;
    }

    public String getIntroLike() {
        return this.introLike;
    }

    public NewsSearch setIntroLike(String introLike) {
        this.introLike = introLike;
        setLikeField(News.INTRO, introLike);
        return this;
    }

    public java.util.List<Object> getSourceIn() {
        return this.sourceIn;
    }

    public NewsSearch setSourceIn(java.util.List<Object> sourceIn) {
        this.sourceIn = sourceIn;
        setInField(News.SOURCE, sourceIn);
        return this;
    }

    public java.util.Date getAddTimeStart() {
        return this.addTimeStart;
    }

    public NewsSearch setAddTimeStart(java.util.Date addTimeStart) {
        this.addTimeStart = addTimeStart;
        setDateStartField(News.ADD_TIME, addTimeStart);
        return this;
    }

    public java.util.Date getAddTimeEnd() {
        return this.addTimeEnd;
    }

    public NewsSearch setAddTimeEnd(java.util.Date addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
        setDateEndField(News.ADD_TIME, addTimeEnd);
        return this;
    }


}