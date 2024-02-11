import type { Article, MessageProp, Series, Tag } from "@/typing";
import { fakeSeries, fakeTags } from "./fakes";
import type { APIError } from "./errors";

export function getAccessToken(): string | null {
	const accessToken = localStorage.getItem("access_token");
	return accessToken;
}

export function getTags(): Tag[] {
	const tags = fakeTags();
	return tags;
}

export function getSeries(): Series[] {
	const sereises = fakeSeries();
	return sereises;
}

export async function getSeriesItem(seriesId: number): Promise<Series> {
	const url = "http://localhost:5000/v1/author/series/" + seriesId;
	const response = await fetch(url);
	if (response.status === 200) {
		const seriesData = await response.json();
		return seriesData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function createSeriesItem(
	seriesData: Series
): Promise<Series | void> {
	const url = "http://localhost:5000/v1/author/series";
	const response = await fetch(url, {
		method: "POST",
		headers: {
			Authorization: "Bearer " + getAccessToken(),
			"Content-Type": "application/json",
		},
		body: JSON.stringify(seriesData),
	});
	if (response.status === 201) {
		const seriesData = await response.json();
		return seriesData;
	} else if (response.status === 200) {
		return;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export function limString(str: string, maxLength: number): string {
	if (str.length < maxLength) {
		return str;
	} else {
		return str.slice(0, maxLength) + " ...";
	}
}

export async function createMediaItem(file: File): Promise<any> {
	const url = "http://localhost:5000/v1/media/uploads";
	const formData = new FormData();
	formData.append("file", file);
	const response = await fetch(url, {
		method: "POST",
		headers: { Authorization: "Bearer " + getAccessToken() },
		body: formData,
	});
	if (response.status === 201) {
		const data = await response.json();
		return data;
	} else if (response.status === 200) {
		return;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function updateArticleItem(
	articleId: number | string,
	articleJson: string
): Promise<Article> {
	const url = "http://localhost:5000/v1/author/article/" + articleId;
	const response = await fetch(url, {
		method: "PATCH",
		headers: {
			"Content-Type": "application/json",
			Authorization: "Bearer " + getAccessToken(),
		},
		body: articleJson,
	});
	if (response.status === 200) {
		const newArticleData = await response.json();
		return newArticleData as Article;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error as APIError;
	}
}

export async function getArticleItem(
	articleId: number | string
): Promise<Article> {
	const url = "http://localhost:5000/v1/author/article/" + articleId;
	const response = await fetch(url);
	if (response.status === 200) {
		const articleData = await response.json();
		const data: Article = {
			id: articleData.id,
			title: articleData.title,
			body: articleData.body,
			slug: articleData.slug,
			createdAt: new Date(articleData.created_at),
			updatedAt: new Date(articleData.updated_at),
			isPublished: articleData.is_published,
			publishedAt: new Date(articleData.published_at),
			seriesId: articleData.series_id,
			authorId: articleData.author_id,
			tags: articleData.tags,
		};
		return data;
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}

export function displayMessage(
	messageprop: MessageProp,
	message: string
): void {
	messageprop.active = true;
	messageprop.message = message;
}
