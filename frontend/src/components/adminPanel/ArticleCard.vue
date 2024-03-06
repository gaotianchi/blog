<script setup lang="ts">
	import icons from "@/components/icons";
	import { computed, reactive, ref } from "vue";
	import { useRouter } from "vue-router";
	import { patchArticleCardItem, deleteArticleItem } from "@/api/remote";
	import { propConfirm, propMessage } from "@/api/local";
	import { allRemoteArticleCards, articleCardIndex } from "@/store";
	import type { ArticleCard, SerializedArticleCard } from "@/typing";
	import { getLocalDatetime, dateFormatter } from "@/utlis";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import TagEditor from "./TagEditor.vue";
	const props = defineProps<{
		articleCard: ArticleCard;
		sCardMode?: boolean;
	}>();
	const emits = defineEmits<{
		updateDraggingCard: [card: ArticleCard];
	}>();
	const status = reactive({
		tags: false,
		card: false,
		dragging: false,
	});
	const currentArtiticleCard = computed<ArticleCard>(() => {
		const index = allRemoteArticleCards.value.findIndex(
			(i) => i.id === props.articleCard.id
		);
		return allRemoteArticleCards.value[index];
	});
	const router = useRouter();
	const localTags = ref(props.articleCard.tags);
	const currentPubishStatus = computed<string>(() => {
		if (props.articleCard.planned && props.articleCard.isPublished) {
			return "Planned";
		} else if (props.articleCard.isPublished) {
			return "Published";
		} else {
			return "Draft";
		}
	});
	function toEditPage(): void {
		if (!props.sCardMode) {
			router.push({
				name: "EditArticle",
				params: {
					articleId: props.articleCard.id,
				},
			});
		}
	}
	function decideToPublishArticle(): void {
		propConfirm({
			header: "Publish Article",
			body: `Are you sure you want to pulish article 《${props.articleCard.title}》`,
			yesMessage: "Publish",
			noMessage: "Cancel",
			callback: publishArticleItem,
		});
	}
	function decideToDeleteArticle(): void {
		propConfirm({
			header: "Delete Article",
			body: `Are you sure you want to delete article 《${props.articleCard.title}》`,
			yesMessage: "Delete",
			noMessage: "Cancel",
			callback: deleteArticle,
		});
	}
	function deleteArticle(): void {
		try {
			propMessage("Deleting article ...");
			deleteArticleItem(props.articleCard.id).then(() => {
				propMessage("Article deleted successfully.");
				const index = allRemoteArticleCards.value.findIndex(
					(i) => i.id === props.articleCard.id
				);
				allRemoteArticleCards.value.splice(index, 1);
			});
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
				props.articleCard.id,
				serializedArticleCard
			);
			articleCardIndex.update(response);
			const index = allRemoteArticleCards.value.findIndex(
				(i) => i.id === props.articleCard.id
			);
			allRemoteArticleCards.value[index] = response;
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
	<div
		class="parent-NJQTAg2j1g"
		@mouseenter="status.card = true"
		@mouseleave="status.card = false"
		:draggable="props.sCardMode"
		@dragstart="status.dragging = true"
		@dragend="
			() => {
				status.dragging = false;
				emits('updateDraggingCard', currentArtiticleCard);
			}
		"
		@dragover=""
	>
		<ConfirmSlot
			:status="status.tags"
			:callback="updateTags"
			@close="status.tags = false"
		>
			<template #header>Editor tags</template>
			<template #body>
				<TagEditor
					:article-card="currentArtiticleCard"
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
			class="parent-Vy7GxWhi1x"
			@click="toEditPage"
			:class="{ scard: props.sCardMode }"
		>
			<slot name="cover"></slot>
		</div>
		<div class="parent-EJbXxWnjke">
			<div class="parent-V1C7xb3syx">
				<div
					class="parent-Ek3X-WnsJx"
					@click="toEditPage"
					:class="{ scard: props.sCardMode }"
				>
					<slot name="title">
						Iriure nonummy vero sea facilisi feugiat takimata.
					</slot>
				</div>
				<div class="parent-4ke4Z-hjyl" v-if="!props.sCardMode">
					<div class="parent-NJaMdZ43Jl" v-if="!status.card">
						{{ currentArtiticleCard.author }}
					</div>
					<div class="parent-4JePW-2ike" v-if="status.card">
						<div
							class="child-41BOZ-2sJx"
							v-if="currentArtiticleCard.isPublished === true"
							@click="convertToDraft"
						>
							<component
								:is="icons.draft"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div
							class="child-41BOZ-2sJx"
							v-if="currentArtiticleCard.isPublished === false"
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
					<div
						class="child-41BOZ-2sJx"
						@click="
							() => {
								router.push({
									name: 'ArticlesPanel',
									query: {
										filter: 'author',
										query: currentArtiticleCard.author,
									},
								});
							}
						"
					>
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
							published: currentArtiticleCard.isPublished,
							planned: currentArtiticleCard.planned,
						}"
					>
						{{ currentPubishStatus }}
					</div>
					<div class="parent-VJ3zvZ2ske">·</div>
					<div class="parent-Vk5DLW2jJl">
						{{
							dateFormatter(
								getLocalDatetime(currentArtiticleCard.createdAt),
								"ddd MMM DD YYYY"
							)
						}}
					</div>
					<div
						class="parent-4J0P8-hsJl"
						v-if="currentArtiticleCard.tags.length > 0"
					>
						<div
							class="child-EyLCwWno1l"
							v-for="tag in currentArtiticleCard.tags"
							v-if="!props.sCardMode"
							@click="
								() => {
									router.push({
										name: 'ArticlesPanel',
										query: {
											filter: 'tag',
											query: tag,
										},
									});
								}
							"
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
