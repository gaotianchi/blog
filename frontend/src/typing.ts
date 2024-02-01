export type Tag = {
	id: number;
	name: string;
};

export type Series = {
	id: number;
	title: string;
	cover: string;
	author: string;
};

export type Article = {
	id: number;
	title: string;
	slug: string;
	createdAt: Date;
	updated_At: Date;
	isPublished: boolean;
	publishedAt: Date;
	seriesId: number;
	authorId: number;
	tags: string[];
};
