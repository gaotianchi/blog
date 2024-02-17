import { reactive, ref, type Ref } from "vue";
import type { Article, SettingStatus, Tag, Confirm, Series } from "./typing";
import {
	defaultArticle,
	defaultSeries,
	defaultConfirm,
	defaultSettingStatus,
} from "@/defaults";
export const localArticle: Article = reactive({ ...defaultArticle });
export const message: Ref<string> = ref("");
export const localSeries: Series = reactive({ ...defaultSeries });
export const confirm: Confirm = reactive({ ...defaultConfirm });
export const settingStatus: SettingStatus = reactive({
	...defaultSettingStatus,
});
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
