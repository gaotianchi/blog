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
	createdAt: string;
	updatedAt: string;
	publishedAt: string;
	isPublished: boolean;
	authorId: number;
	tags: string[];
	seriesId: number;
}