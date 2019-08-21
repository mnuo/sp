<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>小幅宽产品加价调整表
        </b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="5" class="text-left">
        <span>当前录入人:{{login_user.name || ''}}</span>
      </el-col>
      <el-col :span="11" class="text-left">
        <span>双人录入结果：</span>
        <span style="color:red">{{editResult}}</span>
      </el-col>
      <el-col :span="8" class="text-right">
        <!-- <el-button type="primary" >保存</el-button> -->
        <el-button type="primary" @click="matching()">提交</el-button>
        <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      stripe border
      width="100%" :height="tableHight">
      <!-- <el-table-column type="selection" width="35" title="全选"></el-table-column> -->
      <el-table-column type="index" align="center" label="序号" min-width="45">
      </el-table-column>
      <el-table-column label="加价条件" align="center" >
        <el-table-column prop="width" header-align="center" align="center" label="幅宽" min-width="80">
        </el-table-column>
        <el-table-column prop="qty"  align="center" label="套产品中幅数"
                         :render-header="renderheader"
                         min-width="65">
        </el-table-column>
      </el-table-column>
      <el-table-column prop="addPrice" header-align="center" align="center" label="加价幅度(元/吨)" :render-header="renderheader"
                       min-width="65"></el-table-column>
      <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="65"></el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" :render-header="renderheader"
                       min-width="80"></el-table-column>
      <el-table-column prop="addPrice" align="center" label="调整" :render-header="renderheader"
                       min-width="65">
        <template slot-scope="scope">
          <el-button @click="adjust(scope.row)" style="color:#29d" icon="el-icon-edit"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div>
      <el-dialog :visible.sync="dialogAdjust" title="调整" v-dialogDrag width="26%" center>
        <el-row>
          <el-col :span="6" class="text-left">
            加价幅度：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="tableDataA.addPrice" style="width:140px"></el-input>
          </el-col>
          <el-col :span="6" class="text-left">
           （元/吨）
          </el-col>
        </el-row>
        <el-row style="margin-top:10px">
          <el-col :span="6" class="text-left">
            生效时间：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-date-picker type="date" placeholder="生效时间" value-format="yyyy-MM-dd" v-model="tableDataA.startDate"></el-date-picker>
          </el-col>
        </el-row>
        <span slot="footer" >
          <el-button type="primary" @click="doSave()">确定</el-button>
        </span>
      </el-dialog>
    </div>
    <div>
      <el-dialog :visible.sync="dialogSuccess" v-dialogDrag title="调整" width="25%">
        <span>小幅宽产品加价调整双人录入匹配成功！</span>
        <span slot="footer">
          <el-button type="primary" @click="$router.go(-1)">确定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import Price_API from '@/api/import/price'
  import { mapGetters } from 'vuex'
  import {directive} from '@/views/base/import/productBase'
  export default {
    name: "price-list",
    data() {
      return {
        loading: false,
        tableHight: 700,
        dialogAdjust:false,
        dialogSuccess:false,
        tableData: [],
        tableDataA:[],
        width:0,
        qty:0,
        editResult:"待匹配",
      }
    },
    components: {},
    methods: {
      renderheader(h, {column, $index}) {
        return h('span', {}, [
          h('span', {}, column.label.split('</br>')[0]),
          h('br'),
          h('span', {}, column.label.split('</br>')[1])
        ])
      },
      initPage() {
        this.width=this.$route.query.width;
        this.qty=this.$route.query.qty;
        console.log(this.width)
        this.queryImSpecialPriceList();
      },
      queryImSpecialPriceList() {
        this.loading = true;
        var params={
          width:this.width,
          qty:this.qty,
        }
        Price_API.findNewHistory_ImPriceAddCondition(params).then(res => {
          this.loading = false;
          this.tableData = res.data || [];
          for(let i=0;i<this.tableData.length;i++){
            this.tableData[i].qty="≥ "+this.tableData[i].qty;
            this.tableData[i].width="< "+this.tableData[i].width;
          }
          console.log(this.tableData)
        }).catch(err => {
          this.loading = false;
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
          console.log(JSON.stringify(err));
        })
      },
      adjust(item){
        this.tableDataA=item;
        this.dialogAdjust=true;
      },
      //保存
      doSave(){
        var params=this.tableDataA;
        var w = params.width.split(" ");
        var q = params.qty.split(" ");
          params.qty=q[1];
          params.width=w[1];
        Price_API.addOneEdit_ImPriceAddCondition(params).then(res=>{
          this.$message({
            type:res.success?'res.success':'res.info',
            message:res.success?'录入成功':res.msg,
          });
          // console.log("ta",this.tableData)
          this.dialogAdjust=false;
          this.queryImSpecialPriceList();
        }).catch(err=>{
          this.$message({
            type:'error',message:err.msg,
          });
          this.dialogAdjust=false;
        })
      },
      //提交
      matching(){
       for(let i=0;i<this.tableData.length;i++){
          var w = this.tableData[i].width.split(" ");
          var q = this.tableData[i].qty.split(" ");
            this.tableData[i].qty=q[1];
            this.tableData[i].width=w[1];
          }
        var params=this.tableData;
        Price_API.matching_ImPriceAddCondition(params).then(res=>{
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
    },
    created() {
    },
    mounted() {
      this.initPage();
    },
    computed: {
    ...mapGetters(['login_user']),

    },
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
</style>
