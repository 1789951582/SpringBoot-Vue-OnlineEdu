<!-- <template>
	<div v-for="resource in resourceList" :key="resource.resource_id" class="resource">
		<iframe v-if="resource.typeId === 2" class="resource" style="height: 750px;"
			:src="resource.resourceUrl"></iframe>
		<vue-core-video-player v-else-if="resource.typeId === 1"
			:src="resource.resourceUrl"></vue-core-video-player>
		<img v-else-if="resource.typeId === 4" class="resource" style="height: auto;"
			:src="resource.resourceUrl" />
		<Markdown v-else-if="resource.typeId === 3" :id="resource.resourceUrl"></Markdown>
	</div>
</template>

<script>
	export default{
		data() {
			return{
				
			}
		}
	}
</script>

<style>
</style> -->