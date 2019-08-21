<template>
    <div class="product-container">
        <el-row :span="24">
            <div>高速纸机常规卫卷纸使用数量统计</div>
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
                <el-table-column prop="productNo" label="单幅产品编号">
                </el-table-column>
                <el-table-column prop="productName" label="单幅产品名称">
                </el-table-column>
                <el-table-column prop="ration" label="定量(g/m²)">
                </el-table-column>
                <el-table-column prop="layer" label="层数">
                </el-table-column>
                <el-table-column prop="width" label="幅宽(mm)">
                </el-table-column>
                <el-table-column prop="diameter" label="直径(mm)">
                </el-table-column>
                <el-table-column prop="pickUpWeight" label="累计用量(吨)">
                </el-table-column>
                <el-table-column label="各客户使用量明细查询">
                     <template slot-scope="scope">
                        <el-button type="text" @click="openDialog(scope.row)">查看</el-button>
                    </template> 
                </el-table-column>
            </el-table>
        </el-row>
        <!-- 查看弹出框 -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogTableVisible">
            <el-row :span="24">
                <el-col :span="6">
                    <el-date-picker
                        v-model="dialogDate"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
                    </el-date-picker>
                </el-col>
                <el-col :span="18">
                    <el-button type="primary" icon="el-icon-search" @click="dialogSearch">搜索</el-button>
                </el-col>
            </el-row>
            <el-row :span="24">
                <el-table border max-height="400" highlight-curren-row :data="dialogData">
                    <el-table-column type="index" label="序号" width="150"></el-table-column>
                    <el-table-column property="custTypeName" label="客户类型" width="200"></el-table-column>
                    <el-table-column property="custName" label="客户名称"></el-table-column>
                    <el-table-column property="status" label="状态"></el-table-column>
                    <el-table-column property="pickUpWeight" label="累计用量(吨)"></el-table-column>
                </el-table>
            </el-row>
        </el-dialog>
    </div>
</template>

<script>
import {parseTime} from '@/utils'
import Product_API from '@/api/import/product.js'
export default {
    data(){
        return{
            dialogData:[],
            dialogTableVisible: false,
            tableData: [],
            date: '',
            dialogDate: '',
            loading:true,
            startTime: '',
            endTime: '',
            dialogStartTime: '',
            dialogEndTime: '',
            dialogTitle: '',
        }
    },
    watch:{
        '$route': function () {
            this.init();
        }
    },
    methods:{
        dialogInit(){
            let params = {
                platform: this.$route.meta.machine,
                classNo: this.$route.meta.paper,
                kindNo: this.$route.meta.type,
                setNo: this.setNo,
                startDate: this.dialogStartTime,
                endDate: this.dialogEndTime,
            }
            Product_API.findCustomersPickUpByList(params).then(res => {
                this.dialogData = res.data;
            });
        },
        search(){//列表搜索
            this.startTime = parseTime(this.date[0],'{y}-{m}-{d}');
            this.endTime = parseTime(this.date[1],'{y}-{m}-{d}');
            this.init();
        },
        openDialog(val){
            this.dialogTableVisible = true;
            this.dialogTitle = `${val.productName}各客户使用量统计表`;
            this.setNo = val.productNo;
            this.dialogInit();
        },
        dialogSearch(){//明细搜索
            this.dialogStartTime = parseTime(this.date[0],'{y}-{m}-{d}');
            this.dialogEndTime = parseTime(this.date[1],'{y}-{m}-{d}');
            this.dialogInit();
        },
        init(){
            let params = {
                platform: this.$route.meta.machine,
                classNo: this.$route.meta.paper,
                kindNo: this.$route.meta.type,
                kindType: 0,//单幅为0，套为1
                startDate: this.startTime,
                endDate: this.endTime
            }
            Product_API.findPickUpByList(params).then(res => {
                this.tableData =res.data;
                this.loading = false;
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
</style>