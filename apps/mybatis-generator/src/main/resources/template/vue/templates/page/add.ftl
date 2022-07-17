<template>
    <el-dialog
            title="添加"
            :visible.sync="dialogVisible"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
            @close="closeDialogHandler"
            width="500px"
            top="50px"
    >
        <el-form
                ref="postFormData"
                :model="postFormData"
                :rules="postRules"
                class="login-form"
                autocomplete="on"
                label-position="right"
                label-width="120px"
        >

            <#list list as item>
                <#include "filed/${item.inputTag}.ftl">
            </#list>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button
                    size="small"
                    type="primary"
                    @click.native.prevent="postItemHandler"
            >添加
            </el-button
            >
        </div>
        <ProgramListDialog
                :visible="listVisible"
                @pushCloseDialog="closeProgramListDialogHandler"
                @pushCheckItem="checkItemHandler"
        />
    </el-dialog>
</template>
<script>
    import {postAdd${entityName?cap_first}} from "@/api/${entityName}/${entityName}";
    import {dateFormat} from "@/utils/formatDate";

    let _postFormData = {
        <#list list as item>
        ${item.name}: '',
        </#list>

    };
    export default {
        name: "${entityName}AddDialog",

        props: {
            visible: {
                type: Boolean,
                default: false,
            },
        },
        watch: {
            visible(newVal) {
                if (newVal) {
                    this.postFormData = Object.assign({}, _postFormData);
                }

                this.dialogVisible = newVal;
            },
        },
        data() {
            return {
                dialogVisible: false,
                postFormData: {},
                postRules: {},
                listVisible: false,
                <#list list as item>


                </#list>




            };
        },
        methods: {


            closeProgramListDialogHandler() {
                this.listVisible = false;
            },

            // 监听弹出框关闭
            closeDialogHandler() {
                this.$emit("pushCloseDialog");
            },
            //   添加项目
            postItemHandler() {

                this.$refs.postFormData.validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: "Loading",
                            spinner: "el-icon-loading",
                            background: "rgba(0, 0, 0, 0.7)",
                        });
                        postAdd${entityName?cap_first}(this.postFormData).then((backdata) => {
                            loading.close();
                            if (backdata.success) {

                                this.$message.success("添加成功");
                                this.$emit("pushItemEnd");
                            }
                        });
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },


        },
    };
</script>
<style scoped>

</style>
