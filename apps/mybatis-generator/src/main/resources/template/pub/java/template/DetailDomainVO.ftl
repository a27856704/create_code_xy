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
@Accessors(chain = true)
@ApiModel("${remark}")
public class ${detailModelVO} extends ${modelVO} {

<#list columnList as item>
    <#if item.showDetailPage>

    /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */

    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name};
    <#if item.valueString??>
        @ApiModelProperty(value = "${item.descName}")
        private String ${item.name}Desc;
    </#if>

    </#if>
</#list>
}