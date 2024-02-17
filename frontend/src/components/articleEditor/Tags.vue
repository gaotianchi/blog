<script setup lang="ts">
	import { onMounted, ref, type Ref, computed, watch } from "vue";
	import { Index } from "flexsearch";
	import type { Tag } from "@/typing";
	import { getAllRemoteTags } from "@/api/remote";
	import { localArticle } from "@/api/local";
	import InputA from "@/components/InputA.vue";
	const model: Ref<string> = ref(localArticle.tags.join(","));
	const allTags: Ref<Tag[]> = ref([]);
	const index = new Index({ tokenize: "forward" });
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
			const searchResult = index.search(searchText.value, { limit: 10 });
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
		localArticle.tags = currentTags.value;
	});
	async function initTagSearchEngine(): Promise<void> {
		allTags.value = await getAllRemoteTags();
		allTags.value.forEach((tag) => {
			index.add(tag.id, tag.name);
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

<style scoped>
	.parent-VyJIlVgcyg {
		width: 100%;
	}

	.parent-VJtrsOlqye {
		width: 100%;
		line-height: 20px;
		padding: 0 5px;
		border: none;
		border-bottom: var(--second-color) solid 1px;
	}

	.parent-NJcl-4gqyg {
		width: 100%;
		height: 150px;
		padding-left: 20px;
		overflow-y: auto;
	}

	.child-VysQ-Vx9kg {
		height: 25px;
		text-align: left;
		padding: 2px 0 2px 2px;
		font-size: medium;
		cursor: pointer;
		font-weight: 600;
		border-bottom: gainsboro solid 1px;
	}

	.child-VysQ-Vx9kg:hover {
		background-color: gainsboro;
	}
</style>
