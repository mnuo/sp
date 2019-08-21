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
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
    border :span-method="objectOneMethod">
        <el-table-column type="index" header-align="center" align="center" label="序号" width="40"></el-table-column>
        <el-table-column prop="platform" header-align="center" label="机型纸种划分">
            <el-table-column prop="platform" header-align="center" label="纸机" width="150">
              <template slot-scope="scope">
                {{(scope.row.platform).replace(new RegExp(",","g"),'、')}}
              </template>
            </el-table-column>
            <el-table-column prop="paperType" header-align="center" label="纸种" width="80">
              <template slot-scope="scope">
                {{(scope.row.paperType).replace(new RegExp(",","g"),'、')}}
              </template>
            </el-table-column>
            <el-table-column prop="className" header-align="center" label="品类" min-width="60">
              <template slot-scope="scope">
                {{(scope.row.className).replace(new RegExp(",","g"),'、')}}
              </template>
            </el-table-column>
        </el-table-column>
        <el-table-column prop="isPromise" header-align="center" align="center" label="是否约定量" width="50">
          <template slot-scope="scope">
              {{scope.row.isPromise?'是':'否'}}
          </template>
        </el-table-column>
        <el-table-column prop="base" header-align="center" label="基础超量奖(元/吨)" min-width="100">
            <el-table-column prop="baseCondition" header-align="center" label="享受条件" min-width="100"></el-table-column>
            <el-table-column prop="baseAmount" header-align="center" align="right" label="吨奖励" width="80"></el-table-column>
        </el-table-column>
        <el-table-column prop="over" header-align="center" label="约定超量奖(元/吨)">
            <el-table-column prop="overCondition" header-align="center" label="享受条件" min-width="100"></el-table-column>
            <el-table-column prop="overAmount" header-align="center" align="right" label="约吨奖励" width="80"></el-table-column>
        </el-table-column>
        <!-- <el-table-column prop="otherCondition" header-align="center" label="约定超量奖其他"></el-table-column>
        <el-table-column prop="otherAmount" header-align="center" label="约定超量奖其他(元/吨)"></el-table-column> -->
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
        <div class="dialog-pan-title"><div>机型纸种</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >纸机</div></el-col>
              <el-col :span="13">
                <div >
                  <el-select v-model="platform" @change="chosePlatform" placeholder="纸机" multiple collapse-tags value-key="value">
                    <el-option v-for="item in platforms" :key="item.value" :label="item.value" :value="item.value"></el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">纸种</div></el-col>
              <el-col :span="13">
                <div >
                  <el-select v-model="paperType" placeholder="纸种" multiple collapse-tags value-key="value">
                    <el-option v-for="item in paperTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >品类</div></el-col>
              <el-col :span="13">
                <div >
                  <el-select v-model="className" placeholder="品类" multiple collapse-tags value-key="value">
                    <el-option v-for="(value,key) in classNames" :key="key" :label="value" :value="value"></el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title"> <div>超量奖</div></div>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content">是否约定量</div></el-col>
            <el-col :span="13">
              <div>
                  <el-select v-model="isPromise" placeholder="是否约定量">
                    <el-option v-for="item in promises" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12" class="text-right">
          </el-col>
        </el-col>
      </el-row>
      <el-row class="textarea-bottom">
        <el-col :span="24">
          <el-col :span="5"><div class="grid-content">基础超量奖享受条件</div></el-col>
          <el-col :span="14">
            <div>
              <el-input  type="textarea" placeholder="基础超量奖享受条件" v-model="baseCondition"></el-input>
            </div>
          </el-col>
          <el-col :span="4">
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
           <el-col :span="12">
            <el-col :span="10"><div class="grid-content" >基础超量奖吨奖励</div></el-col>
            <el-col :span="13">
              <div >
                <el-input type="number" v-model="baseAmount"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row class="textarea-bottom">
        <el-col :span="24">
            <el-col :span="5"><div class="grid-content">约定超量享受条件</div></el-col>
            <el-col :span="15">
              <div>
                <el-input type="textarea" placeholder="约定超量奖享受条件" v-model="overCondition"></el-input>
              </div>
            </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content">约定超量奖吨奖励</div></el-col>
            <el-col :span="13">
              <div>
                <el-input type="number" v-model="overAmount"></el-input>
              </div>
            </el-col>
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
        <div class="dialog-pan-title"><div>机型纸种</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >纸机</div></el-col>
              <el-col :span="13">
                <div >
                  <el-select v-model="platformEdit" placeholder="纸机" multiple collapse-tags value-key="value">
                    <el-option v-for="item in platforms" :key="item.value" :label="item.value" :value="item.value"></el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">纸种</div></el-col>
              <el-col :span="13">
                <div >
                  <el-select v-model="paperTypeEdit" disabled="disabled" placeholder="纸种" multiple collapse-tags value-key="value">
                    <el-option v-for="item in paperTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >品类</div></el-col>
              <el-col :span="13">
                <div >
                  <el-select v-model="classNameEdit" disabled="disabled" placeholder="品类" multiple collapse-tags value-key="value">
                    <el-option v-for="(value,key) in classNames" :key="key" :label="value" :value="value"></el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title"> <div>超量奖</div></div>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">是否约定量</div></el-col>
              <el-col :span="13">
                <div>
                    <el-select v-model="item.isPromise" placeholder="是否约定量">
                      <el-option v-for="item in promises" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12" class="text-right">
            </el-col>
          </el-col>
        </el-row>
        <el-row class="textarea-bottom">
          <el-col :span="24">
            <el-col :span="5"><div class="grid-content">基础超量奖享受条件</div></el-col>
            <el-col :span="14">
              <div>
                <!-- <el-input v-model="baseCondition"></el-input> -->
                <el-input  type="textarea" placeholder="基础超量奖享受条件" v-model="item.baseCondition"></el-input>
              </div>
            </el-col>
            <el-col :span="4">
              <!-- <el-button type="primary" @click="editCondition()">新增</el-button> -->
            </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content" >基础超量奖吨奖励</div></el-col>
              <el-col :span="13">
                <div >
                  <el-input v-model="item.baseAmount"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row class="textarea-bottom">
          <el-col :span="24">
              <el-col :span="5"><div class="grid-content">约定超量享受条件</div></el-col>
              <el-col :span="15">
                <div>
                  <el-input  type="textarea" placeholder="约定超量奖享受条件" v-model="item.overCondition"></el-input>
                </div>
              </el-col>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10"><div class="grid-content">约定超量奖吨奖励</div></el-col>
              <el-col :span="13">
                <div>
                  <el-input v-model="item.overAmount"></el-input>
                </div>
              </el-col>
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
import Const_Util from '@/utils/consts'
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
      fdMonth:'',
      fdYear:'',
      dialogCalc:false,
      condition1:'',
      symbol1:'',
      condition2:'',
      tableData:[],
      tableDataA:[],
      platforms:[
        {
          value:'高速纸机',
          class:['卫卷纸','纸巾纸(软抽)','餐巾纸']
        },
        {
          value:'1575纸机',
          class:[
            '本色纸巾纸', '本色卫生纸', '餐巾纸原纸', '卫卷纸', '纸巾纸（软抽）', '手帕纸原纸', '卫生纸原纸', '纸巾纸原纸'
          ]
        },
        {
          value:'3900纸机',
          class:[
            '本色纸巾纸', '本色卫生纸', '餐巾纸原纸', '卫卷纸', '纸巾纸（软抽）', '手帕纸原纸', '卫生纸原纸', '纸巾纸原纸'
          ]
        },
      ],
      paperTypes:[
        {
          value:'本色',
          label:'本色',
        },
        {
          value:'白色',
          label:'白色',
        },
      ],
      classNames: {'卫卷纸':'卫卷纸','纸巾纸(软抽)':'纸巾纸(软抽)','餐巾纸':'餐巾纸'},
      promises:[
        {
          value:true,
          label:'是',
        },
        {
          value:false,
          label:'否',
        }
      ],
      //以下为添加参数
      platform:'',
      paperType:'',
      className:'',
      isPromise:'',
      baseCondition:'',
      baseAmount:'',
      overCondition:'',
      overAmount:'',
      platformEdit:[],
      paperTypeEdit:[],
      classNameEdit:[],
      //添加参数结束
      executeStatus: this.$route.meta.executeStatus,
    }
  },
  components:{},
  methods:{
    //初始化数据
    findPolicyMaintainOver(){
      this.executeStatus= this.$route.meta.executeStatus;
      this.loading=true;
      var params={
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        status:this.executeStatus
      };
      Policy_API.findPolicyMaintainOver(params).then(res=>{
        this.loading=false;
        this.tableData=res.data||[];
        console.log(this.tableData);
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    chosePlatform: function(){
        this.classNames ={};
        debugger;
        this.platform.forEach(item =>{
          this.platforms.forEach(ite => {
            if(item == ite.value){
              ite.class.forEach(v => {
                 if(!this.classNames[v] ){
                   this.classNames[v] = v;
                 }
              });
            }
          });
        });
        
    },
    add(){
      this.dialogFormVisibleA=true;
    },
    addOne(){
      this.loading=true;
      var platformAdd = this.platform.join(',');
      var paperTypeAdd = this.paperType.join(',');
      var classNameAdd = this.className.join(',');
      var params={
        platform:platformAdd,
        paperType: paperTypeAdd,
        className: classNameAdd,
        isPromise: this.isPromise,
        baseCondition: this.baseCondition,
        baseAmount: this.baseAmount,
        overCondition: this.overCondition,
        overAmount: this.overAmount,
        fdYear: this.fdYear,
        fdMonth: this.fdMonth,
        startDate: this.executeDate[0],
        endDate: this.executeDate[1],

      };
      Policy_API.addPolicyMaintainOver(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功！':res.msg,
          })
          this.findPolicyMaintainOver();
          this.dialogFormVisibleA= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA= false;
      })
    },
    goEdit: function(item){
        console.log(item);
        var _this=this;
        debugger;
        this.platformEdit = item.platform.split(',');
        this.paperTypeEdit = item.paperType.split(',');
        this.classNameEdit = item.className.split(',');
        _this.tableDataA=[item];
       _this.dialogFormVisibleE = true;
    },
    editOne: function(item){
      this.loading=true;
      this.tableDataA[0].platform = this.platformEdit.join(',');
      this.tableDataA[0].paperType = this.paperTypeEdit.join(',');
      this.tableDataA[0].className = this.classNameEdit.join(',');
      var params = this.tableDataA[0];
      Policy_API.addPolicyMaintainOver(params).then(res=>{
        this.loading=false;
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
          })
          this.findPolicyMaintainOver();
          this.dialogFormVisibleE= false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleE= false;
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
          Policy_API.deletePolicyMaintainOver(params).then(res=>{
            this.loading=false;
            this.$message({
                type:res.success?'success':'info',
                message:res.success?'删除成功！': '请求失败:' + res.msg,
            });
            this.findPolicyMaintainOver();
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
    //合并单元格
    setdates(arr){
      var spanOneArr = [], spanTwoArr = [], spanThreeArr=[],spanFiveArr=[],spanFourArr=[],
          concatOne = 0,  concatTwo = 0, concatThree=0, concatFive=0, concatFour=0;
      this.tableData.forEach((item,index)=>{
        if(index === 0){
          spanOneArr.push(1);  spanTwoArr.push(1); spanThreeArr.push(1);spanFiveArr.push(1);spanFourArr.push(1);
        }else{
          var parentCompare = false;
          if(item.platform === this.tableData[index - 1].platform){ //第2列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1; spanOneArr.push(0);
          }else{
            spanOneArr.push(1);  concatOne = index;
          };
          if(item.paperType === this.tableData[index - 1].paperType && item.platform === this.tableData[index - 1].platform){//第3列需合并相同内容的判断条件
            spanTwoArr[concatTwo] += 1; spanTwoArr.push(0);
          }else{
            spanTwoArr.push(1); concatTwo = index;
          };
          if(item.className === this.tableData[index - 1].className && item.paperType === this.tableData[index - 1].paperType && item.platform === this.tableData[index - 1].platform){ //第3列需合并相同内容的判断条件
            parentCompare = true;
            spanThreeArr[concatThree] += 1; spanThreeArr.push(0);
          }else{
            spanThreeArr.push(1); concatThree = index;
          };
          if(item.isPromise === this.tableData[index - 1].isPromise && parentCompare){ //第3列需合并相同内容的判断条件
            spanFourArr[concatFour] += 1; spanFourArr.push(0);
          }else{
            spanFourArr.push(1); concatFour = index;
          };
          if(item.baseCondition === this.tableData[index - 1].baseCondition && item.isPromise === this.tableData[index - 1].isPromise && parentCompare){ //第3列需合并相同内容的判断条件
            spanFiveArr[concatFive] += 1; spanFiveArr.push(0);
          }else{
            spanFiveArr.push(1); concatFive = index;
          };
        }
      });
      return  {
        one: spanOneArr,
        two: spanTwoArr,
        three: spanThreeArr,
        five: spanFiveArr,
        four: spanFourArr,
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
      if(columnIndex === 2) {
        const _row = (this.setdates(this.tableData).two)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 3) {
        const _row = (this.setdates(this.tableData).three)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      if(columnIndex === 4) {
        const _row = (this.setdates(this.tableData).four)[rowIndex];
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
        const _row = (this.setdates(this.tableData).five)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
  },
  created() {},
  watch: {
    $route: function() {
      this.findPolicyMaintainOver();
    }
  },
  mounted() {
    this.executeDate=this.currentMonth();
    this.findPolicyMaintainOver();
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