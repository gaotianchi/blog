<template>
	<MainPageHeaderComponent
		title="文章管理页面"
		sub-title="在这里可以删除、添加文章以及更改文章的部分属性"
		bs-icon-name="bi-file-text"
		:breadcrumb-items="[{ name: '文章管理', routeName: 'ARTICLES' }]"
	></MainPageHeaderComponent>
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
						v-model="filterStatus"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio1">全部</label>
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio2"
						autocomplete="off"
						value="published"
						v-model="filterStatus"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio2">已发布</label>
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio3"
						autocomplete="off"
						value="draft"
						v-model="filterStatus"
					/>
					<label class="btn btn-outline-dark" for="vbtn-radio3">草稿</label>
					<input
						type="radio"
						class="btn-check"
						name="vbtn-radio"
						id="vbtn-radio4"
						autocomplete="off"
						value="trash"
						v-model="filterStatus"
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
	<div
		class="tile position-relative"
		v-for="(article, index) in articleInfoList"
		@mouseenter="activeTile(index)"
		@mouseleave="activeTile(null)"
		:key="index"
	>
		<div class="row">
			<div class="col-auto">
				<div class="image-container" style="height: 180px; width: 180px; overflow: hidden">
					<RouterLink :to="{ name: 'ARTICLE_EDITOR', params: { id: article.id } }">
						<img
							style="width: 100%; height: 100%"
							class="rounded object-fit-cover"
							:src="article.coverUrl || 'https://via.assets.so/img.jpg?w=300&h=150'"
							:alt="''"
						/>
					</RouterLink>
				</div>
			</div>
			<div class="col text-truncate">
				<RouterLink :to="{ name: 'ARTICLE_EDITOR', params: { id: article.id } }">
					<span class="h4">
						{{
							article.title ||
							'Lorem Ipsum，也称乱数假文或者哑元文本， 是印刷及排版领域所常用的虚拟文字。'
						}}
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
						<li class="list-group-item" v-if="article.seriesName">
							<div class="row">
								<div class="col-1">系列</div>
								<div class="col-10">系列名称</div>
								<div class="col-1"></div>
							</div>
						</li>
						<li class="list-group-item">
							<div class="row">
								<div class="col-1">Slug</div>
								<div class="col-10">{{ article.slug }}</div>
								<div class="col-1"></div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div
			v-if="activedTile === index"
			class="position-absolute"
			style="bottom: 1rem; right: 1rem"
		>
			<button v-if="article.status === 'TRASH'" type="button" class="btn btn-danger">
				彻底删除
			</button>
		</div>
	</div>

	<PaginationComponent
		:key="filterStatus"
		:total-page="totalPage"
		@page-changed="page => handlePageChanged(page)"
	/>
</template>
<script setup lang="ts">
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { makeRequest } from '@/service/request.service';
	import { onMounted, ref, watch } from 'vue';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import Segmentation from '@/component/icon/Segmentation.vue';
	import type {
		APIResponse,
		ArticleInfo,
		ArticleResponse,
		PageInfo,
		UserInfo,
	} from '@/type/response.type';
	import { ArticleStatus } from '@/enum';
	import { useRouter } from 'vue-router';
	import PaginationComponent from '@/component/article.component/PaginationComponent.vue';
	import { getFormarttedDate } from '@/utlis';

	const router = useRouter();

	const articleInfoList = ref<ArticleInfo[]>([]);
	const totalPage = ref(0);
	const user = ref<UserInfo | null>(null);
	const activedTile = ref<number | null>(null);
	const filterStatus = ref<'all' | 'published' | 'trash' | 'draft'>('all');

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
		if (pageArticleInfo.code === 0) {
			articleInfoList.value = pageArticleInfo.data.items;
			totalPage.value = pageArticleInfo.data.totalPage;
		}
	};

	const activeTile = (index: number | null) => {
		activedTile.value = index;
	};

	const handleNewArticleButton = async () => {
		const response: APIResponse<ArticleResponse> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/new',
			{
				method: 'POST',
			}
		);
		console.log(response);
		router.push({
			name: 'ARTICLE_EDITOR',
			params: {
				id: response.data.id,
			},
		});
	};

	const getArtcicleStatusClass = (articleStatus: string) => {
		switch (articleStatus) {
			case 'PUBLISHED':
				return ArticleStatus.PUBLISHED;
			case 'DRAFT':
				return ArticleStatus.DRAFT;
			default:
				return ArticleStatus.TRASH;
		}
	};

	const handlePageChanged = (page: number) => {
		loadPageArticles(page, filterStatus.value);
	};

	onMounted(async () => {
		const userInfoResponse: APIResponse<UserInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/users/info'
		);
		if (userInfoResponse.code === 0) {
			user.value = userInfoResponse.data;
			loadPageArticles(0, 'all');
		}
	});

	watch(filterStatus, newValue => {
		console.log(newValue);
		handlePageChanged(0);
	});
</script>
