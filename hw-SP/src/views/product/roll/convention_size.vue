<template>
    <div class="product-container">
        <el-row :span="24">
            <div v-if="$route.meta.type == 'a'">高速纸机常规卫卷纸产品规格</div>
            <div v-else-if="$route.meta.type == 'b'">高速纸机非常规卫卷纸产品规格</div>
            <div v-else>高速纸机特规卫卷纸产品规格</div>
        </el-row>
        <el-row :span="24">
            <el-col :span="20"><div>&nbsp;</div></el-col>
            <el-col :span="4" >
                <el-button class="text-right" v-if="history" type="warning" @click="goReturn">返回上一级</el-button>
                <el-button v-if="!history" type="primary" @click="jumpIllustrate">编号规则说明</el-button>
                <el-button v-if="!history" type="primary" @click="goHistory">历史产品规格查询</el-button>
            </el-col>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" :span-method="objectSpanMethod" v-loading="loading" highlight-curren-row border style="width: 100%">
                <el-table-column  type="index" label="序号">
                </el-table-column>
                <el-table-column prop="enterpriseSetNo" label="套产品编号">
                </el-table-column>
                <el-table-column prop="cutSpec" label="分切规格">
                </el-table-column>
                <el-table-column prop="totalWidth" label="分切幅宽合计(mm)" width="180">
                </el-table-column>
                <el-table-column prop="enterpriseWidthNo" label="单幅产品编号">
                </el-table-column>
                <el-table-column prop="enterpriseWidthName" label="单幅产品名称">
                </el-table-column>
                <el-table-column prop="ration" label="定量(g/m²)">
                </el-table-column>
                <el-table-column prop="layer" label="层数">
                </el-table-column>
                <el-table-column prop="width" label="幅宽(mm)">
                </el-table-column>
                <el-table-column prop="diameter" label="直径(mm)">
                </el-table-column>
                <el-table-column prop="qty" label="单幅产品数">
                </el-table-column> 
                <el-table-column label="物理指标">
                    <template slot-scope="scope">
                        <el-button type="text">查看</el-button>
                    </template> 
                </el-table-column>
            </el-table>
        </el-row>
    </div>
</template>

<script>
import Product_API from '@/api/import/product.js'
export default {
    data(){
        return{
            arr1:[],
            tableData: [],
            loading:true,
            history:false,
        }
    },
    watch:{
        '$route': function () {
             this.init();
        }
    },
    created() {
        this.init()
    },
    methods:{
        goReturn(){
            this.history =false;
            this.init();
        },
        goHistory(){
            this.history = true;
            this.init();
        },
        jumpIllustrate(){
            this.$router.push({
                path: `/product/illustrate`,
            });
        },
        setdates(arr) {
            var obj = {},
                k, 
                arr1 = [];
            for(var i = 0, len = arr.length; i < len; i++) {
                k = arr[i].enterpriseSetNo;
                if(obj[k]){
                    obj[k]++;
                }else{
                    obj[k] = 1;
                }
            }
            console.log(obj)
            //保存结果{el-'元素'，count-出现次数}
            for(var o in obj) {
                for(let i=0;i<obj[o];i++){
                    if(i===0){
                        this.arr1.push(obj[o])
                    }else{
                        this.arr1.push(1)
                    }
                }
            }
        },
        objectSpanMethod({ row, column, rowIndex, columnIndex }) {
            if (columnIndex === 1||columnIndex === 2||columnIndex === 3||columnIndex === 11) {
                if ( this.arr1[rowIndex] !== 1) {
                    return {
                    rowspan: this.arr1[rowIndex],
                    colspan: 1
                    };
                } else {
                    return {
                        rowspan: 0,
                        colspan: 0
                    };
                }
            }
        },
        init(){
            let params = {
                platform: this.$route.meta.machine,
                classNo: this.$route.meta.paper,
                kindNo: this.$route.meta.type,
                isHistory: this.history
            }
            Product_API.findProduct(params).then(res => {
                 this.tableData = res.data;
                 this.loading = false;
                 this.setdates(this.tableData);
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
    }
}
</script>
<style scoped>
    .toolbar{
        margin-bottom: 15px
    }
    .text-right{
        float: right
    }
</style>