import BloggerSettingPage from '@/page/main.page/BloggerSettingPage.vue';
import type { RouteRecordRaw } from 'vue-router';

export const userRouter: RouteRecordRaw[] = [
	{
		name: 'BLOGGER_SETTING',
		path: '/blogger-setting',
		component: BloggerSettingPage,
		meta: {
			requiresAuth: true,
		},
	},
];
