<#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>
<li class="col_3 row">
    <p>${item.descName}：</p>
    <p>
        <select name="${fieldName}"
                id="${fieldName}">
            <option value="">全部</option>
            <#if item.valueList?? && (item.valueList?size > 0) >
                <#list item.valueList as oneItem>
                    <#if (item.searchFlag==4 || item.searchFlag==5)>
                        <option value="${ oneItem.value}"
                                th:selected="${r'${!#lists.isEmpty(search.'+fieldName+')  &&  #lists.contains(search.'+fieldName+',\''+oneItem.value+'\')}'}">
                            ${oneItem.desc}</option>
                    <#else>
                        <option value="${ oneItem.value}"
                                th:selected="${r'${search.'+fieldName+'==\''+oneItem.value+'\'}'}">
                            ${oneItem.desc}</option>
                    </#if>
                </#list>
            </#if>
        </select>
    </p>
</li>