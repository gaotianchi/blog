<script setup lang="ts">
	import { onMounted, ref, watch, type Ref } from "vue";

	const props = defineProps<{
		name: string;
		id?: string;
		maxLength?: number;
		placeholder?: string;
		autoFocus?: boolean;
	}>();
	const model = defineModel<string>();
	const inputArea: Ref<HTMLInputElement | null> = ref(null);
	onMounted(() => {
		if (props.autoFocus) {
			inputArea.value?.focus();
		}
	});
	watch(model, () => {
		if (model.value && props.maxLength) {
			if (model.value.length > props.maxLength) {
				model.value = model.value.slice(0, props.maxLength);
			}
		}
	});
</script>
<template>
	<div class="parent-VJrIoDZ5kx">
		<input
			type="text"
			:name="name"
			:id="id ? id : name"
			:aria-label="id ? id : name"
			v-model="model"
			class="parent-VJYuitZqkl"
			ref="inputArea"
			:placeholder="placeholder"
		/>
		<div class="parent-41PsiPZ5Jx" v-if="maxLength">
			<span class="child-E1gknPb51g parent-V1j1nw-5Jg">{{
				model ? model.length : 0
			}}</span>
			<span class="child-E1gknPb51g parent-Ey-WhwW51x">/</span>
			<span class="child-E1gknPb51g parent-VJrb3PW9Je">{{
				maxLength
			}}</span>
		</div>
	</div>
</template>
<style scoped>
	.parent-VJrIoDZ5kx {
		width: 100%;
	}
	.parent-VJYuitZqkl {
		width: 100%;
		line-height: 20px;
		padding: 0 5px;
		border: none;
		border-bottom: var(--second-color) solid 1px;
	}

	.parent-41PsiPZ5Jx {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}

	.child-E1gknPb51g {
		font-size: small;
	}
</style>
