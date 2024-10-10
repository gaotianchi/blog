<template>
	<div class="tile">
		<div class="tile-title">信息</div>
		<div class="tile-body">
			<ul class="list-group list-group-flush">
				<li class="list-group-item">
					<div class="row">
						<div class="col-4 p-0">发布状态</div>
						<div class="col-8 p-0">
							<span class="badge" :class="getStatusColorClass(articleInfo?.status)">
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
</template>
<script setup lang="ts">
	import type { ArticleInfo } from '@/type/response.type';
	import { getFormarttedDate } from '@/utlis';

	const props = defineProps<{
		articleInfo: ArticleInfo | null;
	}>();

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
</script>
