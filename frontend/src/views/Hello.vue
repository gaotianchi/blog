<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import type { Settings } from "@/typing";
	import SettingBar from "@/components/blog/SettingBar.vue";
	const origin: Settings = {
		tags: ["hello", "world"],
		datetime: new Date(),
		permalink: "how-are-you-doing",
		seriesId: 47,
	};
	const current = ref(origin);
</script>

<template>
	<div class="parent">
		{{ current }}
	</div>
	<SettingBar
		:settings="origin"
		@update-settings="
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
