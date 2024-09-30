<template>
	<img
		:src="avatar?.url || 'http://localhost:8800/default/avatar.svg'"
		class="img-thumbnail"
		alt="avatar"
		width="200"
		style="cursor: pointer"
		@click="openModal"
	/>
	<ModalComponent
		@save-change="handleSave"
		ref="avatarModal"
		title="编辑头像"
		button-text="编辑头像"
	>
		<template #body>
			<div class="row">
				<div class="col-sm-6 align-self-center position-relative">
					<a
						v-if="avatarUrl"
						class="btn btn-link position-absolute"
						href="#"
						role="button"
						style="right: 20px; top: 0"
						@click="handleRemove"
					>
						<i class="bi bi-trash"></i>
					</a>
					<avatar-editor
						:key="avatarUrl"
						:width="200"
						:height="200"
						:border="2"
						:image="avatarUrl || 'http://localhost:8800/default/avatar.svg'"
						ref="avatarEditorRef"
						@image-ready="s => handleImageReady(s)"
						v-model:scale="scale.value"
					/>
				</div>
				<div class="col-sm-6">
					<div class="d-flex flex-column">
						<label for="customRange1" class="form-label">缩放图片</label>
						<input
							type="range"
							class="form-range w-auto"
							id="customRange1"
							:min="scale.min"
							:max="scale.max"
							:step="scale.step"
							v-model="scale.value"
						/>
					</div>
				</div>
			</div>
		</template>
	</ModalComponent>
</template>
<script lang="ts" setup>
	import { AvatarEditor } from 'avatar-editor';
	import { onMounted, onUnmounted, reactive, ref, watch } from 'vue';
	import { makeRequest } from '@/service/request.service';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import type { APIResponse, AvatarInfo, ImageResponse } from '@/type/response.type';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { dataURLToBlob } from '@/utlis';
	import ModalComponent from '../ModalComponent.vue';

	const props = defineProps<{
		avatar: AvatarInfo | null;
	}>();
	const emits = defineEmits<{
		saveChange: [avatar: AvatarInfo];
	}>();
	const avatarEditorRef = ref<any>(null);
	const avatarUrl = ref(props?.avatar?.url || '');
	const avatarModal = ref();
	const scale = reactive({
		value: 1,
		step: 0.02,
		min: 1,
		max: 3,
	});

	const openModal = () => {
		if (avatarModal.value) {
			avatarModal.value.show();
		}
	};

	const closeModal = () => {
		if (avatarModal.value) {
			avatarModal.value.hide();
		}
	};

	const handleRemove = async () => {
		if (props.avatar) {
			const response: APIResponse<void> = await makeRequest(
				RESOURCE_BASE_URL + '/avatar/delete',
				{
					method: 'DELETE',
				}
			);
			if (response.code === 0) {
				showMessage('成功移除头像', AlertType.SUCCESS);
			} else {
				showMessage('删除头像失败，稍后再试！', AlertType.ERROR);
			}
		}
	};
	let oldDataUrl = '';

	const handleSave = async () => {
		if (avatarEditorRef.value) {
			const canvasData = avatarEditorRef.value.getImageScaled();
			const blob = dataURLToBlob(canvasData.toDataURL('image/png'));
			const formData = new FormData();
			formData.append('file', blob, 'avatar.png');
			const response: APIResponse<AvatarInfo> = await makeRequest(
				RESOURCE_BASE_URL + '/avatar/new',
				{
					method: 'POST',
					body: formData,
				}
			);
			if (response.code === 0) {
				showMessage('图片上传成功。', AlertType.SUCCESS);
				emits('saveChange', response.data);
			} else {
				showMessage('图片上传失败，请稍后重试！', AlertType.ERROR);
			}
		}
		closeModal();
	};

	const handleImageReady = (s: number) => {
		scale.value = s;
		const canvasData = avatarEditorRef.value.getImageScaled();
		oldDataUrl = canvasData.toDataURL('image/png');
	};

	const handleWheelEvent = (e: WheelEvent) => {
		if (e.deltaY > 0 && scale.value - scale.step >= scale.min) {
			scale.value -= scale.step;
		} else {
			if (scale.value + scale.step <= scale.max) {
				scale.value += scale.step;
			}
		}
	};

	onMounted(() => {
		document.addEventListener('wheel', handleWheelEvent);
	});

	onUnmounted(() => {
		document.removeEventListener('wheel', handleWheelEvent);
	});
	watch(
		() => props?.avatar?.url,
		newValue => {
			avatarUrl.value = newValue || '';
		}
	);
</script>
