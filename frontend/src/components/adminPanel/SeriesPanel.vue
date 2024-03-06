<script setup lang="ts">
	import { onMounted, ref, type Ref, reactive } from "vue";
	import {
		postMediaItem,
		postSeriesItem,
		getAllSeries,
		getAllArticleCards,
		patchArticleSeries,
		getSeriesArticleCouns,
	} from "@/api/remote";
	import { propMessage } from "@/api/local";
	import SeriesCardVue from "./SeriesCard.vue";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import icons from "@/components/icons";
	import SeriesEditor from "./SeriesEditor.vue";
	import { defaultSeries } from "@/defaults";
	import { allRemoteSeries } from "@/store";
	import type { ArticleCard, Series } from "@/typing";
	import ArticleCardVue from "./ArticleCard.vue";
	import { allRemoteArticleCards, seriesArticleCount } from "@/store";
	const newSeries: Ref<Series> = ref(defaultSeries);
	const newCoverImg: Ref<File | null> = ref(null);
	const status = reactive({
		newSeries: false,
		dropzone: false,
		activeSeries: 0,
	});
	const currentArticleCards: Ref<ArticleCard[]> = ref([]);
	const dragged: Ref<ArticleCard | null> = ref(null);
	onMounted(() => {
		initAllRemoteSeries();
		initAllRemoteArticleCards();
		initSeriesArticleCount();
	});
	async function initAllRemoteArticleCards(): Promise<void> {
		try {
			const response = await getAllArticleCards();
			allRemoteArticleCards.value = response;
		} catch (error) {
			console.error(error);
		}
	}
	async function initSeriesArticleCount(): Promise<void> {
		try {
			const data = await getSeriesArticleCouns();
			seriesArticleCount.value = data;
		} catch (error) {
			console.error(error);
		}
	}
	async function initAllRemoteSeries(): Promise<void> {
		try {
			const response = await getAllSeries();
			allRemoteSeries.value = response;
		} catch (error) {
			console.error(error);
		}
	}
	function updateArticleSeries(a: ArticleCard, s: Series): void {
		propMessage("Changing article series ...");
		try {
			patchArticleSeries(a.id, s.id).then((card) => {
				const origiSeriesIndex = seriesArticleCount.value.findIndex(
					(i) => i.seriesId === a.seriesId
				);
				const newSeriesIndex = seriesArticleCount.value.findIndex(
					(i) => i.seriesId === s.id
				);
				seriesArticleCount.value[origiSeriesIndex].articlesCount -= 1;
				seriesArticleCount.value[newSeriesIndex].articlesCount += 1;
				currentArticleCards.value = currentArticleCards.value.filter(
					(i) => i.id !== card.id
				);
				const index = allRemoteArticleCards.value.findIndex(
					(i) => i.id === a.id
				);
				allRemoteArticleCards.value[index].seriesId = s.id;

				propMessage("Changed saved.");
			});
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
				allRemoteSeries.value.push(seriesData);
				propMessage("Successfully create new series.");
			});
		} catch (error) {
			console.error(error);
			propMessage("Please try again.");
		}
	}
	function getArticleCards(seriesId: number): ArticleCard[] {
		const result = allRemoteArticleCards.value.filter(
			(i) => i.seriesId === seriesId
		);
		return result;
	}
</script>
<template>
	<div class="parent-EkmTx0v3kg" :class="{ active: status.dropzone }">
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
		<div class="parent-Nkn-ZCv3Je" :class="{ active: status.dropzone }">
			<SeriesCardVue
				v-for="s in allRemoteSeries"
				:series="s"
				@be-clicked="
					() => {
						status.dropzone = true;
						status.activeSeries = s.id;
						currentArticleCards = getArticleCards(s.id);
					}
				"
				class="parent-4JGZtUJp1g"
				:class="{
					dropzone: status.dropzone,
					active: status.activeSeries === s.id,
				}"
				@update-dropzone="
					(s) => {
						if (dragged) {
							updateArticleSeries(dragged, s);
						}
					}
				"
			/>
			<div class="parent-V1hd7GO2yl" @click="status.newSeries = true">
				<component :is="icons.add2" class="parent-V1Np4Mdn1e" />
			</div>
		</div>
		<div class="parent-4kNMSLy61x" v-if="status.dropzone">
			<ArticleCardVue
				v-for="a in currentArticleCards"
				:article-card="a"
				s-card-mode
				@update-dragging-card="
					(c) => {
						dragged = c;
					}
				"
			>
				<template #cover>
					<img width="80px" height="80px" :src="a.images[0]" />
				</template>
				<template #title>
					{{ a.title }}
				</template>
			</ArticleCardVue>
		</div>
	</div>
</template>
<style scoped>
	.parent-EkmTx0v3kg.active {
		display: grid;
		grid-template-columns: auto 1fr;
		gap: 5px;
	}
	.parent-Nkn-ZCv3Je {
		display: flex;
		flex-wrap: wrap;
	}
	.parent-Nkn-ZCv3Je.active {
		display: flex;
		flex-direction: column;
	}
	.parent-4JGZtUJp1g.active {
		width: min-content;
		background-color: lightgrey;
	}
	.parent-4JGZtUJp1g.dropzone {
		margin: 0 0 20px 0;
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
		margin: 20px;
	}
	.parent-V1Np4Mdn1e {
		height: 100px;
		width: 100px;
	}
</style>
