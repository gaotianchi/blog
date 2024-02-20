<script setup lang="ts">
	import { RouterView } from "vue-router";
	import Message from "./components/Message.vue";
	import Confirm from "./components/Confirm.vue";
</script>

<template>
	<Message />
	<Confirm />
	<RouterView />
</template>

<style scoped></style>
