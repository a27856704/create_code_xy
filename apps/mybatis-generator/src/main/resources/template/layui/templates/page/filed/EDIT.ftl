<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
                        <textarea placeholder="请输入${item.descName}" style="display: none;"
                                  class="layui-textarea edit" id="${item.name}"
                                  name="${item.name}"
                         <#-- <#if !add>-->
                                th:text="${r'${domain.'+item.name+'}'}"
                       <#--   <#else>
                                <#if (item.defaultValue!''!='')>
                                    th:text="${item.defaultValue}"
                                </#if>
                        </#if>-->

                        >
                        </textarea>

    </div>
</div>




