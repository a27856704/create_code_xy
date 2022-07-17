<#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
            <el-input
                    v-model="listQuery.${fieldName}"
                    placeholder="请输入${item.descName!''}"
                    class="filter-item"
                    size="mini"
                    style="width: 200px; margin-right: 10px"
                    @keyup.enter.native="handleFilter"
            />
