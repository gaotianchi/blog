import { createRouter, createWebHistory } from 'vue-router';
import mainRouter from './main.router';
import authRouter from './auth.router';
import { authGuard } from './guard.router';
import errorRouter from './error.router';

const routes = [...mainRouter, ...authRouter, ...errorRouter];

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: routes,
});

router.beforeEach(authGuard);

export default router;
