export function usernameCondition_1(value: string): boolean {
	const reg = new RegExp("^[a-z0-9_]+$");
	return reg.test(value);
}
export function usernameCondition_2(value: string): boolean {
	const reg = new RegExp("^[a-z]");
	return reg.test(value);
}
export function usernameCondition_3(value: string): boolean {
	return value.length > 2 && value.length < 100;
}
export function passwordCondition_1(value: string): boolean {
	return value.length > 5;
}
export function passwordCondition_2(value: string): boolean {
	const reg = new RegExp("^(?=.*[a-zA-Z])(?=.*\\d).+$");
	return reg.test(value);
}
