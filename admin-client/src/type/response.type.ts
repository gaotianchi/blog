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
