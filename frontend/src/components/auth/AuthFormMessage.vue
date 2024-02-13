<script setup lang="ts">
	import icons from "@/components/icons";
	const props = defineProps<{
		status: boolean;
	}>();
</script>
<template>
	<div class="parent-EkQQhPEokl">
		<component
			v-if="props.status"
			:is="icons.success"
			class="icon smallest"
		/>
		<component v-else :is="icons.fail" class="icon smallest" />
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
	.parent-Vk-U6DVsJe {
        line-height: 13px;
        font-size: 13px;
        margin-left: 5px;
	}
</style>
