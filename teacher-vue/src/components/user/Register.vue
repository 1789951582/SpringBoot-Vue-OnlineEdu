<template>
	<div class="container">
		<p class="title">账号注册</p>
		<el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
			<el-form-item label="用户名">
				<el-input v-model="formLabelAlign.nickname" placeholder="请输入用户名"></el-input>
			</el-form-item>
			<el-form-item label="密码">
				<el-input v-model="formLabelAlign.pwd" placeholder="请输入密码" type='password'></el-input>
			</el-form-item>
			<el-form-item label="确认密码">
				<el-input v-model="okpwd" placeholder="请输入密码" type='password'></el-input>
			</el-form-item>
			
			<div class="submit">
				<el-link class="toLogin" href="/#/user/login">已有账号？去登录</el-link>
				<!-- <span class="toLogin">已有账号？去登录</span> -->
				<el-button type="primary" class="row-login" @click="register">注册</el-button>
			</div>
		</el-form>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name: "Register",
		data() {
			const validateAcc=(rule,value,callback)=>{
					this.checkNickname(callback)
			};
			const validatePwd=(rule,value,callback)=>{
				if (this.$refs.registerForm) {
					this.$refs.registerForm.validateField('okpwd'); 
				}
				callback();
			};
			const validateCheckPwd=(rule,value,callback)=>{
				if(this.formLabelAlign.pwd!=value){
					callback(new Error('两次输入密码不一致!'));
					return
				}
				callback();
			};
			return {
				labelPosition: 'left',
				formLabelAlign: {
					nickname: undefined,
					pwd: undefined
				},
				okpwd:undefined,
				rules:{
					nickname:[
						{required: true, message:'请输入用户名',trigger:'blur'},
						{max:20,message:"用户名长度应小于20!",trigger:'blur'},
						{validator: validateAcc,trigger:'blur'},
					],
					pwd:[
						{required: true, message:'请输入密码',trigger:'blur'},
						{min:6, max:20,message:"密码长度应该为6~20位!",trigger:'blur'},
						{ validator: validatePwd, trigger: 'blur'}
					],
					okpwd:[
						{required: true, message:'请再次输入密码',trigger:'blur'},
						{ validator: validateCheckPwd, trigger: 'blur'}
					]
				}
			}
		},
		methods:{
			checkNickname(){
				// 验证用户名
			},
			register(){
				axios.post("/teacher/user/register",this.formLabelAlign).then(res=>{
					if(res.data.status==200){
						this.$message.success(res.data.msg)
						window.localStorage.setItem("uid",res.data.data.uid)
						window.localStorage.setItem("token",res.data.data.token)
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

<style>
	.container {
		width: 100%;
		padding: 0 14%;
		margin-bottom: 32px;
	}

	.container .el-radio-group {
		margin: 30px 0px;
	}

	.container .title {
		margin: 30px 0px;
	}

	#login .title {
		text-align: center;
		font-size: 30px;
	}

	.submit .row-login {
		width: 80%;
		background-color: #04468b;
		border-color: #04468b;
		margin: 20px 0px 10px 0px;
		padding: 15px 20px;
	}

	.bottom .submit {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	.toLogin{
		/* width: 100%; */
		text-align: right;
		align-self: flex-end;
	}

</style>