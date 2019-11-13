package vip.sunke.common;


import java.util.ArrayList;


/**
 * @author sunke
 * @Date 2018/12/20 13:57
 * @description
 */

public class SkList<T> extends ArrayList<T> {


    public SkList<T> addObjToList(T o) {

        if (o == null)
            return this;
        add(o);

        return this;

    }
}
