<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import type { Series } from "@/typing";
	import SeriesVue from "@/components/blog/settings/Series.vue";
	const origin = 47;
	const current: Ref<Series> = ref({
		id: origin,
		cover: "",
		name: "",
		author_id: 0,
	});
</script>

<template>
	<div class="parent">
		{{ current }}
	</div>
	<SeriesVue
		:series-id="origin"
		@update-series="
			(s) => {
				current = s;
			}
		"
	/>
</template>
<style scoped>
	.parent {
		min-height: 150px;
	}
</style>
