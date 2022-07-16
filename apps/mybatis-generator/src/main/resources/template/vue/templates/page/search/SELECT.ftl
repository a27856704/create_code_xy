<#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>


<el-select v-model="listQuery.${fieldName}" placeholder="请选择">
    <#if item.valueList?? && (item.valueList?size > 0) >
          <#list item.valueList as oneItem>
            <el-option
                    :key="${oneItem.value}"
                    :label="${oneItem.desc}"
                    :value="${oneItem.value}">
            </el-option>
        </#list>
    </#if>


</el-select>