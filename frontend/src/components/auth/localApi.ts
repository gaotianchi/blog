import type { RegisterStatus } from "./typing";
import defaults from "./defaults";
export function setRegisterStatus(status: RegisterStatus): void {
	const data = JSON.stringify(status);
	sessionStorage.setItem("registerStatus", data);
}
export function getRegisterStatus(): RegisterStatus {
	const data = sessionStorage.getItem("registerStatus");
	if (data) {
		return JSON.parse(data);
	} else {
		return defaults.registerStatus;
	}
}
