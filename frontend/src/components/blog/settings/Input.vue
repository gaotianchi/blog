<script setup lang="ts">
	const props = defineProps<{
		name: string;
		id?: string;
		maxLength: number;
	}>();
	const model = defineModel<string | undefined>();
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
		/>
		<div class="parent-41PsiPZ5Jx">
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
