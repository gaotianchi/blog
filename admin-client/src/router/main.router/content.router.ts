import ArticleEditorPage from '@/page/main.page/content/ArticleEditorPage.vue';
import ArticlesPage from '@/page/main.page/content/ArticlesPage.vue';
import PicturePage from '@/page/main.page/content/PicturePage.vue';
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
		meta: {
			requiresAuth: true,
		},
	},
];

export default contentRouter;
