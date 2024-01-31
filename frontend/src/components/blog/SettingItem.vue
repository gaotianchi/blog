<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import icons from "@/components/icons";

	const active: Ref<boolean> = ref(false);
	const changeStatus = () => {
		active.value = !active.value;
	};
</script>

<template>
	<div class="parent-NkzaO6RYJl">
		<button
			type="button"
			class="child-N1ys-00Fke parent-NyM7s6AK1l"
			:class="active"
			@click="changeStatus"
		>
			<component
				:is="icons.down"
				class="child-VkrD6pCK1l parent-Nyg6a7gcyg icon smallest"
				:class="{ active: active }"
			/>
			<div class="child-VkrD6pCK1l parent-NJ5vAaAFke">
				<div class="child-Ek0lyC0K1g parent-V1pmRXl5yl">
					<slot name="title">Title</slot>
				</div>
				<div class="child-Ek0lyC0K1g parent-4JUNCmx5kg" v-if="!active">
						<slot name="preview">Preview</slot>
				</div>
			</div>
		</button>
		<div class="child-N1ys-00Fke" v-if="active">
			<slot name="detail">Detail</slot>
		</div>
	</div>
</template>

<style scoped>
	.parent-NkzaO6RYJl {
		width: 100%;
		min-height: 40px;
		margin-bottom: 20px;
		border-bottom: rgba(128, 128, 128, 0.514) solid 1px;
	}

	.parent-NyM7s6AK1l {
		width: 100%;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		cursor: pointer;
		border: none;
		background-color: white;
	}

	.parent-Nyg6a7gcyg {
		transform: rotate(-90deg);
		margin-right: 10px;
		transition: all 0.3s ease;
	}

	.parent-Nyg6a7gcyg.active {
		transform: none;
	}

	.parent-NJ5vAaAFke {
		flex-grow: 1;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: flex-start;
	}

	.child-Ek0lyC0K1g {
		text-align: left;
	}

	.parent-V1pmRXl5yl {
		font-size: 1rem;
		font-weight: 400;
	}

	.parent-4JUNCmx5kg {
		font-size: smaller;
		font-style: italic;
	}
</style>
