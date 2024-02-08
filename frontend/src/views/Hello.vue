<script setup lang="ts">
	import Series from "@/components/blog/settings/Series.vue";
	import { type Ref, ref } from "vue";
	type Series = {
		id: number;
		name: string;
		author_id: number;
		cover: string | null;
	};
	const currentSeries: Ref<Series | null> = ref(null);
	function update(data: Series): void {
		currentSeries.value = data;
		console.log(currentSeries);
	}
</script>

<template>
	{{ currentSeries }}
	<Series
		:series="{
			id: 1,
			name: 'hello world',
			cover: 'hello world',
			author_id: 1,
		}"
		@update-series="update"
	/>
</template>

<style scoped></style>
