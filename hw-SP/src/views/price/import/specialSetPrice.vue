<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>【卫卷】特规品销售价格明细表
        </b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <!-- <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button> -->
      </el-col>
    </el-row>
    <el-table
      :data="tableData" :span-method="objectOneMethod"
      stripe border
      ref="multipleTable"
      width="100%" :height="tableHight">
      <!-- <el-table-column type="selection" width="35" title="全选"></el-table-column> -->
      <el-table-column  header-align="center" align="center" label="序号" width="45">      
        <template slot-scope="scope"><span>{{scope.$index+1}} </span></template>
      </el-table-column>
      <el-table-column prop="classText" header-align="center" label="品类" min-width="60"></el-table-column>
      <el-table-column prop="ration" header-align="center" align="center" label="定量</br>(g/㎡)"
                       :render-header="renderheader"
                       width="65"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" align="center" label="起皱率</br>(%)" :render-header="renderheader"
                       width="60"></el-table-column>
      <el-table-column prop="layer" header-align="center" align="center" label="层数" width="50"></el-table-column>
      <el-table-column prop="cutSpec" header-align="center" label="分切幅宽合计</br>(mm)" :render-header="renderheader"
                       min-width="100"></el-table-column>
      <el-table-column prop="diameter" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                       min-width="65"></el-table-column>
      <el-table-column prop="normalPrice" header-align="center" align="center" label="对应常规品价格(元/吨)" width="90">
      </el-table-column>

      <el-table-column prop="addPrice" header-align="center" align="center" label="加价或优惠</br>(元/吨)" :render-header="renderheader" width="90">
      </el-table-column>
      <el-table-column prop="width" header-align="center" align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader" width="90" >
      </el-table-column>
      <el-table-column prop="minWidthAddPrice" header-align="center" align="center" label="小幅宽加价</br>(元/吨)" :render-header="renderheader" width="90"></el-table-column>
      <el-table-column prop="price" header-align="center" align="center" label="执行价格(元/吨)" width="80">
      </el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="执行时间" min-width="90">
      </el-table-column>

      <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" width="60">
      </el-table-column>
      <el-table-column header-align="center" align="center" label="物理指标查看" min-width="60">
        <template slot-scope="scope">
          <el-button icon="el-icon-search"></el-button>
        </template>
      </el-table-column>
      <el-table-column header-align="center" align="center" label="历史价格查询" min-width="60">
        <template slot-scope="scope">
          <el-button icon="el-icon-search" @click="searchHistory(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div>
      <el-dialog :visible.sync="dialogHistory" v-dialogDrag :title=title width="1000px">
        <el-table :data="tableDataA" :span-method="objectOneMethod"  border width="100%" >
          <!-- <el-table-column type="selection" width="35" title="全选"></el-table-column> -->
          
          <el-table-column prop="startDate" header-align="center" align="center" label="调整时间" min-width="60"></el-table-column>
          <el-table-column prop="normalPrice" header-align="center" align="center" label="对应常规品价格(元/吨)" min-width="90">
          </el-table-column>
          <el-table-column prop="addPrice" header-align="center" align="center" label="加价或优惠(元/吨)" :render-header="renderheader" min-width="90">
          </el-table-column>
          <el-table-column prop="width" header-align="center" align="center" label="单幅幅宽(mm)" :render-header="renderheader" min-width="90" >
          </el-table-column>
          <el-table-column prop="minWidthAddPrice" header-align="center" align="center" label="小幅宽加价(元/吨)" :render-header="renderheader" min-width="90"></el-table-column>
          <el-table-column prop="price" header-align="center" align="center" label="执行价格(元/吨)" min-width="80">
          </el-table-column>
          <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" min-width="60">
          </el-table-column>
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
        dialogHistory:false,
        multipleSelection: [],
        tableData: [],
        tableDataA:[],
        title:'1111'
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
        this.loading=true;
        Price_API.findAllSpecialSetPrice().then(res => {
          this.loading=false;
          this.tableData = res.data || [];
          this.setdates(this.tableData);
          console.log(this.tableData)
        }).catch(err => {
          this.loading=false;
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
          console.log(JSON.stringify(err));
        })
      },
      //查看历史
      searchHistory(item){
        this.title="【"+item.enterpriseSetNo+"】特规品历史价格明细表"
        this.dialogHistory=true;
        var params={
          ration:item.ration,
          wrinkleRate:item.wrinkleRate,
          physicalNo:item.physicalNo,
        }
        Price_API.findHistorySpecialSetPrice(params).then(res => {
          this.loading=false;
          this.tableDataA = res.data || [];
          this.setdates(this.tableData);
          console.log(this.tableData)
        }).catch(err => {
          this.loading=false;
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
          console.log(JSON.stringify(err));
        })

        // this.$router.push({
        //   path:'/price/import/specialSetPriceHistory',  
        //   query:{enterpriseSetNo:item.row.enterpriseSetNo,ration:item.row.ration,wrinkleRate:item.row.wrinkleRate,physicalNo:item.row.physicalNo}
        // })
      },

      //合并单元格
    setdates(arr){
      var spanOneArr = [],
          concatOne = 0;
      this.tableData.forEach((item,index)=>{
        if(index === 0){
          spanOneArr.push(1);
        }else{
          if(item.classText === this.tableData[index - 1].classText){ //第2列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1;
            spanOneArr.push(0);
          }else{
            spanOneArr.push(1);
            concatOne = index;
          };
        }
      });
        return  {
        one: spanOneArr,
      }
    },
    objectOneMethod({ row, column, rowIndex, columnIndex }) {
      if(columnIndex === 0||columnIndex === 1||columnIndex === 2||columnIndex === 3||columnIndex === 4||columnIndex === 5||columnIndex === 6||columnIndex === 7||columnIndex === 8||columnIndex === 12||columnIndex === 13||columnIndex === 14||columnIndex === 15) {
        const _row = (this.setdates(this.tableData).one)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
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
