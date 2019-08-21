<template>
    <div class="product-container">
        <el-row :span="24">
            <div>泉州华龙已提货完成订单明细</div>
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
            <el-col class="text-right" :span="16" >
                <el-button class="text-right" type="warning" @click="$router.go(-1)">返回上一级</el-button>
            </el-col>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" highlight-curren-row border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="订单编号">
                </el-table-column>
                <el-table-column prop="index" label="产品品类">
                </el-table-column>
                <el-table-column prop="index" label="产品类型">
                </el-table-column>
                <el-table-column prop="index" label="下单时间">
                </el-table-column>
                <el-table-column prop="index" label="订单数量(吨)">
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
                    <el-table-column prop="index" label="订单客户提货数量">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column prop="index" label="其他客户提货数量">
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
                <el-table-column prop="index" label="备注">
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
            date: '',
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