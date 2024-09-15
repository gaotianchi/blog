<template>
	<!-- Modal Trigger Button -->
	<button
		type="button"
		class="btn btn-primary w-auto"
		data-bs-toggle="modal"
		:data-bs-target="`#${modalId}`"
	>
		{{ buttonText }}
	</button>

	<!-- Modal -->
	<div
		class="modal fade"
		:class="{ show: showModal }"
		:id="modalId"
		:tabindex="-1"
		:aria-labelledby="`${modalId}Label`"
		:aria-hidden="true"
	>
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" :id="`${modalId}Label`">
						{{ title }}
					</h1>
					<button
						type="button"
						class="btn-close"
						data-bs-dismiss="modal"
						aria-label="Close"
					></button>
				</div>
				<div class="modal-body">
					<!-- 模态框内容插槽 -->
					<slot name="body">默认内容</slot>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
						{{ closeButtonText }}
					</button>
					<button type="button" class="btn btn-primary" @click="onSave">
						{{ saveButtonText }}
					</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
	import { ref, defineProps, watch, onMounted } from 'vue';
	import * as bootstrap from 'bootstrap';

	const emits = defineEmits(['saveChange']);

	// 定义可传入的 props
	const props = defineProps({
		title: {
			type: String,
			default: '模态框',
		},
		buttonText: {
			type: String,
			default: '弹出模态框',
		},
		closeButtonText: {
			type: String,
			default: '关闭',
		},
		saveButtonText: {
			type: String,
			default: '保存修改',
		},
		modalId: {
			type: String,
			// required: true,
			default: 'modalId-' + Math.random(),
		},
		show: {
			type: Boolean,
			default: false,
		},
	});

	// 保存 Modal 的实例
	const modalInstance = ref<bootstrap.Modal | null>(null);
	const showModal = ref(false);

	// 监听 `show` prop 的变化以显示/隐藏 Modal
	watch(
		() => props.show,
		newValue => {
			if (modalInstance.value) {
				if (newValue) {
					modalInstance.value.show();
				} else {
					modalInstance.value.hide();
				}
			}
		}
	);

	// 模态框保存按钮的点击事件
	const onSave = () => {
		emits('saveChange');
	};

	// 挂载时初始化 Bootstrap Modal 实例
	onMounted(() => {
		const modalElement = document.getElementById(props.modalId);
		if (modalElement) {
			modalInstance.value = new bootstrap.Modal(modalElement);
		}
	});
</script>
