<script setup lang="ts">
	import icons from "@/components/icons";
	import { ref, watchEffect, type Ref } from "vue";
	import { MdEditor } from "md-editor-v3";
	import "md-editor-v3/lib/style.css";
	type ArticleItems = {
		title?: string;
		body?: string;
		tags?: string[];
	};
	const props = defineProps<{
		article: ArticleItems;
	}>();
</script>

<template>
	<!-- <MdEditor v-model="" /> -->
</template>

<style scoped></style>
