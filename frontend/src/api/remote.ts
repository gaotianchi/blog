import { reactive } from "vue";
import type {
	Article,
	Tag,
	Series,
	RegisterResponseData,
	LoginResponseData,
	SerializedArticle,
	SerializedArticleCard,
	ArticleCard,
} from "@/typing";
import { rootUrl } from "@/confit";
import { defaultArticle, defaultSeries } from "@/defaults";
import type { APIError } from "@/api/errors";
import { getAccessToken } from "@/api/local";
import { deserizalizeArticle } from "@/utlis";

export const remoteArticle: Article = reactive({ ...defaultArticle });
export const remoteSeries: Series = reactive({ ...defaultSeries });
export const allRemoteArticleCards: ArticleCard[] = reactive([]);
export const allRemoteSeries: Series[] = reactive([]);
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

export async function getRemoteSeriesItem(
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
export async function getAllRemoteSeriesItem(): Promise<Series[]> {
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
	const url = "http://localhost:5000/v1/media/uploads";
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
export async function postSeriesItem(seriesData: Series): Promise<Series> {
	const url = "http://localhost:5000/v1/author/series";
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
export async function getAllArticles(): Promise<ArticleCard[]> {
	const url = rootUrl + "/author/articles";
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
export async function patchArticleCardItem(
	articleId: number | string,
	serializedArticleCard: SerializedArticleCard
): Promise<Article> {
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
