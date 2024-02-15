import type { APIError } from "@/api/errors";
import type { RegisterResponseData, LoginResponseData } from "./typing";
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
