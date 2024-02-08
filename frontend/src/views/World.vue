<script setup lang="ts">
	import { reactive, ref, type Ref } from "vue";
	import SettingBar from "@/components/blog/SettingBar.vue";
	type Settings = {
		tags: string[];
		datetime: Date;
		permalink: string;
		series_id: number | null;
	};
	
	function update(s: Settings): void {
		settings.value = s;
		console.log(settings);
	}
	const props: Settings = {
		tags: [],
		datetime: new Date(),
		permalink: "hello-world-hello",
		series_id: null,
	};
	const settings: Ref<Settings> = ref(props);
</script>

<template>
	<SettingBar :settings="props" @update-settings="update" />
	{{ settings }}
</template>
