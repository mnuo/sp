<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>【{{enterpriseSetNo}}】特规品销售价格明细表
        </b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData" :span-method="objectOneMethod" v-loading="loading" element-loading-text="拼命加载中" elemtent-loading-spanner="el-icon-loading"
      stripe border
      ref="multipleTable"
      width="100%" :height="tableHight">
      <!-- <el-table-column type="selection" width="35" title="全选"></el-table-column> -->
      <el-table-column  header-align="center" align="center" label="序号" width="45"></el-table-column>
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

  </div>
</template>

<script>
  import Price_API from '@/api/import/price'

  export default {
    name: "price-list",
    data() {
      return {
        loading: false,
        tableHight: 700,
        multipleSelection: [],
        tableData: [],
        enterpriseSetNo:'',
        ration:'',
        physicalNo:'',
        wrinkleRate:'',
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
        this.enterpriseSetNo=this.$route.query.enterpriseSetNo;
        this.ration=this.$route.query.ration;
        this.physicalNo=this.$route.query.physicalNo;
        this.wrinkleRate=this.$route.query.wrinkleRate;
        this.findHistorySpecialSetPrice();
      },
      findHistorySpecialSetPrice() {
        this.loading=true;
        var params={
          ration:this.ration,
          wrinkleRate:this.wrinkleRate,
          physicalNo:this.physicalNo,
        }
        Price_API.findHistorySpecialSetPrice(params).then(res => {
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
      if(columnIndex === 0||columnIndex === 1||columnIndex === 2||columnIndex === 3) {
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
