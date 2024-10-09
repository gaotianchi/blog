import type { ArticleStatus } from '@/enum';

export type APIResponse<T> = {
	code: number;
	message: string;
	data: T;
};

export type Urls = {
	LOW: string;
	ORIGINAL: string;
	MEDIUM: string;
};

export type ImageResponse = {
	id: number;
	alt: string;
	urls: Urls;
	forAvatar: boolean;
	forArticle: boolean;
	forSeries: boolean;
	creationDatetime: Date;
	updateDatetime: Date;
};

export type UserResponse = {
	id: number;
	username: string;
	profile: string | null;
	timezone: string;
	penName: string;
	avatar: ImageResponse | null;
};

export type UserAccountResponse = {
	id: number;
	username: string;
	registrationDateTime: Date;
	lockedUntil: Date | null;
	accountStatus: string;
};

export type SeriesResponse = {
	id: number;
	name: string;
	creationDatetime: Date;
	cover: ImageResponse;
};

export type ArticleResponse = {
	id: number;
	title: string;
	body: string;
	summary: string;
	author: UserResponse;
	cover: ImageResponse;
	creationDatetime: Date;
	updateDatetime: Date;
	articleStatus: ArticleStatus;
	slug: string;
	series: SeriesResponse;
};

export type ArticleListResponse = {
	articleResponses: ArticleResponse[];
	totalPage: number;
	currentPage: number;
};

export type UserInfo = {
	id: number;
	penName: string;
	profile: string;
	timeZone: string;
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
	userInfoLocation: string;
	articleInfoPageLocation: string;
};
