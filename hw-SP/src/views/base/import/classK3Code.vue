<template>
  <div>
    <!-- <el-row>
      <el-col :span="24">
        <span><b>（二）生产机械产能维护表</b></span>
      </el-col>
    </el-row> -->
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="primary" @click="dialogAddOne=true">新增</el-button>
        <el-button type="primary" v-on:click="goCheck">审核</el-button>
        <el-button type="info">返回目录</el-button>
      </el-col>
    </el-row>

    <el-table
      max-height="620"
      border :data="tableData">
      <el-table-column type="index" header-align="center" align="center" label="序号" winth="50"></el-table-column>
      <el-table-column prop="platform" header-align="center" align="center" label="纸机"
                       min-width="50"></el-table-column>
      <el-table-column prop="classNo" header-align="center" align="center" label="品类ID"
                       min-width="50"></el-table-column>
      <el-table-column prop="className" header-align="center" align="center" label="纸类名称"
                       min-width="50"></el-table-column>
      <el-table-column prop="k3code" header-align="center" align="center" label="对应k3物料代码"
                       min-width="50"></el-table-column>
      <el-table-column prop="k3name" header-align="center" align="left" label="对应k3物料名称"
                       min-width="70"></el-table-column>
      <el-table-column prop="color" header-align="center" align="center" label="颜色"
                       min-width="50"></el-table-column>


      <el-table-column prop="" header-align="center" align="center" label="审核人"
                       min-width="80">
        <el-table-column prop="checkedBy" header-align="center" align="center" label="姓名"
                         min-width="50"></el-table-column>
        <el-table-column prop="checkedDate" header-align="center" align="center" label="审核时间"
                         min-width="80"></el-table-column>
      </el-table-column>

      <el-table-column prop="statusName" header-align="center" align="center" label="状态"
                       min-width="50"></el-table-column>

      <el-table-column
        fixed="right"
        align="center"
        header-align="center"
        label="操作"
        min-width="50">
        <template slot-scope="scope">
          <el-button @click="onClickModifyButton(scope.row)" type="text" size="small">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--新增对话框-->
    <div>
      <el-dialog v-if="dialogAddOne" title="新增" v-dialogDrag width="35%" center v-bind:key="refreshKey">

        <el-row class="row-top">
          <el-col :span="7" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            纸机类型：
          </el-col>
          <el-col :span="12" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            <el-select v-model="saveOneData.platform" class="my-row"
                       v-on:change="onPlatformChange(saveOneData.platform)">
              <el-option v-for="(platform,index) in platformArray" :key="index" v-bind:value="platform"
                         v-bind:label="platform">
                {{platform}}
              </el-option>
            </el-select>
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            品类名称：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-select v-model="saveOneData.classNo" class="my-row">
              <el-option v-for="(productClass,index) in platformKeyProductClassDatasMap.get(saveOneData.platform)"
              :key="index"
                         v-bind:value="productClass.classNo"
                         v-bind:label="productClass.name">
                {{productClass.name}}
              </el-option>
            </el-select>
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            对应k3物料代码：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="saveOneData.k3code" type="number"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            对应k3物料名称：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="saveOneData.k3name"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            颜色：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="saveOneData.color"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            备注：
          </el-col>
          <el-col :span="16" class="text-left">
            <el-input
              v-model="saveOneData.remark"
              type="textarea"
              :rows="3"
              placeholder="备注">
            </el-input>
          </el-col>
        </el-row>

        <span slot="footer">
          <el-button type="primary" @click="doSave()">确定</el-button>
        </span>
      </el-dialog>
    </div>

    <!--修改对话框-->
    <div>
      <el-dialog :visible.sync="dialogModifyOne" title="修改" v-dialogDrag width="35%" center>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            客户：
          </el-col>
          <el-col :span="12" class="text-left">
            <!--<el-select v-model="modifyOneData.customerId" class="my-row">
              <el-option v-for="customer in customerData" :key="customer.fdId" v-bind:value="customer.fdId" v-bind:label="customer.custName">
                {{customer.custName}}
              </el-option>
            </el-select>-->
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            现执行运价：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="modifyOneData.price" type="number"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
            （元/吨)
          </el-col>
        </el-row>

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            补贴金额：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-input v-model="modifyOneData.addPrice" type="number"></el-input>
          </el-col>
          <el-col :span="5" class="text-right">
            （元/吨)
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="7" class="text-left">运输方式：</el-col>
          <el-col :span="12">
            <el-select v-model="modifyOneData.method" class="my-row">
              <el-option value="代办" label="代办">代办</el-option>
              <el-option value="自提" label="自提">自提</el-option>
            </el-select>
          </el-col>
        </el-row>

        <!--  <el-row class="row-top">
            <el-col :span="7" class="text-left">
              审核人：
            </el-col>
            <el-col :span="12" class="text-left">
              <el-input v-model="modifyOneData.checkedBy"></el-input>
            </el-col>
          </el-row>-->

        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            开始日期：
          </el-col>
          <el-col :span="12" class="text-left">
            <el-date-picker
              v-model="modifyOneData.startDate"
              class="my-row"
              type="date"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              placeholder="选择开始日期">
            </el-date-picker>
          </el-col>
        </el-row>

        <!-- <el-row class="row-top">
           <el-col :span="7" class="text-left">
             结束日期：
           </el-col>
           <el-col :span="12" class="text-left">
             <el-date-picker
               v-model="modifyOneData.endDate"
               type="date"
               format="yyyy 年 MM 月 dd 日"
               value-format="yyyy-MM-dd HH:mm:ss"
               placeholder="选择结束日期">
             </el-date-picker>
           </el-col>
         </el-row>-->

        <el-row class="row-top">
          <el-col :span="7" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            结束日期：
          </el-col>
          <el-col :span="12" class="text-left" style="padding-top: 5px;padding-bottom: 5px;">
            {{modifyOneData.endDate}}
          </el-col>
        </el-row>


        <el-row class="row-top">
          <el-col :span="7" class="text-left">
            备注：
          </el-col>
          <el-col :span="16" class="text-left">
            <el-input type="textarea"
                      :rows="3"
                      v-model="modifyOneData.remark"></el-input>
          </el-col>
        </el-row>

        <span slot="footer">
          <el-button type="primary" @click="doModify()">确定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import Base_API from '@/api/import/base.js';
  import {directive} from '@/views/base/import/productBase';
  import Const_Util from '@/utils/consts.js';
  import DataFormat from '@/utils/DataFormat.js';

  export default {
    name: 'classK3Code',
    components: {},
    data() {
      return {
        //Vue组件重新加载标识
        refreshKey: 0,
        tableData: [],
        loading: false,
        dialogAddOne: false,
        dialogModifyOne: false,
        productClassData: [],
        //以platform作为键值的ProductClassData对象map集合
        platformKeyProductClassDatasMap: new Map(),
        platformArray: [],

        saveOneData: {
          checkedBy: null,
          checkedDate: null,
          checkedRemark: null,
          className: null,
          classNo: null,
          color: null,
          fdId: 0,
          k3code: null,
          k3name: null,
          platform: null,
          remark: null,
          status: 0,
          selectedProductClassPosition: null
        },

        modifyOneData: {
          checkedBy: null,
          checkedDate: null,
          checkedRemark: null,
          className: null,
          classNo: null,
          color: null,
          fdId: 0,
          k3code: null,
          k3name: null,
          platform: null,
          remark: null,
          status: 0
        }
      }
    },
    methods: {
      initPage() {

        this.findAll();
        //this.saveOneData.startDate = new Date();
      },
      goCheck() {
        this.$router.push({
          path: '/policy/import/policyTransportPrice/check'
        })
      },
      findAll() {
        Base_API.findAllBaseClassK3Code().then(res => {
          res.data.forEach(function (each, index) {
            each.statusName = Const_Util.allStatus.get(each.status);
          });

          this.tableData = res.data || [];
          //console.log(this.tableData)
        });


        Base_API.findAllProductClassV2().then(res => {
          var myMap = new Map();
          this.productClassData = res.data || [];

          //以platform作为键值，对productClassData进行分类汇总
          for (let i = 0; i < this.productClassData.length; i++) {
            let each = this.productClassData[i];
            let productClassDataArray = myMap.get(each.platform);
            if (productClassDataArray === undefined) {
              productClassDataArray = [];
            }
            productClassDataArray.push(each);
            myMap.set(each.platform, productClassDataArray);
          }

          this.platformKeyProductClassDatasMap = myMap;
          this.platformArray = [];
          for (let each of myMap.keys()) {
            this.platformArray.push(each);
          }
        });

        this.refreshKey=this.refreshKey+1;
      },

      doSave() {
        if (this.saveOneData.platform === null) {
          this.$message.error('纸机类型未选择');
          return;
        }
        if (this.saveOneData.classNo === null) {
          this.$message.error('品类名称未选择');
          return;
        }
        if (this.saveOneData.k3code === null) {
          this.$message.error('对应k3物料代码未填写');
          return;
        }
        if (this.saveOneData.k3name === null) {
          this.$message.error('对应k3物料名称未填写');
          return;
        }

        this.dialogAddOne = false;
        Base_API.saveBaseClassK3Code(this.saveOneData).then(res => {
          this.$message({
            type: res.success ? 'res.success' : 'res.info',
            message: res.success ? '新增成功' : res.msg,
          });
          this.saveOneData = {};
          this.findAll();
        }).catch(err => {
          this.$message({type: 'error', message: err.msg});
          debugger;
        })
      },
      onClickModifyButton(rowData) {
        this.modifyOneData = rowData;
        this.dialogModifyOne = true;
      }
      ,
      doModify: function () {
        if (this.modifyOneData.customerId === null) {
          this.$message.error('客户未选择');
          return;
        }

        this.dialogModifyOne = false;
        Policy_API.updatePoTransport(this.modifyOneData).then(res => {
          this.$message({
            type: res.success ? 'res.success' : 'res.info',
            message: res.success ? '修改成功' : res.msg,
          });
          this.modifyOneData = {};
          this.findAll();
        }).catch(err => {
          this.$message({type: 'error', message: err.msg})
        })
      },
      onPlatformChange: function (selectedPlatform) {
        this.saveOneData.classNo = null;
      }
    },
    mounted() {
      this.initPage()
    }
    ,
    computed: {}
    ,
    watch: {}
  }
</script>


<style scoped>
  .my-row {
    width: 100%;
  }

</style>
