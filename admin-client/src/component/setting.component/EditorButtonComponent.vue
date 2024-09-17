<template>
	<button
		type="button"
		class="btn btn-link p-1"
		ref="tooltipEl"
		:data-bs-title="reading ? '编辑' : '重置'"
		data-bs-toggle="tooltip"
		data-bs-placement="top"
		@click="handleClicked"
	>
		<i
			class="bi"
			:class="{
				'bi-x-lg': !reading,
				'bi-pencil-square': reading,
			}"
		></i>
	</button>
</template>

<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount, watch, computed } from 'vue';
	import * as bootstrap from 'bootstrap';

	const props = defineProps<{
		readingState: boolean;
	}>();

	const emits = defineEmits(['edit', 'reset']);

	const reading = computed(() => {
		return props.readingState;
	});

	const handleClicked = () => {
		if (reading.value) {
			emits('edit');
		} else {
			emits('reset');
		}
	};

	// 引用 DOM 元素
	const tooltipEl = ref<HTMLElement | null>(null);
	let tooltipInstance: bootstrap.Tooltip | null = null;

	// 初始化 Tooltip
	onMounted(() => {
		if (tooltipEl.value) {
			tooltipInstance = new bootstrap.Tooltip(tooltipEl.value);
		}
	});

	// 清理 Tooltip 实例，防止冲突
	onBeforeUnmount(() => {
		if (tooltipInstance) {
			tooltipInstance.dispose();
			tooltipInstance = null;
		}
	});
	// 当 edit 状态改变时更新 Tooltip 的内容
	watch(reading, () => {
		if (tooltipEl.value) {
			const tooltip = bootstrap.Tooltip.getInstance(tooltipEl.value);
			if (tooltip) {
				tooltip.setContent({
					'.tooltip-inner': reading.value ? '编辑' : '重置',
				});
			}
		}
	});
</script>
