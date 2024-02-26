<script setup lang="ts">
	import { ref, watch, type Ref, reactive } from "vue";
	import { Index, Document } from "flexsearch";
	import ArticlesPanel from "@/components/adminPanel/ArticlesPanel.vue";
	const data = [
		{
			author: "Gaotianchi1",
			createdAt: "2024-02-25T04:59:08+0000",
			id: 2,
			images: [
				"https://octodex.github.com/images/minion.png",
				'https://octodex.github.com/images/stormtroopocat.jpg "The Stormtroopocat"',
			],
			isPublished: true,
			planned: false,
			seriesId: 0,
			tags: ["example", "markdown", "hello"],
			title: "MARKDOWN IT EXAMPLE",
		},
		{
			author: "Gaotianchi1",
			createdAt: "2024-02-25T10:25:38+0000",
			id: 3,
			images: [
				"https://imzbf.github.io/md-editor-rt/imgs/mark_emoji.gif",
			],
			isPublished: false,
			planned: false,
			seriesId: 0,
			tags: ["love"],
			title: "md-editor-v3",
		},
	];
	const articleCardIndex = new Document({
		document: {
			id: "id",
			tag: "tags",
			index: [
				{
					field: "title",
					tokenize: "full",
				},
			],
		},
	});
	data.forEach((d) => {
		articleCardIndex.add(d);
	});

</script>

<template>
	<div>
		<form></form>
	</div>
</template>

<style scoped></style>
