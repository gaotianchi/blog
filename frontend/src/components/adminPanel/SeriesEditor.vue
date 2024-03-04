<script setup lang="ts">
	import { reactive, ref, watch, type Ref } from "vue";
	import icons from "@/components/icons";
	import type { PreviewCover, Series } from "@/typing";
	import InputA from "@/components/InputA.vue";
	import { getRenamedFile, getPreviewUrl } from "@/utlis";
	const props = defineProps<{
		series: Series;
	}>();
	const emits = defineEmits<{
		updateSeries: [series: Series, coverImg: File | null];
	}>();
	const newSeries: Series = reactive({
		id: props.series.id,
		name: props.series.name,
		cover: props.series.cover.split("/").pop() ?? "",
		authorId: props.series.authorId,
		createdAt: props.series.createdAt,
	});
	const previewCover: PreviewCover = reactive({
		url: props.series.cover,
		file: null,
	});
	const uploadImageArea: Ref<HTMLInputElement | null> = ref(null);
	watch(newSeries, () => {
		emits("updateSeries", newSeries, previewCover.file);
	});
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
</script>
<template>
	<div class="parent-V1f9R1O3Jl child-N1IGDObcye">
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
		</div>
	</div>
</template>
<style src="@/assets/series-editor.css"></style>
