<template>
	<div class="main" >
		<h2 class="title">注册</h2>
		<el-form :model="form" :rules="rules" ref="registerForm">
			<el-form-item label="用户名" prop="nickname" class="form-item">
				<el-input placeholder="请输入用户名" v-model="form.nickname" ></el-input>
			</el-form-item>
			<el-form-item label="密码" class="form-item" prop="pwd">
				<el-input type="password" placeholder="请输入密码" v-model="form.pwd"></el-input>
			</el-form-item>
			<el-form-item label="确认密码" class="form-item" prop="checkPwd">
				<el-input type="password" placeholder="请再次输入密码" v-model="form.checkPwd"></el-input>
			</el-form-item>
			<el-form-item label="邮箱" class="form-item" prop="emailAddr">
				<el-input  type="email" placeholder="请输入邮箱" v-model="form.emailAddr">
					<el-button
						type="info"
						slot="append" 
						@click="sendCode" 
						:disabled="seadCodeBtnStatus"
						style="width: 100px;"
					>{{countdown ? `${countdown}秒后重发` : "发送验证码"}}</el-button>
				</el-input>
				<div ref="captchaBox" v-show="showCaptchaMask" style="position: absolute;top: -100%;right: -100%;"></div>
			</el-form-item>
			<el-form-item label="验证码" class="form-item" prop="emailCode">
				<el-input style="width: 350px;" 
					placeholder="请输入验证码" 
					v-model="form.emailCode">
				</el-input>
			</el-form-item>
			<el-form-item>
				<el-button class="btn" type="info" round @click="registerBtn">注册</el-button>
			</el-form-item>
		</el-form>
		
	</div>
</template>

<script>
	import {TianAiCaptcha} from "../../../public/tac/js/tac.min.js"
	import "../../../public/tac/css/tac.css"
	import axios from 'axios';
	import qs from 'qs';
	export default {
		name:'Register',
		data() {
			const validateAcc=(rule,value,callback)=>{
					this.checkNickname(callback)
			};
			const validatePwd=(rule,value,callback)=>{
				if (this.$refs.registerForm) {
					this.$refs.registerForm.validateField('checkPwd'); 
				}
				callback();
			};
			const validateCheckPwd=(rule,value,callback)=>{
				if(this.form.pwd!=value){
					callback(new Error('两次输入密码不一致!'));
					return
				}
				callback();
			};
			return {
				showCaptchaMask: false,
				captchaConfig:{
					// 绑定的div
					bindEl: undefined,
				},
				captcha:undefined,
				seadCodeBtnStatus:false,
				timer:undefined,
				form:{
					nickname:'',
					pwd:'',
					checkPwd:'',
					emailAddr:'',
					emailCode:'',
				},
				countdown:undefined,
				rules:{
					nickname:[
						{required: true, message:'请输入账户',trigger:'blur'},
						{max:20,message:"用户名长度应小于20!",trigger:'blur'},
						{validator: validateAcc,trigger:'blur'},
					],
					pwd:[
						{required: true, message:'请输入密码',trigger:'blur'},
						{min:6, max:20,message:"密码长度应该为6~20位!",trigger:'blur'},
						{ validator: validatePwd, trigger: 'blur'}
					],
					checkPwd:[
						{required: true, message:'请再次输入密码',trigger:'blur'},
						{ validator: validateCheckPwd, trigger: 'blur'}
					],
					emailAddr:[
						{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
						{ type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },
					],
					emailCode:[
						{required: true, message:'请输入验证码',trigger:'blur'}
					]
				}
			}
		},
		mounted() {
			this.captchaConfig.bindEl=this.$refs.captchaBox
			this.captcha=new TAC(this.captchaConfig,null)
			this.captcha.validCaptcha=this.sendAnswer
			this.captcha.reload=this.reload
			this.captcha.btnCloseFun=(el,tac)=>{
				this.seadCodeBtnStatus=false
				tac.destroyWindow()
			}
		},
		methods:{
			getCaptcha(){
				return axios.post('/api/passport/get_register_code')
			},
			//检验用户名是否被占用
			checkNickname(callback){
				axios.get("/api/passport/check_nickname",{
					params:{
						nickname: this.form.nickname
					}
				}).then(
					res=>{
						if(res.data.data){
							this.$message.success("恭喜,用户名没有被占用")
							callback()
						}else if(res.data.status==500){
							callback(new Error(res.data.msg))
						}else{
							callback("未知错误,请联系系统管理员")
						}
					},
					err=>{
						callback("未知错误,请联系系统管理员")
					}
				)
			},
			
			sendCode(){
				this.$refs.registerForm.validateField(['emailAddr'],(err)=>{
					if(!err){
						this.getCaptcha().then(res=>{
							if(res.data.status==200){
								this.seadCodeBtnStatus=true
								this.captcha.init();
								this.showCaptchaMask=true
								this.captcha.pushData(res.data.data)
							}else{
								this.$message.error(res.data.msg)
							}
						})
					}else{
						this.$message.error("请输入正确的邮箱地址")
					}
				});
			},
			
			sendAnswer(id,data,c,tac){
				axios.post("/api/passport/getEmailCode",{
					emailAddr:this.form.emailAddr,
					codeId: id,
					datas:data
				},{
					'Content-Type': 'application/json;charset=UTF-8'
				})
				.then(
					res=>{
						if(res.data.status==200){
							if(res.data.data){
								const useTimes = (data.stopTime - data.startTime) / 1000;
								c.showTips(`验证成功,耗时${useTimes}秒`,1)
								this.countdown=60
								this.timer=setInterval(()=>{
									if (this.countdown > 0) {
										this.countdown--; // 每次减少1秒
									}else{
										clearInterval(this.timer); // 清除定时器
										this.timer = undefined; // 重置定时器ID
										this.countdown=undefined
										this.seadCodeBtnStatus=false
									}
								},1000)
								setTimeout(()=>{
									tac.destroyWindow()
								},2000)
							}else{
								c.showTips("验证失败，请重新尝试!", 0);
								setTimeout(()=>{
									tac.reload(tac)
								},2000)
							}
						}else {
							c.showTips(res.data.msg, 0);
							this.$message.error(res.data.msg)
							setTimeout(()=>{
								tac.destroyWindow()
								this.seadCodeBtnStatus=false
							},3000)
						}
					},
					err=>{
						c.showTips("验证码被黑洞吸走了！", 0);
						this.$message.error("未知错误,请检查网络或通知系统管理员")
					})
			},
			reload(tac){
				tac.showLoading()
				this.getCaptcha().then(res=>{
					if(res.data.status==200){
						tac.pushData(res.data.data)
					}else{
						tac.destroyWindow()
						this.seadCodeBtnStatus=false
					}
				})
			},
			registerBtn(){
				this.$refs.registerForm.validate((isErr,ob)=>{
					console.log(isErr)
					if(isErr){
						axios.post('/api/passport/register',this.form,{
							'Content-Type': 'application/json;charset=UTF-8'
						}).then(res=>{
							if(res.data.status==200){
								this.$message.success(res.data.msg)
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

<style>
	.form-item > .el-form-item__label{
		line-height: 100% !important;
		margin-bottom: 8px;
	}
</style>
<style scoped>
	.main{
		float: right;
		
	}
	.title{
		margin-bottom: 20px ;
	}
	
</style>