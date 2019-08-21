<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>加价或优惠调整界面
        </b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="danger" @click="delRowData">删除行</el-button>
        <el-button type="primary" @click="doSave()">保存</el-button>
        <el-button type="danger" @click="$router.go()">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"   v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
      stripe border
      ref="multipleTable" @selection-change="handleSelectionChange"
      width="100%" :height="tableHight">
      <el-table-column type="selection" width="35" title="全选"></el-table-column>
      <el-table-column type="index" header-align="center" align="center" label="序号" width="45"></el-table-column>
      <el-table-column prop="classText" header-align="center" label="品类" min-width="80"></el-table-column>
      <el-table-column prop="ration" header-align="center" align="center" label="定量</br>(g/㎡)"
                       :render-header="renderheader"
                       width="65"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader"
                       width="55"></el-table-column>
      <el-table-column prop="layers" header-align="center" label="层数" width="60"></el-table-column>
      <el-table-column prop="totalWidth" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader"
                       min-width="80"></el-table-column>
      <el-table-column prop="diameter" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                       width="55"></el-table-column>
      <el-table-column prop="adj_normalPrice" header-align="center" align="center" label="对应常规品价格(元/吨)" width="85">
      </el-table-column>
      <el-table-column header-align="center" label="加价或优惠(元/吨)">
        <el-table-column header-align="center" align="center" prop="sourceAddPrice" label="调整前"
                         :render-header="renderheader"
                         width="60">
        </el-table-column>
        <el-table-column header-align="center" prop="adjustAddPrice" label="调整后" :render-header="renderheader"
                         width="100">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.adjustAddPrice" controls-position="right" :step="100">
            </el-input-number>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column prop="price" header-align="center" label="执行价格(元/吨)">
        <el-table-column align="center" prop="sourceExecPrice" label="调整前" :render-header="renderheader"
                         width="60">
        </el-table-column>
        <el-table-column align="center" prop="adjustExecPrice" label="调整后" :render-header="renderheader"
                         width="60">
        </el-table-column>
      </el-table-column>
      <el-table-column prop="adj_startDate" header-align="center" align="center" label="执行时间" min-width="145">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.adj_startDate" align="right" type="date" placeholder="执行时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="adj_isPolicyText" header-align="center" label="是否享受政策" width="60">
      </el-table-column>
      <el-table-column align="center" label="物理指标查看" min-width="60">
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
        this.adjustQueryList();
      },
      adjustQueryList() {
        this.loading = true;
        Price_API.adjustQueryList_imSpecialPrice().then(res => {
          this.tableData = res.data || [];
          this.loading = false;
          console.log(this.tableData)
        }).catch(err => {
          this.loading = false;
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
          console.log(JSON.stringify(err));
        })
      },
      delRowData() {
        if (this.multipleSelection.length <= 0) {
          this.$message({type: 'warning', message: '请选择需要删除的内容'})
          return;
        }
        this.$confirm('确定要删除所选的' + this.multipleSelection.length + '行内容吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: '警告'
        }).then(() => {
            //循环，单条删除？？
            this.multipleSelection.forEach(item => {
              Price_API.adjustDeleteRecords_imSpecialPrice(item.adj_fdId).then(res => {
                this.$message({
                  type: res.success ? 'success' : 'info'
                  , message: res.success ? ('删除成功【' + item.classText + '】') : res.msg
                });
                this.initPage();
              }).catch(err => {
                this.$message(
                  {
                    type: 'error'
                    , message: err.msg
                  }
                )
              })
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

      doSave() {
        let params = this.tableData || [];
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
          item.adjustAddPrice = item.adjustAddPrice;
          item.isPolicy = item.adj_isPolicy;
          item.remark = item.adj_remark;
        })
        let _this = this;
        Price_API.adjustUpdateSome_imSpecialPrice(params).then(res => {
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
