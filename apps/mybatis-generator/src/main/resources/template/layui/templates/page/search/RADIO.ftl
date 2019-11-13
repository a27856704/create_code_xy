&nbsp;&nbsp; <#if ((item.searchFlag)==5)>非</#if> ${item.descName}：
<div class="layui-inline">
    <#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>
    <#if item.valueList?? && (item.valueList?size > 0)>
        <input th:type="radio" th:name="${fieldName}" value=""
               title="全部"
               lay-skin="primary" javaType="integer"
               checked
        />
        <#list item.valueList as oneItem>
            <input th:type="radio" th:name="${fieldName}" th:value="${oneItem.value}"
                   th:title="${oneItem.desc}"
                   lay-skin="primary" javaType="${item.javaType}"
                   th:checked="${r'${!#lists.isEmpty(search.'+fieldName+')  &&  #lists.contains(search.'+fieldName+',\''+oneItem.value+'\')}'}"
            />
        </#list>
    </#if>
</div>