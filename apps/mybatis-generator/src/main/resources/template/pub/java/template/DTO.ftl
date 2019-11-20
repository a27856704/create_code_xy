package ${package};


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import java.math.BigDecimal;

import ${abstractDTOClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@ApiModel("${remark}")
@Data
@Accessors(chain = true)
public class ${modelDTO} extends ${abstractDTO} {

<#list columnList as item>
    <#if item.show>
    /**
    * ${item.descName}
    <#if item.valueString??>
    * ${item.valueString?replace("@",":")}
    </#if>
    */
    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name};
    </#if>
</#list>

}
