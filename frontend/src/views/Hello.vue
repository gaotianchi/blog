<script setup lang="ts">
	import { ref, watch, type Ref, reactive } from "vue";
	import ConfirmPropVue from "@/components/ConfirmProp.vue";
	import type { ConfirmProp } from "@/typing";
	const model = ref("");
	const decision = ref(false);
	const confirmProp: ConfirmProp = reactive({
		active: false,
		header: "Delete Article",
		body: "Are you sure you want to delete the article 'HELLO WORLD'?",
		noMessage: "Cancel",
		yesMessage: "Delete",
		callback: update,
	});
	function update(): void {
		model.value = "hello world";
	}
</script>

<template>
	{{ model }}
	<button type="button" @click="confirmProp.active = true">Toggle</button>
	<ConfirmPropVue
		:confirm-prop="confirmProp"
		@confirm="
			(d) => {
				confirmProp.active = false;
				decision = d;
			}
		"
	/>
</template>

<style scoped></style>
