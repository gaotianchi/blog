<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import { authFormDataValidate } from "@/components/auth/schemas";
	import Ajv, { type JSONSchemaType } from "ajv";
	const ajv = new Ajv();
	const result = ref("");
	const data = {
		username: "gaotianchi",
		password: "gaotianchi123",
	};
	const valid = authFormDataValidate(data);
	if (valid) {
		result.value = "hello world"
	} else {
		result.value = validate.error
	}
</script>

<template>
	{{ result }}
</template>
<style scoped></style>
