<template>
  <div>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
           <el-button type="primary" @click="add()">新增</el-button>
        </div>
      </el-col>
    </el-row>
    <el-table :data="tableData" :span-method="objectOneMethod" v-loading="loading" 
      element-loading-text="拼命加载中" element-loading-spanner="el-icon-loading"
      stripe border  style="width:100%" :max-height="tableHeight">
    <el-table-column type="index" header-align="center" align="center" label="序号" width="45"></el-table-column>
    <el-table-column prop="platform" header-align="center" label="生产机台" min-width="150"></el-table-column> 
    <el-table-column prop="className" :formatter="formatClassName" header-align="center" label="产品类型" min-width="100">
    </el-table-column>
    <el-table-column prop="ration" header-align="center" label="定量" min-width="90"></el-table-column>
    <el-table-column prop="width" header-align="center" label="幅宽" min-width="90"></el-table-column>
    <el-table-column prop="layer" header-align="center" label="层数" min-width="90"></el-table-column>


    <el-table-column prop="wrinkleRate" header-align="center" label="伸长率(%)" :render-header="renderheader" min-width="55"></el-table-column>
    <el-table-column prop="diameter" header-align="center" label="直径 (mm)" :render-header="renderheader" min-width="80"></el-table-column>
    <el-table-column prop="remark" align="center" header-align="center" label="操作" width="50">
      <template slot-scope="scope" >
         <el-tooltip content="修改" placement="top">
            <el-button icon="el-icon-edit" @click="goProductBaseDeatil(scope.row)"></el-button>
         </el-tooltip>
        <!-- <a style="color:#0066cc" @click="goProductBaseDeatil(scope.row)">修改</a> -->
      </template>
    </el-table-column>
    </el-table>
    <el-dialog  :visible.sync="dialogFormVisibleA" title="新增"
    v-dialogDrag>
    <div class="dialog-pan" > 
        <div class="dialog-pan-title">
          <div>基础资料</div>
        </div>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content" >生产机台</div></el-col>
            <el-col :span="13">
              <div >
                <el-select v-model="platform" @change="chosePlatform" style="margin-right:5px"  placeholder="生产机台">
                 <el-option 
                      v-for="item in allPlatForm"
                      :key="item.value"
                      :label="item.value"
                      :value="item.value"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content">产品类型</div></el-col>
            <el-col :span="13">
              <div >
                <el-select v-model="classNo" placeholder="产品类型">
                  <el-option 
                      v-for="(value, key) in allClass"
                      :key="key"
                      :label="value"
                      :value="key"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      </div>
      <div class="dialog-pan">
        <div class="dialog-pan-title">
          <div>产品规格</div>
        </div>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content"  >定量</div></el-col>
            <el-col :span="13">
              <div>
                <el-input v-model="ration"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content" > 幅宽</div></el-col>
            <el-col :span="13">
              <div  >
                <el-input v-model="width"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content"  >直径</div></el-col>
            <el-col :span="13">
              <div>
                <el-input v-model="diameter"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content" >层数</div></el-col>
            <el-col :span="13">
              <div >
                <el-select v-model="layer" collapse-tags multiple placeholder="层数">
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
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content"  >伸长率</div></el-col>
            <el-col :span="13">
              <div>
                <el-input v-model="wrinkleRate"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
           </el-col>
        </el-col>
      </el-row>
      <el-row class="textarea-bottom">
        <el-col :span="24" >
          <el-col :span="5"><div class="grid-content" >备注</div></el-col>
          <el-col :span="18">
            <el-input  type="textarea" placeholder="请输入内容" v-model="remark"></el-input>
          </el-col>
        </el-col>
      </el-row>
      </div>
      <div slot="footer" >
        <el-button @click="dialogFormVisibleA = false">取 消</el-button>
        <el-button type="primary" @click="addOne()">确 定</el-button>
      </div>     
    </el-dialog>
<el-dialog  :visible.sync="dialogFormVisible" title="修改"
    v-dialogDrag>
    <div v-for="item in tableDataA" :key="item.fdId">
    <div class="dialog-pan" > 
      <div class="dialog-pan-title">
          <div>基础资料</div>
      </div>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content" >生产机台</div></el-col>
            <el-col :span="13">
              <div >
                <el-select v-model="item.platform"  @change="chosePlatform" disabled="disabled" style="margin-right:5px"  placeholder="生产机台">
                 <el-option 
                      v-for="item in allPlatForm"
                      :key="item.value"
                      :label="item.value"
                      :value="item.value"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content">产品类型</div></el-col>
            <el-col :span="13">
              <div >

                <el-select v-model="item.classNo" disabled="disabled"  placeholder="产品类型">
                  <el-option 
                      v-for="(value, key) in allClass"
                      :key="key"
                      :label="value"
                      :value="key"></el-option>
                </el-select> 
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      </div>
      <div class="dialog-pan" > 
        <div class="dialog-pan-title">
          <div>产品规格</div>
        </div>
        <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content"  >定量</div></el-col>
            <el-col :span="13">
              <div>
                <el-input v-model="item.ration"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content" > 幅宽</div></el-col>
            <el-col :span="13">
              <div  >
                <el-input v-model="item.width"></el-input>
              </div>
            </el-col>
          </el-col>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content"  >直径</div></el-col>
            <el-col :span="13">
              <div>
                <el-input v-model="item.diameter"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
            <el-col :span="10"><div class="grid-content" >层数</div></el-col>
            <el-col :span="13">
              <div >
                <el-select v-model="layers"  collapse-tags multiple placeholder="层数">
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
      <el-row >
        <el-col :span="24">
          <el-col :span="12">
            <el-col :span="10"><div class="grid-content"  >伸长率</div></el-col>
            <el-col :span="13">
              <div>
                <el-input v-model="item.wrinkleRate"></el-input>
              </div>
            </el-col>
          </el-col>
           <el-col :span="12" class="text-right">
           </el-col>
        </el-col>
      </el-row>
      <el-row class="textarea-bottom">
        <el-col :span="24">
          <el-col :span="5"><div class="grid-content" >备注</div></el-col>
          <el-col :span="18">
            <el-input  type="textarea" placeholder="请输入内容" v-model="item.remark"></el-input>
          </el-col>
        </el-col>
      </el-row>
      </div>
    </div>
    <div slot="footer" >
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateProductOne()">修改</el-button>
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
      tableHeight: '300',
      allPlatForm:[
        {
          value:'高速纸机',
          class:{
            A:'卫卷纸',
            B:'纸巾纸(软抽)',
            C:'餐巾纸'
          },
        },
        {
          value:'1575纸机',
          class:{
            B:'本色纸巾纸',
            BW:'本色卫生纸',
            C:'餐巾纸原纸',
            J:'卫卷纸',
            R:'纸巾纸（软抽）',
            S:'手帕纸原纸',
            W:'卫生纸原纸',
            Z:'纸巾纸原纸',

          }
        },
        {
          value:'3900纸机',
          class: {
            B:'本色纸巾纸',
            BW:'本色卫生纸',
            C:'餐巾纸原纸',
            J:'卫卷纸',
            R:'纸巾纸（软抽）',
            S:'手帕纸原纸',
            W:'卫生纸原纸',
            Z:'纸巾纸原纸',

          }
        },
      ],
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
      platform:'',
      classNo:'',
      kindNo:'',
      ration: '',
      width:'',
      layer:'',
      layers:[],
      wrinkleRate:'',
      diameter:'',
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
    formatClassName: function (row, column, cellValue, index) {
      debugger;
      var className='';
      this.allPlatForm.forEach(item =>{
        
        if(item.value == row.platform){
            className=item.class[row.classNo];
        }
      });
      return className;
    },
    getAllClass:function(platform){
      var allClass=[];
      this.allPlatForm.forEach(item =>{
        if(item.value == platform){
            allClass=item.class;
        }
      });
      return allClass;
    },
    chosePlatform:function (){      
        debugger;
        this.allClass =this.getAllClass(this.platform);
    },

		//状态改成汉字

    //初始化数据
    initPage(){
      this.findList();
    },
    findList(){
      this.loading=true;
      var params={
        kindNo: 'b',
        platform: '高速纸机,1575纸机,3900纸机'
      }
      Base_API.findProductList(params).then(res =>{
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
          if(item.platform === this.tableData[index - 1].platform){ //第2列需合并相同内容的判断条件
            spanOneArr[concatOne] += 1;
            spanOneArr.push(0);
          }else{
            spanOneArr.push(1);
            concatOne = index;
          };
          if(item.classNo === this.tableData[index - 1].classNo && item.platform === this.tableData[index - 1].platform){//第3列需合并相同内容的判断条件
            spanTwoArr[concatTwo] += 1;
            spanTwoArr.push(0);
          }else{
            spanTwoArr.push(1);
            concatTwo = index;
          };
          if(item.wrinkleRate === this.tableData[index - 1].wrinkleRate && item.platform === this.tableData[index - 1].platform){ //第2列需合并相同内容的判断条件
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
      if(columnIndex === 2) {
        const _row = (this.setdates(this.tableData).two)[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
      // if(columnIndex === 4) {
      //   const _row = (this.setdates(this.tableData).three)[rowIndex];
      //   const _col = _row > 0 ? 1 : 0;
      //   return {
      //     rowspan: _row,
      //     colspan: _col
      //   };
      // }
    },
    //提交OA
   
    //跳转到修改页面
    goProductBaseDeatil(item){
      console.log(item);
      var _this=this;
      _this.layers=[item.layer];
      _this.allClass =_this.getAllClass(item.platform);
      _this.tableDataA=[item];
       console.log(_this.tableData);
       _this.dialogFormVisible = true;
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
    updateProductOne(){
       this.tableDataA[0].layer=this.layers.join(',');

       var params=this.tableDataA[0];
       console.log(params);
      Base_API.updateProduct(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'修改成功！':res.msg,
        })
        this.findList();
        this.dialogFormVisible= false;
      }).catch(err=>{
        this.$message({type:'error',message:err.msg});
        this.dialogFormVisible= false;
      })
    },
    //新增
    add(){
      this.layer=[];
      this.dialogFormVisibleA=true;
    },
    addOne(){
      var layersA=this.layer.join(",");
      var params={
          platform:this.platform,
          classNo: this.classNo,
          kindNo:'b',
          ration: this.ration,
          width:this.width,
          layer:layersA,
          wrinkleRate:this.wrinkleRate,
          diameter:this.diameter,
          remark:this.remark
      };
      Base_API.productAddOne(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功，请提交OA！':res.msg,
          })
          this.findList();
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
    this.$nextTick(function () {
      debugger;
          // console.log(this.$refs.table.offsetHeight);
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
})
 


</script>