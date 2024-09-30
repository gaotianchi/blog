<template>
	<InputComponent
		label-name="测试"
		:readonly="ob.readonly"
		v-model="ob.value"
		@reset="ob.readonly = true"
		@edit="ob.readonly = false"
	/>
	<button type="button" class="btn btn-primary me-3" @click="handleReset">reset</button>
</template>
<script setup lang="ts">
	import InputComponent from '@/component/InputComponent.vue';
	import { reactive } from 'vue';
	const ob = reactive({
		readonly: true,
		value: '测试',
	});
	const handleReset = () => {
		ob.readonly = true;
	};
</script>
