<script setup lang="ts">
	import { reactive, ref, type Ref } from "vue";
	import Header from "@/components/Header.vue";
	const model = {
		id: "1",
	};
	const hell = reactive(model);
	const worl = reactive(model);
</script>

<template>
	<Header />
	<input type="text" name="model" id="model" v-model="hell.id" />
	{{ worl }}
</template>
<style scoped></style>
