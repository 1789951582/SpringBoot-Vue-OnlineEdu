<template>
	<el-container style="height: 100%; border: 1px solid #eee">
		<el-aside width="200px" style="height: 100%; padding: 10px; object-fit: cover; background-color: rgb(238, 241, 246)">
			<div class="nav">
				<div class="user-block" v-if="userInfo.tuid!=undefined">
					<el-avatar class="avatar_img" :size="60" :src="avatar"></el-avatar>
					<p>{{userInfo.tnickname}}</p>
					<p class="mini_text">uid:{{userInfo.tuid}}</p>
				</div>
				<el-menu :default-active="$route.path" background-color="#b3c0d1" text-color="#444444" active-text-color="#909399" :router="true">
					<el-menu-item index="/home/subjectList">
						<i class="el-icon-menu"></i>
						<span slot="title">科目</span>
					</el-menu-item>
					<el-menu-item index="/home/aiChat">
						<i class="el-icon-menu"></i>
						<span slot="title">AIChat</span>
					</el-menu-item>
					<el-menu-item index="/home/subjectList">
						<i class="el-icon-menu"></i>
						<span slot="title">便签</span>
					</el-menu-item>
					<el-menu-item index="/home/subjectList">
						<i class="el-icon-menu"></i>
						<span slot="title">设置</span>
					</el-menu-item>
				</el-menu>
			</div>
		</el-aside>
		<!-- <AichatVue></AichatVue> -->
		<router-view></router-view>
	</el-container>
</template>

<script>
	import axios from "axios"
	// import AichatVue from "../components/home/Aichat.vue"
	export default{
		data() {
			return {
				userInfo:{
					tuid:undefined,
				}
			}
		},
		// components:{
		// 	AichatVue
		// },
		beforeMount() {
			this.$store.commit("init")
			this.getUserInfo()
		},
		methods:{
			getUserInfo(){
				axios.post("/teacher/user/get_user_info",{
					uid: this.$store.state.uid,
					token: this.$store.state.token,
				}).then(res=>{
					if(res.data.status==200){
						this.userInfo=res.data.data
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
		},
		computed:{
			avatar() {
				return this.userInfo?.tavatar || require('@/assets/defAvatar.png');
			}
		}
	}
</script>

<style>
	.el-header {
		/* background-color: #B3C0D1; */
		color: #333;
		line-height: 60px;
	}
	
	.el-aside {
		color: #333;
	}
	
	.el-menu {
		border: none !important;
	}
	
	.user-block {
		width: 100%;
		height: 208px;
		/* background-color: #333; */
		/* display: flex; */
		/* align-items: center; */
		/* justify-content: center; */
		text-align: center;
		color: #444444;
		/* margin: 40px auto 20px; */
	}
	
	.user-block p {
		margin-bottom: 4px;
	}
	
	.avatar_img {
		margin-top: 40px;
		margin-bottom: 20px;
	}
	
	.mini_text {
		font-size: 12px;
	}
	
/* 	.container {
		display: flex;
	} */
	
	.nav {
		height: 100%;
		background-color: #b3c0d1;
		border-radius: 10px;
		box-shadow: 0 0 20px 0 rgba(255, 255, 255, 0.15);
	}
	
	.content {
	
		background-color: aquamarine;
	}
</style>