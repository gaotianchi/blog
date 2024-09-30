<template>
	<div class="container">
		<div class="row">
			<!-- 头像 -->
			<!-- <AvatarTileComponent :user="currentUser" /> -->
		</div>
		<div class="row">
			<div class="tile">
				<div class="tile-title">系列封面</div>
				<div class="tile-body"></div>
				<div class="tile-footer"></div>
			</div>
		</div>
		<div class="row">
			<div class="tile">
				<div class="tile-title">文章图片</div>
				<div class="tile-body">
					<div class="container" v-masonry>
						<div
							v-masonry-tile
							class="m-1"
							:class="`'image-'${index}`"
							v-for="(u, index) in []"
							:key="index"
						>
							<img :src="u" alt="image1" loading="lazy" width="300" />
						</div>
					</div>
				</div>
				<div class="tile-footer"></div>
			</div>
		</div>
	</div>
</template>
<script setup lang="ts">
	// import AvatarTileComponent from '@/component/picture.component/AvatarTileComponent.vue';
	// import { RESOURCE_BASE_URL } from '@/config/global.config';
	// import { makeRequest } from '@/service/request.service';
	// import type { APIResponse, UserResponse } from '@/type/response.type';
	// import { onMounted, ref } from 'vue';

	// const currentUser = ref<UserResponse | null>(null);
	// onMounted(async () => {
	// 	const userInfoResponse: APIResponse<UserResponse> = await makeRequest(
	// 		RESOURCE_BASE_URL + '/users/get-info'
	// 	);
	// 	currentUser.value = userInfoResponse.data;
	// });
</script>
