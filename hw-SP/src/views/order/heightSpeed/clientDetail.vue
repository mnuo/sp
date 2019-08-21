<template>
    <div class="product-container">
        <el-row :span="24">
            <div>泉州华龙客户订单明细表</div>
        </el-row>
        <el-row class="tooltop" :span="24">
            <el-col :span="2">
                <div>统计起止时间:</div>
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
            <el-col :span="2">
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            </el-col>
            <el-col :span="2">
                <span>产品类型：</span>
            </el-col>
            <el-col :span="2">
                <el-select v-model="productType" placeholder="请选择">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="2">
                <span>订单状态：</span>
            </el-col>
            <el-col :span="4">
                <el-select v-model="orderStatus" placeholder="请选择">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
             <el-col :span="4">
                <el-button type="primary" icon="el-icon-search" @click="dialogTableVisible=true">说明</el-button>
                <el-button type="warning" icon="el-icon-search">返回上一级</el-button>
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
                <el-table-column prop="index" label="已生产数量(吨)">
                </el-table-column>
                <el-table-column prop="index" label="已提货数量(吨)">
                </el-table-column>
                <el-table-column prop="index" label="待提货数量(吨)">
                </el-table-column>
                <el-table-column prop="index" label="订单状态">
                </el-table-column>
            </el-table>
        </el-row>
        <!-- 查看弹出框 -->
        <el-dialog title="说明" :visible.sync="dialogTableVisible">
            <el-row :span="24">
                <ul>
                    <li class="text-left dot-none">1、本表信息来源：来源于订单系统及K3入库发货信息。</li>
                    <li class="text-left dot-none">2、订单状态</li>
                    <li class="text-left dot-none">（1）待缴定金：客户已提交订单但定金账户不足需补交定金。</li>
                    <li class="text-left dot-none">（2）已交定金待生产：订单已交定金待生产。</li>
                    <li class="text-left dot-none">（3）生产中：订单已有产品入库但未生产完成。</li>
                    <li class="text-left dot-none">（4）已生产待提货：订单已完成生产入库但没有提货完成。</li>
                    <li class="text-left dot-none">（5）订单完成：已提货完成。</li>
                    <li class="text-left dot-none">（6）订单终结：未提货完成，人为做终止订单。</li>
                </ul>

            </el-row>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data(){
        return{
            dialogTableVisible: false,
            tableData: [],
            date: '',
            loading:true,
            startTime: '',
            endTime: '',
            productType: '',
            orderStatus: '',
            options: [{
                value: '选项1',
                label: '黄金糕'
                }, {
                value: '选项2',
                label: '双皮奶'
                }, {
                value: '选项3',
                label: '蚵仔煎'
                }, {
                value: '选项4',
                label: '龙须面'
                }, {
                value: '选项5',
                label: '北京烤鸭'
            }],
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
    .tooltop{
        margin-top: 10px;
    }
    .dot-none{
        list-style-type: none;
    }
</style>