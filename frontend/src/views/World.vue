<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import Series from "@/components/blog/settings/Series.vue";
	import Permalink from "@/components/blog/settings/Permalink.vue";
	import SettingItem from "@/components/blog/SettingItem.vue";
	const model = ref(true);
</script>

<template>
	<!-- {{ model }} -->
	<SettingItem />
</template>
