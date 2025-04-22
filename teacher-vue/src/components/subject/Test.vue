<template>
	<div>
		<el-card>
			<div slot="header" style="display: flex; align-items: center; justify-content: space-between;">
				<el-select class="select" v-model="selCourseId" placeholder="请选择班级" @change="getReleaseTest">
					<el-option v-for="course in subject.courseList" :key="course.courseId" :label="course.classTitle"
						:value="course.courseId">
					</el-option>
				</el-select>
				<div>
					<el-button type="success" round @click="cRelTest">发布</el-button>
				</div>
			</div>
			<ul class="chapterList">
				<li v-for="test in releaseTest" class="task_item" :key="test.testId">
					<div class="title">
						{{test.testTitle}}
					</div>
					<span class="sub-num">{{test.testDescription}}</span>
					<!-- <div class="btn" v-if="chapter.teacherId==$store.state.uid">
						<el-button title="编辑" type="primary" size="small" icon="el-icon-edit" circle
							></el-button>
						<el-button title="删除" type="danger" size="small" icon="el-icon-delete" circle
							></el-button>
					</div>
					<div class="btn" v-else>
						<el-button title="查看" size="small" icon="el-icon-search" circle></el-button>
					</div> -->
				</li>
			</ul>
		</el-card>
		<el-button type="success" round style="margin: 10px; margin-left: auto;display: block;" @click="newTest">新建练习</el-button>
		<el-table :data="subject.testList" stripe>
			<el-table-column label="试卷名" width="200">
				<template slot-scope="scope">
					{{scope.row.testTitle}}
					<!-- <el-tag v-if="scope.row.isPublic==1" size="small"
						style="position: absolute;right: 10px;">公开</el-tag> -->
				</template>
			</el-table-column>
			<el-table-column prop="testDescription" label="介绍" width="650">
			</el-table-column>
			<el-table-column prop="teacherId" label="创建教师id" width="200">
			</el-table-column>
			<el-table-column label="是否公开" width="150">
				<template slot-scope="scope">
					<el-select v-if="scope.row.teacherId==$store.state.uid" v-model="scope.row.isPublic" placeholder="请选择"
						@change="setPublic(scope.row.testId,$event)">
						<el-option key="1" label="是" :value="1"></el-option>
						<el-option key="0" label="否" :value="0"></el-option>
					</el-select>
					<div v-else>
						<span>是</span>
						<!-- <span>否</span> -->
					</div>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="250">
				<template slot-scope="scope">
					<el-button  round size="small" @click="rewriteTest(scope.row)">编辑</el-button>
					<el-button type="success" round size="small" @click="pRelTest(scope.row.testId)">发布</el-button>
					<el-button type="primary" round size="small" @click="toComposeQue(scope.row.testId)">组卷</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-dialog title="发布练习" :visible.sync="releaseTestVisible" width="500px">
			<el-form :model="releaseTestForm" status-icon label-position="left">
				<el-form-item label="班级" label-width="70px" prop="courseId">
					<el-select class="select" v-model="releaseTestForm.courseId" placeholder="请选择">
						<el-option v-for="course in subject.courseList" :key="course.courseId"
							:label="course.classTitle" :value="course.courseId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="试卷" label-width="70px" prop="testId">
					<el-select class="select" v-model="releaseTestForm.testId" placeholder="请选择">
						<el-option v-for="test in subject.testList" :key="test.testId" :label="test.testTitle"
							:value="test.testId">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="结束时间" label-width="70px" prop="endTime">
					<el-date-picker v-model="releaseTestForm.endTime" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="releaseTestVisible = false">取 消</el-button>
				<el-button type="primary" @click="releaseTestReq">确 定</el-button>
			</div>
		</el-dialog>
		<el-dialog :title="rewriteTestTitle" :visible.sync="rewriteTestVisible" width="500px">
			<el-form :model="testFrom" status-icon label-position="left">
				<el-form-item label="试卷名" label-width="70px" prop="testTitle">
					<el-input v-model="testFrom.testTitle"></el-input>
				</el-form-item>
				<el-form-item label="介绍" label-width="70px" prop="testId">
					<el-input v-model="testFrom.testDescription"></el-input>
				</el-form-item>
				<el-form-item v-if="testFrom.testId==undefined" label="是否公开" label-width="70px" prop="isPublic">
					<el-select v-model="testFrom.isPublic" placeholder="请选择">
						<el-option key="1" label="是" :value="1"></el-option>
						<el-option key="0" label="否" :value="0"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="rewriteTestVisible = false">取 消</el-button>
				<el-button v-if="testFrom.testId==undefined" type="primary" @click="newTestReq">确 定</el-button>
				<el-button v-else type="primary" @click="rewriteTestReq">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				selCourseId: undefined,
				releaseTest: [],
				releaseTestVisible: false,
				releaseTestForm: {
					courseId: undefined,
					testId: undefined,
					endTime: undefined
				},
				rewriteTestVisible:false,
				rewriteTestTitle:undefined,
				testFrom:{
					testId:undefined,
					testTitle:undefined,
					testDescription:undefined,
					isPublic:undefined
				}
			}
		},
		props:{
			subjectId:{
				type: String,
				required:true
			}
		},
		methods: {
			getReleaseTest() {
				axios.post(`/teacher/test/get_release_test/${this.selCourseId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token
				}).then(res => {
					if (res.data.status == 200) {
						this.releaseTest = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			cRelTest() {
				this.releaseTestForm.testId = undefined;
				this.releaseTestForm.endTime = undefined;
				this.releaseTestForm.courseId = this.selCourseId
				this.releaseTestVisible = true
			},
			pRelTest(testId) {
				this.releaseTestForm.courseId = undefined;
				this.releaseTestForm.endTime = undefined;
				this.releaseTestForm.testId = testId
				this.releaseTestVisible = true
			},
			releaseTestReq() {
				const formattedData = {
					...this.releaseTestForm,
					endTime: this.releaseTestForm.endTime ? this.releaseTestForm.endTime.toISOString() : null
				};
				axios.post("/teacher/test/release_test",{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					...formattedData
				}).then(res=>{
					if(res.data.status==200){
						if(this.releaseTestForm.courseId==this.selCourseId){
							const test=this.subject.testList.find(test=>test.testId==this.releaseTestForm.testId)
							const rTest={
								endTime:formattedData.endTime,
								status:1,
								testDescription:test.testDescription,
								testId:test.testId,
								testTitle:test.testTitle
							}
							this.releaseTest.push(rTest)
						}
						this.$message.success("发布成功")
						this.releaseTestVisible=false
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			setPublic(testId, newVal){
				axios.post(`/teacher/test/setPublic/${testId}/${newVal}`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res=>{
					if (res.data.status == 200) {
						this.$message.success("修改成功")
					} else {
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			cleanTestFrom(){
				this.testFrom={
					testId:undefined,
					testTitle:undefined,
					testDescription:undefined,
					isPublic:undefined
				}
			},
			rewriteTest(test){
				this.cleanTestFrom()
				this.testFrom.testId=test.testId
				this.testFrom.testTitle=test.testTitle
				this.rewriteTestTitle=test.testTitle
				this.testFrom.testDescription=test.testDescription
				this.rewriteTestVisible=true
				
			},
			newTest(){
				this.cleanTestFrom()
				this.rewriteTestTitle="新建"
				this.rewriteTestVisible=true
			},
			newTestReq(){
				axios.post(`/teacher/test/${this.subjectId}/new_test`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					...this.testFrom
				}).then(res=>{
					if(res.data.status==200){
						this.subject.testList.push(res.data.data)
						this.$message.success("添加成功")
						this.rewriteTestVisible=false
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			rewriteTestReq(){
				axios.post("/teacher/test/rewrite_test",{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					...this.testFrom
				}).then(res=>{
					if(res.data.status==200){
						const idx = this.subject.testList.findIndex(test => test.testId == this.testFrom.testId);
						this.subject.testList[idx].testTitle=this.testFrom.testTitle
						this.subject.testList[idx].testDescription=this.testFrom.testDescription
						this.$message.success("编辑成功")
						this.rewriteTestVisible=false
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			toComposeQue(testId){
				this.$router.push(`/subject/${this.subjectId}subjectId/answer/${testId}`)
			}
		},
		computed: {
			subject() {
				return this.$store.state.subject
			},
		}
	}
</script>

<style>
</style>