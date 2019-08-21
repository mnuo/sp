<template>
  <div>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <el-button type="primary">说明</el-button>
          <!-- <el-button type="primary" @click="dialogProject=true">选择产品</el-button> -->
          <el-button type="danger" @click="doDelete()">删除</el-button>
          <el-button type="primary" @click="doSave()">保存</el-button>
          <el-button type="primary" @click="submit()">提交</el-button>
          <el-button type="primary" @click="$router.go(-1)">返回上一级</el-button>
        </div>
      </el-col>
    </el-row>
      <el-table
        :data="tableData" v-loading="loading" element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        border :max-height="tableHeight"
        style="width: 100%">
        <el-table-column type="index" header-align="center" label="序号" width="25"></el-table-column>
        <el-table-column prop="platform" header-align="center" label="纸机" width="70"></el-table-column>
        <el-table-column prop="color" header-align="center" label="颜色" width="40"></el-table-column>
        <el-table-column prop="classText" header-align="center" label="品类" align="center" width="95"></el-table-column>
        <el-table-column prop="kindText" header-align="center" label="产品类型" width="60"></el-table-column>
        <el-table-column prop="unit" header-align="center" label="定价单位" width="60"></el-table-column>
        <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader" width="75"></el-table-column>
        <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader" width="65"></el-table-column>
        <!-- <el-table-column prop="layers" header-align="center" label="层数" width="55"></el-table-column> -->
        <!-- <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader" min-width="75"></el-table-column> -->
        <el-table-column prop="width" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader" width="80"></el-table-column>
        <!-- <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader" width="55"></el-table-column> -->
        <!-- <el-table-column prop="adj_remark" header-align="center" label="调整类型" align="center" :render-header="renderheader" width="45"></el-table-column> -->
        <el-table-column prop="price" header-align="center" label="调整前价</br>格(元/吨)" :render-header="renderheader" width="60"></el-table-column>
        <el-table-column prop="adj_addPrice" header-align="center" label="调整幅度</br>(元/吨)" width="65" :render-header="renderheader">
          <template slot-scope="scope">
              <el-input type="number" @keyup.native="adjPriceCalc(scope.$index,scope.row)" v-model="scope.row.adj_addPrice" placeholder="调整幅度"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="adj_price" header-align="center" label="调整后价</br>格(元/吨)" width="82" :render-header="renderheader">
          <template slot-scope="scope">
              <el-input type="number" v-model="scope.row.adj_price" placeholder="调整后金额"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="startDate" header-align="center" label="调整后执行时间" width="135">
          <template slot-scope="scope">
            <el-date-picker
            v-model="scope.row.adj_startDate" value-format="yyyy-MM-dd" type="date" placeholder="执行时间">
          </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column prop="endDate" header-align="center" label="调整后结束时间" width="135">
          <template slot-scope="scope">
            <el-date-picker
            v-model="scope.row.adj_endDate" value-format="yyyy-MM-dd" type="date" placeholder="结束时间">
          </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" width="50"></el-table-column>
        <el-table-column prop="adj_remark" header-align="center" label="备注" align="center" min-width="70">
          <template slot-scope="scope">
            <el-input v-model="scope.row.adj_remark" placeholder="请输入备注"></el-input>
          </template>
        </el-table-column>
        <!-- <el-table-column header-align="center" align="center" label="调整" width="45">
          <template slot-scope="scope">
            <el-button  @click="adjust(scope.row)" style="color:#29d;margin-left:-5px" icon="el-icon-edit"></el-button>
          </template>
        </el-table-column> -->
      </el-table>
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
        tableData: [],
        tableHeight: 300,
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
      adjPriceCalc: function(index, row){
        if(!isNaN(row.adj_addPrice) && !isNaN(row.price)){
          debugger;
          row.adj_price = Number(row.price) + Number(row.adj_addPrice);
        }
      },
      queryImPriceList() {
        var params = {
          status:99
        };
         Price_API.findImPriceListForAdjust(params).then(res=>{
           this.tableData=res.data||[];
        }).catch(err=>{
          this.$message({type:'error',message:err.msg})
        })
      },
      //保存
      doSave(){
        // var params=this.tableDataB;
        // params.sourcePrice=this.tableDataB.adj_sourcePrice;
        // if(parseInt(params.addPrice)>0){
        //   params.adj_remark="涨价"
        // }
        // if(parseInt(params.addPrice)==0){

        // }
        // params.price=parseInt(params.adj_sourcePrice)+parseInt(params.addPrice);
        // Price_API.addOneEdit_imPriceAdjust(params).then(res=>{
        //   this.$message({
        //   type:res.success?'res.success':'res.info',
        //   message:res.success?'录入成功':res.msg,
        // });
        // // console.log("ta",this.tableData)
        // this.dialogAdjust=false;
        // // debugger
        // this.findNewHistory();
        // for(let i=0;i<this.tableData.length;i++){
        //   for(let j=0;j<this.tableDataA.length;j++){
        //     if(this.tableData[i].importPriceId==this.tableDataA[j].importPriceId){
        //       this.tableData[i]=this.tableDataA[j];
        //        console.log("oo",this.tableData,this.tableDataA)
        //     }
        //   }
        // }
        let params = this.tableData || [];
        Price_API.addImPriceAdjustSome(params).then(res=>{
          this.$message({
            type:res.success?'success':'info',
            message:res.success?'保存成功':res.msg,
          });
          this.queryImPriceList();
        }).catch(err=>{
          this.$message({
            type:'error',message:err.msg,
          });
        })
      },
      //跳转
      findNewHistory(){
        // this.dialogProject=true;
        Price_API.findNewHistory_imPriceAdjust().then(res=>{
          this.tableDataA=res.data||[];
        })
      },
      submit:function(){
        let params = this.tableData || [];
        Price_API.submitImPriceAdjust(params).then(res=>{
          this.$message({
            type:res.success?'success':'info',
            message:res.success?'提交成功':res.msg,
          });
          this.queryImPriceList();
        }).catch(err=>{
          this.$message({
            type:'error',message:err.msg,
          });
        })
      },
      doDelete:function(){
        if(this.tableData.length == 0 || (this.tableData.length>0 && this.tableData[0].adj_fdId==null)){
          this.$message({
            type:'info',
            message:'没有可以删除的数据',
          });
          return;
        }
        
        Price_API.deleteImPriceAdjustSome(this.tableData).then(res=>{
          this.$message({
            type:res.success?'success':'info',
            message:res.success?'删除成功':res.msg,
          });
          this.queryImPriceList();
        }).catch(err=>{
          this.$message({
            type:'error',message:err.msg,
          });
        })
      },
    },
    created() {

    },
    mounted() {
      this.queryImPriceList();
      this.$nextTick(function () {
        this.tableHeight = window.innerHeight - 120;//表头距离浏览器顶部88+分页62+距离底部20=170+10(浮动系数)
        // 监听窗口大小变化
        let self = this;
        window.onresize = function() {
            self.tableHeight = window.innerHeight - 120;
        }
      });
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
