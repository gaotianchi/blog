import Ajv, { type JSONSchemaType } from "ajv";
import moment from "moment";

const ajv = new Ajv();
export function getLocalDatetime(dateString: string): Date {
	const timezone = moment.tz.guess();
	const localizedMoment = moment.tz(dateString, timezone);
	return localizedMoment.toDate();
}
export function dateFormatter(date: Date, format?: string): string {
	const dateFormat = format || "YYYY-MM-DD  hh:mm  A";
	return moment(date).format(dateFormat);
}
export function limString(str: string, maxLength: number): string {
	if (str.length < maxLength) {
		return str;
	} else {
		return str.slice(0, maxLength) + " ...";
	}
}
export function validator(data: any, schema: JSONSchemaType<any>): boolean {
	const validate = ajv.compile(schema);
	return validate(data);
}

export function isShallowEqual(a: any, b: any): boolean {
	const aProps = Object.getOwnPropertyNames(a);
	const bProps = Object.getOwnPropertyNames(b);

	if (aProps.length !== bProps.length) {
		return false;
	}

	for (let i = 0; i < aProps.length; i++) {
		const propName = aProps[i];

		const propA = a[propName];
		const propB = b[propName];

		if (typeof propA === "object") {
			if (!isShallowEqual(propA, propB)) {
				return false;
			}
		} else if (propA !== propB) {
			return false;
		} else if (Array.isArray(propA)) {
			if (!arraysHaveSameElements(propA, propB)) {
				return false;
			}
		}
	}

	return true;
}

function arraysHaveSameElements(arr1: any[], arr2: any[]) {
	if (arr1.length !== arr2.length) {
		return false;
	}

	return (
		arr1.every((item) => arr2.includes(item)) &&
		arr2.every((item) => arr1.includes(item))
	);
}
