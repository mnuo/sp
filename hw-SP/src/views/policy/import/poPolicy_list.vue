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
            <el-button type="primary" @click="add()">新增</el-button>
            <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table 
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
     border style="width:100%" :span-method="objectOneMethod" :max-height="tableHeight">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="28"></el-table-column>
    <el-table-column prop="isImportWhile" :formatter="fomateplatform" header-align="center" label="机型纸种" min-width="60">
    </el-table-column>
    <el-table-column prop="level" header-align="center" label="级别" width="28"></el-table-column>
    <el-table-column prop="county" header-align="center" label="上月份经销商">
      <el-table-column prop="lastWeight" header-align="center" label="提货总量" min-width="72">
         <template slot-scope="scope">
          {{scope.row.lastMinWeight==null?'':scope.row.lastMinWeight + ((scope.row.lastMaxWeight==0||scope.row.lastMaxWeight==null)?'':'~') + ((scope.row.lastMaxWeight==0||scope.row.lastMaxWeight==null)?'以上':scope.row.lastMaxWeight)}}
        </template>
      </el-table-column>
      <el-table-column prop="lastAmount" header-align="center" align="right" label="级别优惠" min-width="36"></el-table-column>
      <el-table-column prop="lastBaseAmount" header-align="center" align="right" label="基础超量奖" min-width="47"></el-table-column>
      <el-table-column prop="lastBaseAvgAmount" header-align="center" align="right" label="基础超量平均奖" min-width="59"></el-table-column>
      <el-table-column prop="lastPromiseAmount" header-align="center" align="right" label="约定超量奖" min-width="47"></el-table-column>
    </el-table-column>
    <el-table-column prop="county" header-align="center" label="当月份经销商">
      <el-table-column prop="weight" header-align="center" label="提货总量" min-width="72">
        <template slot-scope="scope">
          {{scope.row.minWeight==null?'':scope.row.minWeight + ((scope.row.maxWeight==0 || scope.row.maxWeight==null)?'':'~') + ((scope.row.maxWeight==0 || scope.row.maxWeight==null)?'以上':scope.row.maxWeight)}}
        </template>
      </el-table-column>
      <el-table-column prop="amount" header-align="center" align="right" label="级别优惠" min-width="36"></el-table-column>
      <el-table-column prop="baseAmount" header-align="center" align="right" label="基础超量奖" min-width="47"></el-table-column>
      <el-table-column prop="baseAvgAmount" header-align="center" align="right" label="基础超量平均奖" min-width="59"></el-table-column>
      <el-table-column prop="promiseAmount" header-align="center" align="right" label="约定超量奖" min-width="47"></el-table-column>
    </el-table-column>
    <el-table-column prop="county" header-align="center" label="上月份加工厂">
      <el-table-column prop="lastFweight" header-align="center" label="提货总量" min-width="65">
        <template slot-scope="scope">
          {{scope.row.lastFminWeight==null?'':scope.row.lastFminWeight + ((scope.row.lastFmaxWeight==0||scope.row.lastFmaxWeight==null)?'':'~') + ((scope.row.lastFmaxWeight==0||scope.row.lastFmaxWeight==null)?'以上':scope.row.lastFmaxWeight)}}
        </template>
      </el-table-column>
      <el-table-column prop="lastFamount" header-align="center" align="right" label="级别优惠" min-width="36"></el-table-column>
      <el-table-column prop="lastFbaseAmount" header-align="center" align="right" label="基础超量奖" min-width="47"></el-table-column>
      <el-table-column prop="lastFbaseAvgAmount" header-align="center" align="right" label="基础超量平均奖" min-width="59"></el-table-column>
      <el-table-column prop="lastFpromiseAmount" header-align="center" align="right" label="约定超量奖" min-width="47"></el-table-column>
    </el-table-column>
    <el-table-column prop="county" header-align="center" label="当月份加工厂">
      <el-table-column prop="fweight" header-align="center" label="提货总量" min-width="65">
        <template slot-scope="scope">
          {{(scope.row.fminWeight==null?'':scope.row.fminWeight) + ((scope.row.fmaxWeight==0||scope.row.fmaxWeight==null)?'':'~') + ((scope.row.fmaxWeight==0||scope.row.fmaxWeight==null)?'以上':scope.row.fmaxWeight)}}
        </template>
      </el-table-column>
      <el-table-column prop="famount" header-align="center" align="right" label="级别优惠" min-width="36"></el-table-column>
      <el-table-column prop="fbaseAmount" header-align="center" align="right" label="基础超量奖" min-width="47"></el-table-column>
      <el-table-column prop="fbaseAvgAmount" header-align="center" align="right" label="基础超量平均奖" min-width="59"></el-table-column>
      <el-table-column prop="fpromiseAmount" header-align="center" align="right" label="约定超量奖" min-width="47"></el-table-column>
    </el-table-column>
    <el-table-column prop="" :cell-style="{padding:'0px'}" header-align="center" align="center" label="操作" width="52">
      <template slot-scope="scope">
        <el-tooltip content="修改" placement="top">
          <el-button class="execute-button" icon="el-icon-edit" @click="goEdit(scope.row)"></el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top">
            <el-button  class="execute-button" icon="el-icon-delete" @click="deleteOne(scope.row)"></el-button>
            </el-tooltip>
      </template>
    </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogFormVisibleA" title="新增" width="60%" top="10vh" v-dialogDrag>
      <div class="dialog-pan" > 
        <div class="dialog-pan-title"><div>头信息</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >机型纸种</div></el-col>
              <el-col :span="13">
                  <div class="text-left">
                    <el-select v-model="platform" @change="chosePlatform" placeholder="纸机" multiple collapse-tags value-key="value">
                      <el-option v-for="(value,key) in platforms" :key="key" :label="value" :value="key"></el-option>
                    </el-select> 
                  </div>
              </el-col>
            </el-col>
            <el-col :span="12"></el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">经销商基础超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="baseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
              <el-col :span="10"><div class="grid-content">经销商基础超量平均奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="baseAvgAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
         <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">经销商约定超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="promiseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">加工厂基础超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="fbaseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">加工厂基础超量平均奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="fbaseAvgAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12"  class="text-right">
              <el-col :span="10"><div class="grid-content">加工厂约定超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="fpromiseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <el-table :header-cell-style="{'padding':'0px',}" 
      :data="tableDataAdd" v-loading="loading" element-loading-text="加载中" element-loading-spanner="el-icon-loading"
      border style="width:100%" class="input-table">
      <template slot="empty">
        <p>请添加行</p>
      </template>
      <el-table-column prop="level" header-align="center" label="级别" width="60">
        <template slot-scope="scope">
          <el-input type="number" @keydown.native="proving" v-model="scope.row.level"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="county" header-align="center" label="当月份经销商">
        <el-table-column prop="weight" header-align="center" label="提货总量" min-width="120">
          <template slot-scope="scope">
          <div class="input-row">
            <el-input type="number" v-model="scope.row.minWeight" class="input-item"></el-input>
            <label>~</label>
            <el-input type="number" v-model="scope.row.maxWeight" class="input-item"></el-input>
          </div>
          </template>
        </el-table-column>
        <el-table-column prop="amount" header-align="center" label="级别优惠" min-width="60">
          <template slot-scope="scope">
            <el-input type="number" v-model="scope.row.amount" class="input-table"></el-input>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column prop="county" header-align="center" label="当月份加工厂">
        <el-table-column prop="fweight" header-align="center" label="提货总量" min-width="120">
          <template slot-scope="scope">
          <div class="input-row">
            <el-input type="number" v-model="scope.row.fminWeight" class="input-item"></el-input>
            <label>~</label>
            <el-input type="number" v-model="scope.row.fmaxWeight" class="input-item"></el-input>
          </div>
          </template>
        </el-table-column>
        <el-table-column prop="famount" header-align="center" label="级别优惠" min-width="60">
          <template slot-scope="scope">
            <el-input type="number" v-model="scope.row.famount" class="input-table"></el-input>
          </template>
        </el-table-column>
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
        <el-button @click="dialogFormVisibleA = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleE" title="修改" v-dialogDrag>
    <div v-for="item in tableDataA" :key="item.fdId">
      <div class="dialog-pan" > 
        <div class="dialog-pan-title"><div>机型纸种</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="5"><div class="grid-content" >机型纸种</div></el-col>
            <el-col :span="18">
                <div class="text-left">
                  <el-select disabled="disabled" v-model="item.platform" @change="chosePlatform" placeholder="纸机" multiple collapse-tags value-key="value">
                    <el-option v-for="(value,key) in platforms" :key="key" :label="value" :value="key"></el-option>
                  </el-select> 
                </div>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">级别</div></el-col>
              <el-col :span="13">
                <div >
                  <el-input type="number" v-model="item.level" disabled="disabled"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title"> <div>经销商</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">提货总量</div></el-col>
              <el-col :span="6">
                <div>
                  <el-input type="number" v-model="item.minWeight" ></el-input>
                </div>
              </el-col>
              <el-col :span="1"><div>_</div></el-col>
              <el-col :span="6">
                <div>
                  <el-input type="number" v-model="item.maxWeight"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
              <el-col :span="10"><div class="grid-content">优惠金额</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.amount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">基础超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.baseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
              <el-col :span="10"><div class="grid-content">基础超量平均奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.baseAvgAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">约定超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.promiseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title"> <div>加工厂</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">提货总量</div></el-col>
              <el-col :span="6">
                <div>
                  <el-input type="number" v-model="item.fminWeight"></el-input>
                </div>
              </el-col>
              <el-col :span="1"><div>_</div></el-col>
              <el-col :span="6">
                <div>
                  <el-input type="number" v-model="item.fmaxWeight"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
              <el-col :span="10"><div class="grid-content">优惠金额</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.famount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">基础超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.fbaseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
              <el-col :span="10"><div class="grid-content">基础超量平均奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.fbaseAvgAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">加工厂约定超量奖</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.fpromiseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
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
  name:"policylevel-list",
  data(){
    return{
      loading:false,
      tableData:[],
      tableDataA:[],
      dialogFormVisibleA: false,
      dialogFormVisibleE: false,
      executeDate:[],
      fdMonth:'',
      fdYear:'',
      platforms:{
        'isImportWhite':'高速纸机白色纸',
        'isImportColor':'高速纸机本色纸',
        'is3900White':'3900纸机白色纸',
        'is3900Color':'3900纸机本色纸',
        'is1575White':'1575纸机白色纸',
        'isAll':'所有纸机',
      },
      platform:'',
      level:'',
      minWeight:'',
      maxWeight:'',
      amount:'',
      fminWeight:'',
      fmaxWeight:'',
      famount:'',
      baseAmount:'',
      baseAvgAmount:'',
      promiseAmount:'',
      fbaseAmount:'',
      fbaseAvgAmount:'',
      fpromiseAmount:'',
      tableDataAdd:[],
      tableHeight: 300,
    }
  },
  components:{},
  methods:{
    //初始化数据
    findPolicyMaintain(){
      this.loading=true;
      var params = {
        fdYear: this.fdYear,
        fdMonth: this.fdMonth
      };
      Policy_API.findPolicyMaintain(params).then(res=>{
        this.tableData=res.data||[];
        console.log(this.tableData);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    fomateplatform:function(row, column, cellValue, index){
        let platform = '';
        if(row.isImportWhite){
            platform += '、' + this.platforms['isImportWhite'];
        }
        if(row.isImportColor){
            platform += '、' + this.platforms['isImportColor'];
        }
        if(row.is3900White){
            platform += '、' + this.platforms['is3900White'];
        }
        if(row.is3900Color){
            platform += '、' + this.platforms['is3900Color'];
        }
        if(row.is1575White){
            platform += '、' + this.platforms['is1575White'];
        }
        if(row.isAll){
            platform += '、' + this.platforms['isAll'];
        }
        if(platform.indexOf('、')==0){
          platform = platform.substring(1);
        }
        return platform;
    },
    editplatformvalue:function(row){
        let platform = [];
        if(row.isImportWhite){
            platform.push('isImportWhite');
        }
        if(row.isImportColor){
             platform.push('isImportColor');
        }
        if(row.is3900White){
            platform.push('is3900White');
        }
        if(row.is3900Color){
             platform.push('is3900Color');
        }
        if(row.is1575White){
            platform.push('is1575White');
        }
        if(row.isAll){
             platform.push('isAll');
        }
        return platform;
    },
    chosePlatform: function(){
      let length = this.platform.length;
      let index = this.platform.indexOf('isAll');
      if(length == 1){
        return;
      }
      if(index != -1){
        if(length > 1 && this.platform[length-1] =='isAll'){
          this.platform=['isAll'];
        }else{
          this.platform.splice('isAll',1);
        }

      }
    },
    add:function(){
      this.dialogFormVisibleA=true;
      //this.tableDataAdd=JSON.parse(JSON.stringify(this.tableData));
    },
    goEdit: function(item){
      console.log(item);
        var _this=this;
        item.platform=_this.editplatformvalue(item);
        _this.tableDataA=[item];
       console.log(_this.tableData);
       _this.dialogFormVisibleE = true;
    },
    addOne:function(){
      this.loading=true;
      var params = {
        startDate: this.executeDate[0],
        endDate: this.executeDate[1],
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        platform:this.platform.join(','),
        isImportWhite:false,
        isImportColor:false,
        is3900White:false,
        is3900Color:false,
        is1575White:false,
        isAll:false,
        baseAmount:this.baseAmount,
        baseAvgAmount:this.baseAvgAmount,
        promiseAmount:this.promiseAmount,
        fbaseAmount:this.fbaseAmount,
        fbaseAvgAmount:this.fbaseAvgAmount,
        fpromiseAmount:this.fpromiseAmount,
        details:[],
        // level:this.level,
        // minWeight:this.minWeight,
        // maxWeight:this.maxWeight,
        // amount:this.amount,
        // fminWeight:this.fminWeight,
        // fmaxWeight:this.fmaxWeight,
        // famount:this.famount,
      }
      this.platform.forEach(element => {
        params[element] = true;
      });
      params.details=this.tableDataAdd;
      Policy_API.addPolicyMaintain(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功！':res.msg,
          })
          this.findPolicyMaintain();
          this.tableDataAdd = [];
          this.dialogFormVisibleA= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:'请求异常:'+err.msg})
        console.log(err.errorCode);
        this.dialogFormVisibleA= false;
      });
    },
    deleteForAdd:function(index){
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log(index);
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
    addForAdd:function(){
      this.tableDataAdd.push({});
    },
    deleteOne: function(item){
      
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {detailId: item.detailId};
          this.loading=true;
          Policy_API.deletePolicyMaintain(params).then(res=>{
            this.loading=false;
            this.$message({
                type:res.success?'success':'info',
                message:res.success?'删除成功！': '请求失败:' + res.msg,
            });
            this.findPolicyMaintain();
          }).catch(err => {
            this.loading=false;
            this.$message({type:'error',message:'请求异常:'+err.msg})
            console.log(err.errorCode);
            this.dialogFormVisibleA= false;
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
      });
    },
    editOne:function(){
      this.loading=true;
      var params=this.tableDataA[0];
      Policy_API.editPolicyMaintain(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
          })
          this.findPolicyMaintain();
          this.dialogFormVisibleE= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:'请求异常:'+err.msg})
        console.log(err.errorCode);
        this.dialogFormVisibleE= false;
      });
    },
     //合并单元格
    setdates(arr){
      var spanOneArr = [], spanFiveArr = [], spanSixArr=[],spanSevenArr=[],
          concatOne = 0,  concatFive = 0, concatSix=0, concatSeven=0;
      this.tableData.forEach((item,index)=>{
        if(index === 0){
          spanOneArr.push(1);  spanFiveArr.push(1); spanSixArr.push(1);spanSevenArr.push(1);
        }else{
          var parentCompare = false;

          if(item.isImportWhite === this.tableData[index - 1].isImportWhite 
            && item.isImportColor === this.tableData[index - 1].isImportColor
            && item.is3900White === this.tableData[index - 1].is3900White
            && item.is3900Color === this.tableData[index - 1].is3900Color
            && item.is1575White === this.tableData[index - 1].is1575White
            && item.isAll === this.tableData[index - 1].isAll
          ){ //第2列需合并相同内容的判断条件
            parentCompare = true;
            spanOneArr[concatOne] += 1; spanOneArr.push(0);
          }else{
            spanOneArr.push(1);  concatOne = index;
          };
          // if(item.lastBaseAmount === this.tableData[index - 1].lastBaseAmount && parentCompare){//第3列需合并相同内容的判断条件
          //   spanFiveArr[concatFive] += 1; spanFiveArr.push(0);
          // }else{
          //   spanFiveArr.push(1); concatFive = index;
          // };
          // if(item.lastBaseAvgAmount === this.tableData[index - 1].lastBaseAvgAmount && parentCompare){ //第3列需合并相同内容的判断条件
          //   spanSixArr[concatSix] += 1; spanSixArr.push(0);
          // }else{
          //   spanSixArr.push(1); concatSix = index;
          // };
          // if(item.lastPromiseAmount === this.tableData[index - 1].lastPromiseAmount && parentCompare){ //第3列需合并相同内容的判断条件
          //   spanSevenArr[concatSeven] += 1; spanSevenArr.push(0);
          // }else{
          //   spanSevenArr.push(1); concatSeven = index;
          // };
        }
      });
      return  {
        one: spanOneArr,
        five: spanOneArr,
        six: spanOneArr,
        seven: spanOneArr,
        col10: spanOneArr,
        col11: spanOneArr,
        col12: spanOneArr,
        col15: spanOneArr,
        col16: spanOneArr,
        col17: spanOneArr,
        col20: spanOneArr,
        col21: spanOneArr,
        col22: spanOneArr,
      }
    },
    objectOneMethod({ row, column, rowIndex, columnIndex }) {
      if(columnIndex === 1) {
        const _row = (this.setdates(this.tableData).one)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 5) {
        const _row = (this.setdates(this.tableData).five)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 6) {
        const _row = (this.setdates(this.tableData).six)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 7) {
        const _row = (this.setdates(this.tableData).seven)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 10 || columnIndex === 15 || columnIndex === 20) {
        const _row = (this.setdates(this.tableData).col10)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 11 || columnIndex === 16 || columnIndex === 21) {
        const _row = (this.setdates(this.tableData).col11)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 12 || columnIndex === 17 || columnIndex === 22) {
        const _row = (this.setdates(this.tableData).col12)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
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
  },
  created() {},
  mounted() {
    this.executeDate=this.currentMonth();
    this.findPolicyMaintain();
     this.$nextTick(function () {
        this.tableHeight = window.innerHeight - 40;//表头距离浏览器顶部88+分页62+距离底部20=170+10(浮动系数)
        // 监听窗口大小变化
        let self = this;
        window.onresize = function() {
            self.tableHeight = window.innerHeight - 40;
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
