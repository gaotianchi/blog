import type { AuthFormData } from "./typing";
import Ajv, { type JSONSchemaType } from "ajv";
const ajv = new Ajv();

const authFormDataSchema: JSONSchemaType<AuthFormData> = {
	type: "object",
	properties: {
		username: {
			type: "string",
			minLength: 3,
			maxLength: 100,
			pattern: "^[a-z][a-z0-9_]{2,99}$",
		},
		password: {
			type: "string",
			minLength: 6,
			pattern: "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$",
		},
	},
	required: ["username", "password"],
	additionalProperties: false,
};

export function authFormDataValidate(data: AuthFormData) {
	const validator = ajv.compile(authFormDataSchema);
}
