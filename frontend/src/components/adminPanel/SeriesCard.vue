<script setup lang="ts">
	import icons from "@/components/icons";
	import { onMounted, reactive, ref, type Ref } from "vue";
	import {
		allRemoteSeriesCards,
		getSeriesArticlesCount,
		deleteSeriesItem,
		patchSeriesCard,
		postMediaItem,
	} from "@/api/remote";
	import { dateFormatter, getLocalDatetime } from "@/utlis";
	import type { SeriesCard } from "@/typing";
	import { propConfirm, propMessage } from "@/api/local";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import SeriesEditor from "./SeriesEditor.vue";
	const props = defineProps<{
		seriesCard: SeriesCard;
	}>();
	const status = reactive({
		actionMenu: false,
		confirmSlot: false,
	});
	const newSeriesCard: Ref<SeriesCard> = ref(props.seriesCard);
	const newCoverImg: Ref<File | null> = ref(null);
	const count = ref(0);
	onMounted(() => {
		initCount();
	});
	async function initCount(): Promise<void> {
		try {
			const response = await getSeriesArticlesCount(props.seriesCard.id);
			count.value = response;
		} catch (error) {
			console.error(error);
		}
	}
	function decideToDeleteSeries(): void {
		status.actionMenu = false;
		propConfirm({
			header: "Delete series",
			body: `Are you sure you want to delete series 《${props.seriesCard.name}》`,
			yesMessage: "Publish",
			noMessage: "Cancel",
			callback: deleteSeries,
		});
	}
	function deleteSeries(): void {
		status.actionMenu = false;
		try {
			propMessage("Deleting series ...");
			deleteSeriesItem(props.seriesCard.id).then(() => {
				propMessage("Successfully delete series.");
				const index = allRemoteSeriesCards.findIndex(
					(i) => i.id === props.seriesCard.id
				);
				allRemoteSeriesCards.splice(index, 1);
			});
		} catch (error) {
			console.error(error);
			propMessage("Please try again.");
		}
	}
	function updateSeries(): void {
		propMessage("Updating series ...");
		status.confirmSlot = false;
		if (newCoverImg.value) {
			console.log(newCoverImg.value);
			try {
				postMediaItem(newCoverImg.value);
			} catch (error) {
				console.error(error);
			}
		} else {
			console.log(newSeriesCard.value.cover);
		}
		try {
			patchSeriesCard(newSeriesCard.value).then((card) => {
				const index = allRemoteSeriesCards.findIndex(
					(i) => i.id === props.seriesCard.id
				);
				console.log(card);
				allRemoteSeriesCards[index] = card;
				console.log(allRemoteSeriesCards[index]);
				propMessage("Change saved.");
			});
		} catch (error) {
			console.error(error);
			propMessage("Please try again.");
		}
	}
</script>
<template>
	<ConfirmSlot
		:status="status.confirmSlot"
		:callback="updateSeries"
		@close="status.confirmSlot = false"
	>
		<template #header>Editor series</template>
		<template #body>
			<SeriesEditor
				:series-card="props.seriesCard"
				@updateSeriesCard="
					(card, coverImg) => {
						newSeriesCard = card;
						newCoverImg = coverImg;
					}
				"
			/>
		</template>
		<template #noMessage>Cancel</template>
		<template #yesMessage>Apply</template>
	</ConfirmSlot>
	<div class="parent-EytiXaw31g">
		<div class="parent-4khhQ6D3ke">
			<img
				:src="seriesCard.cover ?? '/default-cover.jpg'"
				alt="series cover"
				class="parent-NJXiS6wnJx"
			/>
			<div class="parent-41bILTD21l">
				<span class="parent-Nk7yvaPh1x">
					{{ count }}
				</span>
				<component
					:is="icons.count"
					class="icon white parent-NkUYtTw21l"
				/>
			</div>
		</div>
		<div class="parent-EkmamTwnyg">
			{{ seriesCard.name }}
		</div>
		<div class="parent-Vy_p7pw2yx">
			<div class="parent-EkKaj6whkg">
				{{
					dateFormatter(
						getLocalDatetime(seriesCard.createdAt),
						"ddd MMM DD YYYY"
					)
				}}
			</div>
			<div class="parent-VkYy26w2kx">
				<component
					:is="icons.more"
					class="parent-N1NQn6v31x icon"
					@click="status.actionMenu = !status.actionMenu"
				/>
				<div class="parent-NJvdnpP3Jx" v-if="status.actionMenu">
					<div
						class="child-E1aa2TvhJx"
						@click="
							() => {
								status.confirmSlot = true;
								status.actionMenu = false;
							}
						"
					>
						<span class="child-N1B16pv3yx">Edit</span>
					</div>
					<div class="child-E1aa2TvhJx" @click="decideToDeleteSeries">
						<span class="child-N1B16pv3yx">Delete</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<style scoped>
	.parent-EytiXaw31g {
		width: 220px;
		height: fit-content;
		display: flex;
		flex-direction: column;
		margin: 0 30px 40px 0;
	}
	.parent-4khhQ6D3ke {
		position: relative;
		width: 220px;
		height: 150px;
		cursor: pointer;
	}
	.parent-NJXiS6wnJx {
		width: 220px;
		height: 150px;
	}
	.parent-41bILTD21l {
		position: absolute;
		width: 80px;
		height: 150px;
		right: 0;
		top: 0;
		background-color: rgba(66, 63, 63, 0.47);
		display: flex;
		flex-direction: column;
		justify-content: flex-end;
		align-items: center;
	}
	.parent-Nk7yvaPh1x {
		color: white;
		margin-bottom: 15px;
		font-size: large;
	}
	.parent-NkUYtTw21l {
		margin-bottom: 20px;
	}
	.parent-EkmamTwnyg {
		font-size: 15px;
		line-height: 20px;
		margin: 5px 0;
		cursor: pointer;
	}
	.parent-EkKaj6whkg {
		font-size: 14px;
		color: grey;
		line-height: 15px;
	}
	.parent-Vy_p7pw2yx {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.parent-VkYy26w2kx {
		position: relative;
		width: 24px;
		height: 24px;
		cursor: pointer;
		border-radius: 50%;
	}
	.parent-VkYy26w2kx:hover {
		background-color: lightgrey;
	}
	.parent-NJvdnpP3Jx {
		position: absolute;
		right: 0;
		top: 24px;
		display: flex;
		flex-direction: column;
		height: fit-content;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
		z-index: 999;
		padding: 5px 0;
	}
	.child-E1aa2TvhJx {
		width: 100px;
		height: 40px;
		display: flex;
		align-items: center;
		cursor: pointer;
		padding-left: 10px;
	}
	.child-E1aa2TvhJx:hover {
		background-color: lightgrey;
	}
</style>
