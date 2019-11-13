<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
        <#if item.valueList?? && (item.valueList?size > 0) >
            <#list item.valueList as oneItem>


                <input th:type="radio" th:name="${item.name}" th:value="${oneItem.value}"
                        <#--  <#if add>
                              <#if ((item.defaultValue!'')== oneItem.value)> checked</#if>
                          <#else>-->
                       th:checked="${r'${((domain.'+item.name +'!=null)?domain.'+item.name+'+\'\''+':\''+(item.defaultValue!'')+'\')==\''+ oneItem.value+'\'}'}"
                        <#-- </#if>-->
                       th:title="${oneItem.desc}"/>
            </#list>
        </#if>
    </div>
</div>

