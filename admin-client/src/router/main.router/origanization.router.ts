import TagPage from '@/page/main.page/origanization/TagPage.vue';
import type { RouteRecordRaw } from 'vue-router';

const origanizationRouter: RouteRecordRaw[] = [
	{
		name: 'TAG',
		path: '/tags',
		component: TagPage,
	},
];

export default origanizationRouter;
