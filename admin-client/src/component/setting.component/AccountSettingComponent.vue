<template>
	<div class="tile">
		<div class="overlay z-3" v-if="loading">
			<div class="m-loader mr-4">
				<svg class="m-circular" viewBox="25 25 50 50">
					<circle
						class="path"
						cx="50"
						cy="50"
						r="20"
						fill="none"
						stroke-width="4"
						stroke-miterlimit="10"
					></circle>
				</svg>
			</div>
			<h3 class="l-text">正在加载 ...</h3>
		</div>
		<div class="tile-title">账户信息</div>
		<div class="tile-body row">
			<ul class="list-group list-group-flush">
				<li class="list-group-item">An item</li>
				<li class="list-group-item">A second item</li>
				<li class="list-group-item">A third item</li>
				<li class="list-group-item">A fourth item</li>
				<li class="list-group-item">And a fifth one</li>
			</ul>
		</div>
	</div>
</template>
<script setup lang="ts">
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, UserAccountResponse } from '@/type/response.type';
	import { onMounted, ref } from 'vue';

	const loading = ref(true);
	// onMounted(async () => {
	// 	const response: APIResponse<UserAccountResponse> = await makeRequest(
	// 		'/users/get-info',
	// 		'auth'
	// 	);
	// 	console.log(response);
	// });
</script>
