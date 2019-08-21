<template>
    <div class="product-container">
        <el-row :span="24">
            <div>1.3客户使用品种及数量统计</div>
        </el-row>
        <!-- <el-row>
             <el-button type="primary" icon="el-icon-search" @click="dialogTableVisible=true">搜索</el-button>
        </el-row> -->
        <el-row :span="24">
            <el-table :data="tableData" highlight-curren-row border style="width: 100%">
                <el-table-column prop="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="客户类型">
                </el-table-column>
                <el-table-column prop="index" label="客户名称">
                </el-table-column>
                <el-table-column prop="index" label="客户状态">
                </el-table-column>
                <el-table-column label="查询">
                    <template slot-scope="scope">
                        <el-button type="text">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <!-- 查看弹出框 -->
        <el-dialog title="泉州华龙使用的常规卫卷纸品种及数量统计表" :visible.sync="dialogTableVisible">
            <el-row :span="24">
                <el-col :span="4">
                    <span>统计起止时间：</span>
                </el-col>
                <el-col :span="8">
                    <el-button type="text">2016年1月1日—2016年3月31日</el-button>
                </el-col>
            </el-row>
            <el-row :span="24">
                <el-table border highlight-curren-row :data="dialogData">
                    <el-table-column property="date" label="序号" width=""></el-table-column>
                    <el-table-column property="name" label="单幅产品编号" width=""></el-table-column>
                    <el-table-column property="address" label="单幅产品名称"></el-table-column>
                    <el-table-column property="address" label="定量(g/m²)"></el-table-column>
                    <el-table-column property="address" label="层数"></el-table-column>
                    <el-table-column property="address" label="幅宽(mm)"></el-table-column>
                    <el-table-column property="address" label="直径(mm)"></el-table-column>
                    <el-table-column property="address" label="累计用量(吨)"></el-table-column>
                </el-table>
            </el-row>
        </el-dialog>
    </div>
</template>

<script>
import Product_API from '@/api/import/product.js'
export default {
    data(){
        return{
            tableData: [],
            dialogData: [],
            dialogTableVisible:false,
            loading: true,
        }
    },
    watch:{
        '$route': function () {
            this.init();
        }
    },
    methods:{
        init(){
            let params = {
                platform: this.$route.meta.machine,
                classNo: this.$route.meta.paper,
                kindNo: this.$route.meta.type,
            }
            Product_API.findCustomersPickUpByList(params).then(res => {
                this.loading = false;
                this.tableData = res.data;
            }).catch(err => {
                this.$message({
                    showClose: true,
                    message: err.msg,
                    type: 'error'
                });
            });
        }
    },
    mounted(){
        this.init()
        
    }
}
</script>
<style scoped>
    .toolbar{
        margin-bottom: 15px
    }
</style>