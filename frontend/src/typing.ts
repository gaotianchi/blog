export type Tag = {
	id: number;
	name: string;
};
export type Series = {
	id: number;
	name: string;
	cover: string;
	authorId: number;
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
}