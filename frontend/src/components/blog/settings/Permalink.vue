<script setup lang="ts">
	import { ref, type Ref, computed, watchEffect } from "vue";
	import Radio from "./Radio.vue";
	import Input from "./Input.vue";
	const props = defineProps<{
		permalink: string;
	}>();
	const emits = defineEmits<{
		updatePermalink: [permalink: string];
	}>();

	const auto: Ref<boolean> = ref(true);

	const customSlug: Ref<string> = ref("");
	function cleanSlug(str: string): string {
		return str
			.toLowerCase()
			.replace(/[^a-z0-9-]/g, "-")
			.slice(0, 8000);
	}
	watchEffect(() => {
		const cleanedSlug = cleanSlug(customSlug.value);
		customSlug.value = cleanedSlug;
	});
	const currentPermalink = computed<string>(() => {
		let result: string;
		if (auto.value) {
			result = props.permalink;
		} else {
			if (customSlug.value) {
				result = customSlug.value;
			} else {
				result = props.permalink;
			}
		}
		emits("updatePermalink", result);
		return result;
	});
</script>

<template>
	<div class="parent-V1Qz5vW5yl">
		<div class="child-4J9WMcl9Je parent-EJ5IqDbqkl">
			{{ currentPermalink }}
		</div>
		<div class="child-4J9WMcl9Je parent-VyzXfqx9Je">
			<Radio name="auto-permalink" :value="true" v-model="auto"
				>Auto</Radio
			>
			<Radio name="custom-permalink" :value="false" v-model="auto"
				>Custom</Radio
			>
		</div>
		<Input
			v-if="!auto"
			name="slug-input-area"
			v-model="customSlug"
			:max-length="8000"
		/>
	</div>
</template>

<style scoped>
	.parent-V1Qz5vW5yl {
		width: 100%;
	}
	.child-4J9WMcl9Je {
		width: 100%;
	}
	.parent-EJ5IqDbqkl {
		min-height: 40px;
		max-height: 60px;
		overflow-y: scroll;
		word-wrap: break-word;
		line-height: 20px;
		text-align: left;
		font-size: small;
		color: grey;
	}
</style>
