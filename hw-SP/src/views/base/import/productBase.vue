<template>
  <div>
    <!-- <el-row>
      <el-col :span="24">
        <div><b>常规品、非常规品定义表</b></div>
      </el-col>
    </el-row> -->
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <el-button type="primary" @click="inputSentProductBaseToOa()">提交OA</el-button>
          <el-button type="primary" @click="add()">新增</el-button>
        </div>
      </el-col>
    </el-row>
    <el-table :data="tableData" :span-method="objectOneMethod" v-loading="loading" 
      element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
      stripe border  style="width:100%">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="50"></el-table-column>
    <el-table-column prop="classText" header-align="center" label="品类" min-width="100"></el-table-column> 
    <el-table-column prop="color" header-align="center" label="颜色" min-width="55"></el-table-column>
    <el-table-column prop="kindText" header-align="center" label="类型" min-width="90"></el-table-column>
    
    <el-table-column prop="ration" header-align="center" label="定量 (g/㎡)" :render-header="renderheader" min-width="75"></el-table-column>
    <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader" min-width="55"></el-table-column>
    <el-table-column prop="layer" header-align="center" label="层数" min-width="60"></el-table-column>
    <el-table-column prop="width" header-align="center" label="单幅幅宽 (mm)" :render-header="renderheader" min-width="80"></el-table-column>
    <el-table-column prop="totalWidth" header-align="center" label="总幅宽 (mm)" :render-header="renderheader" min-width="80"></el-table-column>
    <el-table-column prop="diameter" header-align="center" label="直径 (mm)" :render-header="renderheader" min-width="80"></el-table-column>
    <el-table-column prop="startDate" header-align="center" align="center"  label="执行时间" min-width="85">
      <template slot-scope="scope">
          {{scope.row.startDate!=null?scope.row.startDate.substring(0,10):''}}
      </template>
    </el-table-column>
    <el-table-column prop="statusText" header-align="center" label="状态" width="80"></el-table-column>
    <el-table-column prop="physicalNo" header-align="center" label="物理指标" width="100"></el-table-column>
    <el-table-column prop="remark" header-align="center" label="操作" min-width="55">
      <template slot-scope="scope" >
        <el-button @click="goProductBaseDeatil(scope.row)">修改</el-button>
        <!-- <a style="color:#0066cc" @click="goProductBaseDeatil(scope.row)">修改</a> -->
      </template>
    </el-table-column>
    </el-table>
    <el-dialog  :visible.sync="dialogFormVisibleA" style="width:1400px" title="新增"
    v-dialogDrag>
    <div style="border: 1px solid rgb(218, 215, 215);margin:10px 0px 10px 0px" > 
      <el-row>
        <el-col :span="24">
          <div style="width:100%;text-align:left;margin-bottom:15px;">&#12288;基础资料</div>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">产品品类</div></el-col>
            <el-col :span="13">
              <div style="margin-right:25px">
                <el-select v-model="classNo" style="margin-right:5px"  placeholder="产品品类">
                  <el-option 
                      v-for="item in allClass"
                      :key="item.id"
                      :label="item.name"
                      :value="item.classNo"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple">产品类型</div></el-col>
            <el-col :span="14">
              <div style="margin-right:25px">
                <el-select v-model="kindNo" style="margin-right:5px"  placeholder="产品类型">
                  <el-option 
                      v-for="item in allKind"
                      :key="item.id"
                      :label="item.name"
                      :value="item.kindNo"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">颜色</div></el-col>
            <el-col :span="13">
              <div style="margin-right:25px">
                <el-select v-model="color" style="margin-right:5px"  placeholder="颜色">
                  <el-option 
                      v-for="item in allColor"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple">层数</div></el-col>
            <el-col :span="14">
              <div style="margin-right:25px">
                <el-select v-model="layers" style="margin-right:5px" collapse-tags multiple placeholder="层数">
                  <el-option 
                      v-for="item in allLayers"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      </div>
      <div style="border: 1px solid rgb(218, 215, 215);margin-left:0px">
       <el-row>
        <el-col :span="24"  style="width:100%;">
          <div style="width:100%;text-align:left;margin-bottom: 15px;">&#12288;产品规格</div>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px" >定量范围(小-大)</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="minRation"></el-input>
              </div>
            </el-col>
            <el-col :span="1"><div>_</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="maxRation"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content bg-purple" > 幅宽范围(小-大)</div></el-col>
            <el-col :span="5">
              <div >
                <el-input v-model="minWidth"></el-input>
              </div>
            </el-col>
            <el-col :span="2"><div style="margin-right:10px">_</div></el-col>
            <el-col :span="5">
              <div  >
                <el-input v-model="maxWidth"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">幅宽总范围(小-大)</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="minTotalWidth"></el-input>
              </div>
            </el-col>
            <el-col :span="1"><div>_</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="maxTotalWidth"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content bg-purple">直径范围(小-大)</div></el-col>
            <el-col :span="5">
              <div >
                <el-input v-model="minDiameter"></el-input>
              </div>
            </el-col>
            <el-col :span="2"><div style="margin-right:10px">_</div></el-col>
            <el-col :span="5">
              <div  >
                <el-input v-model="maxDiameter"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">起皱率</div></el-col>
            <el-col :span="11">
              <div>
                <el-input v-model="wrinkleRate"></el-input>
              </div>
            </el-col>
            
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content bg-purple">物理指标号</div></el-col>
            <el-col :span="10">
              <div >
                <el-input v-model="physicalNo"></el-input>
              </div>
            </el-col>
            <el-col :span="1"><div><el-button type="primary" style="height:27px;margin-left:-5px"><b>...</b></el-button></div></el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">生效时间</div></el-col>
            <el-col :span="11">
              <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" placeholder="生效时间" v-model="startDate" style="width: 100%;"></el-date-picker>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple">失效时间</div></el-col>
            <el-col :span="12">
              <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" placeholder="失效时间" v-model="endDate" style="width:100%"></el-date-picker>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-col :span="5"><div class="grid-content bg-purple" style="margin-left:5px">备注</div></el-col>
          <el-col :span="18">
            <el-input  type="textarea" placeholder="请输入内容" v-model="remark" style="margin-bottom: 10px;"></el-input>
          </el-col>
        </el-col>
      </el-row>
      </div>
      <div slot="footer" >
        <el-button @click="dialogFormVisibleA = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
    <el-dialog  :visible.sync="dialogFormVisible" style="width:1400px" title="修改"
    v-dialogDrag>
    <div v-for="item in tableDataA" :key="item.fdId">
      <div style="border: 1px solid rgb(218, 215, 215);margin:10px 0px 10px 0px" > 
      <el-row>
        <el-col :span="24">
          <div style="width:100%;text-align:left;margin-bottom:15px;">&#12288;基础资料</div>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">产品品类</div></el-col>
            <el-col :span="13">
              <div style="margin-right:25px">
                <el-select v-model="item.classNo" style="margin-right:5px"  disabled="disabled" placeholder="产品品类">
                  <el-option 
                      v-for="item in allClass"
                      :key="item.id"
                      :label="item.name"
                      :value="item.classNo"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple">产品类型</div></el-col>
            <el-col :span="14">
              <div style="margin-right:25px">
                <el-select v-model="item.kindNo" style="margin-right:5px"  disabled="disabled" placeholder="产品类型">
                  <el-option 
                      v-for="item in allKind"
                      :key="item.id"
                      :label="item.name"
                      :value="item.kindNo"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">颜色</div></el-col>
            <el-col :span="13">
              <div style="margin-right:25px">
                <el-select v-model="item.color" style="margin-right:5px" disabled="disabled" placeholder="颜色">
                  <el-option 
                      v-for="item in allColor"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple">层数</div></el-col>
            <el-col :span="14">
              <div style="margin-right:25px">
                <el-select v-model="layers" style="margin-right:5px" collapse-tags multiple  disabled="disabled" placeholder="层数">
                  <el-option 
                      v-for="item in allLayers"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      </div>
      <div style="border: 1px solid rgb(218, 215, 215);margin-left:0px">
       <el-row>
        <el-col :span="24"  style="width:100%;">
          <div style="width:100%;text-align:left;margin-bottom: 15px;">&#12288;产品规格</div>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px" >定量范围(小-大)</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="item.minRation"></el-input>
              </div>
            </el-col>
            <el-col :span="1"><div>_</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="item.maxRation"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content bg-purple" > 幅宽范围(小-大)</div></el-col>
            <el-col :span="5">
              <div >
                <el-input v-model="item.minWidth"></el-input>
              </div>
            </el-col>
            <el-col :span="2"><div style="margin-right:10px">_</div></el-col>
            <el-col :span="5">
              <div  >
                <el-input v-model="item.maxWidth"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">幅宽总范围(小-大)</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="item.minTotalWidth"></el-input>
              </div>
            </el-col>
            <el-col :span="1"><div>_</div></el-col>
            <el-col :span="5">
              <div>
                <el-input v-model="item.maxTotalWidth"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content bg-purple">直径范围(小-大)</div></el-col>
            <el-col :span="5">
              <div >
                <el-input v-model="item.minDiameter"></el-input>
              </div>
            </el-col>
            <el-col :span="2"><div style="margin-right:10px">_</div></el-col>
            <el-col :span="5">
              <div  >
                <el-input v-model="item.maxDiameter"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">起皱率</div></el-col>
            <el-col :span="11">
              <div>
                <el-input v-model="item.wrinkleRate"></el-input>
              </div>
            </el-col>
            
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content bg-purple">物理指标号</div></el-col>
            <el-col :span="10">
              <div >
                <el-input v-model="item.physicalNo"></el-input>
              </div>
            </el-col>
            <el-col :span="1"><div><el-button type="primary" style="height:27px;margin-left:-5px"><b>...</b></el-button></div></el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-col :span="12" style="width:50%">
            <el-col :span="10"><div class="grid-content bg-purple" style="margin-left:5px">生效时间</div></el-col>
            <el-col :span="11">
              <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" placeholder="生效时间" v-model="item.startDate" style="width: 100%;"></el-date-picker>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content bg-purple">失效时间</div></el-col>
            <el-col :span="12">
              <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" placeholder="失效时间" v-model="item.endDate" style="width:100%"></el-date-picker>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-col :span="5"><div class="grid-content bg-purple" style="margin-left:5px">备注</div></el-col>
          <el-col :span="18">
            <el-input  type="textarea" placeholder="请输入内容" v-model="item.remark" style="margin-bottom: 10px;"></el-input>
          </el-col>
        </el-col>
      </el-row>
      </div>
      </div>
      <div slot="footer" >
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateProductBase()">确 定</el-button>
      </div>
     
    </el-dialog>

  </div>
  
</template>
<script>
import Base_API from '@/api/import/base';
import Vue from 'vue';
export default {
  name: "price-list",
  data(){
    return{
      loading:false,
      tableData:[],
      tableDataA:[],
      arr1:[],
      dialogFormVisible: false,
      dialogFormVisibleA:false,
      formLabelWidth: '120px',
      allClass:[],
      allKind:[],
      allColor:[
        {
          value: '白色',
          label: '白色',
        },
        {
          value: '本色',
          label: '本色',
        }
      ],
      allLayers:[{
        value:'2',
        label:'2',
      },{
        value:'3',
        label:'3',
      },{
        value:'4',
        label:'4',
      }],
      classNo:'',
      kindNo:'',
      color:'',
      minRation:'',
      maxRation:'',
      minWidth:'',
      maxWidth:'',
      minTotalWidth:'',
      maxTotalWidth:'',
      minDiameter:'',
      maxDiameter:'',
      physicalNo:'',
      wrinkleRate:'',
      layers:[],
      startDate:'',
      endDate:'',
      remark:'',
    }
  },
  components:{},
  methods:{
    renderheader(h,{column,$index}){
      return h('span', {}, [
        h('span', {}, column.label.split('</br>')[0]),
        h('br'),
        h('span', {}, column.label.split('</br>')[1])
      ])
    },

    //初始化数据
    initPage(){
      this.findAllProductBase();
      this.findAllProductClass();
      this.findAllProductKind();
    },
    findAllProductBase(){
      this.loading=true;
      var params='Import'
      Base_API.findAllProductBase(params).then(res =>{
        this.tableData=res.data||[];
        console.log(this.tableData);
        this.loading=false;
        this.setdates(this.tableData);
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:'请求异常'+err.msg});
        console.log(err.errorCode);
      });
    },
    //所有品类
    findAllProductClass(){
      this.loading=true;
      var params='Import'
      Base_API.findAllProductClass(params).then(res =>{
        this.allClass=res.data||[];
        console.log(this.allClass);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$messaget({type:'error',message:'请求异常'+err.msg});
        console.log(err.errorCode);
      });
    },
    //所有产品类型
    findAllProductKind(){
      this.loading=true;
      var params='Import'
      Base_API.findAllProductKind(params).then(res=>{
        this.allKind=res.data||[];
        console.log(this.allKind);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type: 'error', message: '请求异常：' + err.msg})
        console.log(err.errorCode);
      })
    },
    //合并单元格
    setdates(arr){
      var spanOneArr = [],
          spanTwoArr = [],
          spanThreeArr=[],
          concatOne = 0,
          concatTwo = 0,
          concatThree=0;
      this.tableData.forEach((item,index)=>{
        if(index === 0){
          spanOneArr.push(1);
          spanTwoArr.push(1);
          spanThreeArr.push(1);
        }else{
          if(item.classText === this.tableData[index - 1].classText){ //第2列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1;
            spanOneArr.push(0);
          }else{
            spanOneArr.push(1);
            concatOne = index;
          };
          if(item.color === this.tableData[index - 1].color){//第3列需合并相同内容的判断条件
            spanTwoArr[concatTwo] += 1;
            spanTwoArr.push(0);
          }else{
            spanTwoArr.push(1);
            concatTwo = index;
          };
          if(item.kindText === this.tableData[index - 1].kindText){//第3列需合并相同内容的判断条件
            spanThreeArr[concatThree] += 1;
            spanThreeArr.push(0);
          }else{
            spanThreeArr.push(1);
            concatThree = index;
          };
        }
      });
        return  {
        one: spanOneArr,
        two: spanTwoArr,
        three: spanThreeArr,
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
      // if(columnIndex === 2) {
      //   const _row = (this.setdates(this.tableData).two)[rowIndex];
      //   const _col = _row > 0 ? 1 : 0;
      //   return {
      //     rowspan: _row,
      //     colspan: _col
      //   };
      // }
      if(columnIndex === 3) {
        const _row = (this.setdates(this.tableData).three)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
    //提交OA
    inputSentProductBaseToOa(){
      this.$confirm('确定提交OA吗？','提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'警告',
      }).then(()=>{
        Base_API.inputSentProductBaseToOa().then(res=>{
          this.findAllProductBase();
          this.$message({
            type:res.success?'success':'info',
            message:res.success?'提交成功':res.msg
          })
        }).catch(err=>{
          this.$message({type:'error',message:err.msg});
        })
      })
    },
    //跳转到修改页面
    goProductBaseDeatil(item){
      console.log(item);
      var _this=this;
      _this.layers=[item.layer];
      _this.tableDataA=[item];
       console.log(_this.tableDataA);
      if(item.status===10){
        _this.dialogFormVisible = true;
      }
      if(item.status===30){
        _this.$message({
          message: '该定义已审批，不能修改',
          type: 'warning'
        });
      }
      if(item.status===20){
        _this.$message({
          message: '该定义正在审批，不能修改',
          type: 'warning'
        });
      }
      if(item.status===11){
        _this.$message({
          message: '该定义已提交OA，不能修改',
          type: 'warning'
        });
      }
    },
    //修改
    updateProductBase(){
       this.tableDataA[0].layers=this.layers.join(',');
       debugger;
      if(!!this.tableDataA[0].startDate){
        this.tableDataA[0].startDate = this.tableDataA[0].startDate.substring(0,10) + " 00:00:00";
      }
      if(!!this.tableDataA[0].endDate){
        this.tableDataA[0].endDate = this.tableDataA[0].endDate.substring(0,10) + " 00:00:00";
      }

       var params=this.tableDataA[0];
       console.log(params);
      Base_API.updateProductBase(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
        })
        this.findAllProductBase();
        this.dialogFormVisible= false;
      }).catch(err=>{
        this.$message({type:'error',message:err.msg});
        this.dialogFormVisible= false;
      })
    },
    //新增
    add(){
      this.layers=[];
      this.dialogFormVisibleA=true;
    },
    addOne(){
      var layersA=this.layers.join(",");
      var startDate, endDate;
      if(!!this.startDate){
        startDate =this.startDate + " 00:00:00";
      }
      if(!!this.endDate){
        endDate = this.endDate + " 00:00:00";
      }
      debugger;
      var params={
        classNo:this.classNo,
        kindNo:this.kindNo,
        color:this.color,
        minRation:this.minRation,
        maxRation:this.maxRation,
        minWidth:this.minWidth,
        maxWidth:this.maxWidth,
        minTotalWidth:this.minTotalWidth,
        maxTotalWidth:this.maxTotalWidth,
        minDiameter:this.minDiameter,
        maxDiameter:this.maxDiameter,
        layer:layersA,
        startDate:startDate,
        endDate:endDate,
        physicalNo:this.physicalNo,
        wrinkleRate:this.wrinkleRate,
        remark:this.remark,
        platform:"Import",
      };
      Base_API.addOneProductBase(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功，请提交OA！':res.msg,
          })
          this.findAllProductBase();
          this.dialogFormVisibleA= false;

      }).catch(err=>{
        this.$message({type:'error',message:err.msg})
        this.dialogFormVisibleA= false;
      })
    },
  },

  
  created() {

  },
  mounted() {
    this.initPage();
  }
}
 

 
// v-dialogDrag: 弹窗拖拽
Vue.directive('dialogDrag', {
    bind(el, binding, vnode, oldVnode) {
        const dialogHeaderEl = el.querySelector('.el-dialog__header');
        const dragDom = el.querySelector('.el-dialog');
        dialogHeaderEl.style.cursor = 'move';
 
        // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
        const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null);
        
        dialogHeaderEl.onmousedown = (e) => {
            // 鼠标按下，计算当前元素距离可视区的距离
            const disX = e.clientX - dialogHeaderEl.offsetLeft;
            const disY = e.clientY - dialogHeaderEl.offsetTop;
            
            // 获取到的值带px 正则匹配替换
            let styL, styT;
 
            // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
            if(sty.left.includes('%')) {
                styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100);
                styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100);
            }else {
                styL = +sty.left.replace(/\px/g, '');
                styT = +sty.top.replace(/\px/g, '');
            };
            
            document.onmousemove = function (e) {
                // 通过事件委托，计算移动的距离 
                const l = e.clientX - disX;
                const t = e.clientY - disY;
 
                // 移动当前元素  
                dragDom.style.left = `${l + styL}px`;
                dragDom.style.top = `${t + styT}px`;
 
                //将此时的位置传出去
                //binding.value({x:e.pageX,y:e.pageY})
            };
 
            document.onmouseup = function (e) {
                document.onmousemove = null;
                document.onmouseup = null;
            };
        }  
    }
})
 


</script>
