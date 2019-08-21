<template>
  <div>
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
        <el-button type="info" v-on:click="$router.go(-1)">返回上级</el-button>
      </el-col>
    </el-row>

    <el-table border :data="tableData" :span-method="objectOneMethod">
      <!--省份，城市，客户名称，运输方式，现执行运价（元/吨)，补贴金额（元/吨)，开始日期，结束日期
      ，（录入人，（姓名，录入时间））
      ，（审核人，（姓名，审核时间）），修改-->
      <el-table-column type="index" header-align="center" align="center" label="序号" winth="50"></el-table-column>
      <el-table-column prop="city" header-align="center" align="center" label="省份"
                       min-width="50"></el-table-column>
      <el-table-column prop="county" header-align="center" align="center" label="城市"
                       min-width="50"></el-table-column>
      <el-table-column prop="custName" header-align="center" align="left" label="客户名称"
                       min-width="110"></el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="补贴金额（元/吨)"
                       min-width="80"></el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="开始日期"
                       min-width="80"></el-table-column>
      <el-table-column prop="endDate" header-align="center" align="center" label="结束日期"
                       min-width="80"></el-table-column>
      <el-table-column prop="editType" header-align="center" align="center" label="类型"
                       min-width="50"></el-table-column>

      <el-table-column
        fixed="right"
        align="center"
        header-align="center"
        label="审核"
        min-width="50">
        <template slot-scope="scope">
          <el-button @click="onClickCheckButton(scope.row)" type="text" size="small">审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--审核对话框-->
    <div>
      <el-dialog :visible.sync="dialogCheck" title="审核" v-dialogDrag width="25%" center>

        <div align="center">
          <el-button class="button" type="success" v-on:click="doCheck(true)" round>审核通过</el-button>
        </div>
        <br/>
        <br/>
        <div align="center" >
          <el-button class="button" type="warning" v-on:click="doCheck(false)" round>审核不通过</el-button>
        </div>
      </el-dialog>
    </div>


  </div>
</template>

<style scoped>
  .button {
    font-size: 16px;
  }
</style>

<script>
  import Policy_API from '@/api/import/policy.js';
  import {directive} from '@/views/base/import/productBase'

  export default {
    name: 'poTransportCheck',
    data() {
      return {
        tableData: [],
        loading: false,
        dialogCheck: false,
        checkOnData: {
          fdId: null,
          status: 0,
          checkedRemark: null
        }
      }
    },
    methods: {
      initPage() {
        this.findAll();
      },
      findAll() {
        Policy_API.findAllPoArea().then(res => {
          this.tableData = res.data || [];
          //console.log(this.tableData)
        })
      },
      onClickCheckButton(rowData) {
        this.checkOnData = rowData;
        this.dialogCheck = true;
      },
      doCheck(isPassed) {
        if (isPassed) {
          this.checkOnData.status = 30;
        } else {
          this.checkOnData.status = 35;
        }
        this.dialogCheck = false;
        Policy_API.checkPoArea(this.checkOnData).then(res => {
          this.$message({
            type: res.success ? 'res.success' : 'res.info',
            message: res.success ? '审核成功' : res.msg,
          });
          this.checkOnData = {};

          //this.findAll();
        }).catch(err => {
          this.$message({type: 'error', message: err.msg})
        })
      },
      objectOneMethod({row, column, rowIndex, columnIndex}) {

      }
    },
    mounted() {
      this.initPage()
    }
  }
</script>
