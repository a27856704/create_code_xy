package ${pubPackage}.pubInter;

import lombok.Data;
import lombok.experimental.Accessors;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

@Data
@Accessors(chain = true)
public class EnumVO {
    private String id;
    private String name;
}
