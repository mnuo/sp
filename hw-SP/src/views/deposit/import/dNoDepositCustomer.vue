<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>当月不收取定金非常规品明细表</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <el-col :span="2">月&#12288;份：</el-col>
        <el-col :span="4">
          <el-date-picker type="month" format="yyyy-MM" value-format="yyyy-MM" placeholder="选择月份" v-model="monthDate" style="width: 100%;" @change="choseMonth()"></el-date-picker>
        </el-col>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary">说明</el-button>
        <el-button type="primary" @click="goDNoDepositStandard()">不收取定金标准设置</el-button>
        <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
    <el-table-column prop="classNo" header-align="center" label="产品类型" min-width="70"></el-table-column>
    <el-table-column prop="enterpriseSetNo" header-align="center" label="企业套产品编号" min-width="80"></el-table-column>
    <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" min-width="60" :render-header="renderheader"></el-table-column>
    <el-table-column prop="wrincleRate" header-align="center" label="起皱率(%)" min-width="80"></el-table-column>
    <el-table-column prop="layers" header-align="center" label="层数" min-width="50"></el-table-column>
    <el-table-column prop="cutSpec" header-align="center" label="分切规格" min-width="100"></el-table-column>
    <el-table-column prop="diameter" header-align="center" label="直径</br>(mm)" :render-header="renderheader" min-width="60"></el-table-column>
    <el-table-column header-align="center" label="不收定金标准">
      <el-table-column prop="monthOrderWeight" header-align="center" label="月订单量" min-width="80"></el-table-column>
      <el-table-column prop="monthDeliveryWeight" header-align="center" label="月出库量" min-width="80"></el-table-column>
      <el-table-column prop="monthNumber" header-align="center" label="连续月数" min-width="80"></el-table-column>
    </el-table-column>
    
    <el-table-column prop="width" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader" min-width="80"></el-table-column>
    <el-table-column prop="productName" header-align="center" label="单幅产品名称" min-width="80"></el-table-column>
    <el-table-column header-align="center" :label="xMonth">
      <el-table-column prop="orderWeight" header-align="center" label="订单量" min-width="60"></el-table-column>
      <el-table-column prop="deliveryWeight" header-align="center" label="出库量" min-width="60"></el-table-column>
    </el-table-column>
    <el-table-column header-align="center" :label="yMonth">
      <el-table-column prop="orderWeight2" header-align="center" label="订单量" min-width="60"></el-table-column>
      <el-table-column prop="deliveryWeight2" header-align="center" label="出库量" min-width="60"></el-table-column>
    </el-table-column>
    </el-table>
  </div>
</template>
<script>
import Deposit_API from '@/api/import/deposit'
export default {
  name: "price-list",
  data(){
    return{
      loading:false,
      monthDate:'',
      tableData:[],
      xMonth:'',
      yMonth:'',
    }
  },
  components:{},
  methods:{
    renderheader(h, {column, $index}) {
      return h('span', {}, [
        h('span', {}, column.label.split('</br>')[0]),
        h('br'),
        h('span', {}, column.label.split('</br>')[1])
      ])
    },

    //初始化数据
    initPage(){
      this.findAlldNoDepositCustomer();
    },
    findAlldNoDepositCustomer(){
      // this.loading=true;
      var monthDateA=new Date();
      this.monthDate=monthDateA.getFullYear()+'-'+(monthDateA.getMonth()+1);
      var arr=this.monthDate.split('-');
      arr.forEach(function(data,index,arr){
        arr.push(+data);
      });
      if(arr[1]==2){
        this.xMonth='12月份';
        this.yMonth='1月份';
      }
      if(arr[1]==1){
        this.xMonth='11月份';
        this.yMonth='12月份';
      }else{
        this.xMonth=arr[1]-2+'月份';
        this.yMonth=arr[1]-1+'月份';
      }
      Deposit_API.inputQueryNoDepoProd().then(res=>{
        this.loading=true;
        this.tableData=res.data||[];
        this.loading=false;
        // console.log(this.tableData);
      }).catch(err=>{
        this.$message.push({
          type:'error',message:"请求异常"+err.msg,
        })
      })
     
    },

    //查询历史月份
    choseMonth(){
      var arr=this.monthDate.split('-');
      arr.forEach(function(data,index,arr){
        arr.push(+data);
      });
      if(Math.abs(arr[1])===2){
        this.xMonth='12月份';
        this.yMonth='1月份';
      }
      if(Math.abs(arr[1])===1){
        this.xMonth='11月份';
        this.yMonth='12月份';
      }
      if(Math.abs(arr[1])>2){
        this.xMonth=arr[1]-2+'月份';
        this.yMonth=arr[1]-1+'月份';
      }
     
    },

    //跳转-不收取定金标准设置
    goDNoDepositStandard(){
      this.$router.push({
          path:'/deposit/import/dNoDepositStandard',
      })
    },
    //合并单元格====================================
    setdatas(){
      var spanFirst=[2],
          concatFirst=0,
          spanOneArr=[],
          concatOne=0;
      this.tableData.forEach((item,index)=>{
        if(index===0){
          spanOneArr.push(1)
        }else{
          if(item.kindNo === this.tableData[index - 1].kindNo){ //第2列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1;
            spanOneArr.push(0);
          }else{
            spanOneArr.push(1);
            concatOne = index;
          };
        }
      });
      return{
        first: spanFirst,
        one: spanOneArr,
      }
    },
    objectOneMethod({row,colunm,rowIndex,columnIndex}){
      if(columnIndex === 1) {
        const _row = (this.setdatas(this.tableData).first)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },

    //产品定金标准调整
    goDDepositStandardAdjust(){
      this.$router.push({
        path: '/deposit/import/dDepositStandardAdjust'
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
