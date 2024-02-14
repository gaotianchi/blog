import type { AuthFormData } from "./typing";
import Ajv, { type JSONSchemaType } from "ajv";
const ajv = new Ajv();

const authFormDataSchema: JSONSchemaType<AuthFormData> = {
	type: "object",
	properties: {
		username: {
			type: "string",
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

export function authFormDataValidate() {
	return ajv.compile(authFormDataSchema);
}
