import fetch from  '@/utils/request'
// import qs from 'qs'


const Price_API = {
  findDictData: (params) => {
    return fetch.get(`/base/dictData/findByType`,{params});
  },
  queryImPriceList: (params) => {
    return fetch.get(`/price/imPrice/queryImPriceList`,{params})
  },
  //查询历史价格
  queryHistoryPrice: (params) => {
    return fetch.get(`/price/imPrice/queryHistoryPrice`,{params})
  },
  queryHistoryPriceDetail: (params) => {
    return fetch.get(`/price/imPrice/queryHistoryPriceDetail`,{params})
  },
  //价格录入
  inputQueryList: (params) => {
    return fetch.get(`/price/imPrice/inputQueryList`,{params})
  },
  inputQuerySelect: (params) => {
    return fetch.get(`/price/imPrice/inputQuerySelect`,{params})
  },
  inputSelectProd: (params) => {
    return fetch.post(`/price/imPrice/inputSelectProd?prodBaseIds=` + params)
  },
  inputSentToOa: (params) => {
    return fetch.post(`/price/imPrice/inputSentToOa`,params)
  },
  inputUpdateOne: (params) => {
    return fetch.get(`/price/imPrice/inputUpdateOne`,{params})
  },
  inputUpdateSome: (params) => {
    return fetch.post(`/price/imPrice/inputUpdateSome`,params)
  },
  inputDeleteOne: (params) => {
    return fetch.get(`/price/imPrice/inputDeleteOne?fdid=` + params)
  },

  //价格调整
  adjustQueryList: (params) => {
    return fetch.get(`/price/imPrice/adjustQueryList` , {params})
  },
  adjustSentToOa: (params) => {
    return fetch.post(`/price/imPrice/adjustSentToOa` , params)
  },
  adjustUpdateOne: (params) => {
    return fetch.post(`/price/imPrice/adjustUpdateOne` , params)
  },
  adjustUpdateSome: (params) => {
    return fetch.post(`/price/imPrice/adjustUpdateSome` , params)
  },
  //=============20190715==============================
  findImPriceListForAdjust: (params) => {
    return fetch.get(`/price/imPrice/findImPriceListForAdjust`,{params})
  },
  findImPriceListForAdjustPage: (params) => {
    return fetch.get(`/price/imPrice/findImPriceListForAdjustPage`,{params})
  },
  addImPriceAdjustSome: (params) => {
    return fetch.post(`/price/imPrice/addImPriceAdjustSome` , params)
  },
  submitImPriceAdjust: (params) => {
    return fetch.post(`/price/imPrice/submitImPriceAdjust` , params)
  },
  deleteImPriceAdjustSome: (params) => {
    return fetch.post(`/price/imPrice/deleteImPriceAdjustSome` , params)
  },
  //================================================================
  findNewHistory_imPriceAdjust:(params) =>{
    return fetch.get(`/price/imPrice/findNewHistory`,{params})
  },

  addOneEdit_imPriceAdjust:(params) =>{
    return fetch.post(`/price/imPrice/addOneEdit`,params)
  },

  matching_imPriceAdjust:(params) =>{
    return fetch.post(`/price/imPrice/matching`,params)
  },

  findAllList: (params) => {
    return fetch.get(`/price/imPrice/findAllList`,{params})
  },
  findAllListByPage: (params) => {
    return fetch.get(`/price/imPrice/findAllListByPage`,{params})
  },

  //================================================================
  //特规品价格录入
  adjustQueryList_imSpecialPrice: (params) => {
    return fetch.get(`/price/imSpecialPrice/adjustQueryList` , {params})
  },
  queryImSpecialPriceList_imSpecialPrice: (params) => {
    return fetch.get(`/price/imSpecialPrice/queryImSpecialPriceList` , {params})
  },
  adjustSelectPrice_imSpecialPrice: (params) => {
    return fetch.post(`/price/imSpecialPrice/adjustSelectPrice?specialPriceIds=`+params)
  },
  adjustUserOrder_imSpecialPrice: (params) => {
    return fetch.get(`/price/imSpecialPrice/adjustUserOrder` , {params})
  },
  adjustUpdateSome_imSpecialPrice: (params) => {
    return fetch.post(`/price/imSpecialPrice/adjustUpdateSome` , params)
  },

  adjustDeleteRecords_imSpecialPrice: (params) => {
    return fetch.post(`/price/imSpecialPrice/adjustDeleteRecords?specialPriceIds=` + params)
  },
  queryHistoryByBatchId_imSpecialPrice: (params) => {
    return fetch.post(`/price/imSpecialPrice/queryHistoryByBatchId?batchId=` + params)
  },

  //特规品套产品价格明细表
  findAllSpecialSetPrice:(params)=>{
    return fetch.get(`/price/imPriceAddCondition/findAllSpecialSetPrice`,{params})
  },

  findHistorySpecialSetPrice:(params)=>{
    return fetch.get(`/price/imPriceAddCondition/findHistorySpecialSetPrice`,{params})
  },


  //小幅宽产品加价表
  findAll_ImPriceAddCondition:(params) =>{
    return fetch.get(`/price/imPriceAddCondition/findAll`,{params})
  },
  findHistory_ImPriceAddCondition:(params) =>{
    return fetch.get(`/price/imPriceAddCondition/findHistory`,{params})
  },
  findNewHistory_ImPriceAddCondition:(params) =>{
    return fetch.get(`/price/imPriceAddCondition/findNewHistory`,{params})
  },
  addOneEdit_ImPriceAddCondition:(params)=>{
    return fetch.post(`/price/imPriceAddCondition/addOneEdit`,params)
  },
  matching_ImPriceAddCondition:(params)=>{
    return fetch.post(`/price/imPriceAddCondition/matching`,params)
  },
  //幅宽及承兑汇票结算差价表
  findAll_imPriceDifference:(params)=>{
    return fetch.get(`/price/imPriceDifference/findAll`,{params})
  },

  findOne_imPriceDifference:(params)=>{
    return fetch.get(`/price/imPriceDifference/findOne?fdId=`+params)
  },

  findHistory_imPriceDifference:(params)=>{
    return fetch.get(`/price/imPriceDifference/findHistoryImPrice?ItemName=`+params)
  },

  addOne_imPriceDifference:(params)=>{
    return fetch.post(`/price/imPriceDifference/addOne?itemName=`+params)
  },

  updateOne_imPriceDifference:(params)=>{
    return fetch.post(`/price/imPriceDifference/updateOne`,params)
  },
  
  //
  findNewHistory_imPriceDifference:(params) =>{
    return fetch.get(`/price/imPriceDifference/findNewHistory`,{params})
  },
  addOneEdit_imPriceDifference:(params)=>{
    return fetch.post(`/price/imPriceDifference/addOneEdit`,params)
  },
  matching_imPriceDifference:(params)=>{
    return fetch.post(`/price/imPriceDifference/matching`,params)
  },
  //差价表
  findImPriceDifferenceList:(params) =>{
    return fetch.get(`/price/imPriceDifference/findImPriceDifferenceList`,{params})
  },
  findImPriceDifferencePage:(params) =>{
    return fetch.post(`/price/imPriceDifference/findImPriceDifferencePage`,params)
  },
  saveOrUpdateOneImPriceDifference:(params)=>{
    return fetch.post(`/price/imPriceDifference/saveOrUpdateOneImPriceDifference`,params)
  },
  saveOrUpdateBatchImPriceDifference:(params)=>{
    return fetch.post(`/price/imPriceDifference/saveOrUpdateBatchImPriceDifference`,params)
  },
  deleteOneImPriceDifference:(params) =>{
    return fetch.get(`/price/imPriceDifference/deleteOneImPriceDifference`,{params})
  },






}

export default Price_API
