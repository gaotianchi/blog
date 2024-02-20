import Ajv, { type JSONSchemaType } from "ajv";
import moment from "moment-timezone";
import type { Article, SerializedArticle } from "@/typing";

const ajv = new Ajv();
export function getLocalDatetime(dateString: string): Date {
	const localizedMoment = moment.utc(dateString).local();
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
function serializeDate(d: Date): string {
	const utcMoment = moment.utc(d);
	return utcMoment.format("YYYY-MM-DDTHH:mm:ssZZ");
}
export function serializeArticle(a: Article): SerializedArticle {
	const serializedArticle: SerializedArticle = {
		id: a.id,
		title: a.title,
		body: a.body,
		slug: a.slug,
		createdAt: serializeDate(a.createdAt),
		updatedAt: serializeDate(a.updatedAt),
		publishedAt: serializeDate(a.publishedAt),
		isPublished: a.isPublished,
		authorId: a.authorId,
		seriesId: a.seriesId,
		tags: a.tags,
	};
	return serializedArticle;
}

export function deserizalizeArticle(sa: SerializedArticle): Article {
	const a: Article = {
		id: sa.id,
		title: sa.title,
		body: sa.body,
		slug: sa.slug,
		createdAt: getLocalDatetime(sa.createdAt),
		updatedAt: getLocalDatetime(sa.updatedAt),
		publishedAt: getLocalDatetime(sa.publishedAt),
		isPublished: sa.isPublished,
		tags: sa.tags,
		seriesId: sa.seriesId,
		authorId: sa.authorId,
	};
	return a;
}
export function getRenamedFile(
	originalFile: File,
	profix: string = "image"
): File {
	const nameArr = originalFile.name.split(".");
	const newFilename =
		profix +
		"-" +
		dateFormatter(new Date(), "YYYYMMDDhhmmssSSSSSS") +
		"." +
		nameArr[nameArr.length - 1];
	const renameFile = new File([originalFile], newFilename, {
		type: originalFile.type,
	});
	return renameFile;
}
export function getPreviewUrl(file: File): Promise<string> {
	return new Promise((resolve) => {
		const reader = new FileReader();
		reader.onload = () => {
			const url = reader.result as string;
			resolve(url);
		};
		reader.readAsDataURL(file);
	});
}
