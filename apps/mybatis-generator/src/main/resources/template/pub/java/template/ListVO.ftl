package ${package};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import ${baseListVOClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

@Data

@ApiModel("${remark}")
public class ${listVO} extends ${baseListVO}<${detailModelVO}> {

}