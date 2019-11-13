<div class="form_control">
    <label>${item.descName}</label>
    <div class="form_input">
        <input type="text"
               th:value="${r'${domain.'+item.name+'}'}"
               javatype="${item.javaType?lower_case}"
               th:name="${item.name}" th:id="${item.name}" autocomplete="off" placeholder="请输入${item.descName}"
               <#if item.need>data-verify data-verify-rules="{required:true}"
               data-verify-message="{required:'${item.descName}不能为空'}"</#if>/>
    </div>
</div>