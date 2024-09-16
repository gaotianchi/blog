<template>
	<ul class="app-nav">
		<li class="app-search">
			<input class="app-search__input" type="search" placeholder="Search" />
			<button class="app-search__button"><i class="bi bi-search"></i></button>
		</li>
		<!--Notification Menu-->
		<li class="dropdown">
			<a
				class="app-nav__item"
				href="#"
				data-bs-toggle="dropdown"
				aria-label="Show notifications"
			>
				<i class="bi bi-bell fs-5"></i>
			</a>
			<ul class="app-notification dropdown-menu dropdown-menu-right">
				<li class="app-notification__title">You have 4 new notifications.</li>
				<div class="app-notification__content">
					<li>
						<a class="app-notification__item" href="javascript:;">
							<span class="app-notification__icon">
								<i class="bi bi-envelope fs-4 text-primary"></i>
							</span>
							<div>
								<p class="app-notification__message">Lisa sent you a mail</p>
								<p class="app-notification__meta">2 min ago</p>
							</div>
						</a>
					</li>
					<li>
						<a class="app-notification__item" href="javascript:;">
							<span class="app-notification__icon">
								<i class="bi bi-exclamation-triangle fs-4 text-warning"></i>
							</span>
							<div>
								<p class="app-notification__message">Mail server not working</p>
								<p class="app-notification__meta">5 min ago</p>
							</div>
						</a>
					</li>
					<li>
						<a class="app-notification__item" href="javascript:;">
							<span class="app-notification__icon">
								<i class="bi bi-cash fs-4 text-success"></i>
							</span>
							<div>
								<p class="app-notification__message">Transaction complete</p>
								<p class="app-notification__meta">2 days ago</p>
							</div>
						</a>
					</li>
					<li>
						<a class="app-notification__item" href="javascript:;">
							<span class="app-notification__icon">
								<i class="bi bi-envelope fs-4 text-primary"></i>
							</span>
							<div>
								<p class="app-notification__message">Lisa sent you a mail</p>
								<p class="app-notification__meta">2 min ago</p>
							</div>
						</a>
					</li>
					<li>
						<a class="app-notification__item" href="javascript:;">
							<span class="app-notification__icon">
								<i class="bi bi-exclamation-triangle fs-4 text-warning"></i>
							</span>
							<div>
								<p class="app-notification__message">Mail server not working</p>
								<p class="app-notification__meta">5 min ago</p>
							</div>
						</a>
					</li>
					<li>
						<a class="app-notification__item" href="javascript:;">
							<span class="app-notification__icon">
								<i class="bi bi-cash fs-4 text-success"></i>
							</span>
							<div>
								<p class="app-notification__message">Transaction complete</p>
								<p class="app-notification__meta">2 days ago</p>
							</div>
						</a>
					</li>
				</div>
				<li class="app-notification__footer">
					<a href="#">See all notifications.</a>
				</li>
			</ul>
		</li>
		<!-- User Menu-->
		<UserMenuComponent />
	</ul>
</template>
<script lang="ts" setup>
	import UserMenuComponent from './UserMenuComponent.vue';
</script>
