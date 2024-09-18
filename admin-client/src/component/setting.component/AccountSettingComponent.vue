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
				<li class="list-group-item">
					<div class="row">
						<div class="col-sm-2 col-form-label align-content-center">
							<span>邮箱</span>
						</div>
						<div class="col-sm-9 align-content-center">
							<span>{{ currentAccountData?.username }}</span>
						</div>
						<div class="col-sm-1 text-end">
							<button type="button" class="btn btn-link p-1">
								<i class="bi bi-pencil-square"></i>
							</button>
						</div>
					</div>
				</li>
				<li class="list-group-item">
					<div class="row">
						<div class="col-sm-2 col-form-label align-content-center">
							<span>密码</span>
						</div>
						<div class="col-sm-9 align-content-center">
							<span>***********</span>
						</div>
						<div class="col-sm-1 text-end">
							<button type="button" class="btn btn-link p-1">
								<i class="bi bi-pencil-square"></i>
							</button>
						</div>
					</div>
				</li>
				<li class="list-group-item">
					<div class="row">
						<div class="col-sm-2 col-form-label align-content-center">
							<span>注册日期</span>
						</div>
						<div class="col-sm-9 align-content-center">
							<span>{{ currentAccountData?.registrationDateTime }}</span>
						</div>
						<div class="col-sm-1 text-end"></div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</template>
<script setup lang="ts">
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, UserAccountResponse } from '@/type/response.type';
	import { onMounted, ref } from 'vue';

	const loading = ref(true);
	const currentAccountData = ref<UserAccountResponse | null>(null);
	onMounted(async () => {
		const response: APIResponse<UserAccountResponse> = await makeRequest(
			'/users/get-info',
			'auth'
		);
		console.log(response);
		currentAccountData.value = response.data;
		loading.value = false;
	});
</script>
