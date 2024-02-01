<script setup lang="ts">
	import TagVue from "./settings/Tag.vue";
	import Datetime from "./settings/Datetime.vue";
	import Permalink from "./settings/Permalink.vue";
	import SeriesVue from "./settings/Series.vue";
	import SettingItem from "./SettingItem.vue";
	import { reactive, watch } from "vue";
	import { format, getUnixTime } from "date-fns";
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
	const emits = defineEmits<{
		updateSettings: [settings: Settings];
	}>();
	const props = defineProps<{
		settings: Settings;
	}>();
	const currentSettings: Settings = reactive(props.settings);
	watch(
		currentSettings,
		(newSettings) => {
			emits("updateSettings", { ...newSettings });
		},
		{ deep: true }
	);

	function getAutoSlug(): string {
		const timestamp = getUnixTime(new Date()).toString();
		return "blog-post-" + timestamp;
	}
</script>
<template>
	<div class="parent-">
		<SettingItem>
			<template #title>Tags</template>
			<template #preview>{{ currentSettings.tags?.join(",") }}</template>
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
				{{
					format(
						currentSettings.datetime ?? new Date(),
						"yyyy-MM-dd HH:mm"
					)
				}}
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
				currentSettings.permalink ?? getAutoSlug()
			}}</template>
			<template #detail>
				<Permalink
					:permalink="props.settings.permalink ?? getAutoSlug()"
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
			<template #preview>{{ currentSettings.series?.title }}</template>
			<template #detail>
				<SeriesVue
					:series="props.settings.series ?? {}"
					@update-series="
						(series) => {
							currentSettings.series = series;
						}
					"
				/>
			</template>
		</SettingItem>
	</div>
</template>
<style scoped></style>
