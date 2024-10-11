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
		<div class="col-md-8 tile overflow-y-auto" style="max-height: 120vh">
			<textarea
				type="text"
				class="form-control-plaintext h1"
				id="floatingInput"
				placeholder="标题"
				aria-label="文章标题"
				:rows="titleRow"
				ref="titleTextAreaRef"
				v-model="title.value"
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
			<!-- 操作按钮区域 -->
			<div class="tile">
				<!-- 更新按钮 -->
				<button
					v-if="remoteArticleInfo?.status !== 'TRASH'"
					class="btn btn-primary me-md-2"
					type="button"
					:disabled="!contentChanged"
					@click="updateArticleContent"
				>
					<i class="bi bi-arrow-repeat"></i>
					更新
				</button>
				<!--DRAFT 转化为 PUBLISHED -->
				<button
					v-if="remoteArticleInfo?.status === 'DRAFT'"
					class="btn btn-success me-md-2"
					type="button"
					@click="openPublishModal"
				>
					<i class="bi bi-send-fill"></i>
					发布
				</button>
				<!-- PUBLISHED, TRASH 转化为草稿 -->
				<button
					v-if="remoteArticleInfo?.status !== 'DRAFT'"
					class="btn btn-light me-md-2"
					type="button"
					@click="resetArticleStatus('DRAFT')"
				>
					<i class="bi bi-send-slash-fill"></i>
					还原
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
										:class="getStatusColorClass(remoteArticleInfo?.status)"
									>
										{{ remoteArticleInfo?.status }}
									</span>
								</div>
							</div>
						</li>
						<li class="list-group-item">
							<div class="row">
								<div class="col-4 p-0">作者</div>
								<div class="col-8 p-0">{{ remoteArticleInfo?.penName }}</div>
							</div>
						</li>
						<li class="list-group-item">
							<div class="row">
								<div class="col-4 p-0">创建日期</div>
								<div class="col-8 p-0">
									{{ getFormarttedDate(remoteArticleInfo?.creationDatetime) }}
								</div>
							</div>
						</li>
						<li class="list-group-item">
							<div class="row">
								<div class="col-4 p-0">更新日期</div>
								<div class="col-8 p-0">
									{{ getFormarttedDate(remoteArticleInfo?.lastUpdatedDatetime) }}
								</div>
							</div>
						</li>
						<li
							class="list-group-item"
							v-if="remoteArticleInfo?.status === 'PUBLISHED'"
						>
							<div class="row">
								<div class="col-4 p-0">发布日期</div>
								<div class="col-8 p-0">
									{{ getFormarttedDate(remoteArticleInfo?.publishDatetime) }}
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<!-- 摘要组件 -->
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

			<!-- 标签组件 -->
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

			<!-- 系列组件 -->
			<div class="tile">
				<div class="tile-title">系列</div>
				<div class="title-body"></div>
			</div>

			<!-- SLUG 组件 -->
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

	<!-- 发布文章 modal -->
	<ModalComponent
		title="发布文章"
		save-button-text="发布"
		ref="publishModal"
		@save-change="
			() => {
				resetArticleStatus('PUBLISHED');
				publishModal.hide();
			}
		"
	>
		<template #body>
			<p>发布文章《{{ title.value }}》之后所有人都可以看到，确定要发布吗？</p>
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

	// 全局
	onMounted(() => {
		// 加载编辑器
		initBodyEditor();

		// 加载数据
		loadArticleInfo();
	});

	const publishModal = ref();
	const remoteArticleInfo = ref<ArticleInfo | null>(null);

	const loadArticleInfo = async () => {
		// 加载 info
		const articleInfoResponse: APIResponse<ArticleInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/info/' + route.params.id
		);
		if (articleInfoResponse.code !== 0) {
			return console.error(articleInfoResponse.message);
		}
		remoteArticleInfo.value = articleInfoResponse.data;

		// 加载 title, summary, slug
		title.value = remoteArticleInfo.value.title;
		summary.value = remoteArticleInfo.value.summary;
		slug.value = remoteArticleInfo.value.slug;

		// 加载 body
		const bodyResponse: APIResponse<string> = await makeRequest(
			remoteArticleInfo.value.bodyValueLocation
		);
		if (bodyResponse.code !== 0) {
			return console.error(bodyResponse.message);
		}
		remoteBody.value = bodyResponse.data;
		body.value = remoteBody.value;
		bodyEditor.value?.commands.setContent(body.value);

		// 加载 tags
		const tagResponse: APIResponse<TagInfo[]> = await makeRequest(
			remoteArticleInfo.value.tagInfoPageLocation
		);
		if (tagResponse.code !== 0) {
			return console.error(tagResponse.message);
		}
		tags.value = tagResponse.data;
	};
	const resetArticleStatus = async (targetStatus: string) => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/status/' + route.params.id + '/' + targetStatus,
			{
				method: 'PATCH',
			}
		);
		if (response.code === 0) {
			if (remoteArticleInfo.value) {
				remoteArticleInfo.value.status = targetStatus.toUpperCase();
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
	const openPublishModal = () => {
		if (publishModal.value) {
			publishModal.value.show();
		}
	};

	// 编辑器信息栏组件
	const titleTextAreaRef = ref<HTMLTextAreaElement>();
	const titleRow = ref(1);
	const bodyEditor = ref<Editor>();
	const remoteBody = ref('');
	const body = reactive({
		value: '',
		changed: false,
	});
	const title = reactive({
		value: '',
		changed: false,
	});

	const initBodyEditor = () => {
		bodyEditor.value = new Editor({
			extensions: [
				Illustration,
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
		bodyEditor.value?.on('update', updateHtmlContent);
		body.value = bodyEditor.value.getHTML();
	};

	const updateHtmlContent = () => {
		if (bodyEditor.value) {
			body.value = bodyEditor.value?.getHTML();
		}
	};

	const updateArticleContent = async () => {
		console.log(title.changed);
		console.log(body.changed);
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/content/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					title: title.value,
					body: body.value,
				}),
			}
		);
		if (response.code !== 0) {
			showMessage('更新失败', AlertType.ERROR);
			return console.error(response.message);
		}
		showMessage('更新成功', AlertType.SUCCESS);
		body.changed = false;
		title.changed = false;
	};

	const contentChanged = computed(() => {
		return body.changed || title.changed;
	});

	watch(
		() => title.value,
		newValue => {
			title.changed = newValue !== remoteArticleInfo.value?.title;
			if (titleTextAreaRef.value) {
				if (titleTextAreaRef.value.scrollHeight > titleTextAreaRef.value.clientHeight) {
					titleRow.value += 1;
				}
			}
		}
	);

	watch(
		() => body.value,
		newValue => {
			body.changed = newValue !== remoteBody.value;
		}
	);
	// summary 组件
	const summary = ref('');

	// tag 组件
	const tags = ref<TagInfo[]>([]);

	// slug 组件
	const slug = ref('');

	// 其他

	const updateSlugModal = ref();
	const newTagName = ref('');

	const article = reactive({
		title: '',
		body: '',
		summary: '',
		slug: '',
	});

	const changed = reactive({
		title: false,
		body: false,
		summary: false,
		slug: false,
	});

	const articleInfo = ref<ArticleInfo | null>(null);
	const userInfo = ref<UserInfo | null>(null);

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
	const openUpdateSlugModal = () => {
		if (updateSlugModal.value) {
			updateSlugModal.value.show();
		}
	};

	onMounted(() => {
		initBodyEditor();
	});

	const contentSync = computed(() => {
		return !changed.body && !changed.title;
	});

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
