<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
        <select th:name="${item.name}" th:id="${item.name}" ${(item.need)?string('lay-verify=\"required\"','')}>
            <#if item.valueList?? && (item.valueList?size > 0) >
                <#list item.valueList as oneItem>
                <#-- <#if add>
                     <option value="${oneItem.value}"
                             th:selected="${((item.defaultValue!'')== oneItem.value)?string('true','false')}">${ oneItem.desc}</option>
                 <#else>-->
                    <option value="${ oneItem.value}"
                            th:selected="${r'${((domain.'+item.name+'!=null)?domain.'+item.name+'+\'\':\''+(item.defaultValue!'')+'\')==\''+oneItem.value+'\'}'}">
                        ${ oneItem.desc}</option>
                <#--</#if>-->
                </#list>
            </#if>
        </select>
    </div>
</div>




