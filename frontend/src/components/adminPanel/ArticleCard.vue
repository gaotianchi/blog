<script setup lang="ts">
	import icons from "@/components/icons";
	import { reactive, ref } from "vue";
	import { useRouter } from "vue-router";
	import { allRemoteArticleCards, patchArticleCardItem } from "@/api/remote";
	import { propConfirm, propMessage } from "@/api/local";
	import type { SerializedArticleCard } from "@/typing";
	import { getLocalDatetime, dateFormatter } from "@/utlis";
	import ConfirmSlot from "@/components/ConfirmSlot.vue";
	import TagEditor from "./TagEditor.vue";
	const props = defineProps<{
		articleIndex: number;
	}>();
	const status = reactive({
		tags: false,
	});
	const router = useRouter();
	const articles = reactive(allRemoteArticleCards);
	const localTags = ref(articles[props.articleIndex].tags);
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
	async function updateArticleItem(
		serializedArticleCard: SerializedArticleCard
	): Promise<void> {
		try {
			await patchArticleCardItem(
				articles[props.articleIndex].id,
				serializedArticleCard
			);
		} catch (error) {
			console.error(error);
		}
	}
	function publishArticleItem(): void {
		try {
			propMessage("Publishing article...");
			updateArticleItem({
				id: articles[props.articleIndex].id,
				isPublished: true,
			}).then(() => {
				articles[props.articleIndex].isPublished = true;
				propMessage("Changed saved.");
			});
		} catch (error) {
			propMessage("Please try again.");
		}
	}
	function convertToDraft(): void {
		try {
			propMessage("Saving article as draft ...");
			updateArticleItem({
				id: articles[props.articleIndex].id,
				isPublished: false,
			}).then(() => {
				articles[props.articleIndex].isPublished = false;
				propMessage("Changed saved.");
			});
		} catch (error) {
			propMessage("Please try again.");
		}
	}
	function updateTags(): void {
		status.tags = false;
		try {
			updateArticleItem({
				id: articles[props.articleIndex].id,
				tags: localTags.value,
			}).then(() => {
				articles[props.articleIndex].tags = localTags.value;
				propMessage("Changed saved.");
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
	<div class="parent-NJQTAg2j1g">
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
					<div class="parent-4JePW-2ike">
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
						<div class="child-41BOZ-2sJx">
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
						}"
					>
						{{
							articles[props.articleIndex].isPublished
								? "Published"
								: "Unpublished"
						}}
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
<style scoped>
	.parent-NJQTAg2j1g {
		width: 100%;
		height: 100px;
		display: flex;
		align-items: center;
		padding: 10px;
		border-radius: 10px;
		outline: lightgrey solid 1px;
		margin-bottom: 15px;
	}
	.parent-NJQTAg2j1g:hover {
		outline: none;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
	}
	.parent-Vy7GxWhi1x {
		min-width: 80px;
		min-height: 80px;
		max-width: 80px;
		max-height: 80px;
		background-color: grey;
		cursor: pointer;
	}
	.parent-EJbXxWnjke {
		flex-grow: 1;
		display: flex;
		width: 100%;
		height: 100%;
		flex-direction: column;
		justify-content: space-between;
		padding: 10px;
	}
	.parent-V1C7xb3syx {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}
	.parent-Ek3X-WnsJx {
		flex-grow: 1;
		font-size: 17px;
		line-height: 17px;
		cursor: pointer;
	}
	.parent-4ke4Z-hjyl {
		display: flex;
		justify-content: flex-end;
	}
	.parent-4JePW-2ike {
		display: flex;
		justify-content: center;
	}
	.parent-N1GNlb3oke {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.parent-Nkzrbbnike {
		display: flex;
		align-items: center;
	}
	.child-NkE1rZnskl {
		width: 20px;
		height: 20px;
	}
	.child-41BOZ-2sJx {
		margin-left: 5px;
		cursor: pointer;
		height: 30px;
		width: 30px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.child-41BOZ-2sJx:hover {
		background-color: #cacaca8e;
		border-radius: 50%;
	}
	.parent-N1rwLZ3jyl {
		font-size: 16px;
		line-height: 16px;
		font-weight: bold;
		color: var(--first-color);
	}
	.parent-N1rwLZ3jyl.published {
		color: var(--second-color);
	}
	.parent-VJ3zvZ2ske {
		margin: 0 5px;
		font-size: 16px;
		font-weight: 1000;
		line-height: 14px;
		color: grey;
	}
	.parent-4J0P8-hsJl {
		display: flex;
		justify-content: flex-start;
		align-items: center;
		overflow: auto;
	}
	.child-EyLCwWno1l {
		height: 24px;
		width: fit-content;
		border-radius: 20px;
		margin: 1px 3px;
		outline: lightgrey solid 1px;
		padding: 0 10px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.child-Ek0bDMhsJe {
		font-size: 12px;
		line-height: 12px;
		color: rgb(174, 174, 174);
		font-weight: lighter;
	}
	.parent-Vk5DLW2jJl {
		font-size: 14px;
		line-height: 14px;
		font-weight: lighter;
		width: max-content;
		margin-right: 10px;
	}
</style>
