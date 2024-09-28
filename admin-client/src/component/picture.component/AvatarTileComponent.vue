<template>
	<div class="tile">
		<div class="tile-title">头像</div>
		<div class="tile-body">
			<a
				v-for="(u, index) in avatars"
				:name="`'avatar-'${index}`"
				:id="`'avatar-'${index}`"
				class="btn p-0 m-1 rounded"
				href="#"
				role="button"
				@click="openModal(u)"
			>
				<img
					class="rounded"
					style="cursor: pointer"
					:src="u.urls.LOW"
					:alt="u.alt || `'avatar-'${index}`"
					width="100"
				/>
			</a>
		</div>
		<div class="tile-footer"></div>

		<ModalComponentNew
			ref="avatarModal"
			modal-width="modal-lg"
			title="查看头像"
		>
			<template #body>
				<div class="row">
					<div class="col-auto">
						<img
							:src="toggledAvatar?.urls.ORIGINAL"
							class="img-fluid rounded-top"
							:alt="toggledAvatar?.alt || `image-${toggledAvatar?.id}`"
						/>
					</div>
					<div class="col">
						<ul class="list-group list-group-flush">
							<li class="list-group-item">
								<div class="row">
									<div class="col-sm-2 col-form-label">状态</div>
									<div class="col-sm-6 align-content-center">
										<span
											v-if="isUsing"
											class="badge rounded-pill text-bg-primary"
										>
											正在使用
										</span>
										<span
											v-else
											class="badge rounded-pill text-bg-secondary w-auto"
										>
											没有使用
										</span>
									</div>
									<div class="col-sm-4 text-end align-content-center">
										<button
											v-if="!isUsing"
											type="button"
											class="btn btn-danger"
										>
											删除
										</button>
									</div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-sm-2 col-form-label">创建时间</div>
									<div class="col-sm-9">
										{{ toggledAvatar?.creationDatetime }}
									</div>
									<div class="col-sm-1 text-end"></div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-sm-2 col-form-label">修改时间</div>
									<div class="col-sm-9">
										{{ toggledAvatar?.updateDatetime }}
									</div>
									<div class="col-sm-1 text-end"></div>
								</div>
							</li>
							<li class="list-group-item">
								<div class="row">
									<label for="image-alt" class="col-sm-2 col-form-label">
										文字说明
									</label>
									<div class="col-sm-9">
										<input
											type="text"
											:readonly="altReadlyOnly"
											:class="[
												{
													'form-control-plaintext': altReadlyOnly,
													'form-control': !altReadlyOnly,
												},
											]"
											id="image-alt"
											name="image-alt"
											v-model="currentAlt"
											:placeholder="
												toggledAvatar?.alt ? '' : '暂时没有文字说明'
											"
										/>
									</div>
									<div class="col-sm-1 text-end">
										<EditorButtonComponent
											:readingState="altReadlyOnly"
											@edit="
												() => {
													altReadlyOnly = false;
												}
											"
											@reset="
												() => {
													altReadlyOnly = true;
													currentAlt = toggledAvatar?.alt || '';
												}
											"
										/>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</template>
		</ModalComponentNew>
	</div>
</template>
<script setup lang="ts">
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, ImageResponse, UserResponse } from '@/type/response.type';
	import { computed, onMounted, ref } from 'vue';

	import EditorButtonComponent from '../setting.component/EditorButtonComponent.vue';

	import ModalComponentNew from '@/component/ModalComponentNew.vue';

	const props = defineProps<{
		user: UserResponse | null;
	}>();

	const altReadlyOnly = ref(true);
	const currentAlt = ref('');
	const avatarModal = ref();
	const avatars = ref<ImageResponse[]>([]);
	const toggledAvatar = ref<ImageResponse | null>(null);
	const isUsing = computed(() => {
		return toggledAvatar.value?.id === props.user?.avatar?.id;
	});

	const openModal = (image: ImageResponse) => {
		toggledAvatar.value = image;
		avatarModal.value.show();
	};
	onMounted(async () => {
		const url = new URL(RESOURCE_BASE_URL + '/images/all');
		url.searchParams.append('field', 'avatar');
		url.searchParams.append('page', '0');
		const avatarsResponse: APIResponse<ImageResponse[]> = await makeRequest(url.toString());
		avatars.value = avatarsResponse.data;
	});
</script>
