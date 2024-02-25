<script setup lang="ts">
	import { getAllArticles, allRemoteArticleCards } from "@/api/remote";
	import ArticleCard from "./ArticleCard.vue";
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
		<div class="parent-E1k-9G6ske">
			<ArticleCard
				v-for="(article, index) in allRemoteArticleCards"
				:article-index="index"
			>
				<template #cover>
					<img width="80px" height="80px" :src="article.images[0]" />
				</template>
				<template #title>
					{{ article.title }}
				</template>
			</ArticleCard>
		</div>
	</div>
</template>
<style></style>
