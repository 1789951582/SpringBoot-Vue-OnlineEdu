<template>
	<router-view></router-view>
</template>

<script>
	export default{
		props:{
			courseId:{
				default:true
			}
		},
		data() {
			return {
				
			}
		},
		beforeMount() {
			
		},
		
		methods: {
			
		}
	};
</script>

<style>
	.resource {
		width: 100%;

		margin-bottom: 20px;
	}
	.paper {
		h4 {
			cursor: pointer;
		}
	}

	.paper .item a {
		color: #000;
	}

	.item {
		display: flex;
		flex-direction: column;
	}

	.name {
		margin: 0;
	}

	.item .info i {
		margin-right: 5px;
		color: #0195ff;
	}

	.item .info span {
		margin-right: 14px;
	}

	.paper .item {
		width: 380px;
		height: 200px;
		border-radius: 10px;
		padding: 20px 30px;
		border: 1px solid #eee;
		box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
		transition: all 0.6s ease;
	}

	.item h3 {
		flex: 2;
		display: flex;
		align-items: center;
	}

	.item p {
		flex: 3;
	}

	.paper .item:hover {
		box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
		transform: scale(1.03);
	}

	.paper .item .info {
		font-size: 14px;
		color: #88949b;
	}

	.paper .item .name {
		font-size: 14px;
		color: #88949b;
	}

	.paper .item {
		margin: 20px 0;
	}

	.wrapper .paper {
		display: flex;
		justify-content: space-around;
		flex-wrap: wrap;
	}

	/* ..el-page-header */
</style>
<style scoped>
	.status{
		
	}
</style>