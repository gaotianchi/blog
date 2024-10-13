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

	<div class="row col-lg-11 m-auto overflow-auto">
		<!-- 编辑器主体 -->
		<div class="col-md-8 tile overflow-y-auto" style="max-height: 150vh">
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
				<!-- 段首工具栏 -->
				<FloatingMenu
					class="floating-menu"
					:tippy-options="{ duration: 100, placement: 'auto-end' }"
					:editor="bodyEditor"
				>
					<div
						class="btn-group btn-group-sm bg-white"
						role="group"
						aria-label="Small button group"
					>
						<!-- 标题下拉菜单按钮 -->
						<div class="btn-group">
							<button
								@click="setHeading(2)"
								type="button"
								class="btn btn-outline-dark"
							>
								H2
							</button>
							<button
								type="button"
								class="btn btn-outline-dark dropdown-toggle dropdown-toggle-split"
								data-bs-toggle="dropdown"
								aria-expanded="false"
							>
								<span class="visually-hidden">Toggle Dropdown</span>
							</button>
							<ul
								class="dropdown-menu"
								style="min-width: min-content; background-color: transparent"
							>
								<div
									class="btn-group-vertical btn-group-sm"
									role="group"
									aria-label="Small button group"
								>
									<button
										@click="setHeading(3)"
										type="button"
										class="btn btn-outline-dark"
									>
										H3
									</button>
									<button
										@click="setHeading(4)"
										type="button"
										class="btn btn-outline-dark"
									>
										H4
									</button>
									<button
										@click="setHeading(5)"
										type="button"
										class="btn btn-outline-dark"
									>
										H5
									</button>
								</div>
							</ul>
						</div>

						<!-- 插图按钮 -->
						<button @click="openillustrationEditorModal" class="btn btn-outline-dark">
							<i class="bi bi-image"></i>
						</button>
					</div>
				</FloatingMenu>

				<!-- 常规选中文本工具栏 -->
				<BubbleMenu
					plugin-key="global"
					:editor="bodyEditor"
					:tippy-options="{ duration: 100 }"
					:should-show="
						p => {
							return p.to - p.from > 0 && !p.editor.isActive('illustration');
						}
					"
				>
					<div
						class="btn-group btn-group-sm bg-white"
						role="group"
						aria-label="Small button group"
					>
						<!-- 加粗字体 -->
						<button
							@click="bodyEditor.chain().focus().toggleBold().run()"
							type="button"
							class="btn btn-outline-dark"
						>
							<strong>B</strong>
						</button>
						<!-- 链接 -->
						<button @click="toggleLinkNode" type="button" class="btn btn-outline-dark">
							<i class="bi bi-link-45deg"></i>
						</button>
						<!-- 其他工具 -->
						<button
							type="button"
							class="btn btn-outline-dark dropdown-toggle dropdown-toggle-split"
							data-bs-toggle="dropdown"
							aria-expanded="false"
						>
							<span class="visually-hidden">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu bg-white" style="min-width: min-content">
							<div
								class="btn-group-vertical btn-group-sm"
								role="group"
								aria-label="Small button group"
							>
								<!-- 斜体字 -->
								<button
									@click="bodyEditor.chain().focus().toggleItalic().run()"
									type="button"
									class="btn btn-outline-dark"
								>
									<em>I</em>
								</button>
								<button @click="" type="button" class="btn btn-outline-dark">
									其他
								</button>
							</div>
						</ul>
					</div>
				</BubbleMenu>

				<!-- link 编辑面板 -->
				<BubbleMenu
					plugin-key="linkEditorPanel"
					:editor="bodyEditor"
					:tippy-options="{ duration: 100 }"
					:should-show="
						p => {
							const linkNode = p.editor.getAttributes('link');
							link.href = linkNode.href;
							link.openInNewTab = linkNode.target === '_blank' ? true : false;
							return p.editor.isActive('link');
						}
					"
				>
					<div class="card" style="width: 18rem">
						<div class="card-body">
							<div class="form-floating mb-2">
								<input
									type="text"
									class="form-control"
									id="floatingLinkEditor"
									placeholder="链接"
									v-model="link.href"
								/>
								<label for="floatingLinkEditor">编辑链接</label>
							</div>
							<div class="row align-items-center">
								<div class="col">
									<div class="form-check form-switch">
										<input
											class="form-check-input"
											type="checkbox"
											role="switch"
											id="flexSwitchCheckChecked"
											v-model="link.openInNewTab"
										/>
										<label
											class="form-check-label"
											for="flexSwitchCheckChecked"
										>
											新标签
										</label>
									</div>
								</div>
								<div class="col">
									<a href="#" @click="unsetLink" class="btn btn-link">取消</a>
									<a href="#" @click="updateLink" class="btn btn-link">保存</a>
								</div>
							</div>
						</div>
					</div>
				</BubbleMenu>

				<!-- illustration 编辑面板 -->
				<BubbleMenu
					:editor="bodyEditor"
					plugin-key="illustration"
					:tippy-options="{ duration: 100 }"
					:should-show="
						p => {
							loadCurrentIllustrationData();
							return p.editor.isActive('illustration');
						}
					"
				>
					<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
						<div class="btn-group me-2 bg-white" role="group" aria-label="Second group">
							<button
								@click="openillustrationEditorModal"
								type="button"
								class="btn btn-outline-secondary"
							>
								<i class="bi bi-pencil-fill"></i>
							</button>
						</div>
					</div>
				</BubbleMenu>

				<!-- 正文编辑器 -->
				<EditorContent :editor="bodyEditor" />
			</div>
			<!-- <div v-html="body.value"></div> -->
		</div>

		<!-- 右侧边栏 -->
		<div class="col-12 col-md-4">
			<!-- 操作按钮区域 -->
			<div class="tile">
				<!-- 更新按钮 -->
				<button
					v-if="remoteArticleInfo?.status !== 'TRASH'"
					class="btn btn-primary me-md-2 mb-2"
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
					class="btn btn-success me-md-2 mb-2"
					type="button"
					@click="openPublishModal"
				>
					<i class="bi bi-send-fill"></i>
					发布
				</button>
				<!-- PUBLISHED, TRASH 转化为草稿 -->
				<button
					v-if="remoteArticleInfo?.status !== 'DRAFT'"
					class="btn btn-light me-md-2 mb-2"
					type="button"
					@click="resetArticleStatus('DRAFT')"
				>
					<i class="bi bi-send-slash-fill"></i>
					还原
				</button>
				<!-- DRAFT, PUBLISHED 转化为 TRASH -->
				<button
					v-if="remoteArticleInfo?.status !== 'TRASH'"
					class="btn btn-dark me-md-2 mb-2"
					type="button"
					@click="resetArticleStatus('TRASH')"
				>
					<i class="bi bi-trash-fill"></i>
					垃圾
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
										:class="getArtcicleStatusClass(remoteArticleInfo?.status)"
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
						v-model="summary.value"
					></textarea>
				</div>
				<div class="tile-footer" v-if="summary.changed">
					<div class="row justify-content-end">
						<button
							@click="
								summary.value = remoteArticleInfo ? remoteArticleInfo.summary : ''
							"
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
								@click="removeTag(tag)"
								aria-label="Remove"
							></button>
						</div>
					</div>
					<div>
						<input
							type="text"
							class="form-control"
							v-model="newTagName"
							@keyup.enter="addTag"
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
							v-model="slug.value"
						/>
					</div>
				</div>
				<div class="tile-footer" v-if="slug.changed">
					<div class="row justify-content-end">
						<button
							@click="slug.value = remoteArticleInfo ? remoteArticleInfo.slug : ''"
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

	<!-- 更新 SLUG -->
	<ModalComponent title="更新文章固定链接" ref="updateSlugModal" @save-change="updateSlug">
		<template #body>
			<p>确定要将该文章的链接更改为：</p>
			<p>{{ 'http://localhost:8090/' + route.params.id + '/' + slug.value }}</p>
			<p>吗？</p>
		</template>
	</ModalComponent>

	<!-- 编辑 illustration modal -->
	<ModalComponent
		title="编辑插图"
		ref="illustrationEditorModal"
		@save-change="updateIllustration"
		modal-width="modal-xl"
	>
		<template #body>
			<div class="row">
				<div class="col-6">
					<img
						:src="previewUrl || illustration.src || 'https://placehold.co/400x400'"
						class="img-fluid card-img"
						:alt="illustration.alt"
						:title="illustration.title"
					/>
				</div>
				<div class="col-6">
					<!-- 选择并预览本地图片 -->
					<div class="input-group mb-3">
						<button class="btn btn-outline-secondary" type="button">从云端选择</button>
						<input
							@change="handleInputChange"
							ref="inputIllustrationRef"
							type="file"
							class="form-control"
							id="uploadIllustration"
							aria-describedby="inputGroupFileAddon03"
							aria-label="Upload"
							placeholder="从本地选择"
							accept="image/*"
						/>
					</div>
					<!-- title 标题编辑区 -->
					<div class="form-floating mb-3">
						<input
							type="text"
							class="form-control"
							id="illustration.titleInput"
							placeholder="标题"
							v-model="illustration.title"
						/>
						<label for="illustration.titleInput">标题</label>
					</div>
					<!-- alt 编辑器 -->
					<div class="form-floating mb-3">
						<textarea
							type="text"
							class="form-control"
							id="illustration.altInput"
							placeholder="描述"
							style="min-height: 100px"
							v-model="illustration.alt"
						></textarea>
						<label for="illustration.altInput">描述</label>
					</div>
					<!-- align 编辑器 -->
					<div class="mb-3 text-center">
						<div
							class="btn-group bg-white"
							role="group"
							aria-label="Basic radio toggle button group"
						>
							<input
								type="radio"
								class="btn-check"
								:id="id + 'btnradio1'"
								autocomplete="off"
								v-model="illustrationAlign"
								value="float-start"
							/>
							<label class="btn btn-outline-secondary" :for="id + 'btnradio1'">
								<i class="bi bi-text-left"></i>
							</label>
							<input
								type="radio"
								class="btn-check"
								:id="id + 'btnradio3'"
								autocomplete="off"
								v-model="illustrationAlign"
								value="float-end"
							/>
							<label class="btn btn-outline-secondary" :for="id + 'btnradio3'">
								<i class="bi bi-text-right"></i>
							</label>
						</div>
					</div>
				</div>
			</div>
		</template>
	</ModalComponent>
</template>
<script setup lang="ts">
	import { ref, onMounted, watch, reactive, computed, nextTick } from 'vue';
	import StarterKit from '@tiptap/starter-kit';
	import { Editor, EditorContent, FloatingMenu, BubbleMenu } from '@tiptap/vue-3';
	import Placeholder from '@tiptap/extension-placeholder';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import { makeRequest } from '@/service/request.service';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { useRoute } from 'vue-router';
	import type { APIResponse, ArticleInfo, IllustrationInfo, TagInfo } from '@/type/response.type';
	import { getArtcicleStatusClass, getFormarttedDate } from '@/utlis';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import ModalComponent from '@/component/ModalComponent.vue';
	import Link from '@tiptap/extension-link';
	import { Illustration } from '@/component/editor.component/extension/Illustration';
	import type { IllustrationFloat } from '@/type/utlis';
	import { v4 as uuidv4 } from 'uuid';

	// 全局
	const id = uuidv4();
	const route = useRoute();
	const publishModal = ref();
	const remoteArticleInfo = ref<ArticleInfo | null>(null);
	onMounted(async () => {
		// 加载编辑器
		initBodyEditor();

		// 加载数据
		await loadArticleData();

		nextTick(() => {
			const tiptap = document.getElementsByClassName('tiptap')[0];
			console.log(tiptap.scrollHeight);
			console.log(document.body.scrollHeight);
			window.scrollTo(0, document.body.scrollHeight);
			window.dispatchEvent(new Event('resize'));
		});
	});
	const loadArticleData = async () => {
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

	// 操作区域
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
	const updateArticleContent = async () => {
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
		// 更新与文章关联的插图
		const illustrationIds = getAllIllustrationIds();
		console.log(illustrationIds);
		const params = new URLSearchParams();
		illustrationIds.forEach(id => params.append('illustrationIds', id.toString()));
		const resetArticleIllustration: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL +
				'/articles/illustrations/' +
				route.params.id +
				'?' +
				params.toString(),
			{
				method: 'POST',
			}
		);
		if (resetArticleIllustration.code !== 0) {
			return showMessage('插图更新失败', AlertType.ERROR);
		}
	};

	// 编辑器
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
	const contentChanged = computed(() => {
		return body.changed || title.changed;
	});
	const initBodyEditor = () => {
		bodyEditor.value = new Editor({
			extensions: [
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
				Link.configure({
					openOnClick: false,
					defaultProtocol: 'https',
				}),
				Illustration,
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
	// 编辑器拓展部分
	// Heading
	const setHeading = (level: number) => {
		if (bodyEditor.value) {
			bodyEditor.value
				.chain()
				.focus()
				.toggleHeading({ level: level as 2 | 3 | 4 | 4 })
				.run();
		}
	};
	// Link
	const link = reactive({
		href: '',
		openInNewTab: false,
	});
	const toggleLinkNode = () => {
		if (bodyEditor.value) {
			if (bodyEditor.value?.isActive('link')) {
				bodyEditor.value.chain().focus().extendMarkRange('link').unsetLink().run();
			} else {
				bodyEditor.value
					.chain()
					.focus()
					.extendMarkRange('link')
					.toggleLink({ href: '#' })
					.run();
			}
		}
	};
	const updateLink = () => {
		const target = link.openInNewTab ? '_blank' : '_self';
		bodyEditor.value
			?.chain()
			.extendMarkRange('link')
			.unsetLink()
			.setLink({ href: link.href, target: target })
			.run();
	};
	const unsetLink = () => {
		if (bodyEditor.value) {
			bodyEditor.value.chain().focus().extendMarkRange('link').unsetLink().run();
		}
	};

	// Illustration
	const previewUrl = ref<string | null>(null);
	const illustrationEditorModal = ref();
	const inputIllustrationRef = ref();
	const selectedLocalImage = ref<File | null>(null);
	const illustrationAlign = ref<IllustrationFloat>('float-start');
	const illustration = reactive({
		id: 0,
		src: '',
		alt: '',
		title: '',
	});
	const loadCurrentIllustrationData = () => {
		const illustrationExtension = bodyEditor.value?.getAttributes('illustration');
		illustration.title = illustrationExtension?.title;
		illustrationAlign.value = illustrationExtension?.align;
		illustration.alt = illustrationExtension?.alt;
		illustration.src = illustrationExtension?.src;
		illustration.id = illustrationExtension?.id;
	};
	const resetIllustrationValue = () => {
		illustration.src = '';
		illustration.alt = '';
		illustration.title = '';
	};
	const openillustrationEditorModal = () => {
		if (illustrationEditorModal.value) {
			loadCurrentIllustrationData();
			illustrationEditorModal.value.show();
			if (!illustrationAlign.value) {
				illustrationAlign.value = 'float-start';
			}
		}
	};
	const handleInputChange = () => {
		if (inputIllustrationRef.value) {
			const files = inputIllustrationRef.value.files;
			if (files?.length) {
				const currentFile = files[0];
				selectedLocalImage.value = currentFile;
				const reader = new FileReader();
				reader.onload = () => {
					previewUrl.value = reader.result as string;
				};
				reader.readAsDataURL(currentFile);
			}
		}
	};
	const updateIllustration = async () => {
		// 检查是否更新了图片文件，如果是则更新插图图片
		if (selectedLocalImage.value) {
			const formData = new FormData();
			formData.append('file', selectedLocalImage.value);
			formData.append('title', illustration.title);
			formData.append('alt', illustration.alt);
			const newIllustrationResponse: APIResponse<IllustrationInfo> = await makeRequest(
				RESOURCE_BASE_URL + '/illustrations/new',
				{
					method: 'POST',
					body: formData,
				}
			);
			if (newIllustrationResponse.code !== 0) {
				showMessage('图片上传失败', AlertType.ERROR);
				return console.error(newIllustrationResponse.message);
			}
			illustration.src = newIllustrationResponse.data.url;
			illustration.id = newIllustrationResponse.data.id;
			selectedLocalImage.value = null;
		}
		if (!illustration.src.trim()) {
			illustrationEditorModal.value.hide();
			return;
		}

		const extension = bodyEditor.value?.getAttributes('illustration');
		if (extension && extension.id) {
			// 检查是否更新了标题和 alt
			if (extension.title !== illustration.title || extension.alt !== illustration.alt) {
				const updateIllustrationInfoResponse: APIResponse<void> = await makeRequest(
					RESOURCE_BASE_URL + '/illustrations/info/' + extension.id,
					{
						method: 'PATCH',
						body: JSON.stringify({
							title: illustration.title,
							alt: illustration.alt,
						}),
					}
				);
				if (updateIllustrationInfoResponse.code !== 0) {
					return showMessage('插图信息更新失败', AlertType.ERROR);
				}
				showMessage('更新成功', AlertType.SUCCESS);
			}
		}

		bodyEditor.value
			?.chain()
			.focus()
			.setIllustration({
				...illustration,
				align: illustrationAlign.value,
			})
			.run();
		illustrationEditorModal.value.hide();
		resetIllustrationValue();
	};
	function getAllIllustrationIds(): number[] {
		const illustrationImages: NodeListOf<HTMLImageElement> =
			document.querySelectorAll('.illustration-img');
		const ids: number[] = [];
		illustrationImages.forEach(image => {
			const id = image.getAttribute('data-id');
			if (id) {
				ids.push(Number(id));
			}
		});
		return ids;
	}

	// summary 组件
	const summary = reactive({
		value: '',
		changed: false,
	});
	const updateSummary = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/summary/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					summary: summary.value,
				}),
			}
		);
		if (response.code !== 0) {
			showMessage('更新失败', AlertType.ERROR);
			return console.error(response.message);
		}
		showMessage('更新成功', AlertType.SUCCESS);
		summary.changed = false;
	};
	watch(
		() => summary.value,
		newValue => {
			summary.changed = newValue !== remoteArticleInfo.value?.summary;
		}
	);

	// tag 组件
	const tags = ref<TagInfo[]>([]);
	const newTagName = ref('');
	const addTag = async () => {
		if (!newTagName.value.trim()) {
			return showMessage('请输入标签名');
		}
		if (tags.value.some(tag => tag.name == newTagName.value)) {
			newTagName.value = '';
			return showMessage('标签名已经添加');
		}
		const tagInfoResponse: APIResponse<TagInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/tags/new/' + newTagName.value,
			{
				method: 'POST',
			}
		);
		if (tagInfoResponse.code !== 0) {
			showMessage('标签创建失败', AlertType.ERROR);
			return console.error(tagInfoResponse.message);
		}
		const addTagResponse: APIResponse<TagInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/tag/' + route.params.id + '/' + tagInfoResponse.data.id,
			{
				method: 'POST',
			}
		);
		if (addTagResponse.code !== 0) {
			showMessage('标签添加失败', AlertType.ERROR);
			return console.error(addTagResponse.message);
		}
		tags.value.push(tagInfoResponse.data);
		newTagName.value = '';
	};
	const removeTag = async (tag: TagInfo) => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/tag/' + route.params.id + '/' + tag.id,
			{
				method: 'DELETE',
			}
		);
		if (response.code !== 0) {
			showMessage('标签移除失败', AlertType.ERROR);
			return console.error(response.message);
		}
		tags.value = tags.value.filter(t => t.id !== tag.id);
	};

	// slug 组件
	const slug = reactive({
		value: '',
		changed: false,
	});
	const updateSlugModal = ref();
	const updateSlug = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/slug/' + route.params.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					slug: slug.value,
				}),
			}
		);
		if (response.code !== 0) {
			showMessage('更新失败', AlertType.ERROR);
			return console.error(response.message);
		}
		if (remoteArticleInfo.value) {
			remoteArticleInfo.value.slug = slug.value;
			slug.changed = false;
			showMessage('更新成功', AlertType.SUCCESS);
		}
		updateSlugModal.value.hide();
	};
	const openUpdateSlugModal = () => {
		if (updateSlugModal.value) {
			updateSlugModal.value.show();
		}
	};
	watch(
		() => slug.value,
		newValue => {
			slug.changed = newValue !== remoteArticleInfo.value?.slug;
		}
	);
</script>

<style>
	html {
		overflow-y: scroll; /* 强制总是显示滚动条 */
	}
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
