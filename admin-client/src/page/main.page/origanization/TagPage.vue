<template>
	<!-- 操作区域 -->
	<!-- <div class="tile"></div> -->

	<!-- 标签列表 -->
	<div class="tile">
		<ol class="list-group list-group-flush">
			<li
				@mouseenter="focus = tag"
				@mouseleave="focus = null"
				v-for="tag in tags"
				:key="tag.id"
				class="list-group-item"
			>
				<div class="row">
					<div class="col-4 text-truncate fw-bold align-content-center">
						{{ tag.name }}
					</div>
					<div class="col-4 text-end align-content-center">
						<span>{{ tag.articleCount }} 篇文章</span>
					</div>
					<div class="col-4 text-end align-content-center">
						<div
							v-if="focus?.id === tag.id"
							class="btn-group btn-group-sm"
							role="group"
							aria-label="Small button group"
						>
							<button type="button" class="btn btn-outline-primary">
								<i class="bi bi-eye-fill m-0"></i>
							</button>
							<button type="button" class="btn btn-outline-secondary">
								<i class="bi bi-vector-pen m-0"></i>
							</button>
							<button
								@click="openDeleteTagModal(tag)"
								type="button"
								class="btn btn-outline-danger"
							>
								<i class="bi bi-trash3-fill m-0"></i>
							</button>
						</div>

						<span v-else class="text-secondary">
							{{ getFormarttedDate(tag.creationDatetime) }}
						</span>
					</div>
				</div>
			</li>
		</ol>
		<div class="title-footer">
			<PaginationComponent v-if="totalPage > 1" :total-page="totalPage" @page-changed="" />
			<div v-if="tags?.length == 0" class="text-center">没有标签</div>
		</div>
	</div>
	<ModalComponent
		title="删除标签"
		ref="deleteTagModal"
		@save-change="deleteFoucsedTag"
		save-button-text="删除"
	>
		<template #body>
			<p>
				该操作会彻底删除标签
				<strong>{{ currentTag?.name }}</strong>
				，但是不会删除相关文章。
			</p>
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
	import type { APIResponse, PageInfo, TagInfo } from '@/type/response.type';
	import { getFormarttedDate } from '@/utlis';
	import { onMounted, ref } from 'vue';

	// 全局
	const tags = ref<TagInfo[] | null>([]);
	const totalPage = ref(0);
	const focus = ref<TagInfo | null>(null);
	const deleteTagModal = ref();
	const currentTag = ref<TagInfo | null>(null);

	onMounted(() => {
		loadPageTags(0);
	});
	const loadPageTags = async (page: number) => {
		const pageTagsResponse: APIResponse<PageInfo<TagInfo>> = await makeRequest(
			RESOURCE_BASE_URL + '/tags?page=' + page
		);
		if (pageTagsResponse.code !== 0) {
			return console.error(pageTagsResponse.message);
		}
		totalPage.value = pageTagsResponse.data.totalPage;
		tags.value = pageTagsResponse.data.items;
	};
	const deleteFoucsedTag = async () => {
		if (currentTag.value == null) {
			return console.error('no tag found.');
		}
		const deleteTagResponse: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/tags/delete/' + currentTag.value.id,
			{
				method: 'DELETE',
			}
		);
		if (deleteTagResponse.code !== 0) {
			return showMessage('标签删除失败', AlertType.ERROR);
		}
		showMessage('删除成功', AlertType.SUCCESS);
		if (tags.value) {
			tags.value = tags.value?.filter(e => e.id !== currentTag.value?.id);
		}
		deleteTagModal.value?.hide();
	};
	const openDeleteTagModal = (t: TagInfo) => {
		currentTag.value = t;
		if (!deleteTagModal.value) {
			return;
		}
		deleteTagModal.value.show();
	};
</script>
