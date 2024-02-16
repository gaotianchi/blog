<script setup lang="ts">
	import Radio from "../Radio.vue";
	import { localArticle, settingStatus } from "../../localApi";
	import { remoteArticle } from "../../remoteApi";
	import { ref, watch } from "vue";
	import InputA from "../InputA.vue";
	const customSlug = ref("");
	watch(customSlug, () => {
		customSlug.value = cleanSlug(customSlug.value);
		if (
			settingStatus.permalink.mode === "custom" &&
			customSlug.value.length > 0
		) {
			localArticle.slug = customSlug.value;
		} else {
			localArticle.slug = remoteArticle.slug;
		}
	});
	function cleanSlug(str: string): string {
		return str
			.toLowerCase()
			.replace(/[^a-z0-9_-]/g, "")
			.slice(0, 8000);
	}
</script>
<template>
	<div class="parent-V1Qz5vW5yl">
		<div class="child-4J9WMcl9Je parent-EJ5IqDbqkl">
			{{ "https://gaotianchi.com/" + localArticle.slug }}
		</div>
		<div class="child-4J9WMcl9Je parent-VyzXfqx9Je">
			<Radio
				name="default"
				value="default"
				v-model="settingStatus.permalink.mode"
				@selected="
					() => {
						localArticle.slug = remoteArticle.slug;
					}
				"
				>Auto</Radio
			>
			<Radio
				name="custom"
				value="custom"
				v-model="settingStatus.permalink.mode"
				@selected="
					() => {
						localArticle.slug = customSlug || remoteArticle.slug;
					}
				"
				>Custom</Radio
			>
		</div>
		<InputA
			v-if="settingStatus.permalink.mode === 'custom'"
			name="custom-slug"
			v-model="customSlug"
			:max-length="8000"
			auto-focus
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
		margin: 10px 0;
	}
	.parent-EJ5IqDbqkl {
		min-height: 20px;
		max-height: 40px;
		overflow-y: scroll;
		word-wrap: break-word;
		line-height: 20px;
		text-align: left;
		font-size: small;
		color: grey;
	}
</style>
