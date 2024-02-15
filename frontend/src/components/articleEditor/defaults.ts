import type { Tag, Series, Article } from "./typing";

const defaultTag: Tag = {
	id: 0,
	name: "",
};
const defaultSeries: Series = {
	id: 0,
	name: "",
	cover: "",
	authorId: 0,
};
const defaultArticle: Article = {
	id: 0,
	title: "",
	body: "",
	slug: "",
	createdAt: "",
	updatedAt: "",
	publishedAt: "",
	isPublished: false,
	tags: [],
	authorId: 0,
	seriesId: 0,
};

export default {
	tag: defaultTag,
	serise: defaultSeries,
	article: defaultArticle,
};
