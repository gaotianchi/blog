<template>
	<ModalComponent
		@save-change="handleSave"
		modal-id="avatar-modal"
		title="编辑头像"
		button-text="编辑头像"
	>
		<template #button>
			<button type="button" class="btn btn-link p-1">
				<i class="bi bi-pencil-square"></i>
			</button>
		</template>
		<template #body>
			<div class="row">
				<div class="col-sm-8">
					<avatar-editor
						:key="initImageUrl"
						:width="200"
						:height="200"
						:border="2"
						:image="initImageUrl"
						ref="avatarEditorRef"
						@image-ready="(scale: number) => {scaleVal = scale}"
						v-model:scale="scaleVal"
					/>
				</div>
				<div class="col-sm-4">
					<label for="customRange1" class="form-label">缩放图片</label>
					<input
						type="range"
						class="form-range w-auto"
						id="customRange1"
						:min="scaleMin"
						:max="scaleMax"
						:step="scaleStep"
						v-model="scaleVal"
					/>
				</div>
			</div>
		</template>
	</ModalComponent>
</template>
<script lang="ts" setup>
	import ModalComponent from './ModalComponent.vue';
	import { AvatarEditor } from 'avatar-editor';
	import { computed, onMounted, onUnmounted, ref } from 'vue';
	import { makeRequest } from '@/service/request.service';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import type { APIResponse, ImageResponse } from '@/type/response.type';

	// 定义可传入的 props
	const props = defineProps<{
		initUrl?: string;
	}>();

	const initImageUrl = computed(() => {
		return props.initUrl;
	});
	const emits = defineEmits<{
		save: [imageResponse: ImageResponse];
	}>();

	const scaleVal = ref<number>(1);
	const scaleStep = 0.02;
	const scaleMin = 1;
	const scaleMax = 3;
	const avatarEditorRef = ref<any>(null);

	const handleWheelEvent = (e: WheelEvent) => {
		if (e.deltaY > 0 && scaleVal.value - scaleStep >= scaleMin) {
			// Down
			scaleVal.value -= scaleStep;
		} else {
			// Up
			if (scaleVal.value + scaleStep <= scaleMax) {
				scaleVal.value += scaleStep;
			}
		}
	};

	const handleSave = async () => {
		if (avatarEditorRef.value) {
			const canvasData = avatarEditorRef.value.getImageScaled();
			const img = canvasData.toDataURL('image/png');
			const blob = dataURLToBlob(img);
			// 创建 multipart/form-data
			const formData = new FormData();
			formData.append('file', blob, 'avatar.png'); // 添加文件
			const response: APIResponse<ImageResponse> = await makeRequest(
				'/images/new',
				'resource',
				{
					method: 'POST',
					body: formData,
				}
			);
			if (response.code === 0) {
				showMessage('图片上传成功。', AlertType.SUCCESS);
				emits('save', response.data);
			} else {
				showMessage('图片上传失败，请稍后重试！', AlertType.ERROR);
			}
		}
	};

	// 将 base64 转换为 Blob
	const dataURLToBlob = (dataURL: string) => {
		const byteString = atob(dataURL.split(',')[1]); // 去掉头部的 'data:image/png;base64,'
		const mimeString = dataURL.split(',')[0].split(':')[1].split(';')[0]; // 获取 mime 类型

		const byteArray = new Uint8Array(byteString.length);
		for (let i = 0; i < byteString.length; i++) {
			byteArray[i] = byteString.charCodeAt(i);
		}
		return new Blob([byteArray], { type: mimeString });
	};

	onMounted(() => {
		document.addEventListener('wheel', handleWheelEvent);
	});

	onUnmounted(() => {
		document.removeEventListener('wheel', handleWheelEvent);
	});
</script>
