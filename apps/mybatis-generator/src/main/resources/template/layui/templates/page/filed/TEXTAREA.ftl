<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" name="${item.name}" id="${item.name}"
                                  class="layui-textarea" ${(item.need)?string('lay-verify=\"required\"','')}

                                <#-- <#if !add>-->
                                th:text="${r'${domain.'+item.name+'}'}"
                         <#-- <#else>-->
                                <#--   <#if (item.defaultValue!''!='')>
                                       th:text="${item.defaultValue}"
                                   </#if>
                                     </#if>-->
                        ></textarea>
    </div>
</div>

