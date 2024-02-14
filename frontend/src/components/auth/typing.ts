export type InputStatus = "normal" | "error" | "success";
export type AuthAction = "login" | "register";
export type AuthStatus = "normal" | "loading" | "fail" | "success";
export type AuthFormData = {
	username: string;
	password: string;
};
export type InputElement = {
	status: InputStatus;
	value: string;
	conditions: { [key: string]: boolean };
};
export type RegisterStatus = {
	username: InputElement;
	password: InputElement;
	passwordConfirmation: InputElement;
};
