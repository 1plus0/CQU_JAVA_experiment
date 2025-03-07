// store.js
import { createStore } from 'vuex';

const store = createStore({
  state() {
    return {
      // 状态
      count: 0,
    };
  },
  getters: {
    // 计算属性
    doubleCount(state) {
      return state.count * 2;
    }
  },
  mutations: {
    // 同步修改状态的方法
    increment(state) {
      state.count++;
    }
  },
  actions: {
    // 异步操作
    incrementAsync({ commit }) {
      setTimeout(() => {
        commit('increment');
      }, 1000);
    }
  }
});

export default store;