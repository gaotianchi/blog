import MainPage from '@/page/MainPage.vue';
import type { RouteRecordRaw } from 'vue-router';
import testRouter from './main.router/test.router';
import { userRouter } from './main.router/user.router';
import contentRouter from './main.router/content.router';
import origanizationRouter from './main.router/origanization.router';

const routes = [...testRouter, ...userRouter, ...contentRouter, ...origanizationRouter];

const mainRouter: RouteRecordRaw[] = [
	{
		name: 'MAIN',
		path: '/',
		component: MainPage,
		children: routes,
	},
];

export default mainRouter;
