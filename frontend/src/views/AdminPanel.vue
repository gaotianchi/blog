<script setup lang="ts">
	import { useRouter } from "vue-router";
	import NavigationBar from "@/components/adminPanel/NavigationBar.vue";
	import BlogHeader from "@/components/BlogHeader.vue";
	import icons from "@/components/icons";
	import { ref } from "vue";
	const query = ref("");
	const router = useRouter();
	function search(): void {
		if (!query.value.trim()) {
			return;
		}
		router.push({
			name: "ArticlesPanel",
			query: {
				q: query.value,
			},
		});
	}
</script>
<template>
	<BlogHeader>
		<template #search-field>
			<form @submit.prevent="search" class="parent-41nyyA43kg">
				<component :is="icons.search" class="icon big" />
				<input
					type="search"
					name="query"
					id="query"
					class="parent-4kcSZ0Nh1g"
					aria-label="query"
					v-model="query"
				/>
				<button type="submit" style="display: none"></button>
			</form>
		</template>
	</BlogHeader>
	<div class="parent-E13eak3oyg">
		<div class="parent-EJqWTJnjyg">
			<NavigationBar />
		</div>
		<div class="parent-Ny1f6Jhsye">
			<RouterView></RouterView>
		</div>
	</div>
</template>
<style scoped>
	.parent-E13eak3oyg {
		height: calc(100vh - 60px);
		display: grid;
		grid-template-columns: 250px 1fr;
	}
	.parent-EJqWTJnjyg {
		background-color: #e9e6e626;
		padding-right: 20px;
	}
	.parent-Ny1f6Jhsye {
		padding: 0 20px;
	}
	.parent-41nyyA43kg {
		width: 400px;
		height: 45px;
		display: flex;
		align-items: center;
		border-radius: 10px;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		padding: 0 10px;
		background: rgb(255, 255, 255);
	}
	.parent-4kcSZ0Nh1g {
		width: 100%;
		height: 100%;
		outline: none;
		border: none;
		font-size: 17px;
		line-height: 17px;
		padding-left: 10px;
	}
	.parent-4kcSZ0Nh1g::-webkit-search-cancel-button {
		/* -webkit-appearance: none; */
		height: 16px;
		width: 16px;
		cursor: pointer;
	}
	.parent-4kcSZ0Nh1g:focus-visible {
		border: none;
		outline: none;
	}
</style>
