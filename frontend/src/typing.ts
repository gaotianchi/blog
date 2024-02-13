

export type Tag = {
	id: number;
	name: string;
};

export type Series = {
	id: number;
	name: string;
	author_id: number;
	cover: string;
};

export type Settings = {
	tags: string[];
	datetime: Date;
	permalink: string;
	seriesId: number;
};

export type Article = {
	id: number;
	title: string;
	body: string;
	slug: string;
	createdAt: Date;
	updatedAt: Date;
	isPublished: boolean;
	publishedAt: Date;
	seriesId: number;
	authorId: number;
	tags: string[];
};

export type ArticleJson = {
	id: number;
	title: string;
	body: string;
	slug: string;
	created_at: string;
	updated_at: string;
	is_published: boolean;
	published_at: string;
	tags: string[];
	series_id: number | null;
};

export type MessageProp = {
	active: boolean;
	message: string;
};

export type ConfirmProp = {
	active: boolean;
	header?: string;
	body: string;
	yesMessage?: string;
	noMessage?: string;
	callback: CallableFunction;
};

export type TokenResponse = {
	accessToken: string;
	tokenType: string;
};
