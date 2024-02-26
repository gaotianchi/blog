<script setup lang="ts">
	import { computed, onMounted, ref } from "vue";
	import {
		allRemoteSeriesCards,
		getAllRemoteSeriesCards,
	} from "@/api/remote";
	import type { SeriesCardWithIndex } from "@/typing";
	const currentSeriesCards = ref(allRemoteSeriesCards);
	const articlesWithIndex = computed<SeriesCardWithIndex[]>(() => {
		const result: SeriesCardWithIndex[] = [];
		currentSeriesCards.value.forEach((card) => {
			const index = allRemoteSeriesCards.findIndex(
				(i) => i.id === card.id
			);
			result.push({
				index: index,
				series: card,
			});
		});
		return result;
	});
	onMounted(() => {
		initAllRemoteSeries();
	});
	async function initAllRemoteSeries(): Promise<void> {
		try {
			const response = await getAllRemoteSeriesCards();
			Object.assign(allRemoteSeriesCards, response);
			console.log(allRemoteSeriesCards);
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template></template>
<style scoped></style>
