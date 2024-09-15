import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import authService from '@/service/auth.service';

export const authGuard = (
	to: RouteLocationNormalized,
	from: RouteLocationNormalized,
	next: NavigationGuardNext
) => {
	authService.isAuthenticated().then(result => {
		if (!result && to.meta.requiresAuth) {
			next('/signin');
		} else {
			next();
		}
	});
};
