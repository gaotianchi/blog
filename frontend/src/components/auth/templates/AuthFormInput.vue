<script setup lang="ts">
	import { type Ref, onMounted, ref } from "vue";
	import type { InputStatus } from "../typing";
	const props = defineProps<{
		type: "text" | "password";
		name: string;
		status: InputStatus;
		id?: string;
		placeholder?: string;
		autoFocus?: boolean;
	}>();
	const model = defineModel<string>();
	const htmlInputElement: Ref<HTMLInputElement | null> = ref(null);
	onMounted(() => {
		if (props?.autoFocus) {
			htmlInputElement.value?.focus();
		}
	});
</script>
<template>
	<input
		:type="props.type"
		:name="props.name"
		:id="props.id || props.name"
		:placeholder="props.placeholder || `Enter ${props.name}`"
		:class="[props.status]"
		:aria-label="props.name"
		ref="htmlInputElement"
		v-model="model"
	/>
</template>
<style scoped>
	input,
	input.normal {
		width: 300px;
		height: 40px;
		padding: 0 8px;
		font-size: 1rem;
		border: none;
		outline: #8f8f8f solid 1px;
		transition: all 0.1s linear;
	}
	input.error {
		outline: var(--error-color) solid 2px;
	}
	input.success {
		outline: var(--success-color) solid 2px;
	}
	input:focus,
	input:focus-visible {
		outline-width: 3px;
	}
</style>
