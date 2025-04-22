<template>
	<div id="app">
		<router-view></router-view>
		<!-- <AnswerVue></AnswerVue> -->
	</div>
</template>

<script>
	// import AnswerVue from './view/Answer.vue'
	export default {
		name: 'app',
		components: {
			// AnswerVue
		}
	}
</script>

<style>
	* {
		margin: 0px;
		padding: 0px;
		box-sizing: border-box;
	}

	#app {
		height: 100vh;
	}
	.el-header{
		background-color: #b3c0d1;
	}
	ul{
		margin: 0;
		padding: 0;
		list-style-type: none;
	}
	a {
		text-decoration: none;
	}
	.el-card{
		border-radius: 20px !important;
	}
	.el-tree-node__label {
		font-size: 16px !important;
		font-weight: 500 !important;
		/* margin-bottom: 10px; */
	}
	
	.el-tree {
		background: none !important;
	}
	
	.el-tree-node {
		margin-bottom: 10px;
	}
	.el-dialog{
		border-radius: 20px !important;
	}
	.el-tag{
		border-radius: 20px !important;
	}
	.el-main {
		padding: 10px !important;
	}
	.el-upload,.el-upload-dragger{
		width: 100% !important;
		height: 100% !important;
	}
	.el-upload-dragger{
		display: flex !important;
		flex-direction: column !important;
		align-items: center !important;
		justify-content: center !important;
	} 
	.el-table{
		font-size: 16px !important;
	}
</style>