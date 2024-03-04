<script setup lang="ts">
	import { getArticleItem, getSeriesItem } from "@/api/remote";
	import type { Article } from "@/typing";
	import { onMounted, provide } from "vue";
	import { editorLocalAndRemote, articleSerieLocalAndRemote } from "@/store";
	import BlogHeader from "@/components/BlogHeader.vue";
	import EditorBody from "@/components/articleEditor/EditorBody.vue";
	import EditorHeader from "@/components/articleEditor/EditorHeader.vue";
	import { defaultArticle, defaultSeries } from "@/defaults";
	const props = defineProps<{
		articleId: number | string;
	}>();
	provide("articleId", props.articleId);
	editorLocalAndRemote[props.articleId as number] = {
		remote: { ...defaultArticle },
		local: { ...defaultArticle },
	};
	articleSerieLocalAndRemote[props.articleId as number] = {
		remote: { ...defaultSeries },
		local: { ...defaultSeries },
	};
	onMounted(() => {
		initArticleData();
	});
	async function initArticleData(): Promise<void> {
		try {
			const articleData: Article = await getArticleItem(props.articleId);
			editorLocalAndRemote[props.articleId as number] = {
				remote: { ...articleData },
				local: { ...articleData },
			};
			if (articleData.seriesId != 0) {
				await initSeriesItems();
			}
		} catch (error) {
			console.error(error);
		}
	}
	async function initSeriesItems(): Promise<void> {
		try {
			const seriesData = await getSeriesItem(
				editorLocalAndRemote[props.articleId as number].remote.seriesId
			);
			articleSerieLocalAndRemote[props.articleId as number] = {
				remote: { ...seriesData },
				local: { ...seriesData },
			};
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
