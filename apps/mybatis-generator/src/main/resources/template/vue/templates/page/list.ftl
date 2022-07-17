<template>
    <div class="app-container">
        <div class="filter-container">
            <#assign showSearchBtn=false>
            <#list list as item>
                <#if ((item.searchFlag!0)>0 && item.showListPage)>
                    <#include "search/${item.inputTypeEnum}.ftl">
                    <#assign showSearchBtn=true>
                </#if>
            </#list>
        </div>
        <#if showSearchBtn>
            <el-button
                    v-waves
                    class="filter-item"
                    type="primary"
                    size="mini"
                    icon="el-icon-search"
                    @click="handleFilter"
            > 搜索
            </el-button>
        </#if>

        <el-button
                class="filter-item"
                type="warning"
                icon="el-icon-edit"
                size="mini"
                @click="addVisible = true"
        > 添加
        </el-button>

    </div>
    <el-table
            v-loading="listLoading"
            :data="list"
            border
            style="width: 100%"
            @sort-change="sortChangeHandler"
    >
        <#list list as item>
            <#if (item.showListPage!false)>
                <#if item.valueList?? && (item.valueList?size > 0)>
                    <el-table-column label="${item.descName!''}" min-width="120">
                        <template slot-scope="scope">
                            <span v-text="scope.row.${item.name}Desc"></span>
                        </template>
                    </el-table-column>
                <#else>
                    <el-table-column
                            prop="${item.name}"
                            label="${item.descName!''}"
                            min-width="120"
                            sortable="custom"
                    ></el-table-column>
                </#if>
            </#if>
        </#list>
        <el-table-column label="操作" fixed="right" width="180">
            <template slot-scope="scope">

                <el-button
                        type="text"
                        size="mini"
                        class="text_default"
                        @click="showDetailDialogHandler(scope.row.id)"
                >详情
                </el-button>

                <el-button
                        type="text"
                        size="mini"
                        class="text_default"
                        @click="showModItemDialogHandler(scope.row.id)"
                >编辑
                </el-button>

                <el-button
                        type="text"
                        size="mini"
                        class="text_danger"
                        @click="deleteItemHandler(scope.row.id)"
                >删除
                </el-button>


            </template>
        </el-table-column>
    </el-table>
    <div class="page">
        <el-pagination
                background
                layout="prev, pager, next"
                :page-size="listQuery.pageSize"
                :total="totalNum"
                @current-change="handleCurrentChange"
        >
        </el-pagination>
    </div>

    <DetailDialog
            :visible="detailVisible"
            :itemId="itemId"
            @pushCloseDialog="closeDetailDialogHandler"
    />
    <AddDialog
            :visible="addVisible"
            @pushCloseDialog="closeAddDialogHandler"
            @pushItemEnd="itemAddEndHandler"
    />
    <ModDialog
            :visible="modVisible"
            :itemId="itemId"
            @pushCloseDialog="closeModDialogHandler"
            @pushItemEnd="itemModEndHandler"
    />

    </div>
</template>
<script>
    import {dateFormat} from "@/utils/formatDate";
    import {
        ${entityName}List,
        postDelete${entityName?cap_first},
    } from "@/api/${entityName}/${entityName}";
    import DetailDialog from "./detail";
    import AddDialog from "./add";
    import ModDialog from "./mod";
    import waves from "@/directive/waves"; // waves directive

    export default {
        name: "${entityName?cap_first}Page",
        directives: {waves},
        components: {
            DetailDialog,
            AddDialog,
            ModDialog,
        },
        data() {
            return {
                //   列表查询条件
                listQuery: {
                    pageSize: 20,
                    pageNumber: 1,
                    <#list list as item>
                    <#if ((item.searchFlag!0)>0 && item.showListPage)>
                    <#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
                    ${fieldName}: "",
                    </#if>
                    </#list>
                    orderDesc: "",
                    orderBy: "",
                },
                totalNum: 1,
                list: [],
                listLoading: true,
                // 添加
                addVisible: false,
                // 编辑
                modVisible: false,
                itemId: "",

                // 详情
                detailVisible: false,


            };
        },
        created() {
            this.fetchData("refresh");
        },
        methods: {
            // 排序选择
            sortChangeHandler(item) {
                if (item.order == null) {
                    this.listQuery.orderBy = "";
                    this.listQuery.orderDesc = "";
                }
                if (item.order == "ascending") {
                    this.listQuery.orderDesc = "asc";
                }
                if (item.order == "descending") {
                    this.listQuery.orderDesc = "desc";
                }
                <#list list as item>
                if (item.prop == "${item.name}") {
                    this.listQuery.orderBy = "${item.dbName}";
                }
                </#list>

                this.fetchData("refresh");
            },
            // 添加
            itemAddEndHandler() {
                this.fetchData("refresh");
                this.addVisible = false;
            },
            closeAddDialogHandler() {
                this.addVisible = false;
            },
            // 修改
            showModItemDialogHandler(id) {
                this.itemId = id;
                this.modVisible = true;
            },
            closeModDialogHandler() {
                this.modVisible = false;
            },
            itemModEndHandler() {
                this.fetchData("refresh");
                this.modVisible = false;
            },
            // 删除
            deleteItemHandler(id) {
                this.$confirm("您确定要删除吗?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        const loading = this.$loading({
                            lock: true,
                            text: "Loading",
                            spinner: "el-icon-loading",
                            background: "rgba(0, 0, 0, 0.7)",
                        });
                        postDeleteCost(id).then((backdata) => {
                            loading.close();
                            if (backdata.success) {
                                this.$message.success("删除成功");
                                this.fetchData("refresh");
                            }
                        });
                    })
                    .catch(() => {
                    });
            },

            // 详情
            showDetailDialogHandler(id) {
                this.itemId = id;
                this.detailVisible = true;
            },
            closeDetailDialogHandler() {
                this.detailVisible = false;
            },

            //   页面跳转
            handleCurrentChange(val) {
                this.listQuery.pageNumber = val;
                this.fetchData();
            },
            //   搜索
            handleFilter() {
                this.fetchData("refresh");
            },
            // 列表数据获取
            fetchData(type) {
                this.listLoading = true;
                let _params = {
                    pageSize: this.listQuery.pageSize,
                    pageNumber: this.listQuery.pageNumber,
                };

                if ("refresh" == type) _params.pageNumber = 1;


                <#list list as item>
                <#if ((item.searchFlag!0)>0 && item.showListPage)>
                <#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
                if (this.listQuery.${fieldName} != "")
                    _params.${fieldName} = this.listQuery.${fieldName};
                </#if>
                </#list>


                this.requestList(_params);
            },

            requestList(params) {
                costList(params).then((backdata) => {
                    this.listLoading = false;
                    if (backdata.success) {
                        let _list = backdata.data.list;

                        _list.map((item) => {

                            <#list list as item>
                            <#if item.javaType=='Date'>
                            if (item.${item.name} != null)
                                item.${item.name} = dateFormat(new Date(item.${item.name}), "YYYY-mm-dd");
                            </#if>
                            </#list>


                        });

                        this.list = _list;
                        this.totalNum = backdata.data.page.totalCount;
                    }
                });
            },
        },
    };
</script>