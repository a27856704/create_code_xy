package ${pubPackage}.pubInter;

import java.io.Serializable;
import lombok.Data;

/**
* @author ${author}
* @Date ${createTime}
* @description Source search都继承此类
*/

@Data
public class AbstractVO<KeyType> implements Serializable {


    private KeyType id;

}
