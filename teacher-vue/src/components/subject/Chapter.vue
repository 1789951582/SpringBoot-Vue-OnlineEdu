<template>
	<div>
		<el-card>
			<div slot="header" style="display: flex; align-items: center; justify-content: space-between;">
				<span>章节列表</span>
				<div>
					<el-checkbox v-model="isMyChapter" style="margin-right: 10px;">仅显示自己创建的</el-checkbox>
				</div>
			</div>
			<ul class="chapterList">
				<li v-for="chapter in courseList" class="task_item" :key="chapter.chapterId">
					<div class="title">{{chapter.chapterTitle}}</div>
					<span class="sub-num">创建人id:{{chapter.teacherId}}</span>

					<div class="btn">
						<el-button type="success" round size="small"
							@click="getChapterItem(chapter.chapterId)">发布</el-button>
					</div>
				</li>
			</ul>
		</el-card>
		<div class="courseSelect">
			<span class="font">班级:</span>
			<el-select class="select" v-model="courseId" placeholder="请选择">
				<el-option v-for="course in subject.courseList" :key="course.courseId" :label="course.classTitle"
					:value="course.courseId">
				</el-option>
			</el-select>
		</div>
		<div class="preview" v-if="courseId!=undefined">
			<div class="left">
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
						<Markdown v-else-if="resource.typeId === 3" :id="resource.resourceUrl"></Markdown>
					</div>
				</template>
			</div>
			<div class="right" v-loading="okload">
				<el-tree :data="treeData" default-expand-all :expand-on-click-node="false" @node-click="handleNodeClick">
					<span class="custom-tree-node" slot-scope="{ node, data }">
						<span>{{ node.label }}</span>
						<span>
							<el-button type="text" size="mini" @click="() => remove(node, data)">
								Delete
							</el-button>
						</span>
					</span>
				</el-tree>
			</div>
		</div>
		<el-dialog title="发布" :visible.sync="ChapterItemVisible" v-loading="ChapterItemLoading" width="28%">
			<el-table :data="ChapterItemList" @selection-change="ChapterItemSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column label="顺序" width="55">
					<template slot-scope="scope">{{countNum(scope.row)}}</template>
				</el-table-column>
				<el-table-column label="任务点" width="250" prop="itemTitle"></el-table-column>
				<el-table-column label="创建教师id" width="100" prop="teacherId"></el-table-column>
			</el-table>
			<div slot="footer">
				<el-button @click="ChapterItemVisible = false" round>取 消</el-button>
				<el-button type="primary" @click="ReleaseVisible = true" round>确 定</el-button>
			</div>
			<el-dialog title="提示" :visible.sync="ReleaseVisible" append-to-body width="30%">
				是否将
				<span v-for="ChapterItem in ChapterItemSelection">“{{ChapterItem.itemTitle}}”,</span>
				发布到
				<span>{{selectCouresTitle}}</span>
				<div slot="footer">
					<div style="width: 100%; display: flex; justify-content: center;">
						<el-button @click="ReleaseVisible = false" round>取 消</el-button>
						<el-button type="primary" @click="releaseTask" round :loading="okload">确 定</el-button>
					</div>
					<div style="width: 100%;text-align: left; font-size: 14px; color: #8b8b8b;">
						<el-divider content-position="left">温馨提示</el-divider>
						<p>1.若为追加发布(章节已发布)，则在任务点后追加。</p>
						<p>2.不勾选已发布的任务点不代表撤回发布</p>
						<p>3.重复发布已发布的任务点会覆盖掉原本的顺序</p>
					</div>
				</div>
			</el-dialog>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios'
	import Markdown from '../common/Markdown.vue'
	export default {
		components:{
			Markdown
		},
		props:{
			subjectId:{
				type: String,
				required:true
			}
		},
		data() {
			return {
				courseId: undefined,
				treeData: undefined,
				title: "",
				isMyChapter: false,
				// newChapterFormVisible: false,
				
				// delvisible: false,
				ChapterItemVisible: false,
				ChapterItemLoading: false,
				ChapterItemList: [],
				ChapterItemSelection: [],
				ReleaseVisible:false,
				okload:false,
				title:undefined,
				resourceList:[]
			}
		},
		methods: {
			getTask() {
				axios.post(`/api/course/${this.courseId}/get_task`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token
				}).then(res => {
					if (res.data.status == 200) {
						this.treeData = this.toTreeformat(res.data.data.chapters)
						this.okload = false
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			toTreeformat(data) {
				if (data.length <= 0) {
					return undefined;
				}
				return data.map(chapter => ({
					chapterId: chapter.chapterId,
					label: chapter.chapterTitle,
					children: chapter.items.map(item => ({
						label: item.itemTitle,
						itemId: item.itemId // 将 itemId 添加到节点数据中
					}))
				}));
			},
			ChapterItemSelectionChange(val) {
				this.ChapterItemSelection = val;
			},
			getChapterItem(chapterId) {
				if (this.courseId == undefined || this.courseId == null) {
					this.$message.warning("请先选择班级")
					return
				}
				this.ChapterItemVisible = true
				this.ChapterItemLoading = true
				axios.post(`/teacher/subject/get_chapter_item/${chapterId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token
				}).then(res => {
					if (res.data.status == 200) {
						this.ChapterItemList = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.ChapterItemLoading = false
				})
			},
			releaseTask() {
				this.okload=true
				const itemList=this.ChapterItemSelection.map((item,idx)=>({
					...item,
					idx:idx
				}))
				axios.post(`/teacher/subject/release_task/${this.courseId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					itemList:itemList
				}).then(res=>{
					if(res.data.status==200){
						this.$message.success("发布成功")
						setTimeout(()=>{
							this.getTask()
						},1000)
						
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.ReleaseVisible=false
					this.ChapterItemVisible=false
					this.okload=false
				})
			},
			countNum(row) {
				let idx = this.ChapterItemSelection.indexOf(row)
				return idx == -1 ? "" : idx + 1
			},
			
			remove(node, data) {
				let type=undefined
				let id=undefined
				if(data.chapterId==undefined){
					type="item"
					id=data.itemId
				}else{
					type="chapter"
					id=data.chapterId
				}
				axios.post(`/teacher/subject/${this.courseId}/del_task/${type}/${id}`,{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res=>{
					if(res.data.status==200){
						this.$message.success("删除成功")
						this.getTask()
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			handleNodeClick(data) {
				if (data.itemId) {
					this.title = data.label
					axios.post("/api/course/resource/" + data.itemId, {
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
				}
			},
		},
		computed: {
			subject() {
				return this.$store.state.subject
			},
			courseList() {
				console.log(111)
				if (this.isMyChapter) {
					return this.$store.state.subject.chapterList.filter(item => item.teacherId == this.$store.state.uid)
				} else {
					return this.$store.state.subject.chapterList
				}
			},
			selectCouresTitle(){
				if(this.courseId==undefined){
					return undefined
				}
				const idx=this.subject.courseList.findIndex(item=>item.courseId==this.courseId);
				return this.subject.courseList[idx].classTitle
			}
		},
		watch: {
			courseId(newVal, oldVal) {
				if (newVal != undefined || newVal != null) {
					this.getTask()
				}
			}
		}
	}
</script>


<style scoped>
	.courseSelect {
		margin: 10px;

		.font {
			margin-right: 10px;
		}

		.select {
			width: 350px;
		}
	}

	.preview {
		display: flex;

		.left {
			flex: 4;
			height: 200px;
			padding: 5px 10px;
		}

		.right {
			flex: 1;
			min-height: 500px;
			padding: 20px;
			background-color: #f1f2f3;
			border-radius: 20px;
			box-shadow: rgba(0, 0, 0, 0.1) 0px 6px 8px;
		}
	}

	.custom-tree-node {
		flex: 1;
		display: flex;
		align-items: center;
		justify-content: space-between;
		font-size: 14px;
		padding-right: 8px;
	}

	.resource {
		width: 100%;

		margin-top: 20px;
	}

	.chapterList {
		min-height: 140px;
		max-height: 280px;
		overflow: auto
	}
</style>
<style>
	/* 	.preview{
		.right{
			font-size: 24px;
			font-weight: 500;
		}
	} */
</style>