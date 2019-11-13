&nbsp;&nbsp;${item.descName}:
<div class="layui-inline">
    <#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
    <input class="layui-input" name="${fieldName}"
           th:value="${r'${search.'+fieldName+"}"}"
           id="${fieldName}"
           autocomplete="off">
</div>