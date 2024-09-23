<template>
	<MainPageHeaderComponent
		title="文章管理页面"
		sub-title="在这里可以删除、添加文章以及更改文章的部分属性"
		bs-icon-name="bi-file-text"
		:breadcrumb-items="[{ name: '文章管理', routeName: 'ARTICLES' }]"
	></MainPageHeaderComponent>
	<div class="container">
		<div class="tile">
			<div class="row">
				<div class="col-auto">
					<div
						class="image-container"
						style="height: 180px; width: 180px; overflow: hidden"
					>
						<img
							style="width: 100%; height: 100%"
							class="rounded object-fit-cover"
							src="https://via.assets.so/img.jpg?w=300&h=150"
							alt=""
						/>
					</div>
				</div>
				<div class="col text-truncate">
					<span class="h4">
						Lorem Ipsum，也称乱数假文或者哑元文本， 是印刷及排版领域所常用的虚拟文字。
					</span>

					<div class="row mt-2 mb-3">
						<div class="col-auto d-flex align-items-center">
							<span class="badge text-bg-success">已发布</span>
							<Segmentation />
							<span class="text-secondary">2024-09-18T08:49:01.481342Z</span>
							<Segmentation />
							<span class="text-secondary">高天驰</span>
						</div>
					</div>

					<div class="row mb-3">
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
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script setup lang="ts">
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { makeRequest } from '@/service/request.service';
	import { onMounted, ref } from 'vue';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import Segmentation from '@/component/icon/segmentation.vue';

	const currentPage = ref(0);

	onMounted(async () => {
		getPageArticles(currentPage.value);
	});

	const getPageArticles = async (page: number) => {
		const url = new URL(RESOURCE_BASE_URL + '/articles/list');
		url.searchParams.append('page', page.toString());
		const articles = await makeRequest(url.toString());
		console.log(articles);
	};
</script>
