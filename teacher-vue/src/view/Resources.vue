<template>
	<el-container style="height: 100%; border: 1px solid #eee">
		<el-header style="text-align: right; font-size: 12px">
			<div style="height: 100%; display: flex; align-items: center;">
				<el-page-header :content="resourceTitle" @back="goBack"></el-page-header>
			</div>
		</el-header>
		<el-main>

			<div style="display: flex;height: 100%; flex-direction: column; align-items: center;">

				<div style="width: 100%;;flex: 1; overflow: auto; background-color: #f9f9f9;">
					<div v-for="resource in resourceList" :key="resource.resource_id" class="resourceBox">
						<iframe v-if="resource.typeId === 2" class="resource" style="height: 750px;"
							:src="resource.resourceUrl" :key="resource.resourceId"></iframe>
						<vue-core-video-player v-else-if="resource.typeId === 1" :src="resource.resourceUrl"
							class="resource" :key="resource.resourceId"></vue-core-video-player>
						<img v-else-if="resource.typeId === 4" class="resource" style="height: auto;"
							:src="resource.resourceUrl" :key="resource.resourceId" />
						<Markdown v-else-if="resource.typeId === 3" :id="resource.resourceUrl" class="resource"
							:key="resource.resourceId"></Markdown>
						<div style="width: 100%; display: flex; justify-content: end; margin-top: 10px;">
							<el-button type="danger" icon="el-icon-delete" circle
								@click="delResourceMsgBox(resource.resourceId)"></el-button>
						</div>

					</div>
				</div>
				<div style="background: none; width: 100%; position: relative;">
					<!-- <el-button @click="editBoxIsShow=!editBoxIsShow" style="position: absolute; right: 30px; bottom: 30px;" type="primary" round>新增</el-button> -->
					<!-- <div class="editBtn" @click="editBoxIsShow=!editBoxIsShow">
						<p>新增</p>
					</div> -->
					<button class="editBtn" @click="editBoxIsShow=!editBoxIsShow">
						{{editBoxIsShow ? '收起' : '新增'}}
					</button>
				</div>
				<transition name="el-zoom-in-bottom">
					<div v-show="editBoxIsShow" class="editBox">

						<el-tabs tab-position="left" v-model="type">
							<el-tab-pane label="课件/视频/图片" name="1"></el-tab-pane>
							<el-tab-pane label="富文本" name="2"></el-tab-pane>
						</el-tabs>
						<div style="height: 100%;flex: 1;">
							<MarkdownEditVue v-if="type=='2'" v-model="markdownText" @submit="submitMD">
							</MarkdownEditVue>
							<el-upload v-else class="upload-demo" drag
								:action="`/teacher/subject/upload_resource/${itemId}`" multiple name="raw"
								:data="uploadData" :on-success="uploadSuccess" :before-upload="beforeUpload"
								v-loading="isload" accept=".jpg, .webp, .png, .mp4, .ppt, .pptx, .doc, .docx"
								:on-progress="uploadVideoProcess" :show-file-list="false">
								<i class="el-icon-upload"></i>
								<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
								<div class="el-upload__tip" slot="tip">只能上传PPT,Word,mp4,jpg,png,webp文件,文件大小小于100MB</div>
							</el-upload>
						</div>
					</div>
				</transition>
			</div>

		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	import MarkdownEditVue from '../components/common/MarkdownEdit.vue';
	import Markdown from '../components/common/Markdown.vue';
	// import ProgressVue from '../components/common/Progress.vue';
	export default {
		components: {
			MarkdownEditVue,
			Markdown,
			// ProgressVue
		},
		props: {
			itemId: {
				type: String,
				required: true
			}
		},
		data() {
			return {
				isload: false,
				resourceTitle: "课程简介",
				type: "1",
				markdownText: "",
				uploadData: {
					uid: undefined,
					token: undefined
				},
				pollingTimer: null, // 轮询计时器
				activePolling: false, // 轮询状态标志
				resourceList: [],
				editBoxIsShow: false,
				loadProgress: 0,
				// progressFlag: false
			}
		},
		mounted() {
			// window.addEventListener('resize', this.onWindowResize);
			// this.onWindowResize()
			this.getResourceList()
		},
		// beforeDestroy() {
		// 	window.removeEventListener('resize', this.onWindowResize);
		// },
		methods: {
			goBack() {
				this.$router.go(-1)
			},
			beforeUpload(file) {
				if (this.activePolling) {
					this.$notify({
						title: '警告',
						message: '服务器正在处理刚上传的文件，请等待完成后重试',
						type: 'warning'
					})
					return false
				}
				// 获取文件后缀并转为小写
				const fileExtension = file.name.slice(
					((file.name.lastIndexOf(".") - 1) >>> 0) + 2
				).toLowerCase()

				// 允许的后缀列表（注意检查是否需要修正.docx和.doc）
				const allowedExtensions = ['doc', 'docx', 'jpg', 'webp', 'png', 'mp4', 'ppt', 'pptx']

				// 校验文件类型
				if (!allowedExtensions.includes(fileExtension)) {
					this.$notify({
						title: '文件类型错误',
						message: '只能上传PPT,Word,mp4,jpg,png,webp文件',
						type: 'error'
					})
					console.log(111)
					return false
				}
				const h = this.$createElement
				this.loadProgress = 0
				this.notificationInstance = this.$notify({
					title: '开始上传',
					duration: 0,
					dangerouslyUseHTMLString: true,
					message: this.$createElement('div', {
						attrs: {
							id: 'upload-progress'
						}
					}, [
						this.$createElement('el-progress', {
							props: {
								percentage: this.loadProgress,
								status: this.loadProgress === 100 ? 'success' : undefined
							},
							style: {
								width: '300px'
							}
						})
					])
				});
				return true
			},
			uploadSuccess(res) {
				console.log(res)
				if (res.status == 200) {
					this.$notify({
						title: '上传成功',
						message: '资源上传成功,服务器正在处理您上传的文件,请勿手动刷新。',
						type: 'success'
					})
					this.startPolling(res.data)
				} else {
					this.$message.error(res.msg)
				}
			},
			// 启动轮询（带防抖）
			startPolling(resourceId) {
				if (this.activePolling) return
				this.activePolling = true
				this.isload = true
				this.pollingTimer = setTimeout(() => {
					this.Polling(resourceId)
				}, 4000)
			},
			// 停止轮询
			stopPolling() {
				this.activePolling = false
				clearTimeout(this.pollingTimer)
				this.pollingTimer = null
				this.isload = false
			},

			Polling(resourceId) {
				if (!this.activePolling) return

				axios.get(`/teacher/subject/upload_status/${resourceId}`).then(res => {
					if (res.data.status == 200) {
						this.$notify({
							title: '处理完成',
							message: '服务器处理完成，正在为你刷新资源列表...',
							type: 'success'
						})
						this.stopPolling()
						this.getResourceList()
					} else if (res.data.status == 400) {
						this.$notify({
							title: '处理失败',
							message: res.data.msg,
							type: 'error'
						})
						this.stopPolling()
					} else {
						this.pollingTimer = setTimeout(() => {
							this.Polling(resourceId)
						}, 4000)
					}
				}, err => {
					this.$notify({
						title: '轮询失败',
						message: "未知错误,请检查网络或通知系统管理员",
						type: 'error'
					})
					this.stopPolling()
					// this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			submitMD() {
				axios.post(`/teacher/subject/upload_markdown/${this.itemId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					markdownText: this.markdownText
				}).then(res => {
					if (res.data.status == 200) {
						this.$message.success("提交成功")
						this.resourceList.push(res.data.data)
					} else {
						this.$message.success(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			getResourceList() {
				axios.post(`/api/course/resource/${this.itemId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
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
			},
			delResourceMsgBox(resourceId) {
				this.$confirm('此操作将永久删除该文件, 是否继续?', '警告此操作不可逆', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.delResource(resourceId)
				})
			},
			delResource(resourceId) {
				axios.post(`/teacher/subject/del_resource/${resourceId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						const idx = this.resourceList.findIndex(resource => resource.resourceId ==
							resourceId);
						if (idx >= 0) this.resourceList.splice(idx, 1)
						this.$message.success("删除成功")
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			uploadVideoProcess(e, file, fileList) {
				this.loadProgress = parseInt(e.percent);
				if (this.notificationInstance) {
					const progressBar = document.querySelector('#upload-progress .el-progress');
					if (progressBar) {
						progressBar.__vue__.percentage = this.loadProgress;
						if(this.loadProgress == 100){
							progressBar.__vue__.status ='success'
							setTimeout(()=>{this.notificationInstance.close()},3000)
						}
						// progressBar.__vue__.status = this.loadProgress === 100 ? 'success' : undefined;
					}
				}
			}
		},
		beforeDestroy() {
			// 组件销毁时清除轮询
			this.stopPolling()
		}
	}
</script>

<style scoped>
	.upload-demo {
		width: 100%;
		height: 100%;
	}

	.resourceBox {
		width: 80%;
		/* margin: 20px auto; */
		margin: 20px auto;
		background-color: #e7ecf3;
		padding: 20px;
		border-radius: 20px;
	}

	.resource {
		width: 100%;
	}

	.editBox {
		width: 80%;
		display: flex;
		margin: 20px;
		height: 320px
	}

	.editBtn {
		width: 80px;
		height: 80px;
		line-height: 80px;
		text-align: center;
		font-size: 24px;
		border: none;
		border-radius: 180px;
		position: absolute;
		right: 36px;
		bottom: 30px;
		background-color: #b3c0d1;
	}
</style>