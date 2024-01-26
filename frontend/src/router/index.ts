import { createRouter, createWebHistory } from "vue-router";

import Hello from "@/views/Hello.vue";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/hello",
            name: "hello",
            component: Hello
        }
    ]
})

export default router