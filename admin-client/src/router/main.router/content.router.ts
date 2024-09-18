import PicturePage from '@/page/main.page/PicturePage.vue';
import type { RouteRecordRaw } from 'vue-router';

const contentRouter: RouteRecordRaw[] = [
	{
		name: 'PICTURE',
		path: '/pictures',
		component: PicturePage,
		meta: {
			requiresAuth: true,
		},
	},
];

export default contentRouter;
