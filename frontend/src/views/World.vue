<script setup lang="ts">
	import { reactive, ref, type Ref } from "vue";
	import BlogHeader from "@/components/BlogHeader.vue";
	import icons from "@/components/icons";
	import { dateFormatter } from "@/utlis";
	import moment from "moment-timezone";
	const utcDateString = "2024-02-16T04:30:59+0000";
	function getLocalDatetime(dateString: string): Date {
		const localizedMoment = moment.utc(dateString).local();
		console.log(localizedMoment.toDate());
		return localizedMoment.toDate();
	}
	function serializeDate(d: Date): string {
		const utcMoment = moment.utc(d);
		return utcMoment.format("YYYY-MM-DDTHH:mm:ssZZ");
	}
</script>

<template>
	<BlogHeader />
	{{ getLocalDatetime(utcDateString) }}
</template>
<style scoped></style>
