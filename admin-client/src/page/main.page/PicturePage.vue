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
					>
						<img
							:src="illustration.url"
							:alt="illustration.alt"
							:title="illustration.title"
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
</template>
<script setup lang="ts">
	import PaginationComponent from '@/component/PaginationComponent.vue';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { AlertType } from '@/enum';
	import showMessage from '@/service/alert.service';
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, IllustrationInfo, PageInfo, UserInfo } from '@/type/response.type';
	import { onMounted, ref } from 'vue';

	// 全局
	const illustrations = ref<IllustrationInfo[]>([]);
	const userInfo = ref<UserInfo | null>(null);
	const totalPage = ref(0);
	const currentFocus = ref<number | null>(null);
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
</script>
