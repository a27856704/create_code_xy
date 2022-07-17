package ${package};

import lombok.Data;

import ${modelSearchClass};
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Data
@Accessors(chain = true)
@ApiModel("${remark}${entityName}SearchExt")
public class ${modelSearchExt} extends ${modelSearch}<${modelSearchExt}>  {


    public static ${modelSearchExt} getInstance() {
        return new ${modelSearchExt}();
    }

    @Override
    public ${modelSearchExt} getSubThis() {
        return this;
    }

}

