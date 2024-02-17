import type { LoginResponseData } from "./typing";

export function setAccessToken(data: LoginResponseData): void {
	localStorage.setItem("accessToken", JSON.stringify(data));
}
export function getAccessToken(): LoginResponseData | null {
	const data = localStorage.getItem("accessToken");
	if (data) {
		return JSON.parse(data);
	} else {
		return null;
	}
}
