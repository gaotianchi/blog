<script setup lang="ts">
	import BlogHeader from "@/components/BlogHeader.vue";
	import ArticlePreviewCardVue from "@/components/articleReader/ArticlePreviewCard.vue";
	import { getArticlePreivewCards } from "@/api/remote";
	import { onMounted, ref, type Ref } from "vue";
	import type { ArticlePreivewCard } from "@/typing";
	const currentPage: Ref<number> = ref(1);
	const currentCards: Ref<ArticlePreivewCard[]> = ref([]);
	onMounted(() => {
		initPreviewCards();
	});
	function initPreviewCards(): void {
		try {
			getArticlePreivewCards(currentPage.value).then((resp) => {
				currentCards.value = resp;
			});
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<BlogHeader></BlogHeader>
	<div class="parent-NJ4mHLfTyx">
		<div class="parent-"></div>
		<div class="parent-VkHrHIM61l" v-if="currentCards.length > 0">
			<ArticlePreviewCardVue
				class="parent-Vyz2v8fTke"
				v-for="c in currentCards"
				:preview-card="c"
			/>
		</div>
		<div class="parent-NJvaDIfp1x">No articles.</div>
	</div>
</template>
<style scoped>
	.parent-NJ4mHLfTyx {
        margin-top: 15px;
	}
	.parent-VkHrHIM61l {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.parent-Vyz2v8fTke {
        margin-bottom: 20px;
	}
</style>
