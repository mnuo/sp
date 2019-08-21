<template>
    <div class="product-container">
        <el-row :span="24">
            <div>高速纸机已生产未提货完成订单</div>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" highlight-curren-row border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="客户类型">
                </el-table-column>
                <el-table-column prop="index" label="客户名称">
                </el-table-column>
                <el-table-column prop="index" label="订单数量">
                </el-table-column>
                <el-table-column prop="index" label="订单总吨数">
                </el-table-column>
                <el-table-column label="生产">
                    <el-table-column prop="index" label="已生产吨数">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column prop="index" label="欠生产吨数">
                    </el-table-column>
                    <el-table-column prop="index" label="状态">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="提货">
                    <el-table-column prop="index" label="已提货吨数">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column prop="index" label="已生产未提货吨数">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                </el-table-column>
                <el-table-column label="明细查询">
                     <template slot-scope="scope">
                        <el-button type="text">查询</el-button>
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