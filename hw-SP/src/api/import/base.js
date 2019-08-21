import fetch from '@/utils/request'

const Base_API = {
  //产品品类
  findAllProductClass: (params) => {
    return fetch.get(`/product/productClass/findAll?platform=`+params)
  },
  //产品品类V2
  findAllProductClassV2: () => {
    return fetch.get(`/product/productClass/v2/findAll`)
  },
  
  addOneProductclass:(params) =>{
    return fetch.post(`/product/productClass/addOne`,params)
  },
  //产品类型
  findAllProductKind: (params) => {
    return fetch.get(`/product/productKind/findAll?platform=`+params)
  },

  addOneProductKind:(params)=>{
    return fetch.post(`/product/productKind/addOne`,params)
  },

  //常规品、非常规品定义
  findAllProductBase: (params) => {
    return fetch.get(`product/productBase/findAll?platform=`+params)
  },

  inputSentProductBaseToOa: (params) =>{
    return fetch.post(`/product/productBase/inputSentProductBaseToOa`,{params})
  },

  addOneProductBase: (params) =>{
    return fetch.post(`/product/productBase/addOne`,params)
  },

  updateProductBase:(params) =>{
    return fetch.post(`/product/productBase/updateOne`,params)
  },

  //客户基本资料
  findAllCustomer: (params) => {
    return fetch.get(`customer/customer/findCooperatingCustomer`,{params})
  },
  
  findOneCustomer: (params) => {
    return fetch.get(`/customer/customer/findOne?fdId=`+params)
  },
  
  findByPage: (params) => {
    return fetch.get(`/customer/customer/findByPage`,{params})
  },

  findAllCustomerManager:(params)=>{
    return fetch.get(`/customer/baseCustomerManager/findAllByPage`,{params})
  },

  editOneCustomerManager:(params)=>{
    return fetch.post(`/customer/baseCustomerManager/editOne`,params)
  },
  addOneCustomerManager:(params)=>{
    return fetch.post(`/customer/baseCustomerManager/addOne`,params)
  },
  //生产机械产能维护表
  findAll:(params)=>{
    return fetch.get(`/base/baseMachine/findAll`,{params})
  },

  addOneMachine:(params)=>{
    return fetch.post(`/base/baseMachine/save`,params)
  },
  //客户资料
  findCustomerInfoPage: (params) => {
    return fetch.get(`customer/customer/findCustomerByPage`,{params})
  },
  findProductList: (params) => {//生活用纸原纸常规品列表
    return fetch.get(`/product/product/findByKindNo`,{params})
  },
  productAddOne: (params) => {//生活用纸原纸常规品列表
    return fetch.post(`/product/product/productAddOne`,params)
  },
  updateProduct: (params) => {//生活用纸原纸常规品列表
    return fetch.post(`/product/product/updateProduct`,params)
  },
  /**
   * 【不同机型对应K3物料代码】表维护接口
   */
  findAllBaseClassK3Code: (params) => {
    return fetch.get(`/k3/baseClassK3Code/findAll`, {params});
  },
  saveBaseClassK3Code: (params) => {
    return fetch.post(`/k3/baseClassK3Code/save`, params);
  },
  updateBaseClassK3Code: (params) => {
    return fetch.post(`/k3/baseClassK3Code/update`, params);
  },
  checkBaseClassK3Code: (params) => {
    return fetch.get(`/k3/baseClassK3Code/check`, {params});
  }
}

export default Base_API;
