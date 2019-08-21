<template>
  <div>
    <el-row class="search-row">
      <el-col :span="20">
        <el-col :span="1" class="text-right">省</el-col>
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
        <el-col :span="1" class="text-right">市</el-col>
        <el-col :span="2" >
          <el-select v-model="shi"  @change="choseCity" placeholder="市级">
            <el-option value="">全部</el-option>
            <el-option  v-for="item in shi1"
                :key="item.id"
                :label="item.value"
                :value="item.value"></el-option>
          </el-select>    
        </el-col>
        <el-col :span="1" class="text-right">区/县</el-col>
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
        <el-col :span="2" class="text-right">客户类型</el-col>
        <el-col :span="3" >
          <el-select v-model="custTypeName" @change="choseCustType" placeholder="客户类型">
            <el-option value="">全部</el-option>
            <el-option value="经销商">经销商</el-option>
            <el-option value="后加工厂">后加工厂</el-option>
          </el-select>    
        </el-col>
        <el-col :span="2" class="text-right">客户名称</el-col>
        <el-col :span="4">
          <el-input  v-model="custName" v-on:keyup.13="findByPage"></el-input>
        </el-col>
        <el-col :span="1" class="search-button-col">
          <el-button type="primary" class="btn btn-default" @click="findByPage()">查询</el-button>
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
    style="width:100%" :max-height="tableHeight">
    
    <el-table-column type="index" header-align="center" align="center" label="序号" width="45"></el-table-column>
    <el-table-column prop="custName" header-align="center" label="客户名称" min-width="150"></el-table-column>
    <el-table-column prop="shortName" header-align="center" label="客户简称" min-width="100"></el-table-column>
    <el-table-column prop="custTypeName" header-align="center" label="客户类别" width="90"></el-table-column>
    <el-table-column prop="enterpriseType" header-align="center" label="企业性质" min-width="60"></el-table-column>
    <el-table-column prop="lawReposibility" header-align="center" label="负责人" min-width="60"></el-table-column>
    <el-table-column prop="mobile" header-align="center" label="手机" min-width="80"></el-table-column>
    <el-table-column prop="tel" header-align="center" label="电话" min-width="80"></el-table-column>
    <el-table-column prop="fax" header-align="center" label="传真" min-width="80"></el-table-column>
    <el-table-column prop="status" header-align="center" label="状态" min-width="50"></el-table-column>

    <el-table-column prop="" header-align="center" align="center" label="操作" width="50">
      <template slot-scope="scope">
        <el-tooltip content="查看" placement="top">
          <el-button icon="el-icon-search" @click="goToCustomerDetail(scope.row)"></el-button>
        </el-tooltip>
      </template>
    </el-table-column>
    </el-table>
    <pagination @pagination="pagination" :total="pageTotal" :limit="pageData.pageSize"></pagination>
  </div>
</template>
<script>
import Base_API from '@/api/import/base'
import axios from 'axios';
import Pagination from '@/components/Pagination'
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
      pageTotal:50,
      pageData:{
        page:1,
        pageSize: 50
      },
      tableHeight: 300,
    }
  },
  components:{Pagination},
  methods:{
    pagination(val){
     // debugger;
      this.pageData.pageSize = val.limit;
      this.pageData.page=val.page;
      this.findCustomerInfoPage();
    },
    renderheader(h,{column,$index}){
      return h('span',{},[
        h('span',{},column.label.split('</br>')[0]),
        h('br'),
        h('span',{},column.label.split('</br>')[1])
      ])
    },
    //初始化数据
    findCustomerInfoPage(){
      this.loading=true;
      var params=this.pageData;
      var params={
        province:this.sheng,
        city:this.shi,
        county:this.qu,
        custTypeName:this.custTypeName,
        custName:this.custName,
        page:this.pageData.page,
        pageSize:this.pageData.pageSize
      }
      Base_API.findCustomerInfoPage(params).then(res=>{
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
    //查看
    goToCustomerDetail(item){
      console.log(item)
    },
    //按条件查询
    findByPage(){
      this.pageData.page=1;
      this.pageData.pageSize=50;
      this.findCustomerInfoPage();
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
    this.findCustomerInfoPage();
    this.getCityData(),
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