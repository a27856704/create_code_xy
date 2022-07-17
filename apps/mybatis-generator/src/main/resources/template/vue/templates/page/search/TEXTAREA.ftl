<#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
                    <el-input
                            v-model="listQuery.${fieldName}"
                            placeholder="请输入${item.descName!''}"
                            class="filter-item"
                            size="mini"
                            type="textarea"
                            rows="5"
                            autocomplete="on"
                            name="${fieldName}"
                            ref="${fieldName}"
                            @keyup.enter.native="handleFilter"
                    />