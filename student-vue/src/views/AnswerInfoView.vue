<template>
	<el-container style="height: 100%; background-color: #eee; border: 1px solid #eee">
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header content="习题详情" @back="goBack">
				</el-page-header>
			</div>
		</el-header>
		<el-container style="height: 90%" v-loading="isloading">
			<transition>
				<el-aside v-show="!showChatBox">
					<div style="height: 100%; display: flex; flex-direction: column; overflow: auto;">
						<el-card v-for="(value,key) in questionMap" :body-style="{ padding: '16px 10px' }"
							class="queBox" :key="key">
							<div slot="header" class="clearfix">
								<span v-if="key=='MULTI'">选择题</span>
								<span v-else-if="key=='JUDGE'">判断题</span>
								<span v-else-if="key=='FILL'">填空题</span>
								<span v-else-if="key=='SUBJECTIVE'">主观题</span>
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

						</el-card>
					</div>
				</el-aside>
			</transition>

			<el-main>
				<el-card class="questionBox">
					<div slot="header" v-if="activityQueObj!=undefined"
						style="display: flex; align-items: center; justify-content: space-between;">
						<span>题目详情</span>
						<div style="width: 300px;display: flex; align-items: center;">
							<span>本题分值：{{activityQueObj.questionScore}}</span>
							<span style="margin-left: 10px;">总分值：{{sorce}}</span>
						</div>
						<el-button v-show="!showChatBox" type="success" size="small" round @click="showChatBtn">询问AI</el-button>
						<el-button v-show="showChatBox" type="success" size="small" round @click="showChatBox=false">关闭AI窗口</el-button>
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
								<el-tag v-if="question.level==1" size="medium" type="success" effect="dark">简单</el-tag>
								<el-tag v-else-if="question.level==2" size="medium" type="warning" effect="dark">普通</el-tag>
								<el-tag v-else size="medium" type="danger" effect="dark">困难</el-tag>
							<!-- {{levelMap[question.level]}} -->
							</el-descriptions-item>
							<el-descriptions-item label="题目解析">{{question.analysis}}</el-descriptions-item>
						</el-descriptions>
						<div style="width: 100%;margin-top: 40px;">
							<el-table :data="tableData" v-if="activityQueObj.questionType!=4" stripe
								style="width: 100% " >
								<el-table-column prop="answer" label="答案">
								</el-table-column>
								<el-table-column prop="score" label="得分" width="80">
								</el-table-column>
								<el-table-column prop="testTitle" label="练习名" width="200">
								</el-table-column>
								<el-table-column prop="createTime" label="提交日期" width="200" :formatter="dateFormat">
								</el-table-column>
							</el-table>
							<el-collapse v-else accordion>
								<el-collapse-item v-for="item in tableData" :key="item.answerId" :name="item.answerId">
									<template #title>
										<div class="table-row">
											<span class="cell" style="flex: 1;">提交的答案: {{ item.answer }}</span>
											<span class="cell" style="width: 100px;">得分: {{ item.score }}</span>
											<span class="cell" style="width: 200px;">练习名: {{ item.testTitle }}</span>
											<span class="cell" style="width: 300px;">提交时间: {{dateFormat(null,null,item.createTime)}}</span>
										</div>
									</template>
									<markdown-it-vue v-if="item.aiMsg" :content='item.aiMsg'></markdown-it-vue>
									<!-- <div style="padding: 10px; font-size: 18px;">{{item.aiMsg}}</div> -->
								</el-collapse-item>
							</el-collapse>
						</div>
					</div>
					<el-empty v-else description="点击左侧题号,选择一个题目进行查看"></el-empty>
				</el-card>
			</el-main>
			<el-aside width="50%" v-show="showChatBox">
				<QuestionChatVue :mapKey="questionIdToChat"></QuestionChatVue>
			</el-aside>
		</el-container>
	</el-container>
</template>

<script>
	import axios from 'axios'
	import {
		Switch
	} from 'element-ui'
	import QuestionChatVue from '../components/course/QuestionChat.vue'
	import MarkdownItVue from 'markdown-it-vue';
	import 'markdown-it-vue/dist/markdown-it-vue.css';
	export default {
		components: {
			QuestionChatVue,
			MarkdownItVue
		},
		props: {
			courseId:{
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
				delQueIdList: [],
				// testId: 1,
				question: {
					"question": undefined,
					"questionId": undefined
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
				idSet: undefined,
				tableData: [],
				showChatBox: false,
				questionIdToChat: -1,
			}
		},
		mounted() {
			this.getTestQueList()
		},
		methods: {
			goBack() {
				this.$router.go(-1)
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
			computedIsSelect(id) {
				const idx = this.questionMap[this.processingOrder[this.addQueStatus.type - 1]].findIndex(que => que
					.questionId == id)
				// console.log(idx,this.questionMap[this.processingOrder[this.addQueStatus.type-1]])
				if (idx >= 0) {
					return false
				}
				return true
			},
			getAnswerTable() {
				axios.post(
					`/api/test/get_answer_info/${this.activityQueObj.questionType}/${this.activityQueObj.questionId}`, {
						uid: 100000009,
						token: this.token
					}).then(res => {
					if (res.data.status == 200) {
						this.tableData = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			formatTableName(item) {
				return `提交的答案:${item.answer}\t\t得分:${item.score}\t\t练习名:${item.testTitle}\t\t提交时间:${item.createTime}`
			},
			async showChatBtn() {
				const {
					questionId,
					questionType
				} = this.activityQueObj;
				if (!this.questionChat.chatMap[questionId]) {
					this.isloading = true
					try {
						const res = await axios.post(`/aichat/get_question_chat/${questionType}/${questionId}`, {
							uid: 100000009,
							token: this.token
						})
						if (res.data.status === 200) {
							this.$store.commit('insertChatById',{
								key:questionId,
								data:res.data.data
							})
							this.questionIdToChat=questionId
							this.showChatBox = true
						} else {
							this.$message.error(res.data.msg);
						}
					} catch (e) {
						this.$message.error("未知错误,请检查网络或通知系统管理员")
					}finally{
						this.isloading = false
					}
				}else{
					this.questionIdToChat=questionId
					this.showChatBox = true
				}
			},
			dateFormat(row, column, cellValue) {
				if (!cellValue) return '';
				// 使用原生 Date 方法格式化为 'YYYY-MM-DD HH:mm:ss'
				const date = new Date(cellValue);
				const year = date.getFullYear();
				const month = (date.getMonth() + 1).toString().padStart(2, '0');
				const day = date.getDate().toString().padStart(2, '0');
				const hours = date.getHours().toString().padStart(2, '0');
				const minutes = date.getMinutes().toString().padStart(2, '0');
				const seconds = date.getSeconds().toString().padStart(2, '0');
				return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
			},
		},
		computed: {
			questionChat() {
				return this.$store.state.questionChat
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
					if (categoryArray == undefined || categoryArray.length <= 0) continue
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
					this.getAnswerTable()
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

	.table-row {
		display: flex;
		align-items: center;
		width: 100%;
		font-size: 16px;
	}

	.cell {
		display: inline-block;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		box-sizing: border-box;
	}
	::v-deep .el-tag{
		font-size: 16px !important;
	}
</style>