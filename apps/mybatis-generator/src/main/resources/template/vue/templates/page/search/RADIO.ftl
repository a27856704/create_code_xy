<#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>


<#if item.valueList?? && (item.valueList?size > 0)>

    <el-radio v-model="listQuery.${fieldName}" label="">全部</el-radio>

    <#list item.valueList as oneItem>
        <el-radio v-model="listQuery.${fieldName}" label="${oneItem.value}">${oneItem.desc}</el-radio>
    </#list>
</#if>

