<script setup lang="ts">
	import icons from "@/components/icons";
	import { computed, reactive, ref } from "vue";
	import { useRouter } from "vue-router";
	import {
		allRemoteArticleCards,
		patchArticleCardItem,
		deleteArticleItem,
	} from "@/api/remote";
	import { propConfirm, propMessage, articleCardIndex } from "@/api/local";
	import type { SerializedArticleCard } from "@/typing";
	import { getLocalDatetime, dateFormatter } from "@/utlis";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import TagEditor from "./TagEditor.vue";
	const props = defineProps<{
		articleIndex: number;
	}>();
	const status = reactive({
		tags: false,
		card: false,
	});
	const router = useRouter();
	const articles = reactive(allRemoteArticleCards);
	const localTags = ref(articles[props.articleIndex].tags);
	const currentPubishStatus = computed<string>(() => {
		if (
			articles[props.articleIndex].planned &&
			articles[props.articleIndex].isPublished
		) {
			return "Planned";
		} else if (articles[props.articleIndex].isPublished) {
			return "Published";
		} else {
			return "Draft";
		}
	});
	function toEditPage(): void {
		router.push({
			name: "EditArticle",
			params: {
				articleId: articles[props.articleIndex].id,
			},
		});
	}
	function decideToPublishArticle(): void {
		propConfirm({
			header: "Publish Article",
			body: `Are you sure you want to pulish article 《${
				articles[props.articleIndex].title
			}》`,
			yesMessage: "Publish",
			noMessage: "Cancel",
			callback: publishArticleItem,
		});
	}
	function decideToDeleteArticle(): void {
		propConfirm({
			header: "Delete Article",
			body: `Are you sure you want to delete article 《${
				articles[props.articleIndex].title
			}》`,
			yesMessage: "Delete",
			noMessage: "Cancel",
			callback: deleteArticle,
		});
	}
	function deleteArticle(): void {
		try {
			propMessage("Deleting article ...");
			deleteArticleItem(articles[props.articleIndex].id).then(() => {
				propMessage("Article deleted successfully.");
				articles.splice(props.articleIndex, 1);
			});
			articleCardIndex.remove(articles[props.articleIndex].id);
		} catch (error) {
			console.error(error);
			propMessage("Please try again.");
		}
	}
	async function updateArticleItem(
		serializedArticleCard: SerializedArticleCard
	): Promise<void> {
		try {
			const response = await patchArticleCardItem(
				articles[props.articleIndex].id,
				serializedArticleCard
			);
			articleCardIndex.update(response);
			articles[props.articleIndex] = response;
			propMessage("Changed saved.");
		} catch (error) {
			console.error(error);
		}
	}
	function publishArticleItem(): void {
		try {
			propMessage("Publishing article...");
			updateArticleItem({
				isPublished: true,
			});
		} catch (error) {
			propMessage("Please try again.");
		}
	}
	function convertToDraft(): void {
		try {
			propMessage("Saving article as draft ...");
			updateArticleItem({
				isPublished: false,
			});
		} catch (error) {
			propMessage("Please try again.");
		}
	}
	function updateTags(): void {
		status.tags = false;
		try {
			updateArticleItem({
				tags: localTags.value,
			});
		} catch (error) {
			propMessage("Please try again.");
		}
	}
</script>
<template>
	<ConfirmSlot
		:status="status.tags"
		:callback="updateTags"
		@close="status.tags = false"
	>
		<template #header>Editor tags</template>
		<template #body>
			<TagEditor
				:article-index="props.articleIndex"
				@getLocalTags="
					(tags) => {
						localTags = tags;
					}
				"
			/>
		</template>
		<template #noMessage>Cancel</template>
		<template #yesMessage>Apply</template>
	</ConfirmSlot>
	<div
		class="parent-NJQTAg2j1g"
		@mouseenter="status.card = true"
		@mouseleave="status.card = false"
	>
		<div class="parent-Vy7GxWhi1x" @click="toEditPage">
			<slot name="cover"></slot>
		</div>
		<div class="parent-EJbXxWnjke">
			<div class="parent-V1C7xb3syx">
				<div class="parent-Ek3X-WnsJx" @click="toEditPage">
					<slot name="title">
						Iriure nonummy vero sea facilisi feugiat takimata.
					</slot>
				</div>
				<div class="parent-4ke4Z-hjyl">
					<div class="parent-NJaMdZ43Jl" v-if="!status.card">
						{{ articles[props.articleIndex].author }}
					</div>
					<div class="parent-4JePW-2ike" v-if="status.card">
						<div
							class="child-41BOZ-2sJx"
							v-if="
								articles[props.articleIndex].isPublished ===
								true
							"
							@click="convertToDraft"
						>
							<component
								:is="icons.draft"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div
							class="child-41BOZ-2sJx"
							v-if="
								articles[props.articleIndex].isPublished ===
								false
							"
							@click="decideToPublishArticle"
						>
							<component
								:is="icons.publish"
								class="icon child-NkE1rZnskl black"
							/>
						</div>
						<div
							class="child-41BOZ-2sJx"
							@click="decideToDeleteArticle"
						>
							<component
								:is="icons.delete"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div
							class="child-41BOZ-2sJx"
							@click="status.tags = true"
						>
							<component
								:is="icons.tags"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div class="child-41BOZ-2sJx">
							<component
								:is="icons.view"
								class="icon child-NkE1rZnskl"
							/>
						</div>
					</div>
					<div class="child-41BOZ-2sJx">
						<component
							:is="icons.author"
							class="icon child-NkE1rZnskl"
						/>
					</div>
				</div>
			</div>
			<div class="parent-N1GNlb3oke">
				<div class="parent-Nkzrbbnike">
					<div
						class="parent-N1rwLZ3jyl"
						:class="{
							published: articles[props.articleIndex].isPublished,
							planned: articles[props.articleIndex].planned,
						}"
					>
						{{ currentPubishStatus }}
					</div>
					<div class="parent-VJ3zvZ2ske">·</div>
					<div class="parent-Vk5DLW2jJl">
						{{
							dateFormatter(
								getLocalDatetime(
									articles[props.articleIndex].createdAt
								),
								"ddd MMM DD YYYY"
							)
						}}
					</div>
					<div
						class="parent-4J0P8-hsJl"
						v-if="articles[props.articleIndex].tags.length > 0"
					>
						<div
							class="child-EyLCwWno1l"
							v-for="tag in articles[props.articleIndex].tags"
						>
							<span class="child-Ek0bDMhsJe">
								{{ tag }}
							</span>
						</div>
					</div>
				</div>
				<div class="parent-41hBWb2ikx"></div>
			</div>
		</div>
	</div>
</template>
<style src="@/assets/article-card.css"></style>
