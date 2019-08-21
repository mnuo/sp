<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>幅宽及承兑汇票结算差价调整</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table 
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" elemtent-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
    <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="350"></el-table-column>
    <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="150">
    </el-table-column>
    <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="150">
    </el-table-column>
    <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="150">
    </el-table-column>
    <el-table-column prop="endDate" header-align="center" align="center" label="失效时间" min-width="150">
    </el-table-column>
    </el-table>
  </div>
</template>
<script>
  import Price_API from '@/api/import/price'
export default {
   name: "price-list",
  data(){
    return{
       tableData:[],
       loading:false,
       itemName:'1111',
       addPrice:'',
       isPolicyText:'',
       startDate:'',
       isPolicy:true,
       fdId:'',
    }
  },
  components:{},
  methods:{
    //初始化
    initPage(){
      this.itemName=this.$route.query.itemName;
      console.log(this.$route.query.itemName)
      this.findHistory_imPriceDifference();
    },
    findHistory_imPriceDifference(){
      var params=this.itemName;
      Price_API.findHistory_imPriceDifference(params).then(res=>{
        this.loading=true;
        this.tableData=res.data||[];
        console.log(this.tableData)
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({typr:'error',message:err.msg})
      })
    },
    //调整
    doSave(item){
      var params=item;
      console.log(params)
      Price_API.updateOne_imPriceDifference(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功':res.msg,
        })
        this.$router.push({
            path: '/price/import/widthAndBill'
          })
      }).catch(err=>{
        this.$message({type:'error',message:err.msg})
      })
     
    }
  },
  mounted(){
    this.initPage()
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