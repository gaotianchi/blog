import moment from "moment-timezone";
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
