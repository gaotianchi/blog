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

		<ModalComponent
			@save-change="handleSaveChange"
			title="编辑插图"
			ref="modalRef"
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
								v-model="illustartion.title"
								:key="illustartion.title"
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
								v-model="illustartion.alt"
								:key="illustartion.alt"
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
									v-model="illustartion.align"
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
									v-model="illustartion.align"
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
									v-model="illustartion.align"
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
		</ModalComponent>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper, type Primitive } from '@tiptap/vue-3';
	import ModalComponent from '@/component/ModalComponent.vue';
	import { ref, onBeforeUnmount, onMounted, nextTick, reactive, watch } from 'vue';
	import { v4 as uuidv4 } from 'uuid';
	import { makeRequest } from '@/service/request.service';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import type { APIResponse, IllustrationInfo } from '@/type/response.type';
	import { useRoute } from 'vue-router';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';

	const props = defineProps(nodeViewProps);
	const modalId = uuidv4();
	const route = useRoute();
	const modalRef = ref();

	const previewUrl = ref<string>('');
	const imageInputRef = ref<HTMLInputElement>();
	const selectedLocalImage = ref<File | null>(null);

	const illustartion = reactive({
		title: props.node.attrs.title,
		alt: props.node.attrs.alt,
		align: props.node.attrs.align,
		id: props.node.attrs.id,
		src: props.node.attrs.src,
	});
	const changed = reactive({
		title: false,
		alt: false,
	});

	const illustrationInfo = ref<IllustrationInfo | null>(null);

	const handleSaveChange = async () => {
		// 选择了新图片
		// 更新云端和本地的 src, title, alt, id
		if (selectedLocalImage.value) {
			const formData = new FormData();
			formData.append('file', selectedLocalImage.value);
			formData.append('title', illustartion.title || '');
			formData.append('alt', illustartion.alt || '');
			const newIllustrationResponse = await saveNewIllustration(formData);

			if (newIllustrationResponse.code === 0) {
				const linkToArticleResponse: APIResponse<IllustrationInfo> =
					await addIllustrationToArticle(newIllustrationResponse.data.id);
				if (linkToArticleResponse.code === 0) {
					illustrationInfo.value = linkToArticleResponse.data;
					illustartion.src = illustrationInfo.value.url;
					illustartion.id = illustrationInfo.value.id;
					showMessage('插图上传成功', AlertType.SUCCESS);
				}
			} else {
				showMessage('插图上传失败', AlertType.ERROR);
			}
			selectedLocalImage.value = null;
		} else if (changed.title || changed.alt) {
			const response = await updateIllustrationInfo(illustartion.title, illustartion.alt);
			if (response.code === 0) {
				showMessage('更新成功', AlertType.SUCCESS);
			} else {
				showMessage('更新失败', AlertType.ERROR);
			}
		}

		// 没有任何更改
		// 不需要更新云端数据

		updateAtrri();
		hideModal();
	};

	async function saveNewIllustration(formData: FormData): Promise<APIResponse<IllustrationInfo>> {
		const newIllustrationResponse: APIResponse<IllustrationInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/illustrations/new',
			{
				method: 'POST',
				body: formData,
			}
		);
		return newIllustrationResponse;
	}

	async function addIllustrationToArticle(
		illustrationId: number
	): Promise<APIResponse<IllustrationInfo>> {
		const linkToArticleResponse: APIResponse<IllustrationInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/illustration/' + route.params.id + '/' + illustrationId,
			{
				method: 'POST',
			}
		);
		return linkToArticleResponse;
	}

	async function updateIllustrationInfo(newTitle: string, newAlt: string) {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/illustrations/info/' + illustartion.id,
			{
				method: 'PATCH',
				body: JSON.stringify({
					title: newTitle,
					alt: newAlt,
				}),
			}
		);
		return response;
	}

	async function removeIllustrationFromCurrentArticle(
		illustrationId: number
	): Promise<APIResponse<void>> {
		const response: APIResponse<void> = await makeRequest(
			RESOURCE_BASE_URL + '/articles/illustration/' + route.params.id + '/' + illustrationId,
			{
				method: 'DELETE',
			}
		);
		return response;
	}

	const updateAtrri = () => {
		props.updateAttributes({
			title: illustartion.title,
			alt: illustartion.alt,
			align: illustartion.align,
			src: illustartion.src,
			id: illustartion.id,
		});
	};

	const openModal = () => {
		modalRef.value.show();
	};

	const hideModal = () => {
		modalRef.value.hide();
	};

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

	onMounted(() => {
		console.log('onMounted');
		nextTick(() => {
			if (illustartion.src) {
				console.log('nextTick');
				if (illustartion.id) {
					// addIllustrationToArticle(illustartion.id);
				}
			} else {
				console.log('UnnextTick');
				openModal();
			}
		});
	});

	onBeforeUnmount(() => {
		console.log('onBeforeUnmount');
		if (illustartion.id) {
			// removeIllustrationFromCurrentArticle(illustartion.id);
		}
	});

	watch(
		() => illustartion.title,
		newValue => {
			changed.title = newValue != props.node.attrs.title;
		}
	);
	watch(
		() => illustartion.alt,
		newValue => {
			changed.alt = newValue != props.node.attrs.alt;
		}
	);
</script>
