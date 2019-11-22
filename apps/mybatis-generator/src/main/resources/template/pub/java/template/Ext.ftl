package ${package};

import lombok.Data;
import ${modelClass};

<#list columnList as item>
import ${enumsPackage}.${item.entityName?cap_first}${item.name?cap_first}Enum;
</#list>

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Data
public class ${modelExt} extends ${model} {


<#list columnList as item>
    private String ${item.name}Desc;
</#list>

<#list columnList as item>
    public String get${item.name?cap_first}Desc() {
        ${item.name}Desc=${item.entityName?cap_first}${item.name?cap_first}Enum.getDescByType(get${item.name?cap_first}()+"");
        return ${item.name}Desc;
    }
</#list>
}

