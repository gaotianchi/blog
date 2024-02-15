import type { APIError } from "@/api/errors";
import type { RegisterResponseData, LoginResponseData } from "./typing";
import { getAccessToken } from "./localApi";
const rootUrl = "http://localhost:5000/v1";

export async function registerUser(
	formData: FormData
): Promise<RegisterResponseData> {
	const url = rootUrl + "/account/users";
	const response = await fetch(url, {
		method: "POST",
		body: formData,
	});
	if (response.status === 201) {
		const registerResponseData =
			(await response.json()) as RegisterResponseData;
		return registerResponseData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error as APIError;
	}
}

export async function login(formData: FormData): Promise<LoginResponseData> {
	const url = rootUrl + "/account/token";
	const response = await fetch(url, {
		method: "POST",
		body: formData,
	});
	if (response.status === 200) {
		const loginResponseData = await response.json();
		return loginResponseData as LoginResponseData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error as APIError;
	}
}

export async function validateUser(): Promise<boolean> {
	const url = rootUrl + "/account/token";
	const loginResponseData = getAccessToken();
	const response = await fetch(url, {
		method: "GET",
		headers: {
			Authorization:
				loginResponseData?.tokenType +
				" " +
				loginResponseData?.accessToken,
		},
	});
	if (response.status === 200) {
		return true;
	} else {
		return false;
	}
}
