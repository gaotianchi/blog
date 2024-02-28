<script setup lang="ts">
	import { computed, onMounted, ref } from "vue";
	import {
		allRemoteSeriesCards,
		getAllRemoteSeriesCards,
	} from "@/api/remote";
	import SeriesCard from "./SeriesCard.vue";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import icons from "@/components/icons";
	const status = {
		newSeries: false,
	};
	onMounted(() => {
		initAllRemoteSeries();
	});
	async function initAllRemoteSeries(): Promise<void> {
		try {
			const response = await getAllRemoteSeriesCards();
			Object.assign(allRemoteSeriesCards, response);
			console.log(allRemoteSeriesCards);
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<ConfirmSlot
		:status="status.newSeries"
		:callback="() => {}"
		@close="status.newSeries = false"
	>
		<template #header>New series</template>
		<template #body> </template>
		<template #noMessage>Cancel</template>
		<template #yesMessage>Apply</template>
	</ConfirmSlot>
	<div class="parent-EkmTx0v3kg">
		<div class="parent-NkEg-AD3Jg">Breadcrumbs</div>
		<div class="parent-Nkn-ZCv3Je">
			<SeriesCard v-for="s in allRemoteSeriesCards" :series-card="s" />
			<div class="parent-V1hd7GO2yl">
				<component :is="icons.add" class="parent-V1Np4Mdn1e" />
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
		width: 150px;
		display: flex;
		justify-content: center;
		align-items: center;
		cursor: pointer;
		border-radius: 50%;
		background-color: rgba(211, 211, 211, 0.566);
	}
	.parent-V1Np4Mdn1e {
		height: 100px;
		width: 100px;
	}
</style>
