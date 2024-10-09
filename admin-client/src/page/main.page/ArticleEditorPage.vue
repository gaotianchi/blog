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
				<div class="d-grid gap-2 d-md-flex justify-content-md-center mb-3">
					<button class="btn btn-light" type="button">
						<i class="bi bi-eye-fill"></i>
						预览
					</button>
					<button
						v-if="articleInfo?.status === 'DRAFT'"
						class="btn btn-success"
						type="button"
						@click="openPublishModal"
					>
						<i class="bi bi-send-fill"></i>
						发布
					</button>
					<button
						v-if="articleInfo?.status === 'PUBLISHED'"
						class="btn btn-secondary"
						type="button"
						@click="resetArticleStatus('DRAFT')"
					>
						<i class="bi bi-file-earmark-arrow-down-fill"></i>
						还原
					</button>
					<button
						class="btn btn-warning me-md-2"
						type="button"
						:disabled="contentSync"
						@click="updateArticleContent"
					>
						<i class="bi bi-arrow-repeat"></i>
						更新
					</button>
				</div>
				<div class="tile">
					<div class="tile-title">信息</div>
					<div class="tile-body">
						<ul class="list-group list-group-flush">
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">同步状态</div>
									<div class="col-8 p-0">
										<span class="badge text-bg-light" v-if="contentSync">
											已同步
										</span>
										<span class="badge text-bg-warning" v-else>未同步</span>
									</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">发布状态</div>
									<div class="col-8 p-0">
										<span
											class="badge text-bg-secondary"
											v-if="articleInfo?.status === 'DRAFT'"
										>
											{{ articleInfo?.status }}
										</span>
										<span class="badge text-bg-success" v-else>
											{{ articleInfo?.status }}
										</span>
									</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">字数</div>
									<div class="col-8 p-0">20</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">作者</div>
									<div class="col-8 p-0">{{ userInfo?.penName }}</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">创建日期</div>
									<div class="col-8 p-0">
										{{ getFormarttedDate(articleInfo?.creationDatetime) }}
									</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">更新日期</div>
									<div class="col-8 p-0">
										{{ getFormarttedDate(articleInfo?.lastUpdatedDatetime) }}
									</div>
								</div>
							</li>
							<li class="list-group-item" v-if="articleInfo?.status === 'PUBLISHED'">
								<div class="row">
									<div class="col-4 p-0">发布日期</div>
									<div class="col-8 p-0">
										{{ getFormarttedDate(articleInfo?.publishDatetime) }}
									</div>
								</div>
							</li>
						</ul>
					</div>
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

	<ModalComponent
		title="发布文章"
		save-button-text="发布"
		close-button-text="取消"
		ref="publishModal"
		@save-change="
			() => {
				resetArticleStatus('PUBLISHED');
				publishModal.hide();
			}
		"
	>
		<template #body>
			<p>发布文章《{{ articleContent.title }}》之后所有人都可以看到，确定要发布吗？</p>
		</template>
	</ModalComponent>
</template>
<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount, watch, reactive, computed } from 'vue';
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
	import type { APIResponse, ArticleInfo, UserInfo } from '@/type/response.type';
	import { getFormarttedDate } from '@/utlis';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import ModalComponent from '@/component/ModalComponent.vue';

	const route = useRoute();
	const bodyEditor = ref<Editor>();
	const titleRef = ref<HTMLTextAreaElement>();
	const titleRow = ref(1);
	const publishModal = ref();

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
	const userInfo = ref<UserInfo | null>(null);
	const articleBody = ref('');

	const contentSync = computed(() => {
		return !changed.body && !changed.title;
	});

	const updateArticleContent = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/content/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					title: articleContent.title,
					body: articleContent.body,
				}),
			}
		);
		if (response.code === 0) {
			if (articleInfo.value) {
				articleInfo.value.title = articleContent.title;
				articleBody.value = articleContent.body;
			}
			showMessage('更新成功', AlertType.SUCCESS);
		} else {
			showMessage('更新失败', AlertType.ERROR);
		}
	};

	const openPublishModal = () => {
		if (publishModal.value) {
			publishModal.value.show();
		}
	};

	const resetArticleStatus = async (targetStatus: string) => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/status/' + route.params.id + '/' + targetStatus,
			{
				method: 'PATCH',
			}
		);
		if (response.code === 0) {
			if (articleInfo.value) {
				articleInfo.value.status = targetStatus.toUpperCase();
			}
			switch (targetStatus) {
				case 'PUBLISHED':
					showMessage('发布成功', AlertType.SUCCESS);
					break;
				case 'DRAFT':
					showMessage('文章成功还原为草稿', AlertType.SUCCESS);
				default:
					break;
			}
		} else {
			showMessage('状态更新失败', AlertType.ERROR);
		}
	};

	const initArticleData = async () => {
		const articleInfoResponse: APIResponse<ArticleInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/info/' + route.params.id
		);
		articleInfo.value = articleInfoResponse.data;
		const articleBodyResponse: APIResponse<string> = await makeRequest(
			articleInfo.value.bodyValueLocation
		);
		bodyEditor.value?.commands.setContent(articleBodyResponse.data);
		const userInfoResponse: APIResponse<UserInfo> = await makeRequest(
			articleInfo.value.userInfoLocation
		);
		userInfo.value = userInfoResponse.data;
		articleBody.value = articleBodyResponse.data;

		articleContent.body = articleBody.value;
		articleContent.title = articleInfo.value.title;

		console.log(articleInfo.value);
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
		newValue => {
			changed.title = newValue != articleInfo.value?.title;
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
			changed.body = newValue != articleBody.value;
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
