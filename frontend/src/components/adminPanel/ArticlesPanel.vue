<script setup lang="ts">
	import { onMounted, ref, watch, type Ref, computed } from "vue";
	import { useRoute, useRouter } from "vue-router";
	import { getAllArticleCards } from "@/api/remote";
	import { allRemoteArticleCards, articleCardIndex } from "@/store";
	import ArticleCardVue from "./ArticleCard.vue";
	import type {
		ArticleCard,
		ArticleCardStatus,
		ArticleSearchField,
		ArticleCardMeta,
	} from "@/typing";
	const route = useRoute();
	const router = useRouter();
	const currentArticleCards = ref(allRemoteArticleCards);
	const articleFilter: Ref<ArticleCardStatus> = ref("all");
	onMounted(() => {
		initAllRemoteArticleCards();
	});
	watch(
		() => route.query,
		() => {
			updateCurrentCards();
		}
	);

	function filterByMeta(meta: ArticleCardMeta, query: string): ArticleCard[] {
		if (meta === "tag") {
			return filterByTags(query.split(","));
		}
		const searchResult = articleCardIndex.search(query, [meta]);
		const articleIds = (searchResult[0]?.result as number[]) || [];
		const resultArticles = allRemoteArticleCards.value.filter((articleCard) =>
			articleIds.includes(articleCard.id)
		);
		return resultArticles || [];
	}
	function filterByTags(tags: string[]): ArticleCard[] {
		const searchResult = articleCardIndex.search({
			tag: tags,
			bool: "and",
		});
		const articleIds = (searchResult[0]?.result as number[]) || [];
		const resultArticles = allRemoteArticleCards.value.filter((articleCard) =>
			articleIds.includes(articleCard.id)
		);
		return resultArticles || [];
	}
	function filterByStatus(status: ArticleCardStatus): ArticleCard[] {
		switch (status) {
			case "published":
				return allRemoteArticleCards.value.filter(
					(i) => i.isPublished === true && i.planned === false
				);
			case "planned":
				return allRemoteArticleCards.value.filter((i) => i.planned === true);
			case "draft":
				return allRemoteArticleCards.value.filter(
					(i) => i.isPublished === false
				);
			default:
				return allRemoteArticleCards.value;
		}
	}
	function filterBySeries(seriesId: string): ArticleCard[] {
		return allRemoteArticleCards.value.filter(
			(i) => i.seriesId.toString() === seriesId
		);
	}
	function getFilteredCards(
		filter: ArticleSearchField,
		query: ArticleCardStatus | string
	): ArticleCard[] {
		switch (filter) {
			case "title":
				return filterByMeta(filter, query);
			case "author":
				return filterByMeta(filter, query);
			case "tag":
				return filterByMeta(filter, query);
			case "series":
				return filterBySeries(query);
			case "status":
				return filterByStatus(query as ArticleCardStatus);
			default:
				return [];
		}
	}
	function updateCurrentCards(): void {
		if (route.query.filter && route.query.query) {
			currentArticleCards.value = getFilteredCards(
				route.query.filter as ArticleSearchField,
				route.query.query as string
			);
		} else {
			currentArticleCards.value = allRemoteArticleCards.value;
		}
	}
	async function initAllRemoteArticleCards(): Promise<void> {
		try {
			const response = await getAllArticleCards();
			allRemoteArticleCards.value = response;
			allRemoteArticleCards.value.forEach((articleCard) => {
				articleCardIndex.add(articleCard);
			});
		} catch (error) {
			console.error(error);
		}
	}
	function updateWithStatus(status: ArticleCardStatus): void {
		router.push({
			name: "ArticlesPanel",
			query: {
				filter: "status",
				query: status,
			},
		});
		articleFilter.value = status;
	}
</script>
<template>
	<div class="parent-Eysk5zpi1e">
		<div class="parent-4JqeqzTjyg">
			<div class="parent-NyBb9GHhyg">
				<div
					class="child-4yUMcMShJl"
					@click="updateWithStatus('all')"
					:class="{ active: articleFilter === 'all' }"
				>
					<span class="child-4kw8qGH3yx">All</span>
					<span
						class="child-NyINgmrhkl"
						v-if="
							articleFilter === 'all' &&
							route.query?.filter === 'status'
						"
					>
						{{ currentArticleCards.length }}
					</span>
				</div>
				<div
					class="child-4yUMcMShJl"
					@click="updateWithStatus('published')"
					:class="{ active: articleFilter === 'published' }"
				>
					<span class="child-4kw8qGH3yx">Published</span>
					<span
						class="child-NyINgmrhkl"
						v-if="
							articleFilter === 'published' &&
							route.query?.filter === 'status'
						"
					>
						{{ currentArticleCards.length }}
					</span>
				</div>
				<div
					class="child-4yUMcMShJl"
					@click="updateWithStatus('planned')"
					:class="{ active: articleFilter === 'planned' }"
				>
					<span class="child-4kw8qGH3yx">Planned</span>
					<span
						class="child-NyINgmrhkl"
						v-if="
							articleFilter === 'planned' &&
							route.query?.filter === 'status'
						"
					>
						{{ currentArticleCards.length }}
					</span>
				</div>
				<div
					class="child-4yUMcMShJl"
					@click="updateWithStatus('draft')"
					:class="{ active: articleFilter === 'draft' }"
				>
					<span class="child-4kw8qGH3yx">Draft</span>
					<span
						class="child-NyINgmrhkl"
						v-if="
							articleFilter === 'draft' &&
							route.query?.filter === 'status'
						"
					>
						{{ currentArticleCards.length }}
					</span>
				</div>
			</div>
			<div class="parent-Vk5b5GB3kl"></div>
		</div>
		<div class="parent-E1k-9G6ske">
			<ArticleCardVue
				v-for="item in currentArticleCards"
				:article-card="item"
			>
				<template #cover>
					<img width="80px" height="80px" :src="item.images[0]" />
				</template>
				<template #title>
					{{ item.title }}
				</template>
			</ArticleCardVue>
		</div>
	</div>
</template>
<style scoped>
	.parent-Eysk5zpi1e {
		width: 100%;
	}
	.parent-4JqeqzTjyg {
		margin-bottom: 15px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		height: 40px;
		width: 100%;
	}
	.parent-NyBb9GHhyg {
		display: flex;
		align-items: center;
	}
	.child-4yUMcMShJl {
		display: flex;
		justify-content: center;
		align-items: center;
		border: lightgrey solid 1px;
		border-radius: 3px;
		height: 30px;
		padding: 0 10px;
		margin-right: 10px;
		cursor: pointer;
	}
	.child-4kw8qGH3yx {
		font-size: 16px;
		line-height: 16px;
	}
	.child-4yUMcMShJl:hover {
		background-color: aliceblue;
	}
	.child-4yUMcMShJl.active {
		background-color: aliceblue;
	}
	.child-NyINgmrhkl {
		font-size: 13px;
		line-height: 13px;
		height: 19px;
		background-color: #15d3c8;
		border-radius: 50%;
		padding: 0 5px;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-left: 5px;
	}
</style>
