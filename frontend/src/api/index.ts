import type { Series, Tag } from "@/typing";
import { fakeSeries, fakeTags } from "./fakes";
import type { APIError } from "./errors";

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
	const accessToken = localStorage.getItem("access_token");
	const response = await fetch(url, {
		method: "POST",
		headers: {
			Authorization: "Bearer " + accessToken,
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
	const accessToken = localStorage.getItem("access_token");
	const response = await fetch(url, {
		method: "POST",
		headers: { Authorization: "Bearer " + accessToken },
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
