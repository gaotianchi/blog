<script setup lang="ts">
	import {
		reactive,
		ref,
		type Ref,
		onMounted,
		watchEffect,
		watch,
	} from "vue";
	import {
		getSeries,
		getSeriesItem,
		createSeriesItem,
		limString,
		createMediaItem,
	} from "@/api";
	import Radio from "./Radio.vue";
	import Input from "./Input.vue";
	import icons from "@/components/icons";
	import { getUnixTime } from "date-fns";
	import type { ConfirmProp, MessageProp, Series } from "@/typing";
	type Action = "default" | "select" | "new";
	type PreviewCover = {
		url: string;
		file: File | null;
	};
	const props = defineProps<{
		seriesId: number;
	}>();
	const emits = defineEmits<{
		updateSeries: [series: Series];
	}>();
	const action: Ref<Action> = ref("default");
	const allSeries: Series[] = reactive([]);
	const defaultSeries = {
		id: 0,
		name: "",
		author_id: 0,
		cover: "",
	};
	const newSeries: Series = reactive({ ...defaultSeries });
	const originalSeries: Series = reactive({ ...defaultSeries });
	const currentSeries: Series = reactive({ ...defaultSeries });
	const uploadImageArea: Ref<HTMLInputElement | null> = ref(null);
	const previewCover: PreviewCover = reactive({
		url: "",
		file: null,
	});
	const messageProp: MessageProp = reactive({
		active: false,
		message: "",
	});
	const defaultConfirmProp: ConfirmProp = {
		active: false,
		header: undefined,
		body: "",
		yesMessage: undefined,
		noMessage: undefined,
		callback: () => {},
	};
	const confirmProp: ConfirmProp = reactive({ ...defaultConfirmProp });

	watchEffect(() => {
		emits("updateSeries", currentSeries);
	});
	watch(messageProp, () => {
		if (messageProp.active) {
			setTimeout(() => {
				messageProp.active = false;
				messageProp.message = "";
			}, 1500);
		}
	});
	initOriginalSeriesItem();
	onMounted(() => {
		initAllSeries();
	});
	async function initOriginalSeriesItem(): Promise<void> {
		if (!props.seriesId) {
			console.log("No original found.");
			return;
		}
		try {
			const originalSeriesItem = await getSeriesItem(props.seriesId);
			Object.assign(originalSeries, originalSeriesItem);
			Object.assign(currentSeries, originalSeriesItem);
		} catch (error) {
			console.log(error);
		}
	}
	async function initAllSeries(): Promise<void> {
		try {
			const allSeriesData = await getSeries();
			Object.assign(allSeries, allSeriesData);
			sessionStorage.setItem("allSeries", JSON.stringify(allSeries));
		} catch (error) {
			console.error(error);
		}
	}
	function getAllSeries(): Series[] {
		console.log("Get all series from local.");
		const allOldSeridsData = sessionStorage.getItem("allSeries");
		const allOldSeries: Series[] = JSON.parse(allOldSeridsData || "[]");
		return allOldSeries;
	}
	function updateAllLocalSeries(newSeries: Series): void {
		const allOldSeries = getAllSeries();
		allOldSeries.push(newSeries);
		sessionStorage.setItem("allSeries", JSON.stringify(allOldSeries));
	}
	function loadAllSeries(): void {
		Object.assign(currentSeries, originalSeries);
		Object.assign(allSeries, getAllSeries());
	}
	async function createSeries(): Promise<void> {
		messageProp.active = true;
		messageProp.message = "Creating new series ...";
		if (previewCover.file) {
			try {
				await createMediaItem(previewCover.file);
			} catch (error) {
				console.error(error);
			}
		}
		try {
			const newSeriesData = await createSeriesItem(newSeries);
			Object.assign(currentSeries, newSeriesData);
			updateAllLocalSeries(newSeriesData);
			messageProp.active = true;
			messageProp.message = "Successfully create series.";
		} catch (error) {
			console.error(error);
		}
		resetCover();
		Object.assign(newSeries, defaultSeries);
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
	function getRenamedFile(originalFile: File): File {
		const nameArr = originalFile.name.split(".");
		const newFilename =
			"image-" +
			getUnixTime(new Date()) +
			"." +
			nameArr[nameArr.length - 1];
		const renameFile = new File([originalFile], newFilename, {
			type: originalFile.type,
		});
		return renameFile;
	}
	function resetCover(): void {
		previewCover.url = "";
		newSeries.cover = "";
		if (uploadImageArea.value) {
			uploadImageArea.value.value = "";
		}
	}
	function resetConfirmProp(): void {
		Object.assign(confirmProp, { ...defaultConfirmProp });
	}
	function desideToCreateSeries(): void {
		if (!newSeries.name) {
			alert("Series name cannot be empty!");
			return;
		}
		resetConfirmProp();
		Object.assign(confirmProp, {
			active: true,
			header: "Create series",
			body: `Are you sure you want to create series 《${newSeries.name}》?`,
			yesMessage: "Create",
			noMessage: "Cancel",
			callback: createSeries,
		});
	}
</script>
<template>
	<MessageProp :message-prop="messageProp" />
	<ConfirmProp
		:confirm-prop="confirmProp"
		@confirm="
			(decision: boolean) => {
				confirmProp.active = false;
			}
		"
	/>
	<div class="parent-4JxPgYb9Jl">
		<div class="parent-EJ5IqDbqkl child-N1IGDObcye">
			{{
				currentSeries.name
					? limString(currentSeries.name, 90)
					: "No series selected."
			}}
		</div>
		<div class="parent- child-N1IGDObcye">
			<Radio
				name="default"
				value="default"
				v-model="action"
				@selected="Object.assign(currentSeries, originalSeries)"
				>Default</Radio
			>
			<Radio
				name="select"
				value="select"
				v-model="action"
				@selected="loadAllSeries"
				>Select another</Radio
			>
			<Radio
				name="new"
				value="new"
				v-model="action"
				@selected="Object.assign(currentSeries, originalSeries)"
				>New</Radio
			>
		</div>
		<div
			class="parent-EyA9lY-5Jx child-N1IGDObcye"
			v-if="action === 'select'"
		>
			<div
				class="parent- child-EJRV0dWq1e"
				v-for="series in allSeries"
				v-if="allSeries.length > 0"
				@click="Object.assign(currentSeries, series)"
			>
				<div class="parent-4kGYZF-qJl child-4yb5kK-9yx">
					<img :src="series.cover" v-if="series.cover" />
				</div>
				<div class="parent-41Bi-YbqJe child-4yb5kK-9yx">
					{{ limString(series.name, 100) }}
				</div>
			</div>
			<div class="child-EJRV0dWq1e" v-else>No series found.</div>
		</div>
		<div class="parent- child-N1IGDObcye" v-if="action === 'new'">
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
