<template>
	<div class="container">
		<p class="title">账号登录</p>
		<el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
			<el-form-item label="用户名">
				<el-input v-model="formLabelAlign.account" placeholder="请输入用户名或uid"></el-input>
			</el-form-item>
			<el-form-item label="密码">
				<el-input v-model="formLabelAlign.pwd" placeholder="请输入密码"
					type='password'></el-input>
			</el-form-item>
			<div class="submit">
				<el-link href="/#/user/register" class="toLogin">没有账号？去注册</el-link>
				<el-button type="primary" class="row-login" @click="login">登录</el-button>
			</div>
		</el-form>
	</div>
</template>

<script>
	import axios from 'axios'
	export default{
		name:"Login",
		data() {
			return{
				labelPosition: 'left',
				formLabelAlign: {
				  account: undefined,
				  pwd: undefined
				}
			}
		},
		methods:{
			login(){
				axios.post("/teacher/user/login",this.formLabelAlign).then(res=>{
					if(res.data.status==200){
						this.$message.success(res.data.msg)
						window.localStorage.setItem("uid",res.data.data.uid)
						window.localStorage.setItem("token",res.data.data.token)
						this.$router.push("/home")
					}else{
						this.$message.error(res.data.msg)
					}
				},err=>{
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			}
		}
	}
</script>

<style scoped>
	/* .container {
		margin-bottom: 32px;
	}
	.container .el-radio-group {
		margin: 30px 0px;
	}
	.bottom .container .title {
		margin: 30px 0px;
	}
	.bottom{
		width: 100% !important;
	}
	#login .bottom .title {
		text-align: center;
		font-size: 30px;
	}
	
	.bottom .submit .row-login {
		width: 100%;
		background-color: #04468b;
		border-color: #04468b;
		margin: 20px 0px 10px 0px;
		padding: 15px 20px;
	}
	
	.bottom .submit {
		display: flex;
		justify-content: center;
	} */
</style>