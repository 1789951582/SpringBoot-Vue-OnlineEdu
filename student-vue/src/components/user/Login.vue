<template>
	<div class="main">
		<h2 class="title">登录</h2>
		<el-form :model="form" :rules="rules" ref="loginForm" class="from">
			<el-form-item label="账号" prop="account">
				<el-input placeholder="用户名/邮箱" v-model="form.account"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="pwd">
				<el-input type="password" placeholder="请输入密码" v-model="form.pwd"></el-input>
			</el-form-item>
			<el-form-item style="margin-top: 30px;">
				<el-button class="btn" type="info" round @click="loginBtn">登录</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
import axios from 'axios'
	export default{
		name:"Login",
		data() {
			const validateAcc=(rule,value,callback)=>{
				const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
				if (regex.test(value)) {
					callback();
				}else if(value.length>20){
					return callback(new Error('用户名长度应小于20!'));
				}else{
					callback()
				}
			};
			return{
				form:{
					account:"",
					pwd:""
				},
				rules:{
					account:[
						{required: true, message:'请输入用户名或邮箱',trigger:'blur'},
						{validator: validateAcc,trigger:'blur'}
					],
					pwd: [
						{required: true, message:'请输入密码',trigger:'blur'},
						{min:6, max:20,message:"密码长度应该为6~20位!",trigger:'blur'},
					]
				}
			}
		},
		methods:{
			loginBtn(){
				console.log(111)
				this.$refs.loginForm.validate((isErr,ob)=>{
					console.log(isErr)
					if(isErr){
						console.log(111)
						axios.post('/api/passport/login',this.form,{
							'Content-Type': 'application/json;charset=UTF-8'
						}).then(res=>{
							if(res.data.status==200){
								window.localStorage.setItem("uid",res.data.data.uid)
								window.localStorage.setItem("token",res.data.data.token)
								this.$message.success(res.data.msg)
								this.$router.push(`/home`)
							}else{
								this.$message.error(res.data.msg)
							}
						},
						err=>{
							this.$message.error("未知错误,请检查网络或通知系统管理员")
						})
					}
				})
				
			}
		}
	}
</script>

<style scoped>
	.main{
		float: left;
		padding: 70px;
	}
	.title{
		margin-bottom: 50px;
	}
</style>