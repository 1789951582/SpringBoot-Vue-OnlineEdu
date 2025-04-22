import VueRouter from "vue-router";
import Vue from "vue";
import LoginVue from "../components/user/Login.vue";
import RegisterVue from "../components/user/Register.vue";
import UserVue from "../view/User.vue";
import HomeVue from "../view/Home.vue";
import SubjectListVue from "../components/home/SubjectList.vue";
import SubjectVue from "../view/Subject.vue";
import ChapterVue from "../components/subject/Chapter.vue";
import TaskVue from "../components/subject/Task.vue";
import TestVue from "../components/subject/Test.vue";
import QuestionVue from "../components/subject/Question.vue";
import ResourcesVue from "../view/Resources.vue";
import AnswerVue from "../view/Answer.vue";
import AichatVue from "../components/home/Aichat.vue";

Vue.use(VueRouter)

const router = new VueRouter({
	routes: [
		{
			path: '/',
			redirect:'/home'
		} ,
		{
			path: '/home',
			name:'Home',
			meta: {
				requiresAuth: true
			},
			component:HomeVue,
			children:[{
				path:'',
				redirect:'subjectList'
			},{
				path:'subjectList',
				component:SubjectListVue
			},{
				path:'aiChat',
				component:AichatVue
			}]
		},
		{
			path: '/user',
			name: 'User',
			component: UserVue,
			children: [{
				path: '',
				redirect: 'login'
			}, {
				path: 'login',
				name: 'Login',
				component: LoginVue
			}, {
				path: 'register',
				name: 'Register',
				component: RegisterVue
			}]
		},
		{
			path:'/subject/:subjectId',
			name:'Subject',
			component: SubjectVue,
			meta: {
				requiresAuth: true
			},
			props:true,
			children:[{
				path:'',
				redirect:'chapter'
			},{
				path:'chapter',
				name: 'Chapter',
				component:ChapterVue,
				props:true
			},{
				path:'task',
				name: 'Task',
				component:TaskVue,
				props:true
			},{
				path:'test',
				name: 'Test',
				component:TestVue,
				props:true
			},{
				path:'question',
				name: 'Question',
				component:QuestionVue,
				props:true
			}]
		},
		{
			path:'/resource/:itemId',
			name:'Resource',
			component: ResourcesVue,
			props:true,
			meta: {
				requiresAuth: true
			},
		},
		{
			path:'/subject/:subjectId/answer/:testId',
			name:'Answer',
			component:AnswerVue,
			props:true,
			meta: {
				requiresAuth: true
			},
		}
	]
})

router.beforeEach((to, from, next) => {
	if (to.matched.some(record => record.meta.requiresAuth)) {
		const token = localStorage.getItem('token')
		// 如果没有 token，则跳转到登录页
		if (!token) {
			next({
				name: 'Login'
			})
		} else {
			next() // token 存在，放行
		}
	} else {
		next() // 不需要权限，直接放行
	}
})

export default router