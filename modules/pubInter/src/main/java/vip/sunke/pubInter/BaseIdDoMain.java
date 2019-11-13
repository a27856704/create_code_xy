package vip.sunke.pubInter;

import java.util.List;

/**
 * @author sunke
 * @Date 2017/6/9 14:07
 * @description
 */

public class BaseIdDoMain<KeyType> extends AbstractBaseDoMain {


    private KeyType id;
    private List<KeyType> list;

    public KeyType getId() {
        return id;
    }

    public void setId(KeyType id) {
        this.id = id;
    }

    public List<KeyType> getList() {
        return list;
    }

    public void setList(List<KeyType> list) {
        this.list = list;
    }
}
