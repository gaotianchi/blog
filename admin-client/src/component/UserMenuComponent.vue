<template>
	<li class="dropdown">
		<a class="app-nav__item" href="#" data-bs-toggle="dropdown" aria-label="Open Profile Menu">
			<i class="bi bi-person fs-4"></i>
		</a>
		<ul class="dropdown-menu settings-menu dropdown-menu-right">
			<li v-for="i in userNavBarItems">
				<RouterLink class="dropdown-item" :to="{ name: i.routeName }">
					<i class="bi me-2 fs-5" :class="i.bsItemName"></i>
					{{ i.title }}
				</RouterLink>
			</li>
		</ul>
	</li>
</template>
<script lang="ts" setup>
	import { userNavBarItems } from '@/const/navbar.const';
</script>
