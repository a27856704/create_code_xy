package ${package};


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import ${abstractPageDTOClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@ApiModel("${remark}")
@Data
public abstract class ${modelDomainPageDTO} extends ${abstractPageDTO} {

<#list columnList as item>
<#if item.name!='id' && item.name!='delFlag'>
<#if item.searchFlag==1>
    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}Like;
<#elseif item.searchFlag==2>
    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}Eq;
<#elseif item.searchFlag==3>
    @ApiModelProperty(value = "大于${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}Greater;
    @ApiModelProperty(value = "小于${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}Less;
<#elseif item.searchFlag==4>
    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private List ${item.name}In;
<#elseif item.searchFlag==5>
    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private List ${item.name}NotIn;
<#elseif item.searchFlag==6>
    @ApiModelProperty(value = "开始${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}Start;
    @ApiModelProperty(value = "结束${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}End;
<#elseif item.searchFlag==7>
    @ApiModelProperty(value = "${item.descName}<#if item.valueString??> ${item.valueString?replace("@",":")}</#if>")
    private ${item.javaType} ${item.name}Bit;
<#else>
</#if>
</#if>
</#list>


}
