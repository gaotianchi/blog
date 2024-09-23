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
	articleStatus: 'PUBLISHED' | 'DRAFT' | 'TRASH';
	slug: string;
	series: SeriesResponse;
};
