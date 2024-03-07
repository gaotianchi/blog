<script setup lang="ts">
	import type { SerializedArticle } from "@/typing";
	import { visitorGetArticleItem } from "@/api/remote";
	import { defaultSerializedArticle } from "@/defaults";
	import { onMounted, ref, type Ref } from "vue";
	import { dateFormatter, getLocalDatetime } from "@/utlis";
	import { MdPreview } from "md-editor-v3";
	import "md-editor-v3/lib/preview.css";
	const props = defineProps<{
		slug: string;
	}>();
	const currentArticle: Ref<SerializedArticle> = ref(
		defaultSerializedArticle
	);
	onMounted(() => {
		initArticleData();
	});
	function initArticleData(): void {
		try {
			visitorGetArticleItem(props.slug).then((resp) => {
				currentArticle.value = resp;
			});
		} catch (error) {
			console.error(error);
		}
	}
</script>
<template>
	<div class="parent-4y7eNBza1e">
		<div class="parent-NkGKVHM6ye">
			<div class="parent-4k5f4BGpJg">
				<h1 class="parent-V1-SEBfT1g">{{ currentArticle.title }}</h1>
			</div>
			<div class="parent-4JazEHGayx">
				<span class="parent-4ka8EBMpkx">
					{{
						dateFormatter(
							getLocalDatetime(currentArticle.createdAt),
							"ddd MMM DD YYYY"
						)
					}}
				</span>
			</div>
			<div class="parent-VJW7NSzTkx">
				<MdPreview v-model="currentArticle.body" />
			</div>
		</div>
	</div>
</template>
<style scoped>
	.parent-4y7eNBza1e {
		font-weight: 500;
		margin-bottom: 20px;
		width: 80%;
		padding: 30px 70px;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
	}
	/* .parent-NkGKVHM6ye {
	} */
	.parent-4k5f4BGpJg {
		margin-bottom: 20px;
		padding: 0 20px;
	}
	.parent-4JazEHGayx {
		margin-bottom: 15px;
		padding: 0 20px;
	}
	/* .parent-VJW7NSzTkx {
	} */
	.parent-V1-SEBfT1g {
		font-weight: 500;
	}
	.parent-4ka8EBMpkx {
		font-size: 14px;
		color: grey;
	}
</style>
