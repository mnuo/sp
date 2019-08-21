<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>副品及承兑汇票结算差价表
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
      :data="tableData"
      stripe border
      ref="multipleTable" @selection-change="handleSelectionChange"
      width="100%" :height="tableHight">
      <el-table-column type="selection" width="35" title="全选"></el-table-column>
      <el-table-column type="index" align="center" label="序号" min-width="45">
      </el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="价差项目" :render-header="renderheader"
                       min-width="65"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="价差(元/吨)" :render-header="renderheader"
                       min-width="65"></el-table-column>
      <el-table-column prop="layers" header-align="center" label="享受政策" min-width="65"></el-table-column>
      <el-table-column prop="width" header-align="center" label="生效时间" :render-header="renderheader"
                       min-width="80"></el-table-column>
      <el-table-column prop="addPrice" align="center" label="调整" :render-header="renderheader"
                       min-width="65">
        <template slot-scope="scope">
          <i class="text-center el-icon-search"> </i>
        </template>
      </el-table-column>
      <el-table-column prop="adj_sourcePrice" align="center" label="历史价格查询" min-width="90">
        <template slot-scope="scope">
          <i class="text-center el-icon-search"  size="medium"> </i>
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
          this.loading = false;
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
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
            message: '已取消删除'
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
