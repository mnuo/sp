<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-col :span="12" class="text-left date-pickerwidth">
          <el-date-picker
            :readonly="true"
            v-model="executeDate"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['00:00:00', '23:59:59']"
          ></el-date-picker>
        </el-col>
        <el-col :span="12" class="text-right">
          <div>
            <el-button v-if="executeStatus === 0" type="primary" @click="toEditControlRate()">约定超量控制系数</el-button>
            <el-button v-if="executeStatus === 0" type="primary" @click="add()">新增客户</el-button>
            <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spanner="el-icon-loading"
      border
      :max-height="tableHeight"
      ref="table"
    >
      <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
      <el-table-column prop="customerName" header-align="center" label="客户名称" min-width="170"></el-table-column>
      <el-table-column prop="customerType" header-align="center" label="客户类型" min-width="60"></el-table-column>
      <el-table-column prop="lastWeight" header-align="center" align="right" label="上月提货量" min-width="60"></el-table-column>
      <el-table-column prop="base" header-align="center" label="约定量" min-width="100">
        <el-table-column prop="promiseLastMonth" header-align="center" align="right" label="上月" min-width="80"></el-table-column>
        <el-table-column prop="promiseThisMonth" header-align="center" align="right" label="本月" min-width="80"></el-table-column>
      </el-table-column>
      <el-table-column prop="base" header-align="center" label="基础量及超量" min-width="100">
        <el-table-column prop="baseWeight" header-align="center" align="right" label="基础量(68%)" min-width="80"></el-table-column>
        <el-table-column prop="overWeight" header-align="center" align="right" label="超量(32%)" min-width="80"></el-table-column>
      </el-table-column>
      <el-table-column prop="baseAmount" header-align="center" align="right" label="基础超量奖" min-width="60"></el-table-column>
      <el-table-column prop="controlRatio" header-align="center" align="center" label="约定超量控制系数" min-width="60"></el-table-column>
      <el-table-column prop="overAmount" header-align="center" align="right" label="约定超量奖" min-width="60"></el-table-column>
      <el-table-column prop="controlAmount" header-align="center" align="right" label="超控制系数提货奖励" min-width="60"></el-table-column>
      <el-table-column v-if="executeStatus === 0" prop header-align="center" align="center" label="操作" width="52">
        <template slot-scope="scope">
          <el-tooltip content="修改" placement="top">
            <el-button class="execute-button" icon="el-icon-edit" @click="goEdit(scope.row)"></el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button class="execute-button" icon="el-icon-delete" @click="deleteOne(scope.row)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <!-- <div class="el-table el-table--fit el-table--border el-table--group el-table--fluid-height el-table--enable-row-hover el-table--enable-row-transition el-table--mini">
    <div class="el-table__header-wrapper"> -->
    <table cellspacing="0" cellpadding="0" border="0" class="defined-table el-table__header" :style="tableData.length<=0?'display:none;':''+' width: '+tableWidth+'px;'">
      <colgroup>
        <col v-for="(item, index) in itemArr"  :key="index" :name="'el-table_9_column_'+index" :width="item.width" :class="index==itemArr.length-1? 'gutter':''">
      </colgroup>
      <thead class="is-group has-gutter">
        <tr class="">
          <th v-for="(item, index) in itemArr" :key="index" :colspan="index==0?'3':'1'" :style="item.width==0?'width:0px':''" rowspan="1" :class="'el-table_9_column_'+index+' is-center   is-leaf ' + ((index==1||index==2)?'displaynone':'') + (item.width==0?' borderzero':'')">
            <div v-if="index == 0" class="cell">合计</div>
            <div v-if="index === 3||index === 4||index === 5||index === 6||index === 7" class="cell textrightcell">{{item.value}}</div>
          </th>
        </tr>
      </thead>
    </table>
    <table cellspacing="0" cellpadding="0" border="0" class="defined-table el-table__header" :style="tableData.length<=0?'display:none;':''+' width: '+tableWidth+'px;'">
      <colgroup>
        <col v-for="(item, index) in itemArr1"  :key="index" :name="'el-table_9_column_'+index" :width="item.width" :class="index==itemArr.length-1? 'gutter':''">
      </colgroup>
      <thead class="is-group has-gutter">
        <tr class="">
          <th v-for="(item, index) in itemArr1" :key="index" :colspan="index==0?'3':'1'" :style="item.width==0?'width:0px':''" rowspan="1" :class="'el-table_9_column_'+index+' is-center   is-leaf ' + ((index==1||index==2)?'displaynone':'') + (item.width==0?' borderzero':'')">
            <div v-if="index == 0" class="cell">计划供货量</div>
            <div v-if="index === 5" class="cell textrightcell">{{item.value}}</div>
          </th>
        </tr>
      </thead>
    </table>
    <table cellspacing="0" cellpadding="0" border="0" class="defined-table el-table__header" :style="tableData.length<=0?'display:none;':''+' width: '+tableWidth+'px;'">
      <colgroup>
        <col v-for="(item, index) in itemArr2"  :key="index" :name="'el-table_9_column_'+index" :width="item.width" :class="index==itemArr.length-1? 'gutter':''">
      </colgroup>
      <thead class="is-group has-gutter">
        <tr class="">
          <th v-for="(item, index) in itemArr2" :key="index" :colspan="index==0?'3':'1'" :style="item.width==0?'width:0px':''" rowspan="1" :class="'el-table_9_column_'+index+' is-center   is-leaf ' + ((index==1||index==2)?'displaynone':'') + (item.width==0?' borderzero':'')">
            <div v-if="index == 0" class="cell">计划供货量与约定量差额</div>
            <div v-if="index == 5" class="cell textrightcell">{{item.value}}</div>
          </th>
        </tr>
      </thead>
    </table>
    <!-- </div>
    </div> -->

    <el-dialog :visible.sync="dialogFormVisible" title="新增客户" v-dialogDrag>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>客户信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">客户名称</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="customerName" @change="choseCustomer" placeholder="客户名称">
                    <el-option
                      v-for="item in customers"
                      :key="item.fdId"
                      :label="item.custName"
                      :value="item.custName"
                    ></el-option>
                  </el-select>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">客户类型</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input v-model="customerType" disabled="disabled"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>约定量和超控制系数提货奖励</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">本月约定量</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="promiseThisMonth"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">超控制系数提货奖励</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="controlAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12"></el-col>
            <el-col :span="12"></el-col>
          </el-col>
        </el-row>
      </div>
      <div slot="footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleE" title="修改" v-dialogDrag>
      <div v-for="item in tableDataA" :key="item.fdId">
        <div class="dialog-pan">
          <div class="dialog-pan-title">
            <div>客户信息</div>
          </div>
          <el-row>
            <el-col :span="24">
              <el-col :span="12">
                <el-col :span="10">
                  <div class="grid-content">客户名称</div>
                </el-col>
                <el-col :span="13">
                  <div>
                    <el-input v-model="item.customerName" disabled="disabled"></el-input>
                  </div>
                </el-col>
              </el-col>
              <el-col :span="12">
                <el-col :span="10">
                  <div class="grid-content">客户类型</div>
                </el-col>
                <el-col :span="13">
                  <div>
                    <el-input v-model="item.customerType" disabled="disabled"></el-input>
                  </div>
                </el-col>
              </el-col>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-col :span="12">
                <el-col :span="10">
                  <div class="grid-content">上月提货量</div>
                </el-col>
                <el-col :span="13">
                  <div>
                    <el-input v-model="item.lastWeight" disabled="disabled"></el-input>
                  </div>
                </el-col>
              </el-col>
              <el-col :span="12"></el-col>
            </el-col>
          </el-row>
        </div>
        <div class="dialog-pan">
          <div class="dialog-pan-title">
            <div>约定量和超控制系数提货奖励</div>
          </div>
          <el-row>
            <el-col :span="24">
              <el-col :span="12">
                <el-col :span="10">
                  <div class="grid-content">本月约定量</div>
                </el-col>
                <el-col :span="13">
                  <div>
                    <el-input type="number" v-model="item.promiseThisMonth"></el-input>
                  </div>
                </el-col>
              </el-col>
              <el-col :span="12">
                <el-col :span="10">
                  <div class="grid-content">超控制系数提货奖励</div>
                </el-col>
                <el-col :span="13">
                  <div>
                    <el-input type="number" v-model="item.controlAmount"></el-input>
                  </div>
                </el-col>
              </el-col>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-col :span="12"></el-col>
              <el-col :span="12"></el-col>
            </el-col>
          </el-row>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="dialogFormVisibleE = false">取 消</el-button>
        <el-button type="primary" @click="editOne()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleE1" title="约定超量控制系数设置" v-dialogDrag>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>约定超量控制系数</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">约定超量控制系数</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="promiseControlRate"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12"></el-col>
          </el-col>
        </el-row>
      </div>
      <div slot="footer">
        <el-button @click="dialogFormVisibleE1 = false">取 消</el-button>
        <el-button type="primary" @click="editControlRate()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Policy_API from "@/api/import/policy";
import axios from "axios";
import Vue from "vue";
export default {
  name: "policyover-list",
  data() {
    return {
      loading: false,
      dialogFormVisible: false,
      dialogFormVisibleE: false,
      dialogFormVisibleE1: false,
      executeDate: [],
      tableData: [],
      tableDataA: [],
      customers: [],
      fdMonth: "",
      fdYear: "",
      tableHeight: 300,
      promiseControlRate: "",
      customerName: "",
      customerId: "",
      customerType: "",
      promiseThisMonth: "",
      controlAmount: "",
      itemArr:[],
      tableWidth:'',
      lastWeightTotal:'',
      promiseLastMonthTotal:'',
      promiseThisMonthTotal:'',
      baseWeightTotal:'',
      overWeightTotal:'',
      itemArr1:[],
      itemArr2:[],
      planSupplyWeight:'',
      executeStatus: this.$route.meta.executeStatus,
    };
  },
  components: {},
  methods: {
    //初始化数据
    findPolicyMaintainOverRewardCustomer() {
      this.executeStatus=this.$route.meta.executeStatus;
      this.loading = true;
      var params = {
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        platform: this.$route.meta.platform,
        paperType: this.$route.meta.paperType,
        status:this.executeStatus
      };
      console.log(params);
      Policy_API.findPolicyMaintainOverRewardCustomer(params)
        .then(res => {
          this.loading = false;
          this.tableData = res.data.contents || [];
          this.planSupplyWeight=res.data.total;
          console.log(this.tableData);
          this.calcTotal();
          this.$nextTick(function() {
            let _this = this;
            setTimeout(function(){_this.calcStype();}, 200);
          });
        })
        .catch(err => {
          this.loading = false;
          this.$message({ type: "error", message: "请求异常：" + err.msg });
          console.log(err.errorCode);
        });
    },
    calcStype:function(){
      // debugger;
      this.itemArr=[];
      this.itemArr1=[];
      this.itemArr2=[];
      if(this.$refs.table==undefined){
        return;
      }
      Array.prototype.slice.call(this.$refs.table.bodyWrapper.previousElementSibling.getElementsByTagName('col')).forEach((item,index) =>{
        console.log(item.getAttribute('width'));
        let value = '';
        let value1 = '';
        let value2 = '';

        if(index === 3) value=this.lastWeightTotal;
        if(index === 4) value=this.promiseLastMonthTotal;
        if(index === 5) {
          value=this.promiseThisMonthTotal;
          value1 = this.planSupplyWeight;
          value2=this.planSupplyWeight-value;

        }
        if(index === 6) value=this.baseWeightTotal;
        if(index === 7) value=this.overWeightTotal;
  
        this.itemArr.push({width:item.getAttribute('width'), value:value});
        this.itemArr1.push({width:item.getAttribute('width'), value:value1});
        this.itemArr2.push({width:item.getAttribute('width'), value:value2});
      });
      this.tableWidth=this.$refs.table.bodyWrapper.previousElementSibling.children[0].offsetWidth+'';
    },
    calcTotal: function(){
      let lastWeightTotal=0;
      let promiseLastMonthTotal=0;
      let promiseThisMonthTotal=0;
      let baseWeightTotal=0;
      let overWeightTotal=0;
      this.tableData.forEach(item=>{
        let lastWeight = Number(item.lastWeight);
        if(!isNaN(lastWeight)){
          lastWeightTotal += lastWeight;
        }
        let promiseLastMonth = Number(item.promiseLastMonth);
        if(!isNaN(promiseLastMonth)) promiseLastMonthTotal += promiseLastMonth;
        let promiseThisMonth = Number(item.promiseThisMonth);
        if(!isNaN(promiseThisMonth)) promiseThisMonthTotal += promiseThisMonth;
        let baseWeight = Number(item.baseWeight);
        if(!isNaN(baseWeight)) baseWeightTotal += baseWeight;
        let overWeight = Number(item.overWeight);
        if(!isNaN(overWeight)) overWeightTotal += overWeight;
      });
      this.lastWeightTotal=Number(lastWeightTotal).toFixed(2);
      this.promiseLastMonthTotal=Number(promiseLastMonthTotal).toFixed(0);
      this.promiseThisMonthTotal=Number(promiseThisMonthTotal).toFixed(0);
      this.baseWeightTotal=Number(baseWeightTotal).toFixed(0);
      this.overWeightTotal=Number(overWeightTotal).toFixed(0);
    },
    toEditControlRate: function() {
      this.dialogFormVisibleE1 = true;
    },
    editControlRate: function() {
      var params = {
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        platform: this.$route.meta.platform,
        paperType: this.$route.meta.paperType,
        controlRate: this.promiseControlRate
      };
      Policy_API.editControlRatePolicyMaintainOverRewardCustomer(params)
        .then(res => {
          this.loading = false;
          this.$message({
            type: res.success ? "success" : "info",
            message: res.success ? "设置成功！" : res.msg
          });
          this.findPolicyMaintainOverRewardCustomer();
          this.dialogFormVisibleE1 = false;
        })
        .catch(err => {
          this.loading = false;
          this.$message({ type: "error", message: err.msg });
          this.dialogFormVisibleE1 = false;
        });
    },
    goEdit: function(item) {
      this.dialogFormVisibleE = true;
      this.tableDataA = [item];
    },
    editOne: function(item) {
      let params = this.tableDataA[0];
      Policy_API.addPolicyMaintainOverRewardCustomer(params)
        .then(res => {
          this.loading = false;
          this.$message({
            type: res.success ? "success" : "info",
            message: res.success ? "修改成功！" : res.msg
          });
          this.findPolicyMaintainOverRewardCustomer();
          this.dialogFormVisibleE = false;
        })
        .catch(err => {
          this.loading = false;
          this.$message({ type: "error", message: err.msg });
          this.dialogFormVisibleE = false;
        });
    },
    add() {
      this.dialogFormVisible = true;
      this.customers = this.getCustomers();
    },
    getCustomers: function() {
      let params = (params = {
        platform: this.$route.meta.platform,
        paperType: this.$route.meta.paperType,
        fdYear: this.fdYear,
        fdMonth: this.fdMonth
      });
      Policy_API.findCustomersNotInOverReward(params).then(res => {
        this.customers = res.data || [];
      });
    },
    choseCustomer: function(value) {
      this.customers.forEach(item => {
        if (item.custName == value) {
          this.customerType = item.custTypeName;
          this.customerId = item.fdId;
        }
      });
    },
    addOne() {
      this.loading = true;
      let params = {
        platform: this.$route.meta.platform,
        paperType: this.$route.meta.paperType,
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        customerName: this.customerName,
        customerType: this.customerType,
        customerId: this.customerId,
        promiseThisMonth: this.promiseThisMonth,
        controlAmount: this.controlAmount
      };
      Policy_API.addPolicyMaintainOverRewardCustomer(params)
        .then(res => {
          this.loading = false;
          this.$message({
            type: res.success ? "success" : "info",
            message: res.success ? "新增成功！" : res.msg
          });
          this.findPolicyMaintainOverRewardCustomer();
          this.dialogFormVisible = false;
        })
        .catch(err => {
          this.loading = false;
          this.$message({ type: "error", message: err.msg });
          this.dialogFormVisible = false;
        });
    },
    deleteOne: function(item){
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {fdId: item.fdId};
          this.loading=true;
          Policy_API.deletePolicyMaintainOverRewardCustomer(params).then(res=>{
            this.loading=false;
            this.$message({
                type:res.success?'success':'info',
                message:res.success?'删除成功！': '请求失败:' + res.msg,
            });
            this.findPolicyMaintainOverRewardCustomer();
          }).catch(err => {
            this.loading=false;
            this.$message({type:'error',message:'请求异常:'+err.msg})
            console.log(err.errorCode);
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
      });
    },
    //获取当前时间
    formatDate(date) {
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      return currentdate;
    },
    currentMonth: function() {
      let oneDayLong = 24 * 60 * 60 * 1000;
      let now = new Date();
      let year = now.getFullYear();
      console.log(now + "," + year);
      let monthStartDate = new Date(year, now.getMonth(), 1); //当前月1号
      let nextMonthStartDate = new Date(year, now.getMonth() + 1, 1); //下个月1号
      let days =
        (nextMonthStartDate.getTime() - monthStartDate.getTime()) / oneDayLong; //计算当前月份的天数
      let monthEndDate = new Date(year, now.getMonth(), days);
      let monthRange = [monthStartDate, monthEndDate];
      console.log(monthRange);
      this.fdYear = year;
      this.fdMonth = now.getMonth()+1;
      // this.fdYear = 2019;
      // this.fdMonth = 5;
      return [
        this.formatDate(monthStartDate) + " 00:00:00",
        this.formatDate(monthEndDate) + " 23:59:59"
      ];
    }
  },
  watch: {
    $route: function() {
      this.findPolicyMaintainOverRewardCustomer();
    }
  },
  created() {},
  mounted() {
    this.executeDate = this.currentMonth();
    this.findPolicyMaintainOverRewardCustomer();
    this.$nextTick(function() {
      // debugger;
      this.tableHeight = window.innerHeight - 120-70; //表头距离浏览器顶部88+分页62+距离底部20=170+10(浮动系数)
      // 监听窗口大小变化
      let self = this;
      window.onresize = function() {
        self.tableHeight = window.innerHeight - 120-70;
          let _this = self;
          setTimeout(function(){_this.calcStype();}, 200);
      };
    });
  }
};
// v-dialogDrag: 弹窗拖拽
Vue.directive("dialogDrag", {
  bind(el, binding, vnode, oldVnode) {
    const dialogHeaderEl = el.querySelector(".el-dialog__header");
    const dragDom = el.querySelector(".el-dialog");
    dialogHeaderEl.style.cursor = "move";

    // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
    const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null);

    dialogHeaderEl.onmousedown = e => {
      // 鼠标按下，计算当前元素距离可视区的距离
      const disX = e.clientX - dialogHeaderEl.offsetLeft;
      const disY = e.clientY - dialogHeaderEl.offsetTop;

      // 获取到的值带px 正则匹配替换
      let styL, styT;

      // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
      if (sty.left.includes("%")) {
        styL =
          +document.body.clientWidth * (+sty.left.replace(/\%/g, "") / 100);
        styT =
          +document.body.clientHeight * (+sty.top.replace(/\%/g, "") / 100);
      } else {
        styL = +sty.left.replace(/\px/g, "");
        styT = +sty.top.replace(/\px/g, "");
      }

      document.onmousemove = function(e) {
        // 通过事件委托，计算移动的距离
        const l = e.clientX - disX;
        const t = e.clientY - disY;

        // 移动当前元素
        dragDom.style.left = `${l + styL}px`;
        dragDom.style.top = `${t + styT}px`;

        //将此时的位置传出去
        //binding.value({x:e.pageX,y:e.pageY})
      };

      document.onmouseup = function(e) {
        document.onmousemove = null;
        document.onmouseup = null;
      };
    };
  }
});
</script>