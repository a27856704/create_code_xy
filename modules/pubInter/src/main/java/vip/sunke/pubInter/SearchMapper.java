package vip.sunke.pubInter;


/**
 * @author sunke
 * @date 2016-3-28 下午1:54:08
 * @description 在原来的接口上添加一个排序功能
 */


public interface SearchMapper<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> extends IBaseFindMapper<T, TS, KeyType> {


}
