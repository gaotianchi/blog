import { User } from 'oidc-client-ts';
import authService from '@/service/auth.service';
import type { APIResponse } from '@/type/response.type';
import { AUTH_BASE_URL, RESOURCE_BASE_URL } from '@/config/global.config';
import { useRouter } from 'vue-router';

export async function makeRequest<T>(
	url: string,
	options: RequestInit = {}
): Promise<APIResponse<T>> {
	try {
		const user: User | null = await authService.userManager.getUser();
		if (!user || !user.access_token) {
			throw new Error('No valid access token found');
		}
		const headers = new Headers(options.headers || {});
		headers.set('Authorization', `Bearer ${user.access_token}`);
		if (!headers.has('Content-Type') && !(options.body instanceof FormData)) {
			headers.set('Content-Type', 'application/json');
		}
		const requestOptions: RequestInit = {
			...options,
			headers: headers,
		};
		const response = await fetch(url, requestOptions);
		if (response.status === 403 || response.status === 401) {
			window.location.href = '/sign-in';
		}
		if (!response.ok) {
			throw new Error(`HTTP error! Status: ${response.status}`);
		}
		const result = await response.json();
		return result as APIResponse<T>;
	} catch (error) {
		console.error('Request failed:', error);
		throw error;
	}
}
