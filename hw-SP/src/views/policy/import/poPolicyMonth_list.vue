<template>
  <div>
    <el-row>
      <el-col :span="24" >
        <el-col :span="12" class="text-left date-pickerwidth">
          <el-date-picker
            :readonly="true"
            v-model="executeDate"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['00:00:00', '23:59:59']">
          </el-date-picker>
        </el-col>
        <el-col :span="12" class="text-right">
          <div>
            <el-button v-if="executeStatus === 0" type="primary" @click="add()">新增</el-button>
            <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中" 
    element-loading-spanner="el-icon-loading" border :max-height="tableHeight">
        <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
        <el-table-column prop="customerName" header-align="center" label="客户名称" min-width="180"></el-table-column>
        <el-table-column prop="customerType" header-align="center" label="客户类型"  min-width="60"></el-table-column>
        <el-table-column prop="lastAmount" header-align="center" align="right" label="上月级别优惠金额" min-width="55"></el-table-column>
        <el-table-column prop="base" header-align="center" label="上月提货量" min-width="100">
            <el-table-column prop="lastWeightImportWhite" header-align="center" align="right" label="高速纸机白色" min-width="80"></el-table-column>
            <el-table-column prop="lastWeightImportColor" header-align="center" align="right" label="高速纸机本色" min-width="80"></el-table-column>
            <el-table-column prop="lastWeight3900White" header-align="center" align="right" label="3900纸机白色"  min-width="80"></el-table-column>
            <el-table-column prop="lastWeight3900Color" header-align="center" align="right" label="3900纸机本色"  min-width="80"></el-table-column>
            <el-table-column prop="lastWeight1575White" header-align="center" align="right" label="1575纸机白色"  min-width="80"></el-table-column>
        </el-table-column>
        <el-table-column prop="totalWeight" header-align="center" align="right" label="合计" min-width="60"></el-table-column>
        <el-table-column prop="amount" header-align="center" align="right" label="当月级别优惠金额" min-width="55"></el-table-column>
        <el-table-column prop="remark" header-align="center" label="备注" min-width="60"></el-table-column>
        <el-table-column v-if="executeStatus === 0" prop="" header-align="center" align="center" label="操作" width="52">
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
    <el-dialog :visible.sync="dialogFormVisibleA" title="新增" v-dialogDrag>
    <div class="dialog-pan" > 
        <div class="dialog-pan-title"><div>客户信息</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >客户名称</div></el-col>
              <el-col :span="13">
                  <div class="text-left">
                    <el-select v-model="customerName" @change="choseCustomer" placeholder="客户名称" >
                      <el-option v-for="item in customers" :key="item.fdId" :label="item.custName" :value="item.custName"></el-option>
                    </el-select> 
                  </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">客户类型</div></el-col>
              <el-col :span="13">
                <div >
                  <el-input v-model="customerType" readonly="readonly"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title"><div>优惠情况</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">优惠金额</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="amount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
            </el-col>
          </el-col>
        </el-row>
        <el-row class="textarea-bottom">
          <el-col :span="24">
            <el-col :span="5"><div class="grid-content">备注</div></el-col>
            <el-col :span="15">
              <div>
                <el-input  type="textarea" placeholder="备注" v-model="remark"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" >
        <el-button @click="dialogFormVisibleA = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleE" title="修改" v-dialogDrag>
    <div v-for="item in tableDataA" :key="item.fdId">
      <div class="dialog-pan" > 
        <div class="dialog-pan-title"><div>客户信息</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >客户名称</div></el-col>
              <el-col :span="13">
                  <div class="text-left">
                    <el-input disabled="disabled" v-model="item.customerName"></el-input>
                  </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">客户类型</div></el-col>
              <el-col :span="13">
                <div >
                  <el-input disabled="disabled" v-model="item.customerType"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title"><div>优惠情况</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">优惠金额</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.amount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
            </el-col>
          </el-col>
        </el-row>
        <el-row class="textarea-bottom">
          <el-col :span="24">
            <el-col :span="5"><div class="grid-content">备注</div></el-col>
            <el-col :span="15">
              <div>
                <el-input  type="textarea" placeholder="备注" v-model="item.remark"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-row>
      </div>
    </div>
      <div slot="footer" >
        <el-button @click="dialogFormVisibleE = false">取 消</el-button>
        <el-button type="primary" @click="editOne()">确 定</el-button>
      </div>     
    </el-dialog>
  </div>
</template>
<script>
import Policy_API from '@/api/import/policy'
import axios from 'axios';
import Vue from 'vue';
export default {
  name:"policyover-list",
  data(){
    return{
      loading:false,
      dialogFormVisibleA: false,
      dialogFormVisibleE: false,
      executeDate:[],
      tableData:[],
      tableDataA:[],
      fdMonth:'',
      fdYear:'',
      tableHeight: 300,
      customers:[],
      customerName:'',
      customerType:'',
      customerId:'',
      remark:'',
      amount:'',
      executeStatus: this.$route.meta.executeStatus,
    }
  },
  components:{},
  methods:{
    //初始化数据
    findPolicyMaintainMonthCustomer(){
       this.executeStatus= this.$route.meta.executeStatus;
      this.loading=true;
      var params={
        // fdYear: '2019',
        // fdMonth: '06'
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        status:this.executeStatus
      };
      Policy_API.findPolicyMaintainMonthCustomer(params).then(res=>{
        this.loading=false;
        this.tableData=res.data||[];
        console.log(this.tableData);
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    getCustomers: function(){
      let params = {fdYear: this.fdYear, fdMonth: this.fdMonth};
      Policy_API.findCustomersNotIn(params).then(res => {
        this.customers = res.data || [];
      });
    },
    choseCustomer: function(value){
      this.customers.forEach(item =>{
        if(item.custName==value){
          this.customerType=item.custTypeName;
          this.customerId = item.fdId;
        }
      });
    },
    goEdit: function (item){
      this.tableDataA=[item];
      this.dialogFormVisibleE=true;
    },
    editOne(){
     var params=this.tableDataA[0];
      Policy_API.addPolicyMaintainMonthCustomer(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
          })
          this.findPolicyMaintainMonthCustomer();
          this.dialogFormVisibleE = false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
      })
    },
    add(){
      this.dialogFormVisibleA=true;
      this.getCustomers();
    },
    addOne(){
      this.loading=true;
      var params={
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        customerId: this.customerId,
        customerType: this.customerType,
        customerName: this.customerName,
        amount: this.amount,
        remark: this.remark,

      };
      Policy_API.addPolicyMaintainMonthCustomer(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功！':res.msg,
          })
          this.findPolicyMaintainMonthCustomer();
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
          let params = {fdId: item.fdId};
          this.loading=true;
          Policy_API.deletePolicyMaintainMonthCustomer(params).then(res=>{
            this.loading=false;
            this.$message({
                type:res.success?'success':'info',
                message:res.success?'删除成功！': '请求失败:' + res.msg,
            });
            this.findPolicyMaintainMonthCustomer();
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
    formatDate(date){
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
    currentMonth:function(){
      let oneDayLong = 24*60*60*1000;
      let now = new Date();
      let year = now.getFullYear();
      console.log(now+","+year);
      let monthStartDate = new Date(year, now.getMonth(), 1);//当前月1号
      let nextMonthStartDate = new Date(year, now.getMonth()+1, 1);//下个月1号
      let days = (nextMonthStartDate.getTime() - monthStartDate.getTime())/oneDayLong;//计算当前月份的天数
      let monthEndDate = new Date(year, now.getMonth(), days);
      let monthRange = [monthStartDate,monthEndDate];
      console.log(monthRange);
      this.fdYear = year;
      this.fdMonth = now.getMonth()+1;
      return [this.formatDate(monthStartDate)+' 00:00:00',this.formatDate(monthEndDate)+' 23:59:59'];
    },
  },
  created() {},
  watch: {
    $route: function() {
      this.findPolicyMaintainMonthCustomer();
    }
  },
  mounted() {
    this.executeDate=this.currentMonth();
    this.findPolicyMaintainMonthCustomer();
    this.$nextTick(function () {
        this.tableHeight = window.innerHeight - 120;//表头距离浏览器顶部88+分页62+距离底部20=170+10(浮动系数)
        // 监听窗口大小变化
        let self = this;
        window.onresize = function() {
            self.tableHeight = window.innerHeight - 120;
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