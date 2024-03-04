<script setup lang="ts">
	import { ref, watch, type Ref, reactive, onMounted } from "vue";
	import SeriesCard from "@/components/adminPanel/SeriesCard.vue";
	type Foo = Record<number, { haa: number }>;
	const foo: Ref<Foo> = ref({});
	onMounted(() => {
		foo.value[1] = { haa: 1 };
		foo.value[2] = { haa: 2 };
		foo.value[3] = { haa: 3 };
		Object.assign(foo.value[1], { haa: 4 });
	});
</script>

<template>
	{{ foo }}
</template>

<style scoped>
	.parent-EkfJ56wnyx {
		margin-left: 30px;
		margin-top: 30px;
	}
</style>
