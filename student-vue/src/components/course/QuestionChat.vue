<template>
	<div class="chat_box" v-if="myChat">
		<div class="head">
			<div class="chatNameInput">
				<input v-show="isRename" v-model="myChat.chatName">
				<p v-show="!isRename">{{myChat.chatName}}</p>
			</div>
			<el-button size="small" :type="this.isRename ? 'success' :'primary'"
				:icon="this.isRename ? 'el-icon-check' : 'el-icon-edit'" circle
				style="margin: 0 10px; width: 32px; height: 32px;" slot="reference"
				@click="rename"></el-button>
	
			<el-select v-model="model" placeholder="请选择" size="small" style="margin-left: auto;">
				<el-option v-for="item in AiModelDict" :key="item.value" :label="item.label"
					:value="item.value">
					{{item.label}}
					<el-tag v-if="item.badge!=null" size="mini"
						style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">{{item.badge}}</el-tag>
				</el-option>
			</el-select>
		</div>
		<div class="dialogue_box" ref="dialogue_box">
			<div v-for="msg in myMsgList" v-if="msg.role!='system'" :key="msg.mid" style="margin-top: 10px;">
				<div style="display: flex;">
					<div style="width: 36px;height: 36px;">
						<el-avatar size="medium"></el-avatar>
					</div>
					<div
						style="display: grid;align-items: center;flex-direction: column;margin-left: 10px;justify-self: flex-start;">
						<i style="height: 36px;line-height: 36px;font-size: 14px;">{{msg.role}}:</i>
						<markdown-it-vue :content='msg.content'></markdown-it-vue>
					</div>
				</div>
			</div>
		</div>
		<div class="input_field">
			<div class="input_box" @keydown.enter="handleEnter">
				<el-input type="textarea" v-model="text" placeholder="请输入内容"
					:autosize="{ minRows: 3, maxRows: 8  }" maxlength="8000" resize="none" show-word-limit
					clearable :disabled="isEnd">
				</el-input>
			</div>
			<div class="button_box">
				<el-button @click="send" type="primary" icon="el-icon-chat-line-round" circle :loading="loading"
					size="medium" :disabled="loading||isEnd"></el-button>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	import MarkdownItVue from 'markdown-it-vue';
	import 'markdown-it-vue/dist/markdown-it-vue.css';
	export default{
		components:{
			MarkdownItVue
		},
		props:{
			mapKey:{
				type:Number,
				required:true,
			}
		},
		data() {
			return{
				isRename:false,
				model:"Bailian",
				loading:false,
				chatLoad:false,
				text:"",
				// myChat:{
				// 	cid:undefined,
				// 	chatName:undefined,
				// 	createTime:undefined
				// },
				// myMsgList:[],
				isEnd:false,
				dialogue_box_height:0,
				dialogue_box_scrollHeight: 0,
				debounceTimer:null,
				AiModelDict: [{
					value: "Bailian",
					label: "通义Qwen2.5 3b",
					badge: 'free'
				}, {
					value: "Glm",
					label: '智谱GLM4 9B128K',
					badge: 'long',
				}, {
					value: 'Mistral',
					label: 'Mistral 7B32K',
					badge: "Overseas",
				}, {
					value: "Spark",
					label: '讯飞Spark Lite',
					badge: 'Short',
				}, {
					value: 'Hunyuan',
					label: '腾讯混元 Lite',
				}],
			}
		},
		// mounted() {
			
		// },
		beforeDestroy() {
			window.removeEventListener("resize", this.debouncedUpdateSize)
		},
		methods:{
			send(e) {
				if (this.loading && this.text == "") return;
				if (this.myMsgList.length > 60) {
					this.$message.warning("对话上限为30回")
					this.isEnd = true
					return
				};
			
				this.loading = true;
				e.target.blur();
				const dialogueBox = this.$refs.dialogue_box
			
				this.$nextTick(() => {
					dialogueBox.scrollTop = dialogueBox.scrollHeight;
				})
			
				const temp_text = this.text;
				fetch('/aichat/chat', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
					},
					body: JSON.stringify({
						uid: this.$store.state.uid,
						token: this.$store.state.token,
						cid: this.myChat.cid,
						msg: temp_text,
						model: this.model
					})
				}).then(resp => {
					if (!resp.ok) {
						throw new Error(`请求失败: ${resp.status}`);
					}
					this.text = "";
					this.myMsgList.push({
						"role": "user",
						"content": temp_text
					})
					return resp.body.getReader();
				}).then(reader => {
					const decoder = new TextDecoder('utf-8');
			
					const idx = this.myMsgList.length;; //记录创建对象的idx
					this.myMsgList.push({
						"role": "assistant",
						"content": ""
					})
			
					const processStream = ({
						done,
						value
					}) => {
						if (done) {
							this.loading = false;
							// this.activeChat.msgList=this.myMsgList
							return;
						}
						// 更新助手消息内容
						this.myMsgList[idx]["content"] += decoder.decode(value, {
							stream: true,
						});
			
						if (dialogueBox.scrollTop >= dialogueBox.scrollHeight - this.dialogue_box_height -
							300) {
							this.$nextTick(() => {
								dialogueBox.scrollTop = dialogueBox.scrollHeight;
							});
						}
						// 继续读取
						reader.read().then(processStream);
					};
					// 开始读取
					reader.read().then(processStream);
				}).catch(err => {
					this.loading = false;
					this.$notify.error(err.message);
					console.log(err.message)
				});
			},
			handleEnter(e) {
				// 阻止默认行为（防止插入换行符）
				console.log(111)
				event.preventDefault();
				this.send(e)
			},
			getChatMsg(cid) {
				axios.post(`/aichat/get_chat_msg/${cid}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						// this.myMsgList = res.data.data
						this.$store.commit('insertMsgById',{
							key:this.mapKey,
							data:res.data.data
						})
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			rename() {
				if (!this.isRename) {
					this.isRename = true
					return
				}
				axios.post("/aichat/rename", {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					cid: this.myChat.cid,
					newName: this.myChat.chatName
				}).then(res => {
					if (res.data.status == 200) {
						this.$message.success(res.data.msg)
						this.isRename = false
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			updateSize() {
				this.dialogue_box_height = this.$refs.dialogue_box.offsetHeight || 0;
				this.dialogue_box_scrollHeight = this.$refs.dialogue_box.scrollHeight || 0;
			},
			debouncedUpdateSize() {
				if (this.debounceTimer) {
					clearTimeout(this.debounceTimer); // 清除之前的定时器
				}
				this.debounceTimer = setTimeout(() => {
					this.updateSize(); // 在延迟后执行
				}, 200); // 延迟 200ms
			},
		},
		computed:{
			myChat(){
				return this.$store.state.questionChat.chatMap[this.mapKey] || undefined
			},
			myMsgList(){
				return this.$store.state.questionChat.msgMap[this.mapKey] || []
			},
			dialogueBox_toBotton_coordinate() {
				return this.dialogue_box_scrollHeight - this.dialogue_box_height
			}
		},
		watch:{
			// mapKey(newVal){
			// 	if(newVal!=-1){
			// 		this.myMsgList=this.$store.state.questionChat.msgMap[this.mapKey]
			// 	}
			// },
			'myChat.cid': function(newVal, oldVal) {
				if(newVal && this.myMsgList){
					this.getChatMsg(newVal)
					window.addEventListener("resize", this.debouncedUpdateSize)
					this.debouncedUpdateSize()
				}
				
			},
		}
	}
</script>

<style>
	.chat_box {
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
		flex-direction: column;
		min-width: -webkit-fill-available;
		/* padding: 10px; */
		background-color: aliceblue;
		justify-content: center;
		
		.dialogue_box {
			width: 100%;
			/* background-color: aquamarine; */
			flex: 1;
			padding: 10px;
			margin: 10px;
			overflow: auto;
			scroll-snap-align: end;
		}
	}
</style>