import type {
	RegisterStatus,
	InputElement,
	LoginStatus,
	Tag,
	Series,
	Article,
	SettingStatus,
	Confirm,
	PreviewCover,
	ArticleCard,
} from "@/typing";
import { dateFormatter, serializeDate } from "@/utlis";
export const defaultTag: Tag = {
	id: 0,
	name: "",
};
export const defaultSeries: Series = {
	id: 0,
	name: "",
	cover: "",
	authorId: 0,
	createdAt: serializeDate(new Date()),
};
export const defaultArticle: Article = {
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
export const defaultSettingStatus: SettingStatus = {
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
export const defaultConfirm: Confirm = {
	header: undefined,
	body: "",
	yesMessage: undefined,
	noMessage: undefined,
	callback: () => {},
};
export const defaultPreviewCover: PreviewCover = {
	url: "",
	file: null,
};

export const defaultInputElement: InputElement = {
	value: "",
	status: "normal",
	conditions: {},
};
export const defaultRegisterStatus: RegisterStatus = {
	username: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
			condition_3: true,
			condition_4: true,
		},
	},
	password: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
		},
	},
	passwordConfirmation: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
		},
	},
};
export const defaultLoginStatus: LoginStatus = {
	username: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
			condition_3: true,
			condition_4: true,
		},
	},
	password: {
		value: "",
		status: "normal",
		conditions: {
			condition_1: true,
			condition_2: true,
			condition_3: true,
		},
	},
};
export const defaultArticleCard: ArticleCard = {
	id: 0,
	title: "",
	createdAt: serializeDate(new Date()),
	isPublished: false,
	tags: [],
	images: [],
	author: "",
	planned: false,
	seriesId: 0,
};
export const defaultSerializedArticle = {
	id: 0,
	title: "",
	body: "",
	slug: "Blog_article_" + dateFormatter(new Date(), "YYYYMMDDhhmmss"),
	createdAt: serializeDate(new Date()),
	updatedAt: serializeDate(new Date()),
	publishedAt: serializeDate(new Date()),
	isPublished: false,
	tags: [],
	authorId: 0,
	seriesId: 0,
};
