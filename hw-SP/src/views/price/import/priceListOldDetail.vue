<template>
  <div>
    <el-row>
      <el-col :span="20">
        <div><b>价格明细查询</b></div>
      </el-col>
      <el-col :span="4" class="text-right">
        <div>
          <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
        </div>
      </el-col>
    </el-row>
    <el-table
      :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
      stripe border style="width: 100%">
      <el-table-column type="index" header-align="center" label="序号" width="45"></el-table-column>
      <el-table-column prop="classText" header-align="center" label="品类" min-width="80"></el-table-column>
      <el-table-column prop="color" header-align="center" label="颜色" width="55"></el-table-column>
      <el-table-column prop="kindText" header-align="center" label="产品类型" width="70"></el-table-column>
      <el-table-column prop="unit" header-align="center" label="定价单位" width="70"></el-table-column>
      <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader"
                       width="75"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="起皱率</br>(%)" :render-header="renderheader"
                       min-width="55"></el-table-column>
      <el-table-column prop="layers" header-align="center" label="层数" width="60"></el-table-column>
      <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader"
                       min-width="80"></el-table-column>
      <el-table-column prop="diameter" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader"
                       min-width="70"></el-table-column>
      <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                       width="50"></el-table-column>
      <el-table-column prop="price" header-align="center" label="增加价格</br>(元/吨)" :render-header="renderheader"
                       width="70"></el-table-column>
      <el-table-column prop="price" header-align="center" label="执行价格" width="50"></el-table-column>
      <el-table-column prop="startDate" header-align="center" label="执行时间" min-width="85"></el-table-column>
      <el-table-column prop="endDate" header-align="center" label="结束时间" min-width="85"></el-table-column>
      <el-table-column prop="isPolicyText" header-align="center" label="是否享受政策" width="60"></el-table-column>
      <el-table-column prop="remark" header-align="center" label="备注" min-width="100"></el-table-column>
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
        startDate: '',
        price: '',
        addPrice: '',
        tableData: [],
      }
    },
    components: {},
    methods: {
      //初始化
      intPage() {
        var obj = this.$store.state.price.priceHistoryObj || {};//this.priceHistoryObj||{}
        this.startDate = obj.startDate;
        this.price = obj.price;
        this.addPrice = obj.addPrice;
        // this.startDate = this.$route.query.startDate;
        // this.price = this.$route.query.price;
        // this.addPrice = this.$route.query.addPrice;
        this.queryHistoryPriceDetail();
      },
      renderheader(h, {column, $index}) {
        return h('span', {}, [
          h('span', {}, column.label.split('</br>')[0]),
          h('br'),
          h('span', {}, column.label.split('</br>')[1])
        ])
      },
      indexMethod(index) {
        return index;
      },
      queryHistoryPriceDetail() {
        this.loading = true;
        let params = {
          startDate: this.startDate,
          price: this.price,
          addPrice: this.addPrice
        }
        Price_API.queryHistoryPriceDetail(params).then(res => {
          this.tableData = res.data || [];
          this.loading = false;
          console.log(this.tableData)
        }).catch(err => {
          this.loading = false;
        });
      },
    },
    computed: {},
    created() {
    },
    mounted() {
      this.intPage();
    }
  }
</script>


<style lang="scss" scoped>

</style>
