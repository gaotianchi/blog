<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import SettingBar from "@/components/blog/SettingBar.vue";
	type Settings = {
		tags: string[];
		datetime: Date;
		permalink: string;
		series_id: number;
	};
	
	function update(s: Settings): void {
		settings.value = s;
		console.log(settings);
	}
	const props: Settings = {
		tags: [],
		datetime: new Date(),
		permalink: "",
		series_id: 0,
	};
	const settings: Ref<Settings> = ref(props);
</script>

<template>
	<SettingBar :settings="props" @update-settings="(d) => {settings = d}" />
	{{ settings }}
</template>
