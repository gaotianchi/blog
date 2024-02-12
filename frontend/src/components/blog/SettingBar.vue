<script setup lang="ts">
	import TagVue from "./settings/Tag.vue";
	import Datetime from "./settings/Datetime.vue";
	import Permalink from "./settings/Permalink.vue";
	import SeriesVue from "./settings/Series.vue";
	import SettingItem from "./SettingItem.vue";
	import { onMounted, reactive, watchEffect } from "vue";
	import { format } from "date-fns";
	import type { Series, Settings } from "@/typing";
	import { limString } from "@/api";

	const emits = defineEmits<{
		updateSettings: [settings: Settings];
	}>();
	const props = defineProps<{
		settings: Settings;
	}>();
	const currentSettings: Settings = reactive(props.settings);
	const currentSeries: Series = reactive({
		id: props.settings.seriesId,
		name: "",
		cover: "",
		author_id: 0,
	});
	watchEffect(() => {
		emits("updateSettings", currentSettings);
	});
</script>
<template>
	<div class="parent-Vk3Ihqa5kg">
		<SettingItem>
			<template #title>Tags</template>
			<template #preview>{{
				currentSettings.tags.length > 0
					? currentSettings.tags?.join(",").trim()
					: "No tags selected"
			}}</template>
			<template #detail>
				<TagVue
					:tags="props.settings.tags"
					@update-tags="
						(tags) => {
							currentSettings.tags = tags;
						}
					"
				/>
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Datetime</template>
			<template #preview>
				{{ format(currentSettings.datetime, "yyyy-MM-dd HH:mm") }}
			</template>
			<template #detail>
				<Datetime
					:datetime="props.settings.datetime"
					@update-datetime="
						(datetime) => {
							currentSettings.datetime = datetime;
						}
					"
				/>
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Permalink</template>
			<template #preview>{{
				limString("https://gaotianchi.com/" + currentSettings.permalink, 46)
			}}</template>
			<template #detail>
				<Permalink
					:permalink="props.settings.permalink"
					@update-permalink="
						(permalink) => {
							currentSettings.permalink = permalink;
						}
					"
				/>
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Series</template>
			<template #preview>{{
				currentSeries.name ?? "No series selected"
			}}</template>
			<template #detail>
				<SeriesVue
					:series-id="currentSettings.seriesId"
					@update-series="
						(series) => {
							currentSettings.seriesId = series.id;
							currentSeries = series;
						}
					"
				/>
			</template>
		</SettingItem>
	</div>
</template>
<style scoped>
	.parent-Vk3Ihqa5kg {
		width: 300px;
		height: fit-content;
	}
</style>
