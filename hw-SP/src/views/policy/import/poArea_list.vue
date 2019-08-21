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
        <el-button type="primary" @click="dialogAddOne=true">新增</el-button>
        <el-button type="primary" v-on:click="goCheck">审核</el-button>
        <el-button type="info">返回目录</el-button>
      </el-col>
    </el-row>

    <el-table border :data="tableData">
      <!--省份，城市，客户名称，运输方式，现执行运价（元/吨)，补贴金额（元/吨)，开始日期，结束日期
      ，（录入人，（姓名，录入时间））
      ，（审核人，（姓名，审核时间）），修改-->
      <el-table-column type="index" header-align="center" align="center" label="序号" winth="50"></el-table-column>
      <el-table-column prop="city" header-align="center" align="center" label="省份"
                       min-width="50"></el-table-column>
      <el-table-column prop="county" header-align="center" align="center" label="城市"
                       min-width="50"></el-table-column>
      <el-table-column prop="custName" header-align="center" align="left" label="客户名称"
                       min-width="120"></el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="补贴金额（元/吨)"
                       min-width="50"></el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="开始日期"
                       min-width="80"></el-table-column>
      <el-table-column prop="endDate" header-align="center" align="center" label="结束日期"
                       min-width="80"></el-table-column>

      <el-table-column prop="" header-align="center" align="center" label="录入人"
                       min-width="80">
        <el-table-column prop="createrId" header-align="center" align="center" label="姓名"
                         min-width="50"></el-table-column>
        <el-table-column prop="createTime" header-align="center" align="center" label="录入时间"
                         min-width="80"></el-table-column>
      </el-table-column>


      <el-table-column prop="" header-align="center" align="center" label="审核人"
                       min-width="80">
        <el-table-column prop="checkedBy" header-align="center" align="center" label="姓名"
                         min-width="50"></el-table-column>
        <el-table-column prop="checkedDate" header-align="center" align="center" label="审核时间"
                         min-width="80"></el-table-column>
      </el-table-column>

      <el-table-column prop="statusName" header-align="center" align="center" label="状态"
                       min-width="50"></el-table-column>

      <el-table-column
        fixed="right"
        align="center"
        header-align="center"
        label="操作"
        min-width="50">
        <template slot-scope="scope">
          <el-button @click="onClickModifyButton(scope.row)" type="text" size="small">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--新增对话框-->
    <div>
      <el-dialog :visible.sync="dialogAddOne" title="新增" v-dialogDrag width="35%" center>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            客户ID：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-select v-model="saveOneData.customerId" class="my-row">
              <el-option v-for="customer in customerData" :key="customer.fdId" v-bind:value="customer.fdId" v-bind:label="customer.custName">
                {{customer.custName}}
              </el-option>
            </el-select>
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            补贴金额：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input id="input" v-model="saveOneData.amount" type="number"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
            （元/吨)
          </el-col>
        </el-row>


        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            开始日期：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-date-picker
              class="my-row"
              v-model="saveOneData.startDate"
              type="date"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              placeholder="选择开始日期">
            </el-date-picker>
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            结束日期：
          </el-col>
          <el-col :span="12" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            {{saveOneData.endDate}}
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            备注：
          </el-col>
          <el-col :span="16" class="text-left">
            <el-input
              v-model="saveOneData.remark"
              type="textarea"
              :rows="3"
              placeholder="备注">
            </el-input>
          </el-col>
        </el-row>

        <span slot="footer">
          <el-button type="primary" @click="doSave()">确定</el-button>
        </span>
      </el-dialog>
    </div>

    <!--修改对话框-->
    <div>
      <el-dialog :visible.sync="dialogModifyOne" title="修改" v-dialogDrag width="35%" center>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            客户：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-select v-model="modifyOneData.customerId" class="my-row">
              <el-option v-for="customer in customerData" :key="customer.fdId" v-bind:value="customer.fdId" v-bind:label="customer.custName">
                {{customer.custName}}
              </el-option>
            </el-select>
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            补贴金额：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="modifyOneData.amount" type="number"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
            （元/吨)
          </el-col>
        </el-row>


        <!--  <el-row class="row-top">
            <el-col :span="7" class="text-left">
              审核人：
            </el-col>
            <el-col :span="12" class="text-left">
              <el-input v-model="modifyOneData.checkedBy"></el-input>
            </el-col>
          </el-row>-->

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            开始日期：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-date-picker
              v-model="modifyOneData.startDate"
              class="my-row"
              type="date"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              placeholder="选择开始日期">
            </el-date-picker>
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            结束日期：
          </el-col>
          <el-col :span="12" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            {{modifyOneData.endDate}}
          </el-col>
        </el-row>


        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            备注：
          </el-col>
          <el-col :span="16" class="text-left">
            <el-input type="textarea"
                      :rows="3"
                      v-model="modifyOneData.remark"></el-input>
          </el-col>
        </el-row>

        <span slot="footer">
          <el-button type="primary" @click="doModify()">确定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import Policy_API from '@/api/import/policy.js';
  import Base_API from '@/api/import/base.js';
  import {directive} from '@/views/base/import/productBase';
  import Const_Util from '@/utils/consts.js';
  import DataFormat from '@/utils/DataFormat.js';

  export default {
    name: 'poTransport',
    components: {},
    data() {
      return {
        tableData: [],
        loading: false,
        dialogAddOne: false,
        dialogModifyOne: false,
        customerData: [],

        saveOneData: {
          amount: null,
          customerId: null,
          fdMonth: null,
          fdYear: null,
          remark: null,
          startDate: null,
          endDate: null
        },

        modifyOneData: {
          amount: null,
          customerId: null,
          endDate: null,
          fdMonth: null,
          fdYear: null,
          remark: null,
          startDate: null,
          checkedBy: null,
          checkedDate: null
        }
      }
    },
    methods: {
      initPage() {
        this.findAll();
        this.saveOneData.startDate = new Date();
      },
      goCheck() {
        this.$router.push({
          path: '/policy/import/policyAreaPrice/check'
        })
      },
      findAll() {
        Policy_API.findAllPoArea().then(res => {
          res.data.forEach(function (each, index) {
            each.statusName = Const_Util.allStatus.get(each.status);
          });

          this.tableData = res.data || [];
          //console.log(this.tableData)
        });

        Base_API.findByPage().then(res => {
          this.customerData = res.data || [];
        });
      },
      doSave() {
        if (this.saveOneData.customerId === null) {
          this.$message.error('客户未选择');
          return;
        }
        if (this.saveOneData.amount === null) {
          this.$message.error('补贴金额未填写');
          return;
        }

        this.dialogAddOne = false;
        Policy_API.savePoArea(this.saveOneData).then(res => {
          this.$message({
            type: res.success ? 'res.success' : 'res.info',
            message: res.success ? '新增成功' : res.msg,
          });
          this.saveOneData = {};
          this.findAll();
        }).catch(err => {
          debugger;
          this.$message({type: 'error', message: err.msg})
        })
      },
      onClickModifyButton(rowData) {
        this.modifyOneData = rowData;
        this.dialogModifyOne = true;
      }
      ,
      doModify: function () {
        if (this.modifyOneData.amount === null||this.modifyOneData.amount.length===0) {
          this.$message.error('补贴金额未填写');
          return;
        }

        this.dialogModifyOne = false;
        Policy_API.updatePoArea(this.modifyOneData).then(res => {
          this.$message({
            type: res.success ? 'res.success' : 'res.info',
            message: res.success ? '修改成功' : res.msg,
          });
          this.modifyOneData = {};
          this.findAll();
        }).catch(err => {
          this.$message({type: 'error', message: err.msg})
        })
      }
    },
    mounted() {
      this.initPage()
    }
    ,
    computed: {}
    ,
    watch: {
      'saveOneData.startDate': function (newValue, oldValue) {
        var firstDate = new Date(newValue);
        firstDate.setDate(1); //第一天
        var endDate = new Date(firstDate);
        endDate.setMonth(firstDate.getMonth() + 1);
        endDate.setDate(0);
        // this.saveOneData.endDate = endDate.format('yyyy-MM-dd');
        this.saveOneData.endDate = DataFormat.format(endDate, 'yyyy-MM-dd');
        this.saveOneData.fdYear = firstDate.getFullYear();
        this.saveOneData.fdMonth = firstDate.getMonth() + 1;
      },
      'modifyOneData.startDate': function (newValue, oldValue) {
        var firstDate = new Date(newValue);
        firstDate.setDate(1); //第一天
        var endDate = new Date(firstDate);
        endDate.setMonth(firstDate.getMonth() + 1);
        endDate.setDate(0);
        // this.saveOneData.endDate = endDate.format('yyyy-MM-dd');
        this.modifyOneData.endDate = DataFormat.format(endDate, 'yyyy-MM-dd');
        this.modifyOneData.fdYear = firstDate.getFullYear();
        this.modifyOneData.fdMonth = firstDate.getMonth() + 1;
      }
    }
  }
</script>


<style scoped>
  .my-row {
    width: 100%;
  }

</style>
