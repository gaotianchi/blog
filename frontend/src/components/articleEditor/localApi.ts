import { reactive, ref } from "vue";
import type { Article, SettingStatus, Tag, Confirm, Series } from "./typing";
import defaults from "./defaults";

export const localArticle: Article = reactive({ ...defaults.article });
export const message = reactive({
	_value: "",
	get value(): string {
		return this._value;
	},
	set value(v: string) {
		this._value = v;
		setTimeout(() => {
			this._value = "";
		}, 2000);
	},
});
export const localSeries: Series = reactive({ ...defaults.serise });
export const confirm: Confirm = reactive({ ...defaults.confirm });
export const settingStatus: SettingStatus = reactive({
	...defaults.settingStatus,
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
