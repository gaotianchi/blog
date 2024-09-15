import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import '@/assets/css/main.css';

const app = createApp(App);

app.use(router);

app.mount('#app');
