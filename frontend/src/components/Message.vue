<script setup lang="ts">
	import { watch } from "vue";
	import { messageProp } from "@/store";
	watch(messageProp, () => {
		if (messageProp.value.length > 0) {
			setTimeout(() => {
				messageProp.value = "";
			}, 2000);
		}
	});
</script>
<template>
	<Transition>
		<div class="parent-E1A1PUZo1x" v-if="messageProp.length > 0">
			<span class="parent-NyiIFLbi1e">{{ messageProp }}</span>
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
