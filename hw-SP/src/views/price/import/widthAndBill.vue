<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>幅宽及承兑汇票结算差价表</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary" @click="widthAndBillAdjust()">调整</el-button>
        <!-- <el-button type="info" @click="$router.go(-1)">返回上一级</el-button> -->
      </el-col>
    </el-row>
    <el-table 
    :data="tableData" v-loading="loading" element-loading-text="拼命加载中" elemtent-loading-spanner="el-icon-loading"
    stripe border
    style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
    <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="350"></el-table-column>
    <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="150"></el-table-column>
    <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="150"></el-table-column>
    <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="150"></el-table-column>
    <!-- <el-table-column header-align="center" align="center" label="调整" min-width="150">
      <template slot-scope="scope">
        <el-button @click="widthAndBillAdjust(scope.row)">调整</el-button>
      </template>
    </el-table-column> -->
    <el-table-column header-align="center" align="center" label="历史价差查询" min-width="150">
      <template slot-scope="scope">
        <el-button @click="widthAndBillHistory(scope.row)">历史价差</el-button>
      </template>
    </el-table-column>
    </el-table>
    <div>
      <el-dialog :visible.sync="dialogFormAddOne" title="新增" v-dialogDrag>
        <el-row>
          <el-col :span="24" class="text-left">
            <div>录入人：{{firstName}}/{{secondName}}</div>
          </el-col>
        </el-row>
        <el-table :data="tableDataA" border>
          <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemName"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="80">
            <template slot-scope="scope">
              <el-input v-model="scope.row.diffPrice"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="isPolicy" header-align="center" align="center" label="享受政策" min-width="80">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isPolicy">
                <el-option :value="true" label="同优等品">同优等品</el-option>
                <el-option :value="false" label="不享受">不享受</el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="80">
            <template slot-scope="scope">
              <el-date-picker v-model="scope.row.startDate"></el-date-picker>
            </template>
          </el-table-column>
        </el-table>
        <div class="dialog-footer" slot="footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>
      </el-dialog>
    </div>
    <div>
      <el-dialog :visible.sync="dialogAdjust" title="调整" v-dialogDrag>
        <el-row>
          <el-col class="text-left">
            <div>录入人：{{firstName}}/{{secondName}}</div>
          </el-col>
        </el-row>
        <el-table :data="tableDataB" border>

          <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="130">
              <template slot-scope="scope">
                <el-input v-model="scope.row.itemName"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="60">
              <template slot-scope="scope">
                <el-input v-model="scope.row.diffPrice"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="isPolicy" header-align="center" align="center" label="享受政策" min-width="70">
              <template slot-scope="scope">
                <el-select v-model="scope.row.isPolicy">
                  <el-option :value="true" label="同优等品">同优等品</el-option>
                  <el-option :value="false" label="不享受">不享受</el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="80">
              <template slot-scope="scope">
                <el-date-picker v-model="scope.row.startDate"></el-date-picker>
              </template>
            </el-table-column>
          </el-table>
          <div class="dialog-footer" slot="footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addOne()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <div>
      <el-dialog :visible.sync="dialogHistory" title="历史价差查询" v-dialogDrag>
        <el-table :data="tableDataC" border>
          <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
          <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="150"></el-table-column>
          <el-table-column prop="diffPrice" header-align="center" align="center" label="价差(元/吨)" min-width="60"></el-table-column>
          <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="70"></el-table-column>
          <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="80"></el-table-column>
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import Price_API from '@/api/import/price'
  import {directive} from '@/views/base/import/productBase'
export default {
   name: "price-list",
  data(){
    return{
       tableData:[],
       tableDataA:[{
         itemName:'',
         diffPrice:'',
         isPolicy:'',
         startDate:'',
       }],
       tableDataB:[],
       tableDataC:[],
       loading:false,
       dialogFormAddOne:false,
       dialogAdjust:false,
       dialogHistory:false,
       itemName:'',
       firstName:'第一人',
       secondName:'第二人',
    }
  },
  components:{},
  methods:{
    //初始化
    initPage(){
      this.findAll_imPriceDifference();
    },
    findAll_imPriceDifference(){
      Price_API.findAll_imPriceDifference().then(res=>{
        this.loading=true;
        this.tableData=res.data||[];
        console.log(this.tableData)
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:err.msg})
      })
    },
    //调整
    widthAndBillAdjust(){
      // this.dialogAdjust=true;
      // this.tableDataB=[item];
      // console.log(item)
      this.$router.push({
        path:"/price/import/widthAndBillAdjust"
      })

    },
    //历史价差
    widthAndBillHistory(item){
      
      var params=item.itemName;
      console.log("||",item)
      Price_API.findHistory_imPriceDifference(params).then(res=>{
        this.loading=true;
        this.tableDataC=res.data||[];
        console.log("history",this.tableDataC)
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({typr:'error',message:err.msg})
      })
      
      this.dialogHistory=true;
    },
    //新增
    addOne(){
      var params=this.itemName;
      Price_API.addOne_imPriceDifference(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功':res.msg,
        })
        this.dialogFormAddOne=false;
        this.findAll_imPriceDifference();
      }).catch(err=>{
        this.$message({type:'error',message:err.msg})
      })
    }
  },
  mounted(){
    this.initPage()
  }
}
</script>

<style lang="scss" scoped>
.grid-content {
    border-radius: 0px;
    min-height: 25px;
    // margin-left: -10px;
    margin-right: 5px;
    margin-bottom: 15px;
    text-align: center;
    line-height:25px;
    border: 1px solid rgb(218, 215, 215);
  }
  .bg-purple {
    background: #e4e7ed;
  }
</style>