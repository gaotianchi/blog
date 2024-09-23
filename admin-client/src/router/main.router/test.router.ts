import BlankPage from '@/page/main.page/BlankPage.vue';
import type { RouteRecordRaw } from 'vue-router';

const testRouter: RouteRecordRaw[] = [
	{
		name: 'BLANK',
		path: '/blank/:id',
		component: BlankPage,
		meta: {
			requiresAuth: true,
		},
	},
];

export default testRouter;
