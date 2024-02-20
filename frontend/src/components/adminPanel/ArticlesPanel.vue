<script setup lang="ts">
	import { getAllArticles, allRemoteArticleCards } from "@/api/remote";
	import ArticleCare from "./ArticleCare.vue";
	import { onMounted } from "vue";
	onMounted(() => {
		initAllRemoteArticleCards();
	});
	async function initAllRemoteArticleCards(): Promise<void> {
		try {
			const response = await getAllArticles();
			Object.assign(allRemoteArticleCards, response);
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<div class="parent-Eysk5zpi1e">
		<div class="parent-4JqeqzTjyg">Filter</div>
		<div class="parent-E1k-9G6ske">Article cards</div>
	</div>
</template>
<style></style>
