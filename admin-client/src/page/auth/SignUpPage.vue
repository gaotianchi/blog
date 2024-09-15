<template>
	<section class="material-half-bg">
		<div class="cover"></div>
	</section>
	<section class="login-content">
		<div class="logo">
			<h1>Vali</h1>
		</div>
		<div class="login-box">
			<form class="login-form" action="index.html">
				<h3 class="login-head">
					<i class="bi bi-person me-2"></i>
					SIGN UP
				</h3>
				<div class="mb-3">
					<label class="form-label">USERNAME</label>
					<input class="form-control" type="text" placeholder="Email" autofocus />
				</div>
				<div class="mb-3">
					<label class="form-label">PASSWORD</label>
					<input class="form-control" type="password" placeholder="Password" />
				</div>
				<div class="mb-3">
					<label class="form-label">REPEAT PASSWORD</label>
					<input class="form-control" type="password" placeholder="Repeat Password" />
				</div>
				<div class="mb-3 btn-container d-grid">
					<button class="btn btn-primary btn-block">
						<i class="bi bi-box-arrow-in-right me-2 fs-5"></i>
						SIGN UP
					</button>
				</div>
			</form>
		</div>
	</section>
</template>

<script setup lang="ts"></script>

<style scoped>
	.login-box {
		min-height: 420px;
	}
</style>
