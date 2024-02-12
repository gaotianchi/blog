<script setup lang="ts">
	import TagVue from "./settings/Tag.vue";
	import Datetime from "./settings/Datetime.vue";
	import Permalink from "./settings/Permalink.vue";
	import SeriesVue from "./settings/Series.vue";
	import SettingItem from "./SettingItem.vue";
	import { onMounted, reactive, watchEffect } from "vue";
	import { format } from "date-fns";
	import type { Article, Series, Settings } from "@/typing";
	import { getSeriesItem, limString } from "@/api";

	const emits = defineEmits<{
		updateSettings: [settings: Settings];
	}>();
	const props = defineProps<{
		settings: Settings;
	}>();
	const currentSettigns: Settings = reactive(props.settings);
	const futureSettings: Settings = reactive(props.settings);
	const currentSeries: Series = reactive({
		id: props.settings.seriesId,
		name: "",
		cover: "",
		author_id: 0,
	});
	onMounted(() => {});
	initCurrentSettings();
	initCurrentSeries();
	watchEffect(() => {
		emits("updateSettings", futureSettings);
	});
	function initCurrentSettings(): void {
		const articleData = sessionStorage.getItem("localArticle");
		const currentArticle: Article = JSON.parse(articleData || "");
		if (currentArticle) {
			currentSettigns.tags = currentArticle.tags;
			currentSettigns.datetime = new Date(currentArticle.publishedAt);
			currentSettigns.permalink = currentArticle.slug;
			currentSettigns.seriesId = currentArticle.seriesId;
		}
	}
	async function initCurrentSeries(): Promise<void> {
		if (!currentSettigns.seriesId) {
			console.log("No original series found.");
			return;
		}
		try {
			let currentSeriesItem: Series = { ...currentSeries };
			if (sessionStorage.getItem("localSeries")) {
				const seriesItem: Series = JSON.parse(
					sessionStorage.getItem("localSeries") as string
				);
				if (seriesItem.id === currentSettigns.seriesId) {
					currentSeriesItem = JSON.parse(
						sessionStorage.getItem("localSeries") as string
					);
				} else {
					currentSeriesItem = await getSeriesItem(
						currentSettigns.seriesId
					);
					sessionStorage.setItem(
						"localSeries",
						JSON.stringify(currentSeriesItem)
					);
				}
			} else {
				currentSeriesItem = await getSeriesItem(
					currentSettigns.seriesId
				);
				sessionStorage.setItem(
					"localSeries",
					JSON.stringify(currentSeriesItem)
				);
			}
			Object.assign(currentSeries, currentSeriesItem);
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<div class="parent-Vk3Ihqa5kg">
		<SettingItem>
			<template #title>Tags</template>
			<template #preview>{{
				currentSettigns.tags.length > 0
					? currentSettigns.tags?.join(",").trim()
					: "No tags selected"
			}}</template>
			<template #detail>
				<TagVue
					:tags="currentSettigns.tags"
					@update-tags="
						(tags) => {
							futureSettings.tags = tags;
						}
					"
				/>
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Datetime</template>
			<template #preview>
				{{ format(currentSettigns.datetime, "yyyy-MM-dd HH:mm") }}
			</template>
			<template #detail>
				<Datetime
					:datetime="currentSettigns.datetime"
					@update-datetime="
						(datetime) => {
							futureSettings.datetime = datetime;
						}
					"
				/>
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Permalink</template>
			<template #preview>{{
				limString(
					"https://gaotianchi.com/" + futureSettings.permalink,
					46
				)
			}}</template>
			<template #detail>
				<Permalink
					:permalink="props.settings.permalink"
					@update-permalink="
						(permalink) => {
							futureSettings.permalink = permalink;
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
					:series="currentSeries"
					@update-series="
						(series) => {
							futureSettings.seriesId = series.id;
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
		min-height: 500px;
	}
</style>
