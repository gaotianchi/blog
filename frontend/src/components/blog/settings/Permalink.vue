<script setup lang="ts">
	import { computed, ref, watchEffect, type Ref } from "vue";
	import { getUnixTime } from "date-fns";
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
			<div class="parent-E1x3c9x9Jg">
				<input
					class="child-N1vpq9lqkx parent-Ny_C5cg5kx"
					type="radio"
					v-model="auto"
					name="auto-permalink"
					id="auto-permalink"
					:value="true"
				/>
				<label
					class="child-N1vpq9lqkx parent-4yWJs9l9kl"
					for="auto-permalink"
					>Auto</label
				>
			</div>
			<div class="parent-E1x3c9x9Jg">
				<input
					class="child-N1vpq9lqkx parent-Ny_C5cg5kx"
					type="radio"
					v-model="auto"
					name="custom-permalink"
					id="custom-permalink"
					:value="false"
				/>
				<label
					class="child-N1vpq9lqkx parent-4yWJs9l9kl"
					for="custom-permalink"
					>Custom</label
				>
			</div>
		</div>
		<div class="child-4J9WMcl9Je parent-VJrIoDZ5kx" v-if="!auto">
			<input
				type="text"
				name="slug-input-area"
				id="slug-input-area"
				aria-label="slug-input-area"
				v-model="customSlug"
			/>
			<div class="child- parent-41PsiPZ5Jx">
				<span class="child-E1gknPb51g parent-V1j1nw-5Jg">{{ customSlug.length }}</span>
				<span class="child-E1gknPb51g parent-Ey-WhwW51x">/</span>
				<span class="child-E1gknPb51g parent-VJrb3PW9Je">8000</span>
			</div>
		</div>
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

	.parent-E1x3c9x9Jg {
		width: 100%;
		height: 25px;
		padding-left: 20px;
		display: flex;
		align-items: center;
	}

	.parent-Ny_C5cg5kx {
		width: 20px;
		height: 20px;
	}

	.parent-4yWJs9l9kl {
		padding-left: 10px;
		display: flex;
		align-items: center;
		font-size: medium;
		line-height: 20px;
	}

	#slug-input-area {
		width: 100%;
		line-height: 20px;
		padding: 0 5px;
		border: none;
		border-bottom: var(--second-color) solid 1px;
	}

    .parent-41PsiPZ5Jx {
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .child-E1gknPb51g {
        font-size: small;
    }
</style>
