<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
        <input type="text" class="layui-input date" th:name="${item.name}" th:id="${item.name}"
               <#if item.need>lay-verify="datetime"</#if>

                <#--  <#if add>
              <#if (item.defaultValue!'')!= ''> value="${item.defaultValue}"</#if>
          <#else> -->

               th:value="${r'${#dates.format(domain.'+item.name+',\'yyyy-MM-dd HH:mm:ss\')}'}"
                <#-- </#if>-->
        />
    </div>
</div>

