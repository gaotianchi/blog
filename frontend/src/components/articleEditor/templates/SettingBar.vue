<script setup lang="ts">
	import SettingItem from "./SettingItem.vue";
	import Tags from "./settings/Tags.vue";
	import Datetime from "./settings/Datetime.vue";
	import Permalink from "./settings/Permalink.vue";
	import Series from "./settings/Series.vue";
	import {
		getRemoteSeriesItem,
		remoteArticle,
		remoteSeries,
	} from "../remoteApi";
	import { localArticle, localSeries } from "../localApi";
	import { dateFormatter } from "../utlis";
	import { onMounted } from "vue";
	onMounted(() => {
		initSeriesItems();
	});
	async function initSeriesItems(): Promise<void> {
		if (remoteArticle.seriesId === 0) {
			console.log("No series found.");
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
		<SettingItem>
			<template #title>Tags</template>
			<template #preview>{{
				localArticle.tags.length > 0
					? localArticle.tags.join(",")
					: "No tags"
			}}</template>
			<template #detail>
				<Tags />
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Publish Date</template>
			<template #preview>{{
				dateFormatter(localArticle.publishedAt)
			}}</template>
			<template #detail>
				<Datetime />
			</template>
		</SettingItem>
		<SettingItem>
			<template #title>Permalink</template>
			<template #preview>{{
				"https://gaotianchi.com/" + localArticle.slug
			}}</template>
			<template #detail>
				<Permalink />
			</template>
		</SettingItem>
	</div>
</template>
<style></style>
