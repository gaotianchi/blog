<template>
	<MainPageHeaderComponent
		title="编辑内容详情"
		sub-title="编辑文章的具体信息，包括标题、正文、标签等"
		bs-icon-name="bi-pencil-fill"
		:breadcrumb-items="[
			{
				name: '管理文章',
				routeName: 'ARTICLES',
			},
			{
				name: '编辑内容详情',
				routeName: 'ARTICLE_EDITOR',
				params: {
					id: route.params.id,
				},
			},
		]"
	></MainPageHeaderComponent>

	<div class="row col-lg-11 m-auto">
		<div class="col-md-8 overflow-y-auto" style="max-height: calc(100vh - 150px)">
			<textarea
				type="text"
				class="form-control-plaintext h1"
				id="floatingInput"
				placeholder="标题"
				aria-label="文章标题"
				:rows="titleRow"
				ref="titleRef"
				v-model="articleContent.title"
			></textarea>

			<div v-if="bodyEditor">
				<FloatingMenu
					class="floating-menu"
					:tippy-options="{ duration: 100 }"
					:editor="bodyEditor"
				>
					<div
						class="btn-group btn-group-sm bg-white"
						role="group"
						aria-label="Small button group"
					>
						<div class="btn-group" role="group">
							<button
								type="button"
								class="btn btn-outline-dark dropdown-toggle"
								data-bs-toggle="dropdown"
								aria-expanded="false"
							>
								Heading
							</button>
							<div class="dropdown-menu button-group">
								<button
									class="dropdown-item"
									@click="
										bodyEditor.chain().focus().toggleHeading({ level: 1 }).run()
									"
									:class="{
										'is-active': bodyEditor.isActive('heading', {
											level: 1,
										}),
									}"
								>
									H1
								</button>
								<button
									class="dropdown-item"
									@click="
										bodyEditor.chain().focus().toggleHeading({ level: 2 }).run()
									"
									:class="{
										'is-active': bodyEditor.isActive('heading', {
											level: 2,
										}),
									}"
								>
									H2
								</button>
								<button
									class="dropdown-item"
									@click="
										bodyEditor.chain().focus().toggleHeading({ level: 3 }).run()
									"
									:class="{
										'is-active': bodyEditor.isActive('heading', {
											level: 3,
										}),
									}"
								>
									H3
								</button>
							</div>
						</div>
						<!-- <button
							@click="bodyEditor.commands.setQuoteBlock()"
							class="btn btn-outline-dark"
						>
							<i class="bi bi-quote"></i>
						</button> -->
						<button
							@click="bodyEditor.commands.setIllustration()"
							class="btn btn-outline-dark"
						>
							<i class="bi bi-image"></i>
						</button>
					</div>
				</FloatingMenu>
				<!-- <BubbleMenu
					:editor="bodyEditor"
					class="bubble-menu"
					:tippy-options="{ duration: 100 }"
				>
					<div
						class="btn-group btn-group-sm"
						role="group"
						aria-label="Small button group"
					>
						<button
							@click="bodyEditor.commands.setCustomLink()"
							class="btn btn-outline-dark"
						>
							<i class="bi bi-link"></i>
						</button>
					</div>
				</BubbleMenu> -->
				<EditorContent :editor="bodyEditor" />
			</div>

		</div>
		
		<div class="col-12 col-md-4">
			<div class="sticky-top">
				<div class="tile">
					<div class="tile-title">标题与正文</div>
					<div class="tile-body row"></div>
				</div>
				<div class="tile">
					<div class="tile-title">摘要</div>
					<div class="title-body"></div>
				</div>
				<div class="tile">
					<div class="tile-title">标签</div>
					<div class="title-body"></div>
				</div>
				<div class="tile">
					<div class="tile-title">系列</div>
					<div class="title-body"></div>
				</div>
				<div class="tile">
					<div class="tile-title">固定链接</div>
					<div class="tile-body"></div>
				</div>
			</div>
		</div>

	</div>
	<div class="row">bottom</div>
</template>
<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount, watch, reactive } from 'vue';
	import StarterKit from '@tiptap/starter-kit';
	import { Editor, EditorContent, FloatingMenu, BubbleMenu } from '@tiptap/vue-3';
	import { QuoteExtension } from '@/component/editor.component/extension/quote/QuoteExtension';
	import { LinkExtension } from '@/component/editor.component/extension/link/LinkExtension';
	import { Illustration } from '@/component/editor.component/extension/illustration/IllustrationExtension';
	import Placeholder from '@tiptap/extension-placeholder';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import { makeRequest } from '@/service/request.service';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { useRoute } from 'vue-router';
	import type { APIResponse, ArticleInfo } from '@/type/response.type';

	const route = useRoute();
	const bodyEditor = ref<Editor>();
	const titleRef = ref<HTMLTextAreaElement>();
	const titleRow = ref(1);

	// 当前数据
	const articleContent = reactive({
		title: '',
		body: '',
	});

	// 追踪数据的变动状态
	const changed = reactive({
		title: false,
		body: false,
	});

	// 云端数据
	const articleInfo = ref<ArticleInfo | null>(null);
	const articleBody = ref('');

	const initArticleData = async () => {
		const articleInfoResponse: APIResponse<ArticleInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/info/' + route.params.id
		);
		articleInfo.value = articleInfoResponse.data;
		const articleBodyResponse: APIResponse<string> = await makeRequest(
			articleInfo.value.bodyValueLocation
		);
		bodyEditor.value?.commands.setContent(articleBodyResponse.data);

		articleBody.value = articleBodyResponse.data;

		articleContent.body = articleBody.value;
		articleContent.title = articleInfo.value.title;

		console.log(articleInfo.value);
		console.log(articleBody.value);
	};

	const initBodyEditor = () => {
		bodyEditor.value = new Editor({
			extensions: [
				Illustration,
				LinkExtension,
				QuoteExtension,
				StarterKit.configure({
					blockquote: {
						HTMLAttributes: {
							class: 'blockquote',
						},
					},
				}),
				Placeholder.configure({
					placeholder: '正文 ...',
				}),
			],
			content: '',
			editorProps: {
				attributes: {
					class: 'border-0',
				},
			},
		});

		bodyEditor.value.on('update', updateHtmlContent);
		articleContent.body = bodyEditor.value.getHTML();
	};

	onMounted(() => {
		initBodyEditor();
		initArticleData();
	});

	const updateHtmlContent = () => {
		if (bodyEditor.value) {
			articleContent.body = bodyEditor.value?.getHTML();
		}
	};

	onBeforeUnmount(() => {
		if (bodyEditor.value) {
			bodyEditor.value.destroy();
		}
	});

	watch(
		() => articleContent.title,
		() => {
			if (titleRef.value) {
				if (titleRef.value.scrollHeight > titleRef.value.clientHeight) {
					titleRow.value += 1;
				}
			}
		}
	);

	watch(
		() => articleContent.body,
		newValue => {
			changed.title = newValue != articleInfo.value?.title;
		}
	);
</script>

<style>
	.tiptap {
		min-height: calc(100vh - 250px);
	}
	.tiptap:focus,
	input:focus {
		outline: none;
	}

	p.is-editor-empty:first-child::before {
		color: lightgray;
		content: attr(data-placeholder);
		float: left;
		height: 0;
		pointer-events: none;
	}
	.tiptap p {
		font-size: 1rem;
	}
</style>
