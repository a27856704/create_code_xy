&nbsp;&nbsp; <#if ((item.searchFlag)==5)>非</#if>${item.descName}：
<div class="layui-inline">
    <#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>
    <#if item.valueList?? && (item.valueList?size > 0)>
        <#list item.valueList as oneItem>
            <input th:type="checkbox" th:name="${fieldName}" th:value="${oneItem.value}"
                   th:title="${oneItem.desc}"
                   lay-skin="primary" javaType="${item.javaType}"
                    <#if item.searchFlag!=7>
                        th:checked="${r'${ !#lists.isEmpty(search.'+fieldName+')  &&  #lists.contains(search.'+fieldName+',\''+oneItem.value+'\')}'}"
                    <#else>
                        th:checked="${r'${T('+NumberUtil+').bitwise(search.'+fieldName+','+oneItem.value+')}'}"
                    </#if>
                   class="check-box"
                   searchType="${item.searchFlag}"
            />
        </#list>
    </#if>
</div>