<template>
    <div class="product-container">
        <el-row :span="24">
            <div>卫卷纸产品单幅品种使用量统计</div>
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
                <el-table-column prop="index" label="产品类型">
                </el-table-column>
                <el-table-column prop="index" label="单幅产品编号">
                </el-table-column>
                <el-table-column prop="index" label="定量(g/m²)">
                </el-table-column>
                <el-table-column prop="index" label="层数">
                </el-table-column>
                <el-table-column prop="index" label="单幅幅宽(mm)">
                </el-table-column>
                <el-table-column prop="index" label="直径(mm)">
                </el-table-column>
                <el-table-column prop="index" label="累计用量(吨)">
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
            date:[],
        }
    },
    watch:{

    },
    methods:{
        search(){

        },
        init(){

        }
    },
    mounted(){

    }
}
</script>
<style lang="scss" >

</style>
