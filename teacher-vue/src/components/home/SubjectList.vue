<template>
	<el-container>
		<el-header height="100px" style="background: #eff2f6; text-align: right; font-size: 12px">
			<div style="padding: 10px; background-color: #fff; border-radius: 10px;margin-top: 10px;">
				<el-menu default-active="1" mode="horizontal" @select="selectMenu">
					<el-menu-item index="1">所有科目</el-menu-item>
					<el-menu-item index="0">我管理的科目</el-menu-item>
				</el-menu>
			</div>
		</el-header>
		<el-main style="background-color: #eff2f6;">
			<div style="width: 1550px; margin: 20px auto;">
				<div v-for="(o, index) in subjectList" class="course-item" :key="o.subjectId" @click="linkTo(o.subjectId)">
					<el-image fit="cover"
						:src="o.cover"
						class="img" />
					<div class="info">
						<div class="title ellipse">{{o.subjectTitle}}</div>
						<div class="bottom">
							<span class="sub-num">{{o.subjectDescription}}</span>
						</div>
					</div>
				</div>
			</div>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios'
import router from '../../router'
	export default {
		data() {
			return {
				courseActiveIdx: "1",
				pageNum: 1,
				pageSize: 20,
				records: []
			}
		},
		mounted() {
			this.getSubject()
		},
		methods: {
			selectMenu(index, indexPath) {
				this.courseActiveIdx = index
				// this.getCourse()
			},
			getSubject(){
				axios.post("/teacher/subject/get_subject_list",{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					pageNum:this.pageNum,
					pageSize:this.pageSize
				}).then(res=>{
					if(res.data.status==200){
						this.records=res.data.data
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			linkTo(subjectId){
				this.$router.push(`/subject/${subjectId}`)
			}
		},
		computed:{
			subjectList(){
				if(this.courseActiveIdx=='1' || this.records.length<=0){
					return this.records;
				}
				return this.records.filter(item=> item.directorId==this.$store.state.uid)
			}
		}
	}
</script>

<style scoped>
	.course-item {
		width: 270px;
		height: 250px;
		background-color: #FFFFFF;
		margin-bottom: 40px;
		border-radius: 10px;
		display: inline-block;
		margin: 0 20px 40px;

		&:hover {
			cursor: pointer;
			box-shadow: 0 3px 18px rgba(0, 0, 0, .2);
			transform: translateY(-2px);
			transition: all .3s;
		}

		.img {
			height: 140px;
			width: 100%;
			border-radius: 5px;
		}

		.info {
			padding: 5px 20px 12px 20px;
			color: #333;

			.title {
				font-size: 16px;
				font-weight: bold;
			}

			.middle {
				color: #999;
				font-size: 12px;
				margin: 3px 0;
			}

			.bottom {
				.free {
					font-size: 14px;
					color: #f4621f;
				}

				.sub-num {
					font-size: 12px;
					color: #999;
				}
			}
		}
	}
</style>