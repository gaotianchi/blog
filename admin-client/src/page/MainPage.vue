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
					src="https://randomuser.me/api/portraits/men/1.jpg"
					alt="User Image"
				/>
				<div>
					<p class="app-sidebar__user-name">John Doe</p>
					<p class="app-sidebar__user-designation">Frontend Developer</p>
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
	import { ref } from 'vue';
	import SidebarMenuComponent from '@/component/SidebarMenuComponent.vue';
	import NavbarComponent from '@/component/NavbarComponent.vue';

	const Triggered = ref<boolean>(false);
</script>
