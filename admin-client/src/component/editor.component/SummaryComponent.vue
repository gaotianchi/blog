<template>
	<div class="tile">
		<div class="tile-title">摘要</div>
		<div class="title-body">
			<textarea
				class="form-control"
				aria-label="summary textarea"
				name="summary-textarea"
				id="summary-textarea"
				rows="5"
				v-model="summary"
			></textarea>
		</div>
		<div class="tile-footer" v-if="changed">
			<div class="row justify-content-end">
				<button
					@click="summary = article ? article.summary : ''"
					type="button"
					class="btn btn-secondary w-auto me-2"
				>
					还原
				</button>
				<button @click="updateSummary" type="button" class="btn btn-primary w-auto me-2">
					保存
				</button>
			</div>
		</div>
	</div>
</template>
<script setup lang="ts">
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, ArticleInfo } from '@/type/response.type';
	import { ref, watch } from 'vue';

	const summary = defineModel<string>();
	const props = defineProps<{
		articleInfo: ArticleInfo | null;
	}>();
	const changed = ref(false);
	const article = ref<ArticleInfo | null>(props.articleInfo);

	const updateSummary = async () => {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/summary/' + props.articleInfo?.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					summary: summary.value,
				}),
			}
		);
		if (response.code === 0) {
			if (article.value) {
				article.value.summary = summary.value as string;
                changed.value = false;
				console.log(changed.value);
			} 
		} else {
		}
	};
	watch(summary, newValue => {
		return (changed.value = newValue !== props.articleInfo?.summary);
	});
</script>
