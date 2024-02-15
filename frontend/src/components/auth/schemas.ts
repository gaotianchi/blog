import type { AuthFormData, RegisterResponseData } from "./typing";
import Ajv, { type JSONSchemaType } from "ajv";
const ajv = new Ajv();
const datetimePattern =
	"^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})([+-]\\d{4})$";
const authFormDataSchema: JSONSchemaType<AuthFormData> = {
	type: "object",
	properties: {
		username: {
			type: "string",
			pattern: "^[a-z][a-z0-9_]{2,99}$",
		},
		password: {
			type: "string",
			pattern: "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$",
		},
	},
	required: ["username", "password"],
	additionalProperties: false,
};

const registerResponseDataSchema: JSONSchemaType<RegisterResponseData> = {
	type: "object",
	properties: {
		id: {
			type: "integer",
		},
		username: {
			type: "string",
			pattern: "^[a-z][a-z0-9_]{2,99}$",
		},
		nickname: {
			type: "string",
		},
		description: {
			type: "string",
		},
		registeredAt: {
			type: "string",
			pattern: datetimePattern,
		},
		lastLoginAt: {
			type: "string",
			pattern: datetimePattern,
		},
		tokenValidityPeriod: {
			type: "integer",
		},
	},
	required: [
		"id",
		"lastLoginAt",
		"nickname",
		"registeredAt",
		"tokenValidityPeriod",
		"username",
	],
};

export function authFormDataValidate() {
	return ajv.compile(authFormDataSchema);
}

export function registerResponseDataValidate() {
	return ajv.compile(registerResponseDataSchema);
}
