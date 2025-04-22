import Vue from "vue";
import Vuex from "vuex"
Vue.use(Vuex)

const store=new Vuex.Store({
	state:{
		token:localStorage.getItem('token'),
		uid:localStorage.getItem('uid'),
		StuInfo:undefined,
		questionChat:{
			chatMap:{},
			msgMap:{}
		}
	},
	mutations:{
		setStuInfo(state,data){
			state.StuInfo=data
		},
		insertChatById(state,{key,data}){
			Vue.set(state.questionChat.chatMap, key, data)
		},
		insertMsgById(state,{key,data}){
			Vue.set(state.questionChat.msgMap, key, data)
		},
		rightPushMsg(state,{key,data}){
			if (!state.questionChat.msgMap[key]) {
				Vue.set(state.questionChat.msgMap, key, [])
			}
			state.questionChat.msgMap[key].push(data)
		}
	},
	actions:{
		
	}
})

export default store