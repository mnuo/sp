<template>
    <div class="product-container">
        <el-row :span="24">
            <div>高速纸机客户订单明细</div>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" highlight-curren-row border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="客户类型">
                </el-table-column>
                <el-table-column prop="index" label="客户名称">
                </el-table-column>
                <el-table-column label="未完成订单数量">
                    <el-table-column prop="index" label="待缴定金">
                    </el-table-column>
                    <el-table-column prop="index" label="待生产">
                    </el-table-column>
                    <el-table-column prop="index" label="生产中">
                    </el-table-column>
                    <el-table-column prop="index" label="待提货">
                    </el-table-column>
                    <el-table-column prop="index" label="小计">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="已完成或已终结订单数量">
                    <el-table-column prop="index" label="订单完成">
                    </el-table-column>
                    <el-table-column prop="index" label="订单终结">
                    </el-table-column>
                    <el-table-column prop="index" label="小计">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="index" label="客户状态">
                </el-table-column>
                <el-table-column label="查询">
                     <template slot-scope="scope">
                        <el-button type="text" @click="jumpDetail(scope.row)">查看</el-button>
                    </template> 
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
        jumpDetail(val){
            
        },
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