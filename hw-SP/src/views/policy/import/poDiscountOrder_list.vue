<template>
  <div>
    <el-row>
      <el-col :span="24" >
        <el-col :span="18" class="search-row">
          <el-col :span="2" class="text-right">客户名称</el-col>
          <el-col :span="4" >
            <el-select v-model="searchCustomerId"  @change="findPolicyDiscountOrder"  placeholder="客户名称">
              <el-option 
                  v-for="item in customers1"
                  :key="item.fdId"
                  :label="item.custName"
                  :value="item.fdId">
              </el-option>
            </el-select> 
          </el-col>
          <el-col :span="2" class="text-right">政策名称</el-col>
          <el-col :span="4" >
            <el-select v-model="searchPolicyName" @change="findPolicyDiscountOrder" placeholder="政策名称">
              <el-option 
                  v-for="item in policyNames1"
                  :key="item.itemNo"
                  :label="item.itemName"
                  :value="item.itemName">
              </el-option>
            </el-select> 
          </el-col>
          <!-- <el-col :span="2" class="text-right">返还方式</el-col>
          <el-col :span="4" >
            <el-select v-model="searchMethod" @change="findPolicyDiscountOrder" placeholder="返还方式">
              <el-option 
                  v-for="item in methods1"
                  :key="item.itemNo"
                  :label="item.itemName"
                  :value="item.itemName">
              </el-option>
            </el-select> 
          </el-col> -->
          <el-col :span="2" class="text-right">生效日期</el-col>
          <el-col :span="2" >
            <el-date-picker
              v-model="searchTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              size="mini"
              @change="findPolicyDiscountOrder"
              value-format="yyyy-MM-dd"
              style="width:230px;"
              >
            </el-date-picker>
          </el-col>
        </el-col>
        <el-col :span="6" class="text-right">
          <div>
            <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中" 
     element-loading-spanner="el-icon-loading" border :max-height="tableHeight">
        <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
        <el-table-column prop="businessDate" header-align="center"  align="center" label="日期" width="80">
          <template slot-scope="scope">
            {{scope.row.businessDate!=null?scope.row.businessDate.substring(0,10):''}}
          </template>
        </el-table-column>
        <el-table-column prop="customerName" header-align="center" label="客户名称"  min-width="100"></el-table-column>
        <el-table-column prop="policyName" header-align="center" label="政策名称" min-width="80"></el-table-column>
        <el-table-column prop="method" header-align="center" label="返还方式" min-width="60"></el-table-column>
        <el-table-column prop="price" header-align="center" align="right" label="单位折扣额(元/吨)" width="110"></el-table-column>
        <el-table-column prop="amount" header-align="center" align="right" label="金额" min-width="60"></el-table-column>
        <el-table-column prop="orderNo" header-align="center" align="center" label="订单编号" min-width="60"></el-table-column>
        <!-- <el-table-column prop="" header-align="center" align="center" label="操作" width="52">
        <template slot-scope="scope">
              <el-tooltip content="查看" placement="top">
                <el-button class="execute-button" icon="el-icon-search" @click="goEdit(scope.row)"></el-button>
              </el-tooltip>
        </template>
        </el-table-column> -->
    </el-table>
    <pagination v-if="pageTotal > 0" @pagination="pagination" :total="pageTotal" :limit="pageData.pageSize"></pagination>
  </div>
</template>
<script>
import Policy_API from '@/api/import/policy'
import Const_Util from '@/utils/consts'
import Pagination from '@/components/Pagination'
import axios from 'axios';
import Vue from 'vue';
export default {
  name:"policysupply-list",
  data(){
    return{
      loading:false,
      dialogFormVisibleA: false,
      dialogFormVisibleE: false,
      tableData:[],
      tableDataA:[],
      tableHeight: 300,
      pageTotal:0,
      pageData:{
        page:1,
        pageSize: 50
      },
      customers1:[],
      policyNames1:[],
      methods1: [],
      searchCustomerId:'',
      searchPolicyName:'',
      searchMethod:'',
      searchTime:[],
    }
  },
  components:{Pagination},
  methods:{
      pagination(val){
     // debugger;
      this.pageData.pageSize = val.limit;
      this.pageData.page=val.page;
      this.findPolicyDiscountOrder();
    },
    //初始化数据
    findPolicyDiscountOrder(){
      this.loading=true;
       this.loading=true;
      var params=this.pageData;
      var params={
        page:this.pageData.page,
        pageSize:this.pageData.pageSize,
        customerId: this.searchCustomerId,
        policyName: this.searchPolicyName == '全部'?'':this.searchPolicyName,
        method: this.searchMethod == '全部'?'':this.searchMethod,
        startDate: (this.searchTime != null && this.searchTime.length >0)?this.searchTime[0] : '',
        endDate: (this.searchTime != null && this.searchTime.length >1)?this.searchTime[1] : '',
      }

      Policy_API.findPolicyDiscountOrder(params).then(res=>{
        this.loading=false;
        this.tableData=res.data.list||[];
        console.log(this.tableData);
         this.pageTotal=parseInt(res.data.rowCount);
        this.pageData.pageSize=parseInt(res.data.pageSize);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    initCustomers:function(){
      this.customers = [];
      let params = {};
      Policy_API.findCooperatingCustomers(params).then(res=>{
        this.customers1=res.data;
        this.customers1.unshift({fdId:'',custName:'全部'});
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA= false;
      })
    },
    initPolicyNames: function(){
      let params={
        typename:'PolicyName'
      };
      Policy_API.findDictData(params).then(res=>{
        this.policyNames1=res.data;
        this.policyNames1.unshift({itemNo:'',itemName:'全部'});
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA= false;
      })
    },
    initMethods: function(){
      let params={
        typename:'DiscountType'
      };
      Policy_API.findDictData(params).then(res=>{
        this.methods1=res.data;
        this.methods1.unshift({itemNo:'',itemName:'全部'});
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA= false;
      })
    },
  },
  created() {},
  watch: {
    $route: function() {
      this.findPolicyDiscountOrder();
    }
  },
  mounted() {
    this.findPolicyDiscountOrder();
     this.initCustomers();
    this.initPolicyNames();
    this.initMethods();
    this.$nextTick(function () {
        this.tableHeight = window.innerHeight - 180;//表头距离浏览器顶部88+分页62+距离底部20=170+10(浮动系数)
        // 监听窗口大小变化
        let self = this;
        window.onresize = function() {
            self.tableHeight = window.innerHeight - 180;
        }
    });
  }
}
</script>