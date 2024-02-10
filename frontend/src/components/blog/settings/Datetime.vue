<script setup lang="ts">
	import { ref, type Ref, watch, watchEffect, computed } from "vue";
	import Datepicker from "vue3-datepicker";
	import { format } from "date-fns";
	import Radio from "./Radio.vue";
	const props = defineProps<{
		datetime: Date;
	}>();
	const emits = defineEmits<{
		updateDatetime: [newDatetime: Date];
	}>();
	const auto: Ref<boolean> = ref(true);
	const currentDatetime: Ref<Date> = ref(props.datetime);
	const datePicker: Ref<HTMLElement | null> = ref(null);
	watch(datePicker, () => {
		datePicker.value?.focus();
	});
	watchEffect(() => {
		emits("updateDatetime", currentDatetime.value);
	});
</script>
<template>
	<div class="parent-E14-f9e9ye">
		<div class="child-4J9WMcl9Je parent-VyzXfqx9Je">
			<Radio
				name="auto-datetime"
				:value="true"
				v-model="auto"
				@selected="currentDatetime = datetime"
				>Auto</Radio
			>
			<Radio
				name="custom-datetime"
				:value="false"
				v-model="auto"
				@selected="currentDatetime = datetime"
				>Custom</Radio
			>
		</div>
		<div class="child-4J9WMcl9Je parent-NybiMqg51e" v-if="auto">
			{{ format(currentDatetime, "yyyy / MM / dd HH:mm") }}
		</div>
		<div class="child-4J9WMcl9Je parent-41dvG5l91g" v-if="!auto">
			<Datepicker
				:style="'border:none;border-bottom: var(--second-color) solid 1px;font-style:italic;padding:0;width:100%;font-size:small;letter-spacing:3px;'"
				v-model="currentDatetime"
				minimum-view="time"
				typeable
				input-format="yyyy / MM / dd HH:mm"
				ref="datePicker"
			/>
		</div>
	</div>
</template>
<style scoped>
	.parent-E14-f9e9ye {
		width: 100%;
	}

	.parent-VyzXfqx9Je {
		margin: 10px 0;
	}

	.child-4J9WMcl9Je {
		width: 100%;
	}

	.parent-NybiMqg51e {
		width: 100%;
		font-style: italic;
		font-weight: 480;
		font-size: small;
		letter-spacing: 3px;
		padding-top: 1px;
	}
</style>
