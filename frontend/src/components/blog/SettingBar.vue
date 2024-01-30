<script setup lang="ts">
	import TagVue from "./settings/Tag.vue";
	import Datetime from "./settings/Datetime.vue";
	import Permalink from "./settings/Permalink.vue";
	import SeriesVue from "./settings/Series.vue";
	import SettingItem from "./SettingItem.vue";
	import type { Series, Tag } from "@/typing";
    type ArticleSettings = {
        tag: Tag
    }
	const emits = defineEmits<{
        updateSettings: []
    }>();
</script>
<template></template>
<style scoped></style>
