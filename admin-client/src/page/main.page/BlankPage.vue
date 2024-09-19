<template>
	<!-- 一系列触发模态框的按钮 -->
	<button v-for="item in items" :key="item.id" @click="openModal(item)">
		{{ item.name }}
	</button>

	<!-- 模态框组件 -->
	<ModAlComponentNew
		ref="modal"
		:modalId="'dynamicModal'"
		:title="modalTitle"
		@saveChange="handleSave"
	>
		<template #body>
			{{ modalContent }}
		</template>
	</ModAlComponentNew>
</template>

<script setup lang="ts">
	import { ref } from 'vue';

	// 模态框的动态内容
	const modalTitle = ref('模态框标题');
	const modalContent = ref('模态框内容');

	// 列表数据
	const items = ref([
		{ id: 1, name: 'Item 1', content: '内容 1' },
		{ id: 2, name: 'Item 2', content: '内容 2' },
		{ id: 3, name: 'Item 3', content: '内容 3' },
	]);

	// 引用模态框组件
	const modal = ref();

	// 点击按钮时更新模态框内容并显示模态框
	const openModal = (item: any) => {
		modalTitle.value = item.name;
		modalContent.value = item.content;
		modal.value.show();
	};

	// 保存回调
	const handleSave = () => {
		alert('保存成功');
		modal.value.hide();
	};
</script>
