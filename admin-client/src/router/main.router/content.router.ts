import ArticleEditorPage from '@/page/main.page/ArticleEditorPage.vue';
import ArticlesPage from '@/page/main.page/ArticlesPage.vue';
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
	{
		name: 'ARTICLE_EDITOR',
		path: '/article/editor/:id',
		component: ArticleEditorPage,
		meta: {
			requiresAuth: true,
		},
	},
	{
		name: 'ARTICLES',
		path: '/articles',
		component: ArticlesPage,
	},
];

export default contentRouter;