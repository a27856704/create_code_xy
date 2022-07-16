<#if (item.searchFlag==3)>
    <#assign fieldNameStart=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
    <#assign fieldNameEnd=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,false)>


    <el-input
            v-model="listQuery.${fieldNameStart}"
            placeholder="请输入${item.descName!''}"
            class="filter-item"
            size="mini"
            style="width: 200px; margin-right: 10px"
            @keyup.enter.native="handleFilter"
    />
    ~
    <el-input
            v-model="listQuery.${fieldNameEnd}"
            placeholder="请输入${item.descName!''}"
            class="filter-item"
            size="mini"
            style="width: 200px; margin-right: 10px"
            @keyup.enter.native="handleFilter"
    />

<#else>
    <#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
    <el-input
            v-model="listQuery.${fieldName}"
            placeholder="请输入${item.descName!''}"
            class="filter-item"
            size="mini"
            style="width: 200px; margin-right: 10px"
            @keyup.enter.native="handleFilter"
    />
</#if>
