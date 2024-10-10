<template>
	<div class="app sidebar-mini" :class="{ 'sidenav-toggled': Triggered }">
		<!-- Navbar-->
		<header class="app-header">
			<!-- App header logo -->
			<a class="app-header__logo" href="index.html">Vali</a>
			<!-- Sidebar toggle button-->
			<a
				class="app-sidebar__toggle"
				href="#"
				data-toggle="sidebar"
				aria-label="Hide Sidebar"
				@click="Triggered = !Triggered"
			></a>
			<!-- Navbar Right Menu-->
			<NavbarComponent />
		</header>
		<!-- Sidebar menu-->
		<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
		<aside class="app-sidebar">
			<div class="app-sidebar__user">
				<img
					class="app-sidebar__user-avatar"
					:src="user.avatar || 'https://randomuser.me/api/portraits/men/1.jpg'"
					alt="User Image"
				/>
				<div>
					<p class="app-sidebar__user-name">{{ user.penName || 'John Doe' }}</p>
					<!-- <p class="app-sidebar__user-designation">Frontend Developer</p> -->
				</div>
			</div>
			<SidebarMenuComponent />
		</aside>
		<main class="app-content overflow-y-auto">
			<RouterView />
		</main>
	</div>
</template>

<script lang="ts" setup>
	import { onMounted, reactive, ref } from 'vue';
	import SidebarMenuComponent from '@/component/SidebarMenuComponent.vue';
	import NavbarComponent from '@/component/NavbarComponent.vue';
	import type { APIResponse, AvatarInfo, UserInfo } from '@/type/response.type';
	import { makeRequest } from '@/service/request.service';
	import { RESOURCE_BASE_URL } from '@/config/global.config';

	const Triggered = ref<boolean>(false);
	const user = reactive({
		penName: '',
		avatar: '',
	});

	onMounted(async () => {
		const userInfoResponse: APIResponse<UserInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/users/info'
		);
		if (userInfoResponse.code === 0) {
			user.penName = userInfoResponse.data.penName;

			const avatarResponse: APIResponse<AvatarInfo> = await makeRequest(
				userInfoResponse.data.avatarInfoLocation
			);
			if (avatarResponse.code === 0) {
				user.avatar = avatarResponse.data.url;
			}
		}
	});
</script>
