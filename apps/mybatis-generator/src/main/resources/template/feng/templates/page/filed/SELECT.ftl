<div class="form_control">
    <label for="${item.name}">${item.descName}</label>
    <div class="form_input">
        <select th:name="${item.name}"
                javatype="${item.javaType?lower_case}"
                th:id="${item.name}" <#if item.need> data-verify data-verify-rules="{required:true}"
            data-verify-message="{required:'${item.descName}不能为空'}"
                </#if>>
            <#if item.valueList?? && (item.valueList?size > 0) >
                <#list item.valueList as oneItem>
                    <option value="${ oneItem.value}"
                            th:selected="${r'${((domain.'+item.name+'!=null)?domain.'+item.name+'+\'\':\''+(item.defaultValue!'')+'\')==\''+oneItem.value+'\'}'}">${ oneItem.desc}</option>
                </#list>
            </#if>
        </select>
    </div>
</div>