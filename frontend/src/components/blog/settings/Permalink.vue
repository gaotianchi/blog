<script setup lang="ts">
	import { computed, ref, watchEffect, type Ref } from "vue";
	import { getUnixTime } from "date-fns";
	import Radio from "./Radio.vue";
	import Input from "./Input.vue";
	const props = defineProps<{
		oldSlug?: string;
		articleTitle?: string;
	}>();
	const emits = defineEmits<{
		updatePermalink: [permalink: string];
	}>();
	function cleanSlug(str: string): string {
		return (
			str
				.toLowerCase()
				.replace(/[^a-z0-9-]/g, "-")
				// .replace(/-+/g, "-")
				// .replace(/^-|-$/g, "")
				.slice(0, 8000)
		);
	}
	const auto: Ref<boolean> = ref(true);
	const autoPermalinkSlug = computed<string>(() => {
		const autoSlug = cleanSlug(props.articleTitle || "");
		const timestamp = getUnixTime(new Date()).toString();
		if (autoSlug.length > 0) {
			return autoSlug + timestamp;
		} else {
			return "blog-post-" + timestamp;
		}
	});
	const customSlug: Ref<string> = ref(autoPermalinkSlug.value);
	watchEffect(() => {
		const cleanedSlug = cleanSlug(customSlug.value);
		customSlug.value = cleanedSlug;
	});
	const currentPermalink = computed<string>(() => {
		const rootUrl = "https://gaotianchi.com/";
		if (auto.value) {
			const permalink = rootUrl + autoPermalinkSlug.value;
			emits("updatePermalink", permalink);
			return permalink;
		} else {
			const permalink = rootUrl + customSlug.value;
			if (customSlug.value.length > 0) {
				emits("updatePermalink", permalink);
				return permalink;
			} else {
				emits("updatePermalink", rootUrl + autoPermalinkSlug.value);
				return rootUrl + autoPermalinkSlug.value;
			}
		}
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
			<Radio name="custom-datetime" :value="false" v-model="auto"
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
