package ${pubPackage}.pubInter;

import lombok.Data;

import java.io.Serializable;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/
@Data
public abstract class AbstractDomainVO<KeyType> implements Serializable {

    private KeyType id;
}
