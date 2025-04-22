<template>
	<div>
		<el-card>
			<div slot="header" style="display: flex; align-items: center; justify-content: space-between;">
				<span>章节列表</span>
				<div>
					<el-checkbox v-model="isMyChapter" style="margin-right: 10px;">仅显示自己创建的</el-checkbox>
					<el-button type="success" round @click="newChapter">新增</el-button>
				</div>
			</div>
			<ul class="chapterList">
				<li v-for="chapter in chapterList" class="task_item" :key="chapter.chapterId">
					<div class="title">
						{{chapter.chapterTitle}}
					</div>
					<span class="sub-num">创建人id:{{chapter.teacherId}}</span>
					<div class="btn" v-if="chapter.teacherId==$store.state.uid">
						<el-button title="编辑" type="primary" size="small" icon="el-icon-edit" circle
							@click="editChapter(chapter)"></el-button>
						<el-button title="删除" type="danger" size="small" icon="el-icon-delete" circle
							@click="delChapter(chapter.chapterId,chapter.chapterTitle)"></el-button>
					</div>
					<div class="btn" v-else>
						<el-button title="查看" size="small" icon="el-icon-search" circle></el-button>
					</div>
				</li>
			</ul>
		</el-card>
		<div>
			<el-button style="float: right;" type="success" round @click="newItemVisible=true">新增任务点</el-button>
			<p style="color: red;margin: 10px;">注:1.允许把非公开的任务点绑定到公开章节，但不允许把公开任务点绑定到非公开的章节。2.仅对任务点做章节分类，不会影响到已发布的任务点</p>
		</div>
		<el-dialog :title="ChapterFormTitle" :visible.sync="ChapterFormVisible">
			<el-form :model="ChapterForm" label-position="left">
				<el-form-item label="章节名称" label-width="120px">
					<el-input v-model="ChapterForm.chapterTitle" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="其他人可见">
					<el-radio-group v-model="ChapterForm.isPublic">
						<el-radio label="1">是</el-radio>
						<el-radio label="0">否</el-radio>
					</el-radio-group>

				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="ChapterFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="strategy">确 定</el-button>
			</div>
		</el-dialog>
		<el-table :data="itemTable" stripe style="width: 100%" v-loading="isload">
			<el-table-column prop="itemTitle" label="任务名" width="300">
			</el-table-column>
			<el-table-column label="所属章节" width="300">
				<template slot-scope="scope">
					<el-select v-model="scope.row.chapterId" placeholder="请选择"
						@change="resetItemChapter(scope.row.itemId,$event)">
						<el-option v-for="chapter in subject.chapterList" :key="chapter.chapterId"
							:label="chapter.chapterTitle" :value="chapter.chapterId"
							:disabled="disChapter(scope.row.isPublic,chapter.isPublic)">
							{{chapter.chapterTitle}}
							<el-tag v-if="chapter.isPublic==1" size="small"
								style="position: absolute;right: 10px;">公开</el-tag>
						</el-option>
					</el-select>
				</template>
			</el-table-column>
			<el-table-column prop="teacherId" label="创建教师id" width="150">
			</el-table-column>
			<el-table-column label="是否公开" width="200">
				<template slot-scope="scope">
					<el-select v-model="scope.row.isPublic" placeholder="请选择"
						@change="resetItemPublic(scope.row.itemId,$event)">
						<el-option key="1" label="是" :value="1"
							:disabled="findChapter(scope.row.chapterId).isPublic==0"></el-option>
						<el-option key="0" label="否" :value="0"></el-option>
					</el-select>
				</template>
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="200">
				<template slot-scope="scope">
					{{ formatDate(scope.row.createTime) }}
				</template>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button round @click="toResourceView(scope.row.itemId)">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-dialog title="新增任务点" :visible.sync="newItemVisible">
			<el-form :model="ItemForm" status-icon :rules="rules" ref="ItemForm" label-position="left">
				<el-form-item label="任务点名称" label-width="120px" prop="itemTitle">
					<el-input v-model="ItemForm.itemTitle" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="其他人可见" prop="isPublic">
					<el-radio-group v-model="ItemForm.isPublic">
						<el-radio :label="1">是</el-radio>
						<el-radio :label="0">否</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="所属章节" prop="chapterId">
					<el-select v-model="ItemForm.chapterId" placeholder="请选择">
						<el-option v-for="chapter in subject.chapterList" :key="chapter.chapterId"
							:label="chapter.chapterTitle" :value="chapter.chapterId"
							:disabled="disChapter(ItemForm.isPublic,chapter.isPublic)">
							{{chapter.chapterTitle}}
							<el-tag v-if="chapter.isPublic==1" size="small"
								style="position: absolute;right: 10px;">公开</el-tag>
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="newItemVisible = false">取 消</el-button>
				<el-button type="primary" @click="newItemValidate">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			var validateIsPublic = (rule, value, callback) => {
				this.$refs.ItemForm.validateField('chapterId');
				callback();
			};
			// var validateChapter = (rule, value, callback) => {
			// 	disChapter(this.ItemForm.isPublic, chapter.isPublic)
			// };
			var validateChapter = (rule, value, callback) => {
				// const selectedChapter = this.subject.chapterList.find(chapter => chapter.chapterId == value);
				const selectedChapter = this.findChapter(value)
				if (this.disChapter(this.ItemForm.isPublic, selectedChapter.isPublic)) {
					callback(new Error('当前选择的章节不可用于此设置'));
				} else {
					callback();
				}
			};
			return {
				isload: false,
				isMyChapter: false,
				ChapterFormVisible: false,
				ChapterId: undefined,
				ChapterFormTitle: "",
				ChapterForm: {
					chapterTitle: undefined,
					isPublic: '1'
				},
				itemTable: [],
				newItemVisible: false,
				ItemForm: {
					itemTitle: undefined,
					chapterId: undefined,
					isPublic: 1
				},
				rules: {
					itemTitle: [{
						required: true,
						message: '请输入任务点名称',
						trigger: 'blur'
					}, ],
					isPublic: [{
						validator: validateIsPublic,
						trigger: 'change'
					}],
					chapterId: [{
							required: true,
							message: '选择任务点所属的章节',
							trigger: 'blur'
						},
						{
							validator: validateChapter,
							trigger: 'change'
						}
					]
				}
				// formIsRewrite:false,
				// ChapterItemList:[]
			}
		},
		props:{
			subjectId:{
				type: String,
				required:true
			}
		},
		mounted() {
			this.getItemList()
		},
		methods: {
			strategy() {
				if (this.ChapterId) {
					this.updateChapter()
				} else {
					this.newChapter()
				}
			},
			//新增
			newChapter() {
				this.ChapterId = undefined
				this.ChapterFormVisible = true
				this.ChapterFormTitle = "新建章节"
			},
			addChapter() {
				axios.post(`/teacher/subject/${this.subjectId}/add_chapter`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					chapterTitle: this.ChapterForm.name,
					isPublic: this.ChapterForm.isPublic
				}).then(res => {
					if (res.data.status == 200) {
						this.subject.chapterList.push(res.data.data)
						this.$message.success("添加成功")
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.ChapterFormVisible = false
				})
			},
			//删除
			delChapter(chapterId, chapterTitle) {
				this.$confirm(`是否删除“${chapterTitle}”章节,该操作无法撤回请谨慎操作,\n注意,删除章节不会删除章节所包含的任务点`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.commitDelChapter(chapterId)
				});
			},
			commitDelChapter(chapterId) {
				axios.post(`/teacher/subject/${this.subjectId}/del_chapter/${chapterId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.removeChapterItem(chapterId)
						this.$message.success("删除成功")
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			removeChapterItem(chapterId) {
				const index = this.subject.chapterList.findIndex(item => item.chapterId == chapterId);
				if (index >= 0) this.subject.chapterList.splice(index, 1);
			},
			//编辑
			editChapter(chapter) {
				this.ChapterFormTitle = chapter.chapterTitle
				this.ChapterForm.chapterTitle = chapter.chapterTitle
				this.ChapterForm.isPublic = chapter.isPublic.toString()
				this.ChapterId = chapter.chapterId
				this.ChapterFormVisible = true
			},
			updateChapter() {
				axios.post(`/teacher/subject/update_chapter/${this.ChapterId}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					chapterTitle: this.ChapterForm.chapterTitle,
					isPublic: this.ChapterForm.isPublic
				}).then(res => {
					if (res.data.status == 200) {
						const index = this.subject.chapterList.findIndex(item => item.chapterId == this.ChapterId);
						console.log(index, this.ChapterId)
						if (index >= 0) {
							this.subject.chapterList[index].chapterTitle = this.ChapterForm.chapterTitle;
							this.subject.chapterList[index].isPublic = this.ChapterForm.isPublic
							this.$message.success("更新成功")
						}
					} else {
						this.$message.error(res.data.meg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.ChapterFormVisible = false
				})
			},
			getItemList() {
				this.isload = true
				axios.post(`/teacher/subject/${this.subjectId}/get_item_list`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.itemTable = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				}).finally(() => {
					this.isload = false
				})
			},
			disChapter(iIsPublic, cIsPublic) {
				if (iIsPublic == 0) {
					return false
				} else {
					if (cIsPublic == iIsPublic) {
						return false
					}
					return true
				}
				// if (cIsPublic == 0) {
				// 	return false
				// } else {
				// 	if (cIsPublic == iIsPublic) {
				// 		return false
				// 	}
				// 	return true
				// }
			},
			resetItemChapter(itemId, newVal) {
				axios.post("/teacher/subject/reset_item_chapter", {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					itemId: itemId,
					newChapterId: newVal
				}).then(res => {
					if (res.data.status == 200) {
						this.$message.success("修改成功")
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			resetItemPublic(itemId, newVal) {
				axios.post("/teacher/subject/reset_item_public", {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					itemId: itemId,
					isPublic: newVal
				}).then(res => {
					if (res.data.status == 200) {
						this.$message.success("修改成功")
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			findChapter(chapterId) {
				return this.subject.chapterList.find(chapter => chapter.chapterId == chapterId);
			},
			formatDate(isoDateString) {
				const date = new Date(isoDateString);
				const year = date.getFullYear();
				const month = ('0' + (date.getMonth() + 1)).slice(-2); // Months are zero indexed
				const day = ('0' + date.getDate()).slice(-2);
				const hours = ('0' + date.getHours()).slice(-2);
				const minutes = ('0' + date.getMinutes()).slice(-2);

				return `${year}/${month}/${day} ${hours}:${minutes}`;
			},
			newItemValidate() {
				this.$refs.ItemForm.validate((valid) => {
					if (valid) {
						this.newItem()
					} else {
						this.$message.warning("请按照提示正确填写")
						return false;
					}
				});
			},
			newItem(){
				axios.post("/teacher/subject/new_Item",{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					...this.ItemForm
				}).then(res=>{
					if(res.data.status==200){
						this.itemTable.unshift(res.data.data)
						this.$message.success("添加成功")
						this.newItemVisible=false
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			toResourceView(itemId){
				this.$router.push(`/resource/${itemId}`)
			}
			// getChapterItem(chapterId){
			// 	this.isload = true
			// 	axios.post(`/teacher/subject/get_chapter_item/${chapterId}`, {
			// 		uid: this.$store.state.uid,
			// 		token: this.$store.state.token
			// 	}).then(res => {
			// 		if (res.data.status == 200) {
			// 			this.ChapterItemList = res.data.data
			// 		} else {
			// 			this.$message.error(res.data.msg)
			// 		}
			// 	}, err => {
			// 		this.$message.error("未知错误,请检查网络或通知系统管理员")
			// 	}).finally(() => {
			// 		this.isload = false
			// 	})
			// },
			// deleteItem(idx){
			// 	console.log(this.ChapterItemList[idx])
			// }
		},
		computed: {
			subject() {
				return this.$store.state.subject
			},
			chapterList() {
				if (this.isMyChapter) {
					return this.$store.state.subject.chapterList.filter(item => item.teacherId == this.$store.state.uid)
				} else {
					return this.$store.state.subject.chapterList
				}
			},

		},
		// watch:{
		// 	ChapterForm:{
		// 		handler(newVal,oldVal){
		// 			console.log(newVal==oldVal)
		// 			this.formIsRewrite=newVal==oldVal? false:true
		// 		},
		// 		immediate: true,
		// 		deep:true
		// 	}
		// }
	}
</script>

<style>
</style>