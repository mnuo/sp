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
        <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"  v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
      stripe border
      ref="multipleTable" @selection-change="handleSelectionChange"
      width="100%" :height="tableHight">
      <el-table-column type="selection" width="35" title="全选"></el-table-column>
      <el-table-column type="index" header-align="center" align="center" label="序号" width="45"></el-table-column>
      <el-table-column prop="classText" header-align="center" label="品类" min-width="80"></el-table-column>
      <el-table-column prop="ration" header-align="center" align="center" label="定量</br>(g/㎡)"
                       :render-header="renderheader"
                       width="65"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="起皱率</br>(%)" :render-header="renderheader"
                       width="65"></el-table-column>
      <el-table-column prop="layers" header-align="center" label="层数" width="65"></el-table-column>
      <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader"
                       min-width="80"></el-table-column>
      <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                       width="65"></el-table-column>
      <el-table-column prop="adj_sourcePrice" header-align="center" label="对应常规品价格(元/吨)" width="90">
      </el-table-column>
      <el-table-column header-align="center" label="加价或优惠</br>(元/吨)" :render-header="renderheader"
                       width="90">
      </el-table-column>
      <el-table-column prop="price" header-align="center" label="执行价格(元/吨)" width="80">
      </el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="执行时间" min-width="90">
      </el-table-column>
      <el-table-column prop="endDate" header-align="center" align="center" label="结束时间" min-width="90">
      </el-table-column>
      <el-table-column prop="isPolicyText" header-align="center" label="是否享受政策" width="60">
      </el-table-column>
      <el-table-column  align="center" label="物理指标查看" min-width="60">
        <template slot-scope="scope">
          <i class="text-center el-icon-search"> </i>
        </template>
      </el-table-column>
      <el-table-column  lign="center" label="历史价格查询" min-width="60">
        <template slot-scope="scope">
          <i class="text-center el-icon-search"> </i>
        </template>
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
        tableData: []
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
        Price_API.queryImSpecialPriceList_imSpecialPrice().then(res => {
          this.tableData = res.data || [];
          this.loading = false;
          console.log(this.tableData)
        }).catch(err => {
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
          this.loading = false;
          console.log(JSON.stringify(err));
        })
      },
      doSave() {
        let params = this.tableData || [];
        console.log(1, params)
        if (params.length == 0) {
          this.$message({
            type: 'info',
            message: '没有需要提交的数据！'
          })
          return;
        }
        //注：接口提交的参数与加载绑定的参数不一样，需要重新赋值！！
        params.forEach(item => {
          item.startDate = item.adj_startDate;
          item.fdId = item.adj_fdId;
          item.addPrice = item.adj_addPrice;
          item.remark = item.adj_remark;
        })
        console.log(2, params)
        let _this = this;
        Price_API.adjustUpdateSome(params).then(res => {
          this.initPage();
          _this.$message({
            type: res.success ? 'success' : 'info',
            message: res.success ? "保存成功" : res.msg
          })
        }).catch(err => {
          _this.$message({
            type: 'error',
            message: "保存异常：" + err.msg
          })
        })
      },
      sendToOA() {
        this.$confirm('确定要提交OA吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: '警告'
        }).then(() => {
            Price_API.adjustSentToOa().then(res => {
              this.initPage();
              this.$message({
                type: res.success ? 'success' : 'info'
                , message: res.success ? '提交成功' : res.msg
              })
            }).catch(err => {
              this.$message({type: 'error', message: err.msg})
            })
          }
        ).catch((ex) => {
          console.log(ex);
          this.$message({
            type: 'info',
            message: '已取消提交OA的操作'
          })
        })
      },
      goAdjustList() {
        if (this.multipleSelection.length > 0) {
          let specialPriceIds = paramsList.map(item => item.fdId).join(';')
          Price_API.adjustSelectPrice_imSpecialPrice(specialPriceIds).then(res => {
            this.$router.push({
              path: '/price/import/espPriceAdjust'
            })
          }).catch(err => {
            this.$message({type: 'error', message: '加载列表异常：' + err.msg})
            console.log(JSON.stringify(err));
          })
        }
        else {
          this.$confirm('您还未选择任何记录，确定要进入加价或调整界面吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(
            () => {
              this.$router.push({
                path: '/price/import/espPriceAdjust'
              })
            }
          ).catch((err) => {
            this.$message({type: 'info', message: '已取消进入加价或调整界面'})
          })

        }

      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
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
