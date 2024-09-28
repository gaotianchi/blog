<template>
	<NodeViewWrapper class="">
		<div
			class="card text-bg-dark"
			:class="node.attrs.align"
			style="cursor: pointer"
			:style="{
				margin: node.attrs.align === 'float-none' ? 'auto' : '1rem',
				display: node.attrs.align === 'float-none' ? 'block' : 'inline-block',
				width: node.attrs.align === 'float-none' ? '30rem' : '25rem',
			}"
			@click="openModal"
		>
			<img
				:src="
					node.attrs.src ||
					'https://p5.img.cctvpic.com/photoworkspace/contentimg/2023/03/30/2023033011303020756.jpg'
				"
				class="img-fluid card-img"
				:title="node.attrs.title"
				:alt="node.attrs.alt"
			/>

			<div class="card-img-overlay">
				<h5 class="card-title">{{ node.attrs.title }}</h5>
				<p class="card-text">
					{{ node.attrs.alt }}
				</p>
				<p class="card-text"><small>Last updated 3 mins ago</small></p>
			</div>
		</div>

		<ModalComponentNew
			@save-change="handleSaveChange"
			title="编辑插图"
			ref="imageRef"
			modal-width="modal-xl"
		>
			<template #body>
				<div class="row">
					<div class="col-6">
						<img
							:src="
								previewUrl ||
								node.attrs.src ||
								'https://p5.img.cctvpic.com/photoworkspace/contentimg/2023/03/30/2023033011303020756.jpg'
							"
							class="img-fluid card-img"
							:alt="node.attrs.alt"
						/>
					</div>
					<div class="col-6">
						<div class="input-group mb-3">
							<button
								class="btn btn-outline-secondary"
								type="button"
								:id="modalId + 'cloud-file-button'"
							>
								从云端选择
							</button>
							<input
								@change="handleInputChange"
								ref="imageInputRef"
								type="file"
								class="form-control"
								:id="modalId + 'local-file'"
								aria-describedby="inputGroupFileAddon03"
								aria-label="Upload"
								placeholder="从本地选择"
								accept="image/*"
							/>
						</div>
						<div class="form-floating mb-3">
							<input
								type="text"
								class="form-control"
								:id="modalId + 'image-title-input'"
								placeholder="标题"
								v-model="title"
							/>
							<label :for="modalId + 'image-title-input'">标题</label>
						</div>
						<div class="form-floating mb-3">
							<textarea
								type="text"
								class="form-control"
								:id="modalId + 'image-alt-input'"
								placeholder="描述"
								style="min-height: 100px"
								v-model="alt"
							></textarea>
							<label :for="modalId + 'image-alt-input'">描述</label>
						</div>
						<div class="mb-3 text-center">
							<div
								class="btn-group"
								role="group"
								aria-label="Basic radio toggle button group"
							>
								<input
									type="radio"
									class="btn-check"
									:id="modalId + 'btnradio1'"
									autocomplete="off"
									v-model="align"
									value="float-start"
								/>
								<label
									class="btn btn-outline-secondary"
									:for="modalId + 'btnradio1'"
								>
									<i class="bi bi-text-left"></i>
								</label>

								<input
									type="radio"
									class="btn-check"
									:id="modalId + 'btnradio2'"
									autocomplete="off"
									v-model="align"
									value="float-none"
								/>
								<label
									class="btn btn-outline-secondary"
									:for="modalId + 'btnradio2'"
								>
									<i class="bi bi-text-center"></i>
								</label>

								<input
									type="radio"
									class="btn-check"
									:id="modalId + 'btnradio3'"
									autocomplete="off"
									v-model="align"
									value="float-end"
								/>
								<label
									class="btn btn-outline-secondary"
									:for="modalId + 'btnradio3'"
								>
									<i class="bi bi-text-right"></i>
								</label>
							</div>
						</div>
					</div>
				</div>
			</template>
		</ModalComponentNew>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
	import ModalComponentNew from '@/component/ModalComponentNew.vue';
	import { ref, watch, onBeforeUnmount, onMounted, nextTick, reactive } from 'vue';
	import { v4 as uuidv4 } from 'uuid';
	import { makeRequest } from '@/service/request.service';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import type { APIResponse, ImageResponse } from '@/type/response.type';
	import { useRoute } from 'vue-router';

	const props = defineProps(nodeViewProps);
	const route = useRoute();
	const src = ref(props.node.attrs.src);
	const title = ref(props.node.attrs.title || '');
	const alt = ref('');
	const modalId = uuidv4();
	const imageRef = ref();
	const align = ref<'float-start' | 'float-end' | 'float-none'>('float-none');
	const imageId = ref<number | null>(props.node.attrs.imageId);
	const previewUrl = ref<string>('');
	const imageInputRef = ref<HTMLInputElement>();
	const selectedLocalImage = ref<File | null>(null);

	const handleInputChange = () => {
		if (imageInputRef.value) {
			const files = imageInputRef.value.files;
			if (files?.length) {
				const currentFile = files[0];
				selectedLocalImage.value = currentFile;
				const reader = new FileReader();
				reader.onload = () => {
					previewUrl.value = reader.result as string;
				};
				reader.readAsDataURL(currentFile);
			}
		}
	};
	const handleSaveChange = async () => {
		if (selectedLocalImage.value) {
			const formData = new FormData();
			formData.append('file', selectedLocalImage.value);
			formData.append('title', title.value);
			formData.append('alt', alt.value);
			const uploadImageResponse: APIResponse<ImageResponse> = await makeRequest(
				RESOURCE_BASE_URL + '/images/new',
				{
					method: 'POST',
					body: formData,
				}
			);
			const imageResponse = uploadImageResponse.data;
			src.value = imageResponse.urls.MEDIUM;
			imageId.value = imageResponse.id;
			selectedLocalImage.value = null;
			linkToArticle(imageResponse.id);
		}
		updateAtrri();
		hideModal();
	};
	const updateAtrri = () => {
		props.updateAttributes({
			title: title.value,
			alt: alt.value,
			src: src.value,
			align: align.value,
			imageId: imageId.value,
		});
	};

	const linkToArticle = async (imageId: number) => {
		await makeRequest(
			RESOURCE_BASE_URL + `/images/link-to-article/${imageId}/${route.params.id}`,
			{
				method: 'PATCH',
			}
		);
	};

	const unLinkToArticle = async (imageId: number) => {
		await makeRequest(
			RESOURCE_BASE_URL + `/images/unlink-to-article/${imageId}/${route.params.id}`,
			{
				method: 'PATCH',
			}
		);
	};

	const openModal = () => {
		imageRef.value.show();
	};

	const hideModal = () => {
		imageRef.value.hide();
	};

	onMounted(() => {
		console.log('onMounted');
		nextTick(() => {
			if (src.value) {
				console.log('nextTick');
				if (imageId.value) {
					linkToArticle(imageId.value);
				}
			} else {
				console.log('UnnextTick');
				openModal();
			}
		});
	});

	onBeforeUnmount(() => {
		console.log('onBeforeUnmount');
		if (imageId.value) {
			unLinkToArticle(imageId.value);
		}
	});
</script>
