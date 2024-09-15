<template>
	<MainPageHeaderComponent
		title="空白页"
		subTitle="这是一个空白页，用来测试程序是否正常"
		bsIconName="bi-file-earmark"
		:breadcrumbItems="[{ name: '空白页', routeName: 'BLANK' }]"
	/>

	<div class="row">
		<div class="col-md-12">
			<div class="tile">
				<div class="tile-body">Create a beautiful dashboard</div>
			</div>
		</div>
	</div>
</template>
<script lang="ts" setup>
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
</script>
