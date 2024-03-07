<script setup lang="ts">
	import ArticleDetailReader from "@/components/articleReader/ArticleDetailReader.vue";
	import BlogHeader from "@/components/BlogHeader.vue";
	const props = defineProps<{
		slug: string;
	}>();
</script>
<template>
	<BlogHeader></BlogHeader>
	<div class="parent-VyHIkUzpyx">
		<div class="parent-NJ8uk8fa1e">
			<ArticleDetailReader :slug="props.slug" />
		</div>
		<div class="parent-"></div>
	</div>
</template>
<style scoped>
	.parent-VyHIkUzpyx {
		margin-top: 15px;
	}
	.parent-NJ8uk8fa1e {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
</style>
