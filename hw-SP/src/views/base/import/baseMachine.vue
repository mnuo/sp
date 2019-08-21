<template>
  <div id="baseMachine">
    <!-- <el-row>
      <el-col :span="24">
        <span><b>（二）生产机械产能维护表</b></span>
      </el-col>
    </el-row> -->
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary" @click="dialogAddOne=true">新增</el-button>
        <el-button type="primary">保存</el-button>
        <el-button type="info">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table border :data="tableData" :span-method="objectOneMethod">
      <el-table-column type="index" header-align="center" align="center" label="序号" winth="50" ></el-table-column>
      <el-table-column prop="machineType" header-align="center" align="center" label="生产机型" min-width="80"></el-table-column>
      <el-table-column prop="machineNo" header-align="center" align="center" label="机台" min-width="80"></el-table-column>
      <el-table-column prop="singleCapacity" header-align="center" align="center" label="单机产能（吨/月）" min-width="80"></el-table-column>
      <el-table-column prop="totalCapacity" header-align="center" align="center" label="产能总计（吨/月）" min-width="80"></el-table-column>
      <el-table-column prop="createrId" header-align="center" align="center" label="录入人" min-width="80"></el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="录入时间" min-width="80"></el-table-column>
    </el-table>
    <div>
      <el-dialog :visible.sync="dialogAddOne" title="新增" v-dialogDrag width="25%" center>
        <el-row>
          <el-col :span="7" class="text-left">生产机型：</el-col>
          <el-col :span="12">
            <el-select v-model="machineType">
              <el-option value="高速纸机" label="高速纸机">高速纸机</el-option>
              <el-option value="3900纸机" label="3900纸机">3900纸机</el-option>
              <el-option value="1575纸机" label="1575纸机">1575纸机</el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            机&#12288;&#12288;台：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="machineNo"></el-input>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            单机产能：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="singleCapacity"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
            (吨/月)
          </el-col>
        </el-row>
        <span slot="footer">
          <el-button type="primary" @click="doSave()">确定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- <div style="margin-top:20px">
      <textarea style="width:100%;height:80px;background-color:#c8dec2">功能说明：
        1.单机产能：手工录入
        2.产能合计：系统计算
      </textarea>
    </div> -->
  </div>
</template>
<script>
import Base_API from '@/api/import/base';
import {directive} from '@/views/base/import/productBase'
export default {
  name:'',
  data(){
    return{
      tableData:[],
      loading:false,
      dialogAddOne:false,
      machineType:'',
      machineNo:'',
      singleCapacity:0,
    }
  },
  methods:{
    initPage(){
      this.findAll();
    },
    findAll(){
      Base_API.findAll().then(res=>{
        this.tableData=res.data||[];
        this.setdates(this.tableData);
        console.log(this.tableData)
      })
    },
    doSave(){
      var params={
        machineType:this.machineType,
        machineNo:this.machineNo,
        singleCapacity:this.singleCapacity,
      };
      Base_API.addOneMachine(params).then(res=>{
        this.$message({
          type:res.success?'res.success':'res.info',
          message:res.success?'新增成功':res.msg,
        });
        this.dialogAddOne=false;
        // debugger
        this.findAll();
        // this.findAll();
      }).catch(err=>{
        this.$message({type:'error',message:err.msg})
      })
    },
    //合并单元格
//合并单元格
    setdates(arr){
      var spanOneArr = [],          
          concatOne = 0;
      this.tableData.forEach((item,index)=>{
        if(index === 0){
          spanOneArr.push(1);
        }else{
          if(item.machineType === this.tableData[index - 1].machineType){ //第2列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1;
            spanOneArr.push(0);
          }else{
            spanOneArr.push(1);
            concatOne = index;
          };
         
        }
      });
        return  {
        one: spanOneArr,
      }
    },
    objectOneMethod({ row, column, rowIndex, columnIndex }) {
      if(columnIndex === 1||columnIndex===4) {
        const _row = (this.setdates(this.tableData).one)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
  },
  mounted(){
    this.initPage()
  }
}
</script>
