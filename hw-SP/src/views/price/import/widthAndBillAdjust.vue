<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>幅宽及承兑汇票结算差价调整</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6" class="text-left">
        <span>当前录入人：{{login_user.name || ''}}</span>
      </el-col>
      <el-col :span="10" class="text-left">
        <span>双人录入结果：</span>
        <span style="color:red">{{editResult}}</span>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary" @click="matching()">提交</el-button>
        <el-button type="primary" @click="dialogProject=true">选择项目</el-button>
        <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table 
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" elemtent-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
    <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="350"></el-table-column>
    <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="150">
    </el-table-column>
    <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="150">
    </el-table-column>
    <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="150">
    </el-table-column>
    <el-table-column header-align="center" align="center" label="调整">
      <template slot-scope="scope">
        <el-button  @click="adjust(scope.row)" style="color:#29d" icon="el-icon-edit"></el-button>
      </template>
    </el-table-column>
    </el-table>
    <div>
      <el-dialog :visible.sync="dialogProject" title="价差项目选择" v-dialogDrag>
        <el-table :data="tableDataA" border @selection-change="handleSelectionChange">
          <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
          <el-table-column type="selection" width="40" title="全选"></el-table-column>
          <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="150"></el-table-column>
          <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="60"></el-table-column>
          <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="70"></el-table-column>
          <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="80"></el-table-column>
        </el-table>
        <span slot="footer">
          <el-button type="info">取消</el-button>
          <el-button type="primary" @click="select()">确定</el-button>
        </span>
      </el-dialog>
    </div>
    <div>
      <el-dialog :visible.sync="dialogAdjust" title="调整" v-dialogDrag width="26%" center>
        <el-row>
          <el-col :span="6" class="text-left">
            价差项目：
          </el-col>
          <el-col :span="18" class="text-left">
            <el-input v-model="tableDataB.itemName"></el-input>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="6" class="text-left">
            价&#12288;&#12288;差：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="tableDataB.diffPrice" style="width:140px"></el-input>
          </el-col>
          <el-col :span="6" class="text-left">
           （元/吨）
          </el-col>
        </el-row>
        <el-row  class="row-top" >
          <el-col :span="6" class="text-left">享受政策：</el-col>
          <el-col :span="12">
            <el-select v-model="tableDataB.isPolicy">
              <el-option :value="true" label="同优等品">同优等品</el-option>
              <el-option :value="false" label="同副品">同副品</el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row class="row-top">
          <el-col :span="6" class="text-left">
            生效时间：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-date-picker type="date" placeholder="生效时间" value-format="yyyy-MM-dd" v-model="tableDataB.startDate"></el-date-picker>
          </el-col>
        </el-row>
        <span slot="footer" >
          <el-button type="primary" @click="doSave()">确定</el-button>
        </span>
      </el-dialog>
    </div>
    <div>
      <el-dialog :visible.sync="dialogSuccess" v-dialogDrag title="调整" width="25%">
        <span>幅宽及承兑汇票结算差价调整双人录入匹配成功！</span>
        <span slot="footer">
          <el-button type="primary" @click="$router.go(-1)">确定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import Price_API from '@/api/import/price'
  import {directive} from '@/views/base/import/productBase'
  import { mapGetters } from 'vuex'
export default {
   name: "price-list",
  data(){
    return{
       tableData:[],
       tableDataA:[],
       tableDataB:[],
       dialogProject:false,
       dialogAdjust:false,
       dialogSuccess:false,
       multipleSelection:[],
       loading:false,
       itemName:'',
       addPrice:'',
       isPolicyText:'',
       startDate:'',
       isPolicy:true,
       fdId:'',
       editResult:'',
    }
  },
  components:{},
  methods:{
    //初始化
    initPage(){
      this.findNewHistory_imPriceDifference();
    },
    findNewHistory_imPriceDifference(){
      Price_API.findNewHistory_imPriceDifference().then(res=>{
        this.loading=true;
        this.tableDataA=res.data||[];
        // console.log(this.tableData)
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
      })
    },
    //调整
    adjust(item){
      this.tableDataB=item;
      this.dialogAdjust=true;
    },
    //保存
    doSave(){
      // console.log(this.tableData)
      var params=this.tableDataB;
      if(this.tableDataB.isPolicy==true){
        params.isPolicyText="同优等品";
      }else{
        params.isPolicyText="同副品"
      }
      Price_API.addOneEdit_imPriceDifference(params).then(res=>{
        this.$message({
          type:res.success?'res.success':'res.info',
          message:res.success?'录入成功':res.msg,
        });
        // console.log("ta",this.tableData)
        this.dialogAdjust=false;
        // debugger
        this.findNewHistory_imPriceDifference();
        // this.queryImSpecialPriceList();
      }).catch(err=>{
        this.$message({
          type:'error',message:err.msg,
        });
        this.dialogAdjust=false;
      })
    },
     //提交
    matching(){
      this.findNewHistory_imPriceDifference();
 console.log("tableDataA",this.tableDataA)
      for(let j=0;j<this.tableData.length;j++){
        for(let i=0;i<this.tableDataA.length;i++){
          if(this.tableData[j].itemName===this.tableDataA[i].itemName){
            this.tableData[j]=this.tableDataA[i];
            // console.log("tableDataA",this.tableDataA)
            // console.log("tableData",this.tableData)
          }
        }
      }
      var params=this.tableData;
      Price_API.matching_imPriceDifference(params).then(res=>{
        // this.$message({
        //   type:res.success?'res.success':'res.info',
        //   message:res.success?'双人录入成功':res.msg,
        // });
        // console.log("ta",this.tableData)
        this.dialogAdjust=false;
        this.dialogSuccess=true;
        // this.$router.push({
        //   path:'/price/import/addPriceMinWidthA',
        // })
      }).catch(err=>{
        this.$message({
          type:'error',message:err.msg,
        });
        this.editResult="匹配失败";
        this.dialogAdjust=false;
      })
    },
    //选择
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleSelectionProdChange(val) {
      this.selectedProd = val;
    },
    select(){
      this.tableData=this.multipleSelection;
      this.dialogProject=false;
      this.editResult="待匹配"
    }
  },
  computed: {
    ...mapGetters(['login_user']),

    },
  mounted(){
    this.initPage()
  }
}
</script>

<style lang="scss" scoped>
  .el-table td div {
    width: 100%;
    /*padding-left: 0px!important;*/
    /*padding-right:  0px!important;;*/
  }
  .el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 140px;
  }
  .row-top{
    margin-top:10px;
  }
</style>
