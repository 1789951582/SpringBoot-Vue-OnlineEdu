<template>
	<div>
		<el-button style="display: block;margin-left: auto;" type="success" round @click="newQuestionBtn">新建</el-button>
		<el-tabs v-model="activeName" type="card">
			<el-tab-pane label="选择题" name="1"></el-tab-pane>
			<el-tab-pane label="判断题" name="2"></el-tab-pane>
			<el-tab-pane label="填空题" name="3"></el-tab-pane>
			<el-tab-pane label="主观题" name="4"></el-tab-pane>
		</el-tabs>
		<el-table :data="tableData[activeName]" style="width: 100%" v-loading="isload">
			<el-table-column prop="question" label="题干" width="900">
			</el-table-column>
			<el-table-column label="难度" width="100">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.level==1" type="success">简单</el-tag>
					<el-tag v-else-if="scope.row.level==2" type="warning">普通</el-tag>
					<el-tag v-else-if="scope.row.level==3" type="danger">困难</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="createTeacherId" label="创建教师id" width="150">
			</el-table-column>
			<el-table-column prop="updateTeacherId" label="最后一次修改教师id" width="150">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button round size="small" type="primary" @click="updataQuestionBtn(scope.row)">修改</el-button>
					<el-button round size="small" type="danger" @click="delQuestion(scope.row.questionId)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-dialog :title="newQuestionFrom.questionId==undefined ? '新建':'修改'" :visible.sync="questionDialogVisible" width="80%">
			<el-radio-group v-if="newQuestionFrom.questionId==undefined" v-model="newQuestiontype" size="small" style="margin-bottom: 10px;" @change="correct">
				<el-radio-button label="1">选择题</el-radio-button>
				<el-radio-button label="2">判断题</el-radio-button>
				<el-radio-button label="3">填空题</el-radio-button>
				<el-radio-button label="4">主观题</el-radio-button>
			</el-radio-group>
			<el-form label-position="right" :model="newQuestionFrom">
				<el-form-item label="题干">
					<el-input v-model="newQuestionFrom.question" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" resize="none"></el-input>
				</el-form-item>
				<div v-if="newQuestiontype==1">
					<el-form-item label="选项A" label-width="60px">
						<el-input v-model="newQuestionFrom.answerA" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="选项B" label-width="60px">
						<el-input v-model="newQuestionFrom.answerB"></el-input>
					</el-form-item>
					<el-form-item label="选项C" label-width="60px">
						<el-input v-model="newQuestionFrom.answerC"></el-input>
					</el-form-item>
					<el-form-item label="选项D" label-width="60px">
						<el-input v-model="newQuestionFrom.answerD"></el-input>
					</el-form-item>
					<el-form-item label="正确答案">
						<el-select v-model="newQuestionFrom.rightAnswer">
							<el-option key="A" label="选项A" value="A"></el-option>
							<el-option key="B" label="选项B" value="B"></el-option>
							<el-option key="C" label="选项C" value="C"></el-option>
							<el-option key="D" label="选项D" value="D"></el-option>
						</el-select>
					</el-form-item>
				</div>
				<el-form-item v-else-if="newQuestiontype==2" label="正确答案">
					<el-radio-group v-model="newQuestionFrom.rightAnswer">
						<el-radio label="T">对</el-radio>
						<el-radio label="F">错</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item v-else-if="newQuestiontype==3" label="正确答案">
					<el-input v-model="newQuestionFrom.rightAnswer"></el-input>
				</el-form-item>
				<el-form-item v-else-if="newQuestiontype==4" label="正确答案">
					<el-input v-model="newQuestionFrom.rightAnswer" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" resize="none"></el-input>
				</el-form-item>
				<el-form-item label="题目解析">
					<el-button type="text" style="display: block;margin-left: auto;">AI生成解析</el-button>
					<el-input v-model="newQuestionFrom.analysis" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" resize="none"></el-input>
				</el-form-item>
				<el-form-item label="难度">
					<el-select v-model="newQuestionFrom.level">
						<el-option key="1" label="简单" :value="1"></el-option>
						<el-option key="2" label="普通" :value="2"></el-option>
						<el-option key="3" label="困难" :value="3"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="questionDialogVisible = false">取 消</el-button>
				<el-button v-if="newQuestionFrom.questionId==undefined" type="primary" @click="newQuestion">确 定</el-button>
				<el-button v-else type="primary" @click="updateQuestion">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				isload: false,
				activeName: "1",
				// tableData: {},
				questionDialogVisible: false,
				newQuestiontype:"1",
				newQuestionFrom: {
				},
				// questionDialogTitle:""
			}
		},
		props:{
			subjectId:{
				type: String,
				required:true
			}
		},
		methods: {
			getQuestionList(type) {
				this.isload = true
				axios.post(`/teacher/test/${this.subjectId}/get_question_list/${type}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						// this.tableData[type] = res.data.data
						this.$store.commit('insertQueTabDataMap',{type:type,data:res.data.data})
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.isload = false
				})
			},
			correct(){
				if(this.newQuestionFrom.type=='1'||this.newQuestionFrom.type=='2'){
					this.newQuestionFrom.rightAnswer=undefined
				}
			},
			newQuestion(){
				axios.post(`/teacher/test/new_question/${this.newQuestiontype}`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					subjectId:this.subjectId,
					...this.newQuestionFrom
				}).then(res=>{
					if(res.data.status==200){
						if(this.tableData[this.newQuestiontype]!=undefined){
							// this.tableData[this.newQuestiontype].push(res.data.data)
							this.$store.commit('addQuestion',{type:this.newQuestiontype,data:res.data.data})
						}
						this.questionDialogVisible=false
						this.$message.success("添加成功")
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			newQuestionBtn(){
				// this.questionDialogTitle="新建",
				this.newQuestiontype=this.activeName
				this.newQuestionFrom={}
				this.questionDialogVisible=true
			},
			updataQuestionBtn(question){
				this.newQuestionFrom=question
				this.newQuestiontype=this.activeName
				// this.questionDialogTitle="修改",
				this.questionDialogVisible=true
			},
			updateQuestion(){
				axios.post(`/teacher/test/update_question/${this.newQuestiontype}`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					...this.newQuestionFrom
				}).then(res=>{
					if(res.data.status==200){
						// const idx= this.tableData[this.newQuestiontype].findIndex(question=>question.questionId==this.newQuestionFrom.questionId)
						// if (idx >= 0) this.tableData[this.newQuestiontype][idx]=this.newQuestionFrom
						this.$store.commit('updateQuestion',{type:this.newQuestiontype,data:this.newQuestionFrom})
						this.$message.success("修改成功")
						this.questionDialogVisible=false
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			delQuestion(questionId){
				axios.post(`/teacher/test/del_question/${this.activeName}/${questionId}`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res=>{
					if(res.data.status==200){
						// const idx= this.tableData[this.activeName].findIndex(question=>question.questionId==questionId)
						// if (idx >= 0) this.tableData[this.activeName].splice(idx, 1)
						this.$store.commit('delQuestion',{type:this.activeName,questionId:questionId})
						this.$message.success("删除成功")
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			}
		},
		computed:{
			tableData(){
				return this.$store.state.questionTableData
			}
		},
		watch: {
			activeName: {
				handler(newVal, oldVal) {
					if (this.tableData[newVal] == undefined) {
						this.getQuestionList(newVal)
					}
				},
				immediate: true,
			}
		}
	}
</script>

<style>
</style>