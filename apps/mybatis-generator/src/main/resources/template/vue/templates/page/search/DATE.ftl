<#assign fieldNameStart=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
<#assign fieldNameEnd=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,false)>
        <div class="filter-item" style="margin-right: 10px">
            <el-date-picker
                    size="mini"
                    style="width: 160px"
                    v-model="listQuery.${fieldNameStart}"
                    type="date"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="输入开始${item.descName!''}"
            >
            </el-date-picker>
            <el-link style="margin: 0 5px" :underline="false">至</el-link>
            <el-date-picker
                    style="width: 160px"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    v-model="listQuery.${fieldNameEnd}"
                    type="date"
                    placeholder="输入结束${item.descName!''}"
            >
            </el-date-picker>
        </div>

