<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>高速纸机常规、非常规产品价格调整</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <el-button type="primary">说明</el-button>
          <el-button type="primary" @click="dialogProject=true">选择产品</el-button>
          <el-button type="primary" @click="matching()">提交</el-button>
          <el-button type="primary" @click="$router.go(-1)">返回上一级</el-button>
        </div>
      </el-col>
    </el-row>
      <el-table
        :data="tableData" v-loading="loading" element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        stripe border
        style="width: 100%">
        <el-table-column type="index" header-align="center" label="序号" width="30"></el-table-column>
        <el-table-column prop="classText" header-align="center" label="品类" align="center" width="69"></el-table-column>
        <el-table-column prop="color" header-align="center" label="颜色" min-width="45"></el-table-column>
        <el-table-column prop="kindText" header-align="center" label="产品类型" width="70"></el-table-column>
        <el-table-column prop="unit" header-align="center" label="定价单位" width="70"></el-table-column>
        <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader"
                        width="75"></el-table-column>
        <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader"
                          width="55"></el-table-column>
        <el-table-column prop="layers" header-align="center" label="层数" width="55"></el-table-column>
        <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader"
                        min-width="75"></el-table-column>
        <el-table-column prop="diameter" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader"
                        min-width="70"></el-table-column>
        <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                        width="55"></el-table-column>
        <el-table-column prop="adj_remark" header-align="center" label="调整类型" align="center" :render-header="renderheader"
        width="45"></el-table-column>
        <el-table-column prop="adj_sourcePrice" header-align="center" label="调整前价</br>格(元/吨)" :render-header="renderheader"
                        min-width="70"></el-table-column>
        <el-table-column prop="addPrice" header-align="center" label="调整幅度</br>(元/吨)" min-width="70" :render-header="renderheader"></el-table-column>
        <el-table-column prop="price" header-align="center" label="调整后价</br>格(元/吨)" min-width="70" :render-header="renderheader"></el-table-column>
        <el-table-column prop="startDate" header-align="center" label="执行时间" width="83"></el-table-column>
        <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" width="60"></el-table-column>
        <el-table-column prop="remark" header-align="center" label="备注" align="center" width="70"></el-table-column>
        <el-table-column header-align="center" align="center" label="调整" width="45">
          <template slot-scope="scope">
            <el-button  @click="adjust(scope.row)" style="color:#29d;margin-left:-5px" icon="el-icon-edit"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div>
        <el-dialog :visible.sync="dialogProject" v-dialogDrag  title="选择产品" width="1250px">
          <el-table
            :data="tableDataA" v-loading="loading" element-loading-text="拼命加载中"
            element-loading-spinner="el-icon-loading"  @selection-change="handleSelectionChange"
            stripe border
            style="width: 100%">
           <el-table-column type="selection" width="35" title="全选"></el-table-column>
            <el-table-column prop="classText" header-align="center" label="品类" align="center" min-width="70"></el-table-column>
            <el-table-column prop="color" header-align="center" label="颜色" align="center" min-width="45"></el-table-column>
            <el-table-column prop="kindText" header-align="center" label="产品类型" align="center" width="70"></el-table-column>
            <el-table-column prop="unit" header-align="center" label="定价单位" align="center" width="70"></el-table-column>
            <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader"
                            width="75"></el-table-column>
            <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader"
                              width="55"></el-table-column>
            <el-table-column prop="layers" header-align="center" label="层数" align="center" width="55"></el-table-column>
            <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader"
                            min-width="75"></el-table-column>
            <el-table-column prop="diameter" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader"
                            min-width="70"></el-table-column>
            <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                            width="55"></el-table-column>
            <el-table-column prop="adj_remark" header-align="center" label="调整类型" align="center" :render-header="renderheader"
            width="55"></el-table-column>
            <el-table-column prop="adj_sourcePrice" header-align="center" label="调整前价</br>格(元/吨)" :render-header="renderheader"
                            min-width="70"></el-table-column>
            <el-table-column prop="addPrice" header-align="center" label="调整幅度</br>(元/吨)" min-width="70" :render-header="renderheader"></el-table-column>
            <el-table-column prop="price" header-align="center" label="调整后价</br>格(元/吨)" min-width="70" :render-header="renderheader"></el-table-column>
            <el-table-column prop="startDate" header-align="center" label="执行时间" min-width="83"></el-table-column>
            <el-table-column prop="isPolicyText" header-align="center" align="center" label="是否享受政策" width="60"></el-table-column>
            <el-table-column prop="remark" header-align="center" label="备注" min-width="100"></el-table-column>
          </el-table>
          <span slot="footer">
            <el-button type="info">取消</el-button>
            <el-button type="primary" @click="select()">确定</el-button>
        </span>
        </el-dialog>
      </div>
      <div>
        <el-dialog :visible.sync="dialogAdjust" title="调整" v-dialogDrag width="26%" center>
          <el-row>
            <el-col :span="6" class="text-left">
              加价幅度：
            </el-col>
            <el-col :span="12" class="text-left">
              <el-input v-model="tableDataB.addPrice" style="width:140px"></el-input>
            </el-col>
            <el-col :span="6" class="text-left">
            （元/吨）
            </el-col>
          </el-row>
          <el-row style="margin-top:10px">
            <el-col :span="6" class="text-left">
              生效时间：
            </el-col>
            <el-col :span="12" class="text-left">
              <el-date-picker type="date" placeholder="生效时间" value-format="yyyy-MM-dd" v-model="tableDataB.startDate"></el-date-picker>
            </el-col>
          </el-row>
          <span slot="footer" >
            <el-button type="primary" @click="doSave()">确定</el-button>
          </span>
        </el-dialog>
      </div>
      <div>
        <el-dialog :visible.sync="dialogSuccess" v-dialogDrag title="调整" width="25%">
          <span>小幅宽产品加价调整双人录入匹配成功！</span>
          <span slot="footer">
            <el-button type="primary" @click="$router.go(-1)">确定</el-button>
          </span>
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
        ruleForm: [],
        tableData: [],
        tableDataA:[],
        tableDataB:[],
        dialogProject:false,
        dialogAdjust:false,
        dialogSuccess:false,
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
      queryImPriceList() {
        
        
      },
      //保存
      doSave(){
        var params=this.tableDataB;
        params.sourcePrice=this.tableDataB.adj_sourcePrice;
        if(parseInt(params.addPrice)>0){
          params.adj_remark="涨价"
        }
        if(parseInt(params.addPrice)==0){

        }
        params.price=parseInt(params.adj_sourcePrice)+parseInt(params.addPrice);
        Price_API.addOneEdit_imPriceAdjust(params).then(res=>{
          this.$message({
          type:res.success?'res.success':'res.info',
          message:res.success?'录入成功':res.msg,
        });
        // console.log("ta",this.tableData)
        this.dialogAdjust=false;
        // debugger
        this.findNewHistory();
        for(let i=0;i<this.tableData.length;i++){
          for(let j=0;j<this.tableDataA.length;j++){
            if(this.tableData[i].importPriceId==this.tableDataA[j].importPriceId){
              this.tableData[i]=this.tableDataA[j];
               console.log("oo",this.tableData,this.tableDataA)
            }
          }
        }
       
      }).catch(err=>{
        this.$message({
          type:'error',message:err.msg,
        });
        this.dialogAdjust=false;
        })
      },
      //跳转
      findNewHistory(){
        // this.dialogProject=true;
        Price_API.findNewHistory_imPriceAdjust().then(res=>{
          this.tableDataA=res.data||[];
        })
      },
     //选择
      handleSelectionChange(val) {
        this.multipleSelection = val;
        // console.log(this.multipleSelection)
      },
      handleSelectionProdChange(val) {
        this.selectedProd = val;
      },
      select(){
        this.tableData=this.multipleSelection;
        console.log(this.tableData)
        this.dialogProject=false;
        this.editResult="待匹配"
      },
      adjust(item){
        this.dialogAdjust=true;
        this.tableDataB=item;
      },
      matching(){
        this.findNewHistory();
        for(let i=0;i<this.tableData.length;i++){
          for(let j=0;j<this.tableDataA.length;j++){
            if(this.tableData[i].importPriceId==this.tableDataA[j].importPriceId){
              this.tableData[i]=this.tableDataA[j];
            }
          }
        }
        var params=this.tableData;
        for(let i=0;i<params.length;i++){
          params[i].role=params[i].adj_role;
        }
        // debugger
        Price_API.matching_imPriceAdjust(params).then(res=>{
          this.$message({
            type:res.success?'res.success':'res.info',
            message:res.success?'匹配成功':res.msg,
          })
        }).catch(err=>{
          this.$message({type:'error',message:err.msg})
        })
      },
    },
    created() {

    },
    mounted() {
      this.queryImPriceList();
      this.findNewHistory();
    }
  }
</script>

<style lang="scss" scoped>

</style>
