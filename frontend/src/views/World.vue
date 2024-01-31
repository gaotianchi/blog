<script setup lang="ts">
	import { reactive, ref, type Ref } from "vue";
	import SettingBar from "@/components/blog/SettingBar.vue";

	type SeriesItems = {
		title?: string;
		cover?: string;
	};
	type Settings = {
		tags?: string[];
		datetime?: Date;
		permalink?: string;
		series?: SeriesItems;
	};
	const initSettings = {
		tags: [],
		datetime: new Date(),
		permalink: "hello-world",
		series: {
			title: "In minim aliquyam erat diam ipsum aliquyam diam",
		},
	};
	const settings: Ref<Settings> = ref(initSettings);
	function update(set: Settings) {
		settings.value = set;
	}
</script>

<template>
	<SettingBar :settings="initSettings" @update-settings="update" />
	{{ settings }}
</template>
