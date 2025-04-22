<template>
	<el-container>
		<el-main ref="aiMain">
			<el-empty description="请新建点击新建对话或从历史记录中选择一个对话开始" v-show="activeChat.cid==undefined" style="height: 100%;">
				<el-button type="primary" round @click="newChat">新建聊天</el-button>
				<el-button round v-show="!showList" @click="showList=!showList">查看历史记录</el-button>
			</el-empty>
			<div class="chat_box" v-show="activeChat.cid!=undefined">
				<div class="head">
					<div class="chatNameInput">
						<input v-show="isRename" v-model="activeChat.chatName">
						<p v-show="!isRename">{{activeChat.chatName}}</p>
					</div>
					<el-popover placement="bottom" width="300" trigger="manual" v-model="isShowTip">
						<h3>强烈建议你修改对话名,以便之后你能快速找到它!!!</h3>
						<div style="text-align: right; margin: 0">
							<el-button type="primary" size="mini" @click.stop="isShowTip = false">确定</el-button>
						</div>
						<el-button size="small" :type="this.isRename ? 'success' :'primary'"
							:icon="this.isRename ? 'el-icon-check' : 'el-icon-edit'" circle
							style="margin: 0 10px; width: 32px; height: 32px;" slot="reference"
							@click="rename"></el-button>

					</el-popover>
					<el-button round v-show="!showList" @click="showList=!showList">查看历史记录</el-button>

					<el-select v-model="model" placeholder="请选择" size="small" style="margin-left: auto;">
						<el-option v-for="item in AiModelDict" :key="item.value" :label="item.label"
							:value="item.value">
							<!-- <el-badge :value="item.badge">{{item.value}}</el-badge> -->
							{{item.label}}
							<el-tag v-if="item.badge!=null" size="mini"
								style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">{{item.badge}}</el-tag>
						</el-option>
					</el-select>
				</div>
				<div class="dialogue_box" ref="dialogue_box">
					<div v-for="msg in msgList" :key="msg.mid" style="margin-top: 10px;">
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
		</el-main>
		<el-aside width="410px" style="padding: 20px;" v-show="showList">
			<el-card style="height: 100%;">
				<div slot="header" class="clearfix">
					<span>历史记录</span>
				</div>
				<div>
					<AiChatListItemVue v-for="(chat,idx) in chatList" :chat="chat" :click="selectChat"
						:delBtn="()=>delBtn(idx,chat.cid)" :key="chat.cid"></AiChatListItemVue>
				</div>
			</el-card>
		</el-aside>
	</el-container>
	<!-- 	<div style="background-color: #eff2f6;">
		
	</div> -->
</template>

<script>
	import axios from 'axios';
	import AiChatListItemVue from './AiChatListItem.vue';
	import MarkdownItVue from 'markdown-it-vue';
	import 'markdown-it-vue/dist/markdown-it-vue.css';
	// import {
	// 	SSEParser
	// } from '@/utils/sseParser';
	export default {
		components: {
			AiChatListItemVue,
			MarkdownItVue
		},
		data() {
			return {
				isShowTip: false,
				isRename: false,
				activeChat: {
					cid: undefined,
					chatName: undefined,
					createTime: undefined
				},
				model: 'Bailian',
				chatList: [],
				loading: false,
				isEnd: false,
				msgList: [],
				text: "",
				showList: true,
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
				main_width: 0,
				dialogue_box_height: 0,
				dialogue_box_scrollHeight: 0,
				debounceTimer: null, // 用于存储定时器
			}
		},
		mounted() {
			window.addEventListener("resize", this.debouncedUpdateSize)
			this.debouncedUpdateSize()
			this.getChatList()
		},
		beforeDestroy() {
			window.removeEventListener("resize", this.debouncedUpdateSize)
		},
		methods: {
			getChatList() {
				axios.post("/aichat/get_chat_list", {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.chatList = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			newChat() {
				axios.post("/aichat/new_chat", {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.activeChat = res.data.data
						this.chatList.push(this.activeChat)
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
					cid: this.activeChat.cid,
					newName: this.activeChat.chatName
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
			selectChat(chat) {
				this.activeChat = chat
			},
			delBtn(idx, cid) {
				axios.post(`/aichat/delchat/${cid}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.chatList.splice(idx, 1)
						if (this.activeChat.cid == cid) {
							this.cleanActiveChat()
						}
						this.$message.success(res.data.mes)
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			cleanActiveChat() {
				this.activeChat = {
					cid: undefined,
					chatName: undefined,
					createTime: undefined
				}
			},
			getChatMsg(cid) {
				axios.post(`/aichat/get_chat_msg/${cid}`, {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res => {
					if (res.data.status == 200) {
						this.msgList = res.data.data
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			send(e) {
				if (this.loading && this.text == "") return;
				if (this.msgList.length > 60) {
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
						cid: this.activeChat.cid,
						msg: temp_text,
						model: this.model
					})
				}).then(resp => {
					if (!resp.ok) {
						throw new Error(`请求失败: ${resp.status}`);
					}
					this.text = "";
					this.msgList.push({
						"role": "user",
						"content": temp_text
					})
					return resp.body.getReader();
				}).then(reader => {
					const decoder = new TextDecoder('utf-8');

					const idx = this.msgList.length;; //记录创建对象的idx
					this.msgList.push({
						"role": "assistant",
						"content": ""
					})

					const processStream = ({
						done,
						value
					}) => {
						if (done) {
							this.loading = false;
							return;
						}
						// 更新助手消息内容
						this.msgList[idx]["content"] += decoder.decode(value, {
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
			updateSize() {
				this.main_width = this.$refs.aiMain?.$el.offsetWidth || 0;
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
		watch: {
			'activeChat.cid': function(newVal, oldVal) {
				this.getChatMsg(newVal)
				this.debouncedUpdateSize()
			},
			main_width: {
				immediate: true,
				handler(newVal, oldVal) {
					if (newVal < 700) {
						this.showList = false
					} else {
						this.showList = true
					}
				}
			}
		},
		computed: {
			// dialogue_box_height() {
			// 	return this.$refs.dialogue_box.offsetHeight || 0;
			// },
			// dialogue_box_scrollHeight() {
			// 	return this.$refs.dialogue_box.scrollHeight || 0;
			// },
			dialogueBox_toBotton_coordinate() {
				return this.dialogue_box_scrollHeight - this.dialogue_box_height
			}
		},
	}
</script>

<style scoped>





</style>

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

		.head {
			width: 100%;
			height: 70px;
			padding: 0 20px;
			background-color: white;
			font-size: 18px;
			display: flex;
			align-items: center;
		}

		.chatNameInput {
			width: 220px;
			display: inline-block;
		}

		.chatNameInput * {
			height: 40px;
			line-height: 40px;
			font-size: 18px;
		}

		.dialogue_box {
			width: 100%;
			/* background-color: aquamarine; */
			flex: 1;
			padding: 10px;
			margin: 10px;
			overflow: auto;
			scroll-snap-align: end;
		}

		.input_field {
			width: 100%;
			display: flex;
			flex-direction: row;
			padding: 10px;
			/* justify-content: center;
			align-items: center; */

			.input_box {
				flex: 1;
				padding: 10px 16px 10px 16px;
				background-color: #f6f7fb;
				border-radius: 16px;
				box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
				font-size: 16px;
			}

			.button_box {
				display: flex;
				margin-top: auto;
			}

			.button_box button {
				width: 48px;
				height: 48px;
				border-radius: 360px;
				border: none;
				background-color: #6cf;
				font-size: 24px;
				margin-left: 10px;
				margin-bottom: 16px;
				color: aliceblue;
			}
		}
	}
</style>