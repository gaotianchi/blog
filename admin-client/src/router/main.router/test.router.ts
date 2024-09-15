import BlankPage from '@/page/main.page/BlankPage.vue';
import type { RouteRecordRaw } from 'vue-router';

const testRouter: RouteRecordRaw[] = [
	{
		name: 'BLANK',
		path: '/blank',
		component: BlankPage,
	},
];

export default testRouter;
