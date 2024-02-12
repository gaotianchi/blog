<script setup lang="ts">
	import { ref, watch } from "vue";

	const props = defineProps<{
		message: string;
	}>();
	const status = ref(false);
	watch(status, () => {
		if (status.value) {
			setTimeout(() => {
				status.value = false;
			}, 1000);
		}
	});
	if (!props.message.trim()) {
		status.value = false;
	}
</script>
<template>
	<Transition>
		<div
			class="parent-E1A1PUZo1x"
			v-if="status"
			@click="
				() => {
					status = true;
				}
			"
		>
			<span class="parent-NyiIFLbi1e">{{ message }}</span>
		</div>
	</Transition>
</template>
<style scoped>
	.parent-E1A1PUZo1x {
		position: fixed;
		min-width: 200px;
		max-width: 500px;
		min-height: 50px;
		max-height: 100px;
		left: 20px;
		bottom: 50px;
		background-color: #666666;
		display: flex;
		align-items: center;
		padding: 0 15px;
		border-radius: 5px;
		z-index: 1000;
	}
	.parent-NyiIFLbi1e {
		color: white;
		line-height: 18px;
		font-size: 18px;
	}

	.v-enter-active {
		transition: all 0.3s ease-out;
	}

	.v-leave-active {
		transition: all 0.3s ease-out;
	}
	.v-enter-from,
	.v-leave-to {
		transform: translateY(50px);
		opacity: 0;
	}
</style>
