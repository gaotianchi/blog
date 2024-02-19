import { createRouter, createWebHistory } from "vue-router";

import Hello from "@/views/Hello.vue";
import World from "@/views/World.vue";
import { validateUser } from "@/api/remote";
import EditorVue from "@/views/Editor.vue";
import AdminPanelVue from "@/views/AdminPanel.vue";

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: "/hello",
			name: "hello",
			component: Hello,
			meta: { loginRequired: false },
		},
		{
			path: "/world",
			name: "world",
			component: World,
			meta: { loginRequired: false },
		},
		{
			path: "/edit/article/:articleId",
			name: "EditArticle",
			component: EditorVue,
			props: true,
			meta: { loginRequired: true },
		},
		{
			path: "/admin-pancel",
			name: "AdminPancel",
			component: AdminPanelVue,
			meta: { loginRequired: true },
			children: [
				{
					path: "articles",
					name: "ArticlesPanel",
					component: () =>
						import("@/components/adminPanel/ArticlesPanel.vue"),
				},
			],
		},
		{
			path: "/auth",
			component: () => import("@/views/Auth.vue"),
			meta: { loginRequired: false },
			children: [
				{
					path: "register",
					component: () =>
						import("@/components/auth/RegisterForm.vue"),
					name: "Register",
				},
				{
					path: "login",
					component: () => import("@/components/auth/LoginForm.vue"),
					name: "Login",
				},
			],
		},
	],
});
router.beforeEach(async (to, from) => {
	if (to.meta.loginRequired) {
		const isAuthenticated = await validateUser();
		if (!isAuthenticated && to.name !== "Login") {
			return { name: "Login" };
		}
	}
});
export default router;
