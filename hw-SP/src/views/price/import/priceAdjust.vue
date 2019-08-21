<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>产品价格调整录入</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary">调整说明</el-button>
        <el-button type="primary" @click="doSave()">保存</el-button>
        <el-button type="primary" @click="sendToOA()">提交OA</el-button>
        <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              stripe border
              style="width: 100%">
      <el-table-column type="index" fixed="left" header-align="center" label="序号" width="45"></el-table-column>
      <el-table-column prop="classText" fixed="left" header-align="center" label="品类" min-width="110"></el-table-column>
      <el-table-column prop="color" fixed="left" header-align="center" label="颜色" width="55"></el-table-column>
      <el-table-column prop="kindText" fixed="left" header-align="center" label="产品类型" width="70"></el-table-column>
      <el-table-column prop="unit" header-align="center" label="定价单位" width="70"></el-table-column>
      <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader"
                       width="75"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader"
                       width="60"></el-table-column>
      <el-table-column prop="layers" header-align="center" label="层数" width="60"></el-table-column>
      <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader"
                       min-width="80"></el-table-column>
      <el-table-column prop="diameter" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader"
                       min-width="70"></el-table-column>
      <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                       width="55"></el-table-column>
      <el-table-column prop="adj_sourcePrice" header-align="center" align="center" label="调整前价格" width="60">
      </el-table-column>
      <el-table-column header-align="center" label="调整幅度</br>(元/吨)" :render-header="renderheader"
                       width="110">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.adj_addPrice" controls-position="right" :step="100">
          </el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="adj_price" header-align="center" align="center" label="调整后价格" width="60">
      </el-table-column>
      <el-table-column prop="adj_startDate" header-align="center" label="执行时间" min-width="150">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.adj_startDate" align="right" type="date" placeholder="执行时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="adj_endDate" header-align="center" label="结束时间" min-width="150">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.adj_endDate" align="right" type="date" placeholder="结束时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" min-width="100">
      </el-table-column>
      <el-table-column prop="remark" header-align="center" label="备注" min-width="120">
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
        Price_API.adjustQueryList().then(res => {
          this.tableData = res.data || [];
          this.loading = false;
          console.log(this.tableData)
        }).catch(err => {
          this.$message({type: 'error', message: '加载列表异常：' + err.msg});
          this.loading = false;
          console.log(JSON.stringify(err));
        });
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
            message: '已取消提交OA'
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
