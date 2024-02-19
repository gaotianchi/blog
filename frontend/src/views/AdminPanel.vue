<script setup lang="ts">
	import NavigationBar from "@/components/adminPanel/NavigationBar.vue";
	import Header from "@/components/Header.vue";
</script>
<template>
	<Header />
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
</style>
