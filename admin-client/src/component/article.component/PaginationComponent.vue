<template>
	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<li class="page-item">
				<button
					class="page-link"
					:class="{ disabled: currentPage === 0 }"
					@click="handlePrePage"
				>
					上一页
				</button>
			</li>
			<li v-for="p in totalPage" class="page-item">
				<button
					class="page-link"
					:class="{ active: p - 1 === currentPage }"
					@click="currentPage = p - 1"
				>
					{{ p }}
				</button>
			</li>
			<li class="page-item">
				<button
					class="page-link"
					:class="{ disabled: currentPage + 1 === totalPage }"
					@click="currentPage += 1"
				>
					下一页
				</button>
			</li>
		</ul>
	</nav>
</template>

<script setup lang="ts">
	import { ref } from 'vue';

	defineProps({
		totalPage: {
			type: Number,
			required: true,
		},
	});
	const emits = defineEmits<{
		pageChanged: [currenPage: number];
	}>();

	const currentPage = ref(0);

	const handlePrePage = () => {
		currentPage.value -= 1;
		emits('pageChanged', currentPage.value);
	};

	const handleNextPage = () => {
		currentPage.value += 1;
		emits('pageChanged', currentPage.value);
	};
</script>
