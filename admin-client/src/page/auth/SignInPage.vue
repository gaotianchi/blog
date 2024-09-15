<template>
	<section class="material-half-bg">
		<div class="cover"></div>
	</section>
	<section class="login-content">
		<div class="logo">
			<h1>Vali</h1>
		</div>
		<div class="login-box">
			<div class="login-form">
				<h3 class="login-head">
					<i class="bi bi-person me-2"></i>
					SIGN IN WITH
				</h3>
				<div class="mb-3 btn-container d-grid">
					<button @click="login" class="btn btn-outline-primary social-btn btn-lg">
						<i class="bi bi-envelope"></i>
						Email
					</button>
				</div>
				<div class="mb-3 btn-container d-grid">
					<button class="btn btn-outline-dark social-btn btn-lg">
						<i class="bi bi-github"></i>
						GitHub
					</button>
				</div>
				<div class="mb-3 btn-container d-grid">
					<button class="btn btn-outline-danger social-btn btn-lg">
						<i class="bi bi-google"></i>
						Google
					</button>
				</div>
			</div>
		</div>
	</section>
</template>

<script setup lang="ts">
	import authService from '@/service/auth.service';
	const login = () => {
		authService.login();
	};
</script>

<style scoped></style>
