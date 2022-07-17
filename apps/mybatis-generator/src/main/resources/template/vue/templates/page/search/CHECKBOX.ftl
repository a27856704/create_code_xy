<#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>

<el-checkbox-group v-model="listQuery.${fieldName}">
    <#if item.valueList?? && (item.valueList?size > 0)>
        <#list item.valueList as oneItem>
            <el-checkbox label="${oneItem.value}">${oneItem.desc}</el-checkbox>
        </#list>
    </#if>

</el-checkbox-group>