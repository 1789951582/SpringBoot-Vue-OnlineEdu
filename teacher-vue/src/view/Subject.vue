<template>
	<el-container style="height: 100%; border: 1px solid #eee" v-loading="isload">
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header :content="subject.subjectTitle" @back="goBack"></el-page-header>
			</div>
		</el-header>
		<el-main>
			<div class="main">
				<div style="width: 100%; display: flex;justify-content: center; margin-bottom: 10px;">
					<el-menu :default-active="$route.path" mode="horizontal" class="menu" :router="true">
						<el-menu-item :index="`/subject/${subjectId}/chapter`">章节</el-menu-item>
						<el-menu-item :index="`/subject/${subjectId}/task`">任务点管理</el-menu-item>
						<el-menu-item :index="`/subject/${subjectId}/test`">练习</el-menu-item>
						<el-menu-item :index="`/subject/${subjectId}/question`">题库</el-menu-item>
					</el-menu>
				</div>
				<!-- <TaskVue></TaskVue> -->
				<!-- <ChapterVue></ChapterVue> -->
				<!-- <TestVue></TestVue> -->
				<!-- <QuestionVue></QuestionVue> -->
				<router-view></router-view>
			</div>
		</el-main>
	</el-container>
</template>

<script>
import axios from 'axios'
// import ChapterVue from '../components/subject/Chapter.vue'
// import TaskVue from '../components/subject/Task.vue'
// import TestVue from '../components/subject/Test.vue'
// import QuestionVue from '../components/subject/Question.vue'
	export default{
		props:{
			subjectId:{
				type: String,
				required:true
			}
		},
		data() {
			return{
				isload:true,
			}
		},
		beforeMount() {
			this.$store.commit("init")
			this.get_subject()
			
		},
		beforeDestroy() {
			this.$store.commit('cleanQueTabDataMap')
		},
		methods:{
			get_subject(){
				axios.post(`/teacher/subject/get_subject/${this.subjectId}`,{
					uid:this.$store.state.uid,
					token:this.$store.state.token
				}).then(res=>{
					if(res.data.status==200){
						this.$store.commit('setSubject',res.data.data)
					}
					else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(()=>{
					this.isload=false
				})
			},
			goBack(){
				this.$router.push("/home")
			}
		},
		computed:{
			subject(){
				return this.$store.state.subject
			}
		},
		// watch:{
		// 	subjectId:{
		// 		handler(newVal, oldVal) {
		// 			this.get_subject()
		// 		},
		// 		immediate: true,
		// 	}
		// 	// subjectId(newVal, oldVal){
		// 	// 	this.get_subject()
		// 	// }
		// }
	}
</script>

<style>
	.main{
		width: 80%;
		margin: 0 auto;
		display: flex;
		justify-content: center;
		flex-direction: column;
		/* overflow: auto; */
	}
	.menu{
		display: inline-block !important;
	}
/* 	.tasksBox{
		width: 100%;
		height: 100px;
		background-color: aqua;
		margin-top: 20px;
		border-radius: 20px;
		border: ;
	} */
	.task_item {
		width: 265px;
		height: 100px;
		border-radius: 20px;
		display: inline-flex;
		flex-direction: column;
		/* display: flex;
		flex-direction: column; */
		margin: 0 10px 40px;
		padding: 10px;
		border: 1px solid #EBEEF5;
	
		&:hover {
			cursor: pointer;
			box-shadow: 0 3px 18px rgba(0, 0, 0, .2);
			transform: translateY(-2px);
			transition: all .3s;
		}
	
		.title {
			font-size: 18px;
		}
	
		.sub-num {
			margin-top: 5px;
			margin-left: 5px;
			font-size: 12px;
			color: #999;
		}
	
		.btn {
			display: flex;
			justify-content: flex-end;
		}
	}
</style>
