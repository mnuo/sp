import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import app from './modules/app'
import permission from './modules/permission'
import order from './modules/order'
import price from './modules/price'
import getters from './getters'

Vue.use(Vuex)


const store = new Vuex.Store({
  modules: {
    app,
    permission,
    order,
    price,
  },
  getters,
  plugins: [
    createPersistedState({ storage: window.sessionStorage }),
    // createPersistedState(),
    // createPersistedState({
    //   storage: {
    //     getItem: key => Cookies.get(key),
    //     // Please see https://github.com/js-cookie/js-cookie#json, on how to handle JSON.
    //     setItem: (key, value) =>
    //       Cookies.set(key, value, { expires: 3, secure: true }),
    //     removeItem: key => Cookies.remove(key),
    //   },
    // }),
  ]
})

export default store
