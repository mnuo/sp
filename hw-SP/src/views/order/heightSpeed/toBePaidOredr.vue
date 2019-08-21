<template>
    <div class="product-container">
        <el-row :span="24">
            <div>高速纸机待缴定金订单</div>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" show-summary highlight-curren-row border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="客户名称">
                </el-table-column>
                <el-table-column prop="index" label="订单编号">
                </el-table-column>
                <el-table-column prop="index" label="待缴定金金额(元)">
                </el-table-column>
                <el-table-column prop="index" label="产品品类">
                </el-table-column>
                <el-table-column prop="index" label="产品类型">
                </el-table-column>
                <el-table-column prop="index" label="下单时间">
                </el-table-column>
                <el-table-column prop="index" label="要求发货时间">
                </el-table-column>
                <el-table-column prop="index" label="定量(g/m²)">
                </el-table-column>
                <el-table-column prop="index" label="层数">
                </el-table-column>
                <el-table-column prop="index" label="分切规格">
                </el-table-column>
                <el-table-column prop="index" label="分切单品个数">
                </el-table-column>
                <el-table-column prop="index" label="分切幅宽合计(mm)">
                </el-table-column>
                <el-table-column prop="index" label="直径(mm)">
                </el-table-column>
                <el-table-column prop="index" label="订单数量(吨)">
                </el-table-column>
            </el-table>
        </el-row>
    </div>
</template>

<script>
export default {
    data(){
        return{
            tableData:[],
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