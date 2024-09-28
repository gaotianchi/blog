<template>
	<!-- Modal -->
	<div class="modal fade" :id="id" :tabindex="-1" :aria-labelledby="id" :aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" :class="modalWidth">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" :id="id">
						{{ title }}
					</h1>
					<button
						type="button"
						class="btn-close"
						data-bs-dismiss="modal"
						aria-label="Close"
						@click="hide"
					></button>
				</div>
				<div class="modal-body">
					<slot name="body">默认内容</slot>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" @click="hide">
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
	import { ref, onMounted } from 'vue';
	import * as bootstrap from 'bootstrap';
	import { v4 as uuidv4 } from 'uuid';

	const id = uuidv4();

	const emits = defineEmits(['saveChange']);

	const props = defineProps({
		title: {
			type: String,
			default: '模态框',
		},
		closeButtonText: {
			type: String,
			default: '关闭',
		},
		saveButtonText: {
			type: String,
			default: '保存修改',
		},
		modalWidth: {
			type: String,
			required: false,
		},
	});

	const modalInstance = ref<bootstrap.Modal | null>(null);

	const show = () => {
		if (!modalInstance.value) {
			const modalElement = document.getElementById(id);
			if (modalElement) {
				modalInstance.value = new bootstrap.Modal(modalElement);
			}
		}
		if (modalInstance.value) {
			modalInstance.value.show();
		}
	};

	const hide = () => {
		if (modalInstance.value) {
			modalInstance.value.hide();
		}
	};

	const onSave = () => {
		emits('saveChange');
	};

	onMounted(() => {
		const modalElement = document.getElementById(id);
		if (modalElement) {
			modalInstance.value = new bootstrap.Modal(modalElement);
		}
	});

	defineExpose({
		show,
		hide,
	});
</script>
