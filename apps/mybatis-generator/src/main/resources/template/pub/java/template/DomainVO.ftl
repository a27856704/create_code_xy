package ${package};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import java.math.BigDecimal;

import ${baseVOClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

@Data
@Accessors(chain = true)
@ApiModel("${remark}")
public class ${modelVO} extends ${baseVO}<String> {


<#list columnList as item>
    <#if item.showListPage>

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