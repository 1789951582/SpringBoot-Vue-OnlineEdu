import Vue from "vue";
import Vuex from "vuex"
Vue.use(Vuex)

const store=new Vuex.Store({
	state:{
		token:undefined,
		uid:undefined,
		subject:{},
		questionTableData:{}
	},
	mutations:{
		init(state){
			if(state.uid==undefined){
				state.uid=localStorage.getItem('uid')
				state.token=localStorage.getItem('token')
			}
		},
		setSubject(state,data){
			state.subject=data
		},
		cleanQueTabDataMap(state){
			state.questionTableData={}
		},
		insertQueTabDataMap(state,data){
			state.questionTableData[data.type]=data.data
		},
		addQuestion(state,data){
			state.questionTableData[data.type].push(data.data)
		},
		updateQuestion(state,data){
			const idx= state.questionTableData[data.type].findIndex(question=>question.questionId==data.data.questionId)
			if (idx >= 0) state.questionTableData[data.type][idx]=data.data
		},
		delQuestion(state,data){
			const idx= state.questionTableData[data.type].findIndex(question=>question.questionId==data.questionId)
			if (idx >= 0) state.questionTableData[data.type].splice(idx, 1)
		}
	},
	actions:{
		
	}
})

export default store