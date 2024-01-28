import { createRouter, createWebHistory } from "vue-router";

import Hello from "@/views/Hello.vue";
import World from "@/views/World.vue";

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
	],
});

export default router;
