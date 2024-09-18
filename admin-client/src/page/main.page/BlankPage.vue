<template>
	<MainPageHeaderComponent
		title="空白页"
		subTitle="这是一个空白页，用来测试程序是否正常"
		bsIconName="bi-file-earmark"
		:breadcrumbItems="[{ name: '空白页', routeName: 'BLANK' }]"
	/>
	<div class="container" v-masonry>
		<div
			v-masonry-tile
			class="m-1"
			:class="`'image-'${index}`"
			v-for="(u, index) in urls"
			:key="index"
		>
			<img :src="u" alt="image1" loading="lazy" width="300" />
		</div>
	</div>
</template>
<script lang="ts" setup>
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	const urls = [
		'https://via.placeholder.com/300x250',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/300x250',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
	];
</script>
