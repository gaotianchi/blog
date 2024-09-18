import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { VueMasonryPlugin } from 'vue-masonry';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/js/bootstrap.js';
import 'bootstrap/dist/js/bootstrap.bundle.js';
import '@/assets/css/main.css';

const app = createApp(App);

app.use(router);
app.use(VueMasonryPlugin);

app.mount('#app');
