<template>
	<div class="container" id="myDiv">
		<vue-canvas-nest :config="config" :el="'#myDiv'"></vue-canvas-nest>
		<div class="content">
			<Transition name="weather_transition" mode="out-in">
				<Login key="Login" v-if="isLogin"></Login>
				<Register key="Register" v-else></Register>
			</Transition>
			<div class="switch" :style="isLogin ? 'transform: translateX(500px);' : ''">
				<Transition name="weather_transition" mode="out-in">
					<div class="to_signup" v-if="isLogin" key="to_signup">
						<h2 class="title">Hello Friend!</h2>
						<p>去注册一个账号，让我们踏入奇妙的旅途吧！！！</p>
						<button class="btn" @click="toggleSignIn">SIGN UP</button>
					</div>
					<div class="to_signin" v-else-if="!isLogin" key="to_signin">
						<h2 class="title">Welcome Back!</h2>
						<p>已经有账号了嘛，去登入账号来进入奇妙世界吧！！！</p>
						<button class="btn" @click="toggleSignIn">SIGN IN</button>
					</div>
				</Transition>
			</div>
		</div>
	</div>
</template>

<script>
	import vueCanvasNest from 'vue-canvas-nest';
	import Login from "../components/user/Login.vue"
	import Register from '../components/user/Register.vue';
	export default {
	    components: { 
			vueCanvasNest,
			Login,
			Register
		},
		data() {
			return{
				isLogin: true, //显示登录界面
				config: {
					color: '90,90,90',
					zIndex: 0,
					count: 100,
				}
			}
		},
		methods: {
			//切换登录注册
			toggleSignIn() {
				this.isLogin = !this.isLogin;
			},
		},
	}
</script>

<style>
	.container{
		background: #f0f2f5 url(../assets/background.ebcb9160.svg) no-repeat 50%;
		width: 100%;
		min-height: 100%;
		background-size: 100%;
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.el-input__inner:focus{
		border-color: #999 !important;
	}
	.main{
		width: 550px;
		height: 100%;
		text-align: center;
		
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}
	.title {
		font-size: 34px;
		font-weight: 700;
		color: #181818;
		letter-spacing: 10px;
	}
	.from{
		width: 100%;
	}
	.btn{
		width: 40%;
	}
	@keyframes shake {
	
		10%,
		90% {
			transform: translate3d(-1px, 0, 0);
		}
	
		20%,
		80% {
			transform: translate3d(2px, 0, 0);
		}
	
		30%,
		50%,
		70% {
			transform: translate3d(-4px, 0, 0);
		}
	
		40%,
		60% {
			transform: translate3d(4px, 0, 0);
		}
	}
	.el-form-item__label{
		font-size: 16px;
	}
</style>
<style scoped>
	/* Your CSS styles here */
	* {
		margin: 0;
		padding: 0;
		box-sizing: border-box
	}

	.content {
		width: 900px;
		height: 600px;
		border-radius: 12px;
		background: #fff;
		box-shadow: 0 0 20px 0 rgba(175, 187, 204, 0.15);
		z-index: 1;
	}


	.sign_in form,
	.sign_up form {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}



	input {
		width: 350px;
		height: 46px;
		margin: 18px 0;
		padding: 0 10px 0;
		border-radius: 10px;
		border: 1px solid #d4d6d9;
	}

	.btn {
		
		height: 40px;
		margin-top: 50px;
		border-radius: 25px;
		border: none;
	}

	.switch {
		width: 300px;
		height: 600px;
		position: absolute;
		background-color: rgb(102, 102, 102);
		margin: 0 50px;
		/* background: linear-gradient(to bottom,#cbebdb 0%,#3794c0 120%); */
		transition: 1s;
		/* ransform: translateX(500px); */
	}

	.to_signin,
	.to_signup {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		padding: 50px;
		/* display: none; */
	}

	.switch .title {
		color: #ecf0f3;
	}

	p {
		font-size: 14px;
		color: #a0a5a8;
	}

	.sw_toleft {
		transform: translateX(0);
	}

	.tip {
		padding: 1% 2%;
		background: white;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 10px;
		position: absolute;
		top: 20px;
	}

	.weather_transition-enter-active,
	.weather_transition-leave-active {
		transition: all 0.5s linear;
	}

	.weather_transition-leave-to,
	.weather_transition-enter {
		opacity: 0;
	}

	.weather_transition-enter-to,
	.weather_transition-leave-from {
		opacity: 1;
	}

	.switch-enter-active,
	.switch-leave-active {
		transition: all 1s linear;
	}

	/* 输入框震动 */
	.shake {
		animation: shake 0.82s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
		transform: translate3d(0, 0, 0);
	}

	
</style>