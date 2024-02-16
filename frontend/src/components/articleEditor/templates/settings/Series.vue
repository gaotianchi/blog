<script setup lang="ts">
	import Radio from "../Radio.vue";
	import InputA from "../InputA.vue";
	import { localArticle, settingStatus } from "../../localApi";
	import {
		remoteArticle,
		getAllRemoteSeriesItem,
		allRemoteSeries,
	} from "../../remoteApi";
	import icons from "@/components/icons";
	import { onMounted, reactive, ref, type Ref } from "vue";
	import Message from "../Message.vue";
	import Confirm from "../Confirm.vue";
	import { message, confirm } from "../../localApi";
	import type { PreviewCover } from "../../typing";
	const previewCover: PreviewCover = reactive({
		url: "",
		file: null,
	});
	onMounted(() => {
		loadAllSeries();
	});
	async function loadAllSeries(): Promise<void> {
		if (allRemoteSeries.length > 0) {
			console.log("Load remote series from local.");
			return;
		}
		try {
			const seriesData = await getAllRemoteSeriesItem();
			Object.assign(allRemoteSeries, seriesData);
		} catch (error) {
			console.error(error);
		}
	}
	async function createSeries(): Promise<void> {
		
	}
</script>
<template>
	<Message />
	<Confirm />
	<div class="parent-4JxPgYb9Jl">
		<div class="parent-EJ5IqDbqkl child-N1IGDObcye">{{}}</div>
		<div class="parent- child-N1IGDObcye">
			<Radio
				name="default"
				value="default"
				v-model="settingStatus.series.mode"
				@selected=""
				>Default</Radio
			>
			<Radio
				name="selected"
				value="selected"
				v-model="settingStatus.series.mode"
				@selected=""
				>Select another</Radio
			>
			<Radio
				name="new"
				value="new"
				v-model="settingStatus.series.mode"
				@selected=""
				>New</Radio
			>
		</div>
		<div
			class="parent-EyA9lY-5Jx child-N1IGDObcye"
			v-if="settingStatus.series.mode === 'selected'"
		>
			<div
				class="parent- child-EJRV0dWq1e"
				v-for="series in allRemoteSeries"
				v-if="allRemoteSeries.length > 0"
				@click=""
			>
				<div class="parent-4kGYZF-qJl child-4yb5kK-9yx">
					<img :src="series.cover" v-if="series.cover" />
				</div>
				<div class="parent-41Bi-YbqJe child-4yb5kK-9yx">{{}}</div>
			</div>
			<div class="child-EJRV0dWq1e" v-else>No series found.</div>
		</div>
		<div
			class="parent- child-N1IGDObcye"
			v-if="settingStatus.series.mode === 'new'"
		>
			<div class="parent-4y1uGqZ91g child-4kK6OY-cJl">
				<component
					v-if="previewCover.url"
					:is="icons.cancel"
					class="parent-EyjVKFb9Jl icon"
					@click="resetCover"
				/>
				<component
					v-if="!previewCover.url"
					:is="icons.uploadImage"
					class="parent-EyOtKYZcyl child-VkfBtF-cyg"
					@click="triggerFileInput"
				/>
				<img
					class="parent-4yg-t9W5kx"
					v-if="previewCover.url"
					:src="previewCover.url"
					alt="Preview cover"
				/>
				<input
					type="file"
					name="upload-img"
					id="upload-img"
					aria-label="upload-img"
					style="display: none"
					ref="uploadImageArea"
					@change="handleFileUpload"
				/>
			</div>
			<div class="parent-G1U6ilxo1e">
				<Input
					name="new-series-area"
					:max-length="300"
					v-model="newSeries.name"
					class="child-4kK6OY-cJl"
					placeholder="Please enter new series name"
				/>
				<button
					type="button"
					class="parent-NJjk3xxsJg"
					@click="desideToCreateSeries"
				>
					New
				</button>
			</div>
		</div>
	</div>
</template>
<style scoped>
	.parent-4JxPgYb9Jl {
		width: 100%;
	}

	.child-N1IGDObcye {
		width: 100%;
	}

	.parent-EJ5IqDbqkl {
		height: 40px;
		overflow-y: scroll;
		word-wrap: break-word;
		line-height: 20px;
		text-align: left;
		font-size: small;
		color: grey;
	}

	.parent-EyA9lY-5Jx {
		max-height: 420px;
		overflow-y: scroll;
	}

	.child-EJRV0dWq1e {
		height: 70px;
		display: flex;
		align-items: center;
		cursor: pointer;
	}

	.child-EJRV0dWq1e:hover {
		background-color: aliceblue;
	}

	.parent-4kGYZF-qJl {
		height: 50px;
		min-width: 50px;
		background-color: grey;
	}

	.parent-41Bi-YbqJe {
		height: 50px;
		padding-left: 10px;
		text-align: left;
		font-size: small;
	}

	.parent-4y1uGqZ91g {
		height: 100px;
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 10px;
	}

	.parent-EyjVKFb9Jl {
		position: absolute;
		top: 0;
		right: 0;
		padding: 5px;
		cursor: pointer;
	}

	.parent-EyjVKFb9Jl:hover {
		background-color: aliceblue;
	}

	.parent-EyOtKYZcyl {
		cursor: pointer;
	}

	.parent-4yg-t9W5kx {
		max-width: 100%;
		max-height: 100%;
	}
	.parent-G1U6ilxo1e {
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 10px;
	}
	.parent-NJjk3xxsJg {
		max-width: fit-content;
		height: fit-content;
		border: none;
		outline: none;
		cursor: pointer;
		background-color: #ffa33c;
		color: white;
		font-size: small;
		border-radius: 5px;
		padding: 5px 3px;
		margin-left: 10px;
	}
</style>
