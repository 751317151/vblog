import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
      token: '',
  //     userInfo: JSON.parse(sessionStorage.getItem("userInfo"))
      userInfo: JSON.parse(localStorage.getItem("userInfo"))
    },
    mutations: {
      SET_TOKEN: (state, token) => {
        state.token = token
        localStorage.setItem("vblogtoken", token)
      },
      SET_USERINFO: (state, userInfo) => {
        state.userInfo = userInfo
  //       sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
        localStorage.setItem("userInfo", JSON.stringify(userInfo));
      },
      REMOVE_INFO: (state) => {
  //       localStorage.setItem("vblogtoken", '')
  //       sessionStorage.setItem("userInfo", JSON.stringify(''))
  //       state.userInfo = {}
        state.token = ''
        state.userInfo = {}
        localStorage.setItem("vblogtoken", '')
        // sessionStorage.setItem("userInfo", JSON.stringify(''));
        localStorage.setItem("userInfo", JSON.stringify(''));
      }
    },
    getters: {
      getUser: state => {
        return state.userInfo
      }
    },
    actions: {},
    modules: {}
})
  
