<script setup lang="ts">
	import icons from "@/components/icons";
	import { ref, watchEffect, type Ref, computed, reactive, watch } from "vue";
	import SettingBar from "./SettingBar.vue";
	import { MdEditor } from "md-editor-v3";
	import "md-editor-v3/lib/style.css";
	import { APIError } from "@/api/errors";
	type ArtitleItems = {
		id?: number;
		title?: string;
		body?: string;
		slug?: string;
		createdAt?: Date;
		updatedAt?: Date;
		isPublished?: boolean;
		publishedAt?: Date;
		seriesId?: number | null;
		authorId?: number;
		tags?: string[];
	};
	type Status = {
		moreButton: boolean;
		sync: boolean;
	};
	const props = defineProps<{
		articleId: string;
	}>();
	const status: Status = reactive({
		moreButton: false,
		sync: true,
	});
	const model = ref("");
	async function getArticleData(): Promise<ArtitleItems> {
		const url =
			"http://localhost:5000/v1/author/article/" + props.articleId;
		const response = await fetch(url);
		if (response.status === 200) {
			const articleData = await response.json();
			const data: ArtitleItems = {
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
	const currentArticle: Ref<ArtitleItems> = ref({});
	const articleJson = computed<{
		id?: number;
		title?: string;
		body?: string;
		slug?: string;
		created_at?: string;
		updated_at?: string;
		is_published?: boolean;
		published_at?: string;
		tags?: string[];
		series_id?: number | null;
	}>(() => {
		const article = currentArticle.value;
		return {
			id: article?.id,
			title: article?.title,
			body: article?.body,
			slug: article?.slug,
			created_at: article?.createdAt.toISOString(),
			tags: article?.tags,
			is_published: article?.isPublished,
			published_at: article?.publishedAt.toISOString(),
			updated_at: new Date().toISOString(),
			series_id: article?.seriesId,
		};
	});

	async function updateArticle(): Promise<void> {
		const url =
			"http://localhost:5000/v1/author/article/" + props.articleId;
		const response = await fetch(url, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(articleJson.value),
		});
	}

	getArticleData()
		.then((data) => {
			currentArticle.value = data;
			console.log(data);
		})
		.catch((error) => {
			console.error(error);
		});
	watch(currentArticle, () => {
		status.sync = false;
	});
</script>

<template>
	<div class="parent-VJ4oXmhc1l child-">
		<input
			type="text"
			name="article-title"
			id="article-title"
			placeholder="Article title"
			v-model="currentArticle?.title"
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
				>
					<component
						:is="icons.draft"
						class="icon medium child-N1Z087nqke"
						v-if="currentArticle?.isPublished"
					/>
					<component
						:is="icons.publish"
						class="icon medium child-N1Z087nqke"
						v-else
					/>

					<span class="parent-EJQSS72ckl">{{
						currentArticle?.isPublished ? "Update" : "Publish"
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
							v-if="currentArticle?.isPublished"
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
				/>
				<!-- <SettingBar /> -->
			</div>
		</div>
	</div>
	<MdEditor v-model="model" class="parent-VJTd3Xn9kx" />
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
		background-color: white;
		box-shadow: -1px 1px 1px 1px rgba(128, 128, 128, 0.103);
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
		border: grey solid 1px;
		cursor: pointer;
	}
	.child-G1Bcjm39yl {
		width: 45px;
	}
	.parent-VJTd3Xn9kx {
		min-height: calc(100vh - 50px);
	}
</style>
