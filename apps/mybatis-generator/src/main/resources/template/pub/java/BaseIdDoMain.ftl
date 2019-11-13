package ${pubPackage}.pubInter;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public class BaseIdDoMain<KeyType> extends AbstractBaseDoMain {


    private KeyType id;

    public KeyType getId() {
        return id;
    }

    public void setId(KeyType id) {
        this.id = id;
    }


}
