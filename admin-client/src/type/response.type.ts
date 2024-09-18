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
};

export type UserResponse = {
	id: number;
	username: string;
	profile: string;
	timezone: string;
	penName: string;
	avatar: ImageResponse;
};

export type UserAccountResponse = {
	id: number;
	username: string;
	registrationDateTime: Date;
};
