<script setup lang="ts">
	import "md-editor-v3/lib/style.css";
	import { MdEditor } from "md-editor-v3";
	import { inject } from "vue";
	import { postMediaItem } from "@/api/remote";
	import { getRenamedFile } from "@/utlis";
	import { editorLocalAndRemote } from "@/store";
	const articleId = inject("articleId") as number;
	async function onUploadImg(
		files: File[],
		callback: CallableFunction
	): Promise<void> {
		const urls: string[] = [];
		files.forEach(async (file) => {
			try {
				const renamedFile = getRenamedFile(file);
				const response = await postMediaItem(renamedFile);
				urls.push(response.url);
			} catch (error) {
				console.error(error);
			}
		});
		console.log(urls);
		callback(urls);
	}
</script>
<template>
	<MdEditor
		v-model="editorLocalAndRemote[articleId].local.body"
		class="parent-4k6nVctskl"
		@onUploadImg="onUploadImg"
		:toolbarsExclude="['github', 'save']"
	/>
</template>
