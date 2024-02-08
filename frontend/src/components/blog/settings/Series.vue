<script setup lang="ts">
	import { reactive, ref, type Ref, computed, watch } from "vue";
	import { getSeries } from "@/api";
	import Radio from "./Radio.vue";
	import Input from "./Input.vue";
	import icons from "@/components/icons";
	import type { APIError } from "@/api/errors";
	import { getUnixTime } from "date-fns";
	import { create } from "flexsearch";
	type Series = {
		id: number;
		name: string;
		author_id: number;
		cover: string;
	};
	type Action = "default" | "select" | "new";

	const props = defineProps<{
		series: Series;
	}>();
	const emits = defineEmits<{
		updateSeries: [series: Series];
	}>();
	const action: Ref<Action> = ref("default");
	const defaultSeries: Series = props.series;
	const selectedSeries: Series = reactive({
		id: 0,
		name: "",
		author_id: 0,
		cover: "",
	});
	const newSeries: Series = reactive({
		id: 0,
		name: "",
		author_id: 0,
		cover: "",
	});
	const previewUrl: Ref<string> = ref("");
	const currentSeries = computed<Series>(() => {
		let result: Series = defaultSeries;
		if (action.value === "select") {
			if (selectedSeries.name) {
				result = selectedSeries;
			}
		}
		if (action.value === "new") {
			if (newSeries.name) {
				result = newSeries;
			}
		}
		emits("updateSeries", currentSeries.value);
		return result;
	});
	const oldSerieses = getSeries();
	async function createSeries(): Promise<void> {
		if (newSeries.id) {
			console.log("Series has been created.");
			return;
		}
		const url = "http://localhost:5000/v1/author/series";
		const accessToken = localStorage.getItem("access_token");
		const response = await fetch(url, {
			method: "POST",
			headers: { Authorization: "Bearer " + accessToken },
		});
		if (response.status === 201) {
			const responseData = await response.json();
			console.log(responseData);
			Object.assign(newSeries, responseData);
		} else {
			const errorData = await response.json();
			throw errorData.error;
		}
	}
	function limString(str: string, maxLength: number): string {
		if (str.length < maxLength) {
			return str;
		} else {
			return str.slice(0, maxLength) + " ...";
		}
	}
	const uploadImageArea: Ref<HTMLInputElement | null> = ref(null);
	function triggerFileInput(): void {
		uploadImageArea?.value?.click();
	}
	function handleFileUpload(e: Event): void {
		const selectedFile = (e.target as HTMLInputElement).files?.[0];
		console.log(selectedFile);
		processImage(selectedFile);
	}
	async function uploadImage(file: File): Promise<void> {
		const url = "http://localhost:5000/v1/media/uploads";
		const formData = new FormData();
		formData.append("file", file);
		fetch(url, {
			method: "POST",
			body: formData,
		})
			.then((response) => {
				if (response.status === 201) {
					console.log("Success.");
				} else {
					return response.json();
				}
			})
			.then((response) => {
				if (response) {
					const error = response.error as APIError;
					console.log(error.displayMessage);
				}
			});
	}
	function processImage(file: File | undefined): void {
		if (file) {
			const reader = new FileReader();
			reader.onload = () => {
				previewUrl.value = reader.result as string;
			};
			reader.readAsDataURL(file);
			const nameArr = file.name.split(".");
			const newFilename =
				"image-" +
				getUnixTime(new Date()) +
				"." +
				nameArr[nameArr.length - 1];
			const renameFile = new File([file], newFilename, {
				type: file.type,
			});
			newSeries.cover = newFilename;
			uploadImage(renameFile);
		}
	}
	function resetCover(): void {
		previewUrl.value = "";
		newSeries.cover = "";
		if (uploadImageArea.value) {
			uploadImageArea.value.value = "";
		}
	}
	function selectSeries(title: string, cover: string): void {
		selectedSeries.name = title;
		selectedSeries.cover = cover;
	}
</script>
<template>
	<div class="parent-4JxPgYb9Jl">
		<div class="parent-EJ5IqDbqkl child-N1IGDObcye">
			{{
				currentSeries.name
					? limString(currentSeries.name, 90)
					: "No series selected."
			}}
		</div>
		<div class="parent- child-N1IGDObcye">
			<Radio name="default" value="default" v-model="action"
				>Default</Radio
			>
			<Radio name="select" value="select" v-model="action"
				>Select another</Radio
			>
			<Radio name="new" value="new" v-model="action" @click="createSeries"
				>New</Radio
			>
		</div>
		<div
			class="parent-EyA9lY-5Jx child-N1IGDObcye"
			v-if="action === 'select'"
		>
			<div
				class="parent- child-EJRV0dWq1e"
				v-for="series in oldSerieses"
				@click="selectSeries(series.title, series.cover)"
			>
				<div class="parent-4kGYZF-qJl child-4yb5kK-9yx">
					<img :src="series.cover" :alt="series.title" />
				</div>
				<div class="parent-41Bi-YbqJe child-4yb5kK-9yx">
					{{ limString(series.title, 100) }}
				</div>
			</div>
		</div>
		<div class="parent- child-N1IGDObcye" v-if="action === 'new'">
			<div class="parent-4y1uGqZ91g child-4kK6OY-cJl">
				<component
					v-if="previewUrl"
					:is="icons.cancel"
					class="parent-EyjVKFb9Jl icon"
					@click="resetCover"
				/>
				<component
					v-if="!previewUrl"
					:is="icons.uploadImage"
					class="parent-EyOtKYZcyl child-VkfBtF-cyg"
					@click="triggerFileInput"
				/>
				<img
					class="parent-4yg-t9W5kx"
					v-if="previewUrl"
					:src="previewUrl"
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
			<Input
				name="new-series-area"
				:max-length="300"
				v-model="newSeries.name"
				class="child-4kK6OY-cJl"
			/>
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
</style>
