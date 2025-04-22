import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import VueCoreVideoPlayer from 'vue-core-video-player'
import router from './router';
import store from './store';

//  markdown编辑器
// import mavonEditor from 'mavon-editor'  //引入markdown编辑器
// import 'mavon-editor/dist/css/index.css';
// Vue.use(mavonEditor)

Vue.use(ElementUI);
Vue.use(VueCoreVideoPlayer)


Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
