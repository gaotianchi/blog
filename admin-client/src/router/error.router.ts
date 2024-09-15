import ErrorPage from '@/page/ErrorPage.vue';
import type { RouteRecordRaw } from 'vue-router';

const errorRouter: RouteRecordRaw[] = [
	{
		name: 'ERROR',
		path: '/error',
		component: ErrorPage,
	},
];

export default errorRouter;
