<script setup lang="ts">
	import icons from "@/components/icons";
	import { useRouter } from "vue-router";
	const router = useRouter();
</script>
<template>
	<div class="parent-E14b-ehs1g">
		<div class="parent-4kLzWxnokg">
			<button type="button" class="parent-Nk17rx3sJe">
				<component
					:is="icons.add"
					class="icon big parent-NkmPfl2iyx white"
				/>
				<span class="parent-4JZdGlhj1x">New Article</span>
			</button>
		</div>
		<div class="parent-NyqMZg3jkl">
			<div class="child-E17jXe3okl" @click="router.push({name: 'ArticlesPanel'})">
				<component :is="icons.article" class="icon child-41G37ensyx" />
				<span class="child-VkvsQehjyg">Article</span>
			</div>
			<div class="child-E17jXe3okl">
				<component :is="icons.series" class="icon child-41G37ensyx" />
				<span class="child-VkvsQehjyg">Series</span>
			</div>
			<!-- <div class="child-E17jXe3okl">
				<component :is="" class="icon child-41G37ensyx" />
				<span class="child-VkvsQehjyg"></span>
			</div> -->
		</div>
	</div>
</template>
<style scoped>
	.parent-4kLzWxnokg {
		width: 100%;
		height: 100%;
		padding: 20px 0;
		border-bottom: #efecec solid 1px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.parent-Nk17rx3sJe {
		display: flex;
		justify-content: center;
		align-items: center;
		border: none;
		border-radius: 30px;
		height: 40px;
		width: 155px;
		background-color: var(--second-color);
		cursor: pointer;
		box-shadow: 0 1px 4px 0 rgba(60, 64, 67, 0.3);
	}
	.parent-4JZdGlhj1x {
		font-size: 15px;
		font-weight: bold;
		color: white;
	}
	.parent-NyqMZg3jkl {
		width: 100%;
		height: 100%;
		padding-top: 10px;
	}
	.child-E17jXe3okl {
		width: 100%;
		height: 45px;
		display: flex;
		align-items: center;
		padding-left: 30px;
		cursor: pointer;
	}
	.child-E17jXe3okl:hover {
		background-color: #efecec;
	}
	.child-VkvsQehjyg {
		font-size: large;
		margin-left: 15px;
	}
</style>
