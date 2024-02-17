<script setup lang="ts">
	import { onMounted } from "vue";
	import {
		getRemoteSeriesItem,
		remoteArticle,
		remoteSeries,
	} from "@/api/remote";
	import { localArticle, localSeries, settingStatus } from "@/api/local";
	import { dateFormatter, limString } from "@/utlis";
	import SettingItem from "./SettingItem.vue";
	import Tags from "./Tags.vue";
	import Datetime from "./Datetime.vue";
	import Permalink from "./Permalink.vue";
	import Series from "./Series.vue";
	onMounted(() => {
		initSeriesItems();
	});
	async function initSeriesItems(): Promise<void> {
		if (remoteArticle.seriesId === 0) {
			console.log("No series found.");
			return;
		}
		if (remoteSeries.id === localSeries.id) {
			console.log("Series has been load.");
			return;
		}
		try {
			const seriesData = await getRemoteSeriesItem(
				remoteArticle.seriesId
			);
			Object.assign(localSeries, seriesData);
			Object.assign(remoteSeries, seriesData);
			return;
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<div class="parent-NJJFyG_jJx">
		<SettingItem
			:init-status="settingStatus.tags.open"
			@change-status="
				(s) => {
					settingStatus.tags.open = s;
				}
			"
		>
			<template #title>Tags</template>
			<template #preview>{{
				limString(
					localArticle.tags.length > 0
						? localArticle.tags.join(",")
						: "No tags",
					40
				)
			}}</template>
			<template #detail>
				<Tags />
			</template>
		</SettingItem>
		<SettingItem
			:init-status="settingStatus.datetime.open"
			@change-status="
				(s) => {
					settingStatus.datetime.open = s;
				}
			"
		>
			<template #title>Publish Date</template>
			<template #preview>{{
				dateFormatter(localArticle.publishedAt)
			}}</template>
			<template #detail>
				<Datetime />
			</template>
		</SettingItem>
		<SettingItem
			:init-status="settingStatus.permalink.open"
			@change-status="
				(s) => {
					settingStatus.permalink.open = s;
				}
			"
		>
			<template #title>Permalink</template>
			<template #preview>{{
				limString("https://gaotianchi.com/" + localArticle.slug, 40)
			}}</template>
			<template #detail>
				<Permalink />
			</template>
		</SettingItem>
		<SettingItem
			:init-status="settingStatus.series.open"
			@change-status="
				(s) => {
					settingStatus.series.open = s;
				}
			"
		>
			<template #title>Series</template>
			<template #preview>{{
				limString(localSeries.name || "No series selected", 40)
			}}</template>
			<template #detail>
				<Series />
			</template>
		</SettingItem>
	</div>
</template>
<style scoped></style>
