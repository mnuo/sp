<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>产品定金标准调整</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <el-col :span="3">
          <el-select v-model="edit" @change="findAllEdit">
            <el-option v-for="item in options" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-col>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <!-- <el-button type="primary" @click="doSave()">保存</el-button> -->
          <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
        </div>
      </el-col>
    </el-row>
    <el-table
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
    stripe border ref="multipleTable" @selection-change="handleSelectionChange"
    style="width:100%">
    <!-- <el-table-column fixed="left" type="selection" width="40" title="全选"></el-table-column> -->
    <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
    <el-table-column prop="kindNo" header-align="center" label="类型名称" min-width="150"></el-table-column>
    <el-table-column prop="productNo" header-align="center" label="单幅产品编号" min-width="150"></el-table-column>
    <el-table-column prop="specification" header-align="center" label="规格" min-width="150"></el-table-column>
    <el-table-column prop="deposit" header-align="center" label="定金标准(元/吨)" min-width="150">
      <template slot-scope="scope">
        <el-input-number v-model="scope.row.deposit" controls-position="right" :step="100" :min="0">
        </el-input-number>
      </template>
    </el-table-column>
    <el-table-column prop="startTime" header-align="center" label="执行时间" min-width="150">
      <template slot-scope="scope">
        <el-date-picker
          v-model="scope.row.startTime" align="right" type="date" value-format="yyyy-MM-dd" placeholder="执行时间">
        </el-date-picker>
      </template>
    </el-table-column>
    <!-- <el-table-column prop="endTime" header-align="center" label="结束时间" min-width="150">
      <template slot-scope="scope">
        <el-date-picker 
          v-model="scope.row.endTime" align="right" type="date" value-format="yyyy-MM-dd" placeholder="结束时间">
        </el-date-picker>
      </template>
    </el-table-column> -->
    <el-table-column prop="createrId" header-align="center" label="修改人" min-width="90"></el-table-column>
    <el-table-column prop="createTime" header-align="center" label="修改时间" min-width="90"></el-table-column>
    <el-table-column prop="remark" header-align="center" label="修改" min-width="65">
      <template slot-scope="scope" >
        <el-button @click="doSave(scope.row)">修改</el-button>
        <!-- <a style="color:#0066cc" @click="goProductBaseDeatil(scope.row)">修改</a> -->
      </template>
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
      tableData:[],
      options: [{
        value: '1',
        label: '第1修改人'
      }, {
        value: '2',
        label: '第2修改人'
      }],
      edit:'1',
      multipleSelection: [],
      selectedProd: []
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
    findAllEdit(){
      this.loading=true;
      var params=this.edit;
      Deposit_API.findAllEdit(params).then(res=>{
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
    //选择
    handleSelectionChange(val){
      // console.log(val)
      this.multipleSelection=val;
    },
    //保存
    doSave(params){
      // console.log(params)
      // var params=this.multipleSelection||[];
      if(params.length==0){
        this.$message({
          type:'info',
          message:'没有要保存的数据！'
        })
        return;
      }
      
      let _this=this;
      Deposit_API.updateOneDeposit(params).then(res=>{
        // console.log(params, " kkkkkk")
        this.findAllEdit();
        // console.log(res.data, " kkkkkk")
        this.$message({
          type:res.success ? 'success' :'info',
          message:res.success?res.data :res.msg
        })
      }).catch(err=>{
        this.$message({
          type:'error',
          message:'保存异常:' + err.msg
        })
      })
      let k = 1;
    },
  },

  mounted(){
    this.findAllEdit();
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