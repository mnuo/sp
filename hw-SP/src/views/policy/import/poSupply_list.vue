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
            <el-button v-if="executeStatus===0" type="primary" @click="add()">新增</el-button>
            <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中" show-summary
  :summary-method="getTotal" class="supply-table" element-loading-spanner="el-icon-loading" :span-method="objectOneMethod" border :max-height="tableHeight">
        <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
        <el-table-column prop="platform" header-align="center" label="机型" min-width="80"></el-table-column>
        <el-table-column prop="qty" header-align="center" align="center" label="总机台数量"  min-width="55"></el-table-column>
        <el-table-column prop="color" header-align="center" label="纸种" min-width="55"></el-table-column>
        <el-table-column prop="className" header-align="center" label="品类" min-width="80"></el-table-column>
        <el-table-column prop="weight" header-align="center" align="right" label="机台天标准产量(吨)" min-width="55"></el-table-column>
        <el-table-column prop="day" header-align="center" label="生产天数" >
            <el-table-column prop="singleDay" align="center" header-align="center" label="单机台" min-width="50"></el-table-column>
            <el-table-column prop="totalDay" align="center" header-align="center" label="总机台" min-width="50"></el-table-column>
        </el-table-column>
        <el-table-column prop="monthWeight" header-align="center" label="机台月标准产量" >
            <el-table-column prop="singleMonthWeight" align="right" header-align="center" label="单机台" min-width="50"></el-table-column>
            <el-table-column prop="totalMonthWeight" align="right" header-align="center" label="总机台" min-width="50"></el-table-column>
        </el-table-column>
        <el-table-column prop="basePlanDay" header-align="center" label="机台及生产天数计划">
            <el-table-column prop="planDay" header-align="center" label="计划" min-width="70"></el-table-column>
            <el-table-column prop="produceDay" header-align="center" align="center" label="折单机台生产天数" min-width="60"></el-table-column>
        </el-table-column>
        <el-table-column prop="monthPlanWeight" header-align="center" align="right" label="月计划产能(吨)" min-width="60"></el-table-column>
        <el-table-column prop="standardWeight" header-align="center" align="right" label="标准库存(吨)" min-width="60"></el-table-column>
        <el-table-column prop="base" header-align="center" label="月供货计划" >
            <el-table-column prop="lastWeight" header-align="center" align="right" label="上月结余库存(吨)" min-width="65"></el-table-column>
            <el-table-column prop="agentWeight" header-align="center" align="right" label="代加工及自用量(吨)" min-width="65"></el-table-column>
            <el-table-column prop="planSupplyWeight" header-align="center" align="right" label="计划供货量(吨)" min-width="60"></el-table-column>
            <el-table-column prop="total" header-align="center" align="right" label="小计" min-width="60"></el-table-column>
        </el-table-column>
        <el-table-column v-if="executeStatus===0" prop="" header-align="center" align="center" label="操作" width="52">
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
    <el-dialog :visible.sync="dialogFormVisibleA1" title="新增" width="60%" top="10vh" v-dialogDrag>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">机型</div>
              </el-col>
              <el-col :span="13">
                <div>
                </div>
                  <el-select v-model="platform"  @change="chosePlatform"  placeholder="生产机台">
                    <el-option 
                        v-for="item in allPlatform"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value">
                    </el-option>
                  </el-select> 
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">总机台数量</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" @keydown.native="proving" v-model="qty"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">机台天标准产量(吨)</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="weight"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">单机台生产天数</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number"  @keydown.native="proving" v-model="singleDay"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <el-table 
      :data="tableDataAdd" v-loading="loading" element-loading-text="加载中" element-loading-spanner="el-icon-loading"
      border style="width:100%" class="input-table">
      <template slot="empty">
        <p>请添加行</p>
      </template>
      <el-table-column prop="paperType" header-align="center" label="纸种" min-width="80">
        <template slot-scope="scope">
           <el-select v-model="scope.row.color"  placeholder="纸种">
              <el-option 
                v-for="item in allColor"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select> 
        </template>
      </el-table-column>
      <el-table-column prop="className" header-align="center" label="品类" min-width="110">
        <template slot-scope="scope">
          <el-select v-model="scope.row.className" @change="((val)=>{choseClassForTableAdd(val, scope.$index)})"   placeholder="品类">
            <el-option 
              v-for="item in allClass"
              :key="item.key"
              :label="item.value"
              :value="item.value">
            </el-option>
          </el-select> 
        </template>
      </el-table-column>
      <el-table-column prop="planDay" header-align="center" label="机台及生产天数计划" min-width="100">
        <template slot-scope="scope">
          <el-input v-model="scope.row.planDay" class="input-table"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="productDay" header-align="center" label="折单机台生产天数" min-width="100">
        <template slot-scope="scope">
          <el-input v-model="scope.row.produceDay" type="number"  @keydown.native="proving" class="input-item"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="agentWeight" header-align="center" label="代加工及自用量(吨)" min-width="100">
        <template slot-scope="scope">
          <el-input v-model="scope.row.agentWeight" type="number" class="input-table"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="" :cell-style="{padding:'0px'}" header-align="center" align="center" label="操作" width="50">
        <template slot-scope="scope">
          <el-tooltip content="删除" placement="top">
              <el-button  class="execute-button" icon="el-icon-delete" @click="deleteForAdd(scope.$index)"></el-button>
              </el-tooltip>
        </template>
      </el-table-column>
      </el-table>
      <div slot="footer" class="paddingtop20">
         <el-button @click="addForAdd()">添加行</el-button>
        <el-button @click="dialogFormVisibleA1 = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
    
    <el-dialog :visible.sync="dialogFormVisibleE" title="修改" v-dialogDrag>
      <div v-for="item in tableDataA" :key="item.detailId">
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">机型</div>
              </el-col>
              <el-col :span="13">
                <div>
                </div>
                  <el-select v-model="item.platform" :disabled="true"  @change="chosePlatform"  placeholder="生产机台">
                    <el-option 
                        v-for="item in allPlatform"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value">
                    </el-option>
                  </el-select> 
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">纸种</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="item.color" :disabled="true" placeholder="纸种">
                    <el-option 
                      v-for="item in allColor"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
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
                <div class="grid-content">品类</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="item.className" @change="choseClassItem" :disabled="true"  placeholder="品类">
                    <el-option 
                      v-for="item in allClass"
                      :key="item.key"
                      :label="item.value"
                      :value="item.value">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">总机台数量</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number"  @keydown.native="proving" v-model="item.qty"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">机台天标准产量(吨)</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.weight"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">单机台生产天数</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number"  @keydown.native="proving" v-model="item.singleDay"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>计划信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">机台及生产天数计划</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input v-model="item.planDay"></el-input>
                </div>
              </el-col>
              
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">折单机台生产天数</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number"  @keydown.native="proving" v-model="item.produceDay"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">代加工及自用量(吨)</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.agentWeight"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              
            </el-col>
          </el-col>
        </el-row>
      </div>
      </div>
      <div slot="footer">
        <el-button @click="dialogFormVisibleE = false">取 消</el-button>
        <el-button type="primary" @click="editOne()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Policy_API from '@/api/import/policy'
import Const_Util from '@/utils/consts'
import axios from 'axios';
import Vue from 'vue';
export default {
  name:"policysupply-list",
  data(){
    return{
      loading:false,
      dialogFormVisibleA: false,
      dialogFormVisibleA1: false,
      dialogFormVisibleE: false,
      executeDate:[],
      tableData:[],
      tableDataA:[],
      fdMonth:'',
      fdYear:'',
      tableHeight: 300,
      platform:'',
      allPlatform: Const_Util.allPlatform,
      allColor: Const_Util.allColor,
      allClass:[],
      className:'',
      classNo:'',
      color:'',
      qty:'',
      singleDay:'',
      totalDay:'',
      singleMonthWeight:'',
      totalMonthWeight:'',
      weight:'',
      planDay:'',
      produceDay:'',
      monthPlanWeight:'',
      standardWeight:'',
      lastWeight:'',
      agentWeight:'',
      planSupplyWeight:'',
      remark:'',
      tableDataAdd:[],
      executeStatus: this.$route.meta.executeStatus,
    }
  },
  components:{},
  methods:{
    getAllClass:function(platform){
      var allClass=[];
      this.allPlatform.forEach(item =>{
        if(item.value == platform){
            allClass=item.class;
        }
      });
      return allClass;
    },
    proving:function(e){
      var charcode = window.event ? e.keyCode : e.which;
      // var re = /\d/;
      //FF和safari3.1以前版本会对方向键、退格键、删除键触发keypress事件 charcode > 9 可修复此Bug
      //!e.ctrlKey 不屏蔽Ctrl+C,Ctrl+V 
      // if(!re.test(String.fromCharCode(charcode)) ){//&& charcode > 9 && !e.ctrlKey
      console.log(charcode);
      if(charcode==189||charcode==190||charcode==110||charcode==109){
          //阻止默认事件
          if(e.preventDefault){
              e.preventDefault();
          }else{
              e.returnValue = false;
          }                            
      }
    },
    chosePlatform:function (){      
        this.allClass =this.getAllClass(this.platform);
    },
    choseClassForTableAdd:function(current, index){
      this.allClass.forEach(item =>{
        if(item.value==current){
          this.tableDataAdd[index].classNo=item.key;
        }
      });
    },
    choseClassItem:function(current){
      this.allClass.forEach(item =>{
        if(item.value==current){
          this.tableDataA[0].classNo=item.key;
        }
      });
    },
    choseClass:function(item){
      this.allClass.forEach(item =>{
        if(item.value==this.className){
          this.classNo=item.key;
        }
      });
    },
    //初始化数据
    findPolicyMaintainSupply(){
      this.executeStatus=this.$route.meta.executeStatus;
      this.loading=true;
      var params={
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        status:this.executeStatus
      };
      Policy_API.findPolicyMaintainSupply(params).then(res=>{
        this.loading=false;
        this.tableData=res.data||[];
        console.log(this.tableData);
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    goEdit: function (item){
      this.tableDataA=[item];
      this.allClass=this.getAllClass(item.platform);
      this.dialogFormVisibleE=true;
    },
    editOne(){
     var params=this.tableDataA[0];
      Policy_API.editPolicyMaintainSupply(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
          })
          this.findPolicyMaintainSupply();
          this.dialogFormVisibleE = false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
      })
    },
    addForAdd:function(){
      this.tableDataAdd.push({});
    },
    deleteForAdd: function(index){
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.tableDataAdd.splice(index,1);
          this.$message({
              type:'success',
              message:'删除成功！',
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
    },
    add(){
      this.dialogFormVisibleA1=true;
    },
    addOne(){
      this.loading=true;
      var params={
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        platform:this.platform,
        qty:this.qty,
        singleDay:this.singleDay,
        weight:this.weight,
        // totalDay:this.totalDay,
        //singleMonthWeight:this.singleMonthWeight,
        // totalMonthWeight:this.totalMonthWeight,
        details:this.tableDataAdd,
        // standardWeight:this.standardWeight,

        // color:this.color,
        // agentWeight:this.agentWeight,
        // planDay:this.planDay,
        // produceDay:this.produceDay,
        // monthPlanWeight:this.monthPlanWeight,
        
        // lastWeight:this.lastWeight,
        // classNo:this.classNo,
        // className:this.className,
        // planSupplyWeight:this.planSupplyWeight,
        // remark:this.remark,

      };
      // params.details=this.tableDataAdd;
      Policy_API.addPolicyMaintainSupply(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功！':res.msg,
          })
          this.findPolicyMaintainSupply();
          this.dialogFormVisibleA1= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA1= false;
      })
    },
    deleteOne: function(item){
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {detailId: item.detailId};
          this.loading=true;
          Policy_API.deleteSupplyByDetailId(params).then(res=>{
            this.loading=false;
            this.$message({
                type:res.success?'success':'info',
                message:res.success?'删除成功！': '请求失败:' + res.msg,
            });
            this.findPolicyMaintainSupply();
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
    singleDayBlur: function(){
      //单机台生产天数*总机台数量=总机台生产天数
      this.totalDay = this.qty*this.singleDay;
      //机台天标准产量(吨)*单机台生产天数=单机台月标准产量
      this.singleMonthWeight = this.singleDay*this.weight;
      //机台天标准产量(吨)*总机台生产天数=总机台月标准产量
      this.totalMonthWeight = this.totalDay*this.weight;
    },
    productDayBlur:function(){
      //月计划产能=机台天标准产量*折单机台生产天数
      this.monthPlanWeight = this.weight*this.productDay;
      //标准库存:this.
      this.standardWeight = this.totalMonthWeight*this.produceDay/this.totalDay*0.5;
      //月计划标准产能+上月结余-标准库存-代加工及自用量
      this.planSupplyWeight = this.monthPlanWeight+lastWeight-standardWeight-agentWeight;
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
      // this.fdYear=2019;
      // this.fdMonth=5;
      return [this.formatDate(monthStartDate)+' 00:00:00',this.formatDate(monthEndDate)+' 23:59:59'];
    },
     //合并单元格
    setdates(arr){
      var spanOneArr = [], spanThreeArr = [], 
          concatOne = 0,  concatThree = 0;
      this.tableData.forEach((item,index)=>{
        if(index === 0){
          spanOneArr.push(1);  spanThreeArr.push(1); 
        }else{
          var parentCompare = false;
          if(item.platform === this.tableData[index - 1].platform){ //第1列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1; spanOneArr.push(0);
          }else{
            spanOneArr.push(1);  concatOne = index;
          };
          if(item.color === this.tableData[index - 1].color && item.platform === this.tableData[index - 1].platform){//第3列需合并相同内容的判断条件
            spanThreeArr[concatThree] += 1; spanThreeArr.push(0);
          }else{
            spanThreeArr.push(1); concatThree = index;
          };
        }
      });
      return  {
        one: spanOneArr,
        three: spanThreeArr,
      }
    },
    objectOneMethod({ row, column, rowIndex, columnIndex }) {
      if(columnIndex === 1 || columnIndex === 2 || columnIndex === 5 || columnIndex === 6 || columnIndex === 7 || columnIndex === 8 || columnIndex === 9) {
        const _row = (this.setdates(this.tableData).one)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 3 || columnIndex === 17) {
        const _row = (this.setdates(this.tableData).three)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
    getTotal(param) {
      const { columns, data } = param;
      const sums = [];
      
      columns.forEach((column, index) => {
        switch (index) {
          case 1:
            sums[index] = '合计';
            break;
          case 2:
            let qtytotal = 0;
            this.tableData.forEach((item, i) =>{
              if(i === 0){
                  qtytotal=item.qty;
              }else if(item.platform != this.tableData[i-1].platform){
                  qtytotal += item.qty;
              }
            });
            if(qtytotal == 0) qtytotal='';
            sums[index] = qtytotal;
            break;
          case 12:
            let monthPlanWeightTotal = 0;
            this.tableData.forEach((item, i) =>{
              let monthPlanWeight = Number(item.monthPlanWeight);
                if (!isNaN(monthPlanWeight)) {
                  monthPlanWeightTotal += monthPlanWeight;
                }
            });
            if(monthPlanWeightTotal == 0) monthPlanWeightTotal='';
            sums[index] = Math.round(monthPlanWeightTotal*100)/100;
            break;
          case 13:
            let standardWeightTotal = 0;
            this.tableData.forEach((item, i) =>{
              let standardWeight = Number(item.standardWeight);
                if (!isNaN(standardWeight)) {
                  standardWeightTotal += standardWeight;
                }
            });
            if(standardWeightTotal == 0) standardWeightTotal='';
            sums[index] = Math.round(standardWeightTotal*100)/100;
            break;
          case 14:
            let lastWeightTotal = 0;
            this.tableData.forEach((item, i) =>{
              let lastWeight = Number(item.lastWeight);
                if (!isNaN(lastWeight)) {
                  lastWeightTotal += lastWeight;
                }
            });
            if(lastWeightTotal == 0) lastWeightTotal='';
            sums[index] = Math.round(lastWeightTotal*100)/100;
            break;
          case 15:
            let agentWeightTotal = 0;
            this.tableData.forEach((item, i) =>{
              let agentWeight = Number(item.agentWeight);
                if (!isNaN(agentWeight)) {
                  agentWeightTotal += agentWeight;
                }
            });
            if(agentWeightTotal == 0) agentWeightTotal='';
            sums[index] = Math.round(agentWeightTotal*100)/100;
            break;
          case 16:
            let planSupplyWeightTotal = 0;
            this.tableData.forEach((item, i) =>{
              let planSupplyWeight = Number(item.planSupplyWeight);
                if (!isNaN(planSupplyWeight)) {
                  planSupplyWeightTotal += planSupplyWeight;
                }
            });
            if(planSupplyWeightTotal == 0) planSupplyWeightTotal='';
            sums[index] = Math.round(planSupplyWeightTotal*100)/100;
            break;
          case 17:
            sums[index] = sums[index-1];
            break;
          default: sums[index] = ' ';
            break;
        }
      });

      return sums;
    }
  },
  created() {},
  watch: {
    $route: function() {
      this.findPolicyMaintainSupply();
    }
  },
  mounted() {
    this.executeDate=this.currentMonth();
    this.findPolicyMaintainSupply();
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