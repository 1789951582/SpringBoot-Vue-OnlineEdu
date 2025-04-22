<template>
	<div class="item_box" @click="click(chat)">
		<div class="chatNameInput">
			<input :ref="refKey" v-model="chatName" v-show="isSetName" @click.stop>
			<p v-show="!isSetName">{{chatName}}</p>
		</div>
		<div class="chat_list_btn">
			<p style="margin-right: 10px; color: #8a8a8a;font-size: 12px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{dateFormat(chat.createTime)}}</p>
			<el-button type="primary" icon="el-icon-edit" circle size="mini" v-show="!isSetName" @click.stop="renameBtn"></el-button>
			<el-button type="success" icon="el-icon-check" circle size="mini" v-show="isSetName" @click.stop="rename"></el-button>
			<el-button type="danger" icon="el-icon-delete" circle size="mini" @click.stop="delBtn"></el-button>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
	export default{
		name:'AiChatListItem',
		props:{
			chat:{
				type:Object,
				required:true,
				defaut:()=>({
					cid:undefined,
					chatName:undefined,
					createTime:undefined
				})
			},
			click:{
				type:Function,
				required:true
			},
			delBtn:{
				type:Function,
				required:true
			}
		},
		data() {
			return{
				isSetName:false,
				chatName:this.chat.chatName,
			}
		},
		methods:{
			renameBtn(){
				this.isSetName=true
				this.$refs[this.refKey].focus;
			},
			rename(){
				this.isSetName=false
				axios.post("/aichat/rename",{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					cid: this.chat.cid,
					newName: this.chatName
				}).then(res=>{
					if(res.data.status==200){
						this.$message.success(res.data.msg)
						this.isSetName = false
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			dateFormat(time) {
				if (!time) return '';
				// 使用原生 Date 方法格式化为 'YYYY-MM-DD HH:mm:ss'
				const date = new Date(time);
				const year = date.getFullYear();
				const month = (date.getMonth() + 1).toString().padStart(2, '0');
				const day = date.getDate().toString().padStart(2, '0');
				const hours = date.getHours().toString().padStart(2, '0');
				const minutes = date.getMinutes().toString().padStart(2, '0');
				const seconds = date.getSeconds().toString().padStart(2, '0');
				return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
			},
		},
		computed:{
			refKey(){
				return `chatNameRef${this.chat.cid}`
			}
		},
		watch:{
			chatName(){
				this.chat.chatName=this.chatName
			}
		}
	}
</script>

<style scoped>
	.item_box{
		width: 100%;
		height: 100%;
		padding: 10px;
		border-radius: 20px;
	}
	.item_box:hover{
		background-color: #e0e4ed;
	}
	.chat_list_btn{
		height: 100%;
		float: right;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	p{
		margin: 0px;
		padding: 0px;
		overflow: hidden;
		text-overflow:ellipsis;
		white-space: nowrap;
	}
	input{
		padding: 0;
		margin: 0;
		outline: none;
		border: none;
		background: none;
	}
	.chatNameInput{
		width: 110px;
		height: 100%;
		display: inline-block;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.chatNameInput *{
		height: 100%; 
		font-size: 18px;
	}
	::v-deep .el-card__body{
		overflow: auto;
	}
</style>