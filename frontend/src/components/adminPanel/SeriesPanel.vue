<script setup lang="ts">
	import { onMounted, ref, type Ref, reactive } from "vue";
	import { postMediaItem, postSeriesItem, getAllSeries } from "@/api/remote";
	import { propMessage } from "@/api/local";
	import SeriesCardVue from "./SeriesCard.vue";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import icons from "@/components/icons";
	import SeriesEditor from "./SeriesEditor.vue";
	import { defaultSeries } from "@/defaults";
	import { allRemoteSeries } from "@/store";
	import type { Series } from "@/typing";
	const newSeries: Ref<Series> = ref(defaultSeries);
	const newCoverImg: Ref<File | null> = ref(null);
	const status = reactive({
		newSeries: false,
	});
	onMounted(() => {
		initAllRemoteSeries();
	});
	async function initAllRemoteSeries(): Promise<void> {
		try {
			const response = await getAllSeries();
			Object.assign(allRemoteSeries, response);
		} catch (error) {
			console.error(error);
		}
	}
	function createSeries(): void {
		propMessage("Creating series ...");
		if (!newSeries.value.name.trim()) {
			alert("Series name cannot be empty.");
			propMessage("Please try again.");
			status.newSeries = true;
			return;
		}
		if (newCoverImg.value) {
			try {
				postMediaItem(newCoverImg.value);
			} catch (error) {
				console.error(error);
			}
		}
		try {
			postSeriesItem(newSeries.value).then((seriesData) => {
				allRemoteSeries.push(seriesData);
				propMessage("Successfully create new series.");
			});
		} catch (error) {
			console.error(error);
			propMessage("Please try again.");
		}
	}
</script>
<template>
	<ConfirmSlot
		:status="status.newSeries"
		:callback="createSeries"
		@close="status.newSeries = false"
	>
		<template #header>New series</template>
		<template #body>
			<SeriesEditor
				:series="{ ...defaultSeries }"
				@update-series="
					(series, coverImg) => {
						newCoverImg = coverImg;
						newSeries = series;
					}
				"
			/>
		</template>
		<template #noMessage>Cancel</template>
		<template #yesMessage>Create</template>
	</ConfirmSlot>
	<div class="parent-EkmTx0v3kg">
		<div class="parent-NkEg-AD3Jg">Breadcrumbs</div>
		<div class="parent-Nkn-ZCv3Je">
			<SeriesCardVue v-for="s in allRemoteSeries" :series="s" />
			<div class="parent-V1hd7GO2yl" @click="status.newSeries = true">
				<component :is="icons.add2" class="parent-V1Np4Mdn1e" />
			</div>
		</div>
	</div>
</template>
<style scoped>
	.parent-Nkn-ZCv3Je {
		display: flex;
		flex-wrap: wrap;
	}
	.parent-V1hd7GO2yl {
		margin: 0 30px 40px 0;
		height: 150px;
		width: 220px;
		display: flex;
		justify-content: center;
		align-items: center;
		cursor: pointer;
		background-color: rgba(211, 211, 211, 0.566);
	}
	.parent-V1Np4Mdn1e {
		height: 100px;
		width: 100px;
	}
</style>
