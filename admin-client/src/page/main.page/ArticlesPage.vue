<template>
	<MainPageHeaderComponent
		title="文章管理页面"
		sub-title="在这里可以删除、添加文章以及更改文章的部分属性"
		bs-icon-name="bi-file-text"
		:breadcrumb-items="[{ name: '文章管理', routeName: 'ARTICLES' }]"
	></MainPageHeaderComponent>
	<div class="tile">
		<div class="row justify-content-end">
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
	<div class="tile" v-for="(article, index) in currentArticles">
		<div class="row">
			<div class="col-auto">
				<div class="image-container" style="height: 180px; width: 180px; overflow: hidden">
					<img
						style="width: 100%; height: 100%"
						class="rounded object-fit-cover"
						:src="
							article.cover?.urls.LOW || 'https://via.assets.so/img.jpg?w=300&h=150'
						"
						:alt="article.cover?.alt"
					/>
				</div>
			</div>
			<div class="col text-truncate">
				<span class="h4">
					{{
						article.title ||
						'Lorem Ipsum，也称乱数假文或者哑元文本， 是印刷及排版领域所常用的虚拟文字。'
					}}
				</span>

				<div class="row mt-2 mb-3">
					<div class="col-auto d-flex align-items-center">
						<span class="badge" :class="getArtcicleStatusClass(article.articleStatus)">
							{{ article.articleStatus }}
						</span>
						<Segmentation />
						<span class="text-secondary">{{ article.creationDatetime }}</span>
						<Segmentation />
						<span class="text-secondary">{{ article.author.penName }}</span>
					</div>
				</div>

				<div class="row">
					<ul class="list-group list-group-flush">
						<li class="list-group-item">
							<div class="row">
								<div class="col-1">标签</div>
								<div class="col-10">
									<span class="badge rounded-pill text-bg-secondary me-2">
										爱
									</span>
									<span class="badge rounded-pill text-bg-secondary me-2">
										温暖
									</span>
									<span class="badge rounded-pill text-bg-secondary me-2">
										家人
									</span>
								</div>
								<div class="col-1"></div>
							</div>
						</li>
						<li class="list-group-item">
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
	</div>
	<PaginationComponent :total-page="0" @page-changed="" />
</template>
<script setup lang="ts">
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { makeRequest } from '@/service/request.service';
	import { onMounted, ref, watch } from 'vue';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import Segmentation from '@/component/icon/Segmentation.vue';
	import type { APIResponse, ArticleResponse } from '@/type/response.type';
	import { ArticleStatus } from '@/enum';
	import { useRouter } from 'vue-router';
	import PaginationComponent from '@/component/article.component/PaginationComponent.vue';

	const currentPage = ref(0);
	const currentArticles = ref<ArticleResponse[]>([]);
	const currentTotalPage = ref(0);
	const router = useRouter();

	const getPageArticles = async (page: number) => {
		
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

	onMounted(async () => {
		getPageArticles(currentPage.value);
	});
</script>
