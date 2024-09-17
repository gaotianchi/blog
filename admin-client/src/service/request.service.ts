import { User } from 'oidc-client-ts';
import authService from '@/service/auth.service';
import type { APIResponse } from '@/type/response.type';
import { RESOURCE_BASE_URL } from '@/config/global.config';

export async function makeRequest<T>(
	uri: string,
	options: RequestInit = {}
): Promise<APIResponse<T>> {
	try {
		const user: User | null = await authService.userManager.getUser();

		if (!user || !user.access_token) {
			throw new Error('No valid access token found');
		}

		// 如果没有传入 headers，初始化为空对象
		const headers = new Headers(options.headers || {});

		// 设置 Authorization header
		headers.set('Authorization', `Bearer ${user.access_token}`);

		// 仅当 body 不是 FormData 时，才设置默认 Content-Type 为 application/json
		if (!headers.has('Content-Type') && !(options.body instanceof FormData)) {
			headers.set('Content-Type', 'application/json');
		}

		const requestOptions: RequestInit = {
			...options,
			headers: headers, // 使用扩展后的 headers
		};

		const response = await fetch(RESOURCE_BASE_URL + uri, requestOptions);

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
