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
	}
</script>

<template>
	{{ currentSeries }}
	<Series
		:series="{
			id: 0,
			name: '',
			cover: '',
			author_id: 0,
		}"
		@update-series="update"
	/>
</template>

<style scoped></style>
