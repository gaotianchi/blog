<script setup lang="ts">
	import { reactive, ref, type Ref } from "vue";
	import Header from "@/components/Header.vue";
	import icons from "@/components/icons";
	import { dateFormatter } from "@/utlis";
	import moment from "moment";
</script>

<template>
	<Header />
	{{ moment(new Date()).format('YYYY-MM-DDTHH:mm:ssZZ') }}
</template>
<style scoped></style>
