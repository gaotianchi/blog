export type Tag = {
	id: number;
	name: string;
};
export type Series = {
	id: number;
	name: string;
	cover: string;
	authorId: number;
	createdAt: string;
};
export type SerializedArticle = {
	id: number;
	title: string;
	body: string;
	slug: string;
	createdAt: string;
	updatedAt: string;
	publishedAt: string;
	isPublished: boolean;
	authorId: number;
	tags: string[];
	seriesId: number;
};
export type SerializedArticleCard = {
	isPublished?: boolean;
	tags?: string[];
};
export type Article = {
	id: number;
	title: string;
	body: string;
	slug: string;
	createdAt: Date;
	updatedAt: Date;
	publishedAt: Date;
	isPublished: boolean;
	authorId: number;
	tags: string[];
	seriesId: number;
};
export type SettingStatus = {
	tags: {
		open: boolean;
	};
	datetime: {
		open: boolean;
		mode: "default" | "custom";
	};
	permalink: {
		open: boolean;
		mode: "default" | "custom";
	};
	series: {
		open: boolean;
		mode: "default" | "selected" | "new";
	};
};
export type Confirm = {
	header?: string;
	body: string;
	yesMessage?: string;
	noMessage?: string;
	callback: CallableFunction;
};
export type PreviewCover = {
	url: string;
	file: File | null;
};
export type InputStatus = "normal" | "error" | "success";
export type AuthAction = "login" | "register";
export type AuthStatus = "normal" | "loading" | "fail" | "success";
export type AuthFormData = {
	username: string;
	password: string;
};
export type InputElement = {
	status: InputStatus;
	value: string;
	conditions: { [key: string]: boolean };
};
export type RegisterStatus = {
	username: InputElement;
	password: InputElement;
	passwordConfirmation: InputElement;
};
export type LoginStatus = {
	username: InputElement;
	password: InputElement;
};
export type RegisterResponseData = {
	id: number;
	username: string;
	nickname: string;
	description: string;
	registeredAt: string;
	lastLoginAt: string;
	tokenValidityPeriod: number;
};
export type LoginResponseData = {
	accessToken: string;
	tokenType: string;
};
export type APIError = {
	target: string;
	message: string;
	statusCode: number;
};
export type SettingItem = {
	open: boolean;
	mode?: string;
};
export type ArticleCard = {
	id: number;
	title: string;
	createdAt: string;
	isPublished: boolean;
	tags: string[];
	images: string[];
	author: string;
	planned: boolean;
	seriesId: number;
};
export type ArticleCardMeta = "title" | "tag" | "author" | "series";
export type ArticleSearchField = ArticleCardMeta | "status";
export type ArticleCardStatus = "all" | "published" | "planned" | "draft";
export type ArticleCardWithIndex = {
	index: number;
	article: ArticleCard;
};
export type ArticleEditorLocalAndRemote = {
	[key: number]: {
		remote: Article;
		local: Article;
	};
};
export type ArticleSeriesLocalAndRemote = {
	[key: number]: {
		remote: Series;
		local: Series;
	};
};
export type SeriesArticleCount = {
	seriesId: number;
	articlesCount: number;
};
