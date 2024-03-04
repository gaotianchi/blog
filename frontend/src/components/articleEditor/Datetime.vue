<script setup lang="ts">
	import type { DatePickerInstance } from "@vuepic/vue-datepicker";
	import "@vuepic/vue-datepicker/dist/main.css";
	import { ref, type Ref, inject } from "vue";
	import DatePicker from "@vuepic/vue-datepicker";
	import { dateFormatter } from "@/utlis";
	import Radio from "@/components/Radio.vue";
	import { editorLocalAndRemote, settingStatus } from "@/store";
	const articleId = inject("articleId") as number;
	const datepicker: Ref<DatePickerInstance> = ref(null);
	function openPicker(): void {
		if (datepicker.value) {
			datepicker.value.openMenu();
		}
	}
	function resetDate(): void {
		editorLocalAndRemote[articleId].local.publishedAt =
			editorLocalAndRemote[articleId].remote.publishedAt;
	}
</script>
<template>
	<div class="parent-NJdf_pPiJl">
		<div class="parent-NJtmK6vs1g">
			<DatePicker
				v-if="settingStatus.datetime.mode === 'default'"
				v-model="editorLocalAndRemote[articleId].remote.publishedAt"
				:format="dateFormatter"
				input-class-name="dp-readonly-input"
				readonly
			/>
			<DatePicker
				v-show="settingStatus.datetime.mode === 'custom'"
				v-model="editorLocalAndRemote[articleId].local.publishedAt"
				:alt-position="() => ({ top: 88, left: 18 })"
				@cleared="resetDate"
				:format="dateFormatter"
				input-class-name="dp-custom-input"
				ref="datepicker"
			>
				<template #clear-icon="{ clear }"> </template>
			</DatePicker>
		</div>
		<div class="parent-Vy07YaPsJe">
			<Radio
				name="default-datetime"
				value="default"
				v-model="settingStatus.datetime.mode"
				@selected="resetDate"
				>Default</Radio
			>
			<Radio
				name="custom-datetime"
				value="custom"
				v-model="settingStatus.datetime.mode"
				@selected="openPicker"
				>Custom</Radio
			>
		</div>
	</div>
</template>
<style>
	.dp__arrow_top {
		display: none;
	}
	.dp-readonly-input {
		border: none;
		border-radius: 0%;
	}
	.dp-custom-input {
		border: none;
		border-radius: 0%;
		background-color: #eeeeee;
	}
</style>
