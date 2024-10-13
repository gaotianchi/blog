<template>
	<MainPageHeaderComponent
		title="文章管理页面"
		sub-title="在这里可以删除、添加文章以及更改文章的部分属性"
		bs-icon-name="bi-file-text"
		:breadcrumb-items="[{ name: '文章管理', routeName: 'ARTICLES' }]"
	></MainPageHeaderComponent>

	<!-- 操作区域 -->
	<div class="tile">
		<div class="row justify-content-between">
			<div class="col-auto">
				<div class="btn-group" role="group" aria-label="radio toggle button group">
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio1"
						autocomplete="off"
						value="all"
						v-model="filter"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio1">全部</label>
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio2"
						autocomplete="off"
						value="published"
						v-model="filter"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio2">已发布</label>
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio3"
						autocomplete="off"
						value="draft"
						v-model="filter"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio3">草稿</label>
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio4"
						autocomplete="off"
						value="trash"
						v-model="filter"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio4">垃圾箱</label>
				</div>
			</div>
			<div class="col-auto">
				<button
					@click="handleNewArticleButton"
					type="button"
					class="btn btn-primary d-flex align-items-center"
				>
					<i class="bi bi-plus-square"></i>
					<span style="padding-left: 0.5rem">新建文章</span>
				</button>
			</div>
		</div>
	</div>

	<!-- 文章卡片列表 -->
	<div
		class="tile position-relative"
		v-for="(article, index) in articleInfoList"
		@mouseenter="activeTile(index)"
		@mouseleave="activeTile(null)"
		:key="index"
	>
		<div class="row">
			<!-- 封面 -->
			<div class="col-auto">
				<div class="image-container" style="height: 180px; width: 180px; overflow: hidden">
					<RouterLink :to="{ name: 'ARTICLE_EDITOR', params: { id: article.id } }">
						<img
							style="width: 100%; height: 100%"
							class="rounded object-fit-cover"
							:src="article.coverUrl || 'https://via.assets.so/img.jpg?w=180&h=180'"
							:alt="''"
						/>
					</RouterLink>
				</div>
			</div>

			<!-- 主信息栏 -->
			<div class="col text-truncate">
				<RouterLink :to="{ name: 'ARTICLE_EDITOR', params: { id: article.id } }">
					<span class="h4">
						{{ article.title || '未命名' }}
					</span>
				</RouterLink>

				<div class="row mt-2 mb-3">
					<div class="col-auto d-flex align-items-center">
						<span class="badge" :class="getArtcicleStatusClass(article.status)">
							{{ article.status }}
						</span>
						<Segmentation />
						<span class="text-secondary">
							{{ getFormarttedDate(article.creationDatetime) }}
						</span>
						<Segmentation />
						<span class="text-secondary">{{ article.penName }}</span>
					</div>
				</div>

				<div class="row">
					<ul class="list-group list-group-flush">
						<!-- slug -->
						<li class="list-group-item">
							<div class="row">
								<div class="col-1">固定链接</div>
								<div class="col-10">
									<RouterLink :to="{ name: '' }">
										{{
											'http://localhost:8080/' +
											article.id +
											'/' +
											article.slug
										}}
									</RouterLink>
								</div>
								<div class="col-1"></div>
							</div>
						</li>
						<!-- 标签 -->
						<li class="list-group-item" v-if="article.tagNames.length > 0">
							<div class="row">
								<div class="col-1">标签</div>
								<div class="col-10">
									<span
										v-for="(tag, index) in article.tagNames"
										class="badge rounded-pill text-bg-secondary me-2"
										:key="index"
									>
										{{ tag }}
									</span>
								</div>
								<div class="col-1"></div>
							</div>
						</li>
						<!-- 系列 -->
						<li class="list-group-item" v-if="article.seriesName">
							<div class="row">
								<div class="col-1">系列</div>
								<div class="col-10">{{ article.seriesName }}</div>
								<div class="col-1"></div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 卡片操作区域 -->
		<div
			v-if="activedTile === index"
			class="position-absolute"
			style="bottom: 1rem; right: 1rem"
		>
			<div class="btn-group" role="group" aria-label="Basic mixed styles example">
				<button
					@click="openDeleteArticleModal(article)"
					v-if="article.status === 'TRASH'"
					type="button"
					class="btn btn-danger"
				>
					彻底删除
				</button>
			</div>
		</div>
	</div>

	<!-- 删除文章 modal -->
	<ModalComponent
		title="删除文章"
		save-button-text="删除"
		ref="deleteArticleModal"
		@save-change="deleteArticle"
	>
		<template #body>
			<p>确定要彻底删除文章《{{ articleToDelete?.title || '未命名' }}》吗？</p>
		</template>
	</ModalComponent>

	<!-- 分页组件 -->
	<PaginationComponent
		v-if="totalPage > 1"
		:key="filter"
		:total-page="totalPage"
		@page-changed="page => handlePageChanged(page)"
	/>
	<div v-if="articleInfoList.length == 0" class="text-center">没有文章</div>
</template>
<script setup lang="ts">
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { makeRequest } from '@/service/request.service';
	import { onMounted, ref, watch } from 'vue';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import Segmentation from '@/component/icon/Segmentation.vue';
	import type { APIResponse, ArticleInfo, PageInfo, UserInfo } from '@/type/response.type';
	import { AlertType } from '@/enum';
	import { useRouter } from 'vue-router';
	import PaginationComponent from '@/component/PaginationComponent.vue';
	import { getFormarttedDate, getArtcicleStatusClass } from '@/utlis';
	import showMessage from '@/service/alert.service';
	import ModalComponent from '@/component/ModalComponent.vue';

	// 全局
	const router = useRouter();
	const totalPage = ref(0);
	const activedTile = ref<number | null>(null);
	const user = ref<UserInfo | null>(null);
	onMounted(async () => {
		const userInfoResponse: APIResponse<UserInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/users/info'
		);
		if (userInfoResponse.code !== 0) {
			return console.error(userInfoResponse.message);
		}
		user.value = userInfoResponse.data;
		loadPageArticles(0, 'all');
	});
	const loadPageArticles = async (page: number, status: string) => {
		let pageArticleInfo: APIResponse<PageInfo<ArticleInfo>>;
		if (status === 'all') {
			pageArticleInfo = await makeRequest(
				RESOURCE_BASE_URL + '/articles/user/' + user.value?.id + '?page=' + page
			);
		} else {
			pageArticleInfo = await makeRequest(
				RESOURCE_BASE_URL +
					'/articles/user/' +
					user.value?.id +
					'?page=' +
					page +
					'&status=' +
					status
			);
		}
		if (pageArticleInfo.code !== 0) {
			showMessage('文章加载失败', AlertType.ERROR);
			return console.error(pageArticleInfo.message);
		}
		articleInfoList.value = pageArticleInfo.data.items;
		totalPage.value = pageArticleInfo.data.totalPage;
	};
	const handlePageChanged = (page: number) => {
		loadPageArticles(page, filter.value);
	};
	const activeTile = (index: number | null) => {
		activedTile.value = index;
	};

	// 操作区域组件
	const filter = ref<'all' | 'published' | 'trash' | 'draft'>('all');
	const handleNewArticleButton = async () => {
		const response: APIResponse<ArticleInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/new',
			{
				method: 'POST',
			}
		);
		if (response.code !== 0) {
			showMessage('文章创建失败', AlertType.ERROR);
			return console.error(response.message);
		}
		router.push({
			name: 'ARTICLE_EDITOR',
			params: {
				id: response.data.id,
			},
		});
	};
	watch(filter, () => {
		handlePageChanged(0);
	});

	// 卡片列表
	const articleInfoList = ref<ArticleInfo[]>([]);
	const deleteArticleModal = ref();
	const articleToDelete = ref<ArticleInfo | null>(null);
	const deleteArticle = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/delete/' + articleToDelete.value?.id,
			{
				method: 'DELETE',
			}
		);
		if (response.code !== 0) {
			showMessage('删除失败', AlertType.ERROR);
			return console.error(response.message);
		}
		showMessage('成功删除文章', AlertType.SUCCESS);
		articleInfoList.value = articleInfoList.value.filter(
			article => article.id !== articleToDelete.value?.id
		);
		deleteArticleModal.value.hide();
		articleToDelete.value = null;
	};
	const openDeleteArticleModal = (article: ArticleInfo) => {
		if (deleteArticleModal.value) {
			deleteArticleModal.value.show();
			articleToDelete.value = article;
		}
	};

	// 其他
</script>
