import { reactive } from "vue";
import type { Article, Tag } from "./typing";
import defaults from "./defaults";

export const localArticle: Article = reactive({ ...defaults.article });
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
