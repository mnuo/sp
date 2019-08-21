<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>客户资料</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20">
        <el-col :span="1">省</el-col>
        <el-col :span="2" >
          <el-select v-model="sheng" @change="choseProvince" placeholder="省级">
            <el-option value="">全部</el-option>
            <el-option 
                v-for="item in province"
                :key="item.id"
                :label="item.value"
                :value="item.value"></el-option>
          </el-select>    
        </el-col>
        <el-col :span="1">市</el-col>
        <el-col :span="2" >
          <el-select v-model="shi"  @change="choseCity" placeholder="市级">
            <el-option value="">全部</el-option>
            <el-option  v-for="item in shi1"
                :key="item.id"
                :label="item.value"
                :value="item.value"></el-option>
          </el-select>    
        </el-col>
        <el-col :span="2">区/县</el-col>
        <el-col :span="2" >
          <el-select v-model="qu" @change="choseBlock" placeholder="县级">
            <el-option value="">全部</el-option>
            <el-option v-for="item in qu1"
                :key="item.id"
                :label="item.value"
                :value="item.value">
            </el-option>
          </el-select>    
        </el-col>
        <el-col :span="2">客户类型11</el-col>
        <el-col :span="3" >
          <el-select v-model="custTypeName" @change="choseCustType" placeholder="客户类型">
            <el-option value="">全部</el-option>
            <el-option value="经销商">经销商</el-option>
            <el-option value="后加工厂">后加工厂</el-option>
          </el-select>    
        </el-col>
        <el-col :span="2">客户名称</el-col>
        <el-col :span="4">
          <el-input  v-model="custName" v-on:keyup.13="findByPage"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button type="primary" class="btn btn-default" style="height:28px;" @click="findByPage()">查询</el-button>
        </el-col>
      </el-col>
      <el-col :span="4" class="text-right">
        <div>
          <el-button type="info" @click="$router.go(-1)">返回上一级</el-button>
        </div>
      </el-col>
    </el-row>
    <el-table
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
    <el-table-column prop="province" header-align="center" label="省" min-width="100"></el-table-column>
    <el-table-column prop="city" header-align="center" label="市" min-width="100"></el-table-column>
    <el-table-column prop="county" header-align="center" label="县" min-width="100"></el-table-column>
    <el-table-column prop="custName" header-align="center" label="客户名称" min-width="330"></el-table-column>
    <el-table-column prop="shortName" header-align="center" label="客户简称" min-width="150"></el-table-column>
    <el-table-column prop="custTypeName" header-align="center" label="客户类别" min-width="150"></el-table-column>
    <el-table-column prop="statusText" header-align="center" label="客户状态" min-width="150"></el-table-column>
    <el-table-column prop="" header-align="center" align="center" label="操作" width="50">
      <template slot-scope="scope">
        <el-tooltip content="查看" placement="top">
          <el-button icon="el-icon-search" @click="goToCustomerDetail(scope.row)"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
    </el-table>
  </div>
</template>
<script>
import Base_API from '@/api/import/base'
import axios from 'axios';
export default {
  name:"price-list",
  data(){
    return{
      loading:false,
      tableData:[],
      mapJson:'../static/json/map.json',
      province:'',
      sheng: '',
      shi: '',
      shi1: [],
      qu: '',
      qu1: [],
      city:'',
      block:'',
      custTypeName:'',
      custName:'',
      onedate:[],
    }
  },
  components:{},
  methods:{
    renderheader(h,{column,$index}){
      return h('span',{},[
        h('span',{},column.label.split('</br>')[0]),
        h('br'),
        h('span',{},column.label.split('</br>')[1])
      ])
    },
    //初始化数据
    findAllCustomer(){
      this.loading=true;
      Base_API.findAllCustomer().then(res=>{
        this.tableData=res.data||[];
        console.log(this.tableData);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    //查看
    goToCustomerDetail(item){
      console.log(item)
    },
    //按条件查询
    findByPage(){
      var params={
        province:this.sheng,
        city:this.shi,
        county:this.qu,
        custTypeName:this.custTypeName,
        custName:this.custName,
      }
      // console.log(params)
      Base_API.findByPage(params).then(res=>{
        this.tableData=res.data||[];
        console.log(this.tableData);
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
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
        this.findByPage();
      },

      // 选市
      choseCity() {
        this.qu="";
        
        for (var index3 in this.city) {
          if (this.shi === this.city[index3].value) {
            this.qu1 = this.city[index3].children
          }
        }
        this.findByPage();
      },

      // 选区
      choseBlock(e) {
        for(var index4 in this.city){

        }
        this.findByPage();
      },
      choseCustType(e){
        this.findByPage();
      }
  },
  created() {},
  mounted() {
    this.findAllCustomer();
    this.getCityData()
  }
}
</script>