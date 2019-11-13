<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
        <input type="text" th:name="${item.name}" th:id="${item.name}"
                <#-- <#if add>
                     value="${item.defaultValue!''}"
                 <#else>-->
               th:value="${r'${domain.'+item.name+'}'}"
                <#-- </#if>-->
                <#if item.need>
                    lay-verify='required'
                </#if>
               autocomplete="off" placeholder="请输入${item.descName}"
               class="layui-input"/>
    </div>
</div>



