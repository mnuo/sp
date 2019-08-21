<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="12" class="text-right">
        <div>
          <el-button type="primary">说明</el-button>
          <el-button type="primary" @click="showProdListForm()">增加产品</el-button>
          <el-button type="danger" @click="inputDeleteOne()">删除行</el-button>
          <el-button type="primary" @click="doSave()">保存</el-button>
          <el-button type="primary" @click="sendToOA()">提交OA</el-button>
          <el-button type="danger" @click="$router.go(-1)">返回上一级</el-button>
        </div>
      </el-col>
    </el-row>
    <!-- fixed="left" -->
    <el-table
      :data="tableData" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
      stripe border ref="multipleTable" @selection-change="handleSelectionChange"
      style="width: 100%">
      <el-table-column align="center" type="selection" width="25" title="全选(用于删除行)"></el-table-column>
      <el-table-column  type="index" header-align="center" align="center" label="序号"
                       width="25"></el-table-column>
      <el-table-column  prop="platform" header-align="center" label="纸机" width="65"></el-table-column>
      <el-table-column  prop="color" header-align="center" label="颜色" width="40"></el-table-column>
      <el-table-column  prop="classText" header-align="center" label="品类" min-width="95"></el-table-column>
      <el-table-column  prop="kindText" header-align="center" label="产品类型" width="65"></el-table-column>
      <el-table-column prop="unit" header-align="center" label="定价单位" width="60"></el-table-column>
      <el-table-column prop="ration" header-align="center" label="定量</br>(g/㎡)" :render-header="renderheader"
                       width="65"></el-table-column>
      <el-table-column prop="wrinkleRate" header-align="center" label="起皱率(%)" :render-header="renderheader"
                       width="55"></el-table-column>
      <!-- <el-table-column prop="layers" header-align="center" label="层数" width="60"></el-table-column> -->
      <!-- <el-table-column prop="width" header-align="center" label="分切幅宽合计(mm)" :render-header="renderheader" -->
                       <!-- min-width="80"></el-table-column> -->
      <el-table-column prop="width" header-align="center" label="单幅幅宽</br>(mm)" :render-header="renderheader"
                       width="60"></el-table-column>
      <!-- <el-table-column prop="addPrice" header-align="center" label="直径</br>(mm)" :render-header="renderheader"
                       width="55"></el-table-column> -->
      <el-table-column header-align="center" label="增加价格</br>(元/吨)"  :render-header="renderheader"  width="65">
        <template slot-scope="scope">
          <el-input type="number" v-model="scope.row.addPrice" controls-position="right">
          </el-input>
        </template>
      </el-table-column>
      <el-table-column prop="price" header-align="center" label="执行价格" width="80">
        <template slot-scope="scope">
          <el-input type="number" v-model="scope.row.price" controls-position="right">
          </el-input>
        </template>
      </el-table-column>
      <el-table-column prop="startDate" header-align="center" label="执行时间" min-width="135">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.startDate" value-format="yyyy-MM-dd" type="date" placeholder="执行时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="endDate" header-align="center" label="结束时间" min-width="135">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.endDate" value-format="yyyy-MM-dd" type="date" placeholder="结束时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="isPolicyText" header-align="center" label="是否享受政策" width="65">
        <template slot-scope="scope">
          <el-select v-model="scope.row.isPolicyText">
            <el-option value="是">
              <span style="float: left">是</span>
            </el-option>
            <el-option value="否"><span style="float: left">否</span></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="statusText" header-align="center" align="center" label="状态" width="50"></el-table-column>
      <el-table-column prop="remark" header-align="center" label="备注" min-width="100">
        <template slot-scope="scope">
          <el-input v-model="scope.row.remark" placeholder="请输入备注"></el-input>
        </template>
      </el-table-column>
      
    </el-table>
    <el-dialog title="产品列表"
               :visible.sync="dialogProdVisible" v-dialogDrag>
      <el-table :data="prodData" v-loading="loading" element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading"
                stripe border ref="multipleTable" @selection-change="handleSelectionProdChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column type="index" header-align="center" label="序号" width="45"></el-table-column>
        <el-table-column prop="platform" header-align="center" label="纸机" min-width="100"></el-table-column>
        <el-table-column prop="color" header-align="center" label="颜色" width="50"></el-table-column>
        <el-table-column prop="classText" header-align="center" label="品类" min-width="100"></el-table-column>
        <el-table-column prop="kindText" header-align="center" label="产品类型" min-width="80"></el-table-column>
        <el-table-column prop="ration" header-align="center" label="定量" min-width="80"></el-table-column>
        <!-- <el-table-column prop="layers" header-align="center" label="层数" min-width="70"></el-table-column> -->
        <el-table-column prop="width" header-align="center" label="幅宽(mm)" min-width="80"></el-table-column>
        <!-- <el-table-column prop="totalWidth" header-align="center" label="总幅宽" min-width="80"></el-table-column> -->
      </el-table>
      <div slot="footer" class="paddingtop20">
        <el-button @click="selectProdConfirm()">确定</el-button>
        <el-button @click="dialogProdVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Price_API from '@/api/import/price'
  import Vue from 'vue';
  export default {
    name: "price-list",
    data() {
      return {
        loading: false,
        dialogProdVisible: false,
        ruleForm: [],
        tableData: [],
        prodData: [],
        multipleSelection: [],
        selectedProd: []
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
        this.inputQueryList();
      },
      inputQueryList() {
        this.loading=true;
        Price_API.inputQueryList().then(res => {
          this.tableData = res.data || [];
          this.loading=false;
          console.log(this.tableData)
        }).catch(err => {
          this.loading=false;
          this.$message({type: 'error', message: "加载列表异常：" + err.msg})
          console.log(JSON.stringify(err));
        });
      },
      selectProdConfirm() {
        if (this.selectedProd.length <= 0) {
          this.$message({type: 'warning', message: '请选择产品'})
          return;
        }
        let prodBaseIds = this.selectedProd.map(item => item.fdId).join(';');
        Price_API.inputSelectProd(prodBaseIds).then(res => {
          this.initPage();
          this.dialogProdVisible = false;
        }).catch(err => {
          this.$message({type: 'warning', message: err.msg})
          console.log(JSON.stringify(err));
        });
      },
      inputQuerySelect() {
        Price_API.inputQuerySelect().then(res => {
          this.prodData = res.data || [];
          console.log(this.tableData)
        }).catch(err => {
          this.$message({type: 'error', message: '请求异常：' + err.msg})
          console.log(JSON.stringify(err));
        });
      },
      showProdListForm() {
        this.dialogProdVisible = true;
        this.inputQuerySelect();
      },
      inputDeleteOne() {
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
              Price_API.inputDeleteOne(item.fdId).then(res => {
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
        let _this = this;
        Price_API.inputUpdateSome(params).then(res => {
          this.initPage();
          this.$message({
            type: res.success ? 'success' : 'info',
            message: res.success ? "保存成功" : res.msg
          })
        }).catch(err => {
          this.$message({
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
            Price_API.inputSentToOa().then(res => {
              this.initPage();
              this.$message({
                type: res.success ? 'success' : 'info'
                , message: res.success ? '提交成功' : res.msg
              })
            }).catch(err => {
              this.$message(
                {
                  type: 'error'
                  , message: err.msg
                }
              )
            })

          }
        ).catch((ex) => {
          console.log(ex);
          this.$message({
            type: 'info',
            message: '已取消提交OA'
          })
        })
        console.log(this.multipleSelection)
      },

      //选择
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleSelectionProdChange(val) {
        this.selectedProd = val;
      }
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
});
</script>

<style lang="scss" scoped>
  .el-table td div {
    width: 100%;
    /*padding-left: 0px!important;*/
    /*padding-right:  0px!important;;*/
  }


</style>
