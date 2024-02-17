import type { TokenResponse } from "@/typing";
import type { APIError } from "../src/api/errors";

export async function login(formData: FormData): Promise<TokenResponse> {
	const url = "http://localhost:5000/v1/account/token";
	const response = await fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded",
		},
		body: formData,
	});
	if (response.status === 200) {
		const responseData = await response.json();
		return responseData as TokenResponse;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error as APIError;
	}
}
