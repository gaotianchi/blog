<template>
	<div class="app-title">
		<div>
			<h1>
				<i class="bi" :class="bsIconName || 'bi-file-earmark'"></i>
				{{ title }}
			</h1>
			<p>{{ subTitle }}</p>
		</div>
		<ul class="app-breadcrumb breadcrumb">
			<li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
			<li v-for="b in breadcrumbItems" class="breadcrumb-item">
				<RouterLink :to="{ name: b.routeName }">{{ b.name }}</RouterLink>
			</li>
		</ul>
	</div>
</template>
<script lang="ts" setup>
	import type { BreadcrumbItem } from '@/type/nav.type';

	defineProps<{
		title: string;
		subTitle?: string;
		bsIconName?: string;
		breadcrumbItems: BreadcrumbItem[];
	}>();
</script>
