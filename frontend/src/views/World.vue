<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import Datetime from "@/components/blog/settings/Datetime.vue";
	const model: Ref<Date | null> = ref(null);
	function changeDatetime(date: Date): void {
		model.value = date;
	}
</script>

<template>
	<Datetime @update-datetime="changeDatetime" />
</template>
