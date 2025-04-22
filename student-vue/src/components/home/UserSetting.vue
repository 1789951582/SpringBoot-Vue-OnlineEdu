<template>
	<el-container v-loading="loading">
		<el-main>
			<el-card>
				<div slot="header" class="clearfix">
					<span>个人信息修改</span>
				</div>
				<div style="display: flex;">
					<div class="avatarDiv">
						<el-avatar class="avatar" :size="190" :src="savatar"></el-avatar>
						<!-- <el-input value="小皓"></el-input> -->
					</div>
					<div style="flex: 3;">
						<el-descriptions title="用户信息" :column="2" class="user-info">
							<template slot="extra">
								<el-button v-if="!isEditing" type="primary" size="small" @click="startEdit">
									修改
								</el-button>
								<el-button v-else type="success" size="medium" @click="modifyInformation">
									完成
								</el-button>
							</template>
							<el-descriptions-item v-for="item in fields" :key="item.prop" :label="item.label">
								<template v-if="!isEditing">
									<span v-if="item.formatter">{{ item.formatter(stuinfo?.[item.prop]) }}</span>
									<span v-else>{{ stuinfo?.[item.prop] }}</span>
								</template>
								<component v-else :is="item.component || 'el-input'" v-model="formData[item.prop]"
									v-bind="item.componentAttrs || {}" v-on="item.events || {}">
									<template v-if="item.options" style="margin: 0,20px;">
										<el-option v-for="opt in item.options" :key="opt.value" :label="opt.label"
											:value="opt.value" />
									</template>
								</component>
							</el-descriptions-item>
						</el-descriptions>
					</div>
				</div>
			</el-card>
			<div style="display: flex;">
				<el-card style="flex: 1;margin:20px 20px 0 0;">
					<div slot="header">
						<span>邮箱修改</span>
					</div>
					<el-form ref="emailSetting" :model="emailForm">
						<span>旧邮箱:{{stuinfo.semail}}</span>
						<el-form-item label="旧邮箱" :rules="coreFormRules.oEmailAddr" prop="oldEmail">
							<el-input type="email" placeholder="请输入旧邮箱" v-model="emailForm.oldEmail">
								<el-button type="info" slot="append" :disabled="countdown"
									@click="getCode(emailForm.oldEmail,'emailSetting')"
									style="width: 100px;">{{countdown ? `${countdown}秒后重发`:"发送验证码"}}</el-button>
							</el-input>
						</el-form-item>
						<el-form-item label="新邮箱" :rules="coreFormRules.emailAddr" prop="email">
							<el-input type="email" placeholder="请输入新邮箱" v-model="emailForm.email"></el-input>
						</el-form-item>
						<el-form-item label="验证码" required prop="code">
							<el-input v-model="emailForm.code" placeholder="请输入邮箱验证码"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="changeEmail">提交</el-button>
						</el-form-item>
					</el-form>
				</el-card>
				<el-card style="flex: 1;margin:20px 0 0 20px;">
					<div slot="header">
						<span>密码修改</span>
					</div>
					<el-form ref="pwdSetting" :model="pwdForm">
						<!-- <span>旧邮箱:{{stuinfo.semail}}</span> -->
						<el-form-item label="邮箱" :rules="stuinfo.oEmailAddr" prop="oldEmail">
							<el-input type="email" placeholder="请输入旧邮箱" v-model="pwdForm.oldEmail">
								<el-button type="info" slot="append" :disabled="countdown"
									@click="getCode(pwdForm.oldEmail,'pwdSetting')"
									style="width: 100px;">{{countdown ? `${countdown}秒后重发`:"发送验证码"}}</el-button>
							</el-input>
						</el-form-item>
						<el-form-item label="验证码" required prop="code">
							<el-input v-model="pwdForm.code" placeholder="请输入邮箱验证码"></el-input>
						</el-form-item>
						<el-form-item label="新密码" :rules="coreFormRules.pwd" prop="pwd">
							<el-input type="password" v-model="pwdForm.pwd" placeholder="请输入新密码"></el-input>
						</el-form-item>
						<el-form-item label="确认密码" :rules="coreFormRules.checkPwd" prop="checkPwd">
							<el-input type="password" v-model="pwdForm.checkPwd" placeholder="请输入确认密码"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="changePwd">提交</el-button>
						</el-form-item>
					</el-form>
				</el-card>
			</div>
			<!-- 	<div>
						
					</div> -->
			<!-- 		</div>
			</div> -->
		</el-main>
		<template v-if="!loading">
			...
		</template>
	</el-container>

</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			const validatePwd = (rule, value, callback) => {
				if (this.$refs.registerForm) {
					this.$refs.registerForm.validateField('checkPwd');
				}
				callback();
			};
			const validateCheckPwd = (rule, value, callback) => {
				if (this.pwdForm.pwd != value) {
					callback(new Error('两次输入密码不一致!'));
					return
				}
				callback();
			};
			return {
				// userinfoIsSetting: false,
				loading: true,
				isEditing: false,
				formData: undefined,
				fields: [{
						prop: 'srealname',
						label: '真实姓名',
						rules: {
							required: true
						}
					},
					{
						prop: 'sphone',
						label: '手机号',
						rules: [{
								required: true
							},
							{
								pattern: /^1[3-9]\d{9}$/,
								message: '手机号格式不正确'
							}
						]
					},
					{
						prop: 'sschool',
						label: '学校'
					},
					{
						prop: 'scourse',
						label: '班级'
					},
					{
						prop: 'snum',
						label: '学号'
					}, // 修复原代码中重复使用 sschool 的问题
					{
						prop: 'ssex',
						label: '性别',
						component: 'el-select',
						options: [{
								label: '男',
								value: 0
							},
							{
								label: '女',
								value: 1
							}
						],
						formatter: v => v === 0 ? '男' : '女'
					},
					{
						prop: 'sage',
						label: '年龄',
						component: 'el-input-number',
						componentAttrs: {
							min: 1,
							max: 100
						},
						rules: {
							type: 'number',
							min: 1,
							max: 100
						}
					}
				],
				emailForm: {
					oldEmail: '',
					email: '',
					code: ''
				},
				countdown: undefined,
				seadCodeBtnStatus: false,
				coreFormRules: {
					emailAddr: [{
							required: true,
							message: '请输入邮箱地址',
							trigger: 'blur'
						},
						{
							type: 'email',
							message: '请输入正确的邮箱地址',
							trigger: ['blur', 'change']
						},
					],
					oEmailAddr: [{
							required: true,
							message: '请输入邮箱地址',
							trigger: 'blur'
						},
						{
							type: 'email',
							message: '请输入正确的邮箱地址',
							trigger: ['blur', 'change']
						},
					],
					pwd: [{
							required: true,
							message: '请输入密码',
							trigger: 'blur'
						},
						{
							min: 6,
							max: 20,
							message: "密码长度应该为6~20位!",
							trigger: 'blur'
						},
						{
							validator: validatePwd,
							trigger: 'blur'
						}
					],
					checkPwd: [{
							required: true,
							message: '请再次输入密码',
							trigger: 'blur'
						},
						{
							validator: validateCheckPwd,
							trigger: 'blur'
						}
					],
				},
				pwdForm: {
					oldEmail: '',
					pwd: '',
					checkPwd: '',
					code: ''
				},
				timer: undefined,
				isReset:false
			}
		},
		methods:{
			startEdit() {
				this.formData = {
					srealname:this.stuinfo.srealname,
					sphone:this.stuinfo.sphone,
					sschool:this.stuinfo.sschool,
					scourse:this.stuinfo.scourse,
					snum:this.stuinfo.snum,
					ssex:this.stuinfo.ssex,
					sage:this.stuinfo.sage
				}
				this.isEditing = true
			},
			modifyInformation() {
				// const isDataChanged = JSON.stringify(this.formData) !== JSON.stringify(this.stuinfo);
				if (!this.isReset) {
					this.$message.warning("未修改任何信息");
					this.isEditing = false; // 可选：关闭编辑状态
					return; // 直接返回，不提交
				}
				axios.post("/api/user/modify_information", {
					uid: this.$store.state.uid,
					token: this.$store.state.token,
					...this.formData
				}).then(res => {
					if (res.data.status == 200) {
						this.$store.commit("setStuInfo", this.formData)
						this.isEditing = false
						this.$message.success("修改成功")
					} else {
						this.$message.error(res.data.msg)
					}
				}, err => {
					this.$message.error("未知错误,请检查网络或通知系统管理员")
				})
			},
			getCode(oldEmail, formRefName) {
				const formComponent = this.$refs[formRefName];
				if (!formComponent) {
					console.error(`无法找到表单引用: ${formRefName}`);
					return;
				}
				formComponent.validateField(['oldEmail'], (err) => {
					if (!err) {
						axios.post("/api/user/get_code", {
							uid: this.$store.state.uid,
							token: this.$store.state.token,
							oldEmail
						}).then(res => {
							if (res.data.status == 200) {
								this.$message.success("发送成功")
								this.countdown = 60
								this.timer = setInterval(() => {
									if (this.countdown > 0) {
										this.countdown--; // 每次减少1秒
									} else {
										clearInterval(this.timer); // 清除定时器
										this.timer = undefined; // 重置定时器ID
										this.countdown = undefined
										this.seadCodeBtnStatus = false
									}
								}, 1000)
							} else {
								this.$message.error(res.data.msg)
							}
						}, err => {
							this.$message.error("未知错误,请检查网络或通知系统管理员")
						})
					} else {
						this.$message.error("请输入正确的邮箱地址")
					}
				});
			},
			changeEmail(formName) {
				this.$refs.emailSetting.validate((valid) => {
					if (valid) {
						axios.post("/api/user/change_email", {
							uid: this.$store.state.uid,
							token: this.$store.state.token,
							...this.emailForm
						}).then(res => {
							if (res.data.status == 200) {
								this.$message.success("修改成功")
							} else {
								this.$message.error(res.data.msg)
							}
						}, err => {
							this.$message.error("未知错误,请检查网络或通知系统管理员")
						})
					} else {
						this.$message.error('请检查输入的数据');
						return false;
					}
				});
			},
			changePwd(formName) {
				this.$refs.pwdSetting.validate((valid) => {
					if (valid) {
						axios.post("/api/user/change_password", {
							uid: this.$store.state.uid,
							token: this.$store.state.token,
							...this.pwdForm
						}).then(res => {
							if (res.data.status == 200) {
								this.$message.success("修改成功")
							} else {
								this.$message.error(res.data.msg)
							}
						}, err => {
							this.$message.error("未知错误,请检查网络或通知系统管理员")
						})
					} else {
						this.$message.error('请检查输入的数据');
						return false;
					}
				});
			}
		},
		computed: {
			savatar() {
				return this.stuinfo?.savatar || require('@/assets/defAvatar.png');
			},
			stuinfo() {
				return this.$store.state.StuInfo || {}
			},
		},
		watch: {
			stuinfo: {
				handler(val) {
					if (val && Object.keys(val).length) {
						this.loading = false;
					}
				},
				immediate: true
			},
			formData:{
				handler(newVal,oldVal) {
					if(oldVal!=undefined){
						this.isReset=true
					}
				},
				deep: true
			}
		},
	}
</script>

<style>
</style>
<style scoped>
	.avatarDiv {
		/* flex: 1; */
		display: flex;
		/* flex-direction: column; */
		justify-content: center;
		/* align-items: ; */
	}

	.avatar {
		margin: 30px;
	}

	.user-info {
		margin-top: 20px;
		font-size: 16px;

		::v-deep .el-input {
			width: 80% !important;
		}
	}
</style>