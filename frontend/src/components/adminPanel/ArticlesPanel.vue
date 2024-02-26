<script setup lang="ts">
	import { onMounted, ref, watch } from "vue";
	import { useRoute } from "vue-router";
	import { getAllArticles, allRemoteArticleCards } from "@/api/remote";
	import { articleCardIndex } from "@/api/local";
	import ArticleCardVue from "./ArticleCard.vue";
	import type { ArticleCard, ArticleSearchField } from "@/typing";
	const route = useRoute();
	const currentArticleCards = ref(allRemoteArticleCards);
	onMounted(() => {
		initAllRemoteArticleCards();
	});
	watch(
		() => route.query,
		() => {
			updateCurrentCards();
		}
	);
	function updateCurrentCards(): void {
		const searchText = route.query.q as string;
		const searchField = searchText.split(":")[0] as ArticleSearchField;
		const query = searchText.split(":")[1] || searchText;
		currentArticleCards.value = getSearchResult(query, searchField);
	}
	function getSearchResult(
		query: string,
		field: ArticleSearchField
	): ArticleCard[] {
		let searchResult = [];
		let articleIds: number[] = [];
		let resultArticles: ArticleCard[] = [];
		switch (field) {
			case "author":
				searchResult = articleCardIndex.search(query, ["author"]);
				articleIds = (searchResult[0]?.result as number[]) || [];
				resultArticles = allRemoteArticleCards.filter((articleCard) =>
					articleIds.includes(articleCard.id)
				);
				return resultArticles || [];
			case "tag":
				const tags = query.split(",").map((i) => i.trim()) || [];
				searchResult = articleCardIndex.search({
					tag: tags,
				});
				articleIds = (searchResult[0]?.result as number[]) || [];
				resultArticles = allRemoteArticleCards.filter((articleCard) =>
					articleIds.includes(articleCard.id)
				);
				return resultArticles || [];
			default:
				searchResult = articleCardIndex.search(query, ["title"]);
				articleIds = (searchResult[0]?.result as number[]) || [];
				resultArticles = allRemoteArticleCards.filter((articleCard) =>
					articleIds.includes(articleCard.id)
				);
				return resultArticles || [];
		}
	}
	async function initAllRemoteArticleCards(): Promise<void> {
		try {
			const response = await getAllArticles();
			Object.assign(allRemoteArticleCards, response);
			allRemoteArticleCards.forEach((articleCard) => {
				articleCardIndex.add(articleCard);
			});
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<div class="parent-Eysk5zpi1e">
		<div class="parent-4JqeqzTjyg"></div>
		<div class="parent-E1k-9G6ske">
			<ArticleCardVue
				v-for="(article, index) in currentArticleCards"
				:article-index="index"
			>
				<template #cover>
					<img width="80px" height="80px" :src="article.images[0]" />
				</template>
				<template #title>
					{{ article.title }}
				</template>
			</ArticleCardVue>
		</div>
	</div>
</template>
<style></style>
