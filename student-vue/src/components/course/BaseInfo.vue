<template>
	<el-container style="height: 100%; border: 1px solid #eee">
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header :content="CourseInfo.subjectTitle" @back="goBack">
					
				</el-page-header>
				<span v-if="!CourseInfo.courseStatus" style="margin-left: 10px; color: red; font-size: 16px;font-style: italic">已结课</span>
			</div>
		</el-header>
		<el-main>
			<div style="display: flex; justify-content: center;margin-bottom: 10px;">
				<el-menu :default-active="'1'" @select='menuselect' class="el-menu-demo" mode="horizontal"
					text-color="#444444" active-text-color="#b3c0d1">
					<el-menu-item index="1">课程任务</el-menu-item>
					<el-menu-item index="2">课后练习</el-menu-item>
				</el-menu>
			</div>
			<div v-if="activeIndex=='1'" style="width: 1660px; height: auto; margin: 0 auto;  display: flex;">
				<div style="flex: 4;padding: 10px 30px 0px 0px;">
					<div>
						<p v-if="!title" class="resource" style="text-align: center; color: #333;">请先选择你要学习的课程任务</p>
						<template v-else>
							<h1 class="resource" style="text-align: center;">{{ title }}</h1>
							<div v-for="resource in resourceList" :key="resource.resource_id" class="resource">
								<iframe v-if="resource.typeId === 2" class="resource" style="height: 750px;"
									:src="resource.resourceUrl"></iframe>
								<vue-core-video-player v-else-if="resource.typeId === 1"
									:src="resource.resourceUrl"></vue-core-video-player>
								<img v-else-if="resource.typeId === 4" class="resource" style="height: auto;"
									:src="resource.resourceUrl" />
								<Markdown v-else-if="resource.typeId === 3" :id="resource.resourceUrl" :uid="uid"
									:token="token"></Markdown>
							</div>
						</template>
					</div>
				</div>
				<div style="flex: 1; ">
					<div style="width: 100%; min-height: 500px; padding: 20px;background-color: #f1f2f3; border-radius: 10px; font-size: 16px; font-weight: 500;box-shadow: rgba(0, 0, 0, 0.1) 0px 6px 8px;">
						<el-tree :data="treeData" accordion @node-click="handleNodeClick"></el-tree>
					</div>
					
				</div>
			</div>
			<div v-else-if="testList" style="width: 1660px; margin: 0 auto;" class="wrapper">
				<ul class="paper" v-loading="loading">
					<li class="item" v-for="item in testList.records" :key="item.testId" @click="linkToTest(item.testId,item.hasCommit)">
						<h3>{{item.testTitle}}</h3>
						<p class="name">{{item.testDescription}}</p>
						<div class="info">
							<i v-if="item.status != 0" class="el-icon-loading"></i>
							<span>{{ item.endTime.substr(0,10) }}</span>
							<i v-if="item.status == 0" style="color: red;">已结束</i>
							<i v-if="item.hasCommit" style="color: green;float: right;">已完成</i>
						</div>
					</li>
				</ul>
				<el-pagination style="text-align: center; margin-top: 80px;" layout="prev, pager, next"
					:total="testList.total" :page-size="testList.size" :current-page="testList.current"
					:page-count="testList.pages" @current-change="paginationChange">
				</el-pagination>
			</div>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	import {
		of
	} from 'core-js/features/array';
	import Markdown from '@/components/course/Markdown.vue'
	export default {
		components:{
			Markdown
		},
		props:{
			courseId:{
				default:true
			}
		},
		data() {
			return {
				CourseInfo:{},
				uid: undefined,
				token: undefined,
				activeIndex: "1",
				title: "",
				treeData: undefined,
				resourceList: [],
				testList:undefined,
				loading: false,
				testPageNum: 1
			}
		},
		beforeMount() {
			this.uid = window.localStorage.getItem("uid")
			this.token = window.localStorage.getItem("token")
			this.getCourseInfo()
		},
		methods:{
			goBack(){
				this.$router.push(`/home`)
			},
			getCourseInfo(){
				this.loading=true
				axios.post(`/api/course/get_course_info/${this.courseId}`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token
				}).then(res=>{
					if(res.data.status==200){
						this.CourseInfo=res.data.data
						this.getTask()
					}else{
						this.confirm(res.data.msg,"错误",{
							confirmButtonText: '确定',
							type:'error'
						})
					}
				})
			},
			getTask() {
				axios.post(`/api/course/${this.courseId}/get_task`, {
					uid: this.uid,
					token: this.token
				}).then(res => {
					if (res.data.status == 200) {
						this.treeData = this.toTreeformat(res.data.data.chapters)
						this.loading=false
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			toTreeformat(data) {
				if (data.length <= 0) {
					return [];
				}
				return data.map(chapter => ({
					label: chapter.chapterTitle,
					children: chapter.items.map(item => ({
						label: item.itemTitle,
						itemId: item.itemId // 将 itemId 添加到节点数据中
					}))
				}));
			},
			handleNodeClick(data) {
				if (data.itemId) {
					this.title = data.label
					axios.post("/api/course/resource/" + data.itemId, {
						uid: this.uid,
						token: this.token
					}, {
						'Content-Type': 'application/json;charset=UTF-8'
					}).then(res => {
						if (res.data.status == 200) {
							this.resourceList = res.data.data
						} else {
							this.$message.error(res.data.msg)
						}
					}, err => {
						this.$message.error("未知错误,请检查网络或通知系统管理员")
					})
				}
			},
			menuselect(index, indexPath) {
				this.activeIndex = index
			},
			getExamInfo() {
				axios.post(`/api/course/${this.courseId}/get_test`, {
					uid: this.uid,
					token: this.token,
					pageNum: this.testPageNum,
					pageSize: 8
				}).then(res => {
					if (res.data.status == 200) {
						this.testList = res.data.data
					}
				}, err => {
					console.log(error)
				})
			},
			paginationChange(value){
				this.testPageNum=value
				this.getExamInfo()
			},
			linkToTest(testId,hasCommit){
				if(hasCommit){
					this.$router.push(`${this.$route.path}/examInfo/${testId}`)
				}else{
					this.$router.push(`${this.$route.path}/testAnswer/${testId}`)
				}
			}
			
		},
		watch:{
			activeIndex:{
				handler(newData){
					console.log(111)
					if(newData=="2" && !this.testList){
						this.getExamInfo()
					}
				}
			}
		}
	}
</script>

<style scoped>

</style>