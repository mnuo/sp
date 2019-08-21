const price = {
  state: {
    priceHistoryObj: {}  //历史价格查询明细对象查询
  },
  mutations: {
    pushPirceHistoryObj(state, obj) {
      state.priceHistoryObj.startDate = obj.startDate;
      state.priceHistoryObj.addPrice = obj.addPrice;
      state.priceHistoryObj.price = obj.price;
    }
  },
  actions: {}
}

export default price
