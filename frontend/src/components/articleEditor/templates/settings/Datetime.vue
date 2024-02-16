<script setup lang="ts">
	import { reactive, ref, type Ref } from "vue";
	import DatePicker from "@vuepic/vue-datepicker";
	import "@vuepic/vue-datepicker/dist/main.css";
	import Radio from "../Radio.vue";
	import { localArticle, settingStatus } from "../../localApi";
	import { remoteArticle } from "../../remoteApi";
	import type { DatePickerInstance } from "@vuepic/vue-datepicker";
	import { dateFormatter } from "../../utlis";
	const currentArticle = reactive(localArticle);
	const datepicker: Ref<DatePickerInstance> = ref(null);
	function openPicker(): void {
		if (datepicker.value) {
			datepicker.value.openMenu();
		}
	}
	function resetDate(): void {
		currentArticle.publishedAt = remoteArticle.publishedAt;
	}
</script>
<template>
	<div class="parent-NJdf_pPiJl">
		<div class="parent-NJtmK6vs1g">
			<DatePicker
				v-if="settingStatus.datetime.mode === 'default'"
				v-model="remoteArticle.publishedAt"
				:format="dateFormatter"
				input-class-name="dp-readonly-input"
				readonly
			/>
			<DatePicker
				v-show="settingStatus.datetime.mode === 'custom'"
				v-model="currentArticle.publishedAt"
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
				name="default"
				value="default"
				v-model="settingStatus.datetime.mode"
				@selected="resetDate"
				>Default</Radio
			>
			<Radio
				name="custom"
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
