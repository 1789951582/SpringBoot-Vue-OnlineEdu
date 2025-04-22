<template>
	<div id="login">
		<!-- <div class="bg"></div> -->
		<el-row class="main-container">
			<el-col :lg="8" :xs="16" :md="10" :span="10">
				<div class="top">
					<i class="el-icon-data-line icon-kaoshi"></i><span class="title">教师端</span>
				</div>
				<div class="bottom">
					<!-- <LoginVue></LoginVue> -->
					<!-- <RegisterVue></RegisterVue> -->
					<router-view></router-view>
				</div>
			</el-col>
		</el-row>
		<!-- <el-row class="footer">
			<el-col>
				<p class="msg2">版权所有 ©2019 保留所有权利 <a href="http://beian.miit.gov.cn/"
						target="_blank">渝ICP备19001371号</a></p>
			</el-col>
		</el-row> -->
	</div>
</template>

<script>
	import axios from 'axios'
	// import LoginVue from '../components/Login.vue'
	// import RegisterVue from '../components/Register.vue'
	export default{
		name:"User",
		// components:{
		// 	LoginVue,
		// 	RegisterVue
		// }
	}
</script>

<style scoped>
	.remind {
		border-radius: 4px;
		padding: 10px 20px;
		display: flex;
		position: fixed;
		right: 20px;
		bottom: 50%;
		flex-direction: column;
		color: #606266;
		background-color: #fff;
		border-left: 4px solid #409eff;
		box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)
	}
	
	.title{
		line-height: 60px;
	}


	a:link {
		color: #ff962a;
		text-decoration: none;
	}

	#login {
		height: 100vh;
		font-size: 14px;
		color: #000;
		background: url('../assets/img/loginbg.png')center top / cover no-repeat;
		background-color: #b6bccdd1 !important;
	}

	/* #login .bg {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		overflow-y: auto;
		height: 100%;
		background: url('../assets/img/loginbg.png')center top / cover no-repeat;
		background-color: #b6bccdd1 !important;
	} */

	#login .main-container {
		display: flex;
		justify-content: center;
		align-items: center;
	}

	#login .main-container .top {
		margin-top: 100px;
		margin-bottom: 10px;
		font-size: 30px;
		color: #ff962a;
		display: flex;
		justify-content: center;
	}

	#login .top .icon-kaoshi {
		font-size: 60px;
	}

	#login .bottom {
		display: flex;
		justify-content: center;
		background-color: #fff;
		border-radius: 5px;
		box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
	}



	

	.footer {
		margin-top: 50px;
		text-align: center;
	}

	.footer .msg1 {
		font-size: 18px;
		color: #fff;
		margin-bottom: 15px;
	}

	.footer .msg2 {
		font-size: 14px;
		color: #e3e3e3;
		margin-top: 70px;
	}

	.bottom .options {
		margin-bottom: 40px;
		color: #ff962a;
		display: flex;
		justify-content: space-between;
	}

	.bottom .options>a {
		color: #ff962a;
	}

	.bottom .options .register span:nth-child(1) {
		color: #8C8C8C;
	}
</style>