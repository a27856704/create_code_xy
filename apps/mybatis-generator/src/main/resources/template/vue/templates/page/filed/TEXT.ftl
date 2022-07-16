                            <el-form-item prop="${item.name}" label="${item.descName}">
                                <el-input
                                        ref="${item.name}"
                                        v-model="postFormData.${item.name}"
                                        placeholder="请输入${item.descName}"
                                        name="${item.name}"
                                        type="text"
                                        size="mini"
                                        autocomplete="off"
                                        @focus="listVisible = true"
                                />
                            </el-form-item>