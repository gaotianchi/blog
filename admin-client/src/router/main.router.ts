import MainPage from '@/page/MainPage.vue';
import type { RouteRecordRaw } from 'vue-router';
import testRouter from './main.router/test.router';
import { userRouter } from './main.router/user.router';

const routes = [...testRouter, ...userRouter];

const mainRouter: RouteRecordRaw[] = [
	{
		name: 'MAIN',
		path: '/',
		component: MainPage,
		children: routes,
	},
];

export default mainRouter;
