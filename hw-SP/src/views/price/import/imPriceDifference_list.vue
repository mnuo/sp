<template>
  <div>
    <el-row>
      <el-col :span="24" >
        <el-col :span="18" class="search-row">
          <div>&nbsp;</div>
        </el-col>
        <el-col :span="6" class="text-right">
          <div>
            <el-button type="primary" @click="dialogFormVisibleB=true">新 增</el-button>
            <!-- <el-button type="primary" @click="toAdd()">新 增</el-button> -->
            <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      border :span-method="objectOneMethod" 
      width="100%" :max-height="tableHeight">
      <el-table-column type="index" align="center" label="序号" width="40"></el-table-column>
      <el-table-column prop="platform" header-align="center" label="纸机" min-width="65"></el-table-column>
      <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="65"></el-table-column>
      <el-table-column prop="diffPrice" header-align="center" align="right" label="价差(元/吨)" min-width="65"></el-table-column>
      <el-table-column prop="isPolicy" header-align="center" align="center" label="享受政策" min-width="65">
        <template slot-scope="scope">
          {{scope.row.isPolicy==true?'是':'否'}}
        </template>
      </el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" min-width="80"></el-table-column>
      <el-table-column prop="endDate" header-align="center" align="center" label="失效时间" min-width="80"></el-table-column>
       <el-table-column prop="remark" header-align="center" label="备注" min-width="180"></el-table-column>
      <el-table-column prop="" header-align="center" align="center" label="操作" width="80">
        <template slot-scope="scope">
              <el-tooltip content="修改" placement="top">
                <el-button class="execute-button" icon="el-icon-edit" @click="goEdit(scope.row)"></el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <el-button class="execute-button" icon="el-icon-delete" @click="deleteOne(scope.row)"></el-button>
              </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogFormVisibleA" title="新增" width="60%" top="10vh" v-dialogDrag>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">纸机</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="platform" placeholder="纸机">
                    <el-option 
                        v-for="item in allPlatform"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">差价项目</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="itemName" @change="changeItemName" placeholder="差价项目">
                    <el-option 
                        v-for="item in differeceTypeArr"
                        :key="item.itemNo"
                        :label="item.itemName"
                        :value="item.itemName">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">差价</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="diffPrice" ></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">是否享受政策</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="isPolicy"  placeholder="是否享受政策">
                    <el-option 
                        v-for="item in policyTypes"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">生效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                   <el-date-picker
                   value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="startDate"
                    type="date"
                    placeholder="生效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">失效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                  <el-date-picker
                    value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="endDate"
                    type="date"
                    placeholder="失效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
         <el-row>
          <el-col :span="24">
            <el-col :span="24">
              <el-col :span="5">
                <div class="grid-content">备注</div>
              </el-col>
              <el-col :span="18" class="textarea-bottom">
                <div>
                  <el-input type="textarea" placeholder="备注" v-model="remark"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleA = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleB" title="新增" width="60%" top="10vh" v-dialogDrag>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">纸机</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="platformForBatch" @change="platformChangeForBatchAdd" placeholder="纸机">
                    <el-option 
                        v-for="item in allPlatform"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-col>
        </el-row>
      </div>
      <el-table
      :data="tableDataB" border>
      <el-table-column prop="itemName" header-align="center" align="center" label="价差项目" min-width="65">
        <template slot-scope="scope">
          <el-input v-model="scope.row.itemName" :readonly="true"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="diffPrice" header-align="center" align="right" label="价差(元/吨)" min-width="65">
         <template slot-scope="scope">
          <el-input v-model="scope.row.diffPrice" type="number"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="isPolicy" header-align="center" align="center" label="享受政策" min-width="65">
        <template slot-scope="scope">
           <el-select v-model="scope.row.isPolicy"  placeholder="是否享受政策">
              <el-option 
                  v-for="item in policyTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="startDate" header-align="center" align="center" label="生效时间" width="135">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.startDate" value-format="yyyy-MM-dd" type="date" placeholder="执行时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="endDate" header-align="center" align="center" label="失效时间" width="135">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.endDate" value-format="yyyy-MM-dd" type="date" placeholder="执行时间">
          </el-date-picker>
        </template>
      </el-table-column>
       <el-table-column prop="remark" header-align="center" label="备注" min-width="180">
          <template slot-scope="scope">
            <el-input v-model="scope.row.remark"></el-input>
          </template>
       </el-table-column>
      <!-- <el-table-column prop="" header-align="center" align="center" label="操作" width="80">
        <template slot-scope="scope">
              <el-tooltip content="删除" placement="top">
                <el-button class="execute-button" icon="el-icon-delete" @click="deleteNotSaveOne(scope.row)"></el-button>
              </el-tooltip>
        </template>
      </el-table-column> -->
    </el-table>
      <div slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleB = false">取 消</el-button>
        <el-button type="primary" @click="addBatch()">确 定</el-button>
      </div>     
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleE" title="修改" width="60%" top="10vh" v-dialogDrag>
      <div v-for="item in tableDataA" :key="item.fdId">
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>基本信息</div>
        </div>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">纸机</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select :disabled="true" v-model="item.platform" placeholder="纸机">
                    <el-option 
                        v-for="item in allPlatform"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">差价项目</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="item.itemName" :disabled="true" placeholder="差价项目">
                    <el-option 
                        v-for="item in differeceTypeArr"
                        :key="item.itemNo"
                        :label="item.itemName"
                        :value="item.itemName">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">差价</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-input type="number" v-model="item.diffPrice" ></el-input>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">是否享受政策</div>
              </el-col>
              <el-col :span="13">
                <div>
                  <el-select v-model="item.isPolicy"  placeholder="是否享受政策">
                    <el-option 
                        v-for="item in policyTypes"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                  </el-select> 
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">生效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                   <el-date-picker
                   value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="item.startDate"
                    type="date"
                    placeholder="生效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="10">
                <div class="grid-content">失效时间</div>
              </el-col>
              <el-col :span="13">
                <div class="addform-pick">
                  <el-date-picker
                    value-format="yyyy-MM-dd" format="yyyy-MM-dd"
                    v-model="item.endDate"
                    type="date"
                    placeholder="失效时间">
                  </el-date-picker>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
         <el-row>
          <el-col :span="24">
            <el-col :span="24">
              <el-col :span="5">
                <div class="grid-content">备注</div>
              </el-col>
              <el-col :span="18" class="textarea-bottom">
                <div>
                  <el-input type="textarea" placeholder="备注" v-model="item.remark"></el-input>
                </div>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
      </div>
      <div slot="footer" class="paddingtop20">
        <el-button @click="dialogFormVisibleE = false">取 消</el-button>
        <el-button type="primary" @click="editOne()">确 定</el-button>
      </div>     
    </el-dialog>
  </div>
</template>

<script>
  import Price_API from '@/api/import/price'
  import Const_Util from '@/utils/consts'
  import Vue from 'vue';

  export default {
    name: "price-list",
    data() {
      return {
        loading: false,
        tableHeight: 300,
        tableData: [],
        tableDataA: [],
        tableDataB: [],
        dialogFormVisibleA:false,
        dialogFormVisibleE:false,
        dialogFormVisibleB:false,
        differeceTypeArr:[],
        allPlatform: Const_Util.allPlatform,
        startDate:'',
        endDate: '',
        diffPrice: '',
        isPolicy: '',
        platform: '',
        itemName: '',
        itemNo: '',
        remark: '',
        policyTypes:[
          {value:false, label:'否'},
          {value:true, label:'是'}
        ],
        platformForBatch:'',
      }
    },
    components: {},
    methods: {
      findImPriceDifferenceList() {
        this.loading = true;
        Price_API.findImPriceDifferenceList().then(res => {
          this.tableData = res.data || [];
          this.loading = false;
          console.log(this.tableData)
        }).catch(err => {
          this.loading = false;
          this.$message({type: 'error', message: '加载列表异常：' + err.msg})
          console.log(JSON.stringify(err));
        })
        // var params = {
        //    platform:'高速纸机',
        //     page: 2,
        //     size: 30,
        //     pageNo: 2,
        //     pageSize: 30,
        // };
        // Price_API.findImPriceDifferencePage(JSON.stringify(params)).then(res => {
        //   this.tableData = res.data || [];
        //   this.loading = false;
        //   console.log(this.tableData)
        // }).catch(err => {
        //   this.loading = false;
        //   this.$message({type: 'error', message: '加载列表异常：' + err.msg})
        //   console.log(JSON.stringify(err));
        // })
      },
      platformChangeForBatchAdd:function(value){
        debugger;
        this.tableDataB = [];
        this.differeceTypeArr.forEach((item,index) => {
          let row = {
            itemNo: item.itemNo,
            itemName: item.itemName,
            platform: value,
            startDate:'',
            endDate:'',
            remark:'',
            diffPrice: '',
            isPolicy:'',
          }
          this.tableDataB.push(row);
        });
      },
      toAdd:function() {
        this.dialogFormVisibleA = true;
      },
      changeItemName: function(value){
        this.differeceTypeArr.forEach(element => {
          if(element.itemName == value) this.itemNo = element.itemNo;
        });
      },
      addOne() {
        let _this = this;
        let params = {
          startDate: this.startDate,
          endDate: this.endDate,
          diffPrice: this.diffPrice,
          isPolicy: this.isPolicy,
          platform: this.platform,
          itemName: this.itemName,
          itemNo: this.itemNo,
          remark: this.remark,
        };
        Price_API.saveOrUpdateOneImPriceDifference(params).then(res => {
          this.dialogFormVisibleA = false;
          _this.$message({
            type: res.success ? 'success' : 'info',
            message: res.success ? "保存成功" : res.msg
          })
          this.findImPriceDifferenceList();
        }).catch(err => {
          // this.dialogFormVisibleA = false;
          _this.$message({
            type: 'error',
            message: "保存异常：" + err.msg
          })
        })
      },
      addBatch() {
        let _this = this;
        let params = this.tableDataB || [];
        Price_API.saveOrUpdateBatchImPriceDifference(params).then(res => {
          this.dialogFormVisibleB = false;
          _this.$message({
            type: res.success ? 'success' : 'info',
            message: res.success ? "保存成功" : res.msg
          })
          this.findImPriceDifferenceList();
        }).catch(err => {
          // this.dialogFormVisibleB = false;
          _this.$message({
            type: 'error',
            message: "保存异常：" + err.msg
          })
        })
      },
      goEdit: function (item){
        this.tableDataA=[item];
        this.dialogFormVisibleE=true;
      },
      editOne: function(){
        var params = this.tableDataA[0];
        Price_API.saveOrUpdateOneImPriceDifference(params).then(res => {
          this.dialogFormVisibleE = false;
          this.$message({
            type: res.success ? 'success' : 'info',
            message: res.success ? "保存成功" : res.msg
          })
          this.findImPriceDifferenceList();
        }).catch(err => {
          // this.dialogFormVisibleE = false;
          this.$message({
            type: 'error',
            message: "保存异常：" + err.msg
          })
        });
      },
      deleteOne: function(item){
        this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let params = {fdId: item.fdId};
            this.loading=true;
            Price_API.deleteOneImPriceDifference(params).then(res=>{
              this.loading=false;
              this.$message({
                  type:res.success?'success':'info',
                  message:res.success?'删除成功！': '请求失败:' + res.msg,
              });
              this.findImPriceDifferenceList();
            }).catch(err => {
              this.loading=false;
              this.$message({type:'error',message:'请求异常:'+err.msg})
              console.log(err.errorCode);
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });          
        });
      },
      initDifferenceType: function(){
        let params={
          typename:'differenceType'
        };
        Price_API.findDictData(params).then(res=>{
          this.differeceTypeArr=res.data;
        }).catch(err=>{
          this.loading=false;
          this.$message({type: 'error', message: '请求异常：' + err.msg==null?err.message:err.msg});
          this.dialogFormVisibleA= false;
        })
      },
       //合并单元格
      setdates(arr){
        var spanOneArr = [1],
            concatOne = 0;
        this.tableData.forEach((item,index)=>{
          if(index != 0){
            if(item.platform === this.tableData[index - 1].platform){ //第2列需合并相同内容的判断条件
              spanOneArr[concatOne] += 1; spanOneArr.push(0);
            }else{
              spanOneArr.push(1);  concatOne = index;
            };
          }
        });
        return  {
          one: spanOneArr,
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
      },
    },
    created() {
    },
    mounted() {
      this.findImPriceDifferenceList();
      this.initDifferenceType();
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
}); 
</script>


<style lang="scss" scoped>
  .el-table td div {
    width: 100%;
  }
</style>
