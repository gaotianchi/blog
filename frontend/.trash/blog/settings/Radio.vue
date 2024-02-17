<script setup lang="ts">
	const model = defineModel<any>();
	defineProps<{
		name: string;
		id?: string;
		value: any;
	}>();
	const emits = defineEmits(["selected"]);
</script>
<template>
	<div class="parent-E1x3c9x9Jg">
		<input
			class="child-N1vpq9lqkx parent-Ny_C5cg5kx"
			type="radio"
			v-model="model"
			:name="name"
			:id="id ? id : name"
			:value="value"
			@input="$emit('selected')"
		/>
		<label class="child-N1vpq9lqkx parent-4yWJs9l9kl" :for="id ? id : name"
			><slot></slot
		></label>
	</div>
</template>
<style scoped>
	.parent-E1x3c9x9Jg {
		width: 100%;
		height: 25px;
		padding-left: 20px;
		display: flex;
		align-items: center;
	}

	.parent-Ny_C5cg5kx {
		width: 16px;
		height: 16px;
	}

	.parent-4yWJs9l9kl {
		padding-left: 10px;
		display: flex;
		align-items: center;
		font-size: small;
	}
</style>
