import type { Article, Confirm, Tag, LoginResponseData } from "@/typing";
import { confirmProp, messageProp } from "@/store";

export function propConfirm(cf: Confirm): void {
	Object.assign(confirmProp, cf);
}
export function propMessage(msg: string): void {
	messageProp.value = msg;
}
export function getArticle(type: "local" | "remote" = "local"): Article | null {
	let article: Article | null = null;
	switch (type) {
		case "local":
			const localArticleData = sessionStorage.getItem("localArticle");
			article = JSON.parse(localArticleData || "{}");
		case "remote":
			const remoteArticleData = sessionStorage.getItem("remoteArticle");
			article = JSON.parse(remoteArticleData || "{}");
	}
	if (article?.id) {
		return article;
	} else {
		return null;
	}
}

export function setArticle(
	article: Article,
	type: "local" | "remote" = "local"
): void {
	const articleString = JSON.stringify(article);
	switch (type) {
		case "local":
			sessionStorage.setItem("localArticle", articleString);
			return;
		case "remote":
			sessionStorage.setItem("remoteArticle", articleString);
			return;
	}
}
export function getAllLocalTags(): Tag[] {
	const data = sessionStorage.getItem("allLocalTags");
	if (data) {
		return JSON.parse(data);
	} else {
		return [];
	}
}
export function setAllLocalTags(tags: Tag[]): void {
	sessionStorage.setItem("allLocalTags", JSON.stringify(tags));
}

export function setAccessToken(data: LoginResponseData): void {
	localStorage.setItem("accessToken", JSON.stringify(data));
}
export function getAccessToken(): LoginResponseData | null {
	const data = localStorage.getItem("accessToken");
	if (data) {
		return JSON.parse(data);
	} else {
		return null;
	}
}
