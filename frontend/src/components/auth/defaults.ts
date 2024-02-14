import type { RegisterStatus, InputElement } from "./typing";

const defaultInputElement: InputElement = {
	value: "",
	status: "normal",
	conditions: {},
};

const defaultRegisterStatus: RegisterStatus = {
	username: { ...defaultInputElement },
	password: { ...defaultInputElement },
	passwordConfirmation: { ...defaultInputElement },
};

export default {
	inputElement: defaultInputElement,
	registerStatus: defaultRegisterStatus,
};
