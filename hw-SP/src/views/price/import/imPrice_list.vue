<template>
  <div>
    <el-row>
      <el-col :span="24" >
        <el-col :span="18" class="search-row">
          <el-col :span="2" class="text-right">审批状态</el-col>
          <el-col :span="4" >
            <el-select v-model="searchstatus" @change="queryImPriceList" placeholder="审批状态">
              <el-option 
                  v-for="item in statusArr"
                  :key="item.itemNo"
                  :label="item.itemName"
                  :value="item.itemNo">
              </el-option>
            </el-select> 
          </el-col>
          
        </el-col>
        <el-col :span="6" class="text-right">
          <div>
            <el-button type="primary">说明</el-button>
            <!-- <el-button type="primary" @click="goHistoryList()">历史价格查询</el-button> -->
            <el-button type="primary" @click="goAdjustList">价格调整</el-button>
            <el-button type="primary" @click="goAdd()">价格录入</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>


<!-- 
    <el-row class="search-row">
      <el-col :span="2" class="text-right">审批状态</el-col>
      <el-col :span="4" >
        <el-select v-model="searchstatus" @change="queryImPriceList" placeholder="审批状态">
          <el-option 
              v-for="item in statusArr"
              :key="item.itemNo"
              :label="item.itemName"
              :value="item.itemNo">
          </el-option>
        </el-select> 
      </el-col>
      <el-col :span="10">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <el-button type="primary">说明</el-button>
          <el-button type="primary" @click="goHistoryList()">历史价格查询</el-button>
          <el-button type="primary" @click="goAdjustList">价格调整</el-button>
          <el-button type="primary" @click="goAdd()">价格录入</el-button>
        </div>
      </el-col>
    </el-row> -->
    <el-table
      :data="tableData" v-loading="loading" element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
       border :max-height="tableHeight"
      style="width: 100%">
      <el-table-column type="index" header-align="center" align="center" label="序号" width="35"></el-table-column>
      <el-table-column prop="platform" header-align="center" label="纸机" min-width="70"></el-table-column>
      <el-table-column prop="color" header-align="center" label="颜色" width="40"></el-table-column>
      <el-table-column prop="classText" header-align="center" label="品类" min-width="95"></el-table-column>
      <el-table-column prop="kindText" header-align="center" label="产品类型" width="70"></el-table-column>
      <el-table-column prop="unit" header-align="center" label="定价单位" width="70"></el-table-column>
      <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader" width="75"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader" width="55"></el-table-column>
      <!-- <el-table-column prop="layers" header-align="center" label="层数" width="60"></el-table-column> -->
      <!-- <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader" min-width="80"></el-table-column> -->
      <el-table-column prop="width" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader" min-width="70"></el-table-column>
      <!-- <el-table-column prop="diameter" header-align="center" label="直径</br>(mm)" :render-header="renderheader" width="55"></el-table-column> -->
      <el-table-column prop="addPrice" header-align="center" align="right" label="增加价格</br>(元/吨)" :render-header="renderheader" width="70"></el-table-column>
      <el-table-column prop="price" header-align="center" align="right" label="执行价格" width="50"></el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="执行时间" min-width="85"></el-table-column>
      <el-table-column prop="endDate" header-align="center" align="center" label="结束时间" min-width="85"></el-table-column>
      <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" width="50"></el-table-column>
      <el-table-column prop="statusText" header-align="center" align="center" label="状态" width="60"></el-table-column>
      <el-table-column prop="remark" header-align="center" label="备注" min-width="100"></el-table-column>
    </el-table>
    <pagination v-if="pageTotal>0" @pagination="pagination" :total="pageTotal" :limit="pageData.pageSize"></pagination>
  </div>
</template>

<script>
  import Price_API from '@/api/import/price'
  import Pagination from '@/components/Pagination'
  export default {
    name: "price-list",
    data() {
      return {
        loading: false,
        ruleForm: [],
        tableData: [],
        tableHeight: 300,
        pageTotal:0,
        pageData:{
          page:1,
          pageSize: 50
        },
        statusArr:[
          // {itemNo:'',itemName:'全部'},
          // {itemNo:10,itemName:'未提交'},
          // {itemNo:11,itemName:'提交OA'},
          // {itemNo:20,itemName:'审批中'},
          // {itemNo:30,itemName:'已审批'},
          // {itemNo:40,itemName:'已过期'},
        ],
        searchstatus:''
      }
    },
    components: {Pagination},
    methods: {
       pagination(val){
      // debugger;
        this.pageData.pageSize = val.limit;
        this.pageData.page=val.page;
        this.queryImPriceList();
      },
      renderheader(h, {column, $index}) {
        return h('span', {}, [
          h('span', {}, column.label.split('</br>')[0]),
          h('br'),
          h('span', {}, column.label.split('</br>')[1])
        ])
      },
      queryImPriceList() {
        this.loading = true;
        var params = {
          status:this.searchstatus,
          page:this.pageData.page,
          pageSize:this.pageData.pageSize,
        };
        Price_API.findAllListByPage(params).then(res => {
          this.tableData = res.data.list || [];
          console.log(this.tableData);
          this.pageTotal=parseInt(res.data.rowCount);
          this.pageData.pageSize=parseInt(res.data.pageSize);
          this.loading = false;
        }).catch(err => {
          this.loading = false;
          this.$message({type: 'error', message: '请求异常：' + err.msg})
          console.log(err.errorCode);
        });
      },
      //跳转
      goHistoryList() {
        this.$router.push({
          path: '/price/import/priceListOld'
        })
      },
      goAdjustList() {
        this.$router.push({
          path: '/price/import/imPriceAdjust/list'
        });
      },
      goAdd() {
        this.$router.push({
          path: '/price/import/imPrice/add'
        })
      },
       //合并单元格
      setdates(arr){
        var spanOneArr = [1], spanTwoArr = [1], spanThreeArr=[1],spanFourArr=[1],
            concatOne = 0,  concatTwo = 0, concatThree=0, concatFour=0;
        this.tableData.forEach((item,index)=>{
          if(index != 0){
            var platformCompare = false;
            var colorCompare = false;
            var classCompare = false;
            if(item.platform === this.tableData[index - 1].platform){ //第2列需合并相同内容的判断条件
              platformCompare = true;
              spanOneArr[concatOne] += 1; spanOneArr.push(0);
            }else{
              spanOneArr.push(1);  concatOne = index;
            };
            if(item.color === this.tableData[index - 1].color && platformCompare){//第2列需合并相同内容的判断条件
              colorCompare = true;
              spanTwoArr[concatTwo] += 1; spanTwoArr.push(0);
            }else{
              spanTwoArr.push(1); concatTwo = index;
            };
            if(item.classNo === this.tableData[index - 1].classNo && colorCompare){//第3列需合并相同内容的判断条件
              classCompare = true;
              spanThreeArr[concatThree] += 1; spanThreeArr.push(0);
            }else{
              spanThreeArr.push(1); concatThree = index;
            };
            if(item.kindNo === this.tableData[index - 1].kindNo && classCompare){//第4列需合并相同内容的判断条件
              spanFourArr[concatFour] += 1; spanFourArr.push(0);
            }else{
              spanFourArr.push(1); concatFour = index;
            };
          }
        });
        return  {
          one: spanOneArr,
          two: spanTwoArr,
          three: spanThreeArr,
          four: spanFourArr,
        }
      },
      objectOneMethod({ row, column, rowIndex, columnIndex }) {
        if(columnIndex === 1) {
          const _row = (this.setdates(this.tableData).one)[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          return {
            rowspan: _row,
            colspan: _col
          };
        }
        if(columnIndex === 2) {
          const _row = (this.setdates(this.tableData).two)[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          return {
            rowspan: _row,
            colspan: _col
          };
        }
        if(columnIndex === 3) {
          const _row = (this.setdates(this.tableData).three)[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          return {
            rowspan: _row,
            colspan: _col
          };
        }
        if(columnIndex === 4) {
          const _row = (this.setdates(this.tableData).four)[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          return {
            rowspan: _row,
            colspan: _col
          };
        }
      },
      initStatus: function(){
        let params={
          typename:'status'
        };
        Price_API.findDictData(params).then(res=>{
          this.statusArr=res.data;
          // this.statusarr=JSON.parse(JSON.stringify(res.data));
          this.statusArr.unshift({itemNo:'',itemName:'全部'});
        }).catch(err=>{
          this.loading=false;
          this.$message({type: 'error', message: '请求异常：' + err.msg==null?err.message:err.msg});
          this.dialogFormVisibleA= false;
        })
      },
    },
    created() {
    },
    mounted() {
      this.queryImPriceList();
      this.initStatus();
      this.$nextTick(function () {
        this.tableHeight = window.innerHeight - 180;//表头距离浏览器顶部88+分页62+距离底部20=170+10(浮动系数)
        // 监听窗口大小变化
        let self = this;
        window.onresize = function() {
            self.tableHeight = window.innerHeight - 180;
        }
      });
    }
  }
</script>

<style lang="scss" scoped>

</style>


