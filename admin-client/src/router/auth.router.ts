import CallbackPage from '@/page/auth/CallbackPage.vue';
import SignInPage from '@/page/auth/SignInPage.vue';
import SignOutPage from '@/page/auth/SignOutPage.vue';
import SignUpPage from '@/page/auth/SignUpPage.vue';
import type { RouteRecordRaw } from 'vue-router';

const authRouter: RouteRecordRaw[] = [
	{
		name: 'CALLBACK',
		path: '/callback',
		component: CallbackPage,
	},
	{
		name: 'SIGN_IN',
		path: '/sign-in',
		component: SignInPage,
	},
	{
		name: 'SIGN_UP',
		path: '/sign-up',
		component: SignUpPage,
	},
	{
		name: 'SIGN_OUT',
		path: '/sign-out',
		component: SignOutPage,
	},
];

export default authRouter;
