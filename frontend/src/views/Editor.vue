<script setup lang="ts">
	import { localArticle, localSeries } from "@/api/local";
	import {
		getArticleItem,
		getRemoteSeriesItem,
		remoteArticle,
		remoteSeries,
	} from "@/api/remote";
	import type { Article } from "@/typing";
	import { onMounted, provide } from "vue";
	import BlogHeader from "@/components/BlogHeader.vue";
	import EditorBody from "@/components/articleEditor/EditorBody.vue";
	import EditorHeader from "@/components/articleEditor/EditorHeader.vue";
	const props = defineProps<{
		articleId: number | string;
	}>();
	provide("articleId", props.articleId);
	onMounted(() => {
		initArticleData();
	});
	async function initArticleData(): Promise<void> {
		try {
			const articleData: Article = await getArticleItem(props.articleId);
			Object.assign(localArticle, articleData);
			Object.assign(remoteArticle, articleData);
			if (articleData.seriesId != 0) {
				await initSeriesItems();
			}
		} catch (error) {
			console.error(error);
		}
	}
	async function initSeriesItems(): Promise<void> {
		try {
			const seriesData = await getRemoteSeriesItem(
				remoteArticle.seriesId
			);
			Object.assign(localSeries, seriesData);
			Object.assign(remoteSeries, seriesData);
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<BlogHeader></BlogHeader>
	<EditorHeader />
	<EditorBody />
</template>
<style src="@/assets/editor.css"></style>
