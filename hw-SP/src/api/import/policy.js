import fetch from '@/utils/request'

const Policy_API = {
  /**
   * 二、销售政策维护
   *  */
  //(二) 级别优惠及超量奖奖励标准维护-->1.当月生活原纸【标准政策】维护奖励标准维护表
  findPolicyMaintain: (params) => { 
    return fetch.get(`/policy/poPolicy/findPolicyMaintain`,{params});
  },
  findPolicyMaintainExecute: (params) => { 
    return fetch.get(`/policy/poPolicy/findPolicyMaintainExecute`,{params});
  },
  deletePolicyMaintain: (params) => { 
    return fetch.get(`/policy/poPolicy/deletePolicyMaintain`,{params});
  },
  addPolicyMaintain: (params) =>{
    return fetch.post(`/policy/poPolicy/addPolicyMaintain`, params);
  },
  editPolicyMaintain: (params) =>{
    return fetch.post(`/policy/poPolicy/editPolicyMaintain`, params);
  },
  //2.当月生活原纸【超量奖】维护奖励标准维护表
  findPolicyMaintainOver: (params) => { 
    return fetch.get(`/policy/poOver/findPolicyMaintainOver`,{params});
  },
  addPolicyMaintainOver: (params) =>{
    return fetch.post(`/policy/poOver/addPolicyMaintainOver`, params);
  },
  deletePolicyMaintainOver: (params) => { 
    return fetch.get(`/policy/poOver/deletePolicyMaintainOver`,{params});
  },
  /**(三)客户优惠奖励维护 */
  //当月生活原纸机台产能和供货计划维护
  findPolicyMaintainSupply: (params) => { 
    return fetch.get(`/policy/poSupply/findPolicyMaintainSupply`,{params});
  },
  addPolicyMaintainSupply: (params) =>{
    return fetch.post(`/policy/poSupply/addPolicyMaintainSupply`, params);
  },
  editPolicyMaintainSupply: (params) =>{
    return fetch.post(`/policy/poSupply/editPolicyMaintainSupply`, params);
  },
  deleteSupplyByDetailId: (params) => { 
    return fetch.get(`/policy/poSupply/deleteByDetailId`,{params});
  },
  //客户级别优惠维护
  findPolicyMaintainMonthCustomer: (params) => { 
    return fetch.get(`/policy/poPolicyMonth/findPolicyMaintainMonthCustomer`,{params});
  },
  findCustomersNotIn: (params) => { 
    return fetch.get(`/policy/poPolicyMonth/findCustomersNotIn`,{params});
  },
  addPolicyMaintainMonthCustomer: (params) =>{
    return fetch.post(`/policy/poPolicyMonth/addPolicyMaintainMonthCustomer`, params);
  },
  deletePolicyMaintainMonthCustomer: (params) =>{
    return fetch.get(`/policy/poPolicyMonth/deletePolicyMaintainMonthCustomer`, {params});
  },
  //客户超量奖维护
  findPolicyMaintainOverRewardCustomer: (params) => { 
    return fetch.get(`/policy/poOverReward/findPolicyMaintainOverRewardCustomer`,{params});
  },
  addPolicyMaintainOverRewardCustomer: (params) =>{
    return fetch.post(`/policy/poOverReward/addPolicyMaintainOverRewardCustomer`, params);
  },
  deletePolicyMaintainOverRewardCustomer: (params) =>{
    return fetch.get(`/policy/poOverReward/deletePolicyMaintainOverRewardCustomer`, {params});
  },
  editControlRatePolicyMaintainOverRewardCustomer: (params) =>{
    return fetch.get(`/policy/poOverReward/editControlRatePolicyMaintainOverRewardCustomer`, {params});
  },
  findCustomersNotInOverReward: (params) => { 
    return fetch.get(`/policy/poOverReward/findCustomersNotInOverReward`,{params});
  },
  //折扣信息表
  findPolicyDiscount: (params) => { 
    return fetch.get(`/policy/poDiscount/findPolicyDiscountByPage`,{params});
  },
  findCooperatingCustomers: (params) =>{
    return fetch.get(`/customer/customer/findCooperatingCustomer`,{params});
  },
  findDictData: (params) => {
    return fetch.get(`/base/dictData/findByType`,{params});
  },
  addPolicyDiscount: (params) =>{
    return fetch.post(`/policy/poDiscount/save`, params);
  },
  editPolicyDiscount: (params) =>{
    return fetch.post(`/policy/poDiscount/update`, params);
  },
  approveStatus: (params) =>{
    return fetch.get(`/policy/poDiscount/approveStatus`, {params});
  },
  findPolicyDiscountOrder: (params) => { 
    return fetch.get(`/policy/poDiscountOrder/findPolicyDiscountOrderByPage`,{params});
  },
  findDiscountOrderDetails: (params) => { 
    return fetch.get(`/policy/poDiscount/findDiscountOrderDetails`,{params});
  },
  deletePolicyDiscount: (params) => { 
    return fetch.get(`/policy/poDiscount/delete`,{params});
  },



  /**
   * 当月生活原纸【运输补贴】审核表维护接口
   */
  findAllPoTransport: (params) => {
    return fetch.get(`/policy/poTransport/findAll`, {params});
  },
  savePoTransport: (params) => {
    return fetch.post(`/policy/poTransport/save`, params);
  },
  updatePoTransport: (params) => {
    return fetch.post(`/policy/poTransport/update`, params);
  },
  checkPoTransport: (params) => {
    return fetch.get(`/policy/poTransport/check`, {params});
  }
  ,

  /**
   * 当月生活原纸【区域价格补助】审核表维护接口
   */
  findAllPoArea: (params) => {
    return fetch.get(`/policy/poArea/findAll`, {params});
  },
  savePoArea: (params) => {
    return fetch.post(`/policy/poArea/save`, params);
  },
  updatePoArea: (params) => {
    return fetch.post(`/policy/poArea/update`, params);
  },
  checkPoArea: (params) => {
    return fetch.get(`/policy/poArea/check`, {params});
  }

}
export default Policy_API
