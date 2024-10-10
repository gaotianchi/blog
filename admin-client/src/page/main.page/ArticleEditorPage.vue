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
		<!-- 编辑器主体 -->
		<div class="col-md-8 overflow-y-auto" style="max-height: 120vh">
			<textarea
				type="text"
				class="form-control-plaintext h1"
				id="floatingInput"
				placeholder="标题"
				aria-label="文章标题"
				:rows="titleRow"
				ref="titleRef"
				v-model="article.title"
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
						<button
							@click="bodyEditor.commands.setIllustration()"
							class="btn btn-outline-dark"
						>
							<i class="bi bi-image"></i>
						</button>
					</div>
				</FloatingMenu>
				<EditorContent :editor="bodyEditor" />
			</div>
		</div>

		<!-- 右侧边栏 -->
		<div class="col-12 col-md-4">
			<div class="sticky-top">
				<div class="d-grid gap-2 d-md-flex justify-content-md-center mb-3">
					<!-- 操作按钮区域 -->
					<button
						v-if="articleInfo?.status === 'DRAFT'"
						class="btn btn-warning"
						type="button"
						@click="resetArticleStatus('TRASH')"
					>
						<i class="bi bi-trash3-fill"></i>
						移入垃圾桶
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
						取消发布
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

				<!-- 信息栏组件 -->
				<div class="tile">
					<div class="tile-title">信息</div>
					<div class="tile-body">
						<ul class="list-group list-group-flush">
							<li class="list-group-item">
								<div class="row">
									<div class="col-4 p-0">发布状态</div>
									<div class="col-8 p-0">
										<span
											class="badge"
											:class="getStatusColorClass(articleInfo?.status)"
										>
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
									<div class="col-8 p-0">{{ articleInfo?.penName }}</div>
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
					<div class="title-body">
						<textarea
							class="form-control"
							aria-label="summary textarea"
							name="summary-textarea"
							id="summary-textarea"
							rows="5"
							v-model="article.summary"
						></textarea>
					</div>
					<div class="tile-footer" v-if="changed.summary">
						<div class="row justify-content-end">
							<button
								@click="article.summary = article ? article.summary : ''"
								type="button"
								class="btn btn-secondary w-auto me-2"
							>
								还原
							</button>
							<button
								@click="updateSummary"
								type="button"
								class="btn btn-primary w-auto me-2"
							>
								保存
							</button>
						</div>
					</div>
				</div>
				<div class="tile">
					<div class="tile-title">标签</div>
					<div class="title-body">
						<div class="mb-3">
							<div
								v-for="(tag, index) in tags"
								:key="index"
								class="badge rounded-pill bg-secondary me-2 mb-2"
							>
								<span class="ms-1" style="padding-bottom: 5px">{{ tag.name }}</span>
								<button
									type="button"
									class="btn-close btn-close-white ms-1"
									@click="handleBtnRemoveTag(tag)"
									aria-label="Remove"
								></button>
							</div>
						</div>
						<div>
							<input
								type="text"
								class="form-control"
								v-model="newTagName"
								@keyup.enter="handleKeyEnterAddTag"
								placeholder="输入标签并按回车键"
							/>
						</div>
					</div>
				</div>
				<div class="tile">
					<div class="tile-title">系列</div>
					<div class="title-body"></div>
				</div>
				<div class="tile">
					<div class="tile-title">固定链接</div>
					<div class="tile-body">
						<div class="mb-3">
							<input
								aria-label="slug-input"
								type="text"
								class="form-control"
								name="slug-input"
								id="slug-input"
								aria-describedby="helpId"
								placeholder="Slug"
								v-model="article.slug"
							/>
						</div>
					</div>
					<div class="tile-footer" v-if="changed.slug">
						<div class="row justify-content-end">
							<button
								@click="article.slug = articleInfo ? articleInfo.slug : ''"
								type="button"
								class="btn btn-secondary w-auto me-2"
							>
								还原
							</button>
							<button
								@click="openUpdateSlugModal"
								type="button"
								class="btn btn-primary w-auto me-2"
							>
								保存
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

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
			<p>发布文章《{{ article.title }}》之后所有人都可以看到，确定要发布吗？</p>
		</template>
	</ModalComponent>

	<ModalComponent title="更新文章固定链接" ref="updateSlugModal" @save-change="updateSlug">
		<template #body>
			<p>确定要将该文章的链接更改为：</p>
			<p>{{ 'http://localhost:8090/' + route.params.id + '/' + article.slug }}</p>
			<p>吗？</p>
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
	import type { APIResponse, ArticleInfo, TagInfo, UserInfo } from '@/type/response.type';
	import { getFormarttedDate } from '@/utlis';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import ModalComponent from '@/component/ModalComponent.vue';

	const route = useRoute();
	const bodyEditor = ref<Editor>();
	const titleRef = ref<HTMLTextAreaElement>();
	const titleRow = ref(1);
	const publishModal = ref();
	const updateSlugModal = ref();
	const newTagName = ref('');

	// 当前数据
	const article = reactive({
		title: '',
		body: '',
		summary: '',
		slug: '',
	});

	// 追踪数据的变动状态
	const changed = reactive({
		title: false,
		body: false,
		summary: false,
		slug: false,
	});

	// 云端数据
	const articleInfo = ref<ArticleInfo | null>(null);
	const userInfo = ref<UserInfo | null>(null);
	const articleBody = ref('');
	const tags = ref<TagInfo[]>([]);

	const handleKeyEnterAddTag = async () => {
		console.log('enter');
		if (!newTagName.value.trim()) {
			return;
		}
		if (tags.value.some(tag => tag.name == newTagName.value)) {
			newTagName.value = '';
			return;
		}

		const tagInfoResponse: APIResponse<TagInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/tags/new/' + newTagName.value,
			{
				method: 'POST',
			}
		);
		if (tagInfoResponse.code === 0) {
			console.log(tagInfoResponse.data);
			const addTagResponse: APIResponse<TagInfo> = await makeRequest(
				RESOURCE_BASE_URL +
					'/articles/tag/' +
					route.params.id +
					'/' +
					tagInfoResponse.data.id,
				{
					method: 'POST',
				}
			);
			if (addTagResponse.code === 0) {
				tags.value.push(tagInfoResponse.data);
				newTagName.value = '';
			}
		}
	};
	const updateSummary = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/summary/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					summary: article.summary,
				}),
			}
		);
		if (response.code === 0) {
			if (articleInfo.value) {
				articleInfo.value.summary = article.summary;
				changed.summary = false;
			}
		} else {
		}
	};
	const handleBtnRemoveTag = async (tag: TagInfo) => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/tag/' + route.params.id + '/' + tag.id,
			{
				method: 'DELETE',
			}
		);
		if (response.code === 0) {
			tags.value = tags.value.filter(t => t.id !== tag.id);
			console.log(tags.value);
		}
	};
	const updateArticleContent = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/content/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					title: article.title,
					body: article.body,
				}),
			}
		);
		if (response.code === 0) {
			if (articleInfo.value) {
				articleInfo.value.title = article.title;
				articleBody.value = article.body;
			}
			showMessage('更新成功', AlertType.SUCCESS);
		} else {
			showMessage('更新失败', AlertType.ERROR);
		}
	};
	const getStatusColorClass = (status?: string) => {
		switch (status?.toLowerCase()) {
			case 'published':
				return 'text-bg-success';
			case 'draft':
				return 'text-bg-secondary';
			case 'trash':
				return 'text-bg-dark';
			default:
				break;
		}
	};
	const updateSlug = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/slug/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					slug: article.slug,
				}),
			}
		);
		if (response.code === 0) {
			if (articleInfo.value) {
				articleInfo.value.slug = article.slug;
				changed.slug = false;
				showMessage('更新成功', AlertType.SUCCESS);
			}
		} else {
			showMessage('更新失败', AlertType.ERROR);
		}
		updateSlugModal.value.hide();
	};

	const openPublishModal = () => {
		if (publishModal.value) {
			publishModal.value.show();
		}
	};

	const openUpdateSlugModal = () => {
		if (updateSlugModal.value) {
			updateSlugModal.value.show();
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

		article.body = articleBody.value;
		article.title = articleInfo.value.title;
		article.summary = articleInfo.value.summary;
		article.slug = articleInfo.value.slug;

		const tagInfoResponse: APIResponse<TagInfo[]> = await makeRequest(
			articleInfo.value.tagInfoPageLocation
		);
		tags.value = tagInfoResponse.data;

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
		article.body = bodyEditor.value.getHTML();
	};

	onMounted(() => {
		initBodyEditor();
		initArticleData();
	});

	const updateHtmlContent = () => {
		if (bodyEditor.value) {
			article.body = bodyEditor.value?.getHTML();
		}
	};

	const contentSync = computed(() => {
		return !changed.body && !changed.title;
	});

	onBeforeUnmount(() => {
		if (bodyEditor.value) {
			bodyEditor.value.destroy();
		}
	});

	watch(
		() => article.title,
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
		() => article.body,
		newValue => {
			changed.body = newValue != articleBody.value;
		}
	);

	watch(
		() => article.summary,
		newValue => {
			changed.summary = newValue != articleInfo.value?.summary;
		}
	);

	watch(
		() => article.slug,
		newValue => {
			changed.slug = newValue != articleInfo.value?.slug;
		}
	);
</script>

<style>
	.tiptap {
		min-height: 100vh;
		border-bottom: 1px solid gray;
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
