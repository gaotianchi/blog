<script setup lang="ts">
	import { ref, type Ref, computed, watchEffect, watch } from "vue";
	import Radio from "./Radio.vue";
	import Input from "./Input.vue";
	import { getUnixTime } from "date-fns";
	import type { Article } from "@/typing";
	const props = defineProps<{
		permalink: string;
	}>();
	const emits = defineEmits<{
		updatePermalink: [permalink: string];
	}>();

	const auto: Ref<boolean> = ref(true);
	const customPermalink: Ref<string> = ref("");
	const currentPermalink: Ref<string> = ref(props.permalink);
	watch(customPermalink, () => {
		customPermalink.value = cleanSlug(customPermalink.value);
		if (!customPermalink.value) {
			currentPermalink.value = getRemotePermalink();
		} else {
			currentPermalink.value = customPermalink.value;
		}
	});
	watchEffect(() => {
		emits("updatePermalink", currentPermalink.value);
	});
	function getRemotePermalink(): string {
		const SessionRemoteArticle: Article = JSON.parse(
			sessionStorage.getItem("remoteArticle") as string
		);
		if (SessionRemoteArticle.slug) {
			return SessionRemoteArticle.slug;
		} else {
			return "article_" + getUnixTime(new Date());
		}
	}
	function cleanSlug(str: string): string {
		return str
			.toLowerCase()
			.replace(/[^a-z0-9-]/g, "-")
			.slice(0, 8000);
	}
</script>

<template>
	<div class="parent-V1Qz5vW5yl">
		<div class="child-4J9WMcl9Je parent-EJ5IqDbqkl">
			{{ "https://gaotianchi.com/" + currentPermalink }}
		</div>
		<div class="child-4J9WMcl9Je parent-VyzXfqx9Je">
			<Radio
				name="auto-permalink"
				:value="true"
				v-model="auto"
				@selected="currentPermalink = getRemotePermalink()"
				>Auto</Radio
			>
			<Radio
				name="custom-permalink"
				:value="false"
				v-model="auto"
				@selected="currentPermalink = getRemotePermalink()"
				>Custom</Radio
			>
		</div>
		<Input
			v-if="!auto"
			name="slug-input-area"
			v-model="customPermalink"
			:max-length="8000"
			placeholder="Please enter article permalink"
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
