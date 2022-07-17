package ${package};
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${baseSearchClass};
import ${modelClass};
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import lombok.Data;
/**
*   @author ${author}
*   @Date ${date}
*   @description ${description}
*/

@Data
@ApiModel("${remark}${entityName}Search")
public abstract class ${modelSearch}<Sub extends ${modelSearch}<Sub>> extends ${baseSearch} {
<#list columnList as item>
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
</#list>

    /**
    * 返回子类Sub
    * @return
    */
    public abstract Sub getSubThis();

    @Override
    public String createTimeFiled() {
        return null;
    }
    @Override
    public String updateTimeFiled() {
        return null;
    }
    @Override
    public String  setDefaultField() {
        return ${entityName}.ID;
    }
    @Override
    public String  toString() {
        return "${modelSearch}{}"+super.toString();
    }
<#list columnList as item>
<#if item.searchFlag==1>
    public Sub set${item.name?cap_first}Like(${item.javaType} ${item.name}Like){
        this.${item.name}Like = ${item.name}Like;
        setLikeField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}Like);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}Like(){
        return this.${item.name}Like;
    }-->
<#elseif item.searchFlag==2>
    public Sub set${item.name?cap_first}Eq(${item.javaType} ${item.name}Eq){
        this.${item.name}Eq = ${item.name}Eq;
        setEqualField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}Eq+"");
        return getSubThis();
    }
    <#--/**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}Like(){
        return this.${item.name}Eq;
    }-->
<#elseif item.searchFlag==3>
    public Sub set${item.name?cap_first}Greater(${item.javaType} ${item.name}Greater){
        this.${item.name}Greater = ${item.name}Greater;
        setGreaterField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}Greater);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}Greater(){
        return this.${item.name}Greater;
    }-->
    public Sub set${item.name?cap_first}Less(${item.javaType} ${item.name}Less){
        this.${item.name}Less = ${item.name}Less;
        setLessField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}Less);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}Less(){
        return this.${item.name}Less;
    }-->
<#elseif item.searchFlag==4>
    public Sub set${item.name?cap_first}In(List ${item.name}In){
        this.${item.name}In = ${item.name}In;
        setInField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}In);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public List get${item.name?cap_first}In(){
        return this.${item.name}In;
    }-->
<#elseif item.searchFlag==5>
    public Sub set${item.name?cap_first}NotIn(List ${item.name}NotIn){
        this.${item.name}NotIn = ${item.name}NotIn;
        setNotInField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}NotIn);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public List get${item.name?cap_first}NotIn(){
        return this.${item.name}NotIn;
    }-->
<#elseif item.searchFlag==6>
    public Sub set${item.name?cap_first}Start(${item.javaType} ${item.name}Start){
        this.${item.name}Start = ${item.name}Start;
        setDateStartField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}Start);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}Start(){
        return this.${item.name}Start;
    }-->
    public Sub set${item.name?cap_first}End(${item.javaType} ${item.name}End){
        this.${item.name}End = ${item.name}End;
        setDateEndField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}End);
        return getSubThis();
    }
   <#-- /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}End(){
        return this.${item.name}End;
    }-->
<#elseif item.searchFlag==7>
    public Sub set${item.name?cap_first}Bit(${item.javaType} ${item.name}Bit){
        this.${item.name}Bit = ${item.name}Bit;
        setBitField(${entityName}.${item.name?replace("([a-z])([A-Z]+)","$1_$2","r")?upper_case}, ${item.name}Bit);
        return getSubThis();
    }
  <#--  /**
    * ${item.descName}
    <#if item.valueString??>
        * ${item.valueString?replace("@",":")}
    </#if>
    */
    public ${item.javaType} get${item.name?cap_first}Bit(){
        return this.${item.name}Bit;
    }-->
<#else>
</#if>
</#list>
<#--
${searchFieldMethod}
-->
}