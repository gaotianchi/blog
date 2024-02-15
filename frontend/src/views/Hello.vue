<script setup lang="ts">
	import { ref, watch, type Ref, reactive } from "vue";
	import RegisterForm from "@/components/auth/RegisterForm.vue";
	import LoginForm from "@/components/auth/LoginForm.vue";
	import Tags from "@/components/articleEditor/settings/Tags.vue";
	import SettingItem from "@/components/blog/SettingItem.vue";
	import { localArticle } from "@/components/articleEditor/localApi";
</script>

<template>
	<SettingItem>
		<template #title>Tags</template>
		<template #preview>{{
			localArticle.tags.join(",") || "No tags"
		}}</template>
		<template #detail>
			<Tags />
		</template>
	</SettingItem>
</template>

<style scoped></style>
