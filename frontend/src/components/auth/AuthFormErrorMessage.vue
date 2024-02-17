<script setup lang="ts">
	import icons from "@/components/icons";
	const props = defineProps<{
		status: boolean;
	}>();
</script>
<template>
	<div class="parent-EkQQhPEokl" v-if="props.status">
		<div class="parent-Nk7VmcBjJg">
			<component :is="icons.fail" class="icon smallest" />
		</div>
		<span class="parent-Vk-U6DVsJe">
			<slot></slot>
		</span>
	</div>
</template>
<style scoped>
	.parent-EkQQhPEokl {
		min-height: 13px;
		display: flex;
		align-items: center;
	}
	.parent-Nk7VmcBjJg {
		min-width: 12px;
		min-height: 12px;
	}
	.parent-Vk-U6DVsJe {
		line-height: 13px;
		font-size: 13px;
		margin-left: 5px;
	}
</style>
