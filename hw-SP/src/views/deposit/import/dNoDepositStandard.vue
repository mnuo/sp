<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>不收取定金标准设置</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary" @click="goHistory()">历史不收取定金标准</el-button>
        <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%;">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="60"></el-table-column>
    <el-table-column prop="classNo" header-align="center" align="center" label="产品品类" min-width="150"></el-table-column>
    <el-table-column prop="enterpriseSetNo" header-align="center" align="center" label="企业套产品编号" min-width="150"></el-table-column>
    <el-table-column header-align="center" label="不收取定金标准">
      <el-table-column prop="monthOrderWeight" header-align="center" label="月订单量" min-width="150">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.monthOrderWeight" :min="0" :step="100"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="monthDeliveryWeight" header-align="center" label="月出库量" min-width="150">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.monthDeliveryWeight" :min="0" :step="100"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="continuousMonth" header-align="center" label="持续月数" min-width="150">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.continuousMonth"  :min="0" :step="1"></el-input-number>
        </template>
      </el-table-column>
    </el-table-column>
    <el-table-column prop="startTime" header-align="center" label="开始时间" min-width="150">
      <template slot-scope="scope">
        <el-date-picker
          v-model="scope.row.startTime" align="right" type="date" value-format="yyyy-MM-dd" placeholder="执行时间">
        </el-date-picker>
      </template>
    </el-table-column>
    <el-table-column prop="endTime" header-align="center" label="结束时间" min-width="150">
      <template slot-scope="scope">
        <el-date-picker 
        v-model="scope.row.endTime" align="right" type="date" value-format="yyyy-MM-dd" placeholder="结束时间">
        </el-date-picker>
      </template> 
    </el-table-column>
    <el-table-column header-align="center" label="操作" width="65">
      <template slot-scope="scope">
        <el-button @click="updatNoDepoStandard(scope.row)">保存</el-button>
      </template>
    </el-table-column>
    </el-table>
  </div>
</template>
<script>
import Deposit_API from '@/api/import/deposit'
export default {
  name:'',
  data(){
    return{
      tableData:[],
      loading:false,
    }
  },
  components:{},
  methods:{
    
    //初始化数据
    initPage(){
      this.inputQueryProdNoDepoStandard();
    },
    inputQueryProdNoDepoStandard(){
      this.loading=true;
      Deposit_API.inputQueryProdNoDepoStandard().then(res=>{
        this.tableData=res.data||[];
        // console.log(this.tableData);
        this.loading=false;
      }).catch(err=>{
        this.$message({type:"error",message:"请求异常"+err.msg})
      })
    },
    updatNoDepoStandard(params){
      // console.log(params)
      Deposit_API.updatNoDepoStandard(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?"修改成功":res.msg,
        });
        this.inputQueryProdNoDepoStandard();
      }).catch(err=>{
        this.$message({type:'error',message:"修改异常"+err.msg})
      })
    },
    //查看历史数据
    goHistory(){
      this.$router.push({
          path:'/deposit/import/dNoDepositStandardHistory',
      })
    },
  },
  mounted(){
    this.initPage();
  }
}
</script>

<style lang="scss" scoped>
  .el-table td div {
    width: 100%;
    /*padding-left: 0px!important;*/
    /*padding-right:  0px!important;;*/
  }


</style>