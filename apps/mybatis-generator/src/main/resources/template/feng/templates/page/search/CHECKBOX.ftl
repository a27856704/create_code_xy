<#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>
<li class="col_3 row">
    <p><#if ((item.searchFlag)==5)>非</#if>${item.descName}：</p>
    <p>
        <#if item.valueList?? && (item.valueList?size > 0)>
            <#list item.valueList as oneItem>
                <input type="checkbox" javatype="${item.javaType?lower_case}"
                       th:name="${fieldName}" th:value="${oneItem.value}" th:text="${oneItem.desc}"
                        <#if item.searchFlag!=7>
                            th:checked="${r'${ !#lists.isEmpty(search.'+fieldName+')  &&  #lists.contains(search.'+fieldName+',\''+oneItem.value+'\')}'}"
                        <#else>
                            th:checked="${r'${T('+NumberUtil+').bitwise(search.'+fieldName+','+oneItem.value+')}'}"
                        </#if>
                       searchType="${item.searchFlag?lower_case}"/>
            </#list>
        </#if>
    </p>
</li>