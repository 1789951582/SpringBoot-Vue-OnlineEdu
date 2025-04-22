<template>
	<!-- <div ref="myDiv"> -->
	<div class="editor-container">
		<mavon-editor class="markdown-editor" v-model="localValue" />
		<!-- <el-button class="mdBtn" type="success" size="small" round :style="{left: btnLeft+'px'}"
			@click="handleSubmit">提交</el-button> -->
		<el-button class="mdBtn" type="success" size="small" round @click="handleSubmit">提交</el-button>
	</div>
</template>

<script>
	export default {
		name: 'MarkdownEdit',
		props: {
			value: {
				type: String, // 根据实际需要调整类型
				default: ''
			}
		},
		data() {
			return {
				localValue: this.value,
				btnLeft: 0
			}
		},
		// beforeMount() {
		// 	window.addEventListener('resize', this.onWindowResize);
		// },
		// mounted() {
		// 	this.onWindowResize()
		// },
		// beforeDestroy() {
		// 	window.removeEventListener('resize', this.onWindowResize);
		// },
		methods: {
			// onWindowResize() {
			// 	if (this.$refs.myDiv) {
			// 		const parentWidth = this.$refs.myDiv.offsetWidth
			// 		console.log(parentWidth)
			// 		this.btnLeft = parentWidth / 2 - 90
			// 	}
			// },
			handleSubmit() {
				this.$emit('submit');
			}
		},
		watch: {
			value(newVal) {
				this.localValue = newVal; // 同步外部变化
			},
			localValue(newVal) {
				this.$emit('input', newVal);
			}
		}
	}
</script>

<style scoped>
	.editor-container {
		position: relative;
		height: 100%;
	}

	.markdown-editor {
		height: 100%;
		z-index: 0;
	}

	.mdBtn {
		position: absolute;
		float: left;
		/* position: relative; */
		/* top: -46px; */
		left: 50%;
		bottom: -10px;
		transform: translateX(-130%);
		z-index: 1;
	}
</style>