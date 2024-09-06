<template>
  <p v-if="pending">Fetching...</p>
  <pre v-else-if="error">{{ error }}</pre>
  <pre v-else>{{ product }}</pre>
</template>
<script setup lang="ts">
const id = ref(1)
const { data: product, pending, error } = await useFetch(() => `https://dummyjson.com/products/${id.value}`)
const canDecrease = computed(() => id.value > 1)
</script>