export type APIResponse<T> = {
	code: number;
	message: string;
	data: T;
};

export type PageInfo<T> = {
	items: T[];
	totalPage: number;
	currentPage: number;
};

export type UserAccountResponse = {
	id: number;
	username: string;
	registrationDateTime: Date;
	lockedUntil: Date | null;
	accountStatus: string;
};

export type UserInfo = {
	id: number;
	penName: string;
	profile: string;
	timeZone: string;
	avatarUrl: string;
	avatarInfoLocation: string;
	articleInfoPageLocation: string;
	illustrationInfoPageLocation: string;
	seriesInfoPageLocation: string;
	seriesCoverInfoPageLocation: string;
	commentInfoPageLocation: string;
};

export type AvatarInfo = {
	id: number;
	filename: string;
	url: string;
	creationDatetime: string;
	userInfoLocation: string;
};

export type IllustrationInfo = {
	id: number;
	filename: string;
	title: string;
	alt: string;
	url: string;
	creationDatetime: string;
	updateDatetime: string;
	articleCount: number;
	userInfoLocation: string;
	articleInfoPageLocation: string;
};

export type ArticleInfo = {
	id: number;
	title: string;
	summary: string;
	status: string;
	slug: string;
	creationDatetime: string;
	publishDatetime: string;
	lastUpdatedDatetime: string;

	penName: string;
	seriesName: string;
	tagNames: string[];
	coverUrl: string;

	bodyValueLocation: string;
	userInfoLocation: string;
	seriesInfoLocation: string;

	illustrationInfoPageLocation: string;
	commentInfoPageLocation: string;
	tagInfoPageLocation: string;
};

export type TagInfo = {
	id: number;
	name: string;
	creationDatetime: string;
	articleCount: number;
	articleInfoPageLocation: string;
};
