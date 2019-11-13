&nbsp;&nbsp;${item.descName}:
<div class="layui-inline">
    <#if (item.searchFlag==3)>
        <#assign fieldNameStart=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
        <#assign fieldNameEnd=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,false)>
        <input class="layui-input section" name="${fieldNameStart}"
               th:value="${r'${search.'+(fieldNameStart)+"}"}"
               id="${fieldNameStart}"
               autocomplete="off" style="width: 80px;display:inline">
        &nbsp;&nbsp;至&nbsp;&nbsp;
        <input class="layui-input section" name="${fieldNameEnd}"
               th:value="${r'${search.'+(fieldNameEnd)+"}"}"
               id="${fieldNameEnd}"
               autocomplete="off" style="width: 80px;display:inline">
    <#else>
        <#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
        <input class="layui-input" name="${fieldName}"
               th:value="${r'${search.'+fieldName+"}"}"
               id="${fieldName}"
               autocomplete="off">
    </#if>
</div>