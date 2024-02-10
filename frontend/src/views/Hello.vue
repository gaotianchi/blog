<script setup lang="ts">
	import Series from "@/components/blog/settings/Series.vue";
	import { type Ref, ref } from "vue";
	type Series = {
		id: number;
		name: string;
		author_id: number;
		cover: number;
	};
	const originalSeriesId = 47;
	const currentSeriesId = ref(originalSeriesId);
</script>

<template>
	<div>{{ "current: " + currentSeriesId }}</div>
	<Series
		:series-id="originalSeriesId"
		@update-series="
			(result) => {
				currentSeriesId = result;
			}
		"
	/>
</template>

<style scoped></style>
