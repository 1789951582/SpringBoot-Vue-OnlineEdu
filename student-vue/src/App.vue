<template>
	<div id="app">
		<router-view></router-view>
		<!-- <AnswerInfo></AnswerInfo> -->
	</div>
</template>

<script>
	import AnswerInfo from './views/AnswerInfoView.vue'
	export default {
		name: 'app',
		components:{
			AnswerInfo
		}
	}
</script>

<style>
	* {
		box-sizing: border-box;
		margin: 0;
	}

	#app {
		height: 100vh;
	}

	ul {
		list-style: none;
		padding: 0px;
	}

	a {
		text-decoration: none;
	}

	.el-header {
		background-color: #B3C0D1;
		color: #333;
		line-height: 60px;
	}

	.el-aside {
		color: #333;
	}

	.el-menu-item {
		font-size: 16px;
	}

	.el-tree-node__label {
		font-size: 16px;
		font-weight: 500;
		/* margin-bottom: 10px; */
	}

	.el-tree {
		background: none;
	}

	.el-tree-node {
		margin-bottom: 10px;
	}

	.el-main {
		padding: 10px;
	}
	.el-table{
		font-size: 16px !important;
	}
</style>