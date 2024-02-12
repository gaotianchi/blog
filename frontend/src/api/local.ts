import type { Article } from "@/typing";

export function getArticle(type: "local" | "remote" = "local"): Article | null {
	let article: Article | null = null;
	switch (type) {
		case "local":
			const localArticleData = sessionStorage.getItem("localArticle");
			article = JSON.parse(localArticleData || "");
		case "remote":
			const remoteArticleData = sessionStorage.getItem("remoteArticle");
			article = JSON.parse(remoteArticleData || "");
	}
	return article;
}

export function setArticle(
	article: Article,
	type: "local" | "remote" = "local"
): void {
	const articleData = JSON.stringify(article);
	switch (type) {
		case "local":
			sessionStorage.setItem("localArticle", articleData);
			return;
		case "remote":
			sessionStorage.setItem("remoteArticle", articleData);
			return;
	}
}
