<script setup lang="ts">
	import icons from "@/components/icons";
</script>
<template>
	<header class="parent-4JKdmTIsyg">
		<div class="parent-EkcpQ6Is1e">
			<div class="parent-EkyRmaLs1x">
				<component :is="icons.logo" class="icon bigest" />
			</div>
			<div class="parent-V140QTUokg">Gao Tianchi's Blog</div>
		</div>
		<div class="parent-41DK3aE3Jx">
			<slot name="search-field"> </slot>
		</div>
		<nav class="parent-4JG1naE21l">
			<slot name="navbar"></slot>
		</nav>
	</header>
</template>
<style scoped>
	.parent-4JKdmTIsyg {
		height: 55px;
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
		padding: 0 5px;
		margin-bottom: 5px;
	}
	.parent-EkcpQ6Is1e {
		display: flex;
		align-items: center;
	}
	.parent-EkyRmaLs1x {
		height: 48px;
		width: 48px;
	}
	.parent-V140QTUokg {
		font-size: x-large;
		font-weight: 500;
		margin-left: 15px;
	}
	.parent-4JG1naE21l {
		min-width: 250px;
	}
</style>
