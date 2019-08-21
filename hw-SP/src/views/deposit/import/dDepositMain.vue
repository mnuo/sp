<template>
  <div>
    <el-row>
      <el-col :span="24">
        <span><b>客户当月定金对账</b></span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <div>&nbsp;</div>
      </el-col>
      <el-col :span="8" class="text-right">
        <el-button type="info">返回上一级</el-button>
      </el-col>
    </el-row>
    <el-table border>
      <el-table-column type="index" header-align="center" align="center" label="序号" width="50 "></el-table-column>
      <el-table-column prop="" header-align="center" align="center" label="客户类型" width="80"></el-table-column>
      <el-table-column prop="" header-align="center" align="center" label="客户名称" width="120"></el-table-column>
      <el-table-column header-align="center" align="center" label="我司对账审核" min-width="120">
        <el-table-column prop="" header-align="center" align="center" label="审核人" min-width="60"></el-table-column>
        <el-table-column prop="" header-align="center" align="center" label="审核时间" min-width="60"></el-table-column>
      </el-table-column>
      <el-table-column header-align="center" align="center" label="客户审核确认" min-width="260">
        <el-table-column prop="" header-align="center" align="center" label="确认状态" min-width="60"></el-table-column>
        <el-table-column prop="" header-align="center" align="center" label="确认时间" min-width="60"></el-table-column>
        <el-table-column prop="" header-align="center" align="center" label="确认意见" min-width="140"></el-table-column>
      </el-table-column>
      <el-table-column prop="" header-align="center" align="center" label="往来对账明细查询" width="80"></el-table-column>
    </el-table>
    <div style="margin-top:20px">
      <textarea style="width:100%;height:180px;background-color:#c8dec2">功能说明：
        1.本表在每月1号自动生成上月的定金对账表，当月每月缴纳定金也每月返还定金的客户不显示
        2.排序：先经销商，后加工厂
        3.我司对账审核：财务销售会计对账时
      </textarea>
    </div>
  </div>
</template>
