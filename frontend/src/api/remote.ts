import type {
	Article,
	Tag,
	Series,
	RegisterResponseData,
	LoginResponseData,
	SerializedArticle,
	SerializedArticleCard,
	ArticleCard,
	SeriesArticleCount,
} from "@/typing";
import { rootUrl } from "@/config";
import type { APIError } from "@/api/errors";
import { getAccessToken } from "@/api/local";
import { deserizalizeArticle } from "@/utlis";

export async function postArticleItem(): Promise<Article> {
	const url = rootUrl + "/author/articles";
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "POST",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
		},
	});
	if (response.status === 201) {
		const articleData = await response.json();
		return articleData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function getArticleItem(
	articleId: number | string
): Promise<Article> {
	const url = rootUrl + "/author/article/" + articleId;
	const response = await fetch(url);
	if (response.status === 200) {
		const articleData: SerializedArticle = await response.json();
		return deserizalizeArticle(articleData);
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function patchArticleItem(
	articleId: number | string,
	serializedarticle: SerializedArticle
): Promise<Article> {
	const url = rootUrl + "/author/article/" + articleId;
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "PATCH",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
			"Content-Type": "application/json",
		},
		body: JSON.stringify(serializedarticle),
	});
	if (response.status === 200) {
		const sa: SerializedArticle = await response.json();
		return deserizalizeArticle(sa);
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function deleteArticleItem(
	articleId: number | string
): Promise<void> {
	const url = rootUrl + "/author/article/" + articleId;
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "DELETE",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
		},
	});
	if (response.status === 204) {
		return;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function patchArticleCardItem(
	articleId: number | string,
	serializedArticleCard: SerializedArticleCard
): Promise<ArticleCard> {
	const url = rootUrl + "/author/article-card/" + articleId;
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "PATCH",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
			"Content-Type": "application/json",
		},
		body: JSON.stringify(serializedArticleCard),
	});
	if (response.status === 200) {
		const cardData = await response.json();
		return cardData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function getAllArticleCards(): Promise<ArticleCard[]> {
	const url = rootUrl + "/author/article-cards";
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "GET",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
		},
	});
	if (response.status === 200) {
		const cardData = await response.json();
		return cardData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}

export async function postSeriesItem(seriesData: Series): Promise<Series> {
	const url = rootUrl + "/author/series";
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "POST",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
			"Content-Type": "application/json",
		},
		body: JSON.stringify(seriesData),
	});
	if (response.status === 201) {
		const seriesData = await response.json();
		return seriesData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}
export async function getSeriesItem(
	seriesId: number | string
): Promise<Series> {
	const url = rootUrl + "/author/series/" + seriesId;
	const response = await fetch(url);
	if (response.status === 200) {
		const seriesData = await response.json();
		return seriesData as Series;
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}
export async function getAllSeries(): Promise<Series[]> {
	const url = rootUrl + "/author/series";
	const loginResponseData = getAccessToken();
	const response = await fetch(url, {
		method: "GET",
		headers: {
			Authorization:
				loginResponseData?.tokenType +
				" " +
				loginResponseData?.accessToken,
		},
	});
	if (response.status === 200) {
		const seriesData = await response.json();
		return seriesData as Series[];
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}
export async function patchSeriesItem(series: Series): Promise<Series> {
	const url = rootUrl + "/author/series/" + series.id;
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "PATCH",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
			"Content-Type": "application/json",
		},
		body: JSON.stringify(series),
	});
	if (response.status === 200) {
		const card = await response.json();
		return card;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}
export async function deleteSeriesItem(
	seriesId: number | string
): Promise<void> {
	const url = rootUrl + "/author/series/" + seriesId;
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "DELETE",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
		},
	});
	if (response.status === 204) {
		return;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}
export async function getSeriesArticlesCount(
	seriesId: number | string
): Promise<number> {
	const url = rootUrl + "/author/series-articles-count/" + seriesId;
	const response = await fetch(url);
	if (response.status === 200) {
		const count = await response.json();
		return count;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}
export async function getAllRemoteTags(): Promise<Tag[]> {
	const url = rootUrl + "/author/tags";
	const loginResponseData = getAccessToken();
	const response = await fetch(url, {
		method: "GET",
		headers: {
			Authorization:
				loginResponseData?.tokenType +
				" " +
				loginResponseData?.accessToken,
		},
	});
	if (response.status === 200) {
		const tagsData = await response.json();
		return tagsData as Tag[];
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}

export async function registerUser(
	formData: FormData
): Promise<RegisterResponseData> {
	const url = rootUrl + "/account/users";
	const response = await fetch(url, {
		method: "POST",
		body: formData,
	});
	if (response.status === 201) {
		const registerResponseData =
			(await response.json()) as RegisterResponseData;
		return registerResponseData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error as APIError;
	}
}

export async function login(formData: FormData): Promise<LoginResponseData> {
	const url = rootUrl + "/account/token";
	const response = await fetch(url, {
		method: "POST",
		body: formData,
	});
	if (response.status === 200) {
		const loginResponseData = await response.json();
		return loginResponseData as LoginResponseData;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error as APIError;
	}
}

export async function validateUser(): Promise<boolean> {
	const url = rootUrl + "/account/token";
	const loginResponseData = getAccessToken();
	const response = await fetch(url, {
		method: "GET",
		headers: {
			Authorization:
				loginResponseData?.tokenType +
				" " +
				loginResponseData?.accessToken,
		},
	});
	if (response.status === 200) {
		return true;
	} else {
		return false;
	}
}
export async function postMediaItem(file: File): Promise<{ url: string }> {
	const url = rootUrl + "/media/uploads";
	const formData = new FormData();
	formData.append("file", file);
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "POST",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
		},
		body: formData,
	});
	if (response.status === 201) {
		const data = await response.json();
		return data;
	} else {
		const errorResponse = await response.json();
		throw errorResponse.error;
	}
}
export async function patchArticleSeries(
	articleId: number,
	seriesId: number
): Promise<ArticleCard> {
	const url = rootUrl + "/author/article-series/" + articleId;
	const tokenData = getAccessToken();
	const response = await fetch(url, {
		method: "PATCH",
		headers: {
			Authorization: tokenData?.tokenType + " " + tokenData?.accessToken,
			"Content-Type": "application/json",
		},
		body: JSON.stringify({ seriesId: seriesId }),
	});
	if (response.status === 200) {
		const data = await response.json();
		return data;
	} else {
		const error = await response.json();
		throw error.error;
	}
}
export async function getSeriesArticleCouns(): Promise<SeriesArticleCount[]> {
	const url = rootUrl + "/author/series-article-counts";
	const response = await fetch(url);
	if (response.status === 200) {
		const data = await response.json();
		return data;
	} else {
		const error = await response.json();
		throw error.error;
	}
}
