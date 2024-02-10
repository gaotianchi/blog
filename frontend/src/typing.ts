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
