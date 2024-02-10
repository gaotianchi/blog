<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import Radio from "@/components/blog/settings/Radio.vue";
	const model = ref(0);
	function hello(): void {
		model.value += 1;
	}
	const va = ref("");
</script>

<template>
	{{ model }}
	<Radio name="hello" value="hello" v-model="va" @selected="hello"
		>Hello</Radio
	>
	<Radio name="world" value="world" v-model="va">World</Radio>
</template>
