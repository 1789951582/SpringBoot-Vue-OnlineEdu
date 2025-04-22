import VueRouter from "vue-router";
import Vue from "vue";
import AnswerView from "../views/AnswerView.vue";
import CourseView from "../views/CourseView.vue";
import ExamInfoView from "../views/ExamInfoView.vue";
import HomeView from "../views/HomeView.vue";
import UserView from "../views/UserView.vue";
import CourseList from "../components/home/CourseList.vue";
import BaseInfoVue from "../components/course/BaseInfo.vue";
import AnswerViewVue from "../views/AnswerView.vue";
import ExamInfoViewVue from "../views/ExamInfoView.vue";
import AichatVue from "../components/home/Aichat.vue";
import AnswerInfoViewVue from "../views/AnswerInfoView.vue";
import UserSettingVue from "../components/home/UserSetting.vue";

Vue.use(VueRouter)

const router = new VueRouter({
	routes: [{
			path: '/',
			redirect: '/home'
		},
		{
			path: '/home',
			name: 'Home',
			meta: {
				requiresAuth: true
			},
			component: HomeView,
			children: [{
				path: '/',
				redirect: 'courseList'
			}, {
				path: 'courseList',
				component: CourseList
			},{
				path: 'aiChat',
				component:AichatVue
			},{
				path:'setting',
				component:UserSettingVue
			}]
		},
		{
			path: '/user',
			name: 'User',
			component: UserView
		},
		{
			path: '/course/:courseId',
			name: 'Course',
			component: CourseView,
			props: true,
			children: [{
				path: '/',
				component: BaseInfoVue,
				props: true
			}, {
				path: 'testAnswer/:testId',
				name: 'TestAnswer',
				component: AnswerViewVue,
				props: true
			}, {
				path: 'examInfo/:testId',
				name: 'ExamInfo',
				component: ExamInfoViewVue,
				props: true
			},{
				path:'answerInfo/:testId',
				name:'AnswerInfo',
				component:AnswerInfoViewVue,
				props:true
			}]
		},
	]
})

router.beforeEach((to, from, next) => {
	if (to.matched.some(record => record.meta.requiresAuth)) {
		const token = localStorage.getItem('token')
		// 如果没有 token，则跳转到登录页
		if (!token) {
			next({
				name: 'User'
			})
		} else {
			next() // token 存在，放行
		}
	} else {
		next() // 不需要权限，直接放行
	}
})

export default router