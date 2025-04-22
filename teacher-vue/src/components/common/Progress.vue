<template>
	<div class="progressBox">
		<el-progress :percentage="percentage" style="width: 100%;"></el-progress>
	</div>
</template>

<script>
	export default{
		props:{
			percentage:{
				type: Number,
				required:true
			}
		},
		data() {
			return{
				
			}
		}
	}
</script>

<style>
	.progressBox{
		display: flex;
		width: 100%;
	}
</style>