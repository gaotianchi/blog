<script setup lang="ts">
	import { onMounted, ref, type Ref, computed, watch, inject } from "vue";
	import type { Tag } from "@/typing";
	import { getAllRemoteTags } from "@/api/remote";
	import { tagIndex } from "@/store";
	import InputA from "@/components/InputA.vue";
	import { editorLocalAndRemote } from "@/store";
	const articleId = inject("articleId") as number;
	const model: Ref<string> = ref(
		editorLocalAndRemote[articleId].local.tags.join(",")
	);
	const allTags: Ref<Tag[]> = ref([]);
	const typedTags = computed<string[]>(() => {
		const tagArr = model.value?.split(",").map((i) => i.trim()) || [];
		return tagArr.slice(0, tagArr.length - 1);
	});
	const searchText = computed<string>(() => {
		const tagArr = model.value?.split(",").map((i) => i.trim()) || [];
		return tagArr[tagArr.length - 1] || "";
	});
	const suggestedTags = computed(() => {
		if (searchText.value.length === 0) {
			const result = allTags.value.filter(
				(tag) => !typedTags.value.includes(tag.name)
			);
			return result.slice(0, 10);
		} else {
			const searchResult = tagIndex.search(searchText.value, {
				limit: 10,
			});
			const tagsItems = allTags.value
				.filter((tag) => searchResult.includes(tag.id))
				.filter((i) => !typedTags.value.includes(i.name));
			return tagsItems;
		}
	});
	const currentTags = computed<string[]>(() => {
		return model.value
			?.split(",")
			.map((i) => i.trim())
			.filter((i) => i.length > 0);
	});
	onMounted(() => {
		initTagSearchEngine();
		if (model.value.trim()) {
			model.value = model.value + ",";
		}
	});
	watch(currentTags, () => {
		editorLocalAndRemote[articleId].local.tags = currentTags.value;
	});
	async function initTagSearchEngine(): Promise<void> {
		allTags.value = await getAllRemoteTags();
		allTags.value.forEach((tag) => {
			tagIndex.add(tag.id, tag.name);
		});
	}
	function addTag(newTag: string): void {
		typedTags.value.push(newTag);
		model.value = typedTags.value.join(",") + ",";
	}
</script>
<template>
	<div class="parent-VyJIlVgcyg">
		<InputA
			name="tags"
			placeholder="Please enter article tags."
			:autoFocus="true"
			:maxLength="50"
			v-model.trim="model"
		/>
		<div class="child-41pk-Ex9Je parent-NJcl-4gqyg">
			<div
				class="child-VysQ-Vx9kg"
				v-for="tag in suggestedTags"
				v-if="suggestedTags.length > 0"
				@click="addTag(tag.name)"
			>
				{{ tag.name }}
			</div>
			<div v-else class="child-VysQ-Vx9kg">No suggested tags.</div>
		</div>
	</div>
</template>

<style src="@/assets/tags-editor.css"></style>
