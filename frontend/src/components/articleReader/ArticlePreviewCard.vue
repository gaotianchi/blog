<script setup lang="ts">
	import type { ArticlePreivewCard } from "@/typing";
	import { dateFormatter, getLocalDatetime } from "@/utlis";
	import { useRouter } from "vue-router";
	const props = defineProps<{
		previewCard: ArticlePreivewCard;
	}>();
	const router = useRouter();
	function readMore(): void {
		router.push({
			name: "ArticleDetail",
			params: {
				slug: props.previewCard.slug,
			},
		});
	}
</script>
<template>
	<div class="parent-Ey5SeGG6kx">
		<div class="parent-N1ipeMzTkl">
			<span class="parent-E1USZNM61e">
				{{ props.previewCard.title }}
			</span>
		</div>
		<div class="parent-4JPRezMaJl">
			<span class="parent-NJZhZ4z6yg">
				{{
					dateFormatter(
						getLocalDatetime(props.previewCard.createdAt),
						"ddd MMM DD YYYY"
					)
				}}
			</span>
		</div>
		<div class="parent-E1kkWGfayg">
			<div class="parent-Vk4yG4Ga1l">
				<img
					:src="props.previewCard.images[0]"
					class="parent-4JoQGNf61e"
					alt="Article cover"
				/>
			</div>
			<div class="parent-4kOJM4faJg">
				<span class="parent-E1nUGVfpJx">
					{{ props.previewCard.summary }}
				</span>
			</div>
		</div>
		<div class="parent-41o1Zzzp1l">
			<span class="parent-Vy0ufVf6ke" @click="readMore">Read more</span>
		</div>
	</div>
</template>
<style scoped>
	.parent-Ey5SeGG6kx {
		width: 70%;
		display: flex;
		flex-direction: column;
		padding: 20px 30px;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
	}
	.parent-N1ipeMzTkl {
		margin-bottom: 20px;
	}
	.parent-E1USZNM61e {
		font-size: 26px;
		font-weight: 500;
		line-height: 26px;
	}
	.parent-4JPRezMaJl {
		display: flex;
		justify-content: space-between;
		margin-bottom: 10px;
	}
	.parent-NJZhZ4z6yg {
		font-size: 14px;
		color: grey;
	}
	.parent-E1kkWGfayg {
		display: flex;
		margin-bottom: 20px;
	}
	.parent-Vk4yG4Ga1l {
		position: relative;
		min-width: 300px;
		max-width: 300px;
		min-height: 300px;
		overflow: hidden;
	}
	.parent-4JoQGNf61e {
		position: absolute;
		top: 50%;
		left: 50%;
		display: block;
		min-width: 100%;
		min-height: 100%;
		transform: translate(-50%, -50%);
	}
	.parent-4kOJM4faJg {
		padding-left: 20px;
	}
	.parent-E1nUGVfpJx {
		font-size: 17px;
	}
	.parent-41o1Zzzp1l {
		display: flex;
		justify-content: flex-end;
	}
	.parent-Vy0ufVf6ke {
		font-size: 20px;
		color: var(--second-color);
		cursor: pointer;
	}
</style>
