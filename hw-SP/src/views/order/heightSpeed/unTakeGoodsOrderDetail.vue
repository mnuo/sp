<template>
    <div class="product-container">
        <el-row :span="24">
            <div>泉州华龙已生成未提货完成订单明细</div>
        </el-row>
        <el-row :span="24">
            <el-col :span="20"><div>&nbsp;</div></el-col>
            <el-col :span="4" >
                <el-button type="primary" @click="dialogTableVisible = true">订单终结</el-button>
                <el-button class="text-right" type="warning" @click="$router.go(-1)">返回上一级</el-button>
            </el-col>
        </el-row>
        <el-row :span="24">
            <el-table :data="tableData" highlight-curren-row border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="index" label="订单编号">
                </el-table-column>
                <el-table-column prop="index" label="产品品类">
                </el-table-column>
                <el-table-column prop="index" label="产品类型">
                </el-table-column>
                <el-table-column prop="index" label="下单时间">
                </el-table-column>
                <el-table-column prop="index" label="订单数量(吨)">
                </el-table-column>
                <el-table-column label="生产">
                    <el-table-column prop="index" label="已生产吨数">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column prop="index" label="欠生产吨数">
                    </el-table-column>
                    <el-table-column prop="index" label="状态">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="提货">
                    <el-table-column prop="index" label="订单客户提货数量">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column prop="index" label="其他客户提货数量">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column prop="index" label="已生产未提货吨数">
                        <el-table-column prop="index" label="优等品">
                        </el-table-column>
                        <el-table-column prop="index" label="副品">
                        </el-table-column>
                        <el-table-column prop="index" label="合计">
                        </el-table-column>
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="index" label="备注">
                </el-table-column>
            </el-table>
        </el-row>
        <!-- 查看弹出框 -->
        <el-dialog title="订单终结" width="40%" :visible.sync="dialogTableVisible">
            <el-form ref="dialogForm" :rules="rules" :model="dialogForm" label-width="100px">
                <el-form-item label="订单编号" prop="orderNo">
                    <el-input v-model="dialogForm.orderNo"></el-input>
                </el-form-item>
                <el-form-item label="终结原因：" prop="reason">
                    <el-input :autosize="{ minRows: 4, maxRows: 4}" type="textarea" v-model="dialogForm.reason"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="warning" @click="submitForm('dialogForm')">提 交</el-button>
                <el-button @click="dialogTableVisible = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data(){
        return{
            tableData:[],
            dialogTableVisible: false,
            dialogForm: {
                orderNo: '',
                reason: '',
            },
             rules: {
                orderNo: [
                    { required: true, message: '请输入订单编号', trigger: 'blur' },
                ],
                reason: [
                    { required: true, message: '请输入终结原因', trigger: 'blur' },
                ],
             }
        }
    },
    watch:{
        '$route': function () {
            this.init();
        }
    },
    methods:{
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
            if (valid) {
                alert('submit!');
            } else {
                console.log('error submit!!');
                return false;
            }
            });
        },
        jumpDetail(val){
            
        },
        init(){
           
        }
    },
    mounted(){
        this.init()
        
    }
}
</script>
<style scoped>
</style>