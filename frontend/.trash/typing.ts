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
	createdAt: Date;
	updatedAt: Date;
	publishedAt: Date;
	isPublished: boolean;
	authorId: number;
	tags: string[];
	seriesId: number;
};
export type SettingStatus = {
	tags: {
		open: boolean;
	};
	datetime: {
		open: boolean;
		mode: "default" | "custom";
	};
	permalink: {
		open: boolean;
		mode: "default" | "custom";
	};
	series: {
		open: boolean;
		mode: "default" | "selected" | "new";
	};
};
export type Confirm = {
	header?: string;
	body: string;
	yesMessage?: string;
	noMessage?: string;
	callback: CallableFunction;
};
export type PreviewCover = {
	url: string;
	file: File | null;
};
