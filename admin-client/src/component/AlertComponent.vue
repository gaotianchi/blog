<template>
	<div class="alert" :class="type ? type : 'alert-primary'" role="alert">
		{{ message }}
	</div>
</template>
<script lang="ts" setup>
	defineProps<{
		message: string;
		type?: string;
	}>();
</script>

<style scoped>
	.alert {
		width: fit-content;
		min-width: 300px;
		position: fixed;
		bottom: 20px;
		z-index: 99999;
		left: 20px;

		/* 初始状态：完全隐藏在屏幕左侧 */
		transform: translateX(-100%);
		animation: slide-in 0.1s forwards;
	}

	/* 滑入动画：从屏幕左下角边缘滑入到屏幕内 */
	@keyframes slide-in {
		from {
			transform: translateX(-100%);
		}
		to {
			transform: translateX(0); /* 停留在屏幕内 */
		}
	}
</style>
