<script setup lang="ts">
	import { inject, onMounted, reactive, ref, type Ref } from "vue";
	import type { PreviewCover, Series } from "@/typing";
	import { defaultPreviewCover, defaultSeries } from "@/defaults";
	import { getRenamedFile, getPreviewUrl } from "@/utlis";
	import { propConfirm, propMessage } from "@/api/local";
	import { getAllSeries, postMediaItem, postSeriesItem } from "@/api/remote";
	import Radio from "@/components/Radio.vue";
	import InputA from "@/components/InputA.vue";
	import icons from "@/components/icons";
	import {
		editorLocalAndRemote,
		articleSerieLocalAndRemote,
		allRemoteSeries,
		settingStatus,
	} from "@/store";
	const articleId = inject("articleId") as number;
	const uploadImageArea: Ref<HTMLInputElement | null> = ref(null);
	const previewCover: PreviewCover = reactive({ ...defaultPreviewCover });
	const newSeries: Series = reactive({ ...defaultSeries });
	onMounted(() => {
		loadAllSeries();
	});
	async function loadAllSeries(): Promise<void> {
		if (allRemoteSeries.value.length > 0) {
			console.log("Load remote series from local.");
			return;
		}
		try {
			const seriesData = await getAllSeries();
			allRemoteSeries.value = seriesData;
		} catch (error) {
			console.error(error);
		}
	}
	function selectSeries(s: Series): void {
		editorLocalAndRemote[articleId].local.seriesId = s.id;
		Object.assign(articleSerieLocalAndRemote[articleId].local, s);
	}
	function triggerFileInput(): void {
		uploadImageArea?.value?.click();
	}
	async function handleFileUpload(e: Event): Promise<void> {
		const selectedFile = (e.target as HTMLInputElement).files?.[0];
		if (selectedFile) {
			const renamedFile = getRenamedFile(selectedFile);
			previewCover.file = renamedFile;
			previewCover.url = await getPreviewUrl(renamedFile);
			newSeries.cover = renamedFile.name;
		}
	}
	function resetCover(): void {
		previewCover.url = "";
		newSeries.cover = "";
		if (uploadImageArea.value) {
			uploadImageArea.value.value = "";
		}
	}
	async function createSeries(): Promise<void> {
		propMessage("Creating series ...");
		if (previewCover.file) {
			try {
				await postMediaItem(previewCover.file);
			} catch (error) {
				console.error(error);
			}
		}
		try {
			const seriesData = await postSeriesItem(newSeries);
			Object.assign(
				articleSerieLocalAndRemote[articleId].local,
				seriesData
			);
			editorLocalAndRemote[articleId].local.seriesId = seriesData.id;
			allRemoteSeries.value.push(seriesData);
			propMessage("Successfully create new series.");
		} catch (error) {
		} finally {
			resetCover();
			Object.assign(newSeries, defaultSeries);
		}
	}
	function desideToCreateSeries(): void {
		if (!newSeries.name) {
			alert("Series name cannot be empty!");
			return;
		}
		propConfirm({
			header: "Create series",
			body: `Are you sure you want to create series 《${newSeries.name}》?`,
			yesMessage: "Create",
			noMessage: "Cancel",
			callback: createSeries,
		});
	}
	function resetSeries(): void {
		Object.assign(editorLocalAndRemote[articleId].local.seriesId, {
			...defaultSeries,
		});
		Object.assign(articleSerieLocalAndRemote[articleId].local, {
			...defaultSeries,
		});
	}
</script>
<template>
	<div class="parent-4JxPgYb9Jl">
		<div class="parent-EJ5IqDbqkl child-N1IGDObcye">
			{{
				articleSerieLocalAndRemote[articleId].local.name.length > 0
					? articleSerieLocalAndRemote[articleId].local.name
					: "No series selected."
			}}
			<component
				:is="icons.cancel"
				class="icon small parent-VJYBnOR31g"
				@click="resetSeries"
			/>
		</div>
		<div class="parent- child-N1IGDObcye">
			<Radio
				name="default-series"
				value="default"
				v-model="settingStatus.series.mode"
				@selected="
					() => {
						editorLocalAndRemote[articleId].local.seriesId =
							editorLocalAndRemote[articleId].remote.seriesId;
					}
				"
				>Default</Radio
			>
			<Radio
				name="selected-series"
				value="selected"
				v-model="settingStatus.series.mode"
				@selected="
					() => {
						editorLocalAndRemote[articleId].local.seriesId =
							editorLocalAndRemote[articleId].remote.seriesId;
					}
				"
				>Select another</Radio
			>
			<Radio
				name="new-series"
				value="new"
				v-model="settingStatus.series.mode"
				@selected="
					() => {
						editorLocalAndRemote[articleId].local.seriesId =
							editorLocalAndRemote[articleId].remote.seriesId;
					}
				"
				>New</Radio
			>
		</div>
		<div
			class="parent-EyA9lY-5Jx child-N1IGDObcye"
			v-if="settingStatus.series.mode === 'selected'"
		>
			<div
				class="parent- child-EJRV0dWq1e"
				v-for="s in allRemoteSeries"
				v-if="allRemoteSeries.length > 0"
				@click="selectSeries(s)"
			>
				<div class="parent-4kGYZF-qJl child-4yb5kK-9yx">
					<img
						class="child-V1EH5nisJl"
						:src="s.cover"
						v-if="s.cover"
					/>
				</div>
				<div class="parent-41Bi-YbqJe child-4yb5kK-9yx">
					{{ s.name }}
				</div>
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
				<InputA
					name="new-series-area"
					:max-length="300"
					v-model="newSeries.name"
					class="child-4kK6OY-cJl"
					placeholder="Please enter new series name"
					auto-focus
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
<style src="@/assets/series-editor.css"></style>
