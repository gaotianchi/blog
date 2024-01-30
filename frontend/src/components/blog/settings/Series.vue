<script setup lang="ts">
	import { computed, ref, type Ref } from "vue";
	import { getSeries } from "@/api";
	import type { Series } from "@/typing";
	import Radio from "./Radio.vue";
	import Input from "./Input.vue";
	import icons from "@/components/icons";
	import { getUnixTime } from "date-fns";
	const props = defineProps<{
		oldSeries?: Series;
	}>();
	const emits = defineEmits<{
		updateSereis: [series: Series | null];
	}>();
	const action: Ref<"default" | "select" | "new"> = ref("default");
	const selectedSeries: Ref<Series | null> = ref(null);
	const newSeries = computed<Series | null>(() => {
		if (newSeriesTitle.value) {
			if (previewUrl.value) {
				const rootUrl = "https://gaotianchi.com/";
				const coverName =
					"series-cover-" + getUnixTime(new Date()).toString();
				const coverUrl = rootUrl + coverName;
				const newSeries: Series = {
					cover: coverUrl,
					title: newSeriesTitle.value,
				};
				return newSeries;
			} else {
				return {
					title: newSeriesTitle.value,
				};
			}
		} else {
			return null;
		}
	});
	const newSeriesTitle: Ref<string> = ref("");
	const previewUrl: Ref<string> = ref("");
	const currentSeries = computed<Series | null>(() => {
		switch (action.value) {
			case "new":
				emits("updateSereis", newSeries.value);
				return newSeries.value;
			case "select":
				emits("updateSereis", selectedSeries.value);
				return selectedSeries.value;
			default:
				emits("updateSereis", props.oldSeries || null);
				return props.oldSeries || null;
		}
	});
	const oldSerieses = getSeries();
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
	function processImage(image: File | undefined): void {
		if (image) {
			const reader = new FileReader();
			reader.onload = () => {
				previewUrl.value = reader.result as string;
			};
			reader.readAsDataURL(image);
		}

		console.log("upload image here.")
	}
	function resetCover(): void {
		previewUrl.value = "";
		if (uploadImageArea.value) {
			uploadImageArea.value.value = "";
		}
	}
</script>
<template>
	<div class="parent-4JxPgYb9Jl">
		<div class="parent-EJ5IqDbqkl child-N1IGDObcye">
			{{ currentSeries ? limString(currentSeries?.title, 90) : "No series selected." }}
		</div>
		<div class="parent- child-N1IGDObcye">
			<Radio name="default" value="default" v-model="action"
				>Default</Radio
			>
			<Radio name="select" value="select" v-model="action"
				>Select another</Radio
			>
			<Radio name="new" value="new" v-model="action">New</Radio>
		</div>
		<div
			class="parent-EyA9lY-5Jx child-N1IGDObcye"
			v-if="action === 'select'"
		>
			<div
				class="parent- child-EJRV0dWq1e"
				v-for="series in oldSerieses"
				@click="selectedSeries = series"
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
				v-model="newSeriesTitle"
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
