import type { RegisterStatus, InputElement, LoginStatus } from "./typing";

const defaultInputElement: InputElement = {
	value: "",
	status: "normal",
	conditions: {},
};
const defaultRegisterStatus: RegisterStatus = {
	username: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
			condition_3: true,
			condition_4: true,
		},
	},
	password: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
		},
	},
	passwordConfirmation: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
		},
	},
};
const defaultLoginStatus: LoginStatus = {
	username: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
			condition_3: true,
			condition_4: true,
		},
	},
	password: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
			condition_3: true,
		},
	},
};
export default {
	inputElement: defaultInputElement,
	registerStatus: defaultRegisterStatus,
	loginStatus: defaultLoginStatus,
};
