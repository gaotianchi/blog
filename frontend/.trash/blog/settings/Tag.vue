<script setup lang="ts">
	import {
		type Ref,
		ref,
		computed,
		onMounted,
		watchEffect,
		reactive,
	} from "vue";
	import { getTags } from "@/api";
	import { Index } from "flexsearch";
	import type { Article, Tag } from "@/typing";
	const emits = defineEmits<{
		updateTags: [tags: string[]];
	}>();
	const props = defineProps<{
		tags: string[];
	}>();
	const model: Ref<string> = ref(props.tags.sort().join(","));
	const typedTags = computed<string[]>(() => {
		const tagArr = model.value?.split(",").map((i) => i.trim()) || [];
		return tagArr.slice(0, tagArr.length - 1);
	});
	const searchText = computed<string>(() => {
		const tagArr = model.value?.split(",").map((i) => i.trim()) || [];
		return tagArr[tagArr.length - 1] || "";
	});
	const tagInput: Ref<HTMLElement | null> = ref(null);
	const allTags: Tag[] = reactive([]);
	const index = new Index({ tokenize: "forward" });
	allTags.forEach((i) => {
		index.add(i.id, i.name);
	});

	const suggestedTags = computed(() => {
		if (searchText.value.length === 0) {
			const result = allTags.filter(
				(tag) => !typedTags.value.includes(tag.name)
			);
			return result.slice(0, 10);
		} else {
			const searchResult = index.search(searchText.value, { limit: 10 });
			const tagsItems = allTags
				.filter((tag) => searchResult.includes(tag.id))
				.filter((i) => !typedTags.value.includes(i.name));
			return tagsItems;
		}
	});
	onMounted(() => {
		tagInput.value?.focus();
		if (model.value.trim()) {
			model.value = model.value + ",";
		}
		initAllTags();
	});

	watchEffect(() => {
		const tagArr = model.value
			?.split(",")
			.map((i) => i.trim())
			.filter((i) => i.length > 0);
		emits("updateTags", [...new Set(tagArr)].sort());
	});
	async function initAllTags(): Promise<void> {
		try {
			const tagsData = await getTags();
			Object.assign(allTags, tagsData);
		} catch (error) {
			console.error(error);
		}
	}
	function loadRemoteTags(): void {
		const SessionRemoteArticle: Article = JSON.parse(
			sessionStorage.getItem("remoteArticle") as string
		);
	}
	function addTag(newTag: string): void {
		typedTags.value.push(newTag);
		model.value = typedTags.value.join(",") + ",";
		tagInput.value?.focus();
	}
</script>
<template>
	<div class="parent-VyJIlVgcyg">
		<input
			class="child-41pk-Ex9Je parent-VJtrsOlqye"
			type="text"
			name="tag"
			id="tag-editor"
			v-model.trim="model"
			ref="tagInput"
			placeholder="Please enter article tags."
		/>
		<div class="child-41pk-Ex9Je parent-NJcl-4gqyg">
			<div
				class="child-VysQ-Vx9kg"
				v-for="tag in suggestedTags"
				v-if="suggestedTags"
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
		overflow-y: scroll;
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
