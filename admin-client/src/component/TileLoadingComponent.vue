<template>
	<div class="overlay z-3" v-if="loading">
		<div class="m-loader mr-4">
			<svg class="m-circular" viewBox="25 25 50 50">
				<circle
					class="path"
					cx="50"
					cy="50"
					r="20"
					fill="none"
					stroke-width="4"
					stroke-miterlimit="10"
				></circle>
			</svg>
		</div>
		<h3 class="l-text">正在加载 ...</h3>
	</div>
</template>

<script setup lang="ts">
	defineProps<{
		loading: boolean;
	}>();
</script>
