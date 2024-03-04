import type { APIError } from "@/api/errors";
import type { Article, Tag, Series } from "./typing";
import { rootUrl } from "@/config";
import { reactive } from "vue";
import { defaultArticle, defaultSeries } from "@/defaults";

export const remoteArticle: Article = reactive({ ...defaultArticle });
export const remoteSeries: Series = reactive({ ...defaultSeries });
export const allRemoteSeries: Series[] = reactive([]);
export async function getAllRemoteTags(): Promise<Tag[]> {
	const url = rootUrl + "/author/tags";
	const response = await fetch(url);
	if (response.status === 200) {
		const tagsData = await response.json();
		return tagsData as Tag[];
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}

export async function getRemoteSeriesItem(
	seriesId: number | string
): Promise<Series> {
	const url = rootUrl + "/author/serise/" + seriesId;
	const response = await fetch(url);
	if (response.status === 200) {
		const seriesData = await response.json();
		return seriesData as Series;
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}
export async function getAllRemoteSeriesItem(): Promise<Series[]> {
	const url = rootUrl + "/author/series";
	const response = await fetch(url);
	if (response.status === 200) {
		const seriesData = await response.json();
		return seriesData as Series[];
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}
// export async function createSeriesItem(): Promise<Series> {
// 	const url = rootUrl + "/author/series";
// 	const response = await fetch(url, {});
// }
