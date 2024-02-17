import moment from "moment-timezone";
import { message, confirm } from "./localApi";
import type { Confirm } from "./typing";
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
export function propMessage(msg: string): void {
	message.value = msg;
}
export function propConfirm(cf: Confirm): void {
	Object.assign(confirm, cf);
}
