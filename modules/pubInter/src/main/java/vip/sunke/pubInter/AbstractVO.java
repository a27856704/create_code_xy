package vip.sunke.pubInter;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019/11/13 10:58
 * @description
 */

public abstract class AbstractVO<KeyType> implements Serializable {

    private KeyType id;
}
