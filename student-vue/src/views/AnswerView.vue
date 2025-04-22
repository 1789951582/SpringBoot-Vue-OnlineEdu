<!--考生答题界面-->
<template>
	<el-container style="height: 100%; background-color: #eee; border: 1px solid #eee">
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header content="课后练习" @back="goBack">
				</el-page-header>
			</div>
		</el-header>
		<el-container v-loading="isloading">
			<transition>
				<el-aside>
					<el-card :body-style="{ padding: '16px 0px' }">
						<ul class="l-top">
							<li>
								<a class="activeItem" href="javascript:;"></a>
								<span>当前</span>
							</li>
							<li>
								<a class="defitem" href="javascript:;"></a>
								<span>未答</span>
							</li>
							<li>
								<a class="overQuestion" href="javascript:;"></a>
								<span>已答</span>
							</li>
							<li>
								<a class="defitem badge" href="javascript:;"></a>
								<span>标记</span>
							</li>
						</ul>
					</el-card>
					<el-card :body-style="{ padding: '16px 10px' }">
						<div class="item">
							<div v-for="(value,key) in TestQueObj" :key="key">
								<p v-if="key=='MULTI'">选择题部分</p>
								<p v-else-if="key=='JUDGE'">判断题部分</p>
								<p v-else-if="key=='FILL'">填空题部分</p>
								<p v-else-if="key=='SUBJECTIVE'">客观题部分</p>
								<ul>
									<li v-for="item in value" :key="item.questionId">
										<a :class="{
											'activeItem': activityQueObj.questionIdx == item.questionIdx, 
											'defitem': activityQueObj.questionIdx != item.questionIdx,
											'badge': tagSet.has(item.questionIdx),
											'overQuestion':answerMap[item.questionId]!=null && answerMap[item.questionId]!=''
										}" href="javascript:;" @click="queSelectBtn(item)">
											<span>{{item.questionIdx}}</span>
										</a>
									</li>
								</ul>
							</div>
						</div>
						<el-button style="width: 100%;" type="info" @click="commitAnswers" round>提交</el-button>
					</el-card>
				</el-aside>
			</transition>

			<el-main>
				<el-card class="questionBox">
					<div slot="header" :key="activityQueObj.questionIdx">
						<span>第{{activityQueObj.questionIdx}}题</span>
						<span class="scoreText">(本小题{{activityQueObj.questionScore}}分)</span>
					</div>
					<div :key="question.questionId">
						<p class="questionText">{{question.question}}</p>
						<el-radio-group v-if="activityQueObj.questionType==1" v-model="answerMap[question.questionId]"
							size="medium">
							<el-radio class="questionItem" :label="'A'">{{question.answerA}}</el-radio>
							<el-radio class="questionItem" :label="'B'">{{question.answerB}}</el-radio>
							<el-radio class="questionItem" :label="'C'">{{question.answerC}}</el-radio>
							<el-radio class="questionItem" :label="'D'">{{question.answerD}}</el-radio>
						</el-radio-group>
						<el-radio-group v-else-if="activityQueObj.questionType==2"
							v-model="answerMap[question.questionId]" size="medium">
							<el-radio class="questionItem" :label="'T'">对</el-radio>
							<el-radio class="questionItem" :label="'F'">错</el-radio>
						</el-radio-group>
						<el-input class="fillInput" v-else-if="activityQueObj.questionType==3" v-model="answerMap[question.questionId]"
							placeholder="请输入答案"></el-input>
						<el-input v-else-if="activityQueObj.questionType==4" 
						class="subjectInput" type="textarea" 
						:autosize="{ minRows: 6}" 
						placeholder="请输入答案"
						v-model="answerMap[question.questionId]"></el-input>
					</div>
				</el-card>
				<el-card>
					<div style="width: 100%; display: flex; justify-content: space-around;">
						<el-button @click="pageTurning(0)"><i class="el-icon-arrow-left"></i>上一题</el-button>
						<el-button type="text" @click="tagBtn" style="color: red;font-size: 16px;">
							<i class="el-icon-collection-tag"></i>
							标记
						</el-button>
						<el-button @click="pageTurning(1)">下一题<i class="el-icon-arrow-right"></i></el-button>
					</div>
				</el-card>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		name:"AnswerView",
		props:{
			courseId:{
				required:true
			},
			testId:{
				required:true
			}
		},
		data() {
			return {
				isloading: false,
				uid: undefined,
				token: undefined,
				// testId: 1,
				activityQueObj: {
					"questionId": undefined,
					"questionIdx": undefined,
					"questionScore": undefined,
					"questionType": undefined
				},
				tagSet: new Set([]),
				TestQueObj: {
					// multi: [],
				},
				question: {
					"question": undefined,
					"questionId": undefined
				},
				answerMap: {

				}
			}
		},
		beforeMount() {
			this.uid = window.localStorage.getItem("uid")
			this.token = window.localStorage.getItem("token")
			this.getTestQueList()
		},
		methods: {
			queSelectBtn(obj) {
				this.activityQueObj = obj
			},
			tagBtn() {
				let newSet = new Set(this.tagSet);
				if (newSet.has(this.activityQueObj.questionIdx)) {
					newSet.delete(this.activityQueObj.questionIdx)
				} else {
					newSet.add(this.activityQueObj.questionIdx)
				}
				this.tagSet = newSet
			},
			pageTurning(direction) {
				let activityQueIdx = 0
				if (direction == 0) {
					if (this.activityQueObj.questionIdx <= 1) {
						this.$message.warning("已经在第一题了")
						return
					}
					activityQueIdx = this.mergedQuestions.indexOf(this.activityQueObj)
					activityQueIdx--
				} else {
					if (this.activityQueObj.questionIdx >= this.mergedQuestions.length) {
						this.$message.warning("已经在最后一题了")
						return
					}
					activityQueIdx = this.mergedQuestions.indexOf(this.activityQueObj)
					activityQueIdx++
				}
				this.activityQueObj = this.mergedQuestions[activityQueIdx]
			},
			getTestQueList() {
				this.isloading = true
				axios.post(`/api/test/get_test_paper/${this.testId}`, {
					uid: this.uid,
					token: this.token
				}).then(res => {
					if (res.data.status == 200) {
						this.TestQueObj = res.data.data.questionMap
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.isloading = false
				})
			},
			commitAnswers(){
				axios.post(`/api/test/commit_test/${this.testId}`,{
					uid:this.uid,
					token:this.token,
					answerMap:this.answerMap
				}).then(res=>{
					if(res.data.status==200){
						console.log(res.data.msg)
						this.$message(res.data.msg)
						this.goBack()
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			goBack(){
				this.$router.go(-1)
			},
		},
		watch: {
			TestQueObj: {
				handler(newData) {

					Object.keys(newData).some(key => {
						let value = newData[key];
						if (Array.isArray(value) && value.length > 0) { // 检查是否为数组且长度大于0
							this.activityQueObj = value[0]; // 获取数组的第一个元素
							return true; // 返回 true 可以中断 some 循环
						}
						return false;
					});
				}
			},
			activityQueObj: {
				handler(newData) {
					axios.post("/api/test/get_question/" + newData.questionType + "/" + newData.questionId, {
						uid: this.uid,
						token: this.token
					}).then(res => {
						if (res.data.status == 200) {
							this.question = res.data.data
						}
					})
				}
			},
		},
		computed: {
			mergedQuestions() {
				if (!this.TestQueObj) return []
				// return [
				// 	...(this.TestQueObj.MULTI || []),
				// 	...(this.TestQueObj.JUDGE || []),
				// 	...(this.TestQueObj.FILL || [])
				// ]
				if (!this.TestQueObj) return [];

				return Object.values(this.TestQueObj)
					.filter(Array.isArray) // 过滤出值为数组的属性
					.flat(); // 将二维数组扁平化为一维数组
			}
		}
	}
</script>

<style>
	.questionItem .el-radio__label {
		font-size: 18px !important;
		color: #606266 !important;
	}
</style>

<style scoped>
	.l-top {
		display: flex;
		justify-content: space-around;
	}

	.l-top li,
	.item li {
		display: flex;
		flex-direction: column;
	}

	.el-main {
		padding: 0;
		display: flex;
		flex-direction: column;
	}

	.l-top a,
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

	.badge {
		position: relative;
	}

	.badge::before {
		content: "";
		position: absolute;
		top: -2px;
		right: 2px;
		width: 8px;
		height: 8px;
		background-color: red;
		border-radius: 50%;
	}

	.el-card {
		margin: 10px;
	}

	.defitem {
		border: 1px solid #eee;
	}

	.overQuestion {
		background-color: #b3c0d1;
		border: none;
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

	.questionBox {
		flex: 1;
	}

	.activeItem {
		border: 1px solid #FF90AA;
	}

	.scoreText {
		float: right;
		color: red;
		font-style: italic;
	}

	.questionText {
		font-size: 18px;
	}

	.questionItem,
	.fillInput,
	.subjectInput{
		margin-top: 20px;
		display: block;
	}
</style>