<template>
    <div class="product-container">
        <el-row :span="24">
            <div>高速纸机待生产订单</div>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" highlight-curren-row border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="客户类型">
                </el-table-column>
                <el-table-column prop="index" label="客户名称">
                </el-table-column>
                <el-table-column prop="index" label="待生产订单数量">
                </el-table-column>
                <el-table-column prop="index" label="订单吨数">
                </el-table-column>
                <el-table-column label="明细查询">
                     <template slot-scope="scope">
                        <el-button type="text">查询</el-button>
                    </template> 
                </el-table-column>
            </el-table>
        </el-row>
        <el-dialog title="泉州华龙使用的非常规卫卷纸品种及数量统计表" :visible.sync="dialogTableVisible">
            <el-row :span="24">
                <el-table border highlight-curren-row :data="dialogData">
                    <el-table-column property="date" label="序号" width=""></el-table-column>
                    <el-table-column property="name" label="订单编号" width=""></el-table-column>
                    <el-table-column property="address" label="产品类型"></el-table-column>
                    <el-table-column property="address" label="下单时间"></el-table-column>
                    <el-table-column property="address" label="要求发货时间"></el-table-column>
                    <el-table-column property="address" label="定量(g/m²)"></el-table-column>
                    <el-table-column property="address" label="层数"></el-table-column>
                    <el-table-column property="address" label="分切规格"></el-table-column>
                    <el-table-column property="address" label="分切单品个数"></el-table-column>
                    <el-table-column property="address" label="分切幅宽合计(mm)"></el-table-column>
                    <el-table-column property="address" label="直径(mm)"></el-table-column>
                    <el-table-column property="address" label="订单数量(吨)"></el-table-column>
                </el-table>
            </el-row>
        </el-dialog>
    </div>
</template>

<script>
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
           
        }
    },
    mounted(){
        this.init()
        
    }
}
</script>
<style scoped>
</style>