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

export function isSameArticle(a: Article, b: Article): boolean {
	if (a.id !== b.id) {
		return false;
	}
	if (a.title !== b.title) {
		return false;
	}
	if (a.body !== b.body) {
		return false;
	}
	if (a.slug !== b.slug) {
		return false;
	}
	if (serializeDate(a.createdAt) !== serializeDate(b.createdAt)) {
		return false;
	}
	if (serializeDate(a.updatedAt) !== serializeDate(b.updatedAt)) {
		return false;
	}
	if (serializeDate(a.publishedAt) !== serializeDate(b.publishedAt)) {
		return false;
	}
	if (a.isPublished !== b.isPublished) {
		return false;
	}
	if (a.authorId !== b.authorId) {
		return false;
	}
	if (!arraysHaveSameElements(a.tags, b.tags)) {
		return false;
	}
	if (a.seriesId !== b.seriesId) {
		return false;
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
export function serializeDate(d: Date): string {
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
