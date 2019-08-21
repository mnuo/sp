<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>小幅宽产品加价表
        </b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <!-- <el-button type="info" @click="$router.go(-1)">返回上一级</el-button> -->
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
        <el-table-column prop="width" header-align="center" align="center" label="幅宽(mm)" min-width="80">
        </el-table-column>
        <el-table-column prop="qty"  align="center" label="套产品中幅数"
                         :render-header="renderheader"
                         min-width="80">
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
      <el-table-column prop="adj_sourcePrice" align="center" label="历史加价查询" min-width="90">
        <template slot-scope="scope">
          <el-button @click="queryHistory()" icon="el-icon-search"></el-button>
        </template>
      </el-table-column>
    </el-table>
 
    <div>
      <el-dialog :visible.sync="dialogHistory" v-dialogDrag title="历史加价查询">
        <el-table :data="tableDataA" border>
          <el-table-column type="index" align="center" label="序号" min-width="45">
          </el-table-column>
          <el-table-column label="加价条件" align="center" >
            <el-table-column prop="width" header-align="center" align="center" label="幅宽(mm)" min-width="80">
            </el-table-column>
            <el-table-column prop="qty"  align="center" label="套产品中幅数"
                            :render-header="renderheader"
                            min-width="80">
            </el-table-column>
          </el-table-column>
          <el-table-column prop="addPrice" header-align="center" align="center" label="加价幅度</br>(元/吨)" :render-header="renderheader"
                          min-width="65"></el-table-column>
          <el-table-column prop="isPolicyText" header-align="center" align="center" label="享受政策" min-width="65"></el-table-column>
          <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" :render-header="renderheader"
                          min-width="80"></el-table-column>
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
    data() {
      return {
        loading: false,
        tableHight: 700,
        multipleSelection: [],
        tableData: [],
        tableDataA:[],
        dialogAdjust:false,
        dialogHistory:false,
        firstName:'第一人',
        secondName:'第二人',
        width:0,
        qty:0,
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
        this.queryImSpecialPriceList();
      },
      queryImSpecialPriceList() {
        this.loading = true;
        Price_API.findAll_ImPriceAddCondition().then(res => {
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
        var w = item.width.split(" ");
        var q = item.qty.split(" ");
        this.$router.push({
          path: '/price/import/addPriceMinWidthAdjust',
          query:{width:w[1],qty:q[1]}
        })
      },
      queryHistory(){
        this.dialogHistory=true;
        Price_API.findHistory_ImPriceAddCondition().then(res=>{
          this.tableDataA=res.data||[];
          for(let i=0;i<this.tableDataA.length;i++){
            this.tableDataA[i].qty="≥"+this.tableDataA[i].qty;
            this.tableDataA[i].width="<"+this.tableDataA[i].width;
          }
          console.log(this.tableDataA);
        }).catch(err=>{
          this.$message.push({
            type:'error',
            message:err.msg,
          })
        })
      }
    },
    created() {
    },
    mounted() {
      this.initPage();
    }
  }
</script>


<style lang="scss" scoped>
  .el-table td div {
    width: 100%;
    /*padding-left: 0px!important;*/
    /*padding-right:  0px!important;;*/
  }
</style>
