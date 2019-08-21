<template>
  <div>
    <el-row>
      <el-col :span="24" >
        <el-col :span="18" class="search-row">
          <el-col :span="2" class="text-right">客户名称</el-col>
          <el-col :span="4" >
            <el-select v-model="searchCustomerId"  @change="findPolicyDiscount"  placeholder="客户名称">
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
            <el-select v-model="searchPolicyName" @change="findPolicyDiscount" placeholder="政策名称">
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
            <el-select v-model="searchMethod" @change="findPolicyDiscount" placeholder="返还方式">
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
              @change="findPolicyDiscount"
              value-format="yyyy-MM-dd"
              style="width:230px;"
              >
            </el-date-picker>
          </el-col>
        </el-col>
        <el-col :span="6" class="text-right">
          <div>
            <el-button type="primary" @click="add()">新增</el-button>
            <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中" 
     element-loading-spanner="el-icon-loading" border :max-height="tableHeight">
        <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
        <!-- <el-table-column prop="businessDate" header-align="center"  align="center" label="日期" width="78">
          <template slot-scope="scope">
            {{scope.row.businessDate!=null?scope.row.businessDate.substring(0,10):''}}
          </template>
        </el-table-column> -->
        <el-table-column prop="customerName" header-align="center" label="客户名称"  min-width="205"></el-table-column>
        <el-table-column prop="policyName" header-align="center" label="政策名称" min-width="130"></el-table-column>
        <el-table-column prop="method" header-align="center" label="返还方式" width="75"></el-table-column>
        <el-table-column prop="price" header-align="center" align="right" label="单位折扣额(元/吨)" width="60"></el-table-column>
        <el-table-column prop="amount" header-align="center" align="right" label="金额" width="60"></el-table-column>
        <el-table-column prop="commitAmount" header-align="center" align="right" label="已冲金额" width="60"></el-table-column>
        <el-table-column prop="balanceAmount" header-align="center" align="right" label="未冲金额" width="60"></el-table-column>
        <el-table-column prop="startDate" header-align="center" align="center" label="生效日期" width="78">
          <template slot-scope="scope">
            {{scope.row.startDate!=null?scope.row.startDate.substring(0,10):''}}
          </template>
        </el-table-column>
        <el-table-column prop="endDate" header-align="center" align="center" label="失效日期" width="78">
          <template slot-scope="scope">
            {{scope.row.endDate!=null?scope.row.endDate.substring(0,10):''}}
          </template>
        </el-table-column>
        <el-table-column prop="cancelStr" header-align="center" align="center" label="作废标识" width="60"></el-table-column>
        <el-table-column prop="rowCloseStr" header-align="center" align="center" label="关闭标识" width="60"></el-table-column>
        <el-table-column prop="statusStr" header-align="center" align="center" label="状态" width="50"></el-table-column>
        <!-- <el-table-column prop="sourceBillNo" header-align="center" label="派单编码" min-width="80"></el-table-column> -->
        <el-table-column prop="fromSystem" header-align="center" label="来源系统" width="85"></el-table-column>
        <el-table-column prop="" header-align="center" align="center" label="操作" width="75">
          <template slot-scope="scope">
              <el-tooltip content="查看" placement="top">
                <el-button class="execute-button" icon="el-icon-search" @click="goView(scope.row)"></el-button>
              </el-tooltip>
              <el-tooltip v-if="scope.row.isCancel != 1 && scope.row.isRowClose != 1 && scope.row.status == 10 && (scope.row.fromSystem == null || scope.row.fromSystem == '') && scope.row.commitAmount <=0 " content="修改" placement="top">
                <el-button class="execute-button" icon="el-icon-edit" @click="goEdit(scope.row)"></el-button>
              </el-tooltip>
              <el-tooltip v-if="scope.row.isCancel != 1 && scope.row.isRowClose != 1 && scope.row.status == 10 && (scope.row.fromSystem == null || scope.row.fromSystem == '') && scope.row.commitAmount <=0 " content="删除" placement="top">
                <el-button class="execute-button" icon="el-icon-delete" @click="deleteOne(scope.row)"></el-button>
              </el-tooltip>
               <el-tooltip v-if="scope.row.commitAmount >0" content="查看折扣明细" placement="top">
                <el-button class="execute-button" icon="el-icon-tickets" @click="goViewDetail(scope.row)"></el-button>
              </el-tooltip>
          </template>
        </el-table-column>
    </el-table>
    <pagination v-if="pageTotal>0" @pagination="pagination" :total="pageTotal" :limit="pageData.pageSize"></pagination>

    <el-dialog :visible.sync="dialogFormVisibleA" title="新增" width="60%" top="10vh" v-dialogDrag>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">客户名称</div>
              </el-col>
              <el-col :span="13">
                <div>
                </div>
                  <el-select v-model="customerId"  @change="choseCustomer"  placeholder="客户名称">
                    <el-option 
                        v-for="item in customers"
                        :key="item.fdId"
                        :label="item.custName"
                        :value="item.fdId">
                    </el-option>
                  </el-select> 
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">政策名称</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="policyName"  placeholder="政策名称">
                    <el-option 
                        v-for="item in policyNames"
                        :key="item.itemNo"
                        :label="item.itemName"
                        :value="item.itemName">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">返还方式</div>
              </el-col>
              <el-col :span="13">
                <div>
                   <el-select v-model="method"  placeholder="返还方式">
                    <el-option 
                        v-for="item in methods"
                        :key="item.itemNo"
                        :label="item.itemName"
                        :value="item.itemName">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">财务日期</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                  <el-date-picker
                    value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="businessDate"
                    type="date"
                    placeholder="财务日期">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">单位折扣（元/吨）</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="price" ></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">折扣金额（元）</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="amount" ></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">生效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                   <el-date-picker
                   value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="startDate"
                    type="date"
                    placeholder="生效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">失效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                  <el-date-picker
                    value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="endDate"
                    type="date"
                    placeholder="失效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
         <el-row>
          <el-col :span="24">
            <el-col :span="24">
              <el-col :span="5">
                <div class="grid-content">备注</div>
              </el-col>
              <el-col :span="18" class="textarea-bottom">
                <div>
                  <el-input type="textarea" placeholder="备注" v-model="remark"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleA = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleE" :title="edit?'查看':'修改'" width="60%" top="10vh" v-dialogDrag>
      <div v-for="item in tableDataA" :key="item.fdId">
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">客户名称</div>
              </el-col>
              <el-col :span="13">
                <div>
                </div>
                  <el-select v-model="item.customerId" :disabled="true" @change="choseCustomer"  placeholder="客户名称">
                    <el-option 
                        v-for="item in customers"
                        :key="item.fdId"
                        :label="item.custName"
                        :value="item.fdId">
                    </el-option>
                  </el-select> 
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">政策名称</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="item.policyName" :disabled="edit" placeholder="政策名称">
                    <el-option 
                        v-for="item in policyNames"
                        :key="item.itemNo"
                        :label="item.itemName"
                        :value="item.itemName">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">返还方式</div>
              </el-col>
              <el-col :span="13">
                <div>
                   <el-select v-model="item.method" :disabled="edit" placeholder="返还方式">
                    <el-option 
                        v-for="item in methods"
                        :key="item.itemNo"
                        :label="item.itemName"
                        :value="item.itemName">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">财务日期</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                  <el-date-picker :disabled="edit"
                    value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="item.businessDate"
                    type="date"
                    placeholder="财务日期">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">单位折扣（元/吨）</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" :disabled="edit" v-model="item.price" ></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">折扣金额（元）</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" :disabled="edit" v-model="item.amount" ></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">生效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                   <el-date-picker :disabled="edit"
                   value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="item.startDate"
                    type="date"
                    placeholder="生效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">失效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                  <el-date-picker :disabled="edit"
                    value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="item.endDate"
                    type="date"
                    placeholder="失效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
         <el-row>
          <el-col :span="24">
            <el-col :span="24">
              <el-col :span="5">
                <div class="grid-content">备注</div>
              </el-col>
              <el-col :span="18" class="textarea-bottom">
                <div>
                  <el-input type="textarea" :disabled="edit" placeholder="备注" v-model="item.remark"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      </div>
      <div v-if="edit==false" slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleE = false">取 消</el-button>
        <el-button type="primary" @click="editOne()">确 定</el-button>
      </div>  
      <div v-if="edit==true" slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleE = false">取 消</el-button>
        <el-button type="primary" v-if="tableDataA[0].isCancel != '1'" @click="cancelOne()">作废</el-button>
        <el-button type="primary" v-if="tableDataA[0].isCancel == '1'" @click="convertCancelOne()">反作废</el-button>
        <el-button type="primary" v-if="tableDataA[0].status != '30'" @click="approveOne()">审核</el-button>
        <el-button type="primary" v-if="tableDataA[0].status == '30'" @click="convertApproveOne()">反审核</el-button>
      </div>    
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleV" title="折扣信息明细" v-dialogDrag>
      <el-table 
      :data="tableDataDetails" v-loading="loading" element-loading-text="加载中" element-loading-spanner="el-icon-loading"
      border style="width:100%" class="input-table">
      <el-table-column type="index" align="center" header-align="center" label="序号" min-width="40"></el-table-column>
      <el-table-column prop="businessDate" align="center" header-align="center" label="日期" min-width="100">
        <template slot-scope="scope">
          {{scope.row.businessDate != null ? scope.row.businessDate.substring(0,10):''}}
        </template>
      </el-table-column>
      <el-table-column prop="orderNo" align="center" header-align="center" label="订单编号" min-width="100"></el-table-column>
      <el-table-column prop="amount" align="right" header-align="center" label="折扣金额" min-width="100"></el-table-column>
      </el-table>
      <div slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleV = false">关 闭</el-button>
      </div>     
    </el-dialog>
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
      dialogFormVisibleV: false,
      tableData:[],
      tableDataA:[],
      tableDataDetails:[],
      fdMonth:'',
      fdYear:'',
      tableHeight: 300,
      customers:[],
      policyNames:[],
      methods: [],
      customers1:[],
      policyNames1:[],
      methods1: [],
      customerId:'',
      customerName:'',
      policyName:'',
      businessDate: '',
      method:'',
      price:'',
      amount:'',
      commitAmount:'',
      balanceAmount:'',
      startDate:'',
      endDate:'',
      remark:'',
      pageTotal:0,
      pageData:{
        page:1,
        pageSize: 50
      },
      edit:false,
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
      this.findPolicyDiscount();
    },
    //初始化数据
    findPolicyDiscount(){
      debugger;
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

      Policy_API.findPolicyDiscount(params).then(res=>{
        this.loading=false;
        this.tableData=res.data.list||[];
        console.log(this.tableData);
         this.pageTotal=parseInt(res.data.rowCount);
        this.pageData.pageSize=parseInt(res.data.pageSize);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg==null?err.message:err.msg});
        console.log(err.errorCode);
      })
    },
    goViewDetail: function(item){
      this.tableDataDetails=[];
        let params = {
          discountId: item.fdId
        };
        this.dialogFormVisibleV = true;
        Policy_API.findDiscountOrderDetails(params).then(res => {
          this.tableDataDetails=res.data||[];
        }).catch(err=>{
          this.loading=false;
          this.$message({type: 'error', message: '请求异常：' + err.msg})
          console.log(err.errorCode);
      });
    },
    choseCustomer:function(val){
      this.customers.forEach(item => {
        if(item.fdId == val){
          this.customerName=item.custoName;
        }
      });
    },
    goEdit: function (item){
      this.tableDataA=[item];
      this.dialogFormVisibleE=true;
      this.edit = false;
    },
    goView: function (item){
      this.tableDataA=[item];
      this.dialogFormVisibleE=true;
      this.edit = true;
    },
    editOne(){
     var params=this.tableDataA[0];
      if(!!params.startDate){
        params.startDate =params.startDate + " 00:00:00";
      }
      if(!!params.endDate){
        params.endDate = params.endDate + " 00:00:00";
      }
      if(!!params.businessDate){
        params.businessDate =params.businessDate + " 00:00:00";
      }
      Policy_API.editPolicyDiscount(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
          })
          this.findPolicyDiscount();
          this.dialogFormVisibleE = false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
      })
    },
    add: function(){
      this.dialogFormVisibleA=true;
    },
    addOne(){
      this.loading=true;
      var startDate, endDate, businessDate;
      if(!!this.startDate){
        startDate =this.startDate + " 00:00:00";
      }
      if(!!this.endDate){
        endDate = this.endDate + " 00:00:00";
      }
      if(!!this.businessDate){
        businessDate = this.businessDate + " 00:00:00";
      }
      var params={
        customerId:this.customerId,
        customerName:this.customerName,
        policyName:this.policyName,
        businessDate: businessDate,
        method:this.method,
        price:this.price,
        amount:this.amount,
        // commitAmount:this.commitAmount,
        // balanceAmount:this.balanceAmount,
        startDate: startDate,
        endDate: endDate,
        remark:this.remark,
      };
      Policy_API.addPolicyDiscount(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功！':res.msg,
          })
          this.findPolicyDiscount();
          this.dialogFormVisibleA= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA= false;
      })
    },
    deleteOne: function(item){
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {ids: item.fdId};
          this.loading=true;
          Policy_API.deletePolicyDiscount(params).then(res=>{
            this.loading=false;
            this.$message({
                type:res.success?'success':'info',
                message:res.success?'删除成功！': '请求失败:' + res.msg,
            });
            this.findPolicyDiscount();
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
    convertApproveOne: function(){
      let params = {
          fdId: this.tableDataA[0].fdId,
          type:10,
        };
        Policy_API.approveStatus(params).then(res=>{
          this.$message({
                  type:res.success?'success':'info',
                  message:res.success?'反审核成功！': '请求失败:' + res.msg,
            });
            this.findPolicyDiscount();
          this.dialogFormVisibleE= false;
        }).catch(err=>{
          this.loading=false;
          this.$message({type:'error',message:err.msg})
          this.dialogFormVisibleE= false;
        })
    },
    convertCancelOne: function(){
       let params = {
        fdId: this.tableDataA[0].fdId,
        type:1,
      };
      Policy_API.approveStatus(params).then(res=>{
         this.$message({
                type:res.success?'success':'info',
                message:res.success?'反作废成功！': '请求失败:' + res.msg,
          });
          this.findPolicyDiscount();
          this.dialogFormVisibleE= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
      })
    },
    approveOne: function(){
      let params = {
          fdId: this.tableDataA[0].fdId,
          type:30,
        };
        Policy_API.approveStatus(params).then(res=>{
          this.$message({
                  type:res.success?'success':'info',
                  message:res.success?'审核成功！': '请求失败:' + res.msg,
            });
          this.findPolicyDiscount();
          this.dialogFormVisibleE= false;
        }).catch(err=>{
          this.loading=false;
          this.$message({type:'error',message:err.msg})
          this.dialogFormVisibleE= false;
        })
    },
    convertCancelOne: function(){
       let params = {
        fdId: this.tableDataA[0].fdId,
        type:0,
      };
      Policy_API.approveStatus(params).then(res=>{
         this.$message({
                type:res.success?'success':'info',
                message:res.success?'反作废成功！': '请求失败:' + res.msg,
          });
          this.findPolicyDiscount();
            this.dialogFormVisibleE= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
      })
    },
    cancelOne: function(){
      let params = {
        fdId: this.tableDataA[0].fdId,
        type:1,
      };
      Policy_API.approveStatus(params).then(res=>{
         this.$message({
                type:res.success?'success':'info',
                message:res.success?'作废成功！': '请求失败:' + res.msg,
          });
          this.findPolicyDiscount();
            this.dialogFormVisibleE= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
      })
    },
    initCustomers:function(){
      this.customers = [];
      let params = {};
      Policy_API.findCooperatingCustomers(params).then(res=>{
        this.customers=res.data;
        this.customers1=JSON.parse(JSON.stringify(res.data));
        this.customers1.unshift({fdId:'',custName:'全部'});
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg==null?err.message:err.msg});
        this.dialogFormVisibleA= false;
      })
    },
    initPolicyNames: function(){
      let params={
        typename:'PolicyName'
      };
      Policy_API.findDictData(params).then(res=>{
        this.policyNames=res.data;
        this.policyNames1=JSON.parse(JSON.stringify(res.data));
        this.policyNames1.unshift({itemNo:'',itemName:'全部'});
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg==null?err.message:err.msg});
        this.dialogFormVisibleA= false;
      })
    },
    initMethods: function(){
      let params={
        typename:'DiscountType'
      };
      Policy_API.findDictData(params).then(res=>{
        this.methods=res.data;
        this.methods1=JSON.parse(JSON.stringify(res.data));
        this.methods1.unshift({itemNo:'',itemName:'全部'});
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg==null?err.message:err.msg});
        this.dialogFormVisibleA= false;
      })
    },
  },
  created() {},
  watch: {
    $route: function() {
      this.findPolicyDiscount();
    }
  },
  mounted() {
    this.findPolicyDiscount();
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
// v-dialogDrag: 弹窗拖拽
Vue.directive('dialogDrag', {
    bind(el, binding, vnode, oldVnode) {
        const dialogHeaderEl = el.querySelector('.el-dialog__header');
        const dragDom = el.querySelector('.el-dialog');
        dialogHeaderEl.style.cursor = 'move';
 
        // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
        const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null);
        
        dialogHeaderEl.onmousedown = (e) => {
            // 鼠标按下，计算当前元素距离可视区的距离
            const disX = e.clientX - dialogHeaderEl.offsetLeft;
            const disY = e.clientY - dialogHeaderEl.offsetTop;
            
            // 获取到的值带px 正则匹配替换
            let styL, styT;
 
            // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
            if(sty.left.includes('%')) {
                styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100);
                styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100);
            }else {
                styL = +sty.left.replace(/\px/g, '');
                styT = +sty.top.replace(/\px/g, '');
            };
            
            document.onmousemove = function (e) {
                // 通过事件委托，计算移动的距离 
                const l = e.clientX - disX;
                const t = e.clientY - disY;
 
                // 移动当前元素  
                dragDom.style.left = `${l + styL}px`;
                dragDom.style.top = `${t + styT}px`;
 
                //将此时的位置传出去
                //binding.value({x:e.pageX,y:e.pageY})
            };
 
            document.onmouseup = function (e) {
                document.onmousemove = null;
                document.onmouseup = null;
            };
        }  
    }
});
</script>