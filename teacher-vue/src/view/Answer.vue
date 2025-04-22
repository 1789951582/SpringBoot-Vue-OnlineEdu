<template>
	<el-container style="height: 100%; background-color: #eee; border: 1px solid #eee">
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header content="组卷" @back="goBack">
				</el-page-header>
			</div>
		</el-header>
		<el-container v-loading="isloading">
			<transition>
				<el-aside>
					<div style="height: 100%; display: flex; flex-direction: column; overflow: auto;">
						<!-- <el-card v-for="(value,key) in questionMap" :body-style="{ padding: '16px 10px' }" class="queBox">
							<div slot="header" class="clearfix">
								<span v-if="key=='MULTI'">选择题</span>
								<span v-else-if="key=='JUDGE'">判断题</span>
								<span v-else-if="key=='FILL'">填空题</span>
								<span v-else-if="key=='SUBJECTIVE'">主观题</span>
								<el-button style="float: right;" type="success" size="mini" round>添加</el-button>
							</div>
							<div class="item">
								<ul>
									<li v-for="question in value" :key="question.questionId">
										<a :class="question.questionId==activityQueObj.questionId ? 'activeItem': 'defitem'"
											href="javascript:;" @click="queSelectBtn(question)">
											<span>{{question.questionIdx}}</span>
										</a>
									</li>
								</ul>
							</div>
						
						</el-card> -->
						<el-card :body-style="{ padding: '16px 10px' }" class="queBox">
							<div slot="header" class="clearfix">
								<span>选择题</span>
								<el-button style="float: right;" type="success" size="mini" round
									@click="addQueBtn(1)">添加</el-button>
							</div>
							<div class="item">
								<ul>
									<li v-for="question in questionMap.MULTI" :key="question.questionId">
										<a :class="question.questionId==activityQueObj.questionId ? 'activeItem': 'defitem'"
											href="javascript:;" @click="queSelectBtn(question)">
											<span>{{question.questionIdx}}</span>
										</a>
									</li>
								</ul>
							</div>

						</el-card>
						<el-card :body-style="{ padding: '16px 10px' }" class="queBox">
							<div slot="header" class="clearfix">
								<span>判断题</span>
								<el-button style="float: right;" type="success" size="mini" round
									@click="addQueBtn(2)">添加</el-button>
							</div>
							<div class="item">
								<ul>
									<li v-for="question in questionMap.JUDGE" :key="question.questionId">
										<a :class="question.questionId==activityQueObj.questionId ? 'activeItem': 'defitem'"
											href="javascript:;" @click="queSelectBtn(question)">
											<span>{{question.questionIdx}}</span>
										</a>
									</li>
								</ul>
							</div>
						</el-card>
						<el-card :body-style="{ padding: '16px 10px' }" class="queBox">
							<div slot="header" class="clearfix">
								<span>填空题</span>
								<el-button style="float: right;" type="success" size="mini" round
									@click="addQueBtn(3)">添加</el-button>
							</div>
							<div class="item">
								<ul>
									<li v-for="question in questionMap.FILL" :key="question.questionId">
										<a :class="question.questionId==activityQueObj.questionId ? 'activeItem': 'defitem'"
											href="javascript:;" @click="queSelectBtn(question)">
											<span>{{question.questionIdx}}</span>
										</a>
									</li>
								</ul>
							</div>
						</el-card>
						<el-card :body-style="{ padding: '16px 10px' }" class="queBox">
							<div slot="header" class="clearfix">
								<span>主观题</span>
								<el-button style="float: right;" type="success" size="mini" round
									@click="addQueBtn(4)">添加</el-button>
							</div>
							<div class="item">
								<ul>
									<li v-for="question in questionMap.SUBJECTIVE" :key="question.questionId">
										<a :class="question.questionId==activityQueObj.questionId ? 'activeItem': 'defitem'"
											href="javascript:;" @click="queSelectBtn(question)">
											<span>{{question.questionIdx}}</span>
										</a>
									</li>
								</ul>
							</div>
						</el-card>
						<el-button @click="commitTestPaper" style="width: 100%; margin-top: 10px;" type="info" round>提交</el-button>
					</div>
				</el-aside>
			</transition>

			<el-main>
				<el-card class="questionBox">
					<div slot="header" v-if="activityQueObj!=undefined" style="display: flex; align-items: center; justify-content: space-between;">
						<span>题目详情</span>
						<div style="width: 300px;display: flex; align-items: center;">
							<span>本题分值：</span>
							<el-input type="number" v-model.number="activityQueObj.questionScore"
								style="flex: 1;"></el-input>
							<span style="margin-left: 10px;">总分值：{{sorce}}</span>
						</div>
						<el-button @click="delQue" type="danger" size="small" round>移除这道题</el-button>
					</div>
					<div v-if="activityQueObj!=undefined" :key="question.questionId" style="height: 100%;">
						<p class="questionText">{{question.question}}</p>
						<div v-if="activityQueObj.questionType==1">
							<span class="questionItem">选项A: {{question.answerA}}</span>
							<span class="questionItem">选项B: {{question.answerB}}</span>
							<span class="questionItem">选项C: {{question.answerC}}</span>
							<span class="questionItem">选项D: {{question.answerD}}</span>
						</div>
						<el-descriptions :column="2" border style="margin-top: 20px; font-size: 16px;">
							<el-descriptions-item label="正确答案">
								{{activityQueObj.questionType==2 ? judgeAnswerMap[question.rightAnswer]: question.rightAnswer}}
							</el-descriptions-item>
							<el-descriptions-item label="题目难度">
								<el-tag v-if="question.level==1" type="success">简单</el-tag>
								<el-tag v-else-if="question.level==2" type="warning">普通</el-tag>
								<el-tag v-else-if="question.level==3" type="danger">困难</el-tag>
							<!-- {{levelMap[question.level]}} -->
							</el-descriptions-item>
							<el-descriptions-item label="题目解析">{{question.analysis}}</el-descriptions-item>
						</el-descriptions>
					</div>
					<el-empty v-else description="你还没添加题目,赶紧添加吧"></el-empty>
				</el-card>
				<el-dialog :visible.sync="addQueStatus.addQueVisible">
						<el-table :data="tableData[addQueStatus.type]" style="width: 100%;" height="600" v-loading="isloading">
							<el-table-column prop="question" label="题干" width="500">
							</el-table-column>
							<el-table-column label="难度" width="100">
								<template slot-scope="scope">
									<el-tag v-if="scope.row.level==1" type="success">简单</el-tag>
									<el-tag v-else-if="scope.row.level==2" type="warning">普通</el-tag>
									<el-tag v-else-if="scope.row.level==3" type="danger">困难</el-tag>
								</template>
							</el-table-column>
							<el-table-column prop="createTeacherId" label="创建教师id" width="100">
							</el-table-column>
							<el-table-column prop="updateTeacherId" label="最后一次修改教师id" width="100">
							</el-table-column>
							<el-table-column label="操作">
								<template slot-scope="scope">
									<el-button v-if="computedIsSelect(scope.row.questionId)" @click="addQue(scope.row)" size="small" type="primary" round>添加</el-button>
									<span v-else>已选</span>
								</template>
							</el-table-column>
						</el-table>
				</el-dialog>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	import axios from 'axios'
	import {
		Switch
	} from 'element-ui'
	export default {
		props:{
			subjectId:{
				type: String,
				required:true
			},
			testId:{
				type: String,
				required:true
			}
		},
		data() {
			return {
				uid: undefined,
				token: undefined,
				isloading: false,
				questionMap: {},
				activityQueObj: undefined,
				delQueIdList:[],
				// testId: 1,
				question: {
					"question": undefined,
					"questionId": undefined
				},
				levelMap: {
					1: "简单",
					2: "普通",
					3: "困难"
				},
				judgeAnswerMap: {
					'T': "对",
					'F': "错"
				},
				processingOrder: ['MULTI', 'JUDGE', 'FILL', 'SUBJECTIVE'],
				addQueStatus: {
					type: undefined,
					addQueVisible: false
				},
				idSet:undefined
			}
		},
		mounted() {
			this.getTestQueList()
		},
		methods: {
			goBack() {
				this.$router.go(-1)
			},
			commitAnswers() {

			},
			getTestQueList() {
				this.isloading = true
				axios.post(`/api/test/get_test_paper/${this.testId}`, {
					uid: this.uid,
					token: this.token
				}).then(res => {
					if (res.data.status == 200) {
						this.questionMap = res.data.data.questionMap
						this.initSelectQue()
						// this.getFirstNonEmptyElement()
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.isloading = false
				})
			},
			getQuestion() {
				axios.post(
					`/teacher/test/get_question/${this.activityQueObj.questionType}/${this.activityQueObj.questionId}`, {
						uid: this.uid,
						token: this.token
					}).then(res => {
					if (res.data.status == 200) {
						this.question = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			queSelectBtn(obj) {
				this.activityQueObj = obj
			},
			reindexQuestionMap(type) {
				// 定义处理顺序并验证类型有效性
				// const processingOrder = ['MULTI', 'JUDGE', 'FILL', 'SUBJECTIVE'];
				const startIndex = type-1;

				// 有效性检查
				if (startIndex === -1) {
					console.error(`Invalid question type: ${type}`);
					return;
				}

				// 计算基准索引
				let baseIndex = this.processingOrder
					.slice(0, startIndex)
					.reduce((acc, curType) => acc + (this.questionMap[curType]?.length || 0), 0);

				// 批量更新后续索引
				for (let i = startIndex; i < this.processingOrder.length; i++) {
					const targetType = this.processingOrder[i];
					const questions = this.questionMap[targetType];

					if (!questions) continue;

					// 使用缓存提升循环效率
					const len = questions.length;
					for (let j = 0; j < len; j++) {
						questions[j].questionIdx = ++baseIndex; // 前置自增优化
					}
				}
			},
			initSelectQue() {
				Object.keys(this.questionMap).some(key => {
					let value = this.questionMap[key];
					if (Array.isArray(value) && value.length > 0) { // 检查是否为数组且长度大于0
						this.activityQueObj = value[0]; // 获取数组的第一个元素
						return true; // 返回 true 可以中断 some 循环
					}
					return false;
				});
			},
			getQuestionList(type) {
				this.isloading = true
				axios.post(`/teacher/test/${this.subjectId}/get_question_list/${type}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						// this.tableData[type] = res.data.data
						this.$store.commit('insertQueTabDataMap', {
							type: type,
							data: res.data.data
						})
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.isloading = false
				})
			},
			addQueBtn(type) {
				this.addQueStatus.type = type
				if (this.tableData[type] == undefined) {
					this.getQuestionList(type)
				}
				this.addQueStatus.addQueVisible = true
			},
			delQue(){
				this.delQueIdList.push(this.activityQueObj.questionId)
				console.log(this.questionMap[this.processingOrder[this.activityQueObj.questionType-1]])
				const idx= this.questionMap[this.processingOrder[this.activityQueObj.questionType-1]].findIndex(question=>question==this.activityQueObj)
				if (idx >= 0) this.questionMap[this.processingOrder[this.activityQueObj.questionType-1]].splice(idx, 1)
				console.log(idx)
				this.initSelectQue()
				this.reindexQuestionMap(this.activityQueObj.questionType)
			},
			addQue(que){
				const tempQue={
					questionId:que.questionId,
					questionIdx:this.questionMap[this.processingOrder[this.addQueStatus.type-1]].length,
					questionScore:0,
					questionType:this.addQueStatus.type
				}
				this.questionMap[this.processingOrder[this.addQueStatus.type-1]].push(tempQue)
				this.activityQueObj=tempQue
				this.reindexQuestionMap(this.addQueStatus.type)
				this.idSet=undefined
				this.addQueStatus.addQueVisible=false
			},
			computedIsSelect(id){
				const idx=this.questionMap[this.processingOrder[this.addQueStatus.type-1]].findIndex(que=>que.questionId==id)
				// console.log(idx,this.questionMap[this.processingOrder[this.addQueStatus.type-1]])
				if(idx>=0){
					return false
				}
				return true
			},
			commitTestPaper(){
				axios.post("/teacher/test/commit_test_paper",{
					uid: this.uid,
					token: this.token,
					testId:this.testId,
					questionMap:this.questionMap,
					delQueIdList:this.delQueIdList,
				}).then(res=>{
					if(res.data.status==200){
						
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			}
			// setQuestionScore(){
			// 	console.log(this.activityQueObj.questionScore!=undefined,this.activityQueObj.questionScore!=null ,this.activityQueObj.questionScore!="")
			// 	console.log(this.activityQueObj.questionScore)
			// 	if(this.activityQueObj.questionScore!=undefined ||this.activityQueObj.questionScore!=null ||this.activityQueObj.questionScore!=""){
			// 		const idx= this.questionMap[this.processingOrder[this.activityQueObj.questionType]].findIndex(question=>question.questionId==activityQueObj.questionId)
			// 		if (idx >= 0) this.questionMap[this.processingOrder[this.activityQueObj.questionType]][idx].questionScore=this.activityQueObj.questionScore
			// 	}

			// }
		},
		computed: {
			tableData() {
				return this.$store.state.questionTableData
			},
			sorce() {
				let totalScore = 0;
				// for (const key in this.questionMap) {
				// 	if (this.questionMap.hasOwnProperty(key)) {
				// 		const categoryArray = this.questionMap[key];
				// 		if(categoryArray==undefined || categoryArray.length<=0) continue
				// 		for (const item of categoryArray) {
				// 			totalScore += item.questionScore;
				// 		}
				// 	}
				// }
				for (const key in this.questionMap) {
						const categoryArray = this.questionMap[key];
						// console.log(categoryArray==undefined,categoryArray.length<=0)
						if(categoryArray==undefined || categoryArray.length<=0) continue
						for (const item of categoryArray) {
							totalScore += item.questionScore;
						}
				}
				return totalScore;
			}
		},
		watch: {
			activityQueObj: {
				handler() {
					this.getQuestion()
				}
			},
			'activityQueObj.questionScore': function(newValue) {
				if (newValue === null || newValue === undefined || newValue === '' || isNaN(newValue)) {
					this.activityQueObj.questionScore = 0; // 设置默认值
				}
			}
		}
	}
</script>

<style>
	.questionItem .el-radio__label {
		font-size: 18px !important;
		color: #606266 !important;
	}

	.questionItem {
		display: block !important;
		margin: 20px 0;
	}
</style>

<style scoped>
	.item {
		/* overflow: auto; */
	}

	.item li {
		display: flex;
		flex-direction: column;
	}

	.item a {
		width: 30px;
		height: 30px;
		padding: 10px;
		border-radius: 50%;
		text-align: center;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #000;
	}

	.item ul {
		width: 100%;
		display: flex;
		justify-content: space-around;
		flex-wrap: wrap;
		padding: 10px 0px;
		/* flex-direction: column; */
	}

	.item li {
		height: 40px;
		flex: 0 0 calc(100%/5);
		display: flex;
		align-items: center;
		justify-content: center;
		/* margin-left: 5px;
		margin-bottom: 10px; */
	}

	.defitem {
		border: 1px solid #eee;
	}

	.activeItem {
		border: 1px solid #FF90AA;
	}

	.queBox {
		/* flex: 1; */
		margin: 5px;
	}

	.questionBox {
		height: 100%;
	}

	.questionText {
		font-size: 18px;
	}
	::v-deep .el-tag{
		font-size: 16px !important;
	}
</style>