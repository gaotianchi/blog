<script setup lang="ts">
	import { ref, type Ref } from "vue";
	const model = ref("world!");
</script>

<template>
	{{ model }}
</template>
