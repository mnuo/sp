import Cookies from 'js-cookie'

const app = {
  state: {
    sidebar: {
      rightShow: false,
      width: '360px',
      opened: !+Cookies.get('sidebarStatus'),
      withoutAnimation: false
    },
    // size: Cookies.get('size') || 'medium'
  },
  mutations: {
    TOGGLE_SIDEBAR: state => {
      if (state.sidebar.opened) {
        Cookies.set('sidebarStatus', 1)
      } else {
        Cookies.set('sidebarStatus', 0)
      }
      state.sidebar.opened = !state.sidebar.opened
      state.sidebar.withoutAnimation = false
    },
    SHOW_SIDEBAR: state => {
      // if (state.sidebar.opened) {
      //   Cookies.set('sidebarStatus', 1)
      // } else {
      //   Cookies.set('sidebarStatus', 0)
      // }
      // state.sidebar.opened = !state.sidebar.opened
      // state.sidebar.withoutAnimation = false
      state.sidebar.rightShow = true
    },
    HIDE_SIDEBAR: state => {
      // if (state.sidebar.opened) {
      //   Cookies.set('sidebarStatus', 1)
      // } else {
      //   Cookies.set('sidebarStatus', 0)
      // }
      // state.sidebar.opened = !state.sidebar.opened
      // state.sidebar.withoutAnimation = false
      state.sidebar.rightShow = false
    },
    // CLOSE_SIDEBAR: (state, withoutAnimation) => {
    //   Cookies.set('sidebarStatus', 1)
    //   state.sidebar.opened = false
    //   state.sidebar.withoutAnimation = withoutAnimation
    // },
    // SET_SIZE: (state, size) => {
    //   state.size = size
    //   Cookies.set('size', size)
    // }
  },
  actions: {
    toggleSideBar({ commit }) {
      commit('TOGGLE_SIDEBAR')
    },
    showSideBar({ commit }) {
      commit('SHOW_SIDEBAR')
    },
    hideSideBar({ commit }) {
      commit('HIDE_SIDEBAR')
    },
    // closeSideBar({ commit }, { withoutAnimation }) {
    //   commit('CLOSE_SIDEBAR', withoutAnimation)
    // },
    // setSize({ commit }, size) {
    //   commit('SET_SIZE', size)
    // }
  }
}

export default app
