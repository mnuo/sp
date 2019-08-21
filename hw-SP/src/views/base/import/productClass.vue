<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div><b>产品品类定义表</b></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <div>
          <el-button type="primary" @click="dialogAddOne=true">新增</el-button>
        </div>
      </el-col>
    </el-row>
    <el-table
    :data="tableDate" v-loading="loading" element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    stripe border
    style="width:100%">
      <el-table-column type="index" header-align="center" align="center" label="序号" width="100"></el-table-column>
      <el-table-column prop="classNo" header-align="center" align="center" label="品类编号" width="160"></el-table-column>
      <el-table-column prop="name" header-align="center" label="品类名称" width="160"></el-table-column>
      <el-table-column prop="remarks" header-align="center" label="备注" :render-header="renderheader" min-width="420"></el-table-column>
      <el-table-column prop="enabledText" header-align="center" align="center" label="状态" :render-header="renderheader" min-width="160"></el-table-column>
      <el-table-column  header-align="center" label="操作" :render-header="renderheader" min-width="160"></el-table-column>
    </el-table>
    <div>
      <el-dialog  :visible.sync="dialogAddOne" style="width:100%">
        <div>
          <el-row>
            <el-col :span="24">
              <el-col :span="12">
                <el-col :span="10"><div class="grid-content bg-purple">品类编号</div></el-col>
                <el-col :span="12"><div><el-input v-model="classNo"></el-input></div></el-col>
              </el-col>
              <el-col :span="12">
                <el-col :span="10"><div class="grid-content bg-purple">品类名称</div></el-col>
                <el-col :span="12"><div><el-input v-model="name"></el-input></div></el-col>
              </el-col>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-col :span="5"><div class="grid-content bg-purple">备注</div></el-col>
              <el-col :span="18"><el-input  v-model="remarks"></el-input></el-col>
            </el-col>
          </el-row>
        </div>
        <div slot="footer">
          <el-button type="button" @click="dialogAddOne=false">取消</el-button>
          <el-button type="primary" @click="addOne()">确定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import Base_API from '@/api/import/base'
export default {
  name: "price-list",
  data(){
    return{
      loading:false,
      tableDate:[],
      dialogAddOne:false,
      classNo:'',
      name:'',
      remarks:'',
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
    findAllProductClass(){
      this.loading=true;
      var params='Import'
      Base_API.findAllProductClass(params).then(res =>{
        this.tableDate=res.data||[];
        console.log(this.tableDate);
        this.loading=false;
      }).catch(err=>{
        this.loading=false;
        this.$message({type:'error',message:'请求异常'+err.msg});
        console.log(err.errorCode);
      });
    },
    addOne(){
      var params={
        classNo:this.classNo,
        name:this.name,
        remarks:this.remarks,
        platform:'Import',
      };
      console.log(params)
      Base_API.addOneProductclass(params).then(res=>{
        this.$message({
          type:res.success?'success':'info',
          message:res.success?'新增成功':res.msg,
        });
        this.dialogAddOne=false;
        this.findAllProductClass();
      }).catch(err=>{
        this.$message({type:'error',message:'新增失败，'+err.msg});
        this.dialogAddOne=false;
      })
    },
  },
 
  
  created() {

  },
  mounted() {
    this.findAllProductClass();
  }
}
</script>
