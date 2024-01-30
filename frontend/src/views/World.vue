<script setup lang="ts">
	import { ref, type Ref } from "vue";
	import Series from "@/components/blog/settings/Series.vue";
	import Permalink from "@/components/blog/settings/Permalink.vue";
	const model = ref(true);
</script>

<template>
	<!-- {{ model }} -->
	<Series @update-sereis="(result) => console.log(result)" />
</template>
