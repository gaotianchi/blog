<script setup lang="ts">
	import { computed, ref, type Ref, watch, onMounted } from "vue";
	import Datepicker from "vue3-datepicker";
	import { format } from "date-fns";
	import Radio from "./Radio.vue";
	const props = defineProps<{
		oldDatetime?: Date;
	}>();
	const emits = defineEmits<{
		updateDatetime: [newDatetime: Date];
	}>();
	const auto: Ref<boolean> = ref(true);
	const customDatetime: Ref<Date> = ref(
		props.oldDatetime ? props.oldDatetime : new Date()
	);
	const currentDatetime = computed<Date>(() => {
		if (auto.value) {
			if (props.oldDatetime) {
				return props.oldDatetime;
			} else {
				return new Date();
			}
		} else {
			return customDatetime.value;
		}
	});
	watch(currentDatetime, () => {
		emits("updateDatetime", currentDatetime.value);
	});
	onMounted(() => {
		emits("updateDatetime", currentDatetime.value);
	});
	const datePicker: Ref<HTMLElement | null> = ref(null);
	watch(datePicker, () => {
		datePicker.value?.focus();
	});
</script>
<template>
	<div class="parent-E14-f9e9ye">
		<div class="child-4J9WMcl9Je parent-VyzXfqx9Je">
			<Radio name="auto-datetime" :value="true" v-model="auto">Auto</Radio>
			<Radio name="custom-datetime" :value="false" v-model="auto">Custom</Radio>
		</div>
		<div class="child-4J9WMcl9Je parent-NybiMqg51e" v-if="auto">
			{{ format(currentDatetime, "yyyy / MM / dd HH:mm") }}
		</div>
		<div class="child-4J9WMcl9Je parent-41dvG5l91g" v-if="!auto">
			<Datepicker
				:style="'border:none;border-bottom: var(--second-color) solid 1px;font-style:italic;padding:0;width:100%;font-size:small;letter-spacing:3px;'"
				v-model="customDatetime"
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
