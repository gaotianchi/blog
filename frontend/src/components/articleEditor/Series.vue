<script setup lang="ts">
	import { onMounted, reactive, ref, type Ref } from "vue";
	import type { PreviewCover, Series } from "@/typing";
	import { defaultPreviewCover, defaultSeries } from "@/defaults";
	import { dateFormatter } from "@/utlis";
	import {
		localArticle,
		localSeries,
		settingStatus,
		propConfirm,
		propMessage,
	} from "@/api/local";
	import {
		remoteArticle,
		getAllRemoteSeriesItem,
		allRemoteSeries,
		postMediaItem,
		postSeriesItem,
	} from "@/api/remote";
	import Radio from "@/components/Radio.vue";
	import InputA from "@/components/InputA.vue";
	import icons from "@/components/icons";
	import Message from "@/components/Message.vue";
	import Confirm from "@/components/Confirm.vue";
	const previewCover: PreviewCover = reactive({ ...defaultPreviewCover });
	const uploadImageArea: Ref<HTMLInputElement | null> = ref(null);
	const newSeries: Series = reactive({ ...defaultSeries });
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
	function selectSeries(s: Series): void {
		localArticle.seriesId = s.id;
		Object.assign(localSeries, s);
	}
	function triggerFileInput(): void {
		uploadImageArea?.value?.click();
	}
	function getRenamedFile(originalFile: File): File {
		const nameArr = originalFile.name.split(".");
		const newFilename =
			"image-" +
			dateFormatter(new Date(), "YYYYMMDDhhmmss") +
			"." +
			nameArr[nameArr.length - 1];
		const renameFile = new File([originalFile], newFilename, {
			type: originalFile.type,
		});
		return renameFile;
	}
	function getPreviewUrl(file: File): Promise<string> {
		return new Promise((resolve) => {
			const reader = new FileReader();
			reader.onload = () => {
				const url = reader.result as string;
				resolve(url);
			};
			reader.readAsDataURL(file);
		});
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
			Object.assign(localSeries, seriesData);
			localArticle.seriesId = seriesData.id;
			allRemoteSeries.push(seriesData);
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
</script>
<template>
	<Message />
	<Confirm />
	<div class="parent-4JxPgYb9Jl">
		<div class="parent-EJ5IqDbqkl child-N1IGDObcye">
			{{
				localSeries.name.length > 0
					? localSeries.name
					: "No series selected."
			}}
		</div>
		<div class="parent- child-N1IGDObcye">
			<Radio
				name="default-series"
				value="default"
				v-model="settingStatus.series.mode"
				@selected="
					() => {
						localArticle.seriesId = remoteArticle.seriesId;
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
						localArticle.seriesId = remoteArticle.seriesId;
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
						localArticle.seriesId = remoteArticle.seriesId;
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
					<img :src="s.cover" v-if="s.cover" />
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
<style scoped>
	.parent-4JxPgYb9Jl {
		width: 100%;
	}

	.child-N1IGDObcye {
		width: 100%;
	}

	.parent-EJ5IqDbqkl {
		height: 40px;
		overflow-y: auto;
		word-wrap: break-word;
		line-height: 20px;
		text-align: left;
		font-size: small;
		color: grey;
	}

	.parent-EyA9lY-5Jx {
		max-height: 420px;
		overflow-y: auto;
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
