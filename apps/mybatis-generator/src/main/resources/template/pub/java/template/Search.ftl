package ${package};
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${baseSearchClass};
import ${modelClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

@ApiModel("${remark} ${entityName}")
public class ${modelSearch} extends ${baseSearch} {

${searchField}
    public static ${modelSearch} getInstance() {
        return new ${modelSearch}();
    }

    @Override
    public String  setDefaultField() {
        return ${entityName}.CREATE_TIME;
    }

    @Override
    public String  toString() {
        return "${modelSearch}{}"+super.toString();
    }
${searchFieldMethod}
}