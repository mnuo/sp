import fetch from '@/utils/request'
const Deposit_API={
  findAllDepoStandard: (params)=>{
    return fetch.get(`/deposit/dDepositStandard/findAll`,{params})
  },

  findHistoryDeposit: (params)=>{
    return fetch.get(`/deposit/dDepositStandard/findHistoryDeposit`,{params})
  },

  findAllEdit: (params)=>{
    return fetch.get(`/deposit/dDepositStandard/findAllEdit?edit=`+params)
  },

  updateOneDeposit: (params)=>{
    return fetch.post(`/deposit/dDepositStandard/updateOneDeposit`,params)
  },
  inputQueryProdNoDepoStandard: (params)=>{
    return fetch.get(`/deposit/dNoDepositStandard/inputQueryProd`,{params})
  },
  findAllHistoryNoDepoStandard: (params)=>{
    return fetch.get(`/deposit/dNoDepositStandard/findAllHistory`,{params})
  },
  updatNoDepoStandard: (params)=>{
    return fetch.post(`/deposit/dNoDepositStandard/updatDepoStandard`,params)
  },
  inputQueryNoDepoProd: (params)=>{
    return fetch.get(`/deposit/dNoDepositCustomer/inputQueryNoDepoProd`,{params})
  }
}
export default Deposit_API;