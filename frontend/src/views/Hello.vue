<script setup lang="ts">
	import { ref, watch, type Ref, reactive, onMounted } from "vue";
	import ArticlePreviewCard from "@/components/articleReader/ArticlePreviewCard.vue";
	import ArticleDetailReader from "@/components/articleReader/ArticleDetailReader.vue";
</script>

<template>
	<!-- <ArticlePreviewCard
		:preview-card="{
			id: 1,
			title: 'Kasd eum ea at hendrerit soluta amet invidunt et clita exerci labore.',
			createdAt: '2024-03-07T01:31:08+0000',
			tags: ['hello', 'world'],
			images: [
				'https://images.unsplash.com/photo-1707343848723-bd87dea7b118?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHx8',
			],
			author: 'Gao Tianchi',
			summary:
				'Sea tempor feugiat est invidunt liber augue ipsum est placerat stet invidunt sed erat sanctus enim ipsum ea odio. \n\nEa dolores stet duo ipsum delenit dolor nibh. Lorem erat sadipscing vel consetetur et invidunt ipsum voluptua eirmod et in.\nDolor stet et sit nulla aliquyam dolore. Amet eu erat kasd lorem diam sea ea ipsum dolore elitr accusam sed sit justo. Clita labore duis et ipsum kasd diam et elitr eos et sit kasd vulputate sanctus gubergren sed rebum.',
		}"
	/> -->
	<ArticleDetailReader slug="Blog_article_20240307093143" />
</template>

<style scoped>
	.parent-EkfJ56wnyx {
		margin-left: 30px;
		margin-top: 30px;
	}
</style>
