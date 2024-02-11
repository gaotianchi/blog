<script setup lang="ts">
	import icons from "@/components/icons";
	import { computed, onMounted, reactive, ref, watch } from "vue";
	import { MdEditor } from "md-editor-v3";
	import "md-editor-v3/lib/style.css";
	import SettingBar from "./SettingBar.vue";
	import type { Article, ConfirmProp, MessageProp } from "@/typing";
	import { updateArticleItem, getArticleItem, displayMessage } from "@/api";
	import type { APIError } from "@/api/errors";

	type Status = {
		moreButton: boolean;
		sync: boolean;
		update: boolean;
		settings: boolean;
	};
	const props = defineProps<{
		articleId: string;
	}>();
	const status: Status = reactive({
		moreButton: false,
		sync: true,
		update: false,
		settings: false,
	});
	const messageProp: MessageProp = reactive({
		active: false,
		message: "",
	});
	const defaultConfirmProp: ConfirmProp = {
		active: false,
		header: undefined,
		body: "",
		yesMessage: undefined,
		noMessage: undefined,
		callback: () => {},
	};
	const confirmProp: ConfirmProp = reactive({ ...defaultConfirmProp });
	const defaultArticle: Article = {
		id: 0,
		title: "",
		body: "",
		slug: "",
		createdAt: new Date(),
		updatedAt: new Date(),
		isPublished: false,
		publishedAt: new Date(),
		tags: [],
		seriesId: 0,
		authorId: 0,
	};

	const originalArticle: Article = reactive({ ...defaultArticle });
	const currentArticle: Article = reactive({ ...defaultArticle });
	const articleJson = computed<string>(() => {
		const currentJson = {
			id: currentArticle.id,
			title: currentArticle.title.trim(),
			body: currentArticle.body.trim(),
			slug: currentArticle.slug.trim(),
			created_at: currentArticle.createdAt.toISOString(),
			tags: currentArticle.tags,
			is_published: currentArticle.isPublished,
			published_at: currentArticle.publishedAt.toISOString(),
			updated_at: currentArticle.updatedAt.toISOString(),
			series_id: currentArticle.seriesId,
		};
		return JSON.stringify(currentJson);
	});
	onMounted(() => {
		initOriginalArticle();
	});
	watch(articleJson, () => {
		if (articleChanged()) {
			status.sync = false;
		} else {
			status.sync = true;
		}
	});
	watch(messageProp, () => {
		if (messageProp.active) {
			setTimeout(() => {
				messageProp.active = false;
				messageProp.message = "";
			}, 1500);
		}
	});
	function articleChanged(): boolean {
		const oldArticle = sessionStorage.getItem("oldArticle") || "";
		if (articleJson.value != oldArticle) {
			return true;
		} else {
			return false;
		}
	}
	async function initOriginalArticle(): Promise<void> {
		const articleData = await getArticleItem(props.articleId);
		Object.assign(originalArticle, articleData);
		Object.assign(currentArticle, articleData);
		sessionStorage.setItem("oldArticle", articleJson.value);
	}
	async function updateArticle(): Promise<void> {
		status.update = true;
		if (!articleChanged()) {
			displayMessage(messageProp, "Article has been updated.");
			status.update = false;
			return;
		}
		try {
			displayMessage(messageProp, "Saving changes.");
			await updateArticleItem(props.articleId, articleJson.value);
			status.sync = true;
			sessionStorage.setItem("oldArticle", articleJson.value);
			displayMessage(messageProp, "Changes saved successfully.");
		} catch (error) {
			const apiError = error as APIError;
			console.log(apiError);
			displayMessage(messageProp, "Saving failed, please try again.");
		}
		status.update = false;
	}
	function convertToDraft(): void {
		currentArticle.isPublished = false;
		updateArticle();
	}
	function resetConfirmProp(): void {
		Object.assign(confirmProp, { ...defaultConfirmProp });
	}
	function desideToPublishArticle(): void {
		resetConfirmProp();
		confirmProp.active = true;
		confirmProp.header = "Publish Article";
		confirmProp.body = `Are you sure you want to publish the article 《${currentArticle.title}》?`;
		confirmProp.callback = publishArticle;
		confirmProp.yesMessage = "Publish";
	}
	function publishArticle(): void {
		currentArticle.isPublished = true;
		updateArticle();
	}
</script>

<template>
	<MessageProp :message-prop="messageProp" />
	<ConfirmProp
		:confirm-prop="confirmProp"
		@confirm="
			(decision: boolean) => {
				confirmProp.active = false;
			}
		"
	/>
	<div class="parent-VJ4oXmhc1l child-">
		<input
			type="text"
			name="article-title"
			id="article-title"
			placeholder="Article title"
			v-model="currentArticle.title"
		/>
		<component
			v-if="status.sync"
			:is="icons.sync"
			class="icon medium child-G1Bcjm39yl"
		/>
		<component
			v-else
			:is="icons.unsync"
			class="icon medium child-G1Bcjm39yl"
		/>
		<div class="parent-Uybp7Xhq1e child-">
			<div class="parent-Vk4RmXncyg child-">
				<button
					type="button"
					class="parent-V1cCQmh5ye child-E1GkNXh9yl"
					@click="
						currentArticle.isPublished
							? updateArticle()
							: desideToPublishArticle()
					"
				>
					<component
						:is="icons.update"
						class="icon medium child-N1Z087nqke parent-DkmXQLaqyx"
						v-if="currentArticle.isPublished"
						:class="{ active: status.update }"
					/>
					<component
						:is="icons.publish"
						class="icon medium child-N1Z087nqke"
						v-else
					/>

					<span class="parent-EJQSS72ckl">{{
						currentArticle.isPublished ? "Update" : "Publish"
					}}</span>
				</button>
				<button
					type="button"
					class="parent-E1AC7735yx child-E1GkNXh9yl"
					@mouseenter="status.moreButton = true"
					@mouseleave="status.moreButton = false"
				>
					<component
						:is="icons.down"
						class="icon small parent-NJwJvQ29kg"
						:class="{ active: status.moreButton }"
					/>
					<div
						class="parent-VJFuGLhcyg child-"
						v-if="status.moreButton"
					>
						<div
							class="child-VJMNmI35yx"
							v-if="currentArticle.isPublished"
							@click="convertToDraft"
						>
							Convert to draft
						</div>
						<div class="child-VJMNmI35yx">Preview article</div>
					</div>
				</button>
			</div>
			<div class="parent-SyjyVQncJl child-">
				<component
					:is="icons.setting"
					class="icon bigest parent-T1UEoQnqJx"
					@click="status.settings = !status.settings"
					:class="{ active: status.settings }"
				/>
				<div class="parent-Vy_T35aq1x" v-if="status.settings">
					<div class="parent-4yCs6qp9Jg">Article Settings</div>
					<SettingBar
						:settings="{
							tags: originalArticle.tags,
							datetime: originalArticle.publishedAt,
							permalink: originalArticle.slug,
							seriesId: originalArticle.seriesId,
						}"
						@update-settings="
							(s) => {
								currentArticle.tags = s.tags;
								currentArticle.publishedAt = s.datetime;
								currentArticle.slug = s.permalink;
								currentArticle.seriesId = s.seriesId;
							}
						"
					/>
				</div>
			</div>
		</div>
	</div>
	<MdEditor v-model="currentArticle.body" class="parent-VJTd3Xn9kx" />
</template>

<style scoped>
	.parent-VJ4oXmhc1l {
		width: 100%;
		height: 50px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	#article-title {
		width: 100%;
		flex-grow: 1;
		height: 45px;
		border: none;
		outline: none;
		padding-bottom: 3px;
		padding-top: 20px;
		border-bottom: #ffa33c solid 1px;
		font-size: 25px;
		line-height: 45px;
		vertical-align: bottom;
	}
	.parent-Uybp7Xhq1e {
		width: 250px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.parent-Vk4RmXncyg {
		position: relative;
		display: flex;
		align-items: center;
	}
	.child-E1GkNXh9yl {
		border: none;
		cursor: pointer;
		border-radius: 5px;
		height: 45px;
	}
	.parent-V1cCQmh5ye {
		width: 100px;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #fe8700;
	}
	.parent-V1cCQmh5ye:hover {
		background-color: #fe8700;
	}
	@keyframes rotateAnimation {
		from {
			transform: rotate(0deg);
		}
		to {
			transform: rotate(-360deg);
		}
	}
	.parent-DkmXQLaqyx.active {
		animation: rotateAnimation 2s linear infinite;
	}
	.parent-EJQSS72ckl {
		line-height: 24px;
		color: white;
		padding: 0 10px;
		font-size: 16px;
	}
	.parent-NJwJvQ29kg {
		width: fit-content;
		padding: 3px;
		transform: rotate(0deg);
		transition: all 0.3s ease;
	}
	.parent-NJwJvQ29kg.active {
		transform: rotate(180deg);
	}
	.parent-VJFuGLhcyg {
		position: absolute;
		top: 45px;
		right: -69px;
		width: 180px;
		height: fit-content;
		cursor: default;
		z-index: 99;
		padding: 15px 0;
		box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
	}
	.child-VJMNmI35yx {
		width: 100%;
		height: 35px;
		display: flex;
		align-items: center;
		padding-left: 20px;
		font-size: 16px;
		line-height: 20px;
		cursor: pointer;
	}
	.child-VJMNmI35yx:hover {
		background-color: aliceblue;
	}
	.parent-E1AC7735yx {
		margin-left: 2px;
		width: 16px;
		padding: 0;
	}
	.parent-SyjyVQncJl {
		width: 45px;
		height: 45px;
		display: flex;
		justify-content: center;
		align-items: center;
		cursor: pointer;
	}
	.parent-T1UEoQnqJx {
		padding: 10px;
	}
	.parent-T1UEoQnqJx.active {
		border: grey solid 1px;
		background-color: rgba(177, 177, 177, 0.116);
	}
	.child-G1Bcjm39yl {
		width: 45px;
	}
	.parent-VJTd3Xn9kx {
		min-height: calc(100vh - 50px);
	}
	.parent-Vy_T35aq1x {
		position: absolute;
		top: 50px;
		right: 0px;
		z-index: 99;
		padding: 10px 10px;
		box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
		cursor: default;
	}
	.parent-4yCs6qp9Jg {
		font-size: 17px;
		font-weight: 500;
		padding-bottom: 10px;
	}
</style>
