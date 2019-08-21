<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>历史价格查询表</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20">
        <el-col :span="2">统计时间：</el-col>
        <el-col :span="4">
          <el-date-picker type="date" placeholder="选择日期" v-model="startdate" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col :span="1">至</el-col>
        <el-col :span="4">
          <el-date-picker type="date" placeholder="选择日期" v-model="enddate" style="width: 100%;">
          </el-date-picker>
        </el-col>
        <el-col :span="1">
          <i class="text-center el-icon-search" style="cursor: pointer" @click="queryHistoryPrice()"> </i>
        </el-col>
      </el-col>
      <el-col :span="4" class="text-right">
        <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              stripe border style="width: 100%">
      <el-table-column type="index" label="序号" min-width="60">
      </el-table-column>
      <el-table-column align="center" label="调整时间" min-width="80">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.c1!=null?scope.row.c1.substring(0,10):'' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="c2" align="center" label="调整类型" min-width="80"></el-table-column>
      <el-table-column prop="c3" align="center" label="常规卫卷调幅(元/吨)" min-width="80"></el-table-column>
      <el-table-column prop="c4" align="center" label="常规品卫卷现金出厂价格(元/吨)" min-width="80"></el-table-column>
      <el-table-column prop="classText" align="center" label="明细查询" min-width="80">
        <template slot-scope="scope">
          <el-button type="primary" title="查询" cirtle @click="goHistoryDetail(scope)">查看</el-button>
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
        ruleForm: [],
        startdate: '',
        enddate: '',
        tableData: [],
      }
    },
    components: {},
    methods: {
      indexMethod(index) {
        return index;
      },
      //跳转
      goHistoryDetail(item) {
        this.$store.commit('pushPirceHistoryObj', {startDate: item.row.c1, addPrice: item.row.c3, price: item.row.c4});
        this.$router.push({
          path: '/price/import/priceListOldDetail'
        })
      },
      queryHistoryPrice() {
        this.loading = true;
        Price_API.queryHistoryPrice().then(res => {
          this.tableData = res.data || [];
          this.loading = false;
          console.log(this.tableData)
        }).catch(err => {
          this.loading = false;
        });
      }
    },
    created() {
    },
    mounted() {

      this.queryHistoryPrice();
    }

  }
</script>

<style lang="scss" scoped>

</style>
