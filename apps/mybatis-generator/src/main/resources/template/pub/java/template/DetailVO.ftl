package ${package};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import ${baseDataVOClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

@Data

@ApiModel("${remark}")
public class ${detailVO} extends ${baseDataVO}<${detailModelVO}> {

}