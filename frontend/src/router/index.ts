import { createRouter, createWebHistory } from "vue-router";

import Hello from "@/views/Hello.vue";
import World from "@/views/World.vue";
import Editor from "@/components/blog/Editor.vue";

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: "/hello",
			name: "hello",
			component: Hello,
		},
		{
			path: "/world",
			name: "world",
			component: World,
		},
		{
			path: "/edit/article/:articleId(\\d+)",
			name: "editArticle",
			component: () => import("@/components/blog/Editor.vue"),
			props: true,
		},
		{
			path: "/auth/:action(login|register)",
			name: "auth",
			component: () => import("@/components/auth-form/AuthForm.vue"),
			props: true,
		},
	],
});

export default router;
