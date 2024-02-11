import "./assets/main.css";
import MessagePropVue from "@/components/MessageProp.vue";
import ConfirmPropVue from "./components/ConfirmProp.vue";

import { createApp } from "vue";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);
app.component("MessageProp", MessagePropVue).component(
	"ConfirmProp",
	ConfirmPropVue
);
app.use(router);

app.mount("#app");
