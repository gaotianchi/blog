<template>
	<div>正在登录...</div>
</template>

<script setup lang="ts">
	import { onMounted } from 'vue';
	import { useRouter } from 'vue-router';
	import authService from '../../service/auth.service';

	const router = useRouter();
	onMounted(async () => {
		try {
			await authService.handleRedirectCallback(); // 处理重定向
			router.push('/'); // 登录成功后重定向到主页
		} catch (error) {
			console.error('处理重定向时出错:', error);
		}
	});
</script>
