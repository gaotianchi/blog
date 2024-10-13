<template>
	<!-- 所有文章插图 -->
	<div class="tile">
		<div class="tile-title">所有文章图片</div>
		<div class="tile-body">
			<div class="container" v-masonry>
				<div
					v-masonry-tile
					style="cursor: pointer"
					class="m-1"
					:data-illustration-id="illustration.id"
					:class="`'illustration-'${illustration.id}`"
					v-for="illustration in illustrations"
					:key="illustration.id"
				>
					<div
						class="position-relative"
						@mouseenter="currentFocus = illustration.id"
						@mouseleave="currentFocus = null"
						@click="handleClickedIllustration(illustration)"
					>
						<img
							:key="illustration.id"
							:src="illustration.url"
							:alt="illustration.alt || `illustration-${illustration.id}`"
							:title="illustration.title || `illustration-${illustration.id}`"
							loading="lazy"
							width="400"
						/>
						<div
							class="btn-group btn-group-sm position-absolute"
							style="bottom: 1rem; right: 1rem"
							role="group"
							aria-label="Small button group"
						>
							<button
								v-if="
									currentFocus === illustration.id &&
									illustration.articleCount === 0
								"
								@click="deleteIllustration(illustration.id)"
								type="button"
								class="btn btn-outline-danger"
							>
								<i class="bi bi-trash3-fill m-0"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="tile-footer">
			<PaginationComponent
				v-if="totalPage > 1"
				:total-page="totalPage"
				@page-changed="page => handleAllIllustrationPageChanged(page)"
			/>
			<div v-if="illustrations.length == 0" class="text-center">没有插图</div>
		</div>
	</div>

	<ModalComponent
		title="查看插图"
		ref="viewIllustrationDetail"
		modal-width="modal-xl"
		:show-action-button="false"
	>
		<template #body>
			<div class="row">
				<div class="col-8">
					<img
						:src="
							clickedIllustration?.url + '?type=original' ||
							'https://placehold.co/400x400'
						"
						class="img-fluid card-img"
						:alt="clickedIllustration?.alt || `illustration-${clickedIllustration?.id}`"
						:title="
							clickedIllustration?.title || `illustration-${clickedIllustration?.id}`
						"
					/>
				</div>
				<div class="col-4">
					<div class="row mb-3">
						<h5>基本信息</h5>
						<ul class="list-group list-group-flush">
							<li class="list-group-item">
								<div class="row">
									<div class="col-2">标题</div>
									<div class="col-10">
										{{ clickedIllustration?.title }}
									</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-2">描述</div>
									<div class="col-10">
										{{ clickedIllustration?.alt }}
									</div>
								</div>
							</li>
						</ul>
					</div>
					<div class="row mb-3">
						<h5>引用该插图的文章</h5>
						<ol class="list-group list-group-numbered">
							<li
								v-for="article in currentArticles"
								class="list-group-item d-flex justify-content-between align-items-start"
							>
								<div class="ms-2 me-auto">
									<RouterLink
										:to="{ name: 'ARTICLE_EDITOR', params: { id: article.id } }"
									>
										<div class="fw-bold text-truncate">{{ article.title }}</div>
									</RouterLink>
									{{ setSummary(article.summary) }}
								</div>
							</li>
						</ol>
					</div>
				</div>
			</div>
		</template>
	</ModalComponent>
</template>
<script setup lang="ts">
	import ModalComponent from '@/component/ModalComponent.vue';
	import PaginationComponent from '@/component/PaginationComponent.vue';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { AlertType } from '@/enum';
	import showMessage from '@/service/alert.service';
	import { makeRequest } from '@/service/request.service';
	import type {
		APIResponse,
		ArticleInfo,
		IllustrationInfo,
		PageInfo,
		UserInfo,
	} from '@/type/response.type';
	import { onMounted, ref } from 'vue';

	// 全局
	const illustrations = ref<IllustrationInfo[]>([]);
	const userInfo = ref<UserInfo | null>(null);
	const totalPage = ref(0);
	const currentFocus = ref<number | null>(null);
	const clickedIllustration = ref<IllustrationInfo | null>(null);
	const currentArticles = ref<ArticleInfo[]>([]);
	const viewIllustrationDetail = ref();
	onMounted(async () => {
		await getUserInfo();
		loadPageIllustrations(0);
	});
	const handleAllIllustrationPageChanged = (page: number) => {
		loadPageIllustrations(page);
	};
	const getUserInfo = async () => {
		const userResponse: APIResponse<UserInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/users/info'
		);
		if (userResponse.code !== 0) {
			return;
		}
		userInfo.value = userResponse.data;
	};
	const loadPageIllustrations = async (page: number) => {
		const pageIllustrations: APIResponse<PageInfo<IllustrationInfo>> = await makeRequest(
			RESOURCE_BASE_URL + '/illustrations/user/' + userInfo.value?.id + '?page=' + page
		);
		if (pageIllustrations.code !== 0) {
			return showMessage('图片加载失败', AlertType.ERROR);
		}
		totalPage.value = pageIllustrations.data.totalPage;
		illustrations.value = pageIllustrations.data.items;
	};
	const deleteIllustration = async (id: number) => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/illustrations/delete/' + id,
			{
				method: 'DELETE',
			}
		);
		if (response.code !== 0) {
			return showMessage('删除失败', AlertType.ERROR);
		}
		showMessage('删除成功', AlertType.SUCCESS);
		illustrations.value = illustrations.value.filter(i => i.id !== id);
	};
	const handleClickedIllustration = (i: IllustrationInfo) => {
		clickedIllustration.value = i;
		viewIllustrationDetail.value?.show();
		loadArticlesUsingThisIllustration();
	};
	const loadArticlesUsingThisIllustration = async () => {
		const articles: APIResponse<PageInfo<ArticleInfo>> = await makeRequest(
			RESOURCE_BASE_URL +
				'/articles/illustration/' +
				clickedIllustration.value?.id +
				'?page=0'
		);
		if (articles.code !== 0) {
			return console.error(articles.message);
		}
		currentArticles.value = articles.data.items;
	};
	const setSummary = (summary?: string | null) => {
		if (!summary) {
			summary = '';
		}
		if (summary.length > 30) {
			return summary.slice(0, 30) + ' ...';
		} else {
			return summary;
		}
	};
</script>
