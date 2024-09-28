<template>
	<div class="row">
		<label :for="id" class="col-sm-2 col-form-label">{{ labelName }}</label>
		<div class="col-sm-9">
			<input
				v-if="inputType == 'text'"
				type="text"
				:readonly="readonlyRef"
				:class="[
					{
						'form-control-plaintext': readonlyRef,
						'form-control': !readonlyRef,
					},
				]"
				:id="id"
				v-model="model"
			/>
			<textarea
				v-if="inputType == 'textarea'"
				:class="[
					{
						'form-control-plaintext': readonlyRef,
						'form-control': !readonlyRef,
					},
				]"
				:id="id"
				rows="3"
				v-model="model"
				:disabled="readonlyRef"
				:readonly="readonlyRef"
				:style="{ resize: readonlyRef ? 'none' : 'initial' }"
			></textarea>
			<select
				v-if="inputType === 'select'"
				:class="[
					{
						'form-control-plaintext': readonlyRef,
						'form-select': !readonlyRef,
					},
				]"
				:id="id"
				:disabled="readonlyRef"
				v-model="model"
				:style="{ appearance: readonlyRef ? 'none' : 'initial' }"
			>
				<option v-for="t in selectOptions" :value="t" :selected="t === model">
					{{ t }}
				</option>
			</select>
		</div>
		<div class="col-sm-1 text-end">
			<button
				type="button"
				class="btn btn-link p-1"
				ref="tooltip"
				:data-bs-title="readonlyRef ? '编辑' : '重置'"
				data-bs-toggle="tooltip"
				data-bs-placement="top"
				@click="handleClicked"
			>
				<i
					class="bi"
					:class="{
						'bi-x-lg': !readonlyRef,
						'bi-pencil-square': readonlyRef,
					}"
				></i>
			</button>
		</div>
	</div>
</template>
<script setup lang="ts">
	import { onBeforeUnmount, onMounted, ref, watch } from 'vue';
	import { v4 as uuidv4 } from 'uuid';
	import * as bootstrap from 'bootstrap';

	const id = uuidv4();
	const model = defineModel<string>();
	const emits = defineEmits(['edit', 'reset']);
	const tooltip = ref<HTMLElement | null>(null);
	let tooltipInstance: bootstrap.Tooltip | null = null;
	const props = defineProps({
		labelName: {
			type: String,
			required: true,
		},
		readonly: {
			type: Boolean,
			required: true,
		},
		inputType: {
			type: String,
			default: 'text',
			validator: (value: string) => {
				return ['text', 'textarea', 'select'].includes(value);
			},
		},
		selectOptions: {
			type: Array as () => string[],
			default: () => [],
			required: false,
		},
	});
	const readonlyRef = ref(props.readonly);

	const handleClicked = () => {
		if (readonlyRef.value) {
			readonlyRef.value = false;
			emits('edit');
		} else {
			readonlyRef.value = true;
			emits('reset');
		}
	};

	watch(
		() => props.readonly,
		() => {
			readonlyRef.value = props.readonly;
		}
	);

	watch(
		() => props.inputType,
		newValue => {
			if (newValue === 'select' && props.selectOptions.length === 0) {
				console.warn('When inputType is "select", selectOptions cannot be empty.');
			}
		},
		{
			immediate: true,
		}
	);

	watch(readonlyRef, () => {
		if (tooltip.value) {
			const tooltipInstance = bootstrap.Tooltip.getInstance(tooltip.value);
			if (tooltipInstance) {
				tooltipInstance.setContent({
					'.tooltip-inner': readonlyRef.value ? '编辑' : '重置',
				});
			}
		}
	});

	onMounted(() => {
		if (tooltip.value) {
			tooltipInstance = new bootstrap.Tooltip(tooltip.value);
		}
	});

	onBeforeUnmount(() => {
		if (tooltipInstance) {
			tooltipInstance.dispose();
			tooltipInstance = null;
		}
	});
</script>
