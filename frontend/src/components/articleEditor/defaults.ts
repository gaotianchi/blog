import type {
	Tag,
	Series,
	Article,
	SettingStatus,
	Confirm,
} from "./typing";
import { dateFormatter } from "./utlis";
const defaultTag: Tag = {
	id: 0,
	name: "",
};
const defaultSeries: Series = {
	id: 0,
	name: "",
	cover: "",
	authorId: 0,
};
const defaultArticle: Article = {
	id: 0,
	title: "",
	body: "",
	slug: "Blog_article_" + dateFormatter(new Date(), "YYYYMMDDhhmmss"),
	createdAt: new Date(),
	updatedAt: new Date(),
	publishedAt: new Date(),
	isPublished: false,
	tags: [],
	authorId: 0,
	seriesId: 0,
};
const defaultSettingStatus: SettingStatus = {
	tags: {
		open: false,
	},
	datetime: {
		open: false,
		mode: "default",
	},
	permalink: {
		open: false,
		mode: "default",
	},
	series: {
		open: false,
		mode: "default",
	},
};
const defaultConfirm: Confirm = {
	header: undefined,
	body: "",
	yesMessage: undefined,
	noMessage: undefined,
	callback: () => {},
};
export default {
	tag: defaultTag,
	serise: defaultSeries,
	article: defaultArticle,
	settingStatus: defaultSettingStatus,
	confirm: defaultConfirm,
};
