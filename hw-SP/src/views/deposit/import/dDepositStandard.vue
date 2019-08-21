<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>定金标准定义</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <el-button type="primary" @click="goDDepositStandardAdjust()">定金标准调整</el-button>
          <el-button type="primary" @click="findHistoryDeposit()">历史定金标准查询</el-button>
          <!-- <el-button type="danger" @click="">返回</el-button> -->
        </div>
      </el-col>
    </el-row>
    <el-table
    :data="tableData" :span-method="objectOneMethod" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="80"></el-table-column>
    <el-table-column prop="kindNo" header-align="center" label="类型名称" min-width="200"></el-table-column>
    <el-table-column prop="productNo" header-align="center" label="单幅产品编号" min-width="200"></el-table-column>
    <el-table-column prop="specification" header-align="center" label="规格" min-width="200"></el-table-column>
    <el-table-column prop="deposit" header-align="center" label="定金标准(元/吨)" min-width="200"></el-table-column>
    <el-table-column prop="startTime" header-align="center" label="开始执行时间" min-width="200"></el-table-column>
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
      tableData:[],
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
    findAllDepoStandard(){
      this.loading=true;
      Deposit_API.findAllDepoStandard().then(res=>{
        this.tableData=res.data||[];
        this.loading=false;
        this.setdatas(this.tableData);
        // console.log(this.tableData);
      }).catch(err=>{
        this.loading=false;
        this.$message({type:"error",message:"请求异常"+err.msg});
        // console.log(err.errorCode)
      })
    },
    
    //合并单元格====================================
    setdatas(){
      var spanOneArr=[],
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
        one: spanOneArr,
      }
    },
    objectOneMethod({row,colunm,rowIndex,columnIndex}){
      if(columnIndex === 1) {
        const _row = (this.setdatas(this.tableData).one)[rowIndex];
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
    //历史记录
    findHistoryDeposit(){
      this.$router.push({
        path:'/deposit/import/dDepositStandardHistory'
      })
    },
  },
  mounted(){
    this.findAllDepoStandard();
  }
}
</script>
