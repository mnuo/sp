const order = {
    state: {  //存储变量
      name:"",      //用户名
      userInfo: {}, //用户信息
    },
    mutations: {  //设置变量方法
      setName(state, name) {
        state.name = name;
      },
      setInfo(state, info) {
        state.userInfo = info;
      },
    }
  };
  
  export default order;  //抛出模块