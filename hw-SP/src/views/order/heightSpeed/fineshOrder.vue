<template>
    <div class="product-container">
        <el-row :span="24">
            <div>高速纸机已完成订单</div>
        </el-row>
        <el-row :span="24">
            <el-col :span="2">
                <div>统计时间：</div>
            </el-col>
            <el-col :span="6">
                 <el-date-picker
                    v-model="date"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                </el-date-picker>
            </el-col>
            <el-col :span="3">
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            </el-col>
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
                <el-table-column prop="index" label="已生产吨数">
                    <el-table-column prop="index" label="优等品">
                    </el-table-column>
                    <el-table-column prop="index" label="副品">
                    </el-table-column>
                    <el-table-column prop="index" label="合计">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="index" label="已提货吨数">
                    <el-table-column prop="index" label="优等品">
                    </el-table-column>
                    <el-table-column prop="index" label="副品">
                    </el-table-column>
                    <el-table-column prop="index" label="合计">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="明细查询">
                     <template slot-scope="scope">
                        <el-button type="text" >查询</el-button>
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
            date: '',
            loading:true,
            startTime: '',
            endTime: '',
        }

    },
    watch:{
        '$route': function () {
            this.init();
        }
    },
    methods:{
        init(){
           
        },
        search(){

        },

    },
    mounted(){
        this.init()
        
    }
}
</script>
<style scoped>
</style>