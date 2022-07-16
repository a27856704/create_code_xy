package ${package};

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${modelSearchClass};
import lombok.experimental.Accessors;


/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Data
@Accessors(chain = true)
@ApiModel("${remark}${entityName}SearchExt")
public class ${modelSearchExt} extends ${modelSearch}<${modelSearchExt}> {



    public static ${modelSearchExt} getInstance() {
        return new ${modelSearchExt}();
    }


    @Override
    public ${modelSearchExt} getSubThis() {
        return this;
    }

}

