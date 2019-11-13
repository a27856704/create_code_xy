&nbsp;&nbsp;${item.descName}:
<#assign fieldNameStart=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
<#assign fieldNameEnd=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,false)>
<div class="layui-inline">
    <input class="layui-input date" name="${fieldNameStart}"
           th:value="${r'${#dates.format(search.'+(fieldNameStart)+',\'yyyy-MM-dd\')}'}"
           id="${fieldNameStart}"
           autocomplete="off" style="width: 100px;display:inline"/>&nbsp;&nbsp;至&nbsp;&nbsp;<input
            class="layui-input date" name="${fieldNameEnd}"
            th:value="${r'${#dates.format(search.'+(fieldNameEnd)+',\'yyyy-MM-dd\')}'}"
            id="${fieldNameEnd}"
            autocomplete="off" style="width: 100px;display:inline"/>
</div>