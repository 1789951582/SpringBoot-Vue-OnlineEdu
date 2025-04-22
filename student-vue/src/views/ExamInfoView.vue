<template>
	<el-container>
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header content="练习详情" @back="goBack">
				</el-page-header>
			</div>
		</el-header>
		<el-main>
			<div style="width: 960px; margin: 0 auto;">
				<el-card style="width: 100%; height: auto; margin-bottom:30px;">
					<div slot="header">{{commitRecord.testTitle}}</div>
					<div class="center" style="flex-direction: column;">
						<h2 style="margin-bottom: 20px;">你的最高成绩为</h2>
						<div style="display: flex; width: 100%; justify-content: space-around;">
							<div class="img" :class="{'img1Transform': imgShow}">
								<img :src="imgSrc.fail1" alt="哭了" v-if="maxScore < 60">
								<img :src="imgSrc.pass1" alt="过了" v-else>
							</div>
							<div class="border center">
								<span style="font-size: 36px; font-weight: 600;">{{maxScore}}</span>
								<span>分数</span>
							</div>
							<div class="img" :class="{'img2Transform': imgShow}">
								<img :src="imgSrc.fail2" alt="哭了" v-if="maxScore < 60">
								<img :src="imgSrc.pass2" alt="过了" v-else>
							</div>
						</div>

						<div class="buttonBox">
							<el-button @click="toAnswerInfo">查看详情</el-button>
							<el-button type="info" @click="toRedo">重做一次</el-button>
						</div>
						<div style="width: 100%;padding-top: 10px;color: #999999;">
							<span>描述：{{commitRecord.testDescription}}</span>
						</div>
						
					</div>
				</el-card>
				<el-card style="width: 100%; height: auto;">
					<div slot="header">
						提交记录
					</div>
					<div>
						<el-table :data="commitRecord.testCommitList" style="width: 100%">
							<el-table-column prop="createTime" label="日期" :formatter="dateFormatterDay" width="180">
							</el-table-column>
							<el-table-column prop="createTime" label="时间" :formatter="dateFormatterTime" width="180">
							</el-table-column>
							<el-table-column prop="score" label="分数">
							</el-table-column>
						</el-table>
					</div>
				</el-card>
			</div>
		</el-main>
	</el-container>


</template>

<script>
	import axios from 'axios';
	export default {
		name: "ExamInfoView",
		props:{
			courseId:{
				// default:true
			},
			testId:{
				// default:true
			}
		},
		data() {
			return {
				uid: undefined,
				token: undefined,
				score: 0,
				imgShow: true,
				imgSrc: {
					fail1: require("@/assets/img/cry1.gif"),
					fail2: require('@/assets/img/cry2.jpg'),
					pass1: require('@/assets/img/good1.jpg'),
					pass2: require('@/assets/img/good2.gif')
				},
				commitRecord: {
					testCommitList:[]
				}
			}
		},
		beforeMount() {
			this.uid = window.localStorage.getItem("uid")
			this.token = window.localStorage.getItem("token")
			this.getcommitList()
		},
		methods: {
			getcommitList() {
				console.log(111)
				axios.post(`/api/test/commitList/${this.testId}`, {
					uid: this.uid,
					token: this.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.commitRecord = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			dateFormatterDay(row, column, cellValue) {
				if (!cellValue) return '';
				// 使用原生 Date 方法格式化为 'YYYY-MM-DD HH:mm:ss'
				const date = new Date(cellValue);
				const year = date.getFullYear();
				const month = (date.getMonth() + 1).toString().padStart(2, '0');
				const day = date.getDate().toString().padStart(2, '0');
				return `${year}-${month}-${day}`;
			},
			dateFormatterTime(row, column, cellValue) {
				if (!cellValue) return '';
				const date = new Date(cellValue);
				const hours = date.getHours().toString().padStart(2, '0');
				const minutes = date.getMinutes().toString().padStart(2, '0');
				const seconds = date.getSeconds().toString().padStart(2, '0');
				return `${hours}:${minutes}:${seconds}`;
			},
			goBack(){
				this.$router.go(-1)
			},
			toRedo(){
				this.$router.push(`/course/${this.courseId}/testAnswer/${this.testId}`)
			},
			toAnswerInfo(){
				this.$router.push(`/course/${this.courseId}/answerInfo/${this.testId}`)
			}
		},
		computed: {
			maxScore() {
				if (this.commitRecord.testCommitList.length === 0) return null;
				return Math.max(...this.commitRecord.testCommitList.map(item => item.score));
			}
		}
	}
</script>

<style >
	.border {
		border: 6px solid #36aafd !important;
		width: 160px;
		height: 160px;
		border-radius: 50%;
	}

	.center {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}

	.buttonBox {
		margin-top: 20px;
	}

	
	.marginTen: {
		margin: 10px !important;
	}
</style>
<style scoped>
	img {
		width: 160px;
	}
	
</style>