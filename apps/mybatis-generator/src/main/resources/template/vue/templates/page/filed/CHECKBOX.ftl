                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="${item.descName}">
                                        <el-checkbox-group v-model="${item.name}s">
                                            <#if item.valueList?? && (item.valueList?size > 0)>
                                            <#list item.valueList as oneItem>
                                            <el-checkbox label="${oneItem.value}">${oneItem.desc}</el-checkbox>
                                            </#list>
                                            </#if>

                                        </el-checkbox-group>
                                    </el-form-item>
                                </el-col>
                            </el-row>




