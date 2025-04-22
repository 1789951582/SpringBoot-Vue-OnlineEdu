<template>
	<markdown-it-vue :content="content"></markdown-it-vue>
</template>

<script>
	import axios from 'axios';
	import MarkdownItVue from 'markdown-it-vue';
	import 'markdown-it-vue/dist/markdown-it-vue.css'
	export default {
		name: 'Markdown',
		components: {
			MarkdownItVue
		},
		props: {
			id:{
				default:true,
			}
		},
		data(){
			return{
				content:""
			}
		},
		methods:{
			getContent(markdownId) {
				axios.post("/api/course/markdown/" + markdownId, {
						uid: this.$store.state.uid,
						token: this.$store.state.token
					}, {
						headers: {
							'Content-Type': 'application/json;charset=UTF-8'
						}
					}).then(res=>{
						if (res.data.status === 200) {
							this.content=res.data.data.markdownContent
						}else {
							this.$message.error(res.data.msg);
						}
					},err=>{
						this.$message.error("未知错误,请检查网络或通知系统管理员");
				})
				// axios.post("/api/course/markdown/"+markdownId, {
				// 	uid: this.uid,
				// 	token: this.token
				// }, {
				// 	'Content-Type': 'application/json;charset=UTF-8'
				// }).then(res => {
				// 	if (res.data.status == 200) {
				// 		this.content = res.data.data.markdownContent
				// 	} else {
				// 		this.$message.error(res.data.msg)
				// 	}
				// }, err => {
				// 	this.$message.error("未知错误,请检查网络或通知系统管理员")
				// })
			}
		},
		watch:{
			id: {
				immediate: true,
				handler(newId) {
					console.log(newId)
					this.getContent(newId)
				}
			}
		}
	}
</script>