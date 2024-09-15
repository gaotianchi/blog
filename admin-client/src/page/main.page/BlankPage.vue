<template>
	<MainPageHeaderComponent
		title="空白页"
		subTitle="这是一个空白页，用来测试程序是否正常"
		bsIconName="bi-file-earmark"
		:breadcrumbItems="[{ name: '空白页', routeName: 'BLANK' }]"
	/>

	<div class="row tile">
		<div class="col-md-8 align-content-center">
			<div>Create a beautiful dashboard</div>
		</div>
		<div class="col-md-4 align-content-center">
			<button
				type="button"
				@click="showMessage('SUCCESS', AlertType.SUCCESS)"
				class="btn btn-success m-1"
			>
				SUCCESS
			</button>
			<button
				type="button"
				@click="showMessage('ERROR', AlertType.ERROR)"
				class="btn btn-danger m-1"
			>
				ERROR
			</button>
		</div>
	</div>
	<div class="row tile">
		<ModalComponent modalId="basicModal" />
		<AvatarEditorComponent />
	</div>
</template>
<script lang="ts" setup>
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import ModalComponent from '@/component/ModalComponent.vue';
	import AvatarEditorComponent from '@/component/AvatarEditorComponent.vue';
</script>
