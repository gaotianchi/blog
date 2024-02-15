import { createRouter, createWebHistory } from "vue-router";

import Hello from "@/views/Hello.vue";
import World from "@/views/World.vue";
import { getAccessToken } from "@/api";

async function validateUser(): Promise<boolean> {
	const url = "http://localhost:5000/v1/account/token";
	const response = await fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
			Authorization: "Bearer " + getAccessToken(),
		},
	});
	if (response.status === 200) {
		return true;
	} else {
		return false;
	}
}

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
			path: "/edit/article/:articleId(\\d+)",
			name: "editArticle",
			component: () => import("@/components/blog/Editor.vue"),
			props: true,
			meta: { loginRequired: true },
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
		if (!isAuthenticated && to.name !== "auth") {
			return { name: "auth", params: { action: "login" } };
		}
	}
});
export default router;
