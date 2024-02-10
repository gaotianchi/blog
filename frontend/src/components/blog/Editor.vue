<script setup lang="ts">
	import icons from "@/components/icons";
	import { computed, reactive, watch, watchEffect } from "vue";
	import { MdEditor } from "md-editor-v3";
	import "md-editor-v3/lib/style.css";
	import SettingBar from "./SettingBar.vue";
	import type { Article, Settings } from "@/typing";

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

	async function getArticleData(): Promise<Article> {
		const url =
			"http://localhost:5000/v1/author/article/" + props.articleId;
		const response = await fetch(url);
		if (response.status === 200) {
			const articleData = await response.json();
			const data: Article = {
				id: articleData.id,
				title: articleData.title,
				body: articleData.body,
				slug: articleData.slug,
				createdAt: new Date(articleData.created_at),
				updatedAt: new Date(articleData.updated_at),
				isPublished: articleData.is_published,
				publishedAt: new Date(articleData.published_at),
				seriesId: articleData.series_id,
				authorId: articleData.author_id,
				tags: articleData.tags,
			};
			return data;
		} else {
			const errorData = await response.json();
			throw errorData.error;
		}
	}
	const originalArticle: Article = reactive({
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
	});
	const currentArticle: Article = reactive(originalArticle);
	const articleJson = computed<{
		id: number;
		title: string;
		body: string;
		slug: string;
		created_at: string;
		updated_at: string;
		is_published: boolean;
		published_at: string;
		tags: string[];
		series_id: number | null;
	}>(() => {
		const currentJson = {
			id: currentArticle.id,
			title: currentArticle.title,
			body: currentArticle.body,
			slug: currentArticle.slug,
			created_at: currentArticle.createdAt.toISOString(),
			tags: currentArticle.tags,
			is_published: currentArticle.isPublished,
			published_at: currentArticle.publishedAt.toISOString(),
			updated_at: new Date().toISOString(),
			series_id: currentArticle.seriesId,
		};
		console.log(currentJson);
		return currentJson;
	});

	async function update(): Promise<void> {
		const url =
			"http://localhost:5000/v1/author/article/" + props.articleId;
		const accessToken = localStorage.getItem("access_token");
		const response = await fetch(url, {
			method: "PATCH",
			headers: {
				"Content-Type": "application/json",
				Authorization: "Bearer " + accessToken,
			},
			body: JSON.stringify(articleJson.value),
		});
		console.log("Update article.");
	}

	async function publishOrUpdateArticle(): Promise<void> {
		status.update = true;
		if (!currentArticle.isPublished) {
			currentArticle.isPublished = true;
		}
		update();
	}
	watchEffect(() => {
		if (status.update) {
			setTimeout(() => {
				status.update = false;
			}, 1000);
		}
	});
	console.log(currentArticle);
	getArticleData()
		.then((data) => {
			Object.assign(originalArticle, data);
			console.log(originalArticle);
		})
		.catch((error) => {
			console.error(error);
		});
	watch(currentArticle, () => {
		status.sync = false;
	});
	function updateSettings(settings: Settings): void {
		currentArticle.tags = settings.tags;
		currentArticle.slug = settings.permalink;
		currentArticle.seriesId = settings.seriesId;
		currentArticle.tags = settings.tags;
	}
</script>

<template>
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
					@click="publishOrUpdateArticle"
				>
					<component
						:is="icons.update"
						class="icon medium child-N1Z087nqke parent-DkmXQLaqyx"
						v-if="currentArticle.isPublished"
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
						class="icon medium parent-NJwJvQ29kg"
						:class="{ active: status.moreButton }"
					/>
					<div
						class="parent-VJFuGLhcyg child-"
						v-if="status.moreButton"
					>
						<div
							class="child-VJMNmI35yx"
							v-if="currentArticle.isPublished"
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
					class="icon medium parent-T1UEoQnqJx"
					@click="status.settings = !status.settings"
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
		width: 350px;
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
		background-color: #ffa33c;
		border-radius: 5px;
		height: 45px;
	}
	.parent-V1cCQmh5ye {
		width: 100px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.parent-EJQSS72ckl {
		line-height: 24px;
		color: white;
		padding-left: 10px;
		font-size: 16px;
	}
	.parent-NJwJvQ29kg {
		width: fit-content;
		padding: 5px;
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
	}
	.parent-SyjyVQncJl {
		width: 45px;
		height: 45px;
		display: flex;
		justify-content: center;
		align-items: center;
		cursor: pointer;
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
	}
	.parent-4yCs6qp9Jg {
		font-size: 17px;
		font-weight: 500;
		padding-bottom: 10px;
	}
	/* .parent-dJnizjpqye{

	} */
</style>
