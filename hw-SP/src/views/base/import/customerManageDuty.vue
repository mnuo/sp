<template>
  <div id="customermanagerDuty">
    <!-- <el-row>
      <el-col :span="24">
        <span><b>（三）客户分管责任人维护表</b></span>
      </el-col>
    </el-row> -->
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary">保存</el-button>
        <el-button type="info">返回上一级</el-button>
        <el-button type="primary" @click="add()">新增</el-button>
      </el-col>
    </el-row>
    <el-table border :data="tableData"  height="710" >
      <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
      <el-table-column header-align="center" label="客户所在地" width="160">
        <el-table-column prop="province" header-align="center" label="省份" width="80"></el-table-column>
        <el-table-column prop="city" header-align="center" label="城市" width="80"></el-table-column>
      </el-table-column>
      <el-table-column prop="customerType" header-align="center" label="客户类型" width="90"></el-table-column>
      <el-table-column prop="customerName" header-align="center" label="客户名称" min-width="150"></el-table-column>
      <el-table-column prop="saleManager" header-align="center" label="区域经理" min-width="60"></el-table-column>
      <el-table-column prop="runningMan" header-align="center" label="运营专员" min-width="60"></el-table-column>
      <el-table-column prop="billOperator" header-align="center" label="制单员" min-width="60"></el-table-column>
      <el-table-column prop="operator" header-align="center" label="录入人" min-width="60"></el-table-column>
      <el-table-column prop="" header-align="center" label="录入日期" min-width="60"></el-table-column>
       <el-table-column prop="" header-align="center" label="操作" width="55">
        <template slot-scope="scope">
          <el-button  @click="adjust(scope.row)" style="color:#29d" icon="el-icon-edit"></el-button>
        </template>
       </el-table-column>
    </el-table>
    <pagination @pagination="pagination" :total="pageTotal" :limit="pageData.pageSize"></pagination>
    <el-dialog :visible.sync="dialogAdd" title="新增" style="width:1400px" v-dialogDrag center>
      <el-row class="row-top">
        <el-col :span="2" style="text-align:right;padding: 5px 10px 0px 0px;">省</el-col>
          <el-col :span="6" >
            <el-select v-model="sheng" @change="choseProvince" placeholder="省级">
              <el-option value="">全部</el-option>
              <el-option 
                  v-for="item in province"
                  :key="item.id"
                  :label="item.value"
                  :value="item.value"></el-option>
            </el-select>    
          </el-col>
          <el-col :span="2" style="text-align:right;padding: 5px 10px 0px 0px;">市</el-col>
          <el-col :span="6" >
            <el-select v-model="shi"  @change="choseCity" placeholder="市级">
              <el-option value="">全部</el-option>
              <el-option  v-for="item in shi1"
                  :key="item.id"
                  :label="item.value"
                  :value="item.value"></el-option>
            </el-select>    
          </el-col>
          <el-col :span="2" style="text-align:right;padding: 5px 10px 0px 0px;">区/县</el-col>
          <el-col :span="6" >
            <el-select v-model="qu" @change="choseBlock" placeholder="县级">
              <el-option value="">全部</el-option>
              <el-option v-for="item in qu1"
                  :key="item.id"
                  :label="item.value"
                  :value="item.value">
              </el-option>
            </el-select>    
          </el-col>
          
          </el-row>
          <el-row class="row-top">
             <el-col :span="4" style="text-align:right;padding: 5px 10px 0px 0px;">客户类型：</el-col>
      
            <el-col :span="16" >
            <el-select v-model="custTypeName" @change="choseCustType" placeholder="客户类型">
              <el-option value="">全部</el-option>
              <el-option value="经销商">经销商</el-option>
              <el-option value="后加工厂">后加工厂</el-option>
            </el-select>    
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="4" style="text-align:right;padding: 5px 10px 0px 0px;">客户名称：</el-col>
          <el-col :span="14">
            <template>
              <el-input v-model="customerName"></el-input>
            </template>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="4" style="text-align:right;padding: 5px 10px 0px 0px;">区域经理：</el-col>
          <el-col :span="14">
            <template>
              <el-input v-model="saleManager"></el-input>
            </template>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="4" style="text-align:right;padding: 5px 10px 0px 0px;">运营专员：</el-col>
          <el-col :span="14">
            <template>
              <el-input v-model="runningMan"></el-input>
            </template>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="4" style="text-align:right;padding: 5px 10px 0px 0px;">制&#8197;单&#8197;员&#8197;：</el-col>
          <el-col :span="14">
            <template>
              <el-input v-model="billOperator"></el-input>
            </template>
          </el-col>
        </el-row>
        <span slot="footer">
          <el-button type="primary" @click="doAdd()">确定</el-button>
        </span>
      </el-dialog>
    <el-dialog v-for="item in tableDataA" :key="item.fdId" :visible.sync="dialogEdit" :title="custName" v-dialogDrag width="27%" center>
      <el-row class="row-top">
        <el-col :span="8">区域经理：</el-col>
        <el-col :span="16">
          <template>
            <el-input v-model="item.saleManager"></el-input>
          </template>
        </el-col>
      </el-row>
      <el-row class="row-top">
        <el-col :span="8">运营专员：</el-col>
        <el-col :span="16">
          <template>
            <el-input v-model="item.runningMan"></el-input>
          </template>
        </el-col>
      </el-row>
      <el-row class="row-top">
        <el-col :span="8">制&#8197;单&#8197;员&#8197;：</el-col>
        <el-col :span="16">
          <template>
            <el-input v-model="item.billOperator"></el-input>
          </template>
        </el-col>
      </el-row>
      <span slot="footer">
        <el-button type="primary" @click="doSave()">确定</el-button>
      </span>
    </el-dialog>
    
    <!-- <div style="margin-top:20px">
      <textarea style="width:100%;height:100px;background-color:#c8dec2">功能说明：
        1.客户信息读取“销售基础信息平台”正在合作的客户
        2.区域经理、运营专员、制单员：由录入人选择录入
        3.排序：先省份，后城市
      </textarea>
    </div> -->
  </div>
</template>
<script>
import Base_API from '@/api/import/base';
import axios from 'axios';
import Pagination from '@/components/Pagination'
import {directive} from '@/views/base/import/productBase'
export default {
  name:'',
  data(){
    return{
       contentStyleObj:{
　　　　　　height:'720'
　　　　},
      tableData:[],
      loading:false,
      dialogEdit:false,
      dialogAdd:false,
      custName:'',
      tableDataA:[],
      pageTotal:50,
      pageData:{
        page:1,
        pageSize: 50
      },
      province:'',
      city:'',
      customerType:'',
      customerName:'',
      saleManager:'',
      runningMan:'',
      billOperator:'',
      operator:'',
      mapJson:'../static/json/map.json',
      sheng: '',
      shi: '',
      shi1: [],
      qu: '',
      qu1: [],
      city:'',
      
      custTypeName:''
    }
  },
  components:{
    Pagination
  },
  methods:{
    pagination(val){
     // debugger;
      this.pageData.pageSize = val.limit;
      this.pageData.page=val.page;
      this.findAllCustomerManager();
    },
    initPage(){
      this.findAllCustomerManager();
    },
    findAllCustomerManager(){
      var params=this.pageData;
      Base_API.findAllCustomerManager(params).then(res=>{
        this.tableData=res.data.list||[];
        debugger;
        this.pageTotal=parseInt(res.data.rowCount);
        this.pageData.pageSize=parseInt(res.data.pageSize);
        console.log(this.tableData)
      })
    },
    add(){
       this.dialogAdd=true;
    },
    doAdd(){
      var params = {
        province:this.sheng,
        city:this.shi,
        qu:this.qu,
        customerType:this.custTypeName,
        customerName:this.customerName,
        saleManager:this.saleManager,
        runningMan:this.runningMan,
        billOperator:this.billOperator
      };
      Base_API.addOneCustomerManager(params).then(res=>{
        this.$message({
          type:res.success?'res.success':'res.info',
          message:res.success?'录入成功':res.msg,
        });
        this.dialogEdit= false;
      }).catch(err=>{
        this.$message({type:'error',message:err.msg})
      })
    },
    adjust(item){
      this.custName=item.customerName;
      this.dialogEdit=true;
       debugger;
      this.tableDataA=[item];
    },
    doSave(){
      var params=this.tableDataA[0];
      Base_API.editOneCustomerManager(params).then(res=>{
        this.$message({
          type:res.success?'res.success':'res.info',
          message:res.success?'录入成功':res.msg,
        });
        this.dialogEdit= false;
      }).catch(err=>{
        this.$message({type:'error',message:err.msg})
      })
    },
    //=========================================
    // 加载china地点数据，三级
    getCityData:function(){
      var that = this
      axios.get(this.mapJson).then(function(response){
        if (response.status==200) {
          var data = response.data;
          
          that.province = []
          that.city = []
          that.block = []
          // 省市区数据分类
          for (var item in data) {
            
            if (item.match(/0000$/)) {//省
              that.province.push({id: item, value: data[item], children: []});
              
            } else if (item.match(/00$/)) {//市
              that.city.push({id: item, value: data[item], children: []})
            } else {//区
              that.block.push({id: item, value: data[item]})
            }
          }
          // console.log(that.city)
          // 分类市级
          for (var index in that.province) {
            for (var index1 in that.city) {
              if (that.province[index].id.slice(0, 2) === that.city[index1].id.slice(0, 2)) {
                that.province[index].children.push(that.city[index1])
              }
            }
          }
          // 分类区级
          for(var item1 in that.city) {
            for(var item2 in that.block) {
              if (that.block[item2].id.slice(0, 4) === that.city[item1].id.slice(0, 4)) {
                that.city[item1].children.push(that.block[item2])
              }
            }
          }
        }
        else{
          console.log(response.status)
        }
      }).catch(function(error){console.log(typeof+ error)})
      },
// 选省
      choseProvince() {
        this.shi ="";
        this.qu ="";       
        for (var index2 in this.province) {
          
          if (this.sheng === this.province[index2].value) {
            // console.log("=====")
            this.shi1 = this.province[index2].children
          }
        }
       // this.findByPage();
      },

      // 选市
      choseCity() {
        this.qu="";
        
        for (var index3 in this.city) {
          if (this.shi === this.city[index3].value) {
            this.qu1 = this.city[index3].children
          }
        }
       // this.findByPage();
      },

      // 选区
      choseBlock(e) {
        for(var index4 in this.city){

        }
       // this.findByPage();
      },
      choseCustType(){

      }
      // getHeight(){
      //   this.contentStyleObj.height=(window.innerHeight-200)+'px';
      //   this.contentStyleObj.overflow='auto';
      // }
    },
    
    mounted(){
      this.initPage();
      this.getCityData();
      // this.getHeight();
    }
}
</script>
